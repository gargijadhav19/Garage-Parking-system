import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SlotDAO {

    public List<Slot> getAllSlots() {
        List<Slot> slots = new ArrayList<>();
        String query = "SELECT * FROM parking_slots";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Slot slot = new Slot(
                    rs.getString("slot_id"),
                    rs.getString("vehicle_type")
                );
                slot.setOccupied(rs.getBoolean("is_occupied"));
                slots.add(slot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }

    public void updateSlotStatus(String slotId, boolean isOccupied) {
        String query = "UPDATE parking_slots SET is_occupied = ? WHERE slot_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, isOccupied);
            pstmt.setString(2, slotId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}