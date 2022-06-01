package com.tvis;

import java.util.ArrayList;
import java.sql.SQLException;

public class Main {

    private static final boolean devMode = true;

    public static void main(String[] args) throws SQLException {
        MainFrame frame = new MainFrame(devMode);
        //JSerialComm comm = new JSerialComm();
    }
}
