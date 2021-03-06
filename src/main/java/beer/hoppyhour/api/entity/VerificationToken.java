package beer.hoppyhour.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import beer.hoppyhour.api.model.AuthToken;

@Entity
@Table(name = "verification_token")
public class VerificationToken extends AuthToken {

    public VerificationToken() {}

    public VerificationToken(String token, User user) {
        super(token, user);
    }

}
