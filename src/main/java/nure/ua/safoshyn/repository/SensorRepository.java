package nure.ua.safoshyn.repository;

import nure.ua.safoshyn.entity.Sensor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SensorRepository extends CrudRepository<Sensor, Long> {
    Optional<Sensor> findById(String id);
    void deleteById(String id);
}
