package com.tvis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    private JButton stoplichtVolgendeButton = new JButton("stoplichtVolgende");
    private JButton autoToevoegButton = new JButton("Auto(s) toevoegen");
    private JButton voertuigToevoegenButton = new JButton("Voertuig toevoegen");
    private JTextField aantalField = new JTextField(3);
    private JTextField kleurField = new JTextField(3);
    private JCheckBox stoptCheckBox = new JCheckBox();
    public Frame() {
        setTitle("Tymen Vis (1171204)");
        setSize(750, 300);
        setVisible(true);
        setLayout(new FlowLayout());
        add(stoplichtVolgendeButton);
        stoplichtVolgendeButton.addActionListener(this);

        add(autoToevoegButton);
        autoToevoegButton.addActionListener(this);

        add(new JLabel("aantal:"));
        add(aantalField);

        add(new JLabel("kleur: "));
        add(kleurField);

        add(stoptCheckBox);
        add(new JLabel("stopt"));

        add(voertuigToevoegenButton);
        voertuigToevoegenButton.addActionListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setError() {
        setTitle("Tymen Vis (S1171204) - Error: foute input!");
    }

    public void resetTitle() {
        setTitle("Tymen Vis (S1171204)");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object getSource = e.getSource();
    }
}