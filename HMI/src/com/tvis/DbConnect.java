package com.tvis;

import java.sql.*;

public class DbConnect{

    private String url = "jdbc:mysql://localhost/nerdygadgets";
    private String username, password;
    private Connection connection = DriverManager.getConnection(url, username, password);

    public DbConnect() throws SQLException {
    }

    public Connection getConnection() {
        return connection;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
