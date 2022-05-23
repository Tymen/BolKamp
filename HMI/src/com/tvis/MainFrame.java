package com.tvis;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serial;
import java.sql.SQLException;

public class MainFrame extends JFrame implements ActionListener {
    private JTextField textField1;
    private JButton submitButton;
    private JLabel pickStatus;
    private JLabel packStatus;
    private JPanel mainPanel;
    private ImageIcon img = new ImageIcon("HMI/src/com/tvis/Bolkamp Icon.png");

    // Serial Connection variables
    private SerialConnect connection;
    private JComboBox<SerialPort> serialPorts;

    private PickProces pickProcesPanel;
    private PickProcesMonitor pickProcesMonitorPanel;
    private PickMonitor pickMonitor;
    private PackMonitor packMonitor;

    private StartProcess tspProces = new StartProcess();

    private Order order;

    private int orderID;

    public MainFrame () throws SQLException {
        order = new Order(orderID);
        setPickProcesMonitor(new PickProcesMonitor());
        this.pickMonitor = this.pickProcesMonitorPanel.getPickMonitor();
        this.packMonitor = this.pickProcesMonitorPanel.getPackMonitor();
        pickProcesPanel = new PickProces(order);

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
            serialPorts.addItem(port);
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
        }
    }

    public void nextStep(String step) {
        switch (step){
            case "selectOrder":
                // wanneer er een order is ingevoerd wordt er een PickProcesPanel opgesteld op basis van het ordernr
                try {
                    orderID = Integer.parseInt(textField1.getText());
                    textField1.setText("");
                    pickMonitor.reset();
                    order = new Order(orderID);
                    connection = new SerialConnect((SerialPort) serialPorts.getSelectedItem());
                    order.unpackProducts();
                    pickProcesPanel = new PickProces(order);
                    setContentPane(pickProcesPanel.getPickProces());
                    this.pickProcesPanel.getNextButton().addActionListener(this);
                    this.pickProcesPanel.getCancelButton().addActionListener(this);
                    revalidate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                break;
            case "pickProcesMonitor":
                pickProcesPanel.executeTspAlgoritme(order);
                pickMonitor.setProductenToBePicked(order);
                pickProcesPanel.executeBppAlgoritme(order);
                packMonitor.setOrder(order);
                pickProcesMonitorPanel.setOrder(order);
                setContentPane(getPickProcesMonitor());
                revalidate();
                pickMonitor.demoPicker();
                tspProces.startPickProcess(order, connection.getOutputStream());
                break;
            case "firstStep":
                setContentPane(mainPanel);
                revalidate();
                break;
            case "finish":
                setContentPane(mainPanel);
                revalidate();
                // disconnect device aan het einde van het proces.
                connection.disconnectDevice();
                break;
            default:
                break;
        }
    }

}
