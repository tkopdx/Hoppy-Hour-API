package beer.hoppyhour.api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.ToBrewRepository;
import beer.hoppyhour.api.entity.ToBrew;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.UserScheduleEventNotFoundException;

@Service
public class ToBrewService implements IUserScheduleEventService<ToBrew> {

    @Autowired
    ToBrewRepository toBrewRepository;

    @Override
    public List<ToBrew> getEventsByUser(User user) {
        return toBrewRepository.findByUser(user);
    }

    @Override
    public ToBrew getById(Long id) throws UserScheduleEventNotFoundException {
        Optional<ToBrew> toBrew = toBrewRepository.findById(id);

        if (toBrew.isPresent()) {
            return toBrew.get();
        } else {
            throw new UserScheduleEventNotFoundException("No event found with id " + id);
        }
    }

    @Override
    public ToBrew save(ToBrew event) {
        return toBrewRepository.save(event);
    }

    @Override
    public void deleteById(Long id) {
        toBrewRepository.deleteById(id);
    }

    public ToBrew update(Long id, long when) {
        ToBrew toBrew = getById(id);
        Date date = new Date(when);
        toBrew.setWhenToBrew(date);
        return save(toBrew);
    }
    
}
