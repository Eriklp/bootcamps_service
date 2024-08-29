package com.example.bootcamps.domain.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bootcamps")
public class Bootcamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    // Almacenar identificadores de capacidades como Strings
    @ElementCollection
    @CollectionTable(name = "bootcamp_capacities", joinColumns = @JoinColumn(name = "bootcamp_id"))
    @Column(name = "capacity_id")
    private Set<String> capacities = new HashSet<>();

    public Bootcamp() {
    }

    public Bootcamp(Long id, String name, String description, Set<String> capacities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacities = capacities;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getCapacities() {
        return capacities;
    }

    public void setCapacities(Set<String> capacities) {
        this.capacities = capacities;
    }
}
