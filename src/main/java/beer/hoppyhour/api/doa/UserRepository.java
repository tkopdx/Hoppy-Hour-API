package beer.hoppyhour.api.doa;

import org.springframework.data.repository.CrudRepository;

import beer.hoppyhour.api.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
}
