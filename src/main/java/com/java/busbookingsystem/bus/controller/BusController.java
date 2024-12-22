package com.java.busbookingsystem.bus.controller;

import com.java.busbookingsystem.bus.constants.BusConstants;
import com.java.busbookingsystem.bus.model.Bus;
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
    @PreAuthorize("hasAuthority('ADMIN')") // Only admin can create movies
    public ResponseEntity<RestResponse> saveMovie(@Validated @RequestBody Bus bus){
        try{
            Bus saveBus = busservice.save(bus);
            Map<String, Object> response = new HashMap<>();
            response.put("bus", saveBus);
            return RestHelper.responseSuccess(response);
        } catch (Exception e){
            return RestHelper.responseError(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // Allow both USER and ADMIN roles
    public ResponseEntity<List<Bus>> getAllMovies() {
        List<Bus> bus = busservice.findAll();
        if (bus.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no movies found
        }
        return ResponseEntity.ok(bus);
    }

    // GET endpoint to fetch a movie by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // Allow both USER and ADMIN roles
    public ResponseEntity<RestResponse> getMovieId(@PathVariable long id){
        try {
            Bus bus = busservice.findById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("bus", bus);
            return RestHelper.responseSuccess(response);
        } catch (Exception e){
            return RestHelper.responseError(BusConstants.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')") // Only admin can create movies
    public ResponseEntity<String> deleteMovieById(@PathVariable long id) {
        try {
            busservice.deleteById(id);
            return ResponseEntity.ok("Movie deleted successfully."); // Return 200 OK with success message
        } catch (Exception e) {
            return ResponseEntity.status(404).body(BusConstants.NOT_FOUND); // Return 404 if the movie is not found
        }
    }

    @PatchMapping("put/{id}")
    @PreAuthorize("hasAuthority('ADMIN')") // Only admin can create movies
    public ResponseEntity<Bus> updateBus(@PathVariable Long id, @Validated @RequestBody Bus bus) {
        Bus busUpdate = busservice.updateBus(id, bus);
        return ResponseEntity.ok(busUpdate);
    }
}



