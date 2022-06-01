package com.tvis;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

public class MainFrame extends JFrame implements ActionListener {
    private final boolean devMode;
    private JTextField textField1;
    private JButton submitButton;
    private JPanel mainPanel;
    private ImageIcon img = new ImageIcon("src/com/tvis/Bolkamp Icon.png");

    // Serial Connection variables
    private SerialConnect connectionTSP;
    private SerialConnect connectionBPP;
    private JComboBox<SerialPort> serialPort1;
    private JComboBox<SerialPort> serialPort2;
    private JLabel checkOrderMessage;

    private PickProces pickProcesPanel;
    private PickProcesMonitor pickProcesMonitorPanel;
    private PickMonitor pickMonitor;
    private PackMonitor packMonitor;

    private StartProcess Proces = new StartProcess();

    private Order order;

    private int orderID;

    public StartProcess getProces() {
        return Proces;
    }

    public MainFrame (boolean devMode) throws SQLException {
        this.devMode = devMode;

        try {
            order = new Order(orderID, devMode);

            setPickProcesMonitor(new PickProcesMonitor());
            this.pickMonitor = this.pickProcesMonitorPanel.getPickMonitor();
            this.packMonitor = this.pickProcesMonitorPanel.getPackMonitor();
            pickProcesPanel = new PickProces(order);
            this.pickProcesMonitorPanel.getStopProcesButton().addActionListener(this);
            this.pickProcesMonitorPanel.getResetProcesButton().addActionListener(this);
            checkOrderMessage.setText("");
        } catch (Exception e) {
            checkOrderMessage.setText("order has already been picked");
        }

        setFrameSettings();
    }

    public void setFrameSettings() {
        setTitle("Order Picker Bolkamp");
        setContentPane(mainPanel);
        setSize(1600,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(img.getImage());

        this.pickProcesPanel.getNextButton().addActionListener(this);

        // SerialPort combobox om de devices to selecteren.
        SerialPort[] ports = new SerialConnect().getPortArray();
        for(SerialPort port : ports) {
            serialPort1.addItem(port);
            serialPort2.addItem(port);
        }

        submitButton.addActionListener(this);
        this.textField1.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    nextStep("selectOrder");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        this.pickProcesMonitorPanel.getStopProcesButton().addActionListener(this);
        this.pickProcesMonitorPanel.getFinishButton().addActionListener (this);
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

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            System.out.println(e);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object getSource = e.getSource();
        if(getSource == submitButton) {
            nextStep("selectOrder");
        }else if(e.getSource() == pickProcesPanel.getNextButton()) {
            nextStep("pickProcesMonitor");
        } else if (e.getSource() == this.pickProcesPanel.getCancelButton()) {
            nextStep("firstStep");
        } else if (e.getSource() == this.pickProcesMonitorPanel.getFinishButton()) {
            nextStep("finish");
        } else if (e.getSource() == this.pickProcesMonitorPanel.getStopProcesButton()) {
            Proces.noodStop(connectionTSP.getPort1());
        } else if (e.getSource() == this.pickProcesMonitorPanel.getResetProcesButton()) {

            Proces.resetProces(connection.getPort1());
            connection.getPort1().closePort();
            nextStep("resetOrder");
        }
    }

    public void nextStep(String step) {
        switch (step){
            case "selectOrder":
                // wanneer er een order is ingevoerd wordt er een PickProcesPanel opgesteld op basis van het ordernr
                    orderID = Integer.parseInt(textField1.getText());
                    textField1.setText("");
                    pickMonitor.reset();

                    try {
                        order = new Order(orderID, devMode);
                        connectionTSP = new SerialConnect((SerialPort) serialPort1.getSelectedItem());
                        connectionBPP = new SerialConnect((SerialPort) serialPort2.getSelectedItem());

                        try {
                            order.unpackProducts();
                            pickProcesPanel = new PickProces(order);
                            setContentPane(pickProcesPanel.getPickProces());
                            this.pickProcesPanel.getNextButton().addActionListener(this);
                            this.pickProcesPanel.getCancelButton().addActionListener(this);
                            revalidate();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        checkOrderMessage.setText("");
                    } catch (Exception e) {
                        checkOrderMessage.setText("order has already been picked");
                    }


                break;
            case "resetOrder":
                // reset knop
                try {
                    pickMonitor.reset();
                    order = new Order(orderID, devMode);
                    connection = new SerialConnect((SerialPort) serialPorts.getSelectedItem());
                    order.unpackProducts();

                    textField1.setText("");
                    pickMonitor.reset();

                    pickProcesPanel = new PickProces(order);
                    setContentPane(pickProcesPanel.getPickProces());
                    this.pickProcesPanel.getNextButton().addActionListener(this);
                    this.pickProcesPanel.getCancelButton().addActionListener(this);
                    revalidate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "pickProcesMonitor":
                setInfo(order);
                setContentPane(getPickProcesMonitor());
                revalidate();
                Proces.startPickProcess(order, connectionTSP.getPort1(), connectionBPP.getPort1(), pickMonitor, packMonitor);
                break;
            case "firstStep":
                setContentPane(mainPanel);
                revalidate();
                break;
            case "finish":
                order.setPicked();
                setContentPane(mainPanel);
                revalidate();
                // disconnect device aan het einde van het proces.
                connectionTSP.disconnectDevice();
                connectionBPP.disconnectDevice();
                break;
            default:
                break;
        }
    }

    public void setInfo(Order order) {
        pickProcesPanel.executeTspAlgoritme(order);
        pickMonitor.setProductenToBePicked(order);
        pickProcesPanel.executeBppAlgoritme(order);
        packMonitor.setOrder(order);
        pickProcesMonitorPanel.setOrder(order);
    }
}
