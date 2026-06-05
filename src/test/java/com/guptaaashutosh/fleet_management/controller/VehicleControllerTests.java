package com.guptaaashutosh.fleet_management.controller;

import com.guptaaashutosh.fleet_management.model.Vehicle;
import com.guptaaashutosh.fleet_management.service.VehicleService;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@WebMvcTest(VehicleController.class)
public class VehicleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehicle vehicle;


    @BeforeEach
    void setup() {
        vehicle = Vehicle.builder()
                .id(1L)
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
        //given

        BDDMockito.given(vehicleService.createVehicle(ArgumentMatchers.any(Vehicle.class)))
                .willReturn(vehicle);

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicle)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicleNumber", CoreMatchers.is(vehicle.getVehicleNumber())));
    }

    @DisplayName("Get Vehicle By Id Controller Test")
    @Test
    public void givenVehicleId_whenGetVehicleById_thenReturnVehicleObject() throws Exception {

        // Given
        Long vehicleId = 1L;

        BDDMockito.given(vehicleService.getVehicleById(vehicleId))
                .willReturn(vehicle);

        // When
        ResultActions response = mockMvc.perform(
                get("/api/v1/vehicles/{id}", vehicleId));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id",
                        CoreMatchers.is(vehicle.getId().intValue())))
                .andExpect(jsonPath("$.vehicleNumber",
                        CoreMatchers.is(vehicle.getVehicleNumber())));
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

        BDDMockito.given(vehicleService.getAllVehicles())
                .willReturn(vehicleList);

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

        BDDMockito.given(
                        vehicleService.updateVehicle(
                                ArgumentMatchers.eq(vehicleId),
                                ArgumentMatchers.any(Vehicle.class)))
                .willReturn(vehicle);

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

    @Test
    @DisplayName("Delete Vehicle Integration Test")
    void givenVehicleId_whenDeleteVehicle_thenReturn200() throws Exception {
        ResultActions response =
                mockMvc.perform(
                        delete("/api/v1/vehicles/{id}",
                                vehicle.getId()));

        response.andExpect(status().isOk());

        Assertions.assertThat(vehicleService.getVehicleById(vehicle.getId())).isNull();
    }
}
