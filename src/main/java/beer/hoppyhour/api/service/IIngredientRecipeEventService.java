package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.IngredientDetail;
import beer.hoppyhour.api.entity.IngredientDetailRecipeEvent;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

public interface IIngredientRecipeEventService<T extends IngredientDetail<T>> {
    IngredientDetailRecipeEvent<T> createNewEvent(PostRecipeRequestDetailEvent data);
    IngredientDetailRecipeEvent<T> save(IngredientDetailRecipeEvent<T> event);
}
