package nure.ua.safoshyn.service;

import nure.ua.safoshyn.entity.Lesson;

import java.util.List;

public interface LessonService {
    Lesson getLesson(Long id);
    Lesson addLesson(Lesson lesson);
    Lesson addUserAndInstructorToLesson(Long id, Long userId, Long instructorId);
    List<Lesson> getLessons();
    List<Lesson> getLessonsByUser(Long id);
    List<Lesson> getLessonsByDiveClub(Long id);
    Long countSuccessfulLessonsByUser(Long userId);
}
