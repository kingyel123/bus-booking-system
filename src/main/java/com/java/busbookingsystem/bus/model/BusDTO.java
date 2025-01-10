package com.java.busbookingsystem.bus.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDTO {
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
    private Date date;
}
