package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Hop;

public interface HopRepository extends PagingAndSortingRepository<Hop, Long> {
    
}
