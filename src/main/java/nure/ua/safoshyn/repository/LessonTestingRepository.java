package nure.ua.safoshyn.repository;

import nure.ua.safoshyn.entity.LessonTesting;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonTestingRepository extends CrudRepository<LessonTesting, Long> {
    List<LessonTesting> findAllByLessonId(Long id);
}
