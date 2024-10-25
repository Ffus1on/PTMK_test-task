package com.daniil.databases.connections;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    public static Connection getConnection() {

        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fileInputStream);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

        String dbUrl = properties.getProperty("db.url");
        String dbUsername = properties.getProperty("db.user");
        String dbPassword = properties.getProperty("db.password");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
