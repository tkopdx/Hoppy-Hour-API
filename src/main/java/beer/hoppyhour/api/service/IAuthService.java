package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;
import beer.hoppyhour.api.payload.request.SignupRequest;

public interface IAuthService {
    User persistNewUser(SignupRequest signupRequest);
    User findUserById(Long id);
    void saveRegisteredUser(User user);
    VerificationToken getVerificationToken(String token);
    VerificationToken createVerificationToken(User user, String token);
    void deleteVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String previousToken);
    User getUserByToken(String token);
    void resendVerificationEmail(User user, VerificationToken token);
}
