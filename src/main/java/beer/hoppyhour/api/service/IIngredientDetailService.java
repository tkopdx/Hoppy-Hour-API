package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.IngredientDetail;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

public interface IIngredientDetailService<T extends IngredientDetail<T>> {
    T createNewDetail(PostRecipeRequestDetailEvent data);
    T save(T detail);
    T get(Long id);
}
