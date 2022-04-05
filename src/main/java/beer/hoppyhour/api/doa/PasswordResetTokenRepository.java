package beer.hoppyhour.api.doa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import beer.hoppyhour.api.entity.PasswordResetToken;
import beer.hoppyhour.api.entity.User;

@RepositoryRestResource(exported = false)
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    int deleteByToken(String token);
    PasswordResetToken findByUser(User user);
}
