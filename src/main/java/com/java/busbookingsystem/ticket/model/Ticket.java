package com.java.busbookingsystem.ticket.model;

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

    private String source;

    private String destination;

    @Column(name = "departure_time")
    private String departureTime;

    // Use @ManyToOne relationship for 'user' field
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // This ensures the foreign key relationship
    private User user;

}
