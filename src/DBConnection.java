import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Properties properties = new Properties();

            FileInputStream file =
                    new FileInputStream("config/db.properties");

            properties.load(file);
            file.close();

            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.username");
            PASSWORD = properties.getProperty("db.password");

        } catch (IOException e) {
            System.out.println("Database configuration file not found!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {

        try {

            Connection connection =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            System.out.println("EduMart database connected!");

            return connection;

        } catch (SQLException e) {

            System.out.println("Database connection failed!");

            e.printStackTrace();

            return null;
        }
    }
}