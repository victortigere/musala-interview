package com.musala.drone.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "medication")
public class Medication extends BaseEntity{
    private String name;
    private Double weight;
    private String code;
    private String image;
}
