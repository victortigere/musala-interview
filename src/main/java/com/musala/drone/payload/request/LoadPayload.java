package com.musala.drone.payload.request;

import com.musala.drone.business.model.Drone;
import com.musala.drone.business.model.Medication;
import lombok.Data;

@Data
public class LoadPayload {
    private String serialNumber;
    private String medicationCode;
    private int quantity;
}
