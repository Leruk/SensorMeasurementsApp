package org.leruk.spring.SensorMeasurementsApp.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {
    public static void returnErrorsToClient(BindingResult bindingResult)
    {
        StringBuilder stringBuilder = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            stringBuilder.append(error.getField())
                    .append(" — ").append(error.getDefaultMessage());
        }

        throw new MeasurementNotCreatedException(stringBuilder.toString());
    }
}
