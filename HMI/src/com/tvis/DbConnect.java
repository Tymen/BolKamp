package com.tvis;

import java.sql.*;

public class DbConnect{
    // url defining voor database locatie
    private String url = "jdbc:mysql://localhost/nerdygadgets";
    private Connection connection;

    // connectie opzetten om deze te gebruiken voor commands
    public DbConnect(String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
