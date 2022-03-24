package beer.hoppyhour.api.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.RoleRepository;
import beer.hoppyhour.api.doa.UserRepository;
import beer.hoppyhour.api.doa.VerificationTokenRepository;
import beer.hoppyhour.api.entity.Role;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;
import beer.hoppyhour.api.error.UserAlreadyExistsException;
import beer.hoppyhour.api.model.ERole;
import beer.hoppyhour.api.payload.request.SignupRequest;
import io.jsonwebtoken.io.Encoders;

//TODO refresh verification tokens

@Service
public class AuthService implements IAuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository tokenRepository;
    
    @Override
    public User persistNewUser(@Valid SignupRequest signupRequest) throws UserAlreadyExistsException {
        
        //Check database for duplicates and throw errors if found
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new UserAlreadyExistsException("There is an account with the username: " + signupRequest.getUsername());
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UserAlreadyExistsException("There is an account with the email: " + signupRequest.getEmail());
        }

        //Create the user if not found
        User user = new User(signupRequest.getUsername(),
                    //Encode emails with Base64. Decode using Base64 decoder.
                    Encoders.BASE64.encode(signupRequest.getEmail().getBytes()),
                    //Encode passwords with Bcrypt. There is no way to decode these! 
                    passwordEncoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> throwRoleNotFound());
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> throwRoleNotFound());
                        roles.add(adminRole);
                        break;
                    case "expert":
                        Role expertRole = roleRepository.findByName(ERole.ROLE_EXPERT)
                                        .orElseThrow(() -> throwRoleNotFound());
                        roles.add(expertRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                        .orElseThrow( () -> throwRoleNotFound());
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(() -> throwRoleNotFound());
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("Unable to find a user with id " + id);
        }
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    private RuntimeException throwRoleNotFound() {
        RuntimeException e = new RuntimeException("Error: Role is not found.");
        return e;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
}
