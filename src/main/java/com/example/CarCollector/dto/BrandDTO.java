package com.example.CarCollector.dto;

import com.example.CarCollector.model.Cost;

public class BrandDTO {
    private String name;
    private CountryDTO country;
    private Cost cost;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public Cost getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }
}
