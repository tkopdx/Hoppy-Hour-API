package beer.hoppyhour.api.doa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import beer.hoppyhour.api.entity.User;
//TODO confirm that methods are protected. A logged in user should only be able to CRUD their own info. An admin can CRUD anything.

// @PreAuthorize("hasRole('ROLE_USER')")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
    @Override
    //Before allowing client to execute findById, checks to see if the id parameter in the request matches the id of the currently logged in account.
    @PreAuthorize("#id == authentication.principal.id")
    Optional<User> findById(Long id);
    
    
}
