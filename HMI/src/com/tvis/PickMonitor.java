package com.tvis;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PickMonitor extends JPanel {

    private int canvasWidth;
    private int canvasHeight;
    private int sizeBetween;
    private ArrayList<StorageBox> wareHouse = new ArrayList<>();
    private Graphics canvas;

    private ArrayList<Integer[]> productenToBePicked = new ArrayList<>();

    public PickMonitor() {
        canvasHeight = 500;
        canvasWidth = 500;
        sizeBetween = 10;
        this.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        int verticalY = 0;
        for (int i = 5; i >= 1; i--){
            int horizontalX = 0;
            for (int x = 1; x <= 5; x++) {
                Integer[] box = new Integer[] {i, x};
                wareHouse.add(new StorageBox(box, horizontalX, verticalY, 0));
                horizontalX += (canvasWidth - (sizeBetween * 4)) / 5 + sizeBetween;
            }
            verticalY += (canvasHeight - (sizeBetween * 4)) / 5 + sizeBetween;
        }
    }
    // Vooraf X en Y opnieuw bekijken. Want het moet even anders gedaan worden.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.canvas = g;

        for (StorageBox item : wareHouse){
            updateStatus(0, item.getId());
        }

        setProductStatus();
    }

    public void setProductStatus() {
        for (Integer[] product : productenToBePicked) {
            updateStatus(1, product);
        }
    }

    public StorageBox getStorageBox(Integer[] id) {
        StorageBox storageBox = null;
        for (StorageBox item : wareHouse){
            if (Arrays.equals(item.getId(), id)) {
                storageBox = item;
            }
        }
        return storageBox;
    }

    public void updateStatus(int status, Integer[] product) {
        StorageBox storageBox = getStorageBox(product);
        storageBox.setStatus(status);

        Color color = switch (status) {
            //box needs to be picked
            case 1 -> Color.orange;
            //next box to be picked
            case 2 -> Color.yellow;
            //box has been picked
            case 3 -> Color.green;
            //box not used
            default -> Color.gray;
        };

        updateColor(storageBox.getX(), storageBox.getY(), color);
    }

    public void updateColor(int x, int y, Color color) {
        canvas.setColor(color);
        canvas.fillRect(x,y,(canvasWidth - (sizeBetween * 4))/5,(canvasHeight - (sizeBetween * 4))/5);
        canvas.setColor(Color.black);
        canvas.drawRect(x,y,(canvasWidth - (sizeBetween * 4))/5,(canvasHeight - (sizeBetween * 4))/5);
    }

    public void setProductenToBePicked(Order order) {
        for (Product product : order.getProductList()) {
            productenToBePicked.add(product.getLocatie());
        }
    }
}
