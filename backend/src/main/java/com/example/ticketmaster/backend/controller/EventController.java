package com.example.ticketmaster.backend.controller;

import com.example.ticketmaster.backend.exception.ApiRequestException;
import com.example.ticketmaster.backend.model.Event;
import com.example.ticketmaster.backend.repository.EventRepository;
import com.example.ticketmaster.backend.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {

    private EventRepository eventRepository;
    private UserRepository userRepository;

    public EventController(EventRepository eventRepository, UserRepository userRepository) {
        super();
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/events")
    @ApiOperation(value = "Find all available events")
    Collection<Event> events() {
        if(eventRepository.findAll().isEmpty()) {
            throw new ApiRequestException(ApiRequestException.NO_RECORDS_FOUND);
        } else {
            return eventRepository.findAll();
        }
    }

    @GetMapping("event/{id}")
    @ApiOperation(value = "Find event by id",
            notes = "Provide an id to look up specific event from events model",
            response = Event.class)
    ResponseEntity<?> getEvent(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()) {
            return event.map(response -> ResponseEntity.ok().body(response))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        throw new ApiRequestException(ApiRequestException.NO_RECORDS_FOUND);
    }

    @PostMapping("/events")
    @ApiOperation(value = "Save an event",
            notes = "Provide an event name of greater than 2 characters and a date in the future to create an event",
            response = Event.class)
    ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        if (event.getName().length() < 3) {
            throw new ApiRequestException(ApiRequestException.VALID);
        } else if(event.getEvent_date().compareTo(Instant.now()) < 0) {
            throw new ApiRequestException(ApiRequestException.VALID);
        } else {
            try {
                Event result = eventRepository.save(event);
                return ResponseEntity.created(new URI("/api/events" + result.getId())).body(result);
            } catch (Exception e) {
                System.out.println(ApiRequestException.WRONG);
            }
        }
        throw new ApiRequestException(ApiRequestException.WRONG);
    }

    @PutMapping("/event/{id}")
    @ApiOperation(value = "Modify an existing event",
            notes = "Provide an id of existing event and name greater than 2 characters and a date in the future to modify an event",
            response = Event.class)
    ResponseEntity<Event> updateEvent(@Valid @RequestBody Event event) {
        ResponseEntity found = getEvent(event.getId());

        if(found.hasBody()) {
            if(event.getName().length() < 3) {
                throw new ApiRequestException(ApiRequestException.VALID);
            } else if(event.getEvent_date().compareTo(Instant.now()) < 0) {
                throw new ApiRequestException(ApiRequestException.VALID);
            } else {
                try {
                    Event result = eventRepository.save(event);
                    return ResponseEntity.ok().body(result);
                } catch (Exception e) {
                    System.out.println(ApiRequestException.WRONG);
                }
            }
        }
        throw new ApiRequestException(ApiRequestException.WRONG);
    }

    @DeleteMapping("/events/{id}")
    @ApiOperation(value = "Delete an event",
            notes = "Provide an id of an existing event to delete it. Already booked events can not be deleted",
            response = Event.class)
    ResponseEntity<?> deleteEvent(@PathVariable Long id) {

        if(userRepository.findAllByEvent(id) <= 0 ) {
            try {
                eventRepository.deleteById(id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                System.out.println(ApiRequestException.WRONG);
            }
        } else {
            throw new ApiRequestException("There are " +
                    userRepository.findAllByEvent(id) +
                    " ticket(s) sold for this event, so delete not allowed");
        }
        throw new ApiRequestException(ApiRequestException.NO_RECORDS_FOUND);
    }
}
