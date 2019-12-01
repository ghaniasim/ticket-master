package com.example.ticketmaster.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "First name can not be null")
    @Size(min = 2, message = "First name can not be less than 2 characters")
    private String first_name;

    private String last_name;

    private int age;

    @Email(message = "Email is not formatted correctly")
    private String email;

    @ManyToOne
    private Event event ;
}
