import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRequestDAO {

    // ==========================================
    // SEND REQUEST
    // ==========================================

    public boolean sendRequest(
            int exchangeId,
            int requesterId,
            String message) {

        String query =
                "INSERT INTO exchange_requests " +
                "(exchange_id, requester_id, message, status) " +
                "VALUES (?, ?, ?, 'Pending')";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(query)
        ) {

            if (con == null) {
                return false;
            }

            ps.setInt(1, exchangeId);
            ps.setInt(2, requesterId);
            ps.setString(3, message);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // GET REQUESTS RECEIVED BY OWNER
    // ==========================================

    public List<RequestData> getReceivedRequests(
            int ownerId) {

        List<RequestData> list =
                new ArrayList<>();

        String query =
                "SELECT r.id, r.exchange_id, " +
                "r.message, r.status, r.created_at, " +
                "e.offered_item, e.wanted_item, " +
                "u.name AS requester_name, " +
                "u.email AS requester_email " +
                "FROM exchange_requests r " +
                "INNER JOIN exchanges e " +
                "ON r.exchange_id = e.id " +
                "INNER JOIN users u " +
                "ON r.requester_id = u.id " +
                "WHERE e.owner_id = ? " +
                "ORDER BY r.created_at DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(query)
        ) {

            if (con == null) {
                return list;
            }

            ps.setInt(1, ownerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    RequestData data =
                            new RequestData();

                    data.id =
                            rs.getInt("id");

                    data.exchangeId =
                            rs.getInt("exchange_id");

                    data.message =
                            rs.getString("message");

                    data.status =
                            rs.getString("status");

                    data.offeredItem =
                            rs.getString("offered_item");

                    data.wantedItem =
                            rs.getString("wanted_item");

                    data.requesterName =
                            rs.getString("requester_name");

                    data.requesterEmail =
                            rs.getString("requester_email");

                    list.add(data);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }


    // ==========================================
    // UPDATE REQUEST
    // ==========================================

    public boolean updateRequestStatus(
            int requestId,
            int ownerId,
            String status) {

        String query =
                "UPDATE exchange_requests r " +
                "INNER JOIN exchanges e " +
                "ON r.exchange_id = e.id " +
                "SET r.status = ? " +
                "WHERE r.id = ? " +
                "AND e.owner_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(query)
        ) {

            if (con == null) {
                return false;
            }

            ps.setString(1, status);
            ps.setInt(2, requestId);
            ps.setInt(3, ownerId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // REQUEST DATA
    // ==========================================

    public static class RequestData {

        public int id;

        public int exchangeId;

        public String message;

        public String status;

        public String offeredItem;

        public String wantedItem;

        public String requesterName;

        public String requesterEmail;
    }
}