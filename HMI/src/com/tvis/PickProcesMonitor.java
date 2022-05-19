package com.tvis;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    private JPanel packMonitorPanel;
    private JPanel orderInfoPanel;
    private PickMonitor pickMonitor;

    public PickProcesMonitor() {
        this.pickMonitor = new PickMonitor();
        pickMonitorPanel.add(this.pickMonitor);
        pickMonitorPanel.revalidate();
    }

    public JButton getStopProcesButton() {
        return stopProcesButton;
    }

    public JButton getFinishButton() {
        return finishButton;
    }

    public JPanel getPickProcesMonitor() {
        return pickProcesMonitor;
    }

    public PickMonitor getPickMonitor() {
        return pickMonitor;
    }
}
