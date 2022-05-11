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
    private Order bestel = new Order(1001);

    public PickProces () throws SQLException {
        createTable();
    }

    public JPanel getPickProces() {
        return pickProces;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    private void createTable() {
        ArrayList<Product> producten = bestel.getProductList();
        productTable.setEnabled(false);
        Object[][] data = {
                {3, 1, "RC auto", "B1", 2},
                {1, 1, "RC raceAuto", "B2", 2}
        };

        Object[][] data2 = {
            for(Product product : producten) {
                {}
            }
        };
        productTable.setModel(new DefaultTableModel(
                data,
                new String[]
                        {"id", "amount", "description", "locatie", "doos"} ));
    }
}
