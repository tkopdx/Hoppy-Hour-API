package beer.hoppyhour.api.service;

import java.util.Set;

import beer.hoppyhour.api.entity.Role;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.UserNotFoundException;

public interface IUserService {
    User getUser(Long id) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    User saveUser(User user);
    void deleteUserById(Long id);
    Set<Role> getRolesFromStrings(Set<String> strRoles);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    void sendEmailChangedEmail(User user);
}
