package nure.ua.safoshyn.diveLicencePro.web;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.diveLicencePro.entity.CustomUser;
import nure.ua.safoshyn.diveLicencePro.service.CustomUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/custom_user")
public class CustomUserController {

    CustomUserService customUserService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomUser> getCustomUser(@PathVariable Long id) {
        return new ResponseEntity<>(customUserService.getCustomUser(id), HttpStatus.OK);
    }

}
