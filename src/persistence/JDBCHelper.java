package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCHelper {

    private static final String DATABASE_URI      = "jdbc:mariadb://localhost:3306/computer-database-db";
    private static final String DATABASE_USERNAME = "admincdb";
    private static final String DATABASE_PASSWORD = "qwerty1234";

    private static Connection mConnection;

    public static Connection getConnection() {
        if (mConnection == null)
            setConnection();
        return mConnection;
    }

    private static void setConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            mConnection = DriverManager.getConnection(DATABASE_URI, DATABASE_USERNAME, DATABASE_PASSWORD);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Couldn't find class, try adding the JAR properly\n");
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.err.println("Couldn't getConnection from DriverManager, Check parameters\n");
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        if (mConnection != null) {
            try {
                mConnection.close();
            }
            catch (SQLException e) {
                System.err.println("Couldn't close the connection, it was probably NULL\n");
                e.printStackTrace();
            }
        }
    }

}
