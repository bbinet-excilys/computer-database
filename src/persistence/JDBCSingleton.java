package persistence;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum JDBCSingleton {

    INSTANCE;

    private static final String PROPERTIES_FILE = "src/resources/jdbcSettings.properties";
    private Connection          connection;
    private Properties          jdbcProperties  = new Properties();

    private JDBCSingleton() {
        try {
            FileReader mFileReader = new FileReader(PROPERTIES_FILE);
            this.jdbcProperties.load(mFileReader);
            String dbDriver, dbURI, dbUsername, dbPassword;
            dbDriver   = this.jdbcProperties.getProperty("db.driver.class");
            dbURI      = this.jdbcProperties.getProperty("db.uri");
            dbUsername = this.jdbcProperties.getProperty("db.username");
            dbPassword = this.jdbcProperties.getProperty("db.password");
            if (!dbDriver.isEmpty() && !dbURI.isEmpty()) {
                try {
                    Class.forName(dbDriver);
                    connection = DriverManager.getConnection(dbURI, dbUsername, dbPassword);
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
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            }
            catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
