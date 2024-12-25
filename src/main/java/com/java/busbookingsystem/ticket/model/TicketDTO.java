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
    private long seatNo;
    private String source;
    private String destination;
    private String departureTime;
    private Long userId;

}