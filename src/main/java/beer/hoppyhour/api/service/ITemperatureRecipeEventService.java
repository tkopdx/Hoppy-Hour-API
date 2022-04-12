package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.TemperatureRecipeEvent;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

public interface ITemperatureRecipeEventService {
    TemperatureRecipeEvent createNewEvent(PostRecipeRequestDetailEvent data);
    TemperatureRecipeEvent save(TemperatureRecipeEvent event);
}
