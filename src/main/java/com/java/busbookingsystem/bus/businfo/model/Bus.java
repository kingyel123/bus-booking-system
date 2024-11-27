package com.java.busbookingsystem.bus.businfo.model;

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
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long id;
    private String busNo;
    private Long seatsAvailability;
    private String via;

    private Time departure;
}


