package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Ingredient;
import beer.hoppyhour.api.entity.IngredientDetail;

//TODO Creating a repository from a raw type may be poor practice.
//See IngredientDetailRepository for similar issue and rationale.
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient<? extends IngredientDetail<?>>, Long> {

}
