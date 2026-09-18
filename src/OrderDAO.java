import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {

    // ==========================================
    // PLACE ORDER FROM CART
    // ==========================================

    public boolean placeOrderFromCart(int userId) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {
                return false;
            }

            con.setAutoCommit(false);


            // ======================================
            // GET CART ITEMS
            // ======================================

            String cartQuery =
                    "SELECT "
                    + "c.product_id, "
                    + "c.quantity, "
                    + "p.price "
                    + "FROM cart c "
                    + "INNER JOIN products p "
                    + "ON c.product_id = p.id "
                    + "WHERE c.user_id = ?";


            try (
                    PreparedStatement cartPS =
                            con.prepareStatement(cartQuery)
            ) {

                cartPS.setInt(1, userId);


                try (
                        ResultSet rs =
                                cartPS.executeQuery()
                ) {

                    boolean hasItems = false;


                    while (rs.next()) {

                        hasItems = true;


                        int productId =
                                rs.getInt("product_id");


                        int quantity =
                                rs.getInt("quantity");


                        double price =
                                rs.getDouble("price");


                        double totalPrice =
                                price * quantity;


                        // ==================================
                        // INSERT ORDER
                        // ==================================

                        String orderQuery =
                                "INSERT INTO orders "
                                + "(buyer_id, product_id, quantity, total_price, order_status) "
                                + "VALUES (?, ?, ?, ?, ?)";


                        try (
                                PreparedStatement orderPS =
                                        con.prepareStatement(
                                                orderQuery
                                        )
                        ) {

                            orderPS.setInt(
                                    1,
                                    userId
                            );

                            orderPS.setInt(
                                    2,
                                    productId
                            );

                            orderPS.setInt(
                                    3,
                                    quantity
                            );

                            orderPS.setDouble(
                                    4,
                                    totalPrice
                            );

                            orderPS.setString(
                                    5,
                                    "Pending"
                            );

                            orderPS.executeUpdate();
                        }
                    }


                    if (!hasItems) {

                        con.rollback();

                        return false;
                    }
                }
            }


            // ======================================
            // CLEAR CART
            // ======================================

            String deleteCartQuery =
                    "DELETE FROM cart WHERE user_id = ?";


            try (
                    PreparedStatement deletePS =
                            con.prepareStatement(
                                    deleteCartQuery
                            )
            ) {

                deletePS.setInt(
                        1,
                        userId
                );

                deletePS.executeUpdate();
            }


            // ======================================
            // COMMIT
            // ======================================

            con.commit();


            return true;


        } catch (SQLException e) {

            e.printStackTrace();


            if (con != null) {

                try {
                    con.rollback();

                } catch (SQLException rollbackError) {

                    rollbackError.printStackTrace();
                }
            }


            return false;


        } finally {

            if (con != null) {

                try {

                    con.setAutoCommit(true);

                    con.close();

                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
    }
}