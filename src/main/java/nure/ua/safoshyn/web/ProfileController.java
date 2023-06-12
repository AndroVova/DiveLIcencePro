package nure.ua.safoshyn.web;

import jakarta.annotation.security.RolesAllowed;
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
    @GetMapping("/admin/{id}")
    //@RolesAllowed("ADMIN")
    public ResponseEntity<String> findById(@PathVariable Long id) {
        return new ResponseEntity<>(profileService.getProfile(id).getEmail(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody Profile profile) {
        profileService.saveProfile(profile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/register/ad")
    public ResponseEntity<HttpStatus> createAdmin(@Valid @RequestBody Profile profile) {
        try{
            profileService.saveAdminProfile(profile);
        } catch (Exception e){
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(e.getMessage());
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
