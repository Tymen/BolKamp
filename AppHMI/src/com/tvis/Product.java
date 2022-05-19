package com.tvis;

import java.util.ArrayList;

public class Product {

    private String beschrijving;
    private int StockItemID;

    private int size;
    private Integer[] locatie;
    private String locatieVisual;
    private int amount;

    private boolean packed;

    public Product(int stockitemID, String beschrijving, int size, int amount, Integer[] locatie) {
        this.StockItemID = stockitemID;
        this.beschrijving = beschrijving;
        this.packed = false;
        this.locatieVisual = tspLocationToVisual(locatie);
        this.amount = amount;
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

    public int getStockItemID() {
        return StockItemID;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String getLocatieVisual() {
        return locatieVisual;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isPacked() {
        return packed;
    }

    public void setPacked(boolean status) {
        this.packed = status;
    }

    public String tspLocationToVisual(Integer[] locatie) {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        String returnValue = "" + alphabet[locatie[0] - 1] + locatie[1];

        return returnValue;
    }

    @Override
    public String toString() {
        return "Product{" +
                "StockItemID=" + StockItemID +
                '}';
    }
}
