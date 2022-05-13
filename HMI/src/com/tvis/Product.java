package com.tvis;

import java.util.ArrayList;

public class Product {

    private String beschrijving;
    private int StockItemID;

    private int size;
    private Integer[] locatie;

    private boolean packed;

    public Product(int size, Integer[] locatie) {
        this.beschrijving = "product";
        this.StockItemID = 1;
        this.packed = false;
        setSize(size);
        setLocatie(locatie);
    }

    public int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public Integer[] getLocatie() {
        return locatie;
    }

    private void setLocatie(Integer[] locatie) {
        this.locatie = locatie;
    }

    public boolean isPacked() {
        return packed;
    }

    public void setPacked() {
        this.packed = true;
    }

    @Override
    public String toString() {
        return "Product{" +
                "StockItemID=" + StockItemID +
                '}';
    }
}
