package beer.hoppyhour.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.RecipeRepository;
import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.exception.RecipeNotFoundException;

@Service
public class RecipeService implements IRecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public Recipe getRecipeById(Long id) throws RecipeNotFoundException {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        } else {
            throw new RecipeNotFoundException("Recipe with id " + id + " was not found.");
        }
    }
    
}
