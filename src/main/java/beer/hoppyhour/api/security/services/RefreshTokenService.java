package beer.hoppyhour.api.security.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.RefreshTokenRepository;
import beer.hoppyhour.api.entity.RefreshToken;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.TokenRefreshException;
import beer.hoppyhour.api.service.UserService;

@Service
public class RefreshTokenService {
    @Value("${hoppyhour.api.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserService userService;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public Optional<RefreshToken> findByUser(User user) {
        return refreshTokenRepository.findByUser(user);
    }

    public RefreshToken createRefreshToken(Long userId) {
        Instant expiryDate = Instant.now().plusMillis(refreshTokenDurationMs);
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(token, expiryDate);
        User user = userService.getUser(userId);
        refreshToken.setUser(user);
        refreshToken = refreshTokenRepository.save(refreshToken);
        user.setRefreshToken(refreshToken);
        return refreshToken;
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            deleteByUserId(token.getUser().getId());
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request.");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        User user = userService.getUser(userId);
        user.setRefreshToken(null);
        return refreshTokenRepository.deleteByUser(user);
    }

    @Transactional
    public void clearPreviousToken(User user) {
        if (findByUser(user).isPresent()) {
            deleteByUserId(user.getId());
        }
    }
}
