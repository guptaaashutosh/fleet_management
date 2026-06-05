package com.guptaaashutosh.fleet_management.service;


import com.guptaaashutosh.fleet_management.exception.ResourceNotFoundException;
import com.guptaaashutosh.fleet_management.exception.VehicleAlreadyExistsException;
import com.guptaaashutosh.fleet_management.model.Vehicle;
import com.guptaaashutosh.fleet_management.repository.VehicleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTests {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setup(){
        vehicle = Vehicle.builder()
                .id(1l)
                .vehicleNumber("GJ18TX1234")
                .vehicleType("SUV")
                .manufacturer("Mahindra")
                .model("XUV700")
                .manufacturingYear(2024)
                .seatingCapacity(7)
                .fuelType("Diesel")
                .status("ACTIVE")
                .build();

        System.out.println("Before each called");
    }

    @DisplayName("Creating new vehicle test")
    @Test
    public void givenVehicleObject_whenSaveVehicle_thenReturnSavedVehicle(){
       //given
        BDDMockito.given(vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber()))
                .willReturn(Optional.empty());

        BDDMockito.given(vehicleRepository.save(vehicle)).willReturn(vehicle);

        // when : action or behavior that we are going to test
        Vehicle savedVehicle = vehicleService.createVehicle(vehicle);

        // then verify the output
        Assertions.assertThat(savedVehicle).isNotNull();

    }

    @DisplayName("Creating new vehicle with existing vehicle number throws exception")
    @Test
    public void givenExistingVehicleObject_whenSaveVehicle_thenThrowException(){
        BDDMockito.given(vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber()))
                .willReturn(Optional.of(vehicle));

        org.junit.jupiter.api.Assertions.assertThrows(VehicleAlreadyExistsException.class, ()->{
            vehicleService.createVehicle(vehicle);
        });
    }

    @DisplayName("Get all vehicle list")
    @Test
    public void givenVehicleList_whenGetAllVehicle_thenReturnAllVehicleList(){
        //given
        BDDMockito.given(vehicleRepository.findAll()).willReturn(List.of(vehicle));

        // when : action or behavior that we are going to test
        List<Vehicle> listOfVehicle = vehicleService.getAllVehicles();

        // then verify the output
        Assertions.assertThat(listOfVehicle).isNotNull();
        Assertions.assertThat(listOfVehicle.size()).isEqualTo(1);
    }

    @DisplayName("Get vehicle with vehicle number")
    @Test
    public void givenVehicleByNumber_whenGetVehicleByVehicleNumber_thenReturnVehicleWithVehicleNumber(){
        //given
        BDDMockito.given(vehicleRepository.findById(1L)).willReturn(Optional.of(vehicle));

        // when : action or behavior that we are going to test
        Vehicle gotVehicleDetails = vehicleService.getVehicleById(vehicle.getId());

        // then verify the output
        Assertions.assertThat(gotVehicleDetails).isNotNull();
    }

    @DisplayName("Update vehicle test")
    @Test
    public void givenVehicleObject_whenUpdateVehicle_thenReturnUpdatedVehicle(){
        //given
        BDDMockito.given(vehicleRepository.findById(1L)).willReturn(Optional.of(vehicle));
        BDDMockito.given(vehicleRepository.save(vehicle)).willReturn(vehicle);

        vehicle.setVehicleNumber("GJ26TX4444");
        vehicle.setManufacturingYear(2026);

        // when : action or behavior that we are going to test
        Vehicle updatedVehicle = vehicleService.updateVehicle(vehicle.getId(), vehicle);

        // then verify the output
        Assertions.assertThat(updatedVehicle).isNotNull();
        Assertions.assertThat(updatedVehicle.getVehicleNumber()).isEqualTo("GJ26TX4444");

    }

    @DisplayName("Delete vehicle test")
    @Test
    public void givenVehicleId_whenDeleteVehicle_thenNothing(){
        //given
        long vehicleId = 1L;

        BDDMockito.given(vehicleRepository.existsById(vehicleId))
                .willReturn(false);

        // When & Then
        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> vehicleService.deleteVehicle(vehicleId)
        );

        BDDMockito.then(vehicleRepository)
                .should(times(1))
                .existsById(vehicleId);

        BDDMockito.then(vehicleRepository)
                .should(never())
                .deleteById(anyLong());

    }
}
