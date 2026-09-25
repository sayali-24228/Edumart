import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonationDAO {

    // ==========================================
    // ADD DONATION
    // ==========================================

    public boolean addDonation(
            int donorId,
            String itemName,
            String description,
            String category,
            String condition,
            String imagePath) {

        String query =
                "INSERT INTO donations " +
                "(donor_id, item_name, description, category, " +
                "condition_type, image_path) " +
                "VALUES (?, ?, ?, ?, ?, ?)";


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if (con == null) {
                return false;
            }


            try (
                    PreparedStatement ps =
                            con.prepareStatement(query)
            ) {

                ps.setInt(
                        1,
                        donorId
                );

                ps.setString(
                        2,
                        itemName
                );

                ps.setString(
                        3,
                        description
                );

                ps.setString(
                        4,
                        category
                );

                ps.setString(
                        5,
                        condition
                );

                ps.setString(
                        6,
                        imagePath
                );


                return ps.executeUpdate() > 0;
            }


        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // GET AVAILABLE DONATIONS
    // ==========================================

    public List<DonationData> getAvailableDonations(
            int currentUserId) {

        List<DonationData> list =
                new ArrayList<>();


        String query =
                "SELECT d.*, u.name AS donor_name " +
                "FROM donations d " +
                "INNER JOIN users u " +
                "ON d.donor_id = u.id " +
                "WHERE d.status = 'Available' " +
                "AND d.donor_id != ? " +
                "ORDER BY d.created_at DESC";


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if (con == null) {
                return list;
            }


            try (
                    PreparedStatement ps =
                            con.prepareStatement(query)
            ) {

                ps.setInt(
                        1,
                        currentUserId
                );


                try (
                        ResultSet rs =
                                ps.executeQuery()
                ) {

                    while (rs.next()) {

                        DonationData data =
                                new DonationData();

                        data.id =
                                rs.getInt("id");

                        data.donorId =
                                rs.getInt("donor_id");

                        data.donorName =
                                rs.getString(
                                        "donor_name"
                                );

                        data.itemName =
                                rs.getString(
                                        "item_name"
                                );

                        data.description =
                                rs.getString(
                                        "description"
                                );

                        data.category =
                                rs.getString(
                                        "category"
                                );

                        data.condition =
                                rs.getString(
                                        "condition_type"
                                );

                        data.imagePath =
                                rs.getString(
                                        "image_path"
                                );


                        list.add(
                                data
                        );
                    }
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();
        }


        return list;
    }


    // ==========================================
    // CLAIM DONATION
    // ==========================================

    public boolean claimDonation(
            int donationId) {

        String query =
                "UPDATE donations " +
                "SET status = 'Claimed' " +
                "WHERE id = ? " +
                "AND status = 'Available'";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(query)
        ) {

            ps.setInt(
                    1,
                    donationId
            );


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // INNER DATA CLASS
    // ==========================================

    public static class DonationData {

        int id;
        int donorId;

        String donorName;
        String itemName;
        String description;
        String category;
        String condition;
        String imagePath;
    }
}