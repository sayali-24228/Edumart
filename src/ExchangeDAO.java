import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeDAO {

    // ==========================================
    // ADD EXCHANGE
    // ==========================================

    public boolean addExchange(
            int ownerId,
            String offeredItem,
            String offeredDescription,
            String category,
            String imagePath,
            String conditionType,
            String wantedItem,
            String wantedCategory,
            String wantedDescription,
            String additionalRequirements) {

        String query =
                "INSERT INTO exchanges " +
                "(owner_id, offered_item, offered_description, " +
                "wanted_item, wanted_description, category, " +
                "image_path, condition_type, wanted_category, " +
                "additional_requirements) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            if (con == null) {
                return false;
            }

            ps.setInt(1, ownerId);
            ps.setString(2, offeredItem);
            ps.setString(3, offeredDescription);
            ps.setString(4, wantedItem);
            ps.setString(5, wantedDescription);
            ps.setString(6, category);
            ps.setString(7, imagePath);
            ps.setString(8, conditionType);
            ps.setString(9, wantedCategory);
            ps.setString(10, additionalRequirements);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // GET AVAILABLE EXCHANGES
    // ==========================================

    public List<ExchangeData> getAvailableExchanges(
            int currentUserId) {

        List<ExchangeData> list =
                new ArrayList<>();

        String query =
                "SELECT e.*, u.name AS owner_name " +
                "FROM exchanges e " +
                "INNER JOIN users u " +
                "ON e.owner_id = u.id " +
                "WHERE e.status = 'Available' " +
                "AND e.owner_id != ? " +
                "ORDER BY e.created_at DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            if (con == null) {
                return list;
            }

            ps.setInt(1, currentUserId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    ExchangeData data =
                            new ExchangeData();

                    data.id =
                            rs.getInt("id");

                    data.ownerId =
                            rs.getInt("owner_id");

                    data.ownerName =
                            rs.getString("owner_name");

                    data.offeredItem =
                            rs.getString("offered_item");

                    data.offeredDescription =
                            rs.getString(
                                    "offered_description"
                            );

                    data.wantedItem =
                            rs.getString("wanted_item");

                    data.wantedDescription =
                            rs.getString(
                                    "wanted_description"
                            );

                    data.category =
                            rs.getString("category");

                    data.imagePath =
                            rs.getString("image_path");

                    data.conditionType =
                            rs.getString("condition_type");

                    data.wantedCategory =
                            rs.getString(
                                    "wanted_category"
                            );

                    data.additionalRequirements =
                            rs.getString(
                                    "additional_requirements"
                            );

                    list.add(data);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }


    // ==========================================
    // CLOSE EXCHANGE
    // ==========================================

    public boolean closeExchange(
            int exchangeId,
            int ownerId) {

        String query =
                "UPDATE exchanges " +
                "SET status = 'Closed' " +
                "WHERE id = ? " +
                "AND owner_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            if (con == null) {
                return false;
            }

            ps.setInt(1, exchangeId);
            ps.setInt(2, ownerId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // EXCHANGE DATA
    // ==========================================

    public static class ExchangeData {

        public int id;

        public int ownerId;

        public String ownerName;

        public String offeredItem;

        public String offeredDescription;

        public String wantedItem;

        public String wantedDescription;

        public String category;

        public String imagePath;

        public String conditionType;

        public String wantedCategory;

        public String additionalRequirements;
    }
}