package beer.hoppyhour.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import beer.hoppyhour.api.model.AuthToken;

@Entity
@Table(name = "refresh_token")
public class RefreshToken extends AuthToken {

    public RefreshToken() {}

    public RefreshToken(String token, User user) {
        super(token, user);
    }

}
