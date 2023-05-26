package nure.ua.safoshyn.repository;

import nure.ua.safoshyn.entity.CustomUser;
import nure.ua.safoshyn.entity.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
    List<Lesson> findAllByCustomUserId(Long id);
}
