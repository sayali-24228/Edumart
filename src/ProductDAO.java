import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // ==========================================
    // ADD PRODUCT
    // ==========================================

    public boolean addProduct(Product product) {

        String query =
                "INSERT INTO products " +
                "(seller_id, name, description, category, price, condition_type, image_path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection()
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
                        product.getSellerId()
                );

                ps.setString(
                        2,
                        product.getName()
                );

                ps.setString(
                        3,
                        product.getDescription()
                );

                ps.setString(
                        4,
                        product.getCategory()
                );

                ps.setDouble(
                        5,
                        product.getPrice()
                );

                ps.setString(
                        6,
                        product.getConditionType()
                );

                ps.setString(
                        7,
                        product.getImagePath()
                );

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // GET ALL PRODUCTS
    // ==========================================

    public List<Product> getAllProducts() {

        String query =
                "SELECT * FROM products " +
                "ORDER BY created_at DESC";

        return executeQueryWithoutUser(query);
    }


    // ==========================================
    // GET ALL PRODUCTS
    // EXCEPT CURRENT USER'S PRODUCTS
    // ==========================================

    public List<Product> getAllProducts(
            int currentUserId) {

        String query =
                "SELECT * FROM products " +
                "WHERE seller_id != ? " +
                "ORDER BY created_at DESC";

        return executeProductQuery(
                query,
                currentUserId
        );
    }


    // ==========================================
    // SEARCH + CATEGORY + SORT
    // ==========================================

    public List<Product> searchProducts(
            int currentUserId,
            String searchText,
            String category,
            String sortOption) {

        StringBuilder query =
                new StringBuilder(
                        "SELECT * FROM products " +
                        "WHERE seller_id != ? "
                );


        // ======================================
        // SEARCH
        // ======================================

        if (
                searchText != null &&
                !searchText.trim().isEmpty()
        ) {

            query.append(
                    "AND (" +
                    "name LIKE ? " +
                    "OR description LIKE ? " +
                    "OR category LIKE ?" +
                    ") "
            );
        }


        // ======================================
        // CATEGORY
        // ======================================

        if (
                category != null &&
                !category.equals("All Categories")
        ) {

            query.append(
                    "AND category = ? "
            );
        }


        // ======================================
        // SORT
        // ======================================

        if (
                sortOption == null ||
                sortOption.equals("Newest")
        ) {

            query.append(
                    "ORDER BY created_at DESC"
            );

        } else if (
                sortOption.equals(
                        "Price: Low to High"
                )
        ) {

            query.append(
                    "ORDER BY price ASC"
            );

        } else if (
                sortOption.equals(
                        "Price: High to Low"
                )
        ) {

            query.append(
                    "ORDER BY price DESC"
            );

        } else if (
                sortOption.equals(
                        "Name: A to Z"
                )
        ) {

            query.append(
                    "ORDER BY name ASC"
            );

        } else {

            query.append(
                    "ORDER BY created_at DESC"
            );
        }


        List<Product> products =
                new ArrayList<>();


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if (con == null) {
                return products;
            }


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    query.toString()
                            )
            ) {

                int index = 1;


                // CURRENT USER

                ps.setInt(
                        index++,
                        currentUserId
                );


                // SEARCH PARAMETERS

                if (
                        searchText != null &&
                        !searchText.trim().isEmpty()
                ) {

                    String search =
                            "%" +
                            searchText.trim() +
                            "%";


                    ps.setString(
                            index++,
                            search
                    );

                    ps.setString(
                            index++,
                            search
                    );

                    ps.setString(
                            index++,
                            search
                    );
                }


                // CATEGORY

                if (
                        category != null &&
                        !category.equals(
                                "All Categories"
                        )
                ) {

                    ps.setString(
                            index++,
                            category
                    );
                }


                try (
                        ResultSet rs =
                                ps.executeQuery()
                ) {

                    while (rs.next()) {

                        products.add(
                                createProductFromResultSet(
                                        rs
                                )
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
    // EXECUTE QUERY WITH USER ID
    // ==========================================

    private List<Product> executeProductQuery(
            String query,
            int currentUserId) {

        List<Product> products =
                new ArrayList<>();


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if (con == null) {
                return products;
            }


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    query
                            )
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

                        products.add(
                                createProductFromResultSet(
                                        rs
                                )
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
    // EXECUTE QUERY WITHOUT USER ID
    // ==========================================

    private List<Product> executeQueryWithoutUser(
            String query) {

        List<Product> products =
                new ArrayList<>();


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if (con == null) {
                return products;
            }


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    query
                            );

                    ResultSet rs =
                            ps.executeQuery()
            ) {

                while (rs.next()) {

                    products.add(
                            createProductFromResultSet(
                                    rs
                            )
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }


        return products;
    }


    // ==========================================
    // RESULT SET → PRODUCT OBJECT
    // ==========================================

    private Product createProductFromResultSet(
            ResultSet rs)
            throws SQLException {

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
                rs.getString("condition_type")
        );


        product.setImagePath(
                rs.getString("image_path")
        );


        return product;
    }
        // ==========================================
    // GET RELATED PRODUCTS
    // ==========================================

    public List<Product> getRelatedProducts(
            int productId,
            String category,
            int limit) {

        List<Product> products = new ArrayList<>();

        String query =
                "SELECT * FROM products " +
                "WHERE id <> ? AND category = ? " +
                "ORDER BY created_at DESC LIMIT ?";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return products;

            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, productId);
                ps.setString(2, category == null ? "" : category);
                ps.setInt(3, limit);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        products.add(createProductFromResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (products.size() < limit) {
            String fallback =
                    "SELECT * FROM products WHERE id <> ? " +
                    "ORDER BY created_at DESC LIMIT ?";

            try (Connection con = DBConnection.getConnection()) {
                if (con == null) return products;

                try (PreparedStatement ps = con.prepareStatement(fallback)) {
                    ps.setInt(1, productId);
                    ps.setInt(2, limit);

                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next() && products.size() < limit) {
                            Product p = createProductFromResultSet(rs);
                            boolean duplicate = false;
                            for (Product existing : products) {
                                if (existing.getId() == p.getId()) {
                                    duplicate = true;
                                    break;
                                }
                            }
                            if (!duplicate) products.add(p);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return products;
    }


    // ==========================================
        // GET PRODUCTS OF A SELLER
        // ==========================================

        public List<Product> getProductsBySeller(
                int sellerId) {

        List<Product> products =
                new ArrayList<>();

        String query =
                "SELECT * FROM products " +
                "WHERE seller_id = ? " +
                "ORDER BY created_at DESC";


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
                        sellerId
                );


                try (
                        ResultSet rs =
                                ps.executeQuery()
                ) {

                        while (rs.next()) {

                        products.add(
                                createProductFromResultSet(
                                        rs
                                )
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
        // DELETE PRODUCT
        // ==========================================

        public boolean deleteProduct(
                int productId,
                int sellerId) {

        String query =
                "DELETE FROM products " +
                "WHERE id = ? " +
                "AND seller_id = ?";


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
                        productId
                );

                ps.setInt(
                        2,
                        sellerId
                );


                return ps.executeUpdate() > 0;
                }


        } catch (SQLException e) {

                e.printStackTrace();

                return false;
        }
        }


        // ==========================================
        // COUNT SELLER PRODUCTS
        // ==========================================

        public int getSellerProductCount(
                int sellerId) {

        String query =
                "SELECT COUNT(*) " +
                "FROM products " +
                "WHERE seller_id = ?";


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
                        sellerId
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


        // ==========================================
        // COUNT SELLER ORDERS
        // ==========================================

        public int getSellerOrderCount(
                int sellerId) {

        String query =
                "SELECT COUNT(*) " +
                "FROM orders o " +
                "INNER JOIN products p " +
                "ON o.product_id = p.id " +
                "WHERE p.seller_id = ?";


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
                        sellerId
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