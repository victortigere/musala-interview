package com.musala.drone.business.service;

import com.musala.drone.business.model.Medication;
import com.musala.drone.payload.BaseResult;
import com.musala.drone.payload.request.MedicationPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MedicationService {
    public ResponseEntity<BaseResult> addMedication(MedicationPayload medication, MultipartFile[] image) throws IOException;
    public ResponseEntity<BaseResult> getMedications();
    public ResponseEntity<BaseResult> getMedication(String serialNumber);
    public Double calculateMedicationWeight(String medicationCode, int quantity);
}
