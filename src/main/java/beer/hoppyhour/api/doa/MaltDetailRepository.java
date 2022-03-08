package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.MaltDetail;

public interface MaltDetailRepository extends PagingAndSortingRepository<MaltDetail, Long> {
    
}
