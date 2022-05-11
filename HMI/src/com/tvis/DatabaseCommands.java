package com.tvis;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCommands {
    Connection connect;
    Statement q1;

    public DatabaseCommands() throws SQLException {
        // gebruikersnaam en wachtwoord instellen, dit kunnen we later evt in de app implementeren
    }

    public ResultSet getOrderline(int orderID) {
        ResultSet rs = null;
        try {
            q1 = connect.createStatement();
            rs = q1.executeQuery("SELECT StockItemID, StockItemName, Size, Location\n" +
                                     "FROM stockitems\n" +
                                     "WHERE StockItemID IN (SELECT StockItemID FROM orderlines WHERE orderID = " + orderID + ")");
            q1.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
