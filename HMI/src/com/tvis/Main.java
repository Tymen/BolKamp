package com.tvis;

public class Main {

    public static void main(String[] args) {
        //Frame frame = new Frame();\
        // Ja dit is commentaar
        PickProces pickProces = new PickProces();
        PickProcesMonitor pickProcesMonitor = new PickProcesMonitor();
        MainFrame frame = new MainFrame(pickProces, pickProcesMonitor);

        JSerialcomm comm = new JSerialcomm();
    }
}
