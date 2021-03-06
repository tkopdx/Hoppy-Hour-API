package beer.hoppyhour.api.service;

import java.util.Base64;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import beer.hoppyhour.api.doa.PasswordResetTokenRepository;
import beer.hoppyhour.api.doa.RoleRepository;
import beer.hoppyhour.api.email.component.EmailServiceImpl;
import beer.hoppyhour.api.entity.PasswordResetToken;
import beer.hoppyhour.api.entity.Role;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;
import beer.hoppyhour.api.error.UserAlreadyExistsException;
import beer.hoppyhour.api.payload.request.SignupRequest;
import beer.hoppyhour.api.security.services.VerificationTokenService;
import io.jsonwebtoken.io.Encoders;

@Service
public class AuthService implements IAuthService {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;
    
    @Override
    public User persistNewUser(@Valid SignupRequest signupRequest) throws UserAlreadyExistsException {
        
        //Check database for duplicates and throw errors if found
        if (userService.existsByUsername(signupRequest.getUsername())) {
            throw new UserAlreadyExistsException("There is an account with the username: " + signupRequest.getUsername());
        }
        if (userService.existsByEmail(Encoders.BASE64.encode(signupRequest.getEmail().getBytes()))) {
            throw new UserAlreadyExistsException("There is an account with the email: " + signupRequest.getEmail());
        }

        //Create the user if not found
        User user = new User(signupRequest.getUsername(),
                    //Encode emails with Base64. Decode using Base64 decoder.
                    Encoders.BASE64.encode(signupRequest.getEmail().getBytes()),
                    //Encode passwords with Bcrypt. There is no way to decode these! 
                    passwordEncoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = null;
        Set<Role> roles = userService.getRolesFromStrings(strRoles);
        user.setRoles(roles);
        return userService.saveUser(user);
    }

    @Override
    @Transactional
    public VerificationToken generateNewVerificationToken(String previousToken) throws RuntimeException {
        try {
            //Get the previous token
            VerificationToken previous = verificationTokenService.findByToken(previousToken);
            //Get the associated user
            User user = userService.getUser(previous.getUser().getId());
            //Remove the previous token
            verificationTokenService.clearPreviousToken(user);
            //Create a new token and save it
            return verificationTokenService.createToken(user.getId());
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @Override
    public User getUserByToken(String token) {
        return verificationTokenService.findByToken(token).getUser();
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
    public void sendPasswordResetTokenEmail(PasswordResetToken token, User user) {
        byte[] decodedBytes = Base64.getDecoder().decode(user.getEmail());
        String to = new String(decodedBytes);
        String subject = "Password Reset Token from Hoppy Hour";
        //Hard coding the URL for now
        String strToken = token.getToken();
        String message = "Hey! Someone requested a password reset token from us. Use the following code in the Hoppy Hour app to reset your password.\r\n";
        //TODO alter base path for production
        String text = message + "\r\n" + "Your code: " + strToken;
        //send email
        emailService.sendSimpleEmail(to, subject, text);
    }

    
}
