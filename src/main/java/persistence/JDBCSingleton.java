package persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import exception.PropertiesNotFoundException;

/**
 * Singleton implementation of a JDBC connector. Uses parameters for a mariadb
 * database (can be easily changed to a mysql driver).
 *
 * @author bbinet
 */
public enum JDBCSingleton {

  /**
   * The unique instance of the JDBCSingleton (Connection to the database).
   */
  INSTANCE;
  /**
   * The path to the properties file, containing database parameters (driver, url,
   * username, password).
   */
  private static final String JDBC_PROPERTIES_FILE = "properties/jdbcSettings.properties";
  private static final String HIKARI_PROPERTIES    = "properties/hikari.properties";

  private static final Logger LOGGER = LoggerFactory.getLogger(JDBCSingleton.class);

  /**
   * The properties file as a Property object to ease the parsing of the
   * parameters.
   */
  private Properties       jdbcProperties = new Properties();
  /**
   * The connection to the database.
   */
  private Connection       connection;
  private HikariDataSource dataSource;

  private void setJDBCConnection() {
    Logger constructorLogger = LoggerFactory.getLogger(JDBCSingleton.class);
    try {
      ClassLoader loader           = Thread.currentThread().getContextClassLoader();
      InputStream propertiesStream = loader.getResourceAsStream(JDBC_PROPERTIES_FILE);
      jdbcProperties.load(propertiesStream);
      String dbDriver, dbURI, dbUsername, dbPassword;
      dbDriver   = jdbcProperties.getProperty("db.driver.class");
      dbURI      = jdbcProperties.getProperty("db.uri");
      dbUsername = jdbcProperties.getProperty("db.username");
      dbPassword = jdbcProperties.getProperty("db.password");
      if (!dbDriver.isEmpty() && !dbURI.isEmpty()) {
        try {
          Class.forName(dbDriver);
          connection = DriverManager.getConnection(dbURI, dbUsername, dbPassword);
          constructorLogger.info("Setting connection");
        }
        catch (ClassNotFoundException e) {
          constructorLogger.error("Couldn't find the Driver Class. Check parameters value in "
              + JDBC_PROPERTIES_FILE
              + " : " + e.getMessage());
        }
        catch (SQLException e) {
          constructorLogger.error("Couldn't getConnection from the DriverManager. Check parameters value in "
              + JDBC_PROPERTIES_FILE + " : " + e.getMessage());
        }
      }
    }
    catch (FileNotFoundException e) {
      constructorLogger.error("Couldn't find file " + JDBC_PROPERTIES_FILE + " : "
          + e.getMessage());
    }
    catch (IOException e) {
      constructorLogger.error("Couldn't read file " + JDBC_PROPERTIES_FILE + " : "
          + e.getMessage());
    }
  }

  private void setHikariConnectionPool() throws PropertiesNotFoundException {
    try {
      ClassLoader loader           = Thread.currentThread().getContextClassLoader();
      InputStream propertiesStream = loader.getResourceAsStream(HIKARI_PROPERTIES);
      Properties  hikariProperties = new Properties();
      hikariProperties.load(propertiesStream);
      HikariConfig config = new HikariConfig(hikariProperties);
      dataSource = new HikariDataSource(config);
    }
    catch (IOException e) {
      throw new PropertiesNotFoundException("Error reading Hikari properties file :"
          + e.getMessage());
    }
  }

  public Connection getHikariConnection() throws SQLException, PropertiesNotFoundException {
    if (dataSource == null) {
      setHikariConnectionPool();
    }
    return dataSource.getConnection();
  }

  /**
   * Closes the connection instance.
   */
  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
        connection = null;
      }
      catch (SQLException e) {
      }
    }
  }

  /**
   * Method to get the connection from the JDBCSingleton instance.
   *
   * @return The instance of the connection.
   */
  public Connection getJDBCConnection() {
    setJDBCConnection();
    return connection;
  }

}
