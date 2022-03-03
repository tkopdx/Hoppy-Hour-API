package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.OtherIngredient;

public interface OtherIngredientRepository extends PagingAndSortingRepository<OtherIngredient, Long> {
    
}
