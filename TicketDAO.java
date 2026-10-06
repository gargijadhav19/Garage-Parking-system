import java.sql.*;

public class TicketDAO {

    public void saveTicket(Ticket ticket) {
        String query = "INSERT INTO parking_tickets (ticket_id, vehicle_number, vehicle_type, slot_id, entry_time, status) VALUES (?, ?, ?, ?, ?, 'ACTIVE')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, ticket.getTicketId());
            pstmt.setString(2, ticket.getVehicleNumber());
            pstmt.setString(3, ticket.getVehicleType());
            pstmt.setString(4, ticket.getSlotId());
            pstmt.setTimestamp(5, Timestamp.valueOf(ticket.getEntryTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExitAndFee(String ticketId, java.time.LocalDateTime exitTime, double fee) {
        String query = "UPDATE parking_tickets SET exit_time = ?, fee = ?, status = 'COMPLETED' WHERE ticket_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(exitTime));
            pstmt.setDouble(2, fee);
            pstmt.setString(3, ticketId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}