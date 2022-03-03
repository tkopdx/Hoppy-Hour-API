package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Scheduling;

public interface SchedulingRepository extends PagingAndSortingRepository<Scheduling, Long> {
    
}
