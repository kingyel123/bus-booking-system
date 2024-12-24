package com.java.busbookingsystem.bus.mapper;

import com.java.busbookingsystem.bus.model.Bus;
import com.java.busbookingsystem.bus.model.BusDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BusMapper {
    /**
     * Maps the user to user dto.
     *
     * @param bus The user entity.
     * @return Returns the user entity.
     */
    public static BusDTO toDTO(Bus bus) {
        BusDTO dto = new BusDTO();
        BeanUtils.copyProperties(bus, dto);
        return dto;
    }

    /**
     * Maps the list of users to user dto
     *
     * @param bus The list of user entity
     * @return The list of user dto.
     */
    public static List<BusDTO> toDTO(List<Bus> bus) {
        return bus.stream()
                .map(BusMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional user to optional user dto.
     *
     * @param user The user entity
     * @return The optional user dto.
     */
    public static Optional<BusDTO> toDTO(Optional<Bus> user) {
        return user.map( BusMapper::toDTO);
    }

    /**
     * Maps the user dto  to the user entity.
     *
     * @param dto The user dto.
     * @return The user entity.
     */
    public static Bus toEntity(BusDTO dto) {
        Bus bus = new Bus();
        BeanUtils.copyProperties(dto, bus);
        return bus;
    }}
