package nure.ua.safoshyn.exception;

import nure.ua.safoshyn.entity.Lesson;

public class LessonTestingFailureException extends RuntimeException{
    public LessonTestingFailureException(Long id, Lesson lesson){
        super("The user " + lesson.getName() + " with id '" + id + "' got some health problems");
    }
}
