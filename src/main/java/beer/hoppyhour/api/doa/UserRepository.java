package beer.hoppyhour.api.doa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import beer.hoppyhour.api.entity.User;
//TODO confirm that methods are protected. A logged in user should only be able to CRUD their own info. An admin can CRUD anything.

// @PreAuthorize("hasRole('ROLE_USER')")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> findAll();
    
    @Override
    @PreAuthorize("#user.name == authentication?.name or hasRole('ROLE_ADMIN')")
    void delete(User entity);
    
    @Override
    @PreAuthorize("@userRepository.findById(#id)?.user?.name == authentication?.name or hasRole('ROLE_ADMIN')")
    void deleteById(Long id);
    
    @Override
    @PreAuthorize("@userRepository.findById(#id)?.user?.name == authentication?.name or hasRole('ROLE_ADMIN')")
    Optional<User> findById(Long id);
    
    // @PreAuthorize("#user.name == authentication?.name or hasRole('ROLE_ADMIN')")
    // <S extends User> S save(@Param("user") User entity);
}
