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
    private ArrayList<Integer[]> dummyProductLocaties = new ArrayList<>();

    public PickProces () {
        createTable();
        getTspAlgoritmes();
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

    public void executeTspAlgoritme(ArrayList<Product> productList) {
        if (comboBox1.getSelectedItem().toString().equals("Nearest Neighbour")) {
            tspAlgoritme.NearestNeighbour(productList);
        } else if (comboBox1.getSelectedItem().toString().equals("Brute Force")) {
            tspAlgoritme.BruteForce(productList);
        } else {
            System.out.println("unknown algorithm");
        }
    }
}
