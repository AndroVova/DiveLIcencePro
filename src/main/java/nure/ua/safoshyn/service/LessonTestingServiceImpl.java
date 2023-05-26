package nure.ua.safoshyn.service;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.SuccessCriteria;
import nure.ua.safoshyn.emailUtil.EmailUtil;
import nure.ua.safoshyn.entity.CustomUser;
import nure.ua.safoshyn.entity.Lesson;
import nure.ua.safoshyn.entity.LessonTesting;
import nure.ua.safoshyn.entity.Sensor;
import nure.ua.safoshyn.exception.EntityNotFoundException;
import nure.ua.safoshyn.exception.LessonTestingFailureException;
import nure.ua.safoshyn.repository.CustomUserRepository;
import nure.ua.safoshyn.repository.LessonRepository;
import nure.ua.safoshyn.repository.LessonTestingRepository;
import nure.ua.safoshyn.repository.SensorRepository;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LessonTestingServiceImpl implements LessonTestingService {

    LessonTestingRepository lessonTestingRepository;
    SensorRepository sensorRepository;
    LessonRepository lessonRepository;
    CustomUserRepository customUserRepository;

    @Override
    public LessonTesting getLessonTesting(Long id) {
        Optional<LessonTesting> lessonTesting = lessonTestingRepository.findById(id);
        return unwrapLessonTesting(lessonTesting, id);
    }

    @Override
    public LessonTesting saveLessonTesting(LessonTesting test, String sensorId, Long lessonId) throws MessagingException {
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);
        Sensor unwrappedSensor = SensorServiceImpl.unwrapSensor(sensor, sensorId);
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        Lesson unwrappedLesson = LessonServiceImpl.unwrapLesson(lesson, lessonId);

        test.setSensor(unwrappedSensor);
        checkIfLessonWasSuccessful(test, lessonId, unwrappedSensor, unwrappedLesson);

        test.setLesson(unwrappedLesson);
        lessonRepository.save(unwrappedLesson);

        return lessonTestingRepository.save(test);
    }


    @Override
    public void deleteLessonTesting(Long id) {
        lessonTestingRepository.deleteById(id);
    }

    @Override
    public List<LessonTesting> getLessonTestings() {
        return (List<LessonTesting>) lessonTestingRepository.findAll();
    }

    @Override
    public List<LessonTesting> getLessonTestingsByLesson(Long id) {
        return lessonTestingRepository.findAllByLessonId(id);
    }

    @Override
    public List<LessonTesting> getLessonTestingsByUser(Long id) {
        List<CustomUser> userList = lessonRepository.findAllByCustomUserId(id);
        List<LessonTesting> lessonTestings = new ArrayList<>();
        for (CustomUser cu : userList) {
            lessonTestings.addAll(lessonTestingRepository.findAllByLessonId(cu.getId()));
        }
        return lessonTestings;
    }

    //TODO: LOGIC
    @Override
    public Long countLessonTestingsWithIndicatesAboveNorm() {
        return null;
    }

    public static LessonTesting unwrapLessonTesting(Optional<LessonTesting> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, LessonTesting.class);
    }
    private static void checkIfLessonWasSuccessful(LessonTesting test, Long lessonId, Sensor sensor, Lesson lesson) throws MessagingException {
        double heartRate = test.getHeartRateValue();
        Long time = test.getTime();

        double maxHeartRate = sensor.getMaxHeartRateValue();
        Long maxTime = sensor.getMaxTime();

        try{
            if (SuccessCriteria.isTimeCriteriaIsValid(maxTime, time) &&
                    SuccessCriteria.isHeartRateCriteriaIsValid(maxHeartRate, heartRate)) {
                lesson.setIsSuccessful(true);
                test.setLesson(lesson);
            } else{
                lesson.setIsSuccessful(false);
                test.setLesson(lesson);
                throw new LessonTestingFailureException(lessonId, lesson);
            }
        } catch (LessonTestingFailureException e){
            //ToDo:Message?
            EmailUtil.sendMessage(test);
        }
    }

}
