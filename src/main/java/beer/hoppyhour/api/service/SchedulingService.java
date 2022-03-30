package beer.hoppyhour.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.SchedulingRepository;
import beer.hoppyhour.api.entity.Scheduling;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.UserScheduleEventNotFoundException;

@Service
public class SchedulingService implements IUserScheduleEventService<Scheduling> {
    @Autowired
    SchedulingRepository schedulingRepository;

    @Override
    public List<Scheduling> getEventsByUser(User user) {
        return schedulingRepository.findByUser(user);
    }

    @Override
    public Scheduling getById(Long id) throws UserScheduleEventNotFoundException {
        Optional<Scheduling> scheduling = schedulingRepository.findById(id);
        if (scheduling.isPresent()) {
            return scheduling.get();
        }  else {
            throw new UserScheduleEventNotFoundException("No event found with id " + id);
        }
    }

    @Override
    public Scheduling save(Scheduling event) {
        return schedulingRepository.save(event);
    }

    @Override
    public void deleteById(Long id) {
        schedulingRepository.deleteById(id);
    }
    
}
