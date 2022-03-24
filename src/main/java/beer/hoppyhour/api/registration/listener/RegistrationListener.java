package beer.hoppyhour.api.registration.listener;

import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import beer.hoppyhour.api.email.component.EmailServiceImpl;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.registration.OnRegistrationCompleteEvent;
import beer.hoppyhour.api.service.AuthService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    
    @Autowired
    private AuthService service;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailServiceImpl emailService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        byte[] decodedBytes = Base64.getDecoder().decode(user.getEmail());
        String to = new String(decodedBytes);
        String subject = "Verify your email with Hoppy Hour";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String resendUrl = event.getAppUrl() + "/resendRegistrationToken?token=" + token;
        String baseUrl = "http://localhost:8080/api/auth";
        String message = "Hey! Someone signed up for Hoppy Hour with this email at " + event.getLocale() + ". Hopefully, that was you. If it was, the final step before you're hanging out with a bunch of cool brewing buddies is to verify your email. Please click the following link to verify your email and begin your brewing adventure!";
        //TODO alter base path for production
        String text = message + "\r\n" + 
                    baseUrl + confirmationUrl + "\r\n" + 
                    "If your link expires, please click the following to get another:" + "\r\n" +
                    baseUrl + resendUrl;
        //send email
        emailService.sendSimpleEmail(to, subject, text);
    }

}
