package com.tvis;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class StartProcess {
    private ArrayList<Integer[]> shortestPath;
    Thread thrd;
    private PickMonitor pickMonitor;
    private PackMonitor packMonitor;
    private boolean gestopt;

    private Order order;

    private boolean checkStatusTSP(SerialPort port) {
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 10, 0);
        port.setBaudRate(9600);
        Scanner s1 = new Scanner(port.getInputStream());
        while (true) {
            String line = s1.next();
            if(line.equals("j") || line.equals("6")) {
                System.out.println("Product picked");
                break;
            } else if(line.equals("202")) {
                System.out.println("Proces is gestopt!");

            }
        }
        s1.close();
        return true;
    }

    private boolean checkStatusBPP(SerialPort port2) {
        port2.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        port2.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        port2.setBaudRate(9600);
        Scanner s1 = new Scanner(port2.getInputStream());
        System.out.println("d");
        while (true) {
            String line = s1.next();
            if(!line.equals("0")) {
                System.out.println(line);
                packMonitor.repaint();
                System.out.println("Box on correct place");
                break;
            }
        }
        s1.close();
        return true;
    }

    public void startPickProcess(Order order, SerialPort portTSP, SerialPort portBPP, PickMonitor pickMonitor, PackMonitor packMonitor) {
        // krijg de shortest path van TSP
        OutputStream ou1 = portTSP.getOutputStream();
        OutputStream ou2 = portBPP.getOutputStream();
        shortestPath = order.getShortestPath();

        this.packMonitor = packMonitor;
        this.order = order;

        thrd = new Thread() {
            @Override
            public void run() {
                // voor elke locatie ga je door een for loop om die te writen op de Arduino
                for(Integer[] location :shortestPath) {
                    try {
                        System.out.println("a");
                        Product currentProduct = findProduct(location);
                        System.out.println("b");
                        ou2.write(getBoxNumberProduct(currentProduct));
                        System.out.println("c");
                        checkStatusBPP(portBPP);
                        ou1.write(location[0]);
                        TimeUnit.SECONDS.sleep(1);
                        ou1.write(location[1]);
                        System.out.println(location[0] + " " + location[1]);

                        if(checkStatusTSP(portTSP)) {
                            pickedProduct(currentProduct);
                        }

                        packMonitor.repaint();
                        pickMonitor.nextBox();
                        TimeUnit.SECONDS.sleep(2);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                finishProcessTSP(ou1);
                finishProcessBPP(ou2);
            }
        };
        thrd.start();
    }

    public void noodStop(SerialPort port) {
        OutputStream ou = port.getOutputStream();
        try {
            if (!gestopt) {
                System.out.println("gestopt");
                ou.write(3);
            }


        } catch (IOException ignored) {}
    }

    public void resetProces(SerialPort port) {
        OutputStream ou = port.getOutputStream();
        try {
            System.out.println("reset request send");
            ou.write(4);
            thrd.interrupt();

        } catch (IOException ignored) {}
    }

    private void finishProcessTSP(OutputStream ou) {
        try {
            ou.write(7);
            System.out.println("Go home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finishProcessBPP(OutputStream ou) {
        try {
            ou.write(7);
            System.out.println("Box on 1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getBoxNumberProduct(Product product) {
        for (Box box : order.getChosenBoxes()) {
            for (Product p : box.getProductsInBox()) {
                if(p.equals(product)) {
                    packMonitor.setCurrentBox(box.getBoxNumber());
                    return box.getBoxNumber();
                }
            }
        }
        return 0;
    }

    private void pickedProduct(Product product) {
        for(Box box : order.getChosenBoxes()) {
            for (Product p : box.getProductsInBox()) {
                if (p.equals(product)) {
                    box.addPacked(product);
                }
            }
        }
    }

    private Product findProduct(Integer[] location) {
        for(Product product : order.getProductList()) {
            if(Arrays.equals(product.getLocatie(), location)) {
                return product;
            }
        }
        return null;
    }
}