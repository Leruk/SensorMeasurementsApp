package org.leruk.spring.SensorMeasurementsApp.services;

import org.leruk.spring.SensorMeasurementsApp.models.Sensor;
import org.leruk.spring.SensorMeasurementsApp.repositories.SensorRepository;
import org.leruk.spring.SensorMeasurementsApp.utils.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findOne(int id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return sensor.orElseThrow(SensorNotFoundException::new);
    }

    public Optional<Sensor> findByName(String name)
    {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        enrichSensor(sensor);
        sensorRepository.save(sensor);
    }

    @Transactional
    public void update(int id, Sensor sensor) {
        enrichSensor(sensor);

        sensor.setId(id);
        sensorRepository.save(sensor);
    }

    private void enrichSensor(Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
    }
}
