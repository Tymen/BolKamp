package com.tvis;

import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class Order {
    private ArrayList<Product> productList;
    private DbConnect connect = new DbConnect("root", "");
    private int orderID;

    private ArrayList<Box> chosenBoxes;

    private ArrayList<Integer[]> shortestPath = new ArrayList<>();

    public Order(int orderId, boolean devMode) throws Exception {
        this.orderID = orderId;

        // connectie weer terug krijgen v.d. DbConnect class
        Connection con = connect.getConnection();

        int isPicked = 0;

        if (!devMode) {
            // createStatement "prepared" een statement, vervolgens met executeQuery voert het een query uit
            ResultSet resultSet = null;
            try {
                Statement checkOrderQuery = con.createStatement();
                resultSet = checkOrderQuery.executeQuery("SELECT isPicked FROM orders WHERE OrderID = " + orderId);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (resultSet != null) {
                if (resultSet.next()) {
                    isPicked = resultSet.getInt(1);
                }
            }
        }

        if (isPicked == 0) {
            // resultset maken, deze vang later de data op
            ResultSet rs = null;

            try {
                Statement q1 = con.createStatement();
                rs = q1.executeQuery("SELECT si.StockItemID, si.StockItemName, si.Size, si.Location, oi.Quantity FROM stockitems si INNER JOIN orderlines oi ON oi.StockItemID = si.StockItemID WHERE si.StockItemID IN (SELECT StockItemID FROM orderlines WHERE orderID = " + orderId + ") AND oi.orderID = " + orderId);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // hier wordt een arraylist gevuld met producten
            productList = new ArrayList<>();
            if (rs != null) {
                while (rs.next()) {
                    Integer[] locatie = locatieArrayToIntegerArraylist(rs.getInt(4));
                    productList.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(5), locatie));
                }
            }
        } else {
            throw new Exception("Order has already been picked");
        }
    }

    public Integer[] locatieArrayToIntegerArraylist(int locatie) {
        Integer[] newLocaties;
        int x = ((locatie - 1) / 5) + 1;
        int y = ((locatie - 1) % 5) + 1;
        newLocaties = new Integer[]{x, y};

        for(Integer i : newLocaties) {
            System.out.print(i + " ");
        }
        System.out.println();
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
        Box.boxes = 0;
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

    public void setChosenBoxes(ArrayList<Box> chosenBoxes) {
        this.chosenBoxes = chosenBoxes;
    }

    public ArrayList<Box> getChosenBoxes() {
        return chosenBoxes;
    }

    public void setPicked() {
        // connectie weer terug krijgen v.d. DbConnect class
        Connection con = connect.getConnection();
        try {
            Statement checkOrderQuery = con.createStatement();
            checkOrderQuery.executeUpdate("UPDATE orders SET isPicked = 1 WHERE OrderID = " + orderID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
