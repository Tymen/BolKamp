package com.tvis;

import java.util.ArrayList;

public class Product {
    private int orderId;
    private String beschrijving;

    private int size;
    private Integer[] locatie;

    public Product(int orderId, String beschrijving, int size, Integer[] locatie) {
        this.orderId = orderId;
        this.beschrijving = beschrijving;
        this.size = size;
        this.locatie = locatie;
    }
}
