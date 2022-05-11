package com.tvis;

import java.sql.SQLException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws SQLException {
        //Frame frame = new Frame();\
        // Ja dit is commentaar
//        PickProces pickProces = new PickProces();
//        PickProcesMonitor pickProcesMonitor = new PickProcesMonitor();
//        MainFrame frame = new MainFrame(pickProces, pickProcesMonitor);
        Random rand = new Random();
        {
            int loc1 = rand.nextInt((5 - 1) + 1) + 1;

            System.out.println(loc1);
        }
       // JSerialcomm comm = new JSerialcomm();

    }
}
