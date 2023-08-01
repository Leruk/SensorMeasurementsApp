package org.leruk.spring.SensorMeasurementsApp.controllers;

import jakarta.validation.Valid;
import org.leruk.spring.SensorMeasurementsApp.dto.SensorDTO;
import org.leruk.spring.SensorMeasurementsApp.models.Sensor;
import org.leruk.spring.SensorMeasurementsApp.services.SensorService;
import org.leruk.spring.SensorMeasurementsApp.utils.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public List<SensorDTO> getAll() {
        return sensorService.findAll()
                .stream().map(this::convertToSensorDTO).toList();
    }

    @GetMapping("/{id}")
    public SensorDTO getOne(@PathVariable("id") int id)
    {
        return convertToSensorDTO(sensorService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                stringBuilder.append(error.getField())
                        .append(" — ").append(error.getDefaultMessage());
            }

            throw new SensorNotCreatedException(stringBuilder.toString());
        }

        sensorService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult){
        sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                stringBuilder.append(error.getField())
                        .append(" — ").append(error.getDefaultMessage());
            }

            throw new SensorNotUpdatedException(stringBuilder.toString());
        }

        sensorService.update(id, convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor with this id wasn't found!",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
