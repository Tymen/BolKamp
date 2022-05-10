package com.tvis;

import java.sql.*;

public class DatabaseCommands {
    DbConnect db = new DbConnect();
    Connection connect = db.getConnection();

    public DatabaseCommands() throws SQLException {
        // gebruikersnaam en wachtwoord instellen, dit kunnen we later evt in de app implementeren
        db.setPassword("");
        db.setUsername("root");

        String result = rs.getString(3);

        System.out.println(result);
    }
    Statement q1;

    {
        try {
            q1 = connect.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    ResultSet rs = q1.executeQuery("SELECT * FROM internet_orderline");
}
