package org.leruk.spring.SensorMeasurementsApp.repositories;

import org.leruk.spring.SensorMeasurementsApp.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Sensor findByName(String name);
}
