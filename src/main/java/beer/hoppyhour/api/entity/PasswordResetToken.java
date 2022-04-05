package beer.hoppyhour.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import beer.hoppyhour.api.model.AuthToken;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken extends AuthToken {

    public PasswordResetToken() {}

    public PasswordResetToken(String token, User user) {
        super(token, user);
    }

}
