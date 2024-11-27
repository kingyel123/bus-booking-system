package com.java.busbookingsystem.bus.businfo.service;

import com.java.busbookingsystem.bus.businfo.Repository.BusRepository;
import com.java.busbookingsystem.bus.businfo.constants.BusConstants;
import com.java.busbookingsystem.bus.businfo.model.Bus;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService implements IBusService{
@Autowired
    private BusRepository busrepository;

    @Override
    public List<Bus> findAll(){return  busrepository.findAll();};

    @Override
    public Bus save(@NonNull Bus bus){
        return busrepository.save(bus);
    }

    @Override
    public Bus findById(long id) throws  Exception{
        return busrepository.findById(id).orElseThrow(()->new Exception(BusConstants.NOT_FOUND));
    }

    @Override
    public String update(@NonNull Bus bus)  {
        long id = bus.getId(); // Assuming Bus has a method to get its ID
        return busrepository.findById(id)
                .map(bus1 -> {
                    bus1.setBusNo(bus.getBusNo());
                    bus1.setSeatsAvailability(bus.getSeatsAvailability());
                    bus1.setVia(bus.getVia());
                    bus1.setDeparture(bus.getDeparture());
                    busrepository.save(bus1); // Save the updated bus object
                    return BusConstants.UPDATE_SUCCESSFUL; // Return success message
                })
                .orElse(BusConstants.NOT_FOUND); // Return not found message if the bus doesn't exist
    }

    @Override
    public String deleteById(long id) {
        return "";
    }


}
