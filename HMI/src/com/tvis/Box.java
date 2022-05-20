package com.tvis;

import java.util.ArrayList;

public class Box {

    private ArrayList<Product> productsInBox;
    private int boxNumber;
    private int boxSize;
    private int remainingSize;

    static int boxes;

    public Box(int boxSize) {
        boxes += 1;
        this.boxNumber = boxes;
        this.boxSize = boxSize;
        this.productsInBox = new ArrayList<>();
        this.remainingSize = boxSize;
    }

    public void addProduct(Product product) {
        this.productsInBox.add(product);
        this.remainingSize -= product.getSize();
    }

    public ArrayList<Product> getProductsInBox() {
        return productsInBox;
    }

    public int getRemainingSize() {
        return remainingSize;
    }

    public int getBoxNumber() {
        return boxNumber;
    }

    @Override
    public String toString() {
        return "Box{" +
                "productsInBox=" + productsInBox +
                ", boxNumber=" + boxNumber +
                ", boxSize=" + boxSize +
                ", remainingSize=" + remainingSize +
                '}';
    }
}
