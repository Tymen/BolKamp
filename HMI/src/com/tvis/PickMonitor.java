package com.tvis;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PickMonitor extends JPanel {

    private int canvasWidth;
    private int canvasHeight;
    private int sizeBetween;
    private ArrayList<StorageBox> wareHouse = new ArrayList<>();
    private Graphics canvas;
    public PickMonitor() {
        canvasHeight = 500;
        canvasWidth = 500;
        sizeBetween = 10;
        this.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    }
    // Vooraf X en Y opnieuw bekijken. Want het moet even anders gedaan worden.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.canvas = g;
        int verticalY = 0;
        for (int i = 0; i < 5; i++){
            int horizontalX = 0;
            String[] row = new String[]{"A", "B", "C", "D", "E"};
            for (int x = 0; x < 5; x++) {
                g.drawRect(horizontalX,verticalY,(canvasWidth - (sizeBetween * 4))/5,(canvasHeight - (sizeBetween * 4))/5);
                g.setColor(Color.BLUE);
                g.fillRect(horizontalX,verticalY,(canvasWidth - (sizeBetween * 4))/5,(canvasHeight - (sizeBetween * 4))/5);
                wareHouse.add(new StorageBox(row[i] + x, horizontalX, verticalY, 0));
                horizontalX += (canvasWidth - (sizeBetween * 4)) / 5 + sizeBetween;
            }
            verticalY += (canvasHeight - (sizeBetween * 4)) / 5 + sizeBetween;
        }

        for (StorageBox item : wareHouse){
            System.out.println(item.getId());
        }
        updateStatus(1, "B3");
    }

    public StorageBox getStorageBox(String id) {
        StorageBox storageBox = null;
        for (StorageBox item : wareHouse){
            if (id.equals(item.getId())) {
                storageBox = item;
            }
        }
        return storageBox;
    }

    public void updateStatus(int status, String id) {
        StorageBox storageBox = getStorageBox(id);
        storageBox.setStatus(status);
        updateColor(storageBox.getX(), storageBox.getY(), Color.GREEN);
    }

    public void updateColor(int x, int y, Color color) {
        super.paintComponent(canvas);
        canvas.drawRect(x,y,(canvasWidth - (sizeBetween * 4))/5,(canvasHeight - (sizeBetween * 4))/5);
        canvas.setColor(color);
        canvas.fillRect(x,y,(canvasWidth - (sizeBetween * 4))/5,(canvasHeight - (sizeBetween * 4))/5);
    }
}
