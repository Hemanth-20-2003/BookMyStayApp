package com.bookmystay.main;

import java.util.Scanner;
import com.bookmystay.service.RoomInventoryService;

/**
 * Use Case 2: Guest Room Search
 * 
 * @author Developer
 * @version 2.0
 * 
 * BookMyStayApp
 * Main application class.
 * Handles user interaction and menu operations.
 * Business logic is delegated to the service class.
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Creating service object that manages room inventory
        RoomInventoryService service = new RoomInventoryService();

        int choice;

        do {

            System.out.println("\n=== Hotel Room Inventory System ===");
            System.out.println("1. Current Availability");
            System.out.println("2. Update Room Inventory");
            System.out.println("3. Search Available Rooms (Guest)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    service.showAvailability();
                    break;

                case 2:
                    service.updateInventory(scanner);
                    break;

                case 3:
                    service.searchAvailableRooms();
                    break;

                case 4:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 4);

        scanner.close();
    }
}