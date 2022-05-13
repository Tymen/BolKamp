package com.tvis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
    private Order bestel;

    public PickProces(Order order) throws SQLException {
        createTable(order);
    }

    public JPanel getPickProces() {
        return pickProces;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    private void createTable(Order order) throws SQLException {
        // bestel wordt een order met juiste orderID
        bestel = order;
        // arraylist met producten, deze producten worden opgehaald in class "Product"
        ArrayList<Product> producten = bestel.getProductList();
        productTable.setEnabled(false);

        // int aanmaken om de while loop bij te hoden, data array aanmaken om de data te laten zien op het scherm
        int length = producten.size();
        Object[][] data = new Object[length][5];

        //producten worden in de array "data" gezet om deze vervolgens te laten zien in de gui
        int i = 0;
        while(i < length) {
            for (Product product : producten) {
                data[i] = new Object[]{product.getStockItemID(), product.getAmount(), product.getBeschrijving(), product.getLocatieVisual(), 2};
                i++;
            }
        }

        productTable.setModel(new DefaultTableModel(
                data,
                new String[]
                        {"id", "amount", "description", "locatie", "doos"} ));
    }
}
