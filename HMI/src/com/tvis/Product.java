package com.tvis;

import java.util.ArrayList;

public class Product {
    private int orderId;
    private String beschrijving;

    private int size;
    private Integer[] locatie;
    private String locatieVisual;
    private int amount;

    public Product(int orderId, String beschrijving, int size, String locatieVisual, int amount, Integer[] locatie) {
        this.orderId = orderId;
        this.beschrijving = beschrijving;
        this.size = size;
        this.locatie = locatie;
        this.locatieVisual = locatieVisual;
        this.amount = amount;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public int getSize() {
        return size;
    }

    public Integer[] getLocatie() {
        return locatie;
    }

    public String getLocatieVisual() {
        return locatieVisual;
    }

    public int getAmount() {
        return amount;
    }
}
