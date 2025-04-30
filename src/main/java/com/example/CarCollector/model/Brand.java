package com.example.CarCollector.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Country country;

    @OneToOne(mappedBy = "brand")
    @JsonManagedReference
    private Cost cost;

    public Country getCountry() {
        return country;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
