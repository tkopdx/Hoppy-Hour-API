package beer.hoppyhour.api.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import beer.hoppyhour.api.doa.projection.RecipeSearchResult;
import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.payload.request.PostRecipeRequest;
import beer.hoppyhour.api.payload.response.MessageResponse;
import beer.hoppyhour.api.payload.response.PagingHeaders;
import beer.hoppyhour.api.payload.response.PagingResponse;
import beer.hoppyhour.api.payload.response.UserPublicInfoResponse;
import beer.hoppyhour.api.security.services.UserDetailsImpl;
import beer.hoppyhour.api.service.RecipePagingResponseService;
import beer.hoppyhour.api.service.RecipeService;
import beer.hoppyhour.api.service.UserService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    RecipePagingResponseService recipePagingResponseService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    // TODO get recipes with filtering, ordering, sorting
    @Transactional
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RecipeSearchResult>> get(
            @And({
                    @Spec(path = "name", params = "name", spec = Like.class),
                    //TODO This is broken right now. Fix string to instant conversion
                    // @Spec(path = "createdDate", params = { "createdDateGt", "createdDateLt"}, spec = Between.class, config = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX'Z'"),
                    @Spec(path = "method", params = "method", spec = Like.class),
                    @Spec(path = "style", params = "style", spec = Like.class),
                    @Spec(path = "place.country", params = "country", spec = Like.class),
                    @Spec(path = "place.city", params = "city", spec = Like.class),
                    @Spec(path = "type", params = "type", spec = Like.class),
                    @Spec(path = "boilTime", params = {"boilTimeGt", "boilTimeLt"}, spec = Between.class),
                    @Spec(path = "boilTime", params = "boilTime", spec = Equal.class),
                    @Spec(path = "batchSize", params = {"batchSizeGt", "batchSizeLt"}, spec = Between.class),
                    @Spec(path = "batchSize", params = "batchSize", spec = Equal.class),
                    @Spec(path = "ibu", params = {"ibuGt", "ibuLt"}, spec = Between.class),
                    @Spec(path = "ibu", params = "ibu", spec = Equal.class),
                    @Spec(path = "srm", params = {"srmGt", "srmLt"}, spec = Between.class),
                    @Spec(path = "srm", params = "srm", spec = Equal.class),
                    @Spec(path = "cost", params = {"costGt", "costLt"}, spec = Between.class),
                    @Spec(path = "cost", params = "cost", spec = Equal.class),
                    @Spec(path = "rating", params = {"ratingGt", "ratingLt"}, spec = Between.class),
                    @Spec(path = "rating", params = "rating", spec = Equal.class),
                    @Spec(path = "abv", params = { "abvGt", "abvLt" }, spec = Between.class),
                    @Spec(path = "abv", params = "abv", spec = Equal.class),
            }) Specification<Recipe> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) {
        final PagingResponse<RecipeSearchResult> response = recipePagingResponseService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getData(), returnHttpHeaders(response), HttpStatus.OK);
    }

    // TODO get a recipe
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSingleRecipe(@PathVariable Long id) {
        try {
            Recipe recipe = recipeService.getRecipeById(id);
            return ResponseEntity.ok().body(
                recipe
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new MessageResponse(
                    "Error! " + e.getMessage()
                )
            );
        }
    }

    //TODO get the recipe author's public info
    @GetMapping("/{id}/user")
    public ResponseEntity<?> getRecipeAuthorInfo(@PathVariable Long id) {
        try {
            User user = recipeService.getRecipeById(id).getUser();
            return ResponseEntity.ok().body(
                new UserPublicInfoResponse(
                    user.getUsername(), 
                    user.getCreatedDate(),
                    user.getId())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    // TODO add a recipe if a user
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addNewRecipe(@RequestBody PostRecipeRequest request, Authentication authentication) {
        try {
            //get the currently logged in user
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userService.getUser(userDetails.getId());
            //process required recipe fields
            Recipe recipe = recipeService.createNewRecipe(request, user);
            //set ingredient details and events
            recipe = recipeService.setIngredientDetailsAndEvents(request, recipe);
            //return good response with new recipe info
            return ResponseEntity.ok().body(
                recipe
            );
        } catch (Exception e) {
            //return bad response
            return ResponseEntity.badRequest().body(
                new MessageResponse(
                    "Error! " + e.getMessage()
                )
            );
        }
        
    }
    // TODO delete a recipe if user id matches recipe user id

    // TODO edit a recipe if user id matches recipe user id


    //builds the RecipePagingResponse HttpHeaders object
    public HttpHeaders returnHttpHeaders(PagingResponse<RecipeSearchResult> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
}