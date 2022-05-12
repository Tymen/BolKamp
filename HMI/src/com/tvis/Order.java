package com.tvis;

import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class Order {
    private ArrayList<Product> productList;
    private DbConnect connect = new DbConnect("root", "");
    private Random rand = new Random();
    private Random rand2 = new Random();

    public Order(int orderId) throws SQLException {
        ResultSet rs = null;
        Connection con = connect.getConnection();
        try {
            Statement q1 = con.createStatement();
            rs = q1.executeQuery("SELECT si.StockItemID, si.StockItemName, si.Size, si.Location, oi.Quantity FROM stockitems si INNER JOIN orderlines oi ON oi.StockItemID = si.StockItemID WHERE si.StockItemID IN (SELECT StockItemID FROM orderlines WHERE orderID = " + orderId + ") AND oi.orderID = " + orderId);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        productList = new ArrayList<>();
        if(rs != null) {
            while (rs.next()) {
                int loc1 = rand.nextInt((5 - 1) + 1) + 1;
                int loc2 = rand2.nextInt((5 - 1) + 1) + 1;
                productList.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), new Integer[]{loc1, loc2}));
            }
        }
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }
}
