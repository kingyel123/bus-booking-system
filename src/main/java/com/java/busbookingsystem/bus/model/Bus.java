package com.java.busbookingsystem.bus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String busNo;
    private Long seatsAvailability;
    private String departureFrom; // Renamed from 'from' to 'departureFrom'
    private String destination; // Renamed from 'to' to 'destination'
    private Time departure;
    private String model;
    private String brand;
    private String amenities;
    private long price;
}