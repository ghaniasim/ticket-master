package com.example.ticketmaster.backend.repository;

import com.example.ticketmaster.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            value = "SELECT COUNT(*) FROM users WHERE event_id = ?1",
            nativeQuery = true
    )
    int findAllByEvent(Long id);

    @Query(
            value = "SELECT users.first_name, users.last_name, users.age, users.email, events.name \n" +
                    "AS event_name FROM users INNER JOIN events ON users.event_id = events.id",
            nativeQuery = true)
    List<?> findAllBookings();
}
