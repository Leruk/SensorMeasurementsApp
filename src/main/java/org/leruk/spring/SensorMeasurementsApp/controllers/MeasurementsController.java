package org.leruk.spring.SensorMeasurementsApp.controllers;

import jakarta.validation.Valid;
import org.leruk.spring.SensorMeasurementsApp.dto.MeasurementDTO;
import org.leruk.spring.SensorMeasurementsApp.dto.MeasurementsResponse;
import org.leruk.spring.SensorMeasurementsApp.models.Measurement;
import org.leruk.spring.SensorMeasurementsApp.services.MeasurementService;
import org.leruk.spring.SensorMeasurementsApp.utils.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping
    public MeasurementsResponse getAll() {
        return new MeasurementsResponse(measurementService.findAll()
                .stream().map(this::convertToMeasurementDTO).toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            ErrorsUtil.returnErrorsToClient(bindingResult);
        }

        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementService.findAll().stream().filter(Measurement::isRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotFoundException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Measurement with this id wasn't found!",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
