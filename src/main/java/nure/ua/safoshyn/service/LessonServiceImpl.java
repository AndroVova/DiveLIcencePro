package nure.ua.safoshyn.service;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.Lesson;
import nure.ua.safoshyn.exception.EntityNotFoundException;
import nure.ua.safoshyn.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {
    LessonRepository lessonRepository;
    @Override
    public Lesson getLesson(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return unwrapLesson(lesson, id);
    }

    @Override
    public Lesson saveLesson(Lesson lesson) {
        return null;
    }

    @Override
    public List<Lesson> getLessons() {
        return null;
    }

    @Override
    public List<Lesson> getLessonsByUser() {
        return null;
    }

    @Override
    public List<Lesson> getLessonsByDiveClub() {
        return null;
    }

    static Lesson unwrapLesson(Optional<Lesson> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Lesson.class);
    }
}
