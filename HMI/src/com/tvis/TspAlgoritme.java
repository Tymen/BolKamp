package com.tvis;

import java.util.ArrayList;

public class TspAlgoritme {
    private String[] availableAlgoritmes = new String[]{"Nearest Neighbour", "Brute Force", "Ant Colony optimalization"};

    private ArrayList<Integer[]> bruteForceShortestPath;
    private double bruteForceShortestPathDistance;

    public String[] getAvailableAlgoritmes() {
        return availableAlgoritmes;
    }

    public ArrayList<Integer[]> NearestNeighbour(ArrayList<Integer[]> punten) {
        Integer[] currentPoint = new Integer[]{1,1};
        int aantalPoints = punten.size();
        ArrayList<Integer[]> shortestPath = new ArrayList<>(aantalPoints);
        ArrayList<Integer[]> pointsCopy = new ArrayList<>(punten);
        double totalDistance = 0;

        for (int i = 0; i < aantalPoints; i++) {
            Integer[] shortestPoint = new Integer[2];
            double shortestDis = 100;
            int indexOfShortestPoint = 0;

            for (int j = 0; j < pointsCopy.size(); j++) {
                double dis;

                dis = measureDistance(currentPoint, pointsCopy.get(j));
                if (dis < shortestDis) {
                    shortestPoint = pointsCopy.get(j);
                    shortestDis = dis;
                    indexOfShortestPoint = j;
                }
            }

            currentPoint = shortestPoint;
            pointsCopy.remove(indexOfShortestPoint);
            totalDistance += shortestDis;
            shortestPath.add(currentPoint);
        }

        System.out.println(totalDistance);
        return shortestPath;
    }

    public ArrayList<Integer[]> BruteForce(ArrayList<Integer[]> punten) {
        int aantalPoints = punten.size();

        getAllRecursive(aantalPoints, punten);

        System.out.println(bruteForceShortestPathDistance);
        return bruteForceShortestPath;
    }

    public ArrayList<Integer[]> AntColonyOptimalization(ArrayList<Integer[]> punten) {
        Integer[] currentPoint = new Integer[]{1,1};
        int aantalPoints = punten.size();
        ArrayList<Integer[]> shortestPath = new ArrayList<>(aantalPoints);

        return shortestPath;
    }

    private double measureDistance(Integer[] point1, Integer[] point2) {
        int x1 = point1[0];
        int x2 = point2[0];
        int y1 = point1[1];
        int y2 = point2[1];

        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    private void getAllRecursive(int n, ArrayList<Integer[]> points) {

        if(n == 1) {
            double distance = calcDistance(points);
            if (bruteForceShortestPathDistance == 0 || distance < bruteForceShortestPathDistance) {
                bruteForceShortestPathDistance = distance;
                bruteForceShortestPath = points;
            }
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

    private void swap(ArrayList<Integer[]> input, int a, int b) {
        Integer[] tmp = input.get(a);
        input.set(a, input.get(b));
        input.set(b, tmp);
    }

    private double calcDistance(ArrayList<Integer[]> points) {
        Integer[] startPoint = new Integer[]{1,1};
        double totalDistance = 0;
        Integer[] currentPoint = startPoint;

        for (Integer[] point : points) {
            double distance = measureDistance(currentPoint, point);
            currentPoint = point;
            totalDistance += distance;
        }

        return totalDistance;
    }
}
