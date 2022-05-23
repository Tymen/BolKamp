package com.tvis;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PackMonitor extends JPanel {

    private int canvasWidth;
    private int canvasHeight;
    private int sizeBetween;

    private Graphics canvas;

    private int numberOfBoxes;

    private Order order;

    public PackMonitor() {
        canvasHeight = 500;
        canvasWidth = 500;
        sizeBetween = 20;
        this.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.canvas = g;

        numberOfBoxes = order.getChosenBoxes().size();

        int boxWidth = canvasWidth / 2 - sizeBetween;
        int boxHeight = canvasHeight / 2 - sizeBetween;

        int boxesVertical = 0;
        int boxesHorizontal = 0;

        int verticalY = 0;
        int boxNumber = 0;
        for (int i = 1; i <= 2; i++) {
            int horizontalX = 0;
            for (int x = 1; x <= 2; x++) {
                if(boxesVertical * 2 + boxesHorizontal != numberOfBoxes) {
                    boxNumber++;
                    canvas.drawRect(horizontalX, verticalY, boxWidth, boxHeight);
                    canvas.setFont(new Font("Default", Font.PLAIN, 20));
                    canvas.drawString("Doos " + boxNumber, horizontalX + 5, verticalY + 20);
                    drawProducts(horizontalX, verticalY, boxNumber);
                    horizontalX += (canvasWidth - (sizeBetween * 2)) / 2 + sizeBetween;
                    boxesHorizontal += 1;
                    if(boxesHorizontal == 2) {
                        boxesHorizontal = 0;
                        boxesVertical += 1;
                    }
                }
            }
            verticalY += (canvasHeight - (sizeBetween * 2)) / 2 + sizeBetween;
        }
    }

    public void setOrder(Order order) {
        this.order = order;
        repaint();
    }

    public void drawProducts(int x, int y, int boxNumber) {
        ArrayList<Box> boxes = order.getChosenBoxes();

        int lines = 1;

        for(Box box : boxes) {
            if(box.getBoxNumber() == boxNumber) {
                for(Product product : box.getProductsInBox()) {
                    canvas.setFont(new Font("Default", Font.PLAIN, 12));
                    canvas.drawString("Product ID: " + product.getStockItemID(), x + 5, y + 20 + 20 * lines);
                    lines++;
                    if(product.getBeschrijving().length() > 30) {
                        canvas.drawString("Product: " + product.getBeschrijving().substring(0, 30), x + 5, y + 15 + 20 * lines);
                        lines++;
                        canvas.drawString(product.getBeschrijving().substring(30), x + 52, y + 15 + 20 * lines);
                    } else {
                        canvas.drawString("Product: " + product.getBeschrijving(), x + 5, y + 15 + 20 * lines);
                    }
                    lines++;
                    canvas.drawString(" ", x + 5, y + 15 + 20 * lines);
                    lines++;
                }
            }
        }
    }
}
