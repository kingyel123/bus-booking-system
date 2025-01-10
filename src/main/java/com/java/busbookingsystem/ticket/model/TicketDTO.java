package com.java.busbookingsystem.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private long id;
    private String busNo;
    private Long seatNo;
    private String destination;
    private String departureFrom;
    private long price;
    private String model;
    private String brand;
    private String amenities;
    private String departure;
    private Long userId;

}