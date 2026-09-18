import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {

    // ==========================================
    // ADD FAVORITE
    // ==========================================

    public boolean addFavorite(
            int userId,
            int productId) {

        // First check if it already exists
        if (isFavorite(userId, productId)) {
            return true;
        }

        String query =
                "INSERT INTO favorites " +
                "(user_id, product_id) " +
                "VALUES (?, ?)";


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
                        userId
                );

                ps.setInt(
                        2,
                        productId
                );


                return ps.executeUpdate() > 0;
            }


        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // REMOVE FAVORITE
    // ==========================================

    public boolean removeFavorite(
            int userId,
            int productId) {

        String query =
                "DELETE FROM favorites " +
                "WHERE user_id = ? " +
                "AND product_id = ?";


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
                        userId
                );

                ps.setInt(
                        2,
                        productId
                );


                return ps.executeUpdate() > 0;
            }


        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // CHECK FAVORITE
    // ==========================================

    public boolean isFavorite(
            int userId,
            int productId) {

        String query =
                "SELECT id " +
                "FROM favorites " +
                "WHERE user_id = ? " +
                "AND product_id = ?";


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
                        userId
                );

                ps.setInt(
                        2,
                        productId
                );


                try (
                        ResultSet rs =
                                ps.executeQuery()
                ) {

                    return rs.next();
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // GET FAVORITE PRODUCTS
    // ==========================================

    public List<Product> getFavoriteProducts(
            int userId) {

        List<Product> products =
                new ArrayList<>();


        String query =
                "SELECT p.* " +
                "FROM favorites f " +
                "INNER JOIN products p " +
                "ON f.product_id = p.id " +
                "WHERE f.user_id = ? " +
                "ORDER BY f.id DESC";


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if (con == null) {
                return products;
            }


            try (
                    PreparedStatement ps =
                            con.prepareStatement(query)
            ) {

                ps.setInt(
                        1,
                        userId
                );


                try (
                        ResultSet rs =
                                ps.executeQuery()
                ) {

                    while (rs.next()) {

                        Product product =
                                new Product();


                        product.setId(
                                rs.getInt("id")
                        );


                        product.setSellerId(
                                rs.getInt(
                                        "seller_id"
                                )
                        );


                        product.setName(
                                rs.getString(
                                        "name"
                                )
                        );


                        product.setDescription(
                                rs.getString(
                                        "description"
                                )
                        );


                        product.setCategory(
                                rs.getString(
                                        "category"
                                )
                        );


                        product.setPrice(
                                rs.getDouble(
                                        "price"
                                )
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


                        products.add(
                                product
                        );
                    }
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();
        }


        return products;
    }


    // ==========================================
    // COUNT FAVORITES
    // ==========================================

    public int getFavoriteCount(
            int userId) {

        String query =
                "SELECT COUNT(*) " +
                "FROM favorites " +
                "WHERE user_id = ?";


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if (con == null) {
                return 0;
            }


            try (
                    PreparedStatement ps =
                            con.prepareStatement(query)
            ) {

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
            }


        } catch (SQLException e) {

            e.printStackTrace();
        }


        return 0;
    }
}