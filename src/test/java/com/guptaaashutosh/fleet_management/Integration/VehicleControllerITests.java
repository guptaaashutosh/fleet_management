package com.guptaaashutosh.fleet_management.Integration;

import com.guptaaashutosh.fleet_management.model.Vehicle;
import com.guptaaashutosh.fleet_management.repository.VehicleRepository;
import com.guptaaashutosh.fleet_management.service.VehicleService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VehicleControllerITests {

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

        // Given
        Vehicle vehicle2 = Vehicle.builder()
                .id(2L)
                .vehicleNumber("GJ20TX5678")
                .vehicleType("SUV")
                .manufacturer("Toyota")
                .build();

        List<Vehicle> vehicleList = List.of(vehicle, vehicle2);

        // When
        ResultActions response =
                mockMvc.perform(get("/api/v1/vehicles"));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(vehicleList.size())));
    }

    @DisplayName("Update Vehicle Controller Test")
    @Test
    public void givenUpdatedVehicle_whenUpdateVehicle_thenReturnUpdatedVehicle() throws Exception {

        // Given
        Long vehicleId = 1L;

        vehicle.setVehicleNumber("GJ26TX4444");

        // When
        ResultActions response = mockMvc.perform(
                put("/api/v1/vehicles/{id}", vehicleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicle)));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleNumber",
                        CoreMatchers.is("GJ26TX4444")));
    }

    @DisplayName("Delete Vehicle Controller Test")
    @Test
    public void givenVehicleId_whenDeleteVehicle_thenReturn200() throws Exception {

        // Given
        Long vehicleId = 1L;

        // When
        ResultActions response = mockMvc.perform(
                delete("/api/v1/vehicles/{id}", vehicleId));

        // Then
        response.andExpect(status().isOk());
    }




}
