package com.musala.drone.business.repository;

import com.musala.drone.business.model.Drone;
import com.musala.drone.business.model.DroneLoad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoadRepository extends JpaRepository<DroneLoad, Long> {
    Optional<DroneLoad> findDroneLoadByDrone(Drone drone);
}
