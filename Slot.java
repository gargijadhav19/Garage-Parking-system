public class Slot {
    private String slotId; // e.g., "C01", "B01"
    private String vehicleType; // "CAR", "BIKE"
    private boolean isOccupied;

    public Slot(String slotId, String vehicleType) {
        this.slotId = slotId;
        this.vehicleType = vehicleType;
        this.isOccupied = false;
    }

    public String getSlotId() { return slotId; }
    public String getVehicleType() { return vehicleType; }
    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { isOccupied = occupied; }
}