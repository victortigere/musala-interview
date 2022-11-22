package com.musala.drone.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class DroneLoad extends BaseEntity {
    private int quantity;
    @OneToOne
    private Drone drone;
    @OneToOne
    private Medication medication;
}
