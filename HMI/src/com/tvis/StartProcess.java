package com.tvis;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class StartProcess {
    private final boolean[] returnBool = {false};
    private JSerialComm comm = new JSerialComm();
    private Thread thrd;
    public StartProcess() throws SQLException {

    }
    private ArrayList<Integer[]> shortestPath;

    private void checkStatus() {
        SerialPort port = comm.getPort1();
        port.setBaudRate(9600);
        InputStream in = port.getInputStream();
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        thrd = new Thread() {
            @Override
            public void run() {
                Scanner s1 = new Scanner(port.getInputStream());
                while (!returnBool[0]) {
                    try {
                        String line = s1.nextLine();
                        int n1 = Integer.parseInt(line);
                        if (n1 == 6) {
                            returnBool[0] = true;
                            System.out.println("Successfully picked");
                        }
                    } catch(Exception e) {
                        System.out.println("Wrong int");
                    }
                }
                s1.close();
            }
        };
        thrd.start();
    }

    public void startPickProcess(Order order) throws SQLException {
        final int[] n1 = new int[1];
        SerialPort port = comm.getPort1();
        OutputStream ou = port.getOutputStream();
        shortestPath = order.getShortestPath();
        for(Integer[] location : shortestPath) {
            try {
                ou.write(location[0]);
                TimeUnit.SECONDS.sleep(1);
                ou.write(location[1]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}