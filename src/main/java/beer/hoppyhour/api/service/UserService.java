package beer.hoppyhour.api.service;

import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.RoleRepository;
import beer.hoppyhour.api.doa.UserRepository;
import beer.hoppyhour.api.email.component.EmailService;
import beer.hoppyhour.api.entity.Role;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.RoleNotFoundException;
import beer.hoppyhour.api.exception.UserNotFoundException;
import beer.hoppyhour.api.model.ERole;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmailService emailService;

    @Override
    public User getUser(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("with id, " + id);
        }
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Set<Role> getRolesFromStrings(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            //if the request includes no roles, then the default is a user
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RoleNotFoundException("There is no user role."));
            roles.add(userRole);
        } else {
            //if they include roles, add those roles
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new RoleNotFoundException("There is no admin role."));
                        roles.add(adminRole);
                        break;
                    case "expert":
                        Role expertRole = roleRepository.findByName(ERole.ROLE_EXPERT)
                                        .orElseThrow(() -> new RoleNotFoundException("There is no expert role."));
                        roles.add(expertRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                        .orElseThrow(() -> new RoleNotFoundException("There is no mod role."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(() -> new RoleNotFoundException("There is no user role."));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("with email, " + Base64.getDecoder().decode(email));
        }
    }

    @Override
    public void sendEmailChangedEmail(User user) {
        String to = new String(Base64.getDecoder().decode(user.getEmail())); 
        String subject = "A Hoppy Hour user has set email address this as their email.";
        String text = "It looks like a Hoppy Hour user has set this email address as their email. If that was you, then great! If not, please feel free to disregard this email or to contact us at SOME EMAIL.";
        emailService.sendSimpleEmail(to, subject, text);
    }
    
}
