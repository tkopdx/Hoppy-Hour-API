package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Brewed;

public interface BrewedRepository extends PagingAndSortingRepository<Brewed, Long> {
    
}
