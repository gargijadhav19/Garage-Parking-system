import java.time.Duration;
import java.time.LocalDateTime;

public class BillingService {

    /**
     * Calculates duration in hours (rounds up to the nearest whole hour)
     */
    public long calculateDurationInHours(LocalDateTime entryTime, LocalDateTime exitTime) {
        Duration duration = Duration.between(entryTime, exitTime);
        long minutes = duration.toMinutes();
        if (minutes <= 0) return 1; // Minimum charge is 1 hour
        return (long) Math.ceil((double) minutes / 60.0);
    }

    /**
     * Car: First hour = ₹30, Additional hour = ₹20
     * Bike: First hour = ₹20, Additional hour = ₹10
     */
    public double calculateFee(String vehicleType, LocalDateTime entryTime, LocalDateTime exitTime) {
        long hours = calculateDurationInHours(entryTime, exitTime);

        if (vehicleType.equalsIgnoreCase("CAR")) {
            return 30.0 + (hours - 1) * 20.0;
        } else if (vehicleType.equalsIgnoreCase("BIKE")) {
            return 20.0 + (hours - 1) * 10.0;
        }
        
        throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
    }
}