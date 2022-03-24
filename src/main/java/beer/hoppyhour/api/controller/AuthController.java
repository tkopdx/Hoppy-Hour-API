package beer.hoppyhour.api.controller;

import java.util.Calendar;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import beer.hoppyhour.api.doa.RoleRepository;
import beer.hoppyhour.api.doa.UserRepository;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;
import beer.hoppyhour.api.payload.request.LoginRequest;
import beer.hoppyhour.api.payload.request.SignupRequest;
import beer.hoppyhour.api.payload.response.MessageResponse;
import beer.hoppyhour.api.payload.response.UserInfoResponse;
import beer.hoppyhour.api.registration.OnRegistrationCompleteEvent;
import beer.hoppyhour.api.security.jwt.JwtUtils;
import beer.hoppyhour.api.security.services.UserDetailsImpl;
import beer.hoppyhour.api.service.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        try {
            User user = authService.findUserById(userDetails.getId());

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(
                        new UserInfoResponse(
                                userDetails.getId(),
                                userDetails.getUsername(),
                                userDetails.getEmail(),
                                user.getCreatedDate(),
                                user.getupdatedDate(),
                                roles));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }

    // the flow for user registration
    @PostMapping("/signup")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody SignupRequest signupRequest,
            HttpServletRequest request) {
        try {
            // save the new user info in the database. It is not yet enabled.
            User registered = authService.persistNewUser(signupRequest);
            String apiUrl = request.getContextPath();
            // start an event listener that sends an email with a verification token once
            // notified that the user info has been saved.
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
                    apiUrl,
                    request.getLocale(),
                    registered));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        // user info successfully saved and email sent, but user is not yet enabled
        return ResponseEntity
                .ok(new MessageResponse("User registered successfully! Please verify your email within 24 hours."));
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) {
        // Locale locale = request.getLocale();

        VerificationToken verificationToken = authService.getVerificationToken(token);
        if (verificationToken == null) {
            // throw error
            return ResponseEntity.badRequest().body(new MessageResponse("Your token is invalid."));
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            return ResponseEntity.badRequest().body(new MessageResponse("Your token is expired."));
        }

        user.setEnabled(true);
        authService.saveRegisteredUser(user);
        return ResponseEntity.ok(new MessageResponse("Your email has been successfully verified. Cheers!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out! Have a nice day."));
    }
}
