package org.leruk.spring.SensorMeasurementsApp.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.leruk.spring.SensorMeasurementsApp.models.Sensor;

public class MeasurementDTO {
    @NotNull
    @Range(min = -100, max = 100, message = "Value should be between -100 and 100 degrees")
    private Double value;

    @NotNull
    private Boolean isRaining;

    @NotNull
    private Sensor sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
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
}
