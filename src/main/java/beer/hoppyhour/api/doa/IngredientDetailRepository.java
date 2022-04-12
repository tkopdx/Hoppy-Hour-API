package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.IngredientDetail;

//TODO Creating a repository from a raw type may be poor practice.
//However, this does create the desired effect of opening the api/ingredient/id/ingredientDetails and it solves the issues of ingredientDetails being eagerly loaded.
public interface IngredientDetailRepository extends PagingAndSortingRepository<IngredientDetail<? extends IngredientDetail<?>>, Long> {
    
}
