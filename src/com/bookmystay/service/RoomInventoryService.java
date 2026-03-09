package com.bookmystay.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * RoomInventoryService
 * 
 * Service class that manages hotel room inventory.
 * Uses HashMap for fast lookup of room availability and pricing.
 * Supports inventory updates and guest room search.
 */
public class RoomInventoryService {

    // HashMap for room type -> available count
    private HashMap<String, Integer> roomInventory = new HashMap<>();

    // HashMap for room type -> price per night
    private HashMap<String, Double> roomPrice = new HashMap<>();


    /**
     * Constructor
     * Initializes default room inventory and prices.
     */
    public RoomInventoryService() {

        roomInventory.put("Single", 10);
        roomInventory.put("Double", 8);
        roomInventory.put("Suite", 5);

        roomPrice.put("Single", 2000.0);
        roomPrice.put("Double", 3500.0);
        roomPrice.put("Suite", 6000.0);
    }


    /**
     * showAvailability()
     * Displays current room availability and pricing.
     */
    public void showAvailability() {

        System.out.println("\n--- Current Room Availability ---");

        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {

            String roomType = entry.getKey();
            int count = entry.getValue();
            double price = roomPrice.get(roomType);

            System.out.println(roomType +
                    " | Rooms Available: " + count +
                    " | Price: ₹" + price);
        }
    }


    /**
     * updateInventory()
     * Allows admin to update room count and price.
     */
    public void updateInventory(Scanner scanner) {

        System.out.print("Enter room type (Single/Double/Suite): ");
        String roomType = scanner.nextLine();

        // Defensive check to validate room type
        if (!roomInventory.containsKey(roomType)) {
            System.out.println("Room type does not exist.");
            return;
        }

        System.out.print("Enter new available room count: ");
        int newCount = scanner.nextInt();

        System.out.print("Enter new price per night: ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();

        roomInventory.put(roomType, newCount);
        roomPrice.put(roomType, newPrice);

        System.out.println("Room inventory updated successfully.");
    }


    /**
     * searchAvailableRooms()
     * Guest can search for available rooms.
     * Only rooms with availability > 0 are displayed.
     * This method provides read-only access to inventory.
     */
    public void searchAvailableRooms() {

        System.out.println("\n--- Available Rooms For Booking ---");

        boolean found = false;

        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {

            String roomType = entry.getKey();
            int count = entry.getValue();

            // Availability validation
            if (count > 0) {

                double price = roomPrice.get(roomType);

                System.out.println("Room Type: " + roomType +
                        " | Available: " + count +
                        " | Price: ₹" + price);

                found = true;
            }
        }

        // If no rooms are available
        if (!found) {
            System.out.println("No rooms currently available.");
        }
    }
}