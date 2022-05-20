package com.tvis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PickProces implements ItemListener{
    private JLabel pickStatus;
    private JLabel packStatus;
    private JTable productTable;
    private JButton nextButton;
    private JLabel orderIdLabel;
    private JPanel pickProces;
    private JButton cancelButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JLabel datum;
    private JLabel aantalDozen;
    private Order bestel;

    private TspAlgoritme tspAlgoritme;

    private ArrayList<Box> ffdBox;
    private ArrayList<Box> nfdBox;

    private Object[][] data;

    public PickProces(Order order) throws SQLException{
        getTspAlgoritmes();
        getBppAlgoritmes();
        createTable(order);
    }

    public JPanel getPickProces() {
        return pickProces;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    private void createTable(Order order) throws SQLException {
        // bestel wordt een order met juiste orderID
        bestel = order;

        // Dozen berekenen voor de bpp algoritmes
        nfdBox = BPPAlgoritmes.nextFitDecreasing(bestel, 10);
        bestel.unpackProducts();

        ffdBox = BPPAlgoritmes.firstFitDecreasing(bestel, 10);
        bestel.unpackProducts();

        // aantal dozen zetten
        aantalDozen.setText("Dozen: " + nfdBox.size());

        // arraylist met producten, deze producten worden opgehaald in class "Product"
        ArrayList<Product> producten = bestel.getProductList();

        // order id en datum zetten
        orderIdLabel.setText("Order ID: " + bestel.getOrderID());
        datum.setText("Datum: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        productTable.setEnabled(false);

        // int aanmaken om de while loop bij te hoden, data array aanmaken om de data te laten zien op het scherm
        int length = producten.size();
        data = new Object[length][5];

        //producten worden in de array "data" gezet om deze vervolgens te laten zien in de gui
        int i = 0;
        while(i < length) {
            for (Product product : producten) {
                data[i] = new Object[]{product.getStockItemID(), product.getAmount(), product.getBeschrijving(), product.getLocatieVisual(), 0};
                i++;
            }
        }

        // voor elk product het nummer van de doos updaten
        for(Box doos : nfdBox) {
            for(Product product : doos.getProductsInBox()) {
                for(int j = 0; j < data.length; j++) {
                    if(data[j][0].equals(product.getStockItemID())) {
                        data[j][4] = doos.getBoxNumber();
                    }
                }
            }
        }

        productTable.setModel(new DefaultTableModel(
                data,
                new String[]
                        {"Product ID", "Hoeveelheid", "Beschrijving", "Locatie", "Doosnummer"} ));
    }

    private void getTspAlgoritmes() {
        tspAlgoritme = new TspAlgoritme();
        String[] algoritmes = tspAlgoritme.getAvailableAlgoritmes();

        for (String algoritme: algoritmes) {
            comboBox1.addItem(algoritme);
        }
    }

    public void executeTspAlgoritme(Order order) {
        ArrayList<Product> productList = order.getProductList();

        if (comboBox1.getSelectedItem().toString().equals("Nearest Neighbour")) {
            order.setShortestPath(tspAlgoritme.NearestNeighbour(productList));
        } else if (comboBox1.getSelectedItem().toString().equals("Brute Force")) {
            order.setShortestPath(tspAlgoritme.BruteForce(productList));
        } else if (comboBox1.getSelectedItem().toString().equals("Ant Colony optimalization")){
            order.setShortestPath(tspAlgoritme.AntColonyOptimalization(productList));
        } else {
            System.out.println("unknown tsp-algorithm");
        }
    }

    private void getBppAlgoritmes() {
        String[] algoritmes = BPPAlgoritmes.availableAlgoritmes;

        for (String algoritme: algoritmes) {
            comboBox2.addItem(algoritme);
        }
        comboBox2.addItemListener(this);
    }

    public void executeBppAlgoritme(Order order) {

        // kijkt welk algoritme is gekozen en zet de dozen vast in de order
        if(comboBox2.getSelectedItem().toString().equals("First Fit Decreasing")) {
            order.setChosenBoxes(BPPAlgoritmes.firstFitDecreasing(order, 10));
        } else if(comboBox2.getSelectedItem().toString().equals("Next Fit Decreasing")) {
            order.setChosenBoxes(BPPAlgoritmes.nextFitDecreasing(order, 10));
        } else {
            System.out.println("unknown bpp-algorithm");
        }
    }

    // checkt voor update van de bpp algoritme om live het scherm te updaten
    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            Object item = event.getItem();
            updateScreenAlgoritme((String) item);
        }
    }

    private void updateScreenAlgoritme(String algoritme) {
        switch (algoritme) {
            case "First Fit Decreasing":

                aantalDozen.setText("Dozen: " + ffdBox.size());

                // doos nummer per product updaten
                for(Box doos : ffdBox) {
                    for(Product product : doos.getProductsInBox()) {
                        for(int i = 0; i < data.length; i++) {
                            if(data[i][0].equals(product.getStockItemID())) {
                                data[i][4] = doos.getBoxNumber();
                            }
                        }
                    }
                }
                productTable.setModel(new DefaultTableModel(
                        data,
                        new String[]
                                {"Product ID", "Hoeveelheid", "Beschrijving", "Locatie", "Doosnummer"} ));
                break;
            case "Next Fit Decreasing":
                aantalDozen.setText("Dozen: " + nfdBox.size());

                // doos nummer per product updaten
                for(Box doos : nfdBox) {
                    for(Product product : doos.getProductsInBox()) {
                        for(int i = 0; i < data.length; i++) {
                            if(data[i][0].equals(product.getStockItemID())) {
                                data[i][4] = doos.getBoxNumber();
                            }
                        }
                    }
                }
                productTable.setModel(new DefaultTableModel(
                        data,
                        new String[]
                                {"Product ID", "Hoeveelheid", "Beschrijving", "Locatie", "Doosnummer"} ));

                break;
            default:
                break;
        }
    }
}
