package com.tvis;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PackMonitor extends JPanel {

    private int canvasWidth;
    private int canvasHeight;
    private int sizeBetween;

    private int currentBox = 1;

    private Graphics canvas;

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

        int numberOfBoxes = order.getChosenBoxes().size();
        int currentBox = 1;

        drawBoxes(numberOfBoxes, currentBox);
    }

    public void setOrder(Order order) {
        this.order = order;
        repaint();
    }

    public void drawBoxes(int numberOfBoxes, int currentBox) {
        int boxWidth = canvasWidth / 2 - sizeBetween;
        int boxHeight = canvasHeight / 2 - sizeBetween;

        this.currentBox = currentBox;

        int boxesVertical = 0;
        int boxesHorizontal = 0;

        int verticalY = 0;
        int boxNumber = 0;

        // elke doos tekenen met producten
        for (int i = 1; i <= 2; i++) {
            int horizontalX = 0;
            for (int x = 1; x <= 2; x++) {
                if(boxesVertical * 2 + boxesHorizontal != numberOfBoxes) {
                    boxNumber++;

                    // doos in gebruik groen maken, rest zwart
                    if(boxNumber == this.currentBox) {
                        canvas.setColor(Color.green);
                        canvas.drawRect(horizontalX, verticalY, boxWidth, boxHeight);
                        canvas.setColor(Color.black);
                    } else {
                        canvas.drawRect(horizontalX, verticalY, boxWidth, boxHeight);
                    }
                    canvas.setFont(new Font("Default", Font.PLAIN, 20));
                    canvas.drawString("Doos " + boxNumber, horizontalX + 5, verticalY + 20);

                    canvas.drawString("/10", horizontalX + boxWidth - 35, verticalY + 20);

                    // producten tekenen
                    drawProducts(boxNumber, horizontalX, verticalY);

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

    public void drawProducts(int boxNumber, int x, int y) {
        ArrayList<Box> boxes = order.getChosenBoxes();

        Box box = boxes.get(boxNumber - 1);

        int lines = 1;

        int boxSizeFilled = 0;

        // voor elke gepakte product, tekenen in de doos
        for(Product p : box.getPackedProducts()) {
            canvas.setFont(new Font("Default", Font.PLAIN, 12));
            canvas.drawString("Product ID: " + p.getStockItemID(), x + 5, y + 20 + 20 * lines);
            lines++;
            if (p.getBeschrijving().length() > 30) {
                canvas.drawString("Product: " + p.getBeschrijving().substring(0, 30), x + 5, y + 15 + 20 * lines);
                lines++;
                canvas.drawString(p.getBeschrijving().substring(30), x + 52, y + 15 + 20 * lines);
            } else {
                canvas.drawString("Product: " + p.getBeschrijving(), x + 5, y + 15 + 20 * lines);
            }
            lines += 1;
            boxSizeFilled += p.getSize();
        }
        int distance = 90;
        if(boxSizeFilled >= 10) {
            distance = 100;
        }
        canvas.setFont(new Font("Default", Font.PLAIN, 20));
        canvas.drawString("" + boxSizeFilled, x + (canvasWidth / 2 + sizeBetween) - distance, y + 20);
    }
}
