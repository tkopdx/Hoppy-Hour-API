package beer.hoppyhour.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.TemperatureRecipeEventRepository;
import beer.hoppyhour.api.entity.TemperatureRecipeEvent;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class TemperatureRecipeEventService implements ITemperatureRecipeEventService {

    @Autowired
    TemperatureRecipeEventRepository temperatureRecipeEventRepository;

    @Override
    public TemperatureRecipeEvent createNewEvent(PostRecipeRequestDetailEvent data) {
        TemperatureRecipeEvent event = new TemperatureRecipeEvent(data.getSecondsToFlameOut(), data.getEventNotes(), data.getPause(), data.getTemperature());
        return save(event);
    }

    @Override
    public TemperatureRecipeEvent save(TemperatureRecipeEvent event) {
        return temperatureRecipeEventRepository.save(event);
    }
    
}
