package com.tvis;

import java.util.ArrayList;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        //Frame frame = new Frame();\
        ArrayList<Integer[]> producten = new ArrayList<>();
        producten.add(new Integer[]{1, 2});
        producten.add(new Integer[]{2, 2});
        producten.add(new Integer[]{3, 2});
        producten.add(new Integer[]{4, 2});


        PickProcesMonitor pickProcesMonitor = new PickProcesMonitor(producten);
        MainFrame frame = new MainFrame(pickProcesMonitor);
    }
}
