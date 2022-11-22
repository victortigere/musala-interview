package com.musala.drone.business.service;

import com.musala.drone.payload.BaseResult;
import com.musala.drone.payload.request.DronePayload;
import com.musala.drone.payload.request.LoadPayload;
import org.springframework.http.ResponseEntity;

public interface DroneService {
    public ResponseEntity<BaseResult> addDrone(DronePayload dronePayload);
    public ResponseEntity<BaseResult> getDrones();
    public ResponseEntity<BaseResult> getDrone(String code);
    public ResponseEntity<BaseResult> deleteDrone(String code);
    public ResponseEntity<BaseResult> editDrone(DronePayload dronePayload);
    public ResponseEntity<BaseResult> loadDrone(LoadPayload load);
    public ResponseEntity<BaseResult> checkDroneLoadStatus(String code);
    public ResponseEntity<BaseResult> checkDroneBatteryStatus(String code);
    public ResponseEntity<BaseResult> checkAvailableDrones();
    public ResponseEntity<BaseResult> checkDroneLoadedMedications(String code);

}