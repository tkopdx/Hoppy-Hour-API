package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Place;

public interface PlaceRepository extends PagingAndSortingRepository<Place, Long> {
    
}
