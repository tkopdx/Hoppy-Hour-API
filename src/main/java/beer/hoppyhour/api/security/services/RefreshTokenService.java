package beer.hoppyhour.api.security.services;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.RefreshTokenRepository;
import beer.hoppyhour.api.entity.RefreshToken;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.TokenExpiredException;
import beer.hoppyhour.api.service.UserService;

@Service
public class RefreshTokenService implements ITokenService<RefreshToken> {
    @Value("${hoppyhour.api.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserService userService;

    @Override
    public RefreshToken findByToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken.isPresent()) {
            return refreshToken.get();
        } else {
            return null;
        }
    }

    @Override
    public RefreshToken findByUser(User user) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUser(user);
        if (refreshToken.isPresent()) {
            return refreshToken.get();
        } else {
            return null;
        }
    }

    @Override
    public RefreshToken createToken(Long userId) {
        String token = UUID.randomUUID().toString();
        User user = userService.getUser(userId);
        RefreshToken refreshToken = new RefreshToken(token, user);
        refreshToken.setUser(user);
        refreshToken = refreshTokenRepository.save(refreshToken);
        user.setRefreshToken(refreshToken);
        return refreshToken;
    }

    @Override
    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            deleteByUserId(token.getUser().getId());
            throw new TokenExpiredException(token.getToken(), "Refresh token was expired. Please make a new signin request.");
        }
        return token;
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        User user = userService.getUser(userId);
        user.setRefreshToken(null);
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
