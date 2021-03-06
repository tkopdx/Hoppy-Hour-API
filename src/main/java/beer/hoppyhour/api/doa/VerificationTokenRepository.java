package beer.hoppyhour.api.doa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;

@RepositoryRestResource(exported = false)
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
    int deleteByToken(String token);
    int deleteByUser(User user);
}
