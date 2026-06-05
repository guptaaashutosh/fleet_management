package com.guptaaashutosh.fleet_management.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_number", nullable = false, unique = true)
    private String vehicleNumber;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String model;

    @Column(name = "manufacturing_year")
    private Integer manufacturingYear;

    @Column(name = "seating_capacity")
    private Integer seatingCapacity;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(nullable = false)
    private String status;
}