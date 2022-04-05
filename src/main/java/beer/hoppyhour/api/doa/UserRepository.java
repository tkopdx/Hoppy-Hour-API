package beer.hoppyhour.api.doa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import beer.hoppyhour.api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
