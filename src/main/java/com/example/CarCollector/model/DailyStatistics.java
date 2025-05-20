package com.example.CarCollector.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class DailyStatistics {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private long totalUsers;
    private long newUsers;
    private long totalCars;

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getNewUsers() {
        return newUsers;
    }

    public long getTotalCars() {
        return totalCars;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public void setNewUsers(long newUsers) {
        this.newUsers = newUsers;
    }

    public void setTotalCars(long totalCars) {
        this.totalCars = totalCars;
    }
}
