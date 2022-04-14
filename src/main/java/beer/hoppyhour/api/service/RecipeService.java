package beer.hoppyhour.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.RecipeRepository;
import beer.hoppyhour.api.entity.HopDetail;
import beer.hoppyhour.api.entity.IngredientDetailRecipeEvent;
import beer.hoppyhour.api.entity.MaltDetail;
import beer.hoppyhour.api.entity.OtherIngredientDetail;
import beer.hoppyhour.api.entity.Place;
import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.entity.RecipeEvent;
import beer.hoppyhour.api.entity.TemperatureRecipeEvent;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.YeastDetail;
import beer.hoppyhour.api.exception.RecipeNotFoundException;
import beer.hoppyhour.api.payload.request.PostRecipeRequest;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class RecipeService implements IRecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    HopDetailService hopDetailService;

    @Autowired
    MaltDetailService maltDetailService;

    @Autowired
    OtherIngredientDetailService otherIngredientDetailService;

    @Autowired
    YeastDetailService yeastDetailService;

    @Autowired
    HopEventService hopEventService;

    @Autowired
    MaltEventService maltEventService;

    @Autowired
    OtherIngredientEventService otherIngredientEventService;

    @Autowired
    YeastEventService yeastEventService;

    @Autowired
    TemperatureRecipeEventService temperatureRecipeEventService;

    @Autowired
    RecipeEventService recipeEventService;

    @Autowired
    PlaceService placeService;

    @Autowired
    HopService hopService;

    @Autowired
    MaltService maltService;

    @Autowired
    OtherIngredientService otherIngredientService;

    @Autowired
    YeastService yeastService;

    @Override
    public Recipe getRecipeById(Long id) throws RecipeNotFoundException {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        } else {
            throw new RecipeNotFoundException("Recipe with id " + id + " was not found.");
        }
    }

    @Override
    public Recipe createNewRecipe(PostRecipeRequest request, User user) {
        // create new recipe object with request data
        Recipe recipe = new Recipe(
                request.getName(),
                request.getOriginalGravity(),
                request.getFinalGravity(),
                request.getMethod(),
                request.getStyle(),
                request.getBoilTime(),
                request.getBatchSize(),
                request.getPreBoilSize(),
                request.getPostBoilSize(),
                request.getPreBoilGravity(),
                request.getEfficiency(),
                request.getHopUtilization(),
                request.getIbu(),
                request.getSrm(),
                request.getMashpH(),
                request.getCost());
        //associate place and recipe
        Place place = placeService.get(request.getPlace().getPlaceId());
        place.addRecipe(recipe);
        //associate user and recipe
        user.addRecipe(recipe);
        //save
        return save(recipe);
    }

    @Override
    public Recipe setIngredientDetailsAndEvents(PostRecipeRequest request, Recipe recipe) {
        // for each item in request.detailEvents
        for (PostRecipeRequestDetailEvent detailEvent : request.getDetailEvents()) {
            // sort by type
            if (detailEvent.getType() != null) {
                switch (detailEvent.getType()) {
                    case "hop":
                        //create a new detail
                        HopDetail hopDetail = hopDetailService.createNewDetail(detailEvent);
                        //create a new event
                        IngredientDetailRecipeEvent<HopDetail> hopEvent = hopEventService.createNewEvent(detailEvent);
                        //establish the one-to-one relationship
                        hopDetail.setEvent(hopEvent);
                        //link recipe to detail
                        recipe.addHopDetail(hopDetail);
                        //link recipe to event
                        recipe.addEvent(hopEvent);
                        //link recipe and ingredient
                        recipe.addIngredient(hopDetail.getIngredient());
                        break;
                    case "malt":
                        MaltDetail maltDetail = maltDetailService.createNewDetail(detailEvent);
                        IngredientDetailRecipeEvent<MaltDetail> maltEvent = maltEventService.createNewEvent(detailEvent);
                        maltDetail.setEvent(maltEvent);
                        recipe.addMaltDetail(maltDetail);
                        recipe.addEvent(maltEvent);
                        recipe.addIngredient(maltDetail.getIngredient());
                        break;
                    case "other":
                        OtherIngredientDetail otherIngredientDetail = otherIngredientDetailService
                                .createNewDetail(detailEvent);
                        IngredientDetailRecipeEvent<OtherIngredientDetail> otherEvent = otherIngredientEventService.createNewEvent(detailEvent);
                        otherIngredientDetail.setEvent(otherEvent);
                        recipe.addOtherIngredientDetail(otherIngredientDetail);
                        recipe.addEvent(otherEvent);
                        recipe.addIngredient(otherIngredientDetail.getIngredient());
                        break;
                    case "yeast":
                        YeastDetail yeastDetail = yeastDetailService.createNewDetail(detailEvent);
                        IngredientDetailRecipeEvent<YeastDetail> yeastEvent = yeastEventService.createNewEvent(detailEvent);
                        yeastDetail.setEvent(yeastEvent);
                        recipe.addYeastDetail(yeastDetail);
                        recipe.addEvent(yeastEvent);
                        recipe.addIngredient(yeastDetail.getIngredient());
                        break;
                }
            } else if (detailEvent.getTemperature() != null) {
                //create a new temp event
                TemperatureRecipeEvent tempEvent = temperatureRecipeEventService.createNewEvent(detailEvent);
                //add temp event to recipe
                recipe.addEvent(tempEvent);
            } else {
                //create a new note event
                RecipeEvent noteEvent = recipeEventService.createNewEvent(detailEvent);
                //add note event to recipe
                recipe.addEvent(noteEvent);
            }
        }
        // return recipe
        return recipe;
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

}
