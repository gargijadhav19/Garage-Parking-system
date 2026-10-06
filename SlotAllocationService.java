import java.util.List;
import java.util.Optional;

public class SlotAllocationService {
    private List<Slot> slots;

    public SlotAllocationService(List<Slot> slots) {
        this.slots = slots;
    }

    /**
     * Finds the first available slot for the given vehicle type 
     * and updates its status to OCCUPIED.
     */
    public synchronized Slot findAvailableSlot(String vehicleType) {
        Optional<Slot> availableSlot = slots.stream()
                .filter(s -> s.getVehicleType().equalsIgnoreCase(vehicleType))
                .filter(s -> !s.isOccupied())
                .findFirst();

        if (availableSlot.isPresent()) {
            Slot slot = availableSlot.get();
            slot.setOccupied(true); // Change status to OCCUPIED
            return slot;
        }
        
        return null; // No open slot available
    }

    /**
     * Frees up a slot when a vehicle exits
     */
    public void freeSlot(String slotId) {
        for (Slot slot : slots) {
            if (slot.getSlotId().equalsIgnoreCase(slotId)) {
                slot.setOccupied(false);
                break;
            }
        }
    }
}