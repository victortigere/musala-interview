package com.musala.drone.controller;

import com.musala.drone.business.service.DroneService;
import com.musala.drone.payload.BaseResult;
import com.musala.drone.payload.request.DronePayload;
import com.musala.drone.payload.request.LoadPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/drone/")
public class DroneController {

    private final DroneService droneService;

    @GetMapping("get")
    public ResponseEntity<BaseResult> getDrones(){
        return droneService.getDrones();
    }

    @PostMapping("add")
    public ResponseEntity<BaseResult> addDrone(@Valid @RequestBody  DronePayload dronePayload){
       return droneService.addDrone(dronePayload);
    }

    @GetMapping("{serialNumber}")
    public ResponseEntity<BaseResult> getDrone(@PathVariable String serialNumber){
        return droneService.getDrone(serialNumber);
    }

    @GetMapping("delete/{serialNumber}")
    public ResponseEntity<BaseResult> deleteDrone(@PathVariable String serialNumber){
        return droneService.deleteDrone(serialNumber);
    }

    @PostMapping("edit")
    public ResponseEntity<BaseResult> deleteDrone(@RequestBody  DronePayload dronePayload){
        return droneService.editDrone(dronePayload);
    }

    @GetMapping("/status/{serialNumber}")
    public ResponseEntity<BaseResult> checkDroneStatus(@PathVariable String serialNumber){
        return droneService.checkDroneLoadStatus(serialNumber);
    }

    @PostMapping("load")
    public ResponseEntity<BaseResult> loadDrone(@RequestBody LoadPayload payload){
        return droneService.loadDrone(payload);
    }

    @GetMapping("/battery/status/{serialNumber}")
    public ResponseEntity<BaseResult> checkDroneBatteryStatus(@PathVariable String serialNumber){
        return droneService.checkDroneBatteryStatus(serialNumber);
    }

    @GetMapping("/available/drones")
    public ResponseEntity<BaseResult> checkAvailableDrones(){
        return droneService.checkAvailableDrones();
    }

    @GetMapping("/loaded/medication/{serialNumber}")
    public ResponseEntity<BaseResult> checkDroneLoadedMedications(@PathVariable String serialNumber){
        return droneService.checkDroneLoadedMedications(serialNumber);
    }

}
