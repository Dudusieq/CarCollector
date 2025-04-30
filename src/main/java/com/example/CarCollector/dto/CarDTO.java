package com.example.CarCollector.dto;

public class CarDTO {
    private Long id;
    private String marka;
    private String model;
    private int moc;
    private double przyspieszenie;
    private String silnik;
    private OwnerDTO owner;
    private BrandDTO brand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrzyspieszenie() {
        return przyspieszenie;
    }

    public String getSilnik() {
        return silnik;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public BrandDTO getBrand() {
        return brand;
    }

    public void setPrzyspieszenie(double przyspieszenie) {
        this.przyspieszenie = przyspieszenie;
    }

    public void setSilnik(String silnik) {
        this.silnik = silnik;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMoc(int moc) {
        this.moc = moc;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public int getMoc() {
        return moc;
    }

}
