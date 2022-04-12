package beer.hoppyhour.api.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.OtherIngredientRepository;
import beer.hoppyhour.api.entity.OtherIngredient;

@Service
public class OtherIngredientService implements IIngredientService<OtherIngredient> {

    @Autowired
    OtherIngredientRepository otherIngredientRepository;

    @Override
    public OtherIngredient get(Long id) {
        Optional<OtherIngredient> otherIngredient = otherIngredientRepository.findById(id);
        if (otherIngredient.isPresent()) {
            return otherIngredient.get();
        } else {
            throw new EntityNotFoundException("No other ingredient with id " + id + " was found.");
        }
    }
    
}
