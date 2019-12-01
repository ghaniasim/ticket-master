package com.example.ticketmaster.backend.repository;

import com.example.ticketmaster.backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);
}
