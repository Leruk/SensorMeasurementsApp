package org.leruk.spring.SensorMeasurementsApp.repositories;

import org.leruk.spring.SensorMeasurementsApp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
