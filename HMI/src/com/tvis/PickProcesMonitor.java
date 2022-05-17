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

    public PickProcesMonitor(ArrayList<Integer[]> producten) {
        this.pickMonitor = new PickMonitor();
        pickMonitorPanel.add(this.pickMonitor);
        pickMonitorPanel.revalidate();

        pickMonitor.setProductenToBePicked(producten);
    }

    public JPanel getPickProcesMonitor() {
        return pickProcesMonitor;
    }
}
