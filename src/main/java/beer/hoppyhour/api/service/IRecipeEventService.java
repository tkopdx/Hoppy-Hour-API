package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.RecipeEvent;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

public interface IRecipeEventService {
    RecipeEvent createNewEvent(PostRecipeRequestDetailEvent data);
    RecipeEvent save(RecipeEvent event);
}
