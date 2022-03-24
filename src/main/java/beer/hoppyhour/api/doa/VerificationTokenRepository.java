package beer.hoppyhour.api.doa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;

@RestResource(exported = false)
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
}
