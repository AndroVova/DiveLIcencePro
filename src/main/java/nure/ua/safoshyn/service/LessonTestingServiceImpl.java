package nure.ua.safoshyn.service;

import lombok.AllArgsConstructor;
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
        test.setLesson(unwrappedLesson);
        checkIfLessonWasSuccessful(test, lessonId, unwrappedLesson);


        lessonRepository.save(unwrappedLesson);

        return lessonTestingRepository.save(test);
    }

    private void checkIfLessonWasSuccessful(LessonTesting test, Long lessonId, Lesson lesson) throws MessagingException {
        Certificate certificate = new Certificate();
        try {
            if (lesson.getIsSuccessful()){
                certificate = obtainCertificateIfItIsReady(lesson, test);
                if(certificate != null){
                    throw new CertificateIsReadyException(certificate.getId(),certificate);
                }

            } else {
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



    private Certificate obtainCertificateIfItIsReady(Lesson lesson, LessonTesting test){
        Long numberOfSuccessfulLessons = lessonService.countSuccessfulLessonsByUser(lesson.getCustomUser().getId());
        List<Certificate> certificates = certificateService.getCertificatesByUser(lesson.getCustomUser().getId());
        for (Certificate cert : certificates) {
            if (!cert.getIsCompleted()) {
                if (numberOfSuccessfulLessons >= cert.getNumberOfSuccessfulLessonsToGet()) {
                    cert.setIsCompleted(true);
                    certificateRepository.save(cert);
                    test.setLesson(lesson);
                    return cert;
                }
            }
        }
        return null;
    }

    private CustomMessage createSuccessMessage(LessonTesting test, Lesson lesson, Certificate certificate) {
        LocalDate date = lesson.getDate();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        String title = "Congratulations, you got your certificate";
        String body = "The certificate " + certificate.getName() + " with id: '" + certificate.getId() +
                "' is obtained on " + strDate + ", by user " + test.getLesson().getCustomUser().getName() + "!";
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
