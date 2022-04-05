package beer.hoppyhour.api.security.services;

import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.model.AuthToken;

public interface ITokenService<T extends AuthToken> {
    T findByToken(String token);
    T findByUser(User user);
    T createToken(Long userId);
    T verifyExpiration(T token);
    void deleteByUserId(Long userId);
    void clearPreviousToken(User user);
}
