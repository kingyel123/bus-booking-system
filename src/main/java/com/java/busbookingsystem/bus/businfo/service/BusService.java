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



    public List<Bus> findAll(){return  busrepository.findAll();};

    public Bus save(@NonNull Bus bus){
        return busrepository.save(bus);
    }


    public Bus findById(long id) throws  Exception{
        return busrepository.findById(id).orElseThrow(()->new Exception(BusConstants.NOT_FOUND));
    }

    public String update(@NonNull Bus bus){
         busrepository.save(bus);
        return BusConstants.UPDATE_SUCCESSFUL;
    }

    public String deleteById(long Id){
        busrepository.deleteById(Id);
        return  BusConstants.DELETE_SUCCESSFUL;
    }
}
