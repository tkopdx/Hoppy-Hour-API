package beer.hoppyhour.api.service;

import java.util.List;

import javax.transaction.Transactional;

import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.exception.UserScheduleEventNotFoundException;
import beer.hoppyhour.api.model.UserScheduleEvent;

public interface IUserScheduleEventService<T extends UserScheduleEvent> {
    List<T> getEventsByUser(User user);
    T getById(Long id) throws UserScheduleEventNotFoundException;
    T save(T event);
    @Transactional
    void deleteById(Long id);
}
