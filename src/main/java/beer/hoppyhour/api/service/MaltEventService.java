package beer.hoppyhour.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.IngredientDetailRecipeEventRepository;
import beer.hoppyhour.api.entity.IngredientDetailRecipeEvent;
import beer.hoppyhour.api.entity.MaltDetail;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class MaltEventService implements IIngredientRecipeEventService<MaltDetail> {

    @Autowired
    IngredientDetailRecipeEventRepository ingredientDetailRecipeEventRepository;

    @Override
    public IngredientDetailRecipeEvent<MaltDetail> createNewEvent(PostRecipeRequestDetailEvent data) {
        IngredientDetailRecipeEvent<MaltDetail> event = new IngredientDetailRecipeEvent<>(data.getSecondsToFlameOut(), data.getEventNotes(), data.getPause());
        return save(event);
    }

    @Override
    public IngredientDetailRecipeEvent<MaltDetail> save(IngredientDetailRecipeEvent<MaltDetail> event) {
        return ingredientDetailRecipeEventRepository.save(event);
    }
    
}
