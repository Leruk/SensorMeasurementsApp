package org.leruk.spring.SensorMeasurementsApp.utils;

public class SensorNotUpdatedException extends RuntimeException {
    public SensorNotUpdatedException(String msg) {
        super(msg);
    }
}
