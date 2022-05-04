package com.tvis;

import javax.swing.*;

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
        this.pickMonitor = new PickMonitor();
        add(pickMonitor);
    }

    public JPanel getPickProcesMonitor() {
        return pickProcesMonitor;
    }
}
