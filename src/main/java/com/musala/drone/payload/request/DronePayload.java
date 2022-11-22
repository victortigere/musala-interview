package com.musala.drone.payload.request;

import com.musala.drone.business.enums.DroneModel;
import com.musala.drone.business.enums.DroneState;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class DronePayload {
    @NotNull(message = "Serial Number cannot be null")
    @NotEmpty(message = "The Serial Number is required.")
    @Size(min = 2, max = 100, message = "The length of Serial Number must be between 2 and 100 characters.")
    private String serialNumber;

    @NotNull(message = "Drone Model is required")
    private DroneModel droneModel;

    @NotNull(message = "Battery Capacity is required")
    private Double batteryCapacity;

    private DroneState droneState;

    @NotNull(message = "Weight is required")
    private Double weight;
}
