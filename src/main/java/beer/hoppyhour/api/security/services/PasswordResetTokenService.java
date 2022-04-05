package beer.hoppyhour.api.security.services;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import beer.hoppyhour.api.doa.PasswordResetTokenRepository;
import beer.hoppyhour.api.entity.PasswordResetToken;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.TokenExpiredException;
import beer.hoppyhour.api.service.UserService;

@Service
public class PasswordResetTokenService implements ITokenService<PasswordResetToken> {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    UserService userService;

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public PasswordResetToken findByUser(User user) {
        return passwordResetTokenRepository.findByUser(user);
    }

    @Override
    public PasswordResetToken createToken(Long userId) {
        User user = userService.getUser(userId);
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        user.setPasswordResetToken(passwordResetToken);
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken verifyExpiration(PasswordResetToken token) {
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            deleteByUserId(token.getUser().getId());
            System.out.println("the token is expired! throwing error");
            throw new TokenExpiredException(token.getToken(), "The password reset token was expired. Open the Hoppy Hour App and request another password reset token.");
        }
        return token;
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        User user = userService.getUser(userId);
        user.setPasswordResetToken(null);
        //setting token to null and saving user removes the orphan token from database
        userService.saveUser(user);
    }

    @Override
    @Transactional
    public void clearPreviousToken(User user) {
        if (findByUser(user) != null) {
            deleteByUserId(user.getId());
        }
    }
    
}
