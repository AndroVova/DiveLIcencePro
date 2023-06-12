package nure.ua.safoshyn.web;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.Certificate;
import nure.ua.safoshyn.service.CertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/certificate")
public class CertificateController {
    CertificateService certificateService;


    @GetMapping("/{id}")
    public ResponseEntity<Certificate> getCertificate(@PathVariable String id){
        Certificate c = certificateService.getCertificate(id);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
    @PostMapping("/admin")
    public ResponseEntity<Certificate> saveCertificate(@RequestBody Certificate certificate) {
        Certificate c = certificateService.saveCertificate(certificate);
        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }

    @PutMapping("/admin/{id}/user/{customUserId}/instructor/{instructorId}")
    public ResponseEntity<Certificate> updateCertificateStatus(@PathVariable String id,
                                                              @PathVariable Long customUserId,
                                                              @PathVariable Long instructorId) {
        Certificate c = certificateService.addCertificateToUser(id, customUserId, instructorId);
        return new ResponseEntity<>( c, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Certificate> deleteSensor(@PathVariable String id) {
        certificateService.deleteCertificate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Certificate>> getCertificatesByUser(@PathVariable Long id){
        return new ResponseEntity<>(certificateService.getCertificatesByUser(id), HttpStatus.OK);
    }

    @GetMapping("/admin/instructor/{id}")
    public ResponseEntity<List<Certificate>> getCertificatesByInstructor(@PathVariable Long id){
        return new ResponseEntity<>(certificateService.getCertificatesByInstructor(id), HttpStatus.OK);
    }
    @GetMapping("/admin/all")
    public ResponseEntity<List<Certificate>> getCertificates(){
        return new ResponseEntity<>(certificateService.getCertificates(), HttpStatus.OK);
    }
}
