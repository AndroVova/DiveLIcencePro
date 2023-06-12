package nure.ua.safoshyn.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.Certificate;
import nure.ua.safoshyn.entity.CustomUser;
import nure.ua.safoshyn.entity.DiveClub;
import nure.ua.safoshyn.repository.DiveClubRepository;
import nure.ua.safoshyn.service.DiveClubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/dive_club")
public class DiveClubController {

    DiveClubService diveClubService;
    //TODO:RENAME

    @GetMapping("/{id}")
    public ResponseEntity<DiveClub> getDepartment(@PathVariable Long id) {
        return new ResponseEntity<>(diveClubService.getDiveClub(id), HttpStatus.OK);
    }
    @PostMapping("/admin")
    public ResponseEntity<DiveClub> saveDepartment(@Valid @RequestBody DiveClub department) {
        return new ResponseEntity<>(diveClubService.saveDiveClub(department), HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable Long id) {
        diveClubService.deleteDiveClub(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/admin/all")
    public ResponseEntity<List<DiveClub>> getDepartments() {
        return new ResponseEntity<>(diveClubService.getDiveClubs(), HttpStatus.OK);
    }

    @GetMapping("/admin/{id}/users")
    public ResponseEntity<List<CustomUser>> getEnrolledEmployees(@PathVariable Long id) {
        return new ResponseEntity<>(diveClubService.getUsersInDiveClub(id), HttpStatus.OK);
    }
    @GetMapping("/admin/{id}/certificates")
    public ResponseEntity<List<Certificate>> getCertificatesGivenByDiveClub(@PathVariable Long id) {
        return new ResponseEntity<>(diveClubService.getCertificatesGivenByDiveClub(id), HttpStatus.OK);
    }
}
