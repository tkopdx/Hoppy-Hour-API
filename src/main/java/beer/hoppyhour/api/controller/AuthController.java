package beer.hoppyhour.api.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import beer.hoppyhour.api.doa.RoleRepository;
import beer.hoppyhour.api.entity.PasswordResetToken;
import beer.hoppyhour.api.entity.RefreshToken;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;
import beer.hoppyhour.api.exception.TokenExpiredException;
import beer.hoppyhour.api.payload.request.LoginRequest;
import beer.hoppyhour.api.payload.request.PasswordResetConfirm;
import beer.hoppyhour.api.payload.request.PasswordResetRequest;
import beer.hoppyhour.api.payload.request.SignupRequest;
import beer.hoppyhour.api.payload.request.TokenRefreshRequest;
import beer.hoppyhour.api.payload.response.JwtResponse;
import beer.hoppyhour.api.payload.response.MessageResponse;
import beer.hoppyhour.api.payload.response.TokenRefreshResponse;
import beer.hoppyhour.api.registration.OnRegistrationCompleteEvent;
import beer.hoppyhour.api.security.jwt.JwtUtils;
import beer.hoppyhour.api.security.services.PasswordResetTokenService;
import beer.hoppyhour.api.security.services.RefreshTokenService;
import beer.hoppyhour.api.security.services.UserDetailsImpl;
import beer.hoppyhour.api.security.services.VerificationTokenService;
import beer.hoppyhour.api.service.AuthService;
import beer.hoppyhour.api.service.UserService;
import io.jsonwebtoken.io.Encoders;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    AuthService authService;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    UserService userService;
    @Autowired
    VerificationTokenService verificationTokenService;
    @Autowired
    PasswordResetTokenService passwordResetTokenService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        try {

            //Alter JwtResponse and use this user object if you need more user info sent back to client
            // User user = authService.findUserById(userDetails.getId());

            //Build a jwt
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
            //delete any previous refresh tokens
            refreshTokenService.clearPreviousToken(userService.getUser(userDetails.getId()));
            //create a new refresh token
            RefreshToken refreshToken = refreshTokenService.createToken(userDetails.getId());
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toSet());
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(
                            new JwtResponse(
                                refreshToken.getToken(), 
                                userDetails.getId(), 
                                userDetails.getUsername(), 
                                roles)
                    );
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }

    @PostMapping("/refreshtoken")
    // @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        try {
            String refreshToken = request.getRefreshToken();
            RefreshToken token = refreshTokenService.findByToken(refreshToken);
            if (token == null) {
                throw new TokenExpiredException(refreshToken, "Refresh token is not in the database!");
            }
            token = refreshTokenService.verifyExpiration(token);
            User user = token.getUser();
            String jwtString = jwtUtils.generateTokenFromUsername(user.getUsername());
            ResponseCookie cookie = jwtUtils.getRefreshedJwtCookie(jwtString);
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body(
                            new TokenRefreshResponse(
                                refreshToken
                            )
                        );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
    @Transactional
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) {
        try {
            VerificationToken verificationToken = verificationTokenService.findByToken(token);
            if (verificationToken == null) {
                // return bad request
                return ResponseEntity.badRequest().body(new MessageResponse("Your token is invalid."));
            }

            User user = verificationToken.getUser();
            verificationToken = verificationTokenService.verifyExpiration(verificationToken);

            //enable the user and save
            user.setEnabled(true);
            userService.saveUser(user);
            //delete verification token
            verificationTokenService.deleteByUserId(user.getId());
            return ResponseEntity.ok(new MessageResponse("Your email has been successfully verified. Cheers!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new MessageResponse(
                    "Error! " + e.getMessage()
                )
            );
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out! Have a nice day."));
    }

    @GetMapping("/resendRegistrationToken")
    @Transactional
    public ResponseEntity<?> resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
        try {
            //Generate a new token
            VerificationToken newToken = authService.generateNewVerificationToken(existingToken);
            //Get user for later email usage
            User user = authService.getUserByToken(newToken.getToken());
            //Construct email and send
            authService.resendVerificationEmail(user, newToken);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        
        return ResponseEntity.ok().body(new MessageResponse("A new verification email has been sent! Please verify within 24 hours."));
    }

    //allows a user to patch their own password
    @PostMapping("/requestPasswordReset")
    @Transactional
    public ResponseEntity<?> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        try {
            //get user via email
            User user = userService.getUserByEmail(Encoders.BASE64.encode(request.getEmail().getBytes()));
            //delete any previous tokens
            passwordResetTokenService.clearPreviousToken(user);
            //create a password reset token and save it
            PasswordResetToken passwordResetToken = passwordResetTokenService.createToken(user.getId());

            //send email with custom token
            authService.sendPasswordResetTokenEmail(passwordResetToken, user);

            return ResponseEntity.ok().body(
                new MessageResponse(
                    "A password reset token has been sent to your email. Please use that token to reset your password."
                )
            );
        } catch (Exception e) {
            //return bad request
            return ResponseEntity.badRequest().body(
                new MessageResponse(
                    "Error!" + e.getMessage()
                )
            );
        }
    }

    @PostMapping("/confirmPasswordReset")
    @Transactional
    public ResponseEntity<?> confirmPasswordReset(@Valid @RequestBody PasswordResetConfirm request) {
        try {
            
            //get the token
            PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(request.getToken());

            //verify token exists and expiration
            passwordResetToken = passwordResetTokenService.verifyExpiration(passwordResetToken);
            
            //if verified, get user
            User user = passwordResetToken.getUser();
            //encrypt new password
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userService.saveUser(user);
            //delete token
            passwordResetTokenService.deleteByUserId(user.getId());
            //send good response
            return ResponseEntity.ok().body(
                new MessageResponse(
                    "Your password has been successfully updated."
                )
            );
        } catch (Exception e) {
            //send bad response
            return ResponseEntity.badRequest().body(
                new MessageResponse(
                    "Error! " + e.getMessage()
                )
            );
        }

    }
}
