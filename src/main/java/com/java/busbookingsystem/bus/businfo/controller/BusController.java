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

    @GetMapping("/BUS")
    List<Bus> getBus() {
        return busservice.findAll();
    }


    @GetMapping("/BUS/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable Long id) {
        try {
            Bus bus = busservice.findById(id);
            return ResponseEntity.ok(bus);
        } catch (Exception e) {
            throw new RuntimeException("Not Found", e);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteBus(@PathVariable Long id) {
        try {
            String bus =  busservice.deleteById(id);
            return ResponseEntity.ok(bus);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't delete",e);
}
    }

}



