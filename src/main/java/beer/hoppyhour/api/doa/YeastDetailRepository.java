package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.YeastDetail;

public interface YeastDetailRepository extends PagingAndSortingRepository<YeastDetail, Long> {
    
}
