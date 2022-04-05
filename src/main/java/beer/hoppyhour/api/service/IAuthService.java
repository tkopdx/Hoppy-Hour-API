package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.PasswordResetToken;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;
import beer.hoppyhour.api.payload.request.SignupRequest;

public interface IAuthService {
    User persistNewUser(SignupRequest signupRequest);
    VerificationToken generateNewVerificationToken(String previousToken);
    User getUserByToken(String token);
    void resendVerificationEmail(User user, VerificationToken token);
    void sendPasswordResetTokenEmail(PasswordResetToken token, User user);
}
