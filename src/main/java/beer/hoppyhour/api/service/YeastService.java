package beer.hoppyhour.api.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.YeastRepository;
import beer.hoppyhour.api.entity.Yeast;

@Service
public class YeastService implements IIngredientService<Yeast> {

    @Autowired
    YeastRepository yeastRepository;

    @Override
    public Yeast get(Long id) {
        Optional<Yeast> yeast = yeastRepository.findById(id);
        if (yeast.isPresent()) {
            return yeast.get();
        } else {
            throw new EntityNotFoundException("No yeast with id " + id + " was found.");
        }
    }
    
}
