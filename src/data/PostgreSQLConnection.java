package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnection {
	 // Database connection parameters
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/dbtest"; // Update with your database URL
    private static final String USER = "postgres"; // Update with your database username
    private static final String PASS = "dang123"; // Update with your database password

    // Method to establish a connection
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection to PostgreSQL database established successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to PostgreSQL database.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        }
        return connection;
    }

    // Test the connection
    public static void main(String[] args) {
        Connection conn = PostgreSQLConnection.getConnection();
        if (conn != null) {
            try {
                // Perform database operations here
                System.out.println("Database operations can be performed now.");
            } finally {
                try {
                    conn.close(); // Close the connection when done
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
