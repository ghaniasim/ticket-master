package com.example.ticketmaster.backend.controller;

import com.example.ticketmaster.backend.exception.ApiRequestException;
import com.example.ticketmaster.backend.model.User;
import com.example.ticketmaster.backend.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    @ApiOperation(value = "Find all users")
    List<User> getUsers() {
        if(userRepository.findAll().isEmpty()) {
            throw new ApiRequestException(ApiRequestException.NO_RECORDS_FOUND);
        } else {
            return userRepository.findAll();
        }
    }

    @GetMapping("/bookings")
    @ApiOperation(value = "Find all bookings")
    List<?> getBookings() {
        if(userRepository.findAllBookings().isEmpty()) {
            throw new ApiRequestException(ApiRequestException.NO_RECORDS_FOUND);
        } else {
            return userRepository.findAllBookings();
        }
    }

    @PostMapping("/users")
    @ApiOperation(value = "Save user and book an event",
            notes = "Provide first name of minimum 2 characters, correctly formatted email, age greater than 13 " +
                    "and id of existing event to book an event")
    ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
        if (user.getAge() <= 13) {
            throw new ApiRequestException(ApiRequestException.VALID);
        }
        User result = userRepository.save(user);
        return ResponseEntity.created(new URI("/api/users" + result.getId())).body(result);
    }

}
