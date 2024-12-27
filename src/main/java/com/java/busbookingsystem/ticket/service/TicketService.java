package com.java.busbookingsystem.ticket.service;


import com.java.busbookingsystem.bus.model.Bus;
import com.java.busbookingsystem.ticket.Repository.TicketRepository;
import com.java.busbookingsystem.ticket.mapper.TicketMapper;
import com.java.busbookingsystem.ticket.model.Ticket;
import com.java.busbookingsystem.ticket.model.TicketDTO;
import com.java.busbookingsystem.users.model.User;
import com.java.busbookingsystem.users.service.UserServiceImpl;
import com.java.busbookingsystem.utils.exception.GlobalExceptionWrapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

import static com.java.busbookingsystem.ticket.constants.TicketConstants.*;


@Service
public class TicketService implements ITicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserServiceImpl userService;


    @Override
    public TicketDTO save(@NonNull Ticket ticket) {
        User user=userService.fetchSelfInfo();
        ticket.setUser(user);

        Ticket savedTicket = this.ticketRepository.save(ticket);

        return TicketMapper.toDTO(savedTicket);
    }

    @Override
    public TicketDTO fetchById(long id) {
        Ticket ticket = findById(id);
        return TicketMapper.toDTO(ticket);
    }

    private Ticket findById(long id) {
        return this.ticketRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        TICKET.toLowerCase())));
    }


    @Override
    public List<TicketDTO> findAll() {
        List<Ticket> ticket = this.ticketRepository.findAll();
        return TicketMapper.toDTO(ticket);
    }


    @Override
    public String deleteById(long id) {
        Ticket ticket = findById(id);
        ticketRepository.delete(ticket);
        return String.format(DELETE_SUCCESSFUL);
    }


    @Override
    public String update(long id, TicketDTO entity) {
        return "";
    }





}
