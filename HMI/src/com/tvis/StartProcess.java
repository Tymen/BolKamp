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
    public StartProcess() {

    }

    private ArrayList<Integer[]> shortestPath;

    // TODO change to thread
    public void startPickProcess(Order order, OutputStream ou) {
        // krijg de shortest path van TSP
        shortestPath = order.getShortestPath();
        // voor elke locatie ga je door een for loop om die te writen op de Arduino
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