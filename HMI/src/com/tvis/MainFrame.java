package com.tvis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private JTextField textField1;
    private JButton submitButton;
    private JLabel pickStatus;
    private JLabel packStatus;
    private JPanel mainPanel;

    private PickProces pickProcesPanel;
    private PickProcesMonitor pickProcesMonitorPanel;
    private PickMonitor pickMonitor;
    public MainFrame (PickProces pickproces, PickProcesMonitor pickProcesMonitor) {
        setPickProces(pickproces);
        setPickProcesMonitor(pickProcesMonitor);
        setFrameSettings();
    }

    public void setFrameSettings() {
        setTitle("Order Picker Bolkamp");
        setContentPane(mainPanel);
        setSize(1200,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pickMonitor = new PickMonitor();
        this.pickProcesPanel.getNextButton().addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object getSource = e.getSource();
        if(getSource == submitButton) {
            nextStep("selectOrder");
        }else {
            pickProcesPanel.executeTspAlgoritme();
            nextStep("pickProcesMonitor");
        }
    }

    public void nextStep(String step) {
        switch (step){
            case "selectOrder":
                setContentPane(getPickProces());
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
