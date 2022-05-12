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

    public PickProces(int orderId) throws SQLException {
        createTable(orderId);
    }

    public JPanel getPickProces() {
        return pickProces;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    private void createTable(int orderId) throws SQLException {
        bestel = new Order(orderId);
        ArrayList<Product> producten = bestel.getProductList();
        productTable.setEnabled(false);

        int length = producten.size();
        Object[][] data = new Object[length][5];

        int i = 0;
        while(i < length) {
            for (Product product : producten) {
                data[i] = new Object[]{product.getOrderId(), product.getAmount(), product.getBeschrijving(), product.getLocatieVisual(), 2};
                i++;
            }
        }

        productTable.setModel(new DefaultTableModel(
                data,
                new String[]
                        {"id", "amount", "description", "locatie", "doos"} ));
    }
}
