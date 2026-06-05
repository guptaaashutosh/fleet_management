package com.guptaaashutosh.fleet_management.repository;

import com.guptaaashutosh.fleet_management.model.Vehicle;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
public class VehicleRepositoryTests {

    @Autowired
    private VehicleRepository vehicleRepository;

    private static Vehicle vehicle;

    @BeforeEach
    void setup(){
        vehicle = Vehicle.builder()
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

    @AfterAll
    static void cleanUp(){
        vehicle = null;

    }

    // JUnit test for save vehicle operation
    // format
//    public void given_when_then(){}
    @DisplayName("JUnit test for saved vehicle operation")
    @Test
    public void givenVehicleObject_whenSave_thenReturnSavedVehicle(){
        // given - precondition or setup
//        Vehicle vehicle = Vehicle.builder()
//                .vehicleNumber("GJ18TX1234")
//                .vehicleType("SUV")
//                .manufacturer("Mahindra")
//                .model("XUV700")
//                .manufacturingYear(2024)
//                .seatingCapacity(7)
//                .fuelType("Diesel")
//                .status("ACTIVE")
//                .build();

        // when - action or the behavior that we are going to test
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // then - verify the output
        Assertions.assertThat(savedVehicle).isNotNull();

    }

    @Test
    public void givenVehicleList_whenFindAll_thenReturnVehiclesList(){
        Vehicle vehicle1 = Vehicle.builder()
                .vehicleNumber("GJ18TX1234")
                .vehicleType("SUV")
                .manufacturer("Mahindra")
                .model("XUV700")
                .manufacturingYear(2024)
                .seatingCapacity(7)
                .fuelType("Diesel")
                .status("ACTIVE")
                .build();

        Vehicle vehicle2 = Vehicle.builder()
                .vehicleNumber("GJ20TX1236")
                .vehicleType("SUV")
                .manufacturer("Mahindra")
                .model("XUV700")
                .manufacturingYear(2024)
                .seatingCapacity(7)
                .fuelType("Diesel")
                .status("ACTIVE")
                .build();

        vehicleRepository.save(vehicle1);
        vehicleRepository.save(vehicle2);

        List<Vehicle> vehicleList = vehicleRepository.findAll();

        Assertions.assertThat(vehicleList).isNotNull();
    }

    @Test
    public void givenVehicleObject_whenFindById_thenReturnVehicle() {

        // Given
//        Vehicle vehicle = Vehicle.builder()
//                .vehicleNumber("GJ18TX1234")
//                .vehicleType("SUV")
//                .manufacturer("Mahindra")
//                .model("XUV700")
//                .manufacturingYear(2024)
//                .seatingCapacity(7)
//                .fuelType("Diesel")
//                .status("ACTIVE")
//                .build();

        vehicleRepository.save(vehicle);

        // When
        Vehicle savedVehicle = vehicleRepository.findById(vehicle.getId())
                .get();

        // Then
        Assertions.assertThat(savedVehicle).isNotNull();
        Assertions.assertThat(savedVehicle.getId())
                .isGreaterThan(0);
    }

    @Test
    public void givenVehicleObject_whenUpdateVehicle_thenReturnUpdatedVehicle() {

        // Given
//        Vehicle vehicle = Vehicle.builder()
//                .vehicleNumber("GJ18TX1234")
//                .vehicleType("SUV")
//                .manufacturer("Mahindra")
//                .model("XUV700")
//                .manufacturingYear(2024)
//                .seatingCapacity(7)
//                .fuelType("Diesel")
//                .status("ACTIVE")
//                .build();

        vehicleRepository.save(vehicle);

        // When
        Vehicle savedVehicle =
                vehicleRepository.findById(vehicle.getId()).get();

        savedVehicle.setModel("Scorpio N");
        savedVehicle.setStatus("IN_SERVICE");

        Vehicle updatedVehicle =
                vehicleRepository.save(savedVehicle);

        // Then
        Assertions.assertThat(updatedVehicle.getModel())
                .isEqualTo("Scorpio N");

        Assertions.assertThat(updatedVehicle.getStatus())
                .isEqualTo("IN_SERVICE");
    }

    @Test
    public void givenVehicleObject_whenDelete_thenRemoveVehicle() {

        // Given
//        Vehicle vehicle = Vehicle.builder()
//                .vehicleNumber("GJ18TX1234")
//                .vehicleType("SUV")
//                .manufacturer("Mahindra")
//                .model("XUV700")
//                .manufacturingYear(2024)
//                .seatingCapacity(7)
//                .fuelType("Diesel")
//                .status("ACTIVE")
//                .build();

        vehicleRepository.save(vehicle);

        // When
        vehicleRepository.deleteById(vehicle.getId());

        // Then
        Optional<Vehicle> deletedVehicle =
                vehicleRepository.findById(vehicle.getId());

        Assertions.assertThat(deletedVehicle)
                .isEmpty();
    }

    @Test
    public void givenVehicleObject_whenExistsById_thenReturnTrue() {

        // Given
//        Vehicle vehicle = Vehicle.builder()
//                .vehicleNumber("GJ18TX1234")
//                .vehicleType("SUV")
//                .manufacturer("Mahindra")
//                .model("XUV700")
//                .manufacturingYear(2024)
//                .seatingCapacity(7)
//                .fuelType("Diesel")
//                .status("ACTIVE")
//                .build();

        vehicleRepository.save(vehicle);

        // When
        boolean exists =
                vehicleRepository.existsById(vehicle.getId());

        // Then
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    public void givenVehicles_whenCount_thenReturnCount() {

        // Given
        Vehicle vehicle1 = Vehicle.builder()
                .vehicleNumber("GJ18TX1234")
                .vehicleType("SUV")
                .manufacturer("Mahindra")
                .model("XUV700")
                .manufacturingYear(2024)
                .seatingCapacity(7)
                .fuelType("Diesel")
                .status("ACTIVE")
                .build();

        Vehicle vehicle2 = Vehicle.builder()
                .vehicleNumber("GJ20TX1236")
                .vehicleType("Sedan")
                .manufacturer("Honda")
                .model("City")
                .manufacturingYear(2023)
                .seatingCapacity(5)
                .fuelType("Petrol")
                .status("ACTIVE")
                .build();

        vehicleRepository.save(vehicle1);
        vehicleRepository.save(vehicle2);

        // When
        long count = vehicleRepository.count();

        // Then
        Assertions.assertThat(count)
                .isGreaterThanOrEqualTo(2);
    }
}
