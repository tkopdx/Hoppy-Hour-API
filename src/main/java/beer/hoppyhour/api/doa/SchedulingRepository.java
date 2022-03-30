package beer.hoppyhour.api.doa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Scheduling;
import beer.hoppyhour.api.entity.User;

public interface SchedulingRepository extends PagingAndSortingRepository<Scheduling, Long> {
    //allow users to delete their own breweds
    @Override
    void deleteById(Long id);

    //allow users to save or update their own breweds
    @Override
    <S extends Scheduling> S save(S entity);

    //find all breweds associated with user
    List<Scheduling> findByUser(User user);
}
