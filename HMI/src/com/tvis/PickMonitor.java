package com.tvis;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
    private int[] productStatus = new int[]{};
    public PickMonitor() {
        canvasHeight = 500;
        canvasWidth = 500;
        sizeBetween = 10;
        this.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        setupWarehouse();

    }
    public void setupWarehouse() {
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

        drawPath();
    }

    public void setProductStatus() {
        for (int i = 0; i < productenToBePicked.size(); i++) {
            if (productStatus[i] == 0) {
                productStatus[i] = 1;
            }
            updateStatus(productStatus[i], productenToBePicked.get(i));
        }
    }

    public void drawPath() {
        for (int i = 0; i < productenToBePicked.size() - 1; i++) {
            StorageBox storageBox1 = getStorageBox(productenToBePicked.get(i));
            StorageBox storageBox2 = getStorageBox(productenToBePicked.get(i + 1));

            canvas.setColor(Color.blue);
            Graphics2D g2 = (Graphics2D) canvas;
            g2.setStroke(new BasicStroke(10));

            int x1 = storageBox1.getX() + ((canvasWidth - (sizeBetween * 4)) / 5) / 2;
            int x2 = storageBox2.getX() + ((canvasWidth - (sizeBetween * 4)) / 5) / 2;
            int y1 = storageBox1.getY() + ((canvasHeight - (sizeBetween * 4)) / 5) / 2;
            int y2 = storageBox2.getY() + ((canvasHeight - (sizeBetween * 4)) / 5) / 2;

            canvas.drawLine(x1, y1, x2, y2);
            canvas.setColor(Color.red);
            canvas.drawOval(x1 - 5, y1 - 5, 10, 10);
        }

        StorageBox storageBox = getStorageBox(productenToBePicked.get(productenToBePicked.size() - 1));
        int x1 = storageBox.getX() + ((canvasWidth - (sizeBetween * 4)) / 5) / 2;
        int y1 = storageBox.getY() + ((canvasHeight - (sizeBetween * 4)) / 5) / 2;
        canvas.drawOval(x1 - 5, y1 - 5, 10, 10);
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

    public void setProductenToBePicked(Order order) {
        productenToBePicked = new ArrayList<>(order.getShortestPath().size()){};

        for (int i = 0; i < order.getShortestPath().size(); i++) {
            productenToBePicked.add(order.getShortestPath().get(i));
        }

        productStatus = new int[productenToBePicked.size()];
    }
    public void reset() {
        this.productStatus = null;
        productenToBePicked.clear();
        this.wareHouse.clear();
        setupWarehouse();
    }

    public void demoPicker() {
        productStatus[0] = 2;
        repaint();

        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                int i;
                for (i = 1; i < productenToBePicked.size(); i++) {
                    TimeUnit.SECONDS.sleep(2);
                    productStatus[i] = 2;
                    productStatus[i - 1] = 3;
                    repaint();
                }
                TimeUnit.SECONDS.sleep(2);
                productStatus[i - 1] = 3;
                repaint();

                return null;
            }
        };

        swingWorker.execute();
    }

    public void updateColor(int x, int y, Color color) {
        canvas.setColor(color);
        canvas.fillRect(x,y,(canvasWidth - (sizeBetween * 4))/5,(canvasHeight - (sizeBetween * 4))/5);
        canvas.setColor(Color.black);
        canvas.drawRect(x,y,(canvasWidth - (sizeBetween * 4))/5,(canvasHeight - (sizeBetween * 4))/5);
    }
}
