package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.IngredientDetail;
import beer.hoppyhour.api.entity.IngredientDetailRecipeEvent;

public interface IngredientDetailRecipeEventRepository extends PagingAndSortingRepository<IngredientDetailRecipeEvent<? extends IngredientDetail<?>>, Long> {
    
}
