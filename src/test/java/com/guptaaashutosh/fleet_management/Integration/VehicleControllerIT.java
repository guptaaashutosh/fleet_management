package com.guptaaashutosh.fleet_management.Integration;

import com.guptaaashutosh.fleet_management.model.Vehicle;
import com.guptaaashutosh.fleet_management.repository.VehicleRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class VehicleControllerIT {

    @Container
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withUsername("postgres")
            .withPassword("admin")
            .withDatabaseName("fleet_db");

    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehicle vehicle;


    @BeforeEach
    void setup() {
        vehicleRepository.deleteAll();
        vehicle = Vehicle.builder()
                .vehicleNumber("GJ18TX2020")
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


    @DisplayName("Create Vehicle Controller test")
    @Test
    public void givenVehicleObject_whenSaveVehicle_thenReturnSavedVehicle() throws Exception {
        //given vehicle from setup
        // when
        ResultActions response = mockMvc.perform(post("/api/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicle)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicleNumber", CoreMatchers.is(vehicle.getVehicleNumber())));
    }



    @DisplayName("Get All Vehicles Controller Test")
    @Test
    public void givenVehicleList_whenGetAllVehicles_thenReturnVehiclesList() throws Exception {

        Vehicle vehicle2 = Vehicle.builder()
                .vehicleNumber("GJ20TX5678")
                .vehicleType("SUV")
                .manufacturer("Toyota")
                .model("Fortuner")
                .manufacturingYear(2023)
                .seatingCapacity(7)
                .fuelType("Diesel")
                .status("ACTIVE")
                .build();

        vehicleRepository.save(vehicle);
        vehicleRepository.save(vehicle2);

        ResultActions response =
                mockMvc.perform(get("/api/v1/vehicles"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(2));
    }

    @DisplayName("Update Vehicle Controller Test")
    @Test
    public void givenUpdatedVehicle_whenUpdateVehicle_thenReturnUpdatedVehicle() throws Exception {
        // Given
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        savedVehicle.setVehicleNumber("GJ26TX4444");
        savedVehicle.setManufacturingYear(2026);

        // When
        ResultActions response = mockMvc.perform(
                put("/api/v1/vehicles/{id}", savedVehicle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedVehicle)));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleNumber")
                        .value("GJ26TX4444"))
                .andExpect(jsonPath("$.manufacturingYear")
                        .value(2026));

        Vehicle updatedVehicle =
                vehicleRepository.findById(savedVehicle.getId())
                        .orElseThrow();

        Assertions.assertThat(updatedVehicle.getVehicleNumber())
                .isEqualTo("GJ26TX4444");
    }

    @DisplayName("Delete Vehicle Controller Test")
    @Test
    public void givenVehicleId_whenDeleteVehicle_thenReturn200() throws Exception {
        // Given
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // When
        ResultActions response = mockMvc.perform(
                delete("/api/v1/vehicles/{id}", savedVehicle.getId()));

        // Then
        response.andExpect(status().isOk());
    }




}
