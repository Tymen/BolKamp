package com.tvis;

import java.util.ArrayList;

public class Box {

    private ArrayList<Product> productsInBox;
    private int boxNumber;
    private int boxSize;
    private int remainingSize;
    private ArrayList<Product> packedProducts;

    static int boxes;

    public Box(int boxSize) {
        boxes += 1;
        this.boxNumber = boxes;
        this.boxSize = boxSize;
        this.productsInBox = new ArrayList<>();
        this.remainingSize = boxSize;
        this.packedProducts = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.productsInBox.add(product);
        this.remainingSize -= product.getSize();
    }

    public void addPacked(Product product) {
        this.packedProducts.add(product);
    }

    public ArrayList<Product> getPackedProducts() {
        return packedProducts;
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

    // voor dev
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
