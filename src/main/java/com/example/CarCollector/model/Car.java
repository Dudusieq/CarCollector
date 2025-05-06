package com.example.CarCollector.model;

import jakarta.persistence.*;

@Entity
@Table(name="car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "brandcar")
    private String brandcar;
    @Column(name = "model")
    private String model;
    @Column(name = "power")
    private int power;
    @Column(name = "speedup")
    private double speedup;
    @Column(name = "engine")
    private String engine;

    @ManyToOne(fetch = FetchType.EAGER)
    private Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;

    public void setSpeedUp(double speedup) {
        this.speedup = speedup;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public double getSpeedUp() {
        return speedup;
    }

    public String getEngine() {
        return engine;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrandcar(String brandcar) {
        this.brandcar = brandcar;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Long getId() {
        return id;
    }

    public String getBrandcar() {
        return brandcar;
    }

    public String getModel() {
        return model;
    }

    public int getPower() {
        return power;
    }
}