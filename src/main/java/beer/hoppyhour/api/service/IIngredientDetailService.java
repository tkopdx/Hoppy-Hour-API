package beer.hoppyhour.api.service;

import java.util.List;

import beer.hoppyhour.api.entity.Ingredient;
import beer.hoppyhour.api.entity.IngredientDetail;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

public interface IIngredientDetailService<T extends IngredientDetail<T>, A extends Ingredient<T>> {
    T createNewDetail(PostRecipeRequestDetailEvent data);
    List<T> createDetailsForExampleSearch(List<A> ingredients);
    T save(T detail);
    T get(Long id);
}
