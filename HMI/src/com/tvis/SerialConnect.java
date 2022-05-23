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

public class SerialConnect {
    private SerialPort portArray[] = SerialPort.getCommPorts();
    private SerialPort port1;
    private JComboBox<SerialPort> serialBox1 = new JComboBox<>(portArray);
    private JComboBox<SerialPort> serialBox2 = new JComboBox<>(portArray);

    // SerialConnect zorgt ervoor dat er een serial connection established wordt
    public SerialConnect(SerialPort port) {
        port.openPort();
        port.setBaudRate(9600);
        port1 = port;
        System.out.println("Connected to: " + port);
    }

    public SerialConnect(){

    }

    public InputStream getInputstream() {
        return port1.getInputStream();
    }

    public OutputStream getOutputStream() {
        return port1.getOutputStream();
    }

    public SerialPort getPort1() {
        return port1;
    }

    public SerialPort[] getPortArray() {
        return portArray;
    }

    // disconnect device
    public void disconnectDevice() {
        port1.closePort();
        System.out.println("Disconnected from: " + port1);
    }
}