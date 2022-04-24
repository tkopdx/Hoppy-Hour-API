package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.payload.request.PostRecipeRequest;

public interface IRecipeService {
    Recipe getRecipeById(Long id);
    Recipe createNewRecipe(PostRecipeRequest request, User user);
    Recipe setIngredientDetailsAndEvents(PostRecipeRequest request, Recipe recipe);
    Recipe save(Recipe recipe);
    void delete(Recipe recipe);
    Boolean isRecipeOwner(Recipe recipe, Long userId) throws Exception;
    Recipe update(Recipe recipe, PostRecipeRequest request);
    Recipe setRecipeFields(Recipe recipe, PostRecipeRequest request);
}
