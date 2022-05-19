package com.tvis;

import javax.swing.*;
import java.awt.*;

public class PickProcesMonitor extends JFrame{
    private JTable table1;
    private JLabel orderIdLabel;
    private JTable table2;
    private JProgressBar progressBar1;
    private JPanel pickMonitorPanel;
    private JPanel pickProcesMonitor;
    private JButton finishButton;
    private JButton stopProcesButton;
    private JButton pauseProcesButton;
    private PickMonitor pickMonitor;

    public PickProcesMonitor() {
        pickMonitorPanel.setPreferredSize(new Dimension(300, 300));
        this.pickMonitor = new PickMonitor();
    }

    public JPanel getPickProcesMonitor() {
        return pickProcesMonitor;
    }
}
