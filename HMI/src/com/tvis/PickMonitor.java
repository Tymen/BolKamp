package com.tvis;

import javax.swing.*;
import java.awt.*;

public class PickMonitor extends JPanel {
    public PickMonitor() {
        this.setPreferredSize(new Dimension(300, 300));
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0,0,300,120);
        g.setColor(Color.BLUE);
        g.fillRect(0,0,300,120);
    }
}
