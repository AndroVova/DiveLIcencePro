package nure.ua.safoshyn.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.Profile;
import nure.ua.safoshyn.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    ProfileService profileService;
    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id) {
        return new ResponseEntity<>(profileService.getProfile(id).getEmail(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody Profile profile) {
        profileService.saveProfile(profile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
