import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize sample parking slots
        List<Slot> slots = new ArrayList<>();
        slots.add(new Slot("C01", "CAR"));
        slots.add(new Slot("C02", "CAR"));
        slots.add(new Slot("B01", "BIKE"));

        // 2. Instantiate your services
        SlotAllocationService slotService = new SlotAllocationService(slots);
        TicketService ticketService = new TicketService();
        BillingService billingService = new BillingService();

        System.out.println("====================================");
        System.out.println("      PARKING ENTRY SIMULATION     ");
        System.out.println("====================================");
        
        // Simulating Car Entry
        String vehicleType = "CAR";
        String vehicleNo = "MH-12-AB-1234";

        Slot allocatedSlot = slotService.findAvailableSlot(vehicleType);
        
        if (allocatedSlot != null) {
            Ticket ticket = ticketService.generateTicket(vehicleNo, vehicleType, allocatedSlot.getSlotId());
            System.out.println("Vehicle Number  : " + ticket.getVehicleNumber());
            System.out.println("Vehicle Type    : " + ticket.getVehicleType());
            System.out.println("Allocated Slot  : " + ticket.getSlotId());
            System.out.println("Ticket Generated: " + ticket.getTicketId());
            System.out.println("Entry Time      : " + ticket.getEntryTime());

            System.out.println("\n====================================");
            System.out.println("      PARKING EXIT SIMULATION      ");
            System.out.println("====================================");

            // Simulate 2.5 hours pass (150 mins) -> Billed as 3 full hours
            ticket.setExitTime(ticket.getEntryTime().plusMinutes(150));
            
            double totalFee = billingService.calculateFee(
                ticket.getVehicleType(), 
                ticket.getEntryTime(), 
                ticket.getExitTime()
            );
            ticket.setFee(totalFee);

            // Release slot back to available pool
            slotService.freeSlot(ticket.getSlotId());

            System.out.println("Ticket ID      : " + ticket.getTicketId());
            System.out.println("Exit Time      : " + ticket.getExitTime());
            System.out.println("Total Amount   : ₹" + ticket.getFee());
            System.out.println("Slot Status    : " + ticket.getSlotId() + " is now FREED.");
            System.out.println("====================================");
        } else {
            System.out.println("NO SLOT AVAILABLE FOR VEHICLE TYPE: " + vehicleType);
        }
    }
}