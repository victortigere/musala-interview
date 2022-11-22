package com.musala.drone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.drone.business.model.Medication;
import com.musala.drone.business.service.MedicationService;
import com.musala.drone.payload.BaseResult;
import com.musala.drone.payload.request.DronePayload;
import com.musala.drone.payload.request.MedicationPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/medication/")
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;
    private final ObjectMapper objectMapper;


    @PostMapping("add")
    public ResponseEntity<BaseResult> addDrone(@Valid  @RequestParam("medication") String jsonObject,
                                               @RequestParam("image") MultipartFile[] image) throws IOException {
        MedicationPayload medication = objectMapper.readValue(jsonObject, MedicationPayload.class);
        return medicationService.addMedication(medication, image);
    }

    @GetMapping("get")
    public ResponseEntity<BaseResult> getMedications() {
        return medicationService.getMedications();
    }

    @GetMapping("{code}")
    public ResponseEntity<BaseResult> getMedications(@PathVariable("code") String code) {
        return medicationService.getMedication(code);
    }
}
