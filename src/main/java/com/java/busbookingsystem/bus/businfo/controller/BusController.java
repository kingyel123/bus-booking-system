package com.java.busbookingsystem.bus.businfo.controller;

import com.java.busbookingsystem.bus.businfo.constants.BusConstants;
import com.java.busbookingsystem.bus.businfo.model.Bus;
import com.java.busbookingsystem.bus.businfo.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bus")
public  class BusController {
    @Autowired
    private BusService busservice;

    @PostMapping
    public Bus saveBus(@Validated @RequestBody Bus bus) {
        return busservice.save(bus);
    }

    @PutMapping("put/{id}")
    public  ResponseEntity<String> updateBusbyId(@RequestBody Bus bus, @PathVariable Long id) {
        bus.setId(id);
        String response = busservice.update(bus);

        if (BusConstants.UPDATE_SUCCESSFUL.equals(response)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    }


