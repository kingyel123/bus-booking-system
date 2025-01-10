package com.java.busbookingsystem.bus.service;

import com.java.busbookingsystem.Exception.ResourceNotFoundException;
import com.java.busbookingsystem.bus.Repository.BusRepository;
import com.java.busbookingsystem.bus.mapper.BusMapper;
import com.java.busbookingsystem.bus.model.Bus;
import com.java.busbookingsystem.bus.model.BusDTO;
import com.java.busbookingsystem.bus.model.Seat;
import com.java.busbookingsystem.utils.exception.GlobalExceptionWrapper;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.java.busbookingsystem.bus.constants.BusConstants.*;
import static com.java.busbookingsystem.ticket.constants.TicketConstants.NOT_FOUND_MESSAGE;

@Service
public class BusServiceImpl implements IBusService{
   @Autowired
    private BusRepository busrepository;

    @Override
    public BusDTO save(@NonNull Bus bus){

            Bus savedBus= this.busrepository.save(bus);
        return BusMapper.toDTO(savedBus);

        }



    @Override
    public List<BusDTO> findAll() {
        List<Bus> bus= this.busrepository.findAll();
        return BusMapper.toDTO(bus);
    }


    @Override
    public BusDTO fetchById(long id) {
        Bus bus = findById(id);
        return BusMapper.toDTO(bus);
    }




    private Bus findById(long id) {
        return this.busrepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND.toLowerCase())));
    }

    @Override
    public String deleteById(long id) {
        Bus bus = findById(id);
        busrepository.delete(bus);
        return String.format(DELETE_SUCCESSFUL);
    }


    @Override
    public String update(long id, @NonNull BusDTO busdto) {
        Bus bus = busrepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + id));

        // Update the fields with values from the busdto parameter
        bus.setBusNo(busdto.getBusNo());
        bus.setModel(busdto.getModel());
        bus.setDepartureFrom(busdto.getDepartureFrom());
        bus.setDestination(busdto.getDestination());
        bus.setAmenities(busdto.getAmenities());
        bus.setDeparture(busdto.getDeparture());
        bus.setSeatsAvailability(busdto.getSeatsAvailability());
        bus.setBrand(busdto.getBrand());
        bus.setPrice(busdto.getPrice());
        bus.setDate(busdto.getDate());

        this.busrepository.save(bus); // Save the updated bus entity
        return "Bus updated successfully with ID: " + bus.getId(); // Return a success message
    }

    public String bookSeat(long busId, int seatNumber) {
        // Fetch the bus entity from the database
        Bus bus = busrepository.findById(busId).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, BUS.toLowerCase())));


        // Check if the bus exists
        if (bus == null) {
            throw new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, BUS.toLowerCase()));
        }

        // Check if the seat number is valid
        if (seatNumber < 1 || seatNumber > bus.getSeatsAvailability()) {
            throw new GlobalExceptionWrapper.BadRequestException(INVALID_SEAT_NUMBER);
        }

        // Check if the seat is already occupied
        if (isSeatOccupied(busId, seatNumber)) {
            throw new GlobalExceptionWrapper.BadRequestException(SEAT_ALREADY_OCCUPIED);
        }

        // Mark the seat as occupied
        bus.getOccupiedSeats().add(seatNumber);
        busrepository.save(bus);

        return String.format(SEAT_OCCUPIED_SUCCESSFULLY, seatNumber);
    }

    private boolean isSeatOccupied(long busId, int seatNumber) {
        Bus bus = busrepository.findById(busId).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, BUS.toLowerCase())));
        return bus.getOccupiedSeats().contains(seatNumber);
    }

    public List<Seat> getSeats(long busId) {
        Bus bus = busrepository.findById(busId).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException("Bus not found"));
        return bus.getSeats();
    }
    }
