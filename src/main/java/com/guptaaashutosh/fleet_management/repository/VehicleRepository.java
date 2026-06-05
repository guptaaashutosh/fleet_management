package com.guptaaashutosh.fleet_management.repository;
import com.guptaaashutosh.fleet_management.model.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByVehicleNumber(String vehicleNumber);

}

/*
Spring automatically provides:

save()
findById()
findAll()
deleteById()
existsById()
count()
 */