import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketService {
    // Generates unique ticket numbers starting from T0001, T0002...
    private final AtomicInteger ticketCounter = new AtomicInteger(1);

    /**
     * Generates a new ticket with auto-incremented ID and entry timestamp
     */
    public Ticket generateTicket(String vehicleNumber, String vehicleType, String slotId) {
        String ticketId = String.format("T%04d", ticketCounter.getAndIncrement());
        return new Ticket(ticketId, vehicleNumber, vehicleType, slotId, LocalDateTime.now());
    }
}