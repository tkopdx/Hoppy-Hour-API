package beer.hoppyhour.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.IngredientDetailRecipeEventRepository;
import beer.hoppyhour.api.entity.IngredientDetailRecipeEvent;
import beer.hoppyhour.api.entity.YeastDetail;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class YeastEventService implements IIngredientRecipeEventService<YeastDetail> {

    @Autowired
    IngredientDetailRecipeEventRepository ingredientDetailRecipeEventRepository;

    @Override
    public IngredientDetailRecipeEvent<YeastDetail> createNewEvent(PostRecipeRequestDetailEvent data) {
        IngredientDetailRecipeEvent<YeastDetail> event = new IngredientDetailRecipeEvent<>(data.getSecondsToFlameOut(), data.getEventNotes(), data.getPause());
        return save(event);
    }

    @Override
    public IngredientDetailRecipeEvent<YeastDetail> save(IngredientDetailRecipeEvent<YeastDetail> event) {
        return ingredientDetailRecipeEventRepository.save(event);
    }
    
}
