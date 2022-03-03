package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Brewing;

public interface BrewingRepository extends PagingAndSortingRepository<Brewing, Long> {
    
}
