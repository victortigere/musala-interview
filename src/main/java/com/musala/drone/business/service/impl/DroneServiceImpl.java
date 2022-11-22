package com.musala.drone.business.service.impl;

import com.musala.drone.business.enums.DroneState;
import com.musala.drone.business.model.Drone;
import com.musala.drone.business.model.DroneLoad;
import com.musala.drone.business.model.Medication;
import com.musala.drone.business.repository.DroneRepository;
import com.musala.drone.business.repository.LoadRepository;
import com.musala.drone.business.repository.MedicationRepository;
import com.musala.drone.business.service.DroneService;
import com.musala.drone.business.service.MedicationService;
import com.musala.drone.payload.BaseResult;
import com.musala.drone.payload.request.DronePayload;
import com.musala.drone.payload.request.LoadPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final MedicationService medicationService;
    private final MedicationRepository medicationRepository;
    private final LoadRepository loadRepository;

    @Override
    public ResponseEntity<BaseResult> getDrones() {
        return ResponseEntity.ok(new BaseResult(droneRepository.findAll(), "", "", 200));
    }

    @Override
    public ResponseEntity<BaseResult> addDrone(DronePayload dronePayload) {

        Drone drone = new Drone();
        Optional<Drone> checkDrone = droneRepository.findDroneBySerialNumber(dronePayload.getSerialNumber());

        if (checkDrone.isPresent()) {
            return ResponseEntity.ok(new BaseResult(null, "Drone already exists", "00", 200));
        }

        System.out.println(3000 > 500);
        if (dronePayload.getWeight() > 500 == true) {
            return ResponseEntity.ok(new BaseResult(null, "Drone weight maximum limit is 500g", "00", 200));
        }

        drone.setSerialNumber(dronePayload.getSerialNumber());
        drone.setDroneModel(dronePayload.getDroneModel());
        drone.setBatterCapacity(dronePayload.getBatteryCapacity());
        drone.setDroneState(dronePayload.getDroneState());
        drone.setWeight(dronePayload.getWeight());
        droneRepository.save(drone);
        return ResponseEntity.ok(new BaseResult(null, "Drone added successfully", "00", 200));
    }

    @Override
    public ResponseEntity<BaseResult> getDrone(String serialNumber) {
        Optional<Drone> checkDrone = droneRepository.findDroneBySerialNumber(serialNumber);

        if (checkDrone.isPresent()) {
            return ResponseEntity.ok(new BaseResult(checkDrone.get(), "Drone fetched successfully", "00", 200));
        }
        return ResponseEntity.ok(new BaseResult(null, "Drone does not exist successfully", "00", 200));
    }

    @Override
    public ResponseEntity<BaseResult> deleteDrone(String serialNumber) {
        Optional<Drone> checkDrone = droneRepository.findDroneBySerialNumber(serialNumber);
        if (checkDrone.isPresent()) {
            droneRepository.delete(checkDrone.get());
            return ResponseEntity.ok(new BaseResult(null, "Drone deleted successfully", "00", 200));
        }
        return ResponseEntity.ok(new BaseResult(null, "Drone does not exist", "00", 200));
    }

    @Override
    public ResponseEntity<BaseResult> editDrone(DronePayload dronePayload) {
        Optional<Drone> checkDrone = droneRepository.findDroneBySerialNumber(dronePayload.getSerialNumber());
        if (checkDrone.isPresent()) {
            Drone updatedDrone = checkDrone.get();
            updatedDrone.setSerialNumber(dronePayload.getSerialNumber());
            updatedDrone.setDroneModel(dronePayload.getDroneModel());
            updatedDrone.setDroneState(dronePayload.getDroneState());
            updatedDrone.setBatterCapacity(dronePayload.getBatteryCapacity());
            updatedDrone.setWeight(dronePayload.getWeight());
            droneRepository.save(updatedDrone);
            return ResponseEntity.ok(new BaseResult(null, "Drone updated successfully", "00", 200));
        }
        return ResponseEntity.ok(new BaseResult(null, "Drone does not exist", "00", 200));
    }

    @Override
    public ResponseEntity<BaseResult> loadDrone(LoadPayload load) {

        Optional<Drone> drone = droneRepository.findDroneBySerialNumber(load.getSerialNumber());
        if (!drone.isPresent()) {
            return ResponseEntity.ok(new BaseResult(null, "Drone not available", "00", 200));
        }


        if (checkDroneStatus(load.getSerialNumber()) != DroneState.IDLE){
            return ResponseEntity.ok(new BaseResult(null, "Drone already loaded", "00", 200));
        }

        if (checkDroneWeight(load.getSerialNumber()) <= medicationService.calculateMedicationWeight(
                load.getMedicationCode(), load.getQuantity())) {
            return ResponseEntity.ok(new BaseResult(null, "Drone cannot load overweight Medication", "00", 200));
        }

        System.out.println(load.getMedicationCode());
        Optional<Medication> medication = medicationRepository.findMedicationByCode(load.getMedicationCode());
        System.out.println(medication.get().getWeight());
        if (!medication.isPresent()) {
            return ResponseEntity.ok(new BaseResult(null, "Medication not available", "00", 200));
        }

        DroneLoad loadedDrone = new DroneLoad();
        loadedDrone.setDrone(drone.get());
        loadedDrone.setMedication(medication.get());
        loadedDrone.setQuantity(load.getQuantity());
        loadRepository.save(loadedDrone);

        drone.get().setDroneState(DroneState.LOADED);
        droneRepository.save(drone.get());
        return ResponseEntity.ok(new BaseResult(null, "Drone loaded successfully", "00", 200));

    }

    @Override
    public ResponseEntity<BaseResult> checkDroneLoadStatus(String code) {
        return ResponseEntity.ok(new BaseResult(checkDroneStatus(code), "Drone loaded successfully", "00", 200));
    }

    @Override
    public ResponseEntity<BaseResult> checkDroneBatteryStatus(String serialNumber) {
        Optional<Drone> drone = droneRepository.findDroneBySerialNumber(serialNumber);
        if (drone.isPresent()) {
            return ResponseEntity.ok(new BaseResult(drone.get().getBatterCapacity(), "Drone battery status loaded successfully", "00", 200));
        }
        return ResponseEntity.ok(new BaseResult(null, "Cannot check battery of unpresent drone", "00", 200));
    }

    @Override
    public ResponseEntity<BaseResult> checkDroneLoadedMedications(String serialNumber) {

        Optional<Drone> drone = droneRepository.findDroneBySerialNumber(serialNumber);
        if (!drone.isPresent()) {
            return ResponseEntity.ok(new BaseResult(drone.get().getBatterCapacity(), "No drone with that serial number is available", "00", 200));
        }

        Optional<DroneLoad> load = loadRepository.findDroneLoadByDrone(drone.get());
        return ResponseEntity.ok(new BaseResult(load, "Medication loaded for the drone", "00", 200));
    }

    @Override
    public ResponseEntity<BaseResult> checkAvailableDrones() {
        List<Drone> droneList = droneRepository.findAll();

        return ResponseEntity.ok(new BaseResult(droneList.stream().filter(
                x -> !x.getDroneState().equals(DroneState.LOADED) &&
                        !x.getDroneState().equals(DroneState.LOADING) &&
                        !x.getDroneState().equals(DroneState.DELIVERING)), "Available drones for loading ", "00", 200));
    }

    private DroneState checkDroneStatus(String serialNumber) {
        System.out.println(serialNumber);
        Optional<Drone> drone = droneRepository.findDroneBySerialNumber(serialNumber);
        if (drone.isPresent()) {
            return drone.get().getDroneState();
        }
            return DroneState.NOT_AVAILABLE;
    }

    private Double checkDroneWeight(String serialNumber) {
        Optional<Drone> drone = droneRepository.findDroneBySerialNumber(serialNumber);
        if (!drone.isPresent()) {
          return 0.0;
        }
        return drone.get().getWeight();
    }

}
