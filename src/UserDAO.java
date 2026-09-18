import java.sql.*;

public class UserDAO {

    public boolean registerUser(User user) {

        String query =
                "INSERT INTO users (name, email, password) " +
                "VALUES (?, ?, ?)";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(query)
        ) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();

            return true;

        } catch (SQLIntegrityConstraintViolationException e) {

            return false;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    public User loginUser(
            String email,
            String password) {

        String query =
                "SELECT * FROM users " +
                "WHERE email = ? AND password = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(query)
        ) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setId(
                        rs.getInt("id")
                );

                user.setName(
                        rs.getString("name")
                );

                user.setEmail(
                        rs.getString("email")
                );

                user.setPassword(
                        rs.getString("password")
                );

                return user;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }
}