package beer.hoppyhour.api.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.MaltRepository;
import beer.hoppyhour.api.entity.Malt;

@Service
public class MaltService implements IIngredientService<Malt> {

    @Autowired
    MaltRepository maltRepository;

    @Override
    public Malt get(Long id) {
        Optional<Malt> malt = maltRepository.findById(id);
        if (malt.isPresent()) {
            return malt.get();
        } else {
            throw new EntityNotFoundException("No malt with id " + id + " was found.");
        }
    }
    
}
