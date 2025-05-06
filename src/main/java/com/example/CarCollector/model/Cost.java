package com.example.CarCollector.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private double cost;

    @OneToOne
    @JoinColumn(name = "brand_id")
    @JsonBackReference
    private Brand brand;

    public Long getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
