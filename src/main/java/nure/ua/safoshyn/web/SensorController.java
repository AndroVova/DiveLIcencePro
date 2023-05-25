package nure.ua.safoshyn.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.Sensor;
import nure.ua.safoshyn.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/sensor")
public class SensorController {

    SensorService sensorService;

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensor(@PathVariable String id) {
        return new ResponseEntity<>(sensorService.getSensor(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Sensor> saveSensor(@Valid @RequestBody Sensor sensor) {
        sensorService.saveSensor(sensor);
        return new ResponseEntity<>(sensor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Sensor> deleteSensor(@PathVariable String id) {
        sensorService.deleteSensor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sensor>> getSensor() {
        return new ResponseEntity<>(sensorService.getSensors(), HttpStatus.OK);
    }
}
