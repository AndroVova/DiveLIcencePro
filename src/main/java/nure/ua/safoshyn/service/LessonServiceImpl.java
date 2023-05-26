package nure.ua.safoshyn.service;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.*;
import nure.ua.safoshyn.exception.EntityNotFoundException;
import nure.ua.safoshyn.repository.CustomUserRepository;
import nure.ua.safoshyn.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    LessonRepository lessonRepository;
    CustomUserRepository userRepository;

    @Override
    public Lesson getLesson(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return unwrapLesson(lesson, id);
    }

    @Override
    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson addUserAndInstructorToLesson(Long id, Long userId, Long instructorId) {
        Optional<Lesson> l = lessonRepository.findById(id);
        Lesson lesson = unwrapLesson(l, id);
        Optional<CustomUser> user = userRepository.findById(userId);
        CustomUser unwrappedUser = CustomUserServiceImpl.unwrapCustomUser(user, userId);
        Optional<CustomUser> instructor = userRepository.findById(instructorId);
        CustomUser unwrappedInstructor = CustomUserServiceImpl.unwrapCustomUser(instructor, instructorId);

        lesson.setCustomUser(unwrappedUser);
        lesson.setInstructor(unwrappedInstructor);

        return lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> getLessons() {
        return (List<Lesson>)lessonRepository.findAll();
    }

    @Override
    public List<Lesson> getLessonsByUser(Long id) {
        return lessonRepository.findAllByCustomUserId(id);
    }

    @Override
    public List<Lesson> getLessonsByDiveClub(Long id) {
        List<CustomUser> userList = userRepository.findAllByDiveClubId(id);
        List<Lesson> lessonList = new ArrayList<>();
        for (CustomUser l : userList) {
            lessonList.addAll(lessonRepository.findAllByCustomUserId(l.getId()));
        }
        if(lessonList.size() == 0){
            throw new EntityNotFoundException(id, DiveClub.class);
        }
        return lessonList;
    }

    @Override
    public Long countSuccessfulLessonsByUser(Long userId) {
        List<Lesson> lessons = lessonRepository.findAllByCustomUserId(userId);
        return lessons.stream()
                .map(Lesson::getIsSuccessful)
                .count();
    }

    static Lesson unwrapLesson(Optional<Lesson> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Lesson.class);
    }
}
