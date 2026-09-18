import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {

    // ==========================================
    // PRODUCTS LISTED
    // ==========================================

    public int getProductsListed(int userId) {

        return getCount(
                "SELECT COUNT(*) FROM products WHERE seller_id = ?",
                userId
        );
    }


    // ==========================================
    // ORDERS RECEIVED
    // ==========================================

    public int getOrdersReceived(int userId) {

        String query =
                "SELECT COUNT(*) " +
                "FROM orders o " +
                "INNER JOIN products p " +
                "ON o.product_id = p.id " +
                "WHERE p.seller_id = ?";

        return getCount(
                query,
                userId
        );
    }


    // ==========================================
    // FAVORITES
    // ==========================================

    public int getFavorites(int userId) {

        return getCount(
                "SELECT COUNT(*) FROM favorites WHERE user_id = ?",
                userId
        );
    }


    // ==========================================
    // CART ITEMS
    // ==========================================

    public int getCartItems(int userId) {

        String query =
                "SELECT COALESCE(SUM(quantity), 0) " +
                "FROM cart WHERE user_id = ?";

        return getCount(
                query,
                userId
        );
    }


    // ==========================================
    // DONATIONS
    // ==========================================

    public int getDonations(int userId) {

        return getCount(
                "SELECT COUNT(*) FROM donations WHERE donor_id = ?",
                userId
        );
    }


    // ==========================================
    // EXCHANGE LISTINGS
    // ==========================================

    public int getExchangeListings(int userId) {

        return getCount(
                "SELECT COUNT(*) FROM exchanges WHERE owner_id = ?",
                userId
        );
    }


    // ==========================================
    // HELPER
    // ==========================================

    private int getCount(
            String query,
            int userId) {

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(query)
        ) {

            if (con == null) {
                return 0;
            }

            ps.setInt(
                    1,
                    userId
            );

            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }
}