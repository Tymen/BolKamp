package com.tvis;

import com.fazecast.jSerialComm.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class JSerialcomm extends JFrame implements ActionListener {
    // namen voor boven de blokjes (Robot 1 & Robot 2)
    private JLabel name1;
    private JLabel name2;

    // buttons om verbinding te maken met serialports
    private JButton openSerial;
    private JButton openSerial2;

    // test buttons om de potmeter & LED's bij te houden
    private JSlider slider;
    private JButton ledOn;
    private JButton ledOn2;

    // buttons om de motor vooruit / achteruit / uit te zetten
    private JButton forwardMotor;
    private JButton backwardMotor;
    private JButton offMotor;

    private JButton forward2;
    private JButton backward2;
    private JButton off2;

    // getCommPorts = krijg alle beschikbare communicatie poorten
    private SerialPort port[] = SerialPort.getCommPorts();

    // serialpoorten 1 en 2, worden later gebruikt om communicatie te krijgen
    private SerialPort port1;
    private SerialPort port2;

    // comboboxes van serialcomm poorten
    private JComboBox<SerialPort> serialBox1 = new JComboBox<>(port);
    private JComboBox<SerialPort> serialBox2 = new JComboBox<>(port);

    // buttons voor % invullen naar boven en naar beneden
    private JButton boven;
    private JButton onder;
    private JTextField amount;
    private JLabel text;

    public JSerialcomm() {
        //set panel settings
        setSize(300, 400);
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

//        // Buttons robot 1 (LED en Slider)
        name1 = new JLabel("Robot 1");
        name1.setBorder(new EmptyBorder(0, 100, 0, 125));
        openSerial = new JButton("connect");
        openSerial.addActionListener(this);
//        ledOn = new JButton("LED on");
//        ledOn.addActionListener(this);
//        ledOn2 = new JButton("LED2 on");
//        ledOn2.addActionListener(this);
//        slider = new JSlider();
//
//        // add buttons etc. of robot 1
        add(name1);
        serialBox1.setSelectedIndex(0);
        add(serialBox1);
        add(openSerial);
        serialBox2.setSelectedIndex(0);
//        add(slider);
//        add(ledOn);
//        add(ledOn2);
        forward2 = new JButton("motor1 aan");
        backward2 = new JButton("motor1 achter");
        off2 = new JButton("motor1 uit");
        forward2.addActionListener(this);
        backward2.addActionListener(this);
        off2.addActionListener(this);
        add(forward2);
        add(backward2);
        add(off2);

        // Buttons robot 2 (motor besturen
        name2 = new JLabel("Robot 2");
        name2.setBorder(new EmptyBorder(0, 100, 0, 125));
        openSerial2 = new JButton("connect 2");
        openSerial2.addActionListener(this);
        forwardMotor = new JButton("Vooruit");
        backwardMotor = new JButton("achteruit");
        offMotor = new JButton("motor uit");
        forwardMotor.addActionListener(this);
        backwardMotor.addActionListener(this);
        offMotor.addActionListener(this);

        // add buttons robot 2
        add(name2);
        add(serialBox2);
        add(openSerial2);
        add(forwardMotor);
        add(backwardMotor);
        add(offMotor);


        // Buttons custom input
        text = new JLabel("Automatisch naar boven/beneden");
        text.setBorder(new EmptyBorder(0, 25, 0, 75));
        boven = new JButton("omhoog");
        onder = new JButton("omlaag");
        amount = new JTextField(5);
        boven.addActionListener(this);
        onder.addActionListener(this);

        // add custom input buttons
        add(text);
        add(amount);
        add(boven);
        add(onder);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread thrd = new Thread();

        // open / close serial connections
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
        } else if (e.getSource() == openSerial2) {
            port2 = (SerialPort) serialBox2.getSelectedItem();
            if (port2.isOpen()) {
                port2.closePort();
                openSerial2.setText("connect");
                System.out.println("disconnected from robot 2");
                serialBox2.setEnabled(true);
                port2 = null;
            } else {
                port2.openPort();
                openSerial2.setText("disconnect");
                serialBox2.setEnabled(false);
                System.out.println("connected to robot 2");
            }
        }

        // if statement checked eerst of poorten open staan & bestaan, vervolgens maakt het outputstream om bits te schrijven
        // bits worden geschreven met write(), dit komt direct naar arduino
        if (port1 != null && port1.isOpen()) {
            OutputStream ou1 = port1.getOutputStream();
            if(e.getSource() == openSerial) {
                // start Thread to read pot value
                port1.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                thrd = new Thread() {
                    @Override
                    public void run() {
                        Scanner s1 = new Scanner(port1.getInputStream());
                        while (s1.hasNext()) {
                            String line = s1.next();
                            int n1 = Integer.parseInt(line);
                            slider.setValue(n1);
                        }
                        s1.close();
                    }
                };
                thrd.start();
            } else if (e.getSource() == ledOn) {
                if (ledOn.getText().equals("LED on")) {
                    try {
                        ou1.write(1);
                        ledOn.setText("LED off");
                        System.out.println("pressed");
                    } catch (IOException io) {
                        System.out.println("Failed to turn ON");
                    }
                } else if (ledOn.getText().equals("LED off")) {
                    try {
                        ou1.write(1);
                        ledOn.setText("LED on");
                    } catch (IOException io) {
                        System.out.println("Failed to turn OFF");
                    }
                }
            } else if (e.getSource() == ledOn2) {
                if (ledOn2.getText().equals("LED2 on") && port1.isOpen()) {
                    try {
                        ou1.write(11);
                        ledOn2.setText("LED2 off");
                        System.out.println("pressed");
                    } catch (Exception io) {
                        System.out.println("Failed to turn ON");
                    }
                } else if (ledOn2.getText().equals("LED2 off") && port1.isOpen()) {
                    try {
                        ou1.write(11);
                        ledOn2.setText("LED2 on");
                    } catch (IOException io) {
                        System.out.println("Failed to turn OFF");
                    }
                }
            }

//            OutputStream ou1 = port1.getOutputStream();
            if (e.getSource() == off2) {
                try {
                    ou1.write(107);
                } catch (IOException io) {
                    System.out.println("Failed to go backward");
                }
            } else if (e.getSource() == forward2) {
                try {
                    ou1.write(105);
                } catch (IOException io) {
                    System.out.println("Failed to turn off");
                }
            } else if (e.getSource() == backward2) {
                try {
                    ou1.write(106);
                } catch (IOException io) {
                    System.out.println("Failed to go forward");
                }
            }
        }

        // if statement checked eerst of poorten open staan & bestaan, vervolgens maakt het outputstream om bits te schrijven
        // bits worden geschreven met write(), dit komt direct naar arduino
        if (port2 != null && port2.isOpen()) {
            OutputStream ou2 = port2.getOutputStream();
            if (e.getSource() == offMotor) {
                try {
                    ou2.write(104);
                } catch (IOException io) {
                    System.out.println("Failed to go backward");
                }
            } else if (e.getSource() == forwardMotor) {
                try {
                    ou2.write(102);
                } catch (IOException io) {
                    System.out.println("Failed to turn off");
                }
            } else if (e.getSource() == backwardMotor) {
                try {
                    ou2.write(103);
                } catch (IOException io) {
                    System.out.println("Failed to go forward");
                }
            }

            if(e.getSource() == boven) {
                try {
                    int upwardsAmount = Integer.parseInt(amount.getText());

                    if(upwardsAmount <= 100) {
                        ou2.write(upwardsAmount);
                    } else {
                        upwardsAmount = 100;
                        ou2.write(upwardsAmount);
                    }
                } catch(IOException io) {
                    System.out.println("Failed to send percentage");
                }
            } else if(e.getSource() == onder) {
                try {
                    ou2.write(101);
                } catch(IOException io) {
                    System.out.println("Failed to go down");
                }
            }
        }
    }
}
