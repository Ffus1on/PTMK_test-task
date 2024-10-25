package com.daniil.databases;


import com.daniil.databases.connections.DatabaseConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public void initializeDatabase() {
        String folderPath = "src/main/resources/databasescripts";

        File folder = new File(folderPath);
        File[] sqlFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".sql"));

        if (sqlFiles == null || sqlFiles.length == 0) {
            throw new RuntimeException("No SQL files found in directory: " + folderPath);
        }

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            for (File sqlFile : sqlFiles) {
                StringBuilder sql = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new FileReader(sqlFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sql.append(line).append("\n");
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error reading SQL file: " + sqlFile.getName(), e);
                }

                statement.execute(sql.toString());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error initializing database", e);
        }
    }
}
