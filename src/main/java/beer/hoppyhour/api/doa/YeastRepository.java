package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Yeast;

public interface YeastRepository extends PagingAndSortingRepository<Yeast, Long> {
    
}
