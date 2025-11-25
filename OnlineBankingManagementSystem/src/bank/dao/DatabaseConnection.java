//package bank.dao;
//
//import bank.util.Constants;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DatabaseConnection {
//    private static Connection connection;
//
//    public static Connection getConnection() throws SQLException {
//        if (connection == null || connection.isClosed()) {
//            try {
//                // Try to load the driver
//                Class.forName("com.mysql.cj.jdbc.Driver");
//            } catch (ClassNotFoundException e) {
//                throw new SQLException("MySQL JDBC Driver not found. Please add it to your classpath.", e);
//            }
//
//            connection = DriverManager.getConnection(
//                    Constants.DB_URL,
//                    Constants.DB_USER,
//                    Constants.DB_PASSWORD
//            );
//        }
//        return connection;
//    }
//
//    public static void closeConnection() {
//        try {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//            }
//        } catch (SQLException e) {
//            System.err.println("Error closing connection: " + e.getMessage());
//        }
//    }
//}