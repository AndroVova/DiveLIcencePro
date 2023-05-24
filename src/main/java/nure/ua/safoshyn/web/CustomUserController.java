package nure.ua.safoshyn.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.CustomUser;
import nure.ua.safoshyn.service.CustomUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/custom_user")
public class CustomUserController {

    CustomUserService customUserService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomUser> getCustomUser(@PathVariable Long id) {
        return new ResponseEntity<>(customUserService.getCustomUser(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomUser> saveCustomUser(@Valid @RequestBody CustomUser customUser) {
        customUserService.saveCustomUser(customUser);
        return new ResponseEntity<>(customUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomUser> deleteCustomUser(@PathVariable Long id) {
        customUserService.deleteCustomUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomUser>> getCustomUsers(){
        return new ResponseEntity<>(customUserService.getCustomUsers(), HttpStatus.OK);
    }

    @PutMapping("/{customUserId}/dive_club/{diveClubId}")
    public ResponseEntity<CustomUser> updateDiveClubToCustomUser(@PathVariable Long diveClubId, @PathVariable Long customUserId) {
        return new ResponseEntity<>(customUserService.addCustomUserToNewDiveClub(customUserId, diveClubId), HttpStatus.OK);
    }

}
