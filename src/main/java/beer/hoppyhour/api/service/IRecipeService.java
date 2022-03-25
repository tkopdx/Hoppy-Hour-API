package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.Recipe;

public interface IRecipeService {
    Recipe getRecipeById(Long id);
}
