package nure.ua.safoshyn.repository;

import nure.ua.safoshyn.entity.CustomUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {
    List<CustomUser> findAllByDiveClubId(Long id);
}
