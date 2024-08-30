package com.example.bootcamps.application;

import java.util.Set;

public class BootcampDTO {
    private Long id;
    private String name;
    private String description;
    private Set<String> capacities;

    public BootcampDTO(Long id, String name, String description, Set<String> capacities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacities = capacities;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getCapacities() {
        return capacities;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacities(Set<String> capacities) {
        this.capacities = capacities;
    }
}
