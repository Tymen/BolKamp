package com.tvis;

import java.util.ArrayList;

public class TspAlgoritme {

    //string array om de beschikbare tsp algoritmes op te halen.
    private String[] availableAlgoritmes = new String[]{"Nearest Neighbour", "Brute Force", "Ant Colony optimalization"};

    //het kortste pad van het brute force algoritme
    private ArrayList<Integer[]> bruteForceShortestPath;

    //de afstand van het kortste pad van het brute force algoritme
    private double bruteForceShortestPathDistance;

    //getter voor beschikbare algoritmes
    public String[] getAvailableAlgoritmes() {
        return availableAlgoritmes;
    }

    //het nearest neighbour algoritme - geeft een pad terug
    public ArrayList<Integer[]> NearestNeighbour(ArrayList<Integer[]> punten) {
        Integer[] currentPoint = new Integer[]{1,1};
        int aantalPoints = punten.size();
        ArrayList<Integer[]> shortestPath = new ArrayList<>(aantalPoints);
        ArrayList<Integer[]> pointsCopy = new ArrayList<>(punten);
        double totalDistance = 0;

        //loop over het aantal punten
        for (int i = 0; i < aantalPoints; i++) {
            Integer[] shortestPoint = new Integer[2];
            double shortestDis = 100;
            int indexOfShortestPoint = 0;

            //loop over overgebleven punten
            for (int j = 0; j < pointsCopy.size(); j++) {
                double dis;

                //bereken afstand tussen huidig punt en nieuw punt - kortste punt word opgeslagen
                dis = measureDistance(currentPoint, pointsCopy.get(j));
                if (dis < shortestDis) {
                    shortestPoint = pointsCopy.get(j);
                    shortestDis = dis;
                    indexOfShortestPoint = j;
                }
            }

            //voeg nieuw punt toe aan pad
            currentPoint = shortestPoint;
            pointsCopy.remove(indexOfShortestPoint);
            totalDistance += shortestDis;
            shortestPath.add(currentPoint);
        }

        System.out.println(totalDistance);
        return shortestPath;
    }

    //brute force algoritme - geeft de kortste pad terug
    public ArrayList<Integer[]> BruteForce(ArrayList<Integer[]> punten) {
        int aantalPoints = punten.size();

        getAllRecursive(aantalPoints, punten);

        System.out.println(bruteForceShortestPathDistance);
        return bruteForceShortestPath;
    }

    //TODO wordt gerealiseerd als er tijd over is.
    //ant colony optimalisatie - voert nearest neighbour uit en optimaliseert dit - optimalisatielengte bepaalt hoe lang het algoritme optimaliseert
    public ArrayList<Integer[]> AntColonyOptimalization(ArrayList<Integer[]> punten, int optimalisatieLengte) {
        Integer[] currentPoint = new Integer[]{1,1};
        int aantalPoints = punten.size();
        ArrayList<Integer[]> shortestPath = new ArrayList<>(aantalPoints);

        return shortestPath;
    }

    //berekent de afstand tussen twee punten
    private double measureDistance(Integer[] point1, Integer[] point2) {
        int x1 = point1[0];
        int x2 = point2[0];
        int y1 = point1[1];
        int y2 = point2[1];

        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    //loop voor brute force algoritme om alle mogelijke paden te checken
    private void getAllRecursive(int n, ArrayList<Integer[]> points) {
        //n == 1 bij een nieuw volledig pad - berekent de afstand van het nieuwe pad en slaat hem op als dit een kortere weg is
        if(n == 1) {
            double distance = calcDistance(points);
            if (bruteForceShortestPathDistance == 0 || distance < bruteForceShortestPathDistance) {
                bruteForceShortestPathDistance = distance;
                bruteForceShortestPath = points;
            }
        //wissel punten om nieuw pad te maken
        } else {
            for(int i = 0; i < n-1; i++) {
                getAllRecursive(n - 1, points);
                if(n % 2 == 0) {
                    swap(points, i, n-1);
                } else {
                    swap(points, 0, n-1);
                }
            }
            getAllRecursive(n - 1, points);
        }
    }

    //functie voor brute force loop om het pad aan te passen - wisselt punten in array
    private void swap(ArrayList<Integer[]> input, int a, int b) {
        Integer[] tmp = input.get(a);
        input.set(a, input.get(b));
        input.set(b, tmp);
    }

    //berekent de afstand van een pad.
    private double calcDistance(ArrayList<Integer[]> points) {
        Integer[] startPoint = new Integer[]{1,1};
        double totalDistance = 0;
        Integer[] currentPoint = startPoint;

        //bereken afstand tussen twee punten en voeg toe aan totaal
        for (Integer[] point : points) {
            double distance = measureDistance(currentPoint, point);
            currentPoint = point;
            totalDistance += distance;
        }

        return totalDistance;
    }
}
