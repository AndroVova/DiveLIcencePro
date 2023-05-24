package nure.ua.safoshyn.repository;

import nure.ua.safoshyn.entity.Sensor;
import org.springframework.data.repository.CrudRepository;

public interface SensorRepository extends CrudRepository<Sensor, Long> {
}
