package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.HopDetail;

public interface HopDetailRepository extends PagingAndSortingRepository<HopDetail, Long> {
    
}
