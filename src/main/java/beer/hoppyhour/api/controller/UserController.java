package beer.hoppyhour.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import beer.hoppyhour.api.entity.Brewed;
import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.entity.ToBrew;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.payload.request.EmailPatchRequest;
import beer.hoppyhour.api.payload.request.UserScheduleEventDeleteRequest;
import beer.hoppyhour.api.payload.request.UserScheduleEventSaveRequest;
import beer.hoppyhour.api.payload.response.MessageResponse;
import beer.hoppyhour.api.payload.response.UserInfoResponse;
import beer.hoppyhour.api.payload.response.UserPublicInfoResponse;
import beer.hoppyhour.api.payload.response.UserScheduleEventResponse;
import beer.hoppyhour.api.service.BrewedService;
import beer.hoppyhour.api.service.RecipeService;
import beer.hoppyhour.api.service.ToBrewService;
import beer.hoppyhour.api.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    BrewedService brewedService;

    @Autowired
    ToBrewService toBrewService;

    //allows a logged in user to get their own detailed info
    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> getPrivateInfo(@PathVariable Long id, Authentication authentication) {
        
            try {
                User user = userService.getUser(id);
                Set<String> strRoles = authentication.getAuthorities().stream().map(r -> r.toString()).collect(Collectors.toSet());
                return ResponseEntity.ok().body(
                    new UserInfoResponse(
                        user.getId(), 
                        user.getUsername(), 
                        user.getEmail(), 
                        user.getCreatedDate(), 
                        user.getUpdatedDate(), 
                        strRoles)
                    );
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
            }
    }

    //allows a user to patch their own email
    @PatchMapping("/{id}/email")
    @PreAuthorize("#id == authentication.principal.id") 
    public ResponseEntity<?> patchEmail(@PathVariable Long id, @Valid @RequestBody EmailPatchRequest emailPatchRequest) {
        try {
            User user = userService.getUser(id);
            user.setEmail(encoder.encode(emailPatchRequest.getEmail()));
            userService.saveUser(user);
            return ResponseEntity.ok().body(
                new MessageResponse(
                    //first send a verification email before updating their email in the database
                    "Your email has been updated."
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //allows anyone to get a user's public info
    @GetMapping("/{id}/public")
    public ResponseEntity<?> getPublicInfo(@PathVariable Long id) {
        try {
            User user = userService.getUser(id);
            return ResponseEntity.ok().body(
                new UserPublicInfoResponse(
                    user.getUsername(), 
                    user.getCreatedDate())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //allows a user to get their own breweds
    @GetMapping("/{id}/breweds")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> getBreweds(@PathVariable Long id) {
        try {
            //get the user
            User user = userService.getUser(id);
            //get all breweds for user and return a list of basic recipe info
            List<Brewed> breweds = brewedService.getEventsByUser(user);
            //send response
            return ResponseEntity.ok().body(
                new UserScheduleEventResponse<Brewed>(
                    breweds
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error! " + e.getMessage());
        }
    }

    //allows a user to get their own tobrews
    @GetMapping("/{id}/tobrews")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> getToBrews(@PathVariable Long id) {
        try {
            //get the user
            User user = userService.getUser(id);
            //get all breweds for user and return a list of basic recipe info
            List<ToBrew> toBrews = toBrewService.getEventsByUser(user);
            //send response
            return ResponseEntity.ok().body(
                new UserScheduleEventResponse<ToBrew>(
                    toBrews
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error! " + e.getMessage());
        }
    }

    //allows a user to add a toBrew
    @PostMapping("/{id}/tobrews")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> saveNewToBrew(@PathVariable Long id, @RequestBody UserScheduleEventSaveRequest request) {
        try {
            //convert request string to Date object
            Date date = new Date(request.getWhenToBrewInMs());
            //make a new brewed object
            ToBrew toBrew = new ToBrew(date);
            //add to user
            User user = userService.getUser(id);
            user.addToBrew(toBrew);
            //add to recipe
            Recipe recipe = recipeService.getRecipeById(request.getRecipeId());
            recipe.addToBrew(toBrew);
            //save in brewed repo
            toBrewService.save(toBrew);
            return ResponseEntity.ok().body(
                new MessageResponse(
                    "Saved toBrew successfully."
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error! " + e.getMessage());
        }
    }

    //allows a user to delete a toBrew
    @DeleteMapping("/{id}/tobrews")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> deleteToBrew(@PathVariable Long id, @RequestBody UserScheduleEventDeleteRequest request) {
        try {
            //make sure the client owns the requested item
            if (id == toBrewService.getById(request.getId()).getUser().getId()) {
                //delete
                toBrewService.deleteById(request.getId());
                //send response
                return ResponseEntity.ok().body(
                    new MessageResponse(
                        "Deleted to brew successfully."
                    )
                );
            } else {
                return ResponseEntity.badRequest().body(
                    new MessageResponse("User with id " + id + " cannot delete this item.")
                );
            }
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error! " + e.getMessage());
        }
    }

    //TODO allows a user to get their own schedulings
    //TODO allows a user to get their own brewings
    
    //TODO allows a user to patch their own username

    //TODO allows an admin or a user to delete their own account
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            //delete
            userService.deleteUserById(id);
            //send response
            return ResponseEntity.ok().body(
                new MessageResponse(
                    "The account has been deleted."
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error! " + e.getMessage());
        }
    }

    //TODO allows an admin to patch a user's roles
}
