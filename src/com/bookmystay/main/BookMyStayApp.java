package com.bookmystay.main;

import java.util.Scanner;
import com.bookmystay.service.RoomInventoryService;

/**
 * BookMyStayApp
 * 
 * Main application class for the hotel booking system.
 * Provides menu options for both Admin and Customer operations.
 */
public class BookMyStayApp {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		RoomInventoryService service = new RoomInventoryService();

		int choice;

		do {

			System.out.println("\n=== Hotel Booking System ===");

			System.out.println("1. Show Availability / Update Inventory (Admin)");
			System.out.println("2. Search Available Rooms (Customer)");
			System.out.println("3. Add Booking Request (Customer)");
			System.out.println("4. Process Booking Request (System)");
			System.out.println("5. View Booking Queue (Admin)");
			System.out.println("6. View Assigned Rooms (Admin)");
			System.out.println("7. Add Service to Reservation (Guest)");
			System.out.println("8. Calculate Service Cost (Guest)");
			System.out.println("9. View Booking History (Admin)");
			System.out.println("10. Cancel Reservation (Admin)");
			System.out.println("11. Generate Report (Admin)");
			System.out.println("12. Exit");

			System.out.print("Enter your choice: ");

			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {

			case 1:
				service.showAvailability(scanner);
				break;

			case 2:
				service.searchAvailableRooms();
				break;

			case 3:
				service.addBookingRequest(scanner);
				break;

			case 4:
				service.processBookingRequest();
				break;

			case 5:
				service.viewBookingQueue();
				break;

			case 6:
				service.viewAssignedRooms();
				break;

			case 7:
				service.addServiceToReservation(scanner);
				break;

			case 8:
				service.calculateServiceCost(scanner);
				break;
				
			case 9:
			    service.viewBookingHistory();
			    break;

			case 10:
			    service.cancelReservation(scanner);
			    break;

			case 11:
			    service.generateReport();
			    break;

			case 12:
			    System.out.println("Exiting system...");
			    break;

			default:
				System.out.println("Invalid choice.");
			}

		} while (choice != 12);

		scanner.close();
	}
}