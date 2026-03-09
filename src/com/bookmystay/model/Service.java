package com.bookmystay.model;

/**
 * Service
 * 
 * Represents an optional add-on service
 * that can be attached to a reservation.
 */
public class Service {

    public String serviceName;
    public double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + price + ")";
    }
}