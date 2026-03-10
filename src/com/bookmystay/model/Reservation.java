package com.bookmystay.model;

/**
 * Reservation
 * 
 * Represents a confirmed hotel reservation.
 * Stores guest information, room type and assigned room ID.
 */
public class Reservation {

    public String guestName;
    public String roomType;
    public String roomId;
    public boolean active;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.active = true;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName +
               " | Room Type: " + roomType +
               " | Room ID: " + roomId +
               " | Status: " + (active ? "Active" : "Cancelled");
    }
}