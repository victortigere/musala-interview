package com.musala.drone.business.model;


import com.musala.drone.business.enums.DroneModel;
import com.musala.drone.business.enums.DroneState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "drone")
public class Drone extends BaseEntity {
    private String serialNumber;
    private DroneModel droneModel;
    private Double batterCapacity;
    private DroneState droneState;
    private Double weight;
}
