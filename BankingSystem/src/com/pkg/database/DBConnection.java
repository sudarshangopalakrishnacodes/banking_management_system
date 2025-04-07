package com.pkg.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DBConnection {

    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    private static final String PROPERTIES_FILE = "db.properties";

    public static Connection getConnection() {
        Connection connection = null;

        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                LOGGER.info("Connection Successful");
            } else {
                LOGGER.warning("Connection Failed");
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not read database properties file", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection failed", e);
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to close connection", e);
        }
    }
}
