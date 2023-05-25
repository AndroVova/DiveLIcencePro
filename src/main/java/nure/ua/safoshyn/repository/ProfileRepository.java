package nure.ua.safoshyn.repository;

import nure.ua.safoshyn.entity.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Optional<Profile> findByEmail(String email);
}
