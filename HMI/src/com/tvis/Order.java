package com.tvis;

import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class Order {
    private int orderId;
    private ArrayList<Product> productList;
    private DbConnect connect = new DbConnect("root", "");
    private Random rand = new Random();
    private Random rand2 = new Random();

    public Order(int orderId) throws SQLException {
        ResultSet rs = null;
        Connection con = connect.getConnection();
        try {
            Statement q1 = con.createStatement();
            rs = q1.executeQuery("SELECT StockItemID, StockItemName, Size, Location\n" +
                    "FROM stockitems\n" +
                    "WHERE StockItemID IN (SELECT StockItemID FROM orderlines WHERE orderID = " + orderId + ")");
            q1.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        this.orderId = orderId;
        productList = new ArrayList<>();
        if(rs != null) {
            while (rs.next()) {
                int loc1 = rand.nextInt((5 - 1) + 1) + 1;
                int loc2 = rand2.nextInt((5 - 1) + 1) + 1;
                productList.add(new Product(rs.getInt(0), rs.getString(1), rs.getInt(2), new Integer[]{loc1, loc2}));
            }
        }
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }
}
