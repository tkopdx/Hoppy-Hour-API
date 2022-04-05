package beer.hoppyhour.api.security.services;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import beer.hoppyhour.api.doa.VerificationTokenRepository;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;
import beer.hoppyhour.api.exception.TokenExpiredException;
import beer.hoppyhour.api.service.UserService;

@Service
public class VerificationTokenService implements ITokenService<VerificationToken> {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserService userService;

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    @Override
    public VerificationToken createToken(Long userId) {
        User user = userService.getUser(userId);
        String token = UUID.randomUUID().toString();
        VerificationToken myToken = new VerificationToken(token, user);
        user.setVerificationToken(myToken);
        return verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken verifyExpiration(VerificationToken token) {
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            deleteByUserId(token.getUser().getId());
            throw new TokenExpiredException(token.getToken(), "The email verification token was expired. Use the resend verification email link in your email to receive a fresh link.");
        }
        return token;
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        User user = userService.getUser(userId);
        user.setVerificationToken(null);
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
