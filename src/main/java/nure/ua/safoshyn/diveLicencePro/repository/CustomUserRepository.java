package nure.ua.safoshyn.diveLicencePro.repository;

import nure.ua.safoshyn.diveLicencePro.entity.CustomUser;
import org.springframework.data.repository.CrudRepository;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {
}
