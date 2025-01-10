package com.java.busbookingsystem.bus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "bus")
public class Bus {
    @OneToMany(mappedBy = "bus")
    private List<Seat> seats;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String busNo;
    private Long seatsAvailability;
    private String departureFrom;
    private String destination;
    @DateTimeFormat(pattern = "HH:mm")
    private Time departure;
    private String model;
    private String brand;
    private String amenities;
    private long price;
    private Date date;

    @ElementCollection
    private List<Integer> occupiedSeats = new ArrayList<>();


    public List<Seat> getSeats() {
        return seats;
    }
}
