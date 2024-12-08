package com.java.busbookingsystem.users.controller;



import com.java.busbookingsystem.users.model.User;
import com.java.busbookingsystem.users.model.UserDTO;
import com.java.busbookingsystem.users.service.UserService;
import com.java.busbookingsystem.utils.RestHelper;
import com.java.busbookingsystem.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Fetch self info of the instructor
     *
     * @return The details of the authenticated user.
     */

    @GetMapping("/self")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<RestResponse> fetchSelfInfo() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.fetchSelfInfo());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches the instructor by identifier.
     *
     * @param id The unique identifier of the instructor.
     * @return The instructor entity.
     */

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.fetchById(id));
        return RestHelper.responseSuccess(listHashMap);
    }


    /**
     * Fetches all the instructor entities in the system.
     *
     * @return The list of instructor entities.
     */

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }
    /**
     * Signing up the new instructor.
     *
     * @param user The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User user) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Updates the existing instructor entity.
     *
     * @param userDTO The updated instructor entity.
     * @return The message indicating the confirmation on updated instructor entity.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','ADMIN')")
    public ResponseEntity<RestResponse> update(@PathVariable long id,
                                               @Validated @RequestBody UserDTO userDTO) {
        String message = userService.update(id, userDTO);
        return RestHelper.responseMessage(message);
    }

    /**
     * Deletes the instructor by id.
     *
     * @param id The unique identifier of the entity.
     * @return The message indicating the confirmation on deleted instructor entity.
     */

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        String message = userService.deleteById(id);
        return RestHelper.responseMessage(message);
        }

}
