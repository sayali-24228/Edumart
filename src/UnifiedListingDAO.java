import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnifiedListingDAO {

    private static final String BASE_QUERY =
            "SELECT * FROM (" +
            " SELECT p.id AS listing_id, 'SELL' AS listing_type, " +
            " p.seller_id AS owner_id, u.name AS owner_name, " +
            " p.name AS listing_name, p.description, p.category, " +
            " p.price, p.condition_type, p.image_path, " +
            " '' AS wanted_item, '' AS wanted_category, " +
            " '' AS additional_requirements, p.created_at " +
            " FROM products p INNER JOIN users u ON p.seller_id = u.id " +

            " UNION ALL " +

            " SELECT d.id AS listing_id, 'DONATE' AS listing_type, " +
            " d.donor_id AS owner_id, u.name AS owner_name, " +
            " d.item_name AS listing_name, d.description, d.category, " +
            " CAST(0 AS DECIMAL(10,2)) AS price, d.condition_type, " +
            " d.image_path, '' AS wanted_item, '' AS wanted_category, " +
            " '' AS additional_requirements, d.created_at " +
            " FROM donations d INNER JOIN users u ON d.donor_id = u.id " +
            " WHERE d.status = 'Available' " +

            " UNION ALL " +

            " SELECT e.id AS listing_id, 'EXCHANGE' AS listing_type, " +
            " e.owner_id AS owner_id, u.name AS owner_name, " +
            " e.offered_item AS listing_name, e.offered_description AS description, " +
            " e.category, CAST(0 AS DECIMAL(10,2)) AS price, " +
            " e.condition_type, e.image_path, e.wanted_item, " +
            " e.wanted_category, e.additional_requirements, e.created_at " +
            " FROM exchanges e INNER JOIN users u ON e.owner_id = u.id " +
            " WHERE e.status = 'Available' " +
            ") listings ";

    public List<UnifiedListing> searchListings(
            int currentUserId,
            String searchText,
            String category,
            String listingType,
            String sortOption) {

        List<UnifiedListing> list = new ArrayList<>();

        StringBuilder query = new StringBuilder(
                BASE_QUERY +
                "WHERE 1 = 1 "
        );

        if (searchText != null && !searchText.trim().isEmpty()) {
            query.append(
                    "AND (listing_name LIKE ? " +
                    "OR description LIKE ? " +
                    "OR category LIKE ? " +
                    "OR wanted_item LIKE ? " +
                    "OR owner_name LIKE ?) "
            );
        }

        if (category != null && !category.equals("All Categories")) {
            query.append("AND category = ? ");
        }

        if (listingType != null && !listingType.equals("All Listings")) {
            query.append("AND listing_type = ? ");
        }

        if ("Price: Low to High".equals(sortOption)) {
            query.append("ORDER BY price ASC, created_at DESC");
        } else if ("Price: High to Low".equals(sortOption)) {
            query.append("ORDER BY price DESC, created_at DESC");
        } else if ("Name: A to Z".equals(sortOption)) {
            query.append("ORDER BY listing_name ASC");
        } else {
            query.append("ORDER BY created_at DESC");
        }

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query.toString())
        ) {
            int index = 1;

            // currentUserId is retained in the method signature so the
            // same DAO can be used by Home and Browse. All listings are
            // intentionally shown; action buttons protect own listings.

            if (searchText != null && !searchText.trim().isEmpty()) {
                String search = "%" + searchText.trim() + "%";
                for (int i = 0; i < 5; i++) {
                    ps.setString(index++, search);
                }
            }

            if (category != null && !category.equals("All Categories")) {
                ps.setString(index++, category);
            }

            if (listingType != null && !listingType.equals("All Listings")) {
                ps.setString(index++, listingType);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<UnifiedListing> getLatestListings(int currentUserId, int limit) {
        List<UnifiedListing> all = searchListings(
                currentUserId,
                "",
                "All Categories",
                "All Listings",
                "Newest"
        );

        if (all.size() > limit) {
            return new ArrayList<>(all.subList(0, limit));
        }

        return all;
    }

    private UnifiedListing map(ResultSet rs) throws SQLException {
        UnifiedListing item = new UnifiedListing();

        item.setId(rs.getInt("listing_id"));
        item.setListingType(rs.getString("listing_type"));
        item.setOwnerId(rs.getInt("owner_id"));
        item.setOwnerName(rs.getString("owner_name"));
        item.setName(rs.getString("listing_name"));
        item.setDescription(rs.getString("description"));
        item.setCategory(rs.getString("category"));
        item.setPrice(rs.getDouble("price"));
        item.setConditionType(rs.getString("condition_type"));
        item.setImagePath(rs.getString("image_path"));
        item.setWantedItem(rs.getString("wanted_item"));
        item.setWantedCategory(rs.getString("wanted_category"));
        item.setAdditionalRequirements(rs.getString("additional_requirements"));

        return item;
    }
}
