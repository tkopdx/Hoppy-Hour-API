package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;
import beer.hoppyhour.api.payload.request.SignupRequest;

public interface IAuthService {
    User persistNewUser(SignupRequest signupRequest);
    User findUserById(Long id);
    void saveRegisteredUser(User user);
    VerificationToken getVerificationToken(String token);
    void createVerificationToken(User user, String token);
}
