package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Ingredient;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {

}
