package com.java.busbookingsystem.ticket.service;

import com.java.busbookingsystem.bus.utils.IGenericCrudService;
import com.java.busbookingsystem.ticket.model.Ticket;
import com.java.busbookingsystem.ticket.model.TicketDTO;

import java.util.List;


public interface ITicketService extends IGenericCrudService<Ticket, TicketDTO> {
    List<TicketDTO> findByUser ();
}
