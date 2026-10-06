import java.time.LocalDateTime;
import java.util.List;

public class DBTestMain {
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("   GARAGE PARKING SYSTEM — DB BACKEND TEST    ");
        System.out.println("==============================================\n");

        // 1. Initialize DAOs
        SlotDAO slotDAO = new SlotDAO();
        TicketDAO ticketDAO = new TicketDAO();

        // 2. Fetch active slots from MySQL
        List<Slot> slotsFromDB = slotDAO.getAllSlots();
        System.out.println("Fetched " + slotsFromDB.size() + " slots from MySQL.");

        // 3. Initialize Services
        SlotAllocationService slotService = new SlotAllocationService(slotsFromDB);
        TicketService ticketService = new TicketService();
        BillingService billingService = new BillingService();

        // --- SIMULATE VEHICLE ENTRY ---
        String vehicleNo = "MH-12-PQ-9999";
        String vehicleType = "CAR";

        Slot slot = slotService.findAvailableSlot(vehicleType);
        if (slot != null) {
            // Update slot state in MySQL
            slotDAO.updateSlotStatus(slot.getSlotId(), true);

            // Generate and save Ticket in MySQL
            Ticket ticket = ticketService.generateTicket(vehicleNo, vehicleType, slot.getSlotId());
            ticketDAO.saveTicket(ticket);

            System.out.println("\n[ENTRY SUCCESSFUL]");
            System.out.println("Ticket ID      : " + ticket.getTicketId());
            System.out.println("Vehicle Number : " + ticket.getVehicleNumber());
            System.out.println("Allocated Slot : " + ticket.getSlotId());
            System.out.println("Entry Time     : " + ticket.getEntryTime());

            // --- SIMULATE VEHICLE EXIT ---
            System.out.println("\n----------------------------------------------");
            System.out.println("          SIMULATING EXIT PROCEDURE          ");
            System.out.println("----------------------------------------------");

            // Simulate 3 hours duration
            LocalDateTime exitTime = ticket.getEntryTime().plusHours(3);
            double totalFee = billingService.calculateFee(vehicleType, ticket.getEntryTime(), exitTime);

            // Update Ticket exit status and free slot in MySQL
            ticketDAO.updateExitAndFee(ticket.getTicketId(), exitTime, totalFee);
            slotDAO.updateSlotStatus(slot.getSlotId(), false);
            slotService.freeSlot(slot.getSlotId());

            System.out.println("[EXIT SUCCESSFUL]");
            System.out.println("Ticket ID    : " + ticket.getTicketId());
            System.out.println("Exit Time    : " + exitTime);
            System.out.println("Total Amount : Rs. " + totalFee);
            System.out.println("Status       : Slot " + slot.getSlotId() + " freed in DB.");
        } else {
            System.out.println("No available slot found for " + vehicleType);
        }

        System.out.println("\n==============================================");
        System.out.println("             TEST RUN COMPLETED               ");
        System.out.println("==============================================");
    }
}