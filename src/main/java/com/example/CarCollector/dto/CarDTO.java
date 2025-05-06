package com.example.CarCollector.dto;

public class CarDTO {
    private Long id;
    private String brandcar;
    private String model;
    private int power;
    private double speedup;
    private String engine;
    private OwnerDTO owner;
    private BrandDTO brand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSpeedUp() {
        return speedup;
    }

    public String getEngine() {
        return engine;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public BrandDTO getBrand() {
        return brand;
    }

    public void setSpeedUp(double speedup) {
        this.speedup = speedup;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
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