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

import beer.hoppyhour.api.doa.RoleRepository;
import beer.hoppyhour.api.entity.Brewed;
import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.entity.Role;
import beer.hoppyhour.api.entity.Scheduling;
import beer.hoppyhour.api.entity.ToBrew;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.payload.request.EmailPatchRequest;
import beer.hoppyhour.api.payload.request.RolesPatchRequest;
import beer.hoppyhour.api.payload.request.ToBrewDatePatchRequest;
import beer.hoppyhour.api.payload.request.UserScheduleEventDeleteRequest;
import beer.hoppyhour.api.payload.request.UserScheduleEventSaveRequest;
import beer.hoppyhour.api.payload.response.MessageResponse;
import beer.hoppyhour.api.payload.response.UserInfoResponse;
import beer.hoppyhour.api.payload.response.UserPublicInfoResponse;
import beer.hoppyhour.api.payload.response.UserScheduleEventResponse;
import beer.hoppyhour.api.service.BrewedService;
import beer.hoppyhour.api.service.RecipeService;
import beer.hoppyhour.api.service.SchedulingService;
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

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SchedulingService schedulingService;

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

    //allows an admin to patch a user's roles
    @PatchMapping("/{id}/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> patchRoles(@PathVariable Long id, @Valid @RequestBody RolesPatchRequest request) {
        if (request.getStrRoles().isEmpty()) {
            return ResponseEntity.badRequest().body(
                new MessageResponse("Make sure to include the roles you wish to add in your request. strRoles = ['admin', 'mod'], for example.")
            );
        }
        
        try {
            User user = userService.getUser(id);
            Set<Role> roles = user.getRoles();
            Set<Role> newRoles = userService.getRolesFromStrings(request.getStrRoles());
            roles.addAll(newRoles);
            user.setRoles(roles);
            userService.saveUser(user);
            return ResponseEntity.ok().body(
                new MessageResponse("The user's roles have been updated.")
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new MessageResponse(e.getMessage())
            );
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
            //get all toBrews for user and return a list of basic recipe info
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
    public ResponseEntity<?> saveNewToBrew(@PathVariable Long id, @Valid @RequestBody UserScheduleEventSaveRequest request) {        
        try {
            //convert request string to Date object
            Date date = new Date(request.getWhenToBrewInMs());
            //make a new toBrew object
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

    //allows a user to patch their toBrew date
    @PatchMapping("/{id}/tobrews")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> patchToBrewDate(@PathVariable Long id, @Valid @RequestBody ToBrewDatePatchRequest request) {
        try {
            //make sure the client owns the requested item
            if (id == toBrewService.getById(request.getId()).getUser().getId()) {
                //update
                toBrewService.update(request.getId(), request.getWhenToBrewInMs());
                //send response
                return ResponseEntity.ok().body(
                    new MessageResponse(
                        "Updated toBrew date successfully."
                    )
                );
            } else {
                return ResponseEntity.badRequest().body(
                    new MessageResponse("User with id " + id + " cannot update this item.")
                );
            }
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error! " + e.getMessage());
        }
    }

    //allows a user to delete a toBrew
    @DeleteMapping("/{id}/tobrews")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> deleteToBrew(@PathVariable Long id, @Valid @RequestBody UserScheduleEventDeleteRequest request) {
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

    //allows a user to get their own schedulings
    @GetMapping("/{id}/schedulings")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> getSchedulings(@PathVariable Long id) {
        try {
            //get the user
            User user = userService.getUser(id);
            //get all schedulings for user and return a list of basic recipe info
            List<Scheduling> schedulings = schedulingService.getEventsByUser(user);
            //send response
            return ResponseEntity.ok().body(
                new UserScheduleEventResponse<Scheduling>(
                    schedulings
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error! " + e.getMessage());
        }
    }

    //allows a user to add a new scheduling
    @PostMapping("/{id}/schedulings")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> saveNewScheduling(@PathVariable Long id, @Valid @RequestBody UserScheduleEventSaveRequest request) {
        try {
            //make a new scheduling object
            Scheduling scheduling = new Scheduling();
            //add to user
            User user = userService.getUser(id);
            user.addScheduling(scheduling);
            //add to recipe
            Recipe recipe = recipeService.getRecipeById(request.getRecipeId());
            recipe.addScheduling(scheduling);
            //save in scheduling repo
            schedulingService.save(scheduling);
            return ResponseEntity.ok().body(
                new MessageResponse(
                    "Saved scheduling successfully."
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error! " + e.getMessage());
        }
    }

    //allows a user to delete their scheduling
    @DeleteMapping("/{id}/schedulings")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> deleteScheduling(@PathVariable Long id, @Valid @RequestBody UserScheduleEventDeleteRequest request) {
        try {
            //make sure the client owns the requested item
            if (id == schedulingService.getById(request.getId()).getUser().getId()) {
                //delete
                schedulingService.deleteById(request.getId());
                //send response
                return ResponseEntity.ok().body(
                    new MessageResponse(
                        "Deleted to scheduling successfully."
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

    //TODO allows a user to get their own brewings
    //TODO allows a user to add a brewing
    //TODO allows a user to delete a brewing
    
    //TODO allows a user to patch their own username
    //TODO allows a user to patch their own password

    //allows an admin or a user to delete their own account
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ROLE_ADMIN')")
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
}
