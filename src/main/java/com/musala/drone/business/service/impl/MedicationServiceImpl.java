package com.musala.drone.business.service.impl;

import com.musala.drone.business.model.Medication;
import com.musala.drone.business.repository.MedicationRepository;
import com.musala.drone.business.service.MedicationService;
import com.musala.drone.config.FileStorageProperties;
import com.musala.drone.payload.BaseResult;
import com.musala.drone.payload.request.MedicationPayload;
import com.musala.drone.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final FileStorageProperties fileStorageProperties;

    @Override
    public ResponseEntity<BaseResult> addMedication(MedicationPayload medicationPayload, MultipartFile[] image) throws IOException {
        Optional<Medication> checkMedication = medicationRepository.findMedicationByCode(medicationPayload.getCode());

        if(image[0].isEmpty()){
            return ResponseEntity.ok(new BaseResult(null, "Please upload image", "00", 200));
        }

        if(!isNameValid(medicationPayload.getName())){
            return ResponseEntity.ok(new BaseResult(null, "Name is not valid", "00", 200));
        }

        if(!isCodeValid(medicationPayload.getCode())){
            return ResponseEntity.ok(new BaseResult(null, "Code is not valid", "00", 200));
        }

        if(!isWeightValid(medicationPayload.getWeight())){
            return ResponseEntity.ok(new BaseResult(null, "Weight is not valid", "00", 200));
        }

        if (checkMedication.isPresent()){
            return ResponseEntity.ok(new BaseResult(null, "Medication already exists", "00", 200));
        }

        String medicationImage = "med-" + StringUtils.cleanPath(image[0].getOriginalFilename().replace(
                image[0].getOriginalFilename(), medicationPayload.getCode() + "." +
                        image[0].getOriginalFilename().split("\\.")[1]));

        String uploadImageDir = fileStorageProperties.getUploadDir() + "/medicationImages";

        if(new File(uploadImageDir + "/" + medicationImage).exists()){
            new File(uploadImageDir + "/" + medicationImage).delete();
        }
        FileUploadUtil.saveFile(uploadImageDir, medicationImage, image[0]);

        Medication medication = new Medication();
        medication.setCode(medicationPayload.getCode());
        medication.setName(medicationPayload.getName());
        medication.setWeight(Double.parseDouble(medicationPayload.getWeight()));
        medication.setImage(medicationImage);
        medicationRepository.save(medication);
        return ResponseEntity.ok(new BaseResult(null, "Medication saved successfully", "00", 200));
    }

    @Override
    public ResponseEntity<BaseResult> getMedications() {
        return ResponseEntity.ok(new BaseResult(medicationRepository.findAll(), "Medication fetched successfully", "00", 200));
    }

    @Override
    public ResponseEntity<BaseResult> getMedication(String code) {
        Optional<Medication> checkMedication = medicationRepository.findMedicationByCode(code);

        if (checkMedication.isPresent()){
            return ResponseEntity.ok(new BaseResult(checkMedication.get(), "Medication fetched successfully", "00", 200));
        }
        return ResponseEntity.ok(new BaseResult(null, "Medication does not exists", "00", 200));
    }

    public Double calculateMedicationWeight(String medicationCode, int quantity){

        Optional<Medication> medication = medicationRepository.findMedicationByCode(medicationCode);
        if(medication.isPresent()){
          return  medication.get().getWeight() * quantity;
        }

        return 0.0;
    }

    public Boolean isNameValid(String name){
        return name.matches("^([A-Za-z]|[0-9]|_)+$");
    }

    public Boolean isCodeValid(String code){
        return code.matches("^([A-Z]|[0-9]|_)+$");
    }

    public Boolean isWeightValid(String weight){
        if(weight != null){
            return weight.matches("[0-9]{1,13}(\\.[0-9]*)?");
        }
        return false;
    }



}
