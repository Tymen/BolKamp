package com.tvis;

import java.util.ArrayList;

public class Product {
    private int orderId;
    private String beschrijving;

    private int size;
    private Integer[] locatie;

    public Product(int size, Integer[] locatie) {
        this.orderId = 1;
        this.beschrijving = "product";
        this.size = size;
        this.locatie = locatie;
    }
}
