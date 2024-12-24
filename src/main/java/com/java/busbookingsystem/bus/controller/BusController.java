package com.java.busbookingsystem.bus.controller;

import com.java.busbookingsystem.bus.constants.BusConstants;
import com.java.busbookingsystem.bus.model.Bus;
import com.java.busbookingsystem.bus.model.BusDTO;
import com.java.busbookingsystem.bus.service.BusService;
import com.java.busbookingsystem.utils.RestHelper;
import com.java.busbookingsystem.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/bus")
public  class BusController {
    @Autowired
    private BusService busservice;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> save(@Validated @RequestBody Bus bus) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("bus", busservice.save(bus));
        return RestHelper.responseSuccess(listHashMap);
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // Allow both USER and ADMIN roles
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("bus", busservice.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }

    // GET endpoint to fetch a movie by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // Allow both USER and ADMIN roles
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("bus", busservice.fetchById(id));
        return RestHelper.responseSuccess(listHashMap);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> deleteBusbyId(@PathVariable long id) {
        String message = busservice.deleteById(id);
        return RestHelper.responseMessage(message);


    }

    @PatchMapping("put/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> update(@PathVariable long id,
                                               @Validated @RequestBody BusDTO busDTO) {
        String message = busservice.update(id, busDTO);
        return RestHelper.responseMessage(message);
    }

}



