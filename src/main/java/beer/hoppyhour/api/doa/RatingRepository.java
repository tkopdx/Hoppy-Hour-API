package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Rating;

public interface RatingRepository extends PagingAndSortingRepository<Rating, Long> {
    
}
