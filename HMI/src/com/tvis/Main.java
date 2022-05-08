package com.tvis;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //Frame frame = new Frame();\
        PickProces pickProces = new PickProces();
        PickProcesMonitor pickProcesMonitor = new PickProcesMonitor();
        MainFrame frame = new MainFrame(pickProces, pickProcesMonitor);

        //test voor tsp algoritmes
        /* ArrayList<Integer[]> punten = new ArrayList<>();
        punten.add(new Integer[]{3, 2});
        punten.add(new Integer[]{2, 3});
        punten.add(new Integer[]{4, 2});
        punten.add(new Integer[]{3, 4});
        punten.add(new Integer[]{1, 2});
        punten.add(new Integer[]{1, 4});

        TspAlgoritme tspAlgoritme = new TspAlgoritme();
        tspAlgoritme.NearestNeighbour(punten);
        tspAlgoritme.BruteForce(punten); */
    }
}
