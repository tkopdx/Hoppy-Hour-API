package beer.hoppyhour.api.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.BrewedRepository;
import beer.hoppyhour.api.entity.Brewed;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.UserScheduleEventNotFoundException;

@Service
public class BrewedService implements IUserScheduleEventService<Brewed> {

    @Autowired
    BrewedRepository brewedRepository;

    @Autowired
    RecipeService recipeService;

    @Override
    public List<Brewed> getEventsByUser(User user) {
        return brewedRepository.findByUser(user);
    }

    @Override
    public Brewed getById(Long id) throws UserScheduleEventNotFoundException {
        Optional<Brewed> brewed = brewedRepository.findById(id);

        if (brewed.isPresent()) {
            return brewed.get();
        } else {
            throw new UserScheduleEventNotFoundException("No event found with id " + id);
        }
    }

    @Override
    public Brewed save(Brewed event) {
        return brewedRepository.save(event);
    }

    @Override
    public void deleteById(Long id) {
        brewedRepository.deleteById(id);
    }
    
}
