package beer.hoppyhour.api.service;

import java.util.List;

import beer.hoppyhour.api.entity.Ingredient;

public interface IIngredientService<T extends Ingredient<?>> {
    T get(Long id);
    List<T> getAllById(List<Long> ids);
}
