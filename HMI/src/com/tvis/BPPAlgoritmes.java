package com.tvis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BPPAlgoritmes {

// two ways to pack boxes as tight as possible

    public static final String[] availableAlgoritmes = new String[]{"Next Fit Decreasing", "First Fit Decreasing"};

    static ArrayList<Box> nextFitDecreasing(Order order, int boxSize) {

        // get all products
        ArrayList<Product> products = order.getProductList();

        // get all product sizes
        Integer[] weight = getSizesProducts(products);

        // sort in decreasing order of size
        Arrays.sort(weight, Collections.reverseOrder());

        // create an array to store boxes
        ArrayList<Box> boxes = new ArrayList<>();

        // add first box
        boxes.add(new Box(boxSize));

        // get remaining size of the box
        int bin_rem = boxes.get(0).getRemainingSize();

        // Place items one by one
        for (Integer size : weight) {
            for (Product product : products) {
                if (product.getSize() == size && !product.isPacked()) {
                    if (size > bin_rem) { // If this item can't fit in current bin

                        boxes.add(new Box(boxSize));

                        boxes.get(boxes.size()-1).addProduct(product);
                        bin_rem = boxSize - size; // get remaining box size
                    } else {
                        bin_rem -= size;
                        boxes.get(boxes.size()-1).addProduct(product);
                    }
                    // set product to 'packed'
                    product.setPacked(true);
                }
            }
        }
        return boxes;
    }

    static ArrayList<Box> firstFitDecreasing(Order order, int boxSize) {

        // get all products
        ArrayList<Product> products = order.getProductList();

        // get all product sizes
        Integer[] weight = getSizesProducts(products);

        // sort all weights in decreasing order
        Arrays.sort(weight, Collections.reverseOrder());



        // Create an array to store boxes and add first box
        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(new Box(boxSize));

        // Place items one by one
        for (Integer size : weight) {
            for(Product product : products) {
                if(product.getSize() == size && !product.isPacked()) {

                    // Find the first bin that can accommodate
                    int j;
                    for (j = 0; j < boxes.size(); j++) {
                        if (boxes.get(j).getRemainingSize() >= size) {
                            boxes.get(j).addProduct(product);
                            product.setPacked(true);
                            break;
                        }
                    }

                    // If no bin could accommodate the size
                    if (!product.isPacked()) {
                        boxes.add(new Box(boxSize));
                        boxes.get(boxes.size()-1).addProduct(product);
                        product.setPacked(true);
                    }
                }
            }
        }
        return boxes;
    }

    private static Integer[] getSizesProducts(ArrayList<Product> products) {

        // array for the sizes
        ArrayList<Integer> sizes = new ArrayList<>();

        // add all sizes
        for(Product product : products) {
            sizes.add(product.getSize());
        }

        // convert to Integer[]
        Integer[] arr = new Integer[sizes.size()];
        arr = sizes.toArray(arr);

        return arr;
    }
}
