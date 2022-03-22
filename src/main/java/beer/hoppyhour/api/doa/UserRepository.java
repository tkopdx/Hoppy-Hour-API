package beer.hoppyhour.api.doa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import beer.hoppyhour.api.entity.User;
//TODO confirm that methods are protected. A logged in user should only be able to CRUD their own info. An admin can CRUD anything.

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
}
