package org.leruk.spring.SensorMeasurementsApp.utils;

import org.leruk.spring.SensorMeasurementsApp.models.Sensor;
import org.leruk.spring.SensorMeasurementsApp.services.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "A sensor with that name has already been created");
        }

    }
}
