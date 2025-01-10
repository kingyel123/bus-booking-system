package com.java.busbookingsystem.ticket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.java.busbookingsystem.users.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "bus_no")
    private String busNo;

    @Column(name = "seat_no")
    private Long seatNo;


    @Column(name = "destination")
    private String destination;

    @Column(name = "departure_from")
    private String departureFrom; // Renamed from 'from' to 'departureFrom'

    @Column(name = "price")
    private long price;

    @Column(name = "model")
    private String model;

    @Column(name = "brand")
    private String brand;

    @Column(name = "amenities")
    private String amenities;

    @Column(name = "departure")
    private String departure;

    // Foreign key relationship with User
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")  // This ensures the foreign key relationship
    @JsonBackReference // This will be the child side of the relationship

    private User user;

    public void setUser(User user) {
        this.user = user;
    }


}
    
