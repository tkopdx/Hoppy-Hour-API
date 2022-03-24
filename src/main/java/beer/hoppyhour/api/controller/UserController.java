package beer.hoppyhour.api.controller;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import beer.hoppyhour.api.doa.UserRepository;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.payload.request.EmailPatchRequest;
import beer.hoppyhour.api.payload.response.MessageResponse;
import beer.hoppyhour.api.payload.response.UserInfoResponse;
import beer.hoppyhour.api.payload.response.UserPublicInfoResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    PasswordEncoder encoder;

    //allows a logged in user to get their own detailed info
    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> getPrivateInfo(@PathVariable Long id, Authentication authentication) {
        System.out.println("Getting a user with id " + id);
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No user found with id " + id + "."));
        } else {
            try {
                Set<String> strRoles = authentication.getAuthorities().stream().map(r -> r.toString()).collect(Collectors.toSet());

                return ResponseEntity.ok().body(
                    new UserInfoResponse(
                        user.get().getId(), 
                        user.get().getUsername(), 
                        user.get().getEmail(), 
                        user.get().getCreatedDate(), 
                        user.get().getupdatedDate(), 
                        strRoles)
                    );
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
            }
        }
    }

    //allows a user to patch their own email
    @PatchMapping("/{id}/email")
    @PreAuthorize("#id == authentication.principal.id") 
    public ResponseEntity<?> patchEmail(@PathVariable Long id, @Valid @RequestBody EmailPatchRequest emailPatchRequest) {
        System.out.println("Getting a user with id " + id);
        Optional<User> possibleUser = repository.findById(id);
        if (possibleUser.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No user found with id " + id + "."));
        } else {
            //get the user
            User user = possibleUser.get();
            user.setEmail(encoder.encode(emailPatchRequest.getEmail()));
            repository.save(user);
            return ResponseEntity.ok().body(
                new MessageResponse(
                    //first send a verification email before updating their email in the database
                    "Your email has been updated."
                )
            );
        }
    }

    //allows anyone to get a user's public info
    @GetMapping("/{id}/public")
    public ResponseEntity<?> getPublicInfo(@PathVariable Long id) {
        System.out.println("Getting a user with id " + id);
        Optional<User> user = repository.findById(id);

        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No user found with id " + id));
        } else {
            return ResponseEntity.ok().body(
                new UserPublicInfoResponse(
                    user.get().getUsername(), 
                    user.get().getCreatedDate())
            );
        }
    }

    //TODO allows a user to get their own breweds
    //TODO allows a user to get their own tobrews
    //TODO allows a user to get their own schedulings
    //TODO allows a user to get their own brewings
    
    //TODO allows a user to patch their own username

    //TODO allows an admin or a user to delete their own account

    //TODO allows an admin to patch a user's roles
}
