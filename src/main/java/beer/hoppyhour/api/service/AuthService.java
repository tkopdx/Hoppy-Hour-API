package beer.hoppyhour.api.service;

import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import beer.hoppyhour.api.doa.RoleRepository;
import beer.hoppyhour.api.doa.UserRepository;
import beer.hoppyhour.api.doa.VerificationTokenRepository;
import beer.hoppyhour.api.email.component.EmailServiceImpl;
import beer.hoppyhour.api.entity.Role;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;
import beer.hoppyhour.api.error.UserAlreadyExistsException;
import beer.hoppyhour.api.model.ERole;
import beer.hoppyhour.api.payload.request.SignupRequest;
import io.jsonwebtoken.io.Encoders;

@Service
public class AuthService implements IAuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    EmailServiceImpl emailService;
    
    @Override
    public User persistNewUser(@Valid SignupRequest signupRequest) throws UserAlreadyExistsException {
        
        //Check database for duplicates and throw errors if found
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new UserAlreadyExistsException("There is an account with the username: " + signupRequest.getUsername());
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UserAlreadyExistsException("There is an account with the email: " + signupRequest.getEmail());
        }

        //Create the user if not found
        User user = new User(signupRequest.getUsername(),
                    //Encode emails with Base64. Decode using Base64 decoder.
                    Encoders.BASE64.encode(signupRequest.getEmail().getBytes()),
                    //Encode passwords with Bcrypt. There is no way to decode these! 
                    passwordEncoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> throwRoleNotFound());
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> throwRoleNotFound());
                        roles.add(adminRole);
                        break;
                    case "expert":
                        Role expertRole = roleRepository.findByName(ERole.ROLE_EXPERT)
                                        .orElseThrow(() -> throwRoleNotFound());
                        roles.add(expertRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                        .orElseThrow( () -> throwRoleNotFound());
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(() -> throwRoleNotFound());
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("Unable to find a user with id " + id);
        }
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    private RuntimeException throwRoleNotFound() {
        RuntimeException e = new RuntimeException("Error: Role is not found.");
        return e;
    }

    @Override
    public VerificationToken createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        return tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String previousToken) throws RuntimeException {
        try {
            //Get the previous token
            VerificationToken previous = getVerificationToken(previousToken);
            //Get the associated user
            User user = findUserById(previous.getUser().getId());
            //Create a new string token
            String newToken = UUID.randomUUID().toString();
            //Create a new token and save it
            return createVerificationToken(user, newToken);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @Override
    public User getUserByToken(String token) {
        return getVerificationToken(token).getUser();
    }

    @Override
    public void resendVerificationEmail(User user, VerificationToken token) {
        byte[] decodedBytes = Base64.getDecoder().decode(user.getEmail());
        String to = new String(decodedBytes);
        String subject = "Another verification code from Hoppy Hour";
        //Hard coding the URL for now
        String baseUrl = "http://localhost:8080/api/auth";
        String resendUrl = "/resendRegistrationToken?token=" + token.getToken().toString();
        String confirmationUrl = "/registrationConfirm?token=" + token.getToken().toString();
        String message = "Hey! Someone requested a new verification email from us. Hopefully, that was you. If it was, the final step before you're hanging out with a bunch of cool brewing buddies is to verify your email. Please click the following link to verify your email and begin your brewing adventure!";
        //TODO alter base path for production
        String text = message + "\r\n" + 
                    baseUrl + confirmationUrl + "\r\n" +
                    "If your token expires, please click the following click to get another:" + "\r\n" +
                    baseUrl + resendUrl;
        //send email
        emailService.sendSimpleEmail(to, subject, text);
    }

    @Override
    @Transactional
    public int deleteVerificationToken(String token) {
        return tokenRepository.deleteByToken(token);
    }
}
