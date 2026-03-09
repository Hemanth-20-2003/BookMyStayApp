package com.bookmystay.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

import com.bookmystay.model.Reservation;

/**
 * RoomInventoryService
 * 
 * Handles hotel room inventory and booking operations.
 * Uses HashMap for inventory and Queue for booking requests.
 */
public class RoomInventoryService {

    // HashMap for room type -> available count
    private HashMap<String, Integer> roomInventory = new HashMap<>();

    // HashMap for room type -> price per night
    private HashMap<String, Double> roomPrice = new HashMap<>();

    // Queue for booking requests (FIFO principle)
    private Queue<Reservation> bookingQueue = new LinkedList<>();
    
 // Set to store all booked room IDs (ensures uniqueness)
    private HashSet<String> bookedRoomIds = new HashSet<>();

    // Map of roomType -> assigned room IDs
    private HashMap<String, HashSet<String>> assignedRooms = new HashMap<>();


    /**
     * Constructor
     * Initializes room inventory and pricing.
     */
    public RoomInventoryService() {

        roomInventory.put("Single", 10);
        roomInventory.put("Double", 8);
        roomInventory.put("Suite", 5);

        roomPrice.put("Single", 2000.0);
        roomPrice.put("Double", 3500.0);
        roomPrice.put("Suite", 6000.0);
        
        assignedRooms.put("Single", new HashSet<>());
        assignedRooms.put("Double", new HashSet<>());
        assignedRooms.put("Suite", new HashSet<>());
    }


    /**
     * showAvailability()
     * Displays current room availability.
     * Admin can optionally update inventory.
     */
    public void showAvailability(Scanner scanner) {

        System.out.println("\n--- Current Room Availability ---");

        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {

            String roomType = entry.getKey();
            int count = entry.getValue();
            double price = roomPrice.get(roomType);

            System.out.println(roomType +
                    " | Rooms Available: " + count +
                    " | Price: ₹" + price);
        }

        System.out.print("\nDo you want to update inventory? (yes/no): ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Defensive validation
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
    }


    /**
     * searchAvailableRooms()
     * Allows customers to view available rooms without modifying inventory.
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

        if (!found) {
            System.out.println("No rooms currently available.");
        }
    }


    /**
     * addBookingRequest()
     * Accepts booking request from customer.
     * Request is stored in queue based on arrival order.
     */
    public void addBookingRequest(Scanner scanner) {

        System.out.print("Enter Guest Name: ");
        String guestName = scanner.nextLine();

        System.out.print("Enter Room Type (Single/Double/Suite): ");
        String roomType = scanner.nextLine();

        if (!roomInventory.containsKey(roomType)) {
            System.out.println("Invalid room type.");
            return;
        }

        Reservation reservation = new Reservation(guestName, roomType);

        // Add booking request to queue
        bookingQueue.offer(reservation);

        System.out.println("Booking request added to queue.");
    }


    /**
     * processBookingRequest()
     * Processes booking requests in FIFO order.
     */
    /**
     * processBookingRequest()
     * Processes booking requests in FIFO order
     * and allocates unique room IDs.
     */
    public void processBookingRequest() {

        if (bookingQueue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        Reservation reservation = bookingQueue.poll();
        String roomType = reservation.roomType;

        int available = roomInventory.get(roomType);

        if (available > 0) {

            // Generate unique room ID
            String roomId = generateRoomId(roomType);

            // Add to global booked set
            bookedRoomIds.add(roomId);

            // Add to assigned rooms map
            assignedRooms.get(roomType).add(roomId);

            // Update inventory immediately
            roomInventory.put(roomType, available - 1);

            System.out.println("Booking Confirmed!");
            System.out.println("Guest: " + reservation.guestName);
            System.out.println("Room Type: " + roomType);
            System.out.println("Assigned Room ID: " + roomId);

        } else {

            System.out.println("Room unavailable for "
                    + reservation.guestName +
                    ". Request returned to queue.");

            bookingQueue.offer(reservation);
        }
    }


    /**
     * viewBookingQueue()
     * Displays all pending booking requests.
     */
    public void viewBookingQueue() {

        System.out.println("\n--- Pending Booking Requests ---");

        if (bookingQueue.isEmpty()) {
            System.out.println("No pending bookings.");
            return;
        }

        for (Reservation r : bookingQueue) {

            System.out.println("Guest: " + r.guestName
                    + " | Room Type: " + r.roomType);
        }
    }
    /**
     * generateRoomId()
     * Generates unique room ID for a booking.
     * Example: SIN-101, DOU-203
     */
    private String generateRoomId(String roomType) {

        String prefix = roomType.substring(0, 3).toUpperCase();
        String roomId;

        do {
            int number = (int)(Math.random() * 900 + 100);
            roomId = prefix + "-" + number;

        } while (bookedRoomIds.contains(roomId));

        return roomId;
    }
    /**
     * viewAssignedRooms()
     * Displays allocated room IDs per room type.
     */
    public void viewAssignedRooms() {

        System.out.println("\n--- Allocated Rooms ---");

        for (Map.Entry<String, HashSet<String>> entry : assignedRooms.entrySet()) {

            String roomType = entry.getKey();
            HashSet<String> rooms = entry.getValue();

            System.out.println(roomType + " Rooms: " + rooms);
        }
    }
}