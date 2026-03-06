package com.bookmystay.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Use Case 1: Room Inventory Setup & Management
 * 
 * @author Developer
 * @version 5.0
 * 
 * RoomInventoryApp
 * Manages hotel room inventory using HashMap.
 * Shows availability and allows admin to update room counts and prices.
 */
public class BookMyStayApp {

    // HashMap for room type -> available count
    private static HashMap<String, Integer> roomInventory = new HashMap<>();

    // HashMap for room type -> price per night
    private static HashMap<String, Double> roomPrice = new HashMap<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Initialize room types
        roomInventory.put("Single", 10);
        roomInventory.put("Double", 8);
        roomInventory.put("Suite", 5);

        roomPrice.put("Single", 2000.0);
        roomPrice.put("Double", 3500.0);
        roomPrice.put("Suite", 6000.0);

        int choice;

        do {
            System.out.println("\n=== Hotel Room Inventory System ===");
            System.out.println("1. Current Availability");
            System.out.println("2. Update Room Inventory");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    showAvailability();
                    break;

                case 2:
                    updateInventory(scanner);
                    break;

                case 3:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 3);

        scanner.close();
    }

    // Display current room availability
    private static void showAvailability() {

        System.out.println("\n--- Current Room Availability ---");

        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {

            String roomType = entry.getKey();
            int count = entry.getValue();
            double price = roomPrice.get(roomType);

            System.out.println(roomType + " | Rooms Available: " + count + " | Price: ₹" + price);
        }
    }

    // Update inventory
    private static void updateInventory(Scanner scanner) {

        System.out.print("Enter room type (Single/Double/Suite): ");
        String roomType = scanner.nextLine();

        if (!roomInventory.containsKey(roomType)) {
            System.out.println("Room type does not exist.");
            return;
        }

        System.out.print("Enter new available room count: ");
        int newCount = scanner.nextInt();

        System.out.print("Enter new price per night: ");
        double newPrice = scanner.nextDouble();

        roomInventory.put(roomType, newCount);
        roomPrice.put(roomType, newPrice);

        System.out.println("Room inventory updated successfully.");
    }
}