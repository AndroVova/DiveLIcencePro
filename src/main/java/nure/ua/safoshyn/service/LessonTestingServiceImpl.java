package nure.ua.safoshyn.service;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.SuccessCriteria;
import nure.ua.safoshyn.emailUtil.EmailUtil;
import nure.ua.safoshyn.emailUtil.CustomMessage;
import nure.ua.safoshyn.entity.*;
import nure.ua.safoshyn.exception.CertificateIsReadyException;
import nure.ua.safoshyn.exception.EntityNotFoundException;
import nure.ua.safoshyn.exception.LessonTestingFailureException;
import nure.ua.safoshyn.repository.*;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    CertificateRepository certificateRepository;

    LessonService lessonService;
    CertificateService certificateService;

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
        List<Lesson> lessonsList = lessonRepository.findAllByCustomUserId(id);
        List<LessonTesting> lessonTestings = new ArrayList<>();
        for (Lesson l : lessonsList) {
            lessonTestings.addAll(lessonTestingRepository.findAllByLessonId(l.getId()));
        }
        return lessonTestings;
    }

    @Override
    public List<LessonTesting> getLessonTestingsBySensor(String id) {
        return lessonTestingRepository.findAllBySensorId(id);
    }

    //TODO: LOGIC
    @Override
    public Long countLessonTestingsWithIndicatesAboveNorm(Long id) {
        return null;
    }

    public static LessonTesting unwrapLessonTesting(Optional<LessonTesting> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, LessonTesting.class);
    }

    private void checkIfLessonWasSuccessful(LessonTesting test, Long lessonId, Sensor sensor, Lesson lesson) throws MessagingException {
        double heartRate = test.getHeartRateValue();
        Long time = test.getTime();

        double maxHeartRate = sensor.getMaxHeartRateValue();
        Long maxTime = sensor.getMaxTime();
        Certificate certificate = new Certificate();
        try {
            if (SuccessCriteria.isTimeCriteriaIsValid(maxTime, time) &&
                    SuccessCriteria.isHeartRateCriteriaIsValid(maxHeartRate, heartRate)) {
                lesson.setIsSuccessful(true);
                Long numberOfsuccessfulLessons = lessonService.countSuccessfulLessonsByUser(lesson.getCustomUser().getId());
                List<Certificate> certificates = certificateService.getCertificatesByUser(lesson.getCustomUser().getId());
                for (Certificate cert : certificates) {
                    if (!cert.getIsCompleted()) {
                        if (numberOfsuccessfulLessons >= cert.getNumberOfSuccessfulLessonsToGet()) {
                            certificate = cert;
                            cert.setIsCompleted(true);
                            certificateRepository.save(cert);
                            test.setLesson(lesson);
                            throw new CertificateIsReadyException(cert.getId(), cert);
                        }
                        break;
                    }
                }

                test.setLesson(lesson);
            } else {
                lesson.setIsSuccessful(false);
                test.setLesson(lesson);
                throw new LessonTestingFailureException(lessonId, lesson);
            }
        } catch (LessonTestingFailureException e) {
            CustomMessage customMessage = createTestFailureMessage(test, lesson);
            EmailUtil.sendMessage(test, customMessage);
        } catch (CertificateIsReadyException e) {
            CustomMessage customMessage = createSuccessMessage(test, lesson, certificate);
            EmailUtil.sendMessage(test, customMessage);
        }
    }

    private CustomMessage createSuccessMessage(LessonTesting test, Lesson lesson, Certificate certificate) {
        LocalDate date = lesson.getDate();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        String title = "Congratulations, you got your certificate";
        String body = "The certificate " + certificate.getName() + " with id: '" + certificate.getId() +
                "' is obtained, by user " + test.getLesson().getCustomUser().getName() + "!";
        return new CustomMessage(title, body);
    }

    private CustomMessage createTestFailureMessage(LessonTesting test, Lesson lesson) {
        LocalDate date = lesson.getDate();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        String title = "Was found an problem through your dive";
        String body = "An Testing failure has occurred for user " + lesson.getCustomUser().getName() +
                " with ID " + lesson.getCustomUser().getId() + " on " + strDate + "\n";
        return new CustomMessage(title, body);
    }

}
