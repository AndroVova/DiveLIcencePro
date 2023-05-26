package nure.ua.safoshyn.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.LessonTesting;
import nure.ua.safoshyn.service.LessonTestingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/lesson_testing")
public class LessonTestingController {

    LessonTestingService lessonTestingService;

    @GetMapping("/{id}")
    public ResponseEntity<LessonTesting> getLessonTesting(@PathVariable Long id) {
        return new ResponseEntity<>(lessonTestingService.getLessonTesting(id), HttpStatus.OK);
    }
    @PostMapping("/sensor/{sensor_id}/lesson/{lesson_id}")
    public ResponseEntity<LessonTesting> saveLessonTesting(@RequestBody LessonTesting test, @PathVariable String sensor_id, @PathVariable Long lesson_id) throws MessagingException {
        var lt = lessonTestingService.saveLessonTesting(test, sensor_id, lesson_id);
        return new ResponseEntity<>(lt, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<LessonTesting> deleteLessonTesting(@PathVariable Long id) {
        lessonTestingService.deleteLessonTesting(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LessonTesting>> getLessonTestings() {
        return new ResponseEntity<>(lessonTestingService.getLessonTestings(), HttpStatus.OK);
    }

    @GetMapping("/all/lesson/{id}")
    public ResponseEntity<List<LessonTesting>> getLessonTestingsByLesson(@PathVariable Long id) {
        return new ResponseEntity<>(lessonTestingService.getLessonTestingsByLesson(id), HttpStatus.OK);
    }
    @GetMapping("/all/user/{id}")
    public ResponseEntity<List<LessonTesting>> getAlcoTestingsByUser(@PathVariable Long id) {
        return new ResponseEntity<>(lessonTestingService.getLessonTestingsByUser(id), HttpStatus.OK);
    }
}
