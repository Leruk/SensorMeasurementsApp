package org.leruk.spring.SensorMeasurementsApp.services;

import org.leruk.spring.SensorMeasurementsApp.models.Measurement;
import org.leruk.spring.SensorMeasurementsApp.repositories.MeasurementRepository;
import org.leruk.spring.SensorMeasurementsApp.utils.MeasurementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public Measurement findOne(int id) {
        Optional<Measurement> measurement = measurementRepository.findById(id);
        return measurement.orElseThrow(MeasurementNotFoundException::new);
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    @Transactional
    public void update(int id, Measurement measurement) {
        enrichMeasurement(measurement);

        measurement.setId(id);
        measurementRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());

        measurement.setMeasurementDateTime(LocalDateTime.now());
    }
}
