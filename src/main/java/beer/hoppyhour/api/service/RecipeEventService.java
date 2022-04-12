package beer.hoppyhour.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.RecipeEventRepository;
import beer.hoppyhour.api.entity.RecipeEvent;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class RecipeEventService implements IRecipeEventService {

    @Autowired
    RecipeEventRepository recipeEventRepository;

    @Override
    public RecipeEvent createNewEvent(PostRecipeRequestDetailEvent data) {
        RecipeEvent event = new RecipeEvent(data.getSecondsToFlameOut(), data.getEventNotes(), data.getPause());
        return save(event);
    }

    @Override
    public RecipeEvent save(RecipeEvent event) {
        return recipeEventRepository.save(event);
    }
    
}
