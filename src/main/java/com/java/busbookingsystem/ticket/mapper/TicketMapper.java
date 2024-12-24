package com.java.busbookingsystem.ticket.mapper;

import com.java.busbookingsystem.ticket.model.Ticket;
import com.java.busbookingsystem.ticket.model.TicketDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TicketMapper {
    /**
     * Maps the instructor to instructor dto.
     *
     * @param ticket The instructor entity.
     * @return Returns the instructor entity.
     */
    public static TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        BeanUtils.copyProperties(ticket, dto, "password");
        if (ticket.getUser() != null) {
            dto.setUserId(ticket.getUser().getId());  // Copy the 'id' from User to userId in DTO
        }
        return dto;
    }

    /**
     * Maps the list of instructors to instructor dto
     *
     * @param tickets The list of instructor entity
     * @return The list of instructor dto.
     */
    public static List<TicketDTO> toDTO(List<Ticket> tickets) {
        return tickets.stream()
                .map(TicketMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional instructor to optional instructor dto.
     *
     * @param ticket The instructor entity
     * @return The optional instructor dto.
     */
    public static Optional<TicketDTO> toDTO(Optional<Ticket> ticket) {
        return ticket.map(TicketMapper::toDTO);
    }

    /**
     * Maps the instructor dto  to the instructor entity.
     *
     * @param dto The instructor dto.
     * @return The instructor entity.
     */
    public static Ticket toEntity(TicketDTO dto) {
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(dto, ticket);
        return ticket;
    }
}
