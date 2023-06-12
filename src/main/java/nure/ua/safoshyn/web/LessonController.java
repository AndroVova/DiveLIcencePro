package nure.ua.safoshyn.web;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.DiveClub;
import nure.ua.safoshyn.entity.Lesson;
import nure.ua.safoshyn.entity.Sensor;
import nure.ua.safoshyn.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/lesson")
public class LessonController {

    LessonService lessonService;

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable Long id) {
        return new ResponseEntity<>(lessonService.getLesson(id), HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<Lesson> saveLesson(@RequestBody Lesson lesson) {
        Lesson l = lessonService.addLesson(lesson/*, user_id, instructor_id*/);
        return new ResponseEntity<>(l, HttpStatus.CREATED);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<Lesson> updateLesson(@RequestBody Lesson lesson, @PathVariable Long id) {
        Lesson l = lessonService.updateLesson(lesson, id);
        return new ResponseEntity<>(l, HttpStatus.CREATED);
    }

    @PutMapping("/admin/{id}/user/{user_id}/instructor/{instructor_id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @PathVariable Long user_id, @PathVariable Long instructor_id) {
        Lesson l = lessonService.addUserAndInstructorToLesson(id, user_id, instructor_id);
        return new ResponseEntity<>(l, HttpStatus.CREATED);
    }

    @GetMapping("/count_successful_lessons/{id}")
    public ResponseEntity<Long> getCountSuccessfulLessonsByUser(@PathVariable Long id) {
        Long l = lessonService.countSuccessfulLessonsByUser(id);
        return new ResponseEntity<>(l, HttpStatus.CREATED);
    }
    @GetMapping("/admin/all")
    public ResponseEntity<List<Lesson>> getLessons() {
        return new ResponseEntity<>(lessonService.getLessons(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Lesson>> getLessonsByUser(@PathVariable Long id) {
        return new ResponseEntity<>(lessonService.getLessonsByUser(id), HttpStatus.OK);
    }
    @GetMapping("/admin/dive_club/{id}")
    public ResponseEntity<List<Lesson>> getLessonsByDiveClub(@PathVariable Long id) {
        return new ResponseEntity<>(lessonService.getLessonsByDiveClub(id), HttpStatus.OK);
    }
    @Transactional
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Lesson> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
