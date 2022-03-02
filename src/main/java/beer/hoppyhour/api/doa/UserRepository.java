package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    
}
