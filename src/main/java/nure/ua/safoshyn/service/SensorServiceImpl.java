package nure.ua.safoshyn.service;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.Sensor;
import nure.ua.safoshyn.exception.EntityNotFoundException;
import nure.ua.safoshyn.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SensorServiceImpl implements SensorService{
    SensorRepository sensorRepository;
    @Override
    public Sensor getSensor(String id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return unwrapSensor(sensor, id);
    }

    @Override
    public Sensor saveSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public void deleteSensor(String id) {
        sensorRepository.deleteById(id);
    }

    @Override
    public List<Sensor> getSensors() {
        return (List<Sensor>) sensorRepository.findAll();
    }

    public static Sensor unwrapSensor(Optional<Sensor> entity, String sensorId) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(sensorId, Sensor.class);
    }
}