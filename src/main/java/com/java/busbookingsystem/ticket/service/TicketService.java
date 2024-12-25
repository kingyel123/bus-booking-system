package com.java.busbookingsystem.ticket.service;


import com.java.busbookingsystem.ticket.Repository.TicketRepository;
import com.java.busbookingsystem.ticket.mapper.TicketMapper;
import com.java.busbookingsystem.ticket.model.Ticket;
import com.java.busbookingsystem.ticket.model.TicketDTO;
import com.java.busbookingsystem.users.model.User;
import com.java.busbookingsystem.users.service.UserServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public TicketDTO fetchById(long id) throws Exception {
        return null;
    }

    @Override
    public List<TicketDTO> findAll() {
        List<Ticket> ticket = this.ticketRepository.findAll();
        return TicketMapper.toDTO(ticket);
    }

    @Override
    public String update(long id, TicketDTO entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        return "";
    }


}
