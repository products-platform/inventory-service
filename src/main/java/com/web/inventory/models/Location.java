package com.web.inventory.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "LOCATIONS")
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String state;
    private String country;
    private String type;

    private Boolean active = true;

    private Instant createdAt;
    private Instant updatedAt;
}
