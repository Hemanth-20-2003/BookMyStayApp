# BookMyStayApp
## UC1: Room Inventory Setup & Management
This use case manages hotel room inventory using HashMaps to store room types, available counts, and pricing information. The system initializes different room categories and allows the admin to update counts or prices dynamically while maintaining data consistency. This ensures a centralized and efficient way to manage room availability.

## UC2: Room Search & Availability Check
This use case allows guests to search available room types and view pricing information without modifying the inventory. The system performs a fast lookup from HashMaps to display only rooms that are currently available. This ensures accurate availability information and prevents booking attempts for unavailable rooms.

## UC3: Booking Request (First-Come-First-Served)
This use case processes booking requests using a Queue data structure that follows the FIFO principle. Each reservation request is placed in the queue and processed in the order it was received, ensuring fair allocation during high-demand scenarios. This approach maintains predictable booking order and prevents request conflicts.

## UC4: Reservation Confirmation & Room Allocation
This use case confirms reservations and allocates rooms using a combination of HashSet and HashMap data structures. A unique room ID is assigned and stored in a set to prevent duplication, while the mapping between room types and assigned rooms is maintained in a HashMap. This ensures that no room is double-booked and inventory remains synchronized.

## UC5: Add-On Service Selection
This use case allows guests to attach additional services such as breakfast, spa, or airport pickup to their reservations. The system maps reservation IDs to a list of selected services, enabling multiple services to be linked with a single booking. This flexible structure supports easy expansion of future services.
