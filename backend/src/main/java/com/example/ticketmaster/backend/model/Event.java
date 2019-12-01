package com.example.ticketmaster.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name="events")
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Instant event_date;
}
