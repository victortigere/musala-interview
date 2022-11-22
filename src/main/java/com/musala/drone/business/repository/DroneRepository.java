package com.musala.drone.business.repository;

import com.musala.drone.business.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    Optional<Drone> findDroneBySerialNumber(String serialNumber);
}
