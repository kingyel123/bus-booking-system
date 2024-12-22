package com.java.busbookingsystem.bus.service;

import com.java.busbookingsystem.Exception.ResourceNotFoundException;
import com.java.busbookingsystem.bus.Repository.BusRepository;
import com.java.busbookingsystem.bus.model.Bus;
import com.java.busbookingsystem.utils.exception.GlobalExceptionWrapper;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService implements IBusService{
   @Autowired
    private BusRepository busrepository;

    @Override
    public Bus save(@NonNull Bus bus){
        try {
            if (busrepository.existsById(bus.getId())){
                throw new GlobalExceptionWrapper.BadRequestException("Bus with id '" + bus.getBusNo() + "' already exists ");
            }
            return busrepository.save(bus);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalExceptionWrapper.BadRequestException("Bus with this id already exists");
        }
    }


    @Override
    public List<Bus> findAll() {
        // Fetch all movies from the database
        return busrepository.findAll();
    }





    @Override
    public Bus findById(long id) {
        return busrepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionWrapper.NotFoundException(
                        String.format("Bus not found with id: %d", id)));
    }



    @Override
    public String update(long id, Long entity) {
        return "";
    }

    @Override
    public Bus save(Long entity) {
        return null;
    }

    @Override
    public Bus fetchById(long id) throws Exception {
        return null;
    }
    @Override
    public String update(long id, Bus entity) {
        return "";
    }
    @Override
    public String update(Bus entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        Bus bus = findById(id);
        busrepository.delete(bus);
        return null;
    }

    @Override
    public Bus updateBus(Long id, Bus bus) {
        Bus bus1 = busrepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + id));

        // Update the fields with values from the bus parameter
        bus1.setBusNo(bus.getBusNo());
        bus1.setModel(bus.getModel());
        bus1.setDepartureFrom(bus.getDepartureFrom());
        bus1.setDestination(bus.getDestination());
        bus1.setAmenities(bus.getAmenities());
        bus1.setDeparture(bus.getDeparture());
        bus1.setSeatsAvailability(bus.getSeatsAvailability());
        bus1.setBrand(bus.getBrand());
        bus1.setPrice(bus.getPrice());

        // Save the updated bus back to the database
        return busrepository.save(bus1);
    }

    }
