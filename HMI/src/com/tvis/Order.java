package com.tvis;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private ArrayList<Product> productList;

    private ArrayList<Integer[]> shortestPath = new ArrayList<>();

    public Order(int orderId) {
        this.orderId = orderId;
        productList = new ArrayList<>();
        productList.add(new Product(2, new Integer[]{1, 2}));
        productList.add(new Product(4, new Integer[]{2, 3}));
        productList.add(new Product(7, new Integer[]{4, 5}));
        productList.add(new Product(9, new Integer[]{3, 2}));
        productList.add(new Product(3, new Integer[]{1, 4}));
        productList.add(new Product(2, new Integer[]{5, 2}));
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setShortestPath(ArrayList<Integer[]> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public ArrayList<Integer[]> getShortestPath() {
        return shortestPath;
    }
}
