package com.example.CarCollector.model;

import jakarta.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marka;
    private String model;
    private int moc;
    private double przyspieszenie;
    private String silnik;

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

    public void setPrzyspieszenie(double przyspieszenie) {
        this.przyspieszenie = przyspieszenie;
    }

    public void setSilnik(String silnik) {
        this.silnik = silnik;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public double getPrzyspieszenie() {
        return przyspieszenie;
    }

    public String getSilnik() {
        return silnik;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getId() {
        return id;
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
