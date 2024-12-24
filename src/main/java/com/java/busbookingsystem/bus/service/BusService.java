package com.java.busbookingsystem.bus.service;

import com.java.busbookingsystem.Exception.ResourceNotFoundException;
import com.java.busbookingsystem.bus.Repository.BusRepository;
import com.java.busbookingsystem.bus.mapper.BusMapper;
import com.java.busbookingsystem.bus.model.Bus;
import com.java.busbookingsystem.bus.model.BusDTO;
import com.java.busbookingsystem.utils.exception.GlobalExceptionWrapper;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.java.busbookingsystem.bus.constants.BusConstants.*;

@Service
public class BusService implements IBusService{
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

        this.busrepository.save(bus); // Save the updated bus entity
        return "Bus updated successfully with ID: " + bus.getId(); // Return a success message
    }
    }
