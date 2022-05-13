package com.tvis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainFrame extends JFrame implements ActionListener {
    private JTextField textField1;
    private JButton submitButton;
    private JLabel pickStatus;
    private JLabel packStatus;
    private JPanel mainPanel;

    private PickProces pickProcesPanel;
    private PickProcesMonitor pickProcesMonitorPanel;
    private PickMonitor pickMonitor;

    private Order order;

    private int orderID;

    public MainFrame (PickProcesMonitor pickProcesMonitor) throws SQLException {
        setPickProcesMonitor(pickProcesMonitor);
        setFrameSettings();
    }

    public void setFrameSettings() throws SQLException {
        setTitle("Order Picker Bolkamp");
        setContentPane(mainPanel);
        setSize(1200,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pickMonitor = new PickMonitor();
        submitButton.addActionListener(this);

        setVisible(true);
    }

    public void setPickProces(PickProces pickProces) {
        this.pickProcesPanel = pickProces;
    }

    public void setPickProcesMonitor(PickProcesMonitor pickProcesMonitor) {
        this.pickProcesMonitorPanel = pickProcesMonitor;
    }

    private JPanel getPickProces() {
        return pickProcesPanel.getPickProces();
    }

    public JPanel getPickProcesMonitor() {
        return pickProcesMonitorPanel.getPickProcesMonitor();
    }

    public int getOrderID() {
        return orderID;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object getSource = e.getSource();
        if(getSource == submitButton) {
<<<<<<< HEAD
            orderID = Integer.parseInt(textField1.getText());
            try {
                order = new Order(orderID);
                BPPAlgoritmes.nextFitDecreasing(order, 10);
                order.unpackProducts();
                BPPAlgoritmes.firstFitDecreasing(order, 10);
                nextStep("selectOrder");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(e.getSource() == pickProcesPanel.getNextButton()) {
            try {
                nextStep("pickProcesMonitor");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
=======
            order = new Order(Integer.parseInt(textField1.getText()));
            nextStep("selectOrder");
        }else {
            nextStep("pickProcesMonitor");
>>>>>>> d32e35c (deleted unneccesary line)
        }
    }

    public void nextStep(String step) throws SQLException {
        switch (step){
            case "selectOrder":
                // wanneer er een order is ingevoerd wordt er een PickProcesPanel opgesteld op basis van het ordernr
                pickProcesPanel = new PickProces(order);
                setContentPane(pickProcesPanel.getPickProces());
                this.pickProcesPanel.getNextButton().addActionListener(this);
                revalidate();
                break;
            case "pickProcesMonitor":
                setContentPane(getPickProcesMonitor());
                revalidate();
                break;
            default:
                break;
        }
    }
}
