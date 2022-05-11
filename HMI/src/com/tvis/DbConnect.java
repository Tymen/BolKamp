package com.tvis;

import java.sql.*;

public class DbConnect{

    private String url = "jdbc:mysql://localhost/nerdygadgets";
    private Connection connection;

    public DbConnect(String username, String password) throws SQLException {
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
