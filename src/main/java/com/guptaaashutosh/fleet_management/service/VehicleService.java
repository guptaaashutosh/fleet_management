package com.guptaaashutosh.fleet_management.service;


import com.guptaaashutosh.fleet_management.exception.ResourceNotFoundException;
import com.guptaaashutosh.fleet_management.exception.VehicleAlreadyExistsException;
import com.guptaaashutosh.fleet_management.model.Vehicle;
import com.guptaaashutosh.fleet_management.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;

    public Vehicle createVehicle(Vehicle vehicle) {
        Optional<Vehicle> savedVehicle = repository.findByVehicleNumber(vehicle.getVehicleNumber());
        if(savedVehicle.isPresent()){
            throw new VehicleAlreadyExistsException("Vehicle already exists with the vehicle number : "+ vehicle.getVehicleNumber());
        }
        return repository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Vehicle not found with id: " + id));
    }

    public Vehicle updateVehicle(Long id, Vehicle vehicle) {

        Vehicle existingVehicle = getVehicleById(id);

        existingVehicle.setVehicleNumber(vehicle.getVehicleNumber());
        existingVehicle.setVehicleType(vehicle.getVehicleType());
        existingVehicle.setManufacturer(vehicle.getManufacturer());
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setManufacturingYear(vehicle.getManufacturingYear());
        existingVehicle.setSeatingCapacity(vehicle.getSeatingCapacity());
        existingVehicle.setFuelType(vehicle.getFuelType());
        existingVehicle.setStatus(vehicle.getStatus());

        return repository.save(existingVehicle);
    }

    public void deleteVehicle(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException(
                    "Vehicle not found with id: " + id);
        }

        repository.deleteById(id);
    }
}