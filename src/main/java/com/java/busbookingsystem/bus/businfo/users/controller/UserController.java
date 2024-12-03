package com.java.busbookingsystem.bus.businfo.users.controller;



import com.java.busbookingsystem.bus.businfo.constants.BusConstants;
import com.java.busbookingsystem.bus.businfo.users.model.User;
import com.java.busbookingsystem.bus.businfo.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userservice;

    // Endpoint to save a new user
    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
        return userservice.save(user);
    }

    // Endpoint to update a user by ID
    @PutMapping("put/{id}")
    public ResponseEntity<String> updateUserById(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        String response = userservice.update(user);

        if (BusConstants.UPDATE_SUCCESSFUL.equals(response)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Endpoint to fetch all users
    @GetMapping
    public List<User> getUsers() {
        return userservice.findAll();
    }

    // Endpoint to fetch a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws Exception {
        User user = userservice.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String response = userservice.deleteById(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
