import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // ==========================================
    // ADD TO CART
    // ==========================================

    public boolean addToCart(int userId, int productId) {

        String checkQuery =
                "SELECT id, quantity FROM cart " +
                "WHERE user_id = ? AND product_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps =
                         con.prepareStatement(checkQuery)) {

                ps.setInt(1, userId);
                ps.setInt(2, productId);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    int currentQuantity =
                            rs.getInt("quantity");

                    String updateQuery =
                            "UPDATE cart SET quantity = ? " +
                            "WHERE user_id = ? AND product_id = ?";

                    try (PreparedStatement update =
                                 con.prepareStatement(updateQuery)) {

                        update.setInt(
                                1,
                                currentQuantity + 1
                        );

                        update.setInt(
                                2,
                                userId
                        );

                        update.setInt(
                                3,
                                productId
                        );

                        int rows =
                                update.executeUpdate();

                        return rows > 0;
                    }

                } else {

                    String insertQuery =
                            "INSERT INTO cart " +
                            "(user_id, product_id, quantity) " +
                            "VALUES (?, ?, 1)";

                    try (PreparedStatement insert =
                                 con.prepareStatement(insertQuery)) {

                        insert.setInt(
                                1,
                                userId
                        );

                        insert.setInt(
                                2,
                                productId
                        );

                        int rows =
                                insert.executeUpdate();

                        return rows > 0;
                    }
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // GET CART PRODUCTS
    // ==========================================

    public List<Product> getCartProducts(int userId) {

        List<Product> products =
                new ArrayList<>();

        String query =
                "SELECT p.* " +
                "FROM cart c " +
                "INNER JOIN products p " +
                "ON c.product_id = p.id " +
                "WHERE c.user_id = ? " +
                "ORDER BY c.id DESC";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return products;
            }

            try (PreparedStatement ps =
                         con.prepareStatement(query)) {

                ps.setInt(
                        1,
                        userId
                );

                ResultSet rs =
                        ps.executeQuery();

                while (rs.next()) {

                    Product product =
                            new Product();

                    product.setId(
                            rs.getInt("id")
                    );

                    product.setSellerId(
                            rs.getInt("seller_id")
                    );

                    product.setName(
                            rs.getString("name")
                    );

                    product.setDescription(
                            rs.getString("description")
                    );

                    product.setCategory(
                            rs.getString("category")
                    );

                    product.setPrice(
                            rs.getDouble("price")
                    );

                    product.setConditionType(
                            rs.getString(
                                    "condition_type"
                            )
                    );

                    product.setImagePath(
                            rs.getString(
                                    "image_path"
                            )
                    );

                    products.add(product);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return products;
    }


    // ==========================================
    // GET QUANTITY
    // ==========================================

    public int getQuantity(
            int userId,
            int productId) {

        String query =
                "SELECT quantity FROM cart " +
                "WHERE user_id = ? AND product_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return 0;
            }

            try (PreparedStatement ps =
                         con.prepareStatement(query)) {

                ps.setInt(
                        1,
                        userId
                );

                ps.setInt(
                        2,
                        productId
                );

                ResultSet rs =
                        ps.executeQuery();

                if (rs.next()) {

                    return rs.getInt(
                            "quantity"
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }


    // ==========================================
    // UPDATE QUANTITY
    // ==========================================

    public boolean updateQuantity(
            int userId,
            int productId,
            int quantity) {

        if (quantity <= 0) {

            return removeFromCart(
                    userId,
                    productId
            );
        }

        String query =
                "UPDATE cart " +
                "SET quantity = ? " +
                "WHERE user_id = ? AND product_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps =
                         con.prepareStatement(query)) {

                ps.setInt(
                        1,
                        quantity
                );

                ps.setInt(
                        2,
                        userId
                );

                ps.setInt(
                        3,
                        productId
                );

                int rows =
                        ps.executeUpdate();

                return rows > 0;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // REMOVE FROM CART
    // ==========================================

    public boolean removeFromCart(
            int userId,
            int productId) {

        String query =
                "DELETE FROM cart " +
                "WHERE user_id = ? AND product_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps =
                         con.prepareStatement(query)) {

                ps.setInt(
                        1,
                        userId
                );

                ps.setInt(
                        2,
                        productId
                );

                int rows =
                        ps.executeUpdate();

                return rows > 0;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // CLEAR CART
    // ==========================================

    public boolean clearCart(int userId) {

        String query =
                "DELETE FROM cart WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps =
                         con.prepareStatement(query)) {

                ps.setInt(
                        1,
                        userId
                );

                ps.executeUpdate();

                return true;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
}