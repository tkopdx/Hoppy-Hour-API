package beer.hoppyhour.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.IngredientRepository;
import beer.hoppyhour.api.entity.Ingredient;
import beer.hoppyhour.api.entity.IngredientDetail;

@Service
public class IngredientService implements IIngredientService<Ingredient<? extends IngredientDetail<?>>> {

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public Ingredient<? extends IngredientDetail<?>> get(Long id) {
        Optional<Ingredient<? extends IngredientDetail<?>>> ingredient = ingredientRepository.findById(id);
        if (ingredient.isPresent()) {
            return ingredient.get();
        } else {
            throw new EntityNotFoundException(
                "The ingredient with id " + id + " was not found."
            );
        }
    }

    @Override
    public List<Ingredient<? extends IngredientDetail<?>>> getAllById(List<Long> ids) {
        Iterable<Ingredient<? extends IngredientDetail<?>>> iterableIngredients = ingredientRepository.findAllById(ids);
        return StreamSupport.stream(iterableIngredients.spliterator(), false)
        .collect(Collectors.toList());
    }

    public Set<Ingredient<? extends IngredientDetail<?>>> getAllByIdAsSet(List<Long> ids) {
        Iterable<Ingredient<? extends IngredientDetail<?>>> iterableIngredients = ingredientRepository.findAllById(ids);
        return StreamSupport.stream(iterableIngredients.spliterator(), false)
        .collect(Collectors.toSet());
    }
    
}
