package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Malt;

public interface MaltRepository extends PagingAndSortingRepository<Malt, Long> {
    
}
