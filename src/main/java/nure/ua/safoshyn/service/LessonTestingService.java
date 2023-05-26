package nure.ua.safoshyn.service;

import jakarta.persistence.EntityManager;
import nure.ua.safoshyn.entity.LessonTesting;

import javax.mail.MessagingException;
import java.util.List;

public interface LessonTestingService {
    LessonTesting getLessonTesting(Long id);
    LessonTesting saveLessonTesting(LessonTesting test, String sensor_id, Long lessonId) throws MessagingException;
    void deleteLessonTesting(Long id);
    List<LessonTesting> getLessonTestings();
    List<LessonTesting> getLessonTestingsByLesson(Long id);
    List<LessonTesting> getLessonTestingsByUser(Long id);
    List<LessonTesting> getLessonTestingsBySensor(String id);
    Long countLessonTestingsWithIndicatesAboveNorm(/*, String id*/);
    //Long countAlcoTestingsWithValueGreaterThanMaxValue(EntityManager entityManager/*, String id*/);
    //List<AlcoTesting> findAlcoTestingsWithValueGreaterThanMaxValue(EntityManager entityManager);
}
