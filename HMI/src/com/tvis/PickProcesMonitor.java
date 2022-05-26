package com.tvis;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PickProcesMonitor extends JFrame{
    private JTable productTable;
    private JLabel orderIdLabel;
    private JProgressBar progressBar1;
    private JPanel pickMonitorPanel;
    private JPanel pickProcesMonitor;
    private JButton finishButton;
    private JButton stopProcesButton;
    private JButton pauseProcesButton;
    private JPanel packMonitorPanel;
    private JPanel orderInfoPanel;
    private JLabel date;
    private JLabel aantalDozen;
    private PickMonitor pickMonitor;
    private PackMonitor packMonitor;

    private Order order;

    public PickProcesMonitor() {
        this.packMonitor = new PackMonitor();
        packMonitorPanel.add(packMonitor);
        packMonitorPanel.revalidate();
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

    public PackMonitor getPackMonitor() {
        return packMonitor;
    }

    public void setOrder(Order order) {
        this.order = order;
        orderIdLabel.setText("Order ID: " + order.getOrderID());
        date.setText("Datum: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        aantalDozen.setText("Dozen: " + order.getChosenBoxes().size());
    }

}
