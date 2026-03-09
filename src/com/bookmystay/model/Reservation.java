package com.bookmystay.model;

/**
 * Reservation
 * 
 * Represents a booking request made by a guest.
 * Each reservation stores guest name and requested room type.
 */
public class Reservation {

    public String guestName;
    public String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}