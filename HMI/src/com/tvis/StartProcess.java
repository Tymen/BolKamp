package com.tvis;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class StartProcess {
    private ArrayList<Integer[]> shortestPath;
    Thread thrd;
    private PickMonitor pickMonitor;
    private boolean gestopt;

    private boolean checkStatus(SerialPort port) {
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 10, 0);
        port.setBaudRate(9600);
        Scanner s1 = new Scanner(port.getInputStream());
        while (true) {
            String line = s1.next();
            if(line.equals("j") || line.equals("6")) {
                System.out.println("I should be done now");
                break;
            } else if(line.equals("202")) {
                System.out.println("Proces is gestopt!");

            }
        }
        s1.close();
        return true;
    }


    public void startPickProcess(Order order, SerialPort port, PickMonitor pickMonitor) {
        // krijg de shortest path van TSP
        OutputStream ou = port.getOutputStream();
        shortestPath = order.getShortestPath();
        thrd = new Thread() {
            @Override
            public void run() {
                // voor elke locatie ga je door een for loop om die te writen op de Arduino
                for(Integer[] location :shortestPath) {
                    try {
                        ou.write(location[0]);
                        TimeUnit.SECONDS.sleep(1);
                        ou.write(location[1]);
                        System.out.println(location[0] + " " + location[1]);
                        checkStatus(port);
                        pickMonitor.nextBox();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                finishProcess(ou);
            }
        };
        thrd.start();
    }

    public void noodStop(SerialPort port) {
        OutputStream ou = port.getOutputStream();
        try {
            if (!gestopt) {
                System.out.println("gestopt");
                ou.write(3);
            }


        } catch (IOException ignored) {}
    }

    public void resetProces(SerialPort port) {
        OutputStream ou = port.getOutputStream();
        try {
            System.out.println("reset request send");
            ou.write(4);

        } catch (IOException ignored) {}
    }

    private void finishProcess(OutputStream ou) {
        try {
            ou.write(7);
            System.out.println("Go home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}