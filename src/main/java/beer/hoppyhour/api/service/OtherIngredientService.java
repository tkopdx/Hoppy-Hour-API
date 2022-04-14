package beer.hoppyhour.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<OtherIngredient> getAllById(List<Long> ids) {
        Iterable<OtherIngredient> iterableHops = otherIngredientRepository.findAllById(ids);
        return StreamSupport.stream(iterableHops.spliterator(), false)
        .collect(Collectors.toList());
    }
    
}
