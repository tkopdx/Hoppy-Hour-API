package beer.hoppyhour.api.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.HopRepository;
import beer.hoppyhour.api.entity.Hop;

@Service
public class HopService implements IIngredientService<Hop> {

    @Autowired
    HopRepository hopRepository;

    @Override
    public Hop get(Long id) {
        Optional<Hop> hop = hopRepository.findById(id);
        if (hop.isPresent()) {
            return hop.get();
        } else {
            throw new EntityNotFoundException("No hop with id " + id + " was found.");
        }
    }
    
}
