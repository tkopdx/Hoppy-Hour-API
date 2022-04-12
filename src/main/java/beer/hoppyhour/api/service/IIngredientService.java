package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.Ingredient;

public interface IIngredientService<T extends Ingredient<?>> {
    T get(Long id);
}
