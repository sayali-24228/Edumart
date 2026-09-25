import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonationRequestDAO {

    public boolean sendRequest(
            int donationId,
            int requesterId,
            String message) {

        String check =
                "SELECT id FROM donation_requests " +
                "WHERE donation_id = ? " +
                "AND requester_id = ? " +
                "AND status = 'Pending'";

        String insert =
                "INSERT INTO donation_requests " +
                "(donation_id, requester_id, message, status) " +
                "VALUES (?, ?, ?, 'Pending')";

        try (Connection con = DBConnection.getConnection()) {

            try (PreparedStatement ps = con.prepareStatement(check)) {
                ps.setInt(1, donationId);
                ps.setInt(2, requesterId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return false;
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setInt(1, donationId);
                ps.setInt(2, requesterId);
                ps.setString(3, message);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RequestData> getReceivedRequests(int donorId) {

        List<RequestData> list = new ArrayList<>();

        String query =
                "SELECT r.id, r.donation_id, r.message, r.status, " +
                "r.created_at, d.item_name, d.category, " +
                "d.condition_type, u.name AS requester_name, " +
                "u.email AS requester_email " +
                "FROM donation_requests r " +
                "INNER JOIN donations d ON r.donation_id = d.id " +
                "INNER JOIN users u ON r.requester_id = u.id " +
                "WHERE d.donor_id = ? " +
                "ORDER BY r.created_at DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)
        ) {
            ps.setInt(1, donorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RequestData data = new RequestData();

                    data.id = rs.getInt("id");
                    data.donationId = rs.getInt("donation_id");
                    data.message = rs.getString("message");
                    data.status = rs.getString("status");
                    data.createdAt = rs.getTimestamp("created_at");
                    data.itemName = rs.getString("item_name");
                    data.category = rs.getString("category");
                    data.condition = rs.getString("condition_type");
                    data.requesterName = rs.getString("requester_name");
                    data.requesterEmail = rs.getString("requester_email");

                    list.add(data);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateRequestStatus(
            int requestId,
            int donorId,
            String status) {

        if (!"Accepted".equalsIgnoreCase(status)
                && !"Rejected".equalsIgnoreCase(status)) {
            return false;
        }

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            String updateRequest =
                    "UPDATE donation_requests r " +
                    "INNER JOIN donations d ON r.donation_id = d.id " +
                    "SET r.status = ? " +
                    "WHERE r.id = ? " +
                    "AND d.donor_id = ? " +
                    "AND r.status = 'Pending'";

            try (PreparedStatement ps =
                         con.prepareStatement(updateRequest)) {

                ps.setString(1, status);
                ps.setInt(2, requestId);
                ps.setInt(3, donorId);

                if (ps.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }

            if ("Accepted".equalsIgnoreCase(status)) {

                String claimDonation =
                        "UPDATE donations d " +
                        "INNER JOIN donation_requests r " +
                        "ON d.id = r.donation_id " +
                        "SET d.status = 'Claimed' " +
                        "WHERE r.id = ? " +
                        "AND d.donor_id = ? " +
                        "AND d.status = 'Available'";

                try (PreparedStatement ps =
                             con.prepareStatement(claimDonation)) {

                    ps.setInt(1, requestId);
                    ps.setInt(2, donorId);

                    if (ps.executeUpdate() == 0) {
                        con.rollback();
                        return false;
                    }
                }

                String rejectOtherRequests =
                        "UPDATE donation_requests r " +
                        "INNER JOIN donation_requests accepted " +
                        "ON accepted.donation_id = r.donation_id " +
                        "SET r.status = 'Rejected' " +
                        "WHERE accepted.id = ? " +
                        "AND r.id <> accepted.id " +
                        "AND r.status = 'Pending'";

                try (PreparedStatement ps =
                             con.prepareStatement(rejectOtherRequests)) {

                    ps.setInt(1, requestId);
                    ps.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class RequestData {
        public int id;
        public int donationId;
        public String itemName;
        public String category;
        public String condition;
        public String requesterName;
        public String requesterEmail;
        public String message;
        public String status;
        public Timestamp createdAt;
    }
}
