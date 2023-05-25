package nure.ua.safoshyn.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
    }

    public EntityNotFoundException(String id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
    }
    public EntityNotFoundException(Long id, Class<?> entity1, Class<?> entity2 ) {
        super("In the " + entity1.getSimpleName().toLowerCase() + " with id '" + id +
                "' does not contain any record of " + entity2.getSimpleName().toLowerCase());
    }
    public EntityNotFoundException(String id, Class<?> entity1, Class<?> entity2 ) {
        super("In the " + entity1.getSimpleName().toLowerCase() + " with id '" + id +
                "' does not contain any record of " + entity2.getSimpleName().toLowerCase());
    }
}
