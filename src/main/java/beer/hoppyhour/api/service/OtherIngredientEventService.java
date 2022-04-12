package beer.hoppyhour.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.IngredientDetailRecipeEventRepository;
import beer.hoppyhour.api.entity.IngredientDetailRecipeEvent;
import beer.hoppyhour.api.entity.OtherIngredientDetail;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class OtherIngredientEventService implements IIngredientRecipeEventService<OtherIngredientDetail> {

    @Autowired
    IngredientDetailRecipeEventRepository ingredientDetailRecipeEventRepository;

    @Override
    public IngredientDetailRecipeEvent<OtherIngredientDetail> createNewEvent(PostRecipeRequestDetailEvent data) {
        IngredientDetailRecipeEvent<OtherIngredientDetail> event = new IngredientDetailRecipeEvent<>(data.getSecondsToFlameOut(), data.getEventNotes(), data.getPause());
        return save(event);
    }

    @Override
    public IngredientDetailRecipeEvent<OtherIngredientDetail> save(
            IngredientDetailRecipeEvent<OtherIngredientDetail> event) {
        return ingredientDetailRecipeEventRepository.save(event);
    }
    
}
