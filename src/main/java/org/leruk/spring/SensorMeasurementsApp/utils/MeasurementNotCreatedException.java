package org.leruk.spring.SensorMeasurementsApp.utils;

public class MeasurementNotCreatedException extends RuntimeException {
    public MeasurementNotCreatedException(String msg) {
        super(msg);
    }
}
