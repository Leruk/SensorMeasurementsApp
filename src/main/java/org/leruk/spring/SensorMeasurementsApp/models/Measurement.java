package org.leruk.spring.SensorMeasurementsApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @NotNull
    @Range(min = -100, max = 100, message = "Value should be between -100 and 100 degrees")
    private Double value;

    @Column(name = "raining")
    @NotNull
    private Boolean isRaining;

    @NotNull
    @Column(name = "measurement_date_time")
    private LocalDateTime measurementDateTime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private Sensor sensor;

    public Measurement() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return isRaining;
    }

    public void setRaining(Boolean raining) {
        isRaining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getMeasurementDateTime() {
        return measurementDateTime;
    }

    public void setMeasurementDateTime(LocalDateTime measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", value=" + value +
                ", raining=" + isRaining +
                '}';
    }
}
