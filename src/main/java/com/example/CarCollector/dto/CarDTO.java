package com.example.CarCollector.dto;

public class CarDTO {
    private Long id;
    private String marka;
    private String model;
    private int moc;

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
