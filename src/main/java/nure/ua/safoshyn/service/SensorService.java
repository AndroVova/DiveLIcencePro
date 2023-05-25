package nure.ua.safoshyn.service;

import nure.ua.safoshyn.entity.Sensor;

import java.util.List;

public interface SensorService {
    Sensor getSensor(String id);
    Sensor saveSensor(Sensor sensor);
    void deleteSensor(String id);
    List<Sensor> getSensors();
}
