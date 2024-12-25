package com.java.busbookingsystem.ticket.controller;


import com.java.busbookingsystem.ticket.model.Ticket;
import com.java.busbookingsystem.ticket.model.TicketDTO;
import com.java.busbookingsystem.ticket.service.TicketService;
import com.java.busbookingsystem.utils.RestHelper;
import com.java.busbookingsystem.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/ticket")
public  class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<RestResponse> saveTicket(@Validated @RequestBody Ticket ticket){
        TicketDTO saveTicket = ticketService.save(ticket);
        Map<String, Object> response = new HashMap<>();
        response.put("ticket", saveTicket);
        return RestHelper.responseSuccess(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("tickets", ticketService.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }


}


