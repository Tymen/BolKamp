package com.tvis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class PickProces {
    private JLabel pickStatus;
    private JLabel packStatus;
    private JTable productTable;
    private JButton nextButton;
    private JLabel orderIdLabel;
    private JPanel pickProces;
    private JButton cancelButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;

    private TspAlgoritme tspAlgoritme;
<<<<<<< HEAD
=======
    private ArrayList<Integer[]> dummyProductLocaties = new ArrayList<>();
>>>>>>> b4bca00fae76d7ef12abb3d7d6ff852f21248a1a

    public PickProces () {
        createTable();
        getTspAlgoritmes();
<<<<<<< HEAD
=======
        //set dummy locaties
        dummyProductLocaties.add(new Integer[]{3, 2});
        dummyProductLocaties.add(new Integer[]{2, 3});
        dummyProductLocaties.add(new Integer[]{4, 2});
        dummyProductLocaties.add(new Integer[]{3, 4});
        dummyProductLocaties.add(new Integer[]{1, 2});
        dummyProductLocaties.add(new Integer[]{1, 4});
>>>>>>> b4bca00fae76d7ef12abb3d7d6ff852f21248a1a
    }

    public JPanel getPickProces() {
        return pickProces;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    private void createTable() {
        productTable.setEnabled(false);
        Object[][] data = {
                {3, 1, "RC auto", "B1", 2},
                {1, 1, "RC raceAuto", "B2", 2}
        };
        productTable.setModel(new DefaultTableModel(
                data,
                new String[]
                        {"id", "amount", "description", "locatie", "doos"}        ));
    }

    private void getTspAlgoritmes() {
        tspAlgoritme = new TspAlgoritme();
        String[] algoritmes = tspAlgoritme.getAvailableAlgoritmes();

        for (String algoritme: algoritmes) {
            comboBox1.addItem(algoritme);
        }
    }

<<<<<<< HEAD
    public void executeTspAlgoritme(ArrayList<Product> productList) {
        if (comboBox1.getSelectedItem().toString().equals("Nearest Neighbour")) {
            tspAlgoritme.NearestNeighbour(productList);
        } else if (comboBox1.getSelectedItem().toString().equals("Brute Force")) {
            tspAlgoritme.BruteForce(productList);
=======
    public void executeTspAlgoritme() {
        if (comboBox1.getSelectedItem().toString().equals("Nearest Neighbour")) {
            tspAlgoritme.NearestNeighbour(dummyProductLocaties);
        } else if (comboBox1.getSelectedItem().toString().equals("Brute Force")) {
            tspAlgoritme.BruteForce(dummyProductLocaties);
>>>>>>> b4bca00fae76d7ef12abb3d7d6ff852f21248a1a
        } else {
            System.out.println("unknown algorithm");
        }
    }
}
