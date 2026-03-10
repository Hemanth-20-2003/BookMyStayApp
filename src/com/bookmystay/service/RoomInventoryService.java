package com.bookmystay.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.bookmystay.model.Reservation;
import com.bookmystay.model.Service;

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

	// Map of reservationId -> list of services
	private HashMap<String, List<Service>> reservationServices = new HashMap<>();

	// List to store booking history
	private List<Reservation> bookingHistory = new LinkedList<>();


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
			reservationServices.put(roomId, new LinkedList<>());
			// Add to global booked set
			bookedRoomIds.add(roomId);
			reservation.roomId = roomId;

			// store reservation in history
			bookingHistory.add(reservation);
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
	/**
	 * addServiceToReservation()
	 * Allows guest to attach optional services to a booking.
	 */
	public void addServiceToReservation(Scanner scanner) {

		System.out.print("Enter Reservation ID: ");
		String reservationId = scanner.nextLine();

		if (!reservationServices.containsKey(reservationId)) {
			System.out.println("Reservation not found.");
			return;
		}

		System.out.println("Available Services:");
		System.out.println("1. Breakfast (₹500)");
		System.out.println("2. Spa (₹1500)");
		System.out.println("3. Airport Pickup (₹800)");

		System.out.print("Select service: ");
		int choice = scanner.nextInt();
		scanner.nextLine();

		Service service = null;

		switch (choice) {

		case 1:
			service = new Service("Breakfast", 500);
			break;

		case 2:
			service = new Service("Spa", 1500);
			break;

		case 3:
			service = new Service("Airport Pickup", 800);
			break;

		default:
			System.out.println("Invalid service.");
			return;
		}

		reservationServices.get(reservationId).add(service);

		System.out.println("Service added to reservation.");
	}
	/**
	 * calculateServiceCost()
	 * Calculates total add-on service cost for a reservation.
	 */
	public void calculateServiceCost(Scanner scanner) {

		System.out.print("Enter Reservation ID: ");
		String reservationId = scanner.nextLine();

		if (!reservationServices.containsKey(reservationId)) {
			System.out.println("Reservation not found.");
			return;
		}

		List<Service> services = reservationServices.get(reservationId);

		double total = 0;

		System.out.println("\nServices Selected:");

		for (Service s : services) {
			System.out.println(s);
			total += s.price;
		}

		System.out.println("Total Additional Cost: ₹" + total);
	}
	
	/**
	 * viewBookingHistory()
	 * Displays all past and current reservations.
	 */
	public void viewBookingHistory() {

	    System.out.println("\n--- Booking History ---");

	    if (bookingHistory.isEmpty()) {
	        System.out.println("No reservations found.");
	        return;
	    }

	    for (Reservation r : bookingHistory) {
	        System.out.println(r);
	    }
	}
	/**
	 * cancelReservation()
	 * Allows admin to cancel an existing reservation.
	 */
	public void cancelReservation(Scanner scanner) {

	    System.out.print("Enter Room ID to cancel: ");
	    String roomId = scanner.nextLine();

	    for (Reservation r : bookingHistory) {

	        if (r.roomId.equals(roomId) && r.active) {

	            r.active = false;

	            // increase inventory back
	            int available = roomInventory.get(r.roomType);
	            roomInventory.put(r.roomType, available + 1);

	            System.out.println("Reservation cancelled successfully.");
	            return;
	        }
	    }

	    System.out.println("Reservation not found.");
	}
	/**
	 * generateReport()
	 * Shows statistics for admin reporting.
	 */
	public void generateReport() {

	    int totalBookings = bookingHistory.size();
	    int activeBookings = 0;
	    int cancelledBookings = 0;

	    for (Reservation r : bookingHistory) {

	        if (r.active)
	            activeBookings++;
	        else
	            cancelledBookings++;
	    }

	    System.out.println("\n--- Booking Report ---");
	    System.out.println("Total Bookings: " + totalBookings);
	    System.out.println("Active Bookings: " + activeBookings);
	    System.out.println("Cancelled Bookings: " + cancelledBookings);
	}
}