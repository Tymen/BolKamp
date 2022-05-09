package com.tvis;

import javax.swing.*;
import java.awt.*;

public class PickMonitor extends JPanel {
    public PickMonitor() {
        setBackground(Color.white);
        this.setPreferredSize(new Dimension(300, 300));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0,0,750,120);
        g.setColor(Color.BLUE);
        g.fillRect(0,0,750,120);

        g.drawRect(0,120,750,40);
        g.setColor(Color.GRAY);
        g.fillRect(0,120,750,40);

        g.drawRect(0,160,750,75);
        g.setColor(Color.YELLOW);
        g.fillRect(0,160,750,75);

        g.setColor(Color.BLACK);
        g.drawRect(60,30,40,60);
        g.setColor(Color.BLACK);
        g.fillRect(60,30,40,60);

        g.drawOval(60, 10, 40, 40);
        g.setColor(Color.BLACK);
        g.fillOval(60, 10, 40, 40);

        g.drawOval(60, 65, 40, 40);
        g.setColor(Color.BLACK);
        g.fillOval(60, 65, 40, 40);
    }
}
