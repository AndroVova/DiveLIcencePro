package nure.ua.safoshyn.repository;

import nure.ua.safoshyn.entity.CustomUser;
import org.springframework.data.repository.CrudRepository;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {
}
