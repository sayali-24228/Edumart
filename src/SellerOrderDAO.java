import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerOrderDAO {


    // ==========================================
    // GET ORDERS FOR SELLER
    // ==========================================

    public List<SellerOrderData> getSellerOrders(int sellerId) {

        List<SellerOrderData> orders =
                new ArrayList<>();


        String query =
                "SELECT "
                + "o.id AS order_id, "
                + "o.product_id, "
                + "o.quantity, "
                + "o.total_price, "
                + "o.order_status, "
                + "o.order_date, "
                + "p.name AS product_name, "
                + "u.name AS buyer_name, "
                + "u.email AS buyer_email "
                + "FROM orders o "
                + "INNER JOIN products p "
                + "ON o.product_id = p.id "
                + "INNER JOIN users u "
                + "ON o.buyer_id = u.id "
                + "WHERE p.seller_id = ? "
                + "ORDER BY o.order_date DESC";


        try (Connection con =
                     DBConnection.getConnection()) {

            if (con == null) {

                return orders;
            }


            try (PreparedStatement ps =
                         con.prepareStatement(query)) {

                ps.setInt(
                        1,
                        sellerId
                );


                try (ResultSet rs =
                             ps.executeQuery()) {

                    while (rs.next()) {

                        SellerOrderData order =
                                new SellerOrderData();


                        order.setOrderId(
                                rs.getInt(
                                        "order_id"
                                )
                        );


                        order.setProductId(
                                rs.getInt(
                                        "product_id"
                                )
                        );


                        order.setProductName(
                                rs.getString(
                                        "product_name"
                                )
                        );


                        order.setBuyerName(
                                rs.getString(
                                        "buyer_name"
                                )
                        );


                        order.setBuyerEmail(
                                rs.getString(
                                        "buyer_email"
                                )
                        );


                        order.setQuantity(
                                rs.getInt(
                                        "quantity"
                                )
                        );


                        order.setTotalPrice(
                                rs.getDouble(
                                        "total_price"
                                )
                        );


                        order.setStatus(
                                rs.getString(
                                        "order_status"
                                )
                        );


                        order.setOrderDate(
                                rs.getTimestamp(
                                        "order_date"
                                )
                        );


                        orders.add(
                                order
                        );
                    }
                }
            }


        } catch (SQLException e) {

            System.out.println(
                    "Error loading seller orders:"
            );

            e.printStackTrace();
        }


        return orders;
    }


    // ==========================================
    // UPDATE ORDER STATUS
    // ==========================================

    public boolean updateOrderStatus(
            int orderId,
            int sellerId,
            String status) {


        String query =
                "UPDATE orders o "
                + "INNER JOIN products p "
                + "ON o.product_id = p.id "
                + "SET o.order_status = ? "
                + "WHERE o.id = ? "
                + "AND p.seller_id = ?";


        try (Connection con =
                     DBConnection.getConnection()) {

            if (con == null) {

                return false;
            }


            try (PreparedStatement ps =
                         con.prepareStatement(query)) {


                ps.setString(
                        1,
                        status
                );


                ps.setInt(
                        2,
                        orderId
                );


                ps.setInt(
                        3,
                        sellerId
                );


                int rows =
                        ps.executeUpdate();


                return rows > 0;
            }


        } catch (SQLException e) {

            System.out.println(
                    "Error updating order status:"
            );

            e.printStackTrace();

            return false;
        }
    }
}