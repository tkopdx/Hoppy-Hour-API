package beer.hoppyhour.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.BrewingRepository;
import beer.hoppyhour.api.entity.Brewing;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.UserScheduleEventNotFoundException;

@Service
public class BrewingService implements IUserScheduleEventService<Brewing> {

    @Autowired
    BrewingRepository brewingRepository;

    @Override
    public List<Brewing> getEventsByUser(User user) {
        return brewingRepository.findByUser(user);
    }

    @Override
    public Brewing getById(Long id) throws UserScheduleEventNotFoundException {
        Optional<Brewing> brewing = brewingRepository.findById(id);
        if (brewing.isPresent()) {
            return brewing.get();
        } else {
            throw new UserScheduleEventNotFoundException(
                "There was no brewing event found with id, " + id
            );
        }
    }

    @Override
    public Brewing save(Brewing event) {
        return brewingRepository.save(event);
    }

    @Override
    public void deleteById(Long id) {
        brewingRepository.deleteById(id);
    }
    
}
