package com.tvis;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        //Frame frame = new Frame();\
        PickProcesMonitor pickProcesMonitor = new PickProcesMonitor();
        MainFrame frame = new MainFrame(pickProcesMonitor);
        JSerialcomm comm = new JSerialcomm();

    }
}
