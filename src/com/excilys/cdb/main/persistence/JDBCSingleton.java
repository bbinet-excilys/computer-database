package com.excilys.cdb.main.persistence;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *         Singleton implementation of a JDBC connector. Uses parameters for a
 *         mariadb database (can be easily changed to a mysql driver)
 *
 * @author bbinet
 */
public enum JDBCSingleton {

    INSTANCE;

    private static final String PROPERTIES_FILE = "src/resources/jdbcSettings.properties";
    private Connection          connection;
    private Properties          jdbcProperties  = new Properties();
    static final Logger         LOG             = LoggerFactory.getLogger(JDBCSingleton.class);

    /**
     * JDBCSingleton constructor. Read properties from the PropertiesFile and initiates the Connection with the database.
     */
    private JDBCSingleton() {
        Logger constructorLogger = LoggerFactory.getLogger(JDBCSingleton.class);
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
                    constructorLogger.error("Couldn't find the Driver Class. Check parameters value in "
                            + PROPERTIES_FILE + " : " + e.getMessage());
                }
                catch (SQLException e) {
                    constructorLogger.error("Couldn't getConnection from the DriverManager. Check parameters value in "
                            + PROPERTIES_FILE + " : " + e.getMessage());
                }
            }
        }
        catch (FileNotFoundException e) {
            constructorLogger.error("Couldn't find file " + PROPERTIES_FILE + " : " + e.getMessage());
        }
        catch (IOException e) {
            constructorLogger.error("Couldn't read file " + PROPERTIES_FILE + " : " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                LOG.trace("Closing JDBC connection");
            }
            catch (SQLException e) {
                LOG.error("Error closing JDBC connection :" + e.getMessage());
            }
        }
    }
}
