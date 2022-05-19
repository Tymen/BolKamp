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
    private int orderID;

    private ArrayList<Integer[]> shortestPath = new ArrayList<>();

    public Order(int orderId) throws SQLException {
        this.orderID = orderId;

        // resultset maken, deze vang later de data op
        ResultSet rs = null;
        // connectie weer terug krijgen v.d. DbConnect class
        Connection con = connect.getConnection();
        // createStatement "prepared" een statement, vervolgens met executeQuery voert het een query uit
        try {
            Statement q1 = con.createStatement();
            rs = q1.executeQuery("SELECT si.StockItemID, si.StockItemName, si.Size, si.Location, oi.Quantity FROM stockitems si INNER JOIN orderlines oi ON oi.StockItemID = si.StockItemID WHERE si.StockItemID IN (SELECT StockItemID FROM orderlines WHERE orderID = " + orderId + ") AND oi.orderID = " + orderId);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        // hier wordt een arraylist gevuld met producten
        productList = new ArrayList<>();
        if(rs != null) {
            while (rs.next()) {
                Integer[] locatie = locatieArrayToIntegerArraylist(rs.getInt(4));
                productList.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(5), locatie));
                System.out.println(locatie[0] + " " + locatie[1]);
            }
        }
    }

    public Integer[] locatieArrayToIntegerArraylist(int locatie) {
        Integer[] newLocaties;
        int x = ((locatie - 1) / 5) + 1;
        int y = ((locatie - 1) % 5) + 1;
        newLocaties = new Integer[]{x, y};

        return newLocaties;
    }

    // productlijst returnen
    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void unpackProducts() {
        for(Product product : productList) {
            product.setPacked(false);
        }
    }


    public void setShortestPath(ArrayList<Integer[]> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public ArrayList<Integer[]> getShortestPath() {
        return shortestPath;
    }

    public int getOrderID() {
        return orderID;
    }
}
