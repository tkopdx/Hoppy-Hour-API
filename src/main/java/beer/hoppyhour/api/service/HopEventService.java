package beer.hoppyhour.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.IngredientDetailRecipeEventRepository;
import beer.hoppyhour.api.entity.HopDetail;
import beer.hoppyhour.api.entity.IngredientDetailRecipeEvent;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class HopEventService implements IIngredientRecipeEventService<HopDetail> {

    @Autowired
    IngredientDetailRecipeEventRepository ingredientDetailRecipeEventRepository;

    @Override
    public IngredientDetailRecipeEvent<HopDetail> createNewEvent(PostRecipeRequestDetailEvent data) {
        IngredientDetailRecipeEvent<HopDetail> event = new IngredientDetailRecipeEvent<>(data.getSecondsToFlameOut(), data.getEventNotes(), data.getPause());
        return save(event);
    }

    @Override
    public IngredientDetailRecipeEvent<HopDetail> save(IngredientDetailRecipeEvent<HopDetail> event) {
        return ingredientDetailRecipeEventRepository.save(event);
    }
    
}
