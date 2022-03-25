package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.UserNotFoundException;

public interface IUserService {
    User getUser(Long id) throws UserNotFoundException;
    User saveUser(User user);
}
