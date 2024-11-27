package com.java.busbookingsystem.bus.businfo.controller;

import com.java.busbookingsystem.bus.businfo.model.Bus;
import com.java.busbookingsystem.bus.businfo.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bus")
public class BusController {
    @Autowired
private BusService busservice;

@PostMapping
    public Bus saveBud(@Validated @RequestBody Bus bus){
    return busservice.save(bus);
}


}
