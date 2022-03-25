package beer.hoppyhour.api.doa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import beer.hoppyhour.api.entity.ToBrew;
import beer.hoppyhour.api.entity.User;

@PreAuthorize("hasRole('ROLE_USER')")
public interface ToBrewRepository extends PagingAndSortingRepository<ToBrew, Long> {
    //allow users to delete their own breweds
    @Override
    void deleteById(Long id);

    //allow users to save or update their own breweds
    @Override
    <S extends ToBrew> S save(S entity);

    //find all breweds associated with user
    List<ToBrew> findByUser(User user);
}
