package beer.hoppyhour.api.controller;

import java.util.HashSet;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import beer.hoppyhour.api.service.IngredientService;
import beer.hoppyhour.api.service.RecipePagingResponseService;
import beer.hoppyhour.api.service.RecipeService;
import beer.hoppyhour.api.service.UserService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Joins;

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

    @Autowired
    IngredientService ingredientService;

    // TODO get recipes with filtering, ordering, sorting
    @Transactional
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> get(
            @Joins({
                    @Join(path = "hopDetails", alias = "h")
            }) @And({
                    @Spec(path = "h.ingredient.name", params = "hop", spec = In.class), // looks for all recipes that
                                                                                        // match at least one of the hop
                                                                                        // names
                    @Spec(path = "name", params = "name", spec = Like.class),
                    // TODO This is broken right now. Fix string to instant conversion
                    // @Spec(path = "createdDate", params = { "createdDateGt", "createdDateLt"},
                    // spec = Between.class, config = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX'Z'"),
                    @Spec(path = "method", params = "method", spec = Like.class),
                    @Spec(path = "style", params = "style", spec = Like.class),
                    @Spec(path = "place.country", params = "country", spec = Like.class),
                    @Spec(path = "place.city", params = "city", spec = Like.class),
                    @Spec(path = "type", params = "type", spec = Like.class),
                    @Spec(path = "boilTime", params = { "boilTimeGt", "boilTimeLt" }, spec = Between.class),
                    @Spec(path = "boilTime", params = "boilTime", spec = Equal.class),
                    @Spec(path = "batchSize", params = { "batchSizeGt", "batchSizeLt" }, spec = Between.class),
                    @Spec(path = "batchSize", params = "batchSize", spec = Equal.class),
                    @Spec(path = "ibu", params = { "ibuGt", "ibuLt" }, spec = Between.class),
                    @Spec(path = "ibu", params = "ibu", spec = Equal.class),
                    @Spec(path = "srm", params = { "srmGt", "srmLt" }, spec = Between.class),
                    @Spec(path = "srm", params = "srm", spec = Equal.class),
                    @Spec(path = "cost", params = { "costGt", "costLt" }, spec = Between.class),
                    @Spec(path = "cost", params = "cost", spec = Equal.class),
                    @Spec(path = "rating", params = { "ratingGt", "ratingLt" }, spec = Between.class),
                    @Spec(path = "rating", params = "rating", spec = Equal.class),
                    @Spec(path = "abv", params = { "abvGt", "abvLt" }, spec = Between.class),
                    @Spec(path = "abv", params = "abv", spec = Equal.class),
            }) Specification<Recipe> spec,
            Sort sort,
            @PathParam("page-size") Long pageSize,
            @PathParam("page-number") Long pageNumber) {

        try {
            final PagingResponse<RecipeSearchResult> response = recipePagingResponseService.get(spec, pageSize,
                    pageNumber, sort);
            return new ResponseEntity<>(response.getData(), returnHttpHeaders(response), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse(
                            "Error! " + e.getMessage()));
        }
    }

    @GetMapping("/searchbyingredients")
    public ResponseEntity<?> searchByIngredients(@RequestParam("id") HashSet<Long> ids, Sort sort,
            @RequestParam(name = "page-size", required = false) Long pageSize,
            @RequestParam(name = "page-number", required = false) Long pageNumber) {
        try {
            // query with paging
            final PagingResponse<RecipeSearchResult> recipes = recipePagingResponseService.getAllByExample(ids,
                    pageSize, pageNumber, sort);
            // return good response
            return ResponseEntity.ok().body(
                    recipes);
        } catch (Exception e) {
            // return bad response
            return ResponseEntity.badRequest().body(
                    new MessageResponse(
                            "Error! " + e.getMessage()));
        }

    }

    // TODO get a recipe
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSingleRecipe(@PathVariable Long id) {
        try {
            Recipe recipe = recipeService.getRecipeById(id);
            return ResponseEntity.ok().body(
                    recipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse(
                            "Error! " + e.getMessage()));
        }
    }

    // TODO get the recipe author's public info
    @GetMapping("/{id}/user")
    public ResponseEntity<?> getRecipeAuthorInfo(@PathVariable Long id) {
        try {
            User user = recipeService.getRecipeById(id).getUser();
            return ResponseEntity.ok().body(
                    new UserPublicInfoResponse(
                            user.getUsername(),
                            user.getCreatedDate(),
                            user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    // TODO add a recipe if a user
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addNewRecipe(@RequestBody PostRecipeRequest request, Authentication authentication) {
        try {
            // get the currently logged in user
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userService.getUser(userDetails.getId());
            // process required recipe fields
            Recipe recipe = recipeService.createNewRecipe(request, user);
            // set ingredient details and events
            recipe = recipeService.setIngredientDetailsAndEvents(request, recipe);
            // return good response with new recipe info
            return ResponseEntity.ok().body(
                    recipe);
        } catch (Exception e) {
            // return bad response
            return ResponseEntity.badRequest().body(
                    new MessageResponse(
                            "Error! " + e.getMessage()));
        }

    }

    // TODO delete a recipe if user id matches recipe user id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id, Authentication authentication) {
        try {
            // get user details
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            // get the requested recipe and check against user id
            Recipe recipe = recipeService.getRecipeById(id);
            // delete if verified
            if (recipeService.isRecipeOwner(recipe, userDetails.getId())) {
                recipeService.delete(recipe);
            }

            // return response
            return ResponseEntity.ok().body(
                new MessageResponse(
                    "Successfully deleted the recipe."
                )
            );
            
        } catch (Exception e) {
            // return bad response
            return ResponseEntity.badRequest().body(
                new MessageResponse(
                    "Error! " + e.getMessage()
                )
            );
        }

    }

    // TODO edit a recipe if user id matches recipe user id
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id, Authentication authentication, @RequestBody PostRecipeRequest request) {
        try {
            //get user details
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            //get recipe
            Recipe recipe = recipeService.getRecipeById(id);
            //if verified, update recipe
            if (recipeService.isRecipeOwner(recipe, userDetails.getId())) {
                recipe = recipeService.update(recipe, request);
            }
            //return recipe
            return ResponseEntity.ok().body(
                    recipe);

        } catch (Exception e) {
            //return bad response
            return ResponseEntity.badRequest().body(
                new MessageResponse(
                    "Error! " + e.getMessage()
                )
            );
        }
    }

    // builds the RecipePagingResponse HttpHeaders object
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
