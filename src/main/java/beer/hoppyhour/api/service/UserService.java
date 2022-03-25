package beer.hoppyhour.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.UserRepository;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.UserNotFoundException;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

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
    
}
