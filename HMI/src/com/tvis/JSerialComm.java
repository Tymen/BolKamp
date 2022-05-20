package com.tvis;

import com.fazecast.jSerialComm.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class JSerialComm extends JFrame implements ActionListener {
    private JLabel name1;

    private JButton openSerial;

    private SerialPort port[] = SerialPort.getCommPorts();
    private SerialPort port1;

    private JComboBox<SerialPort> serialBox1 = new JComboBox<>(port);
    private JComboBox<SerialPort> serialBox2 = new JComboBox<>(port);

    private JTextField xAs;
    private JTextField yAs;
    private JButton submit;
    private Integer[] locatie;

    public JSerialComm() {
        // Create GUI buttons
        openSerial = new JButton("connect");
        openSerial.addActionListener(this);
        name1 = new JLabel("Robot 1");
        name1.setBorder(new EmptyBorder(0, 100, 0, 125));

        setSize(400, 200);
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        add(name1);
        serialBox1.setSelectedIndex(0);
        add(serialBox1);
        add(openSerial);
        add(new JSeparator(SwingConstants.VERTICAL));
        serialBox2.setSelectedIndex(0);
        xAs = new JTextField(10);
        yAs = new JTextField(10);
        submit = new JButton("Submit");
        add(xAs);
        add(yAs);
        submit.addActionListener(this);
        add(submit);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public SerialPort getPort1() {
        return port1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread thrd;
        if (e.getSource() == openSerial) {
            port1 = (SerialPort) serialBox1.getSelectedItem();
            if (port1.isOpen()) {
                port1.closePort();
                openSerial.setText("connect");
                System.out.println("disconnected from robot 1");
                serialBox1.setEnabled(true);
                port1 = null;
            } else {
                port1.openPort();
                port1.setBaudRate(9600);
                openSerial.setText("disconnect");
                System.out.println("connected to robot 1");
                serialBox1.setEnabled(false);
            }
        }

        if(port1 != null && port1.isOpen() && e.getSource() == submit) {
            OutputStream ou = port1.getOutputStream();
            locatie = new Integer[2];
            locatie[0] = Integer.valueOf(xAs.getText());
            locatie[1] = Integer.valueOf(yAs.getText());
            System.out.println("Success");

            for(int i = 0; i < 2; i++) {
                try {
                    ou.write(locatie[i]);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println(locatie[i]);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}