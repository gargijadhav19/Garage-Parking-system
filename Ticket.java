import java.time.LocalDateTime;

public class Ticket {
    private String ticketId;
    private String vehicleNumber;
    private String vehicleType;
    private String slotId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;

    public Ticket(String ticketId, String vehicleNumber, String vehicleType, String slotId, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.slotId = slotId;
        this.entryTime = entryTime;
        this.fee = 0.0;
    }

    public String getTicketId() { return ticketId; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getVehicleType() { return vehicleType; }
    public String getSlotId() { return slotId; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }
    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }
}