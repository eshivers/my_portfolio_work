/*
 * CS 2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Student Name: Erica Shivers
 * Description: Homework 08 - SortingAlgorithms
 */

import java.util.*;

public class SortingAlgorithms {

    static final int DESCENDING_ORDER = -1;
    static final int ASCENDING_ORDER  =  1;

    // TODO: modify the selection sort algorithm by adding the ability of sorting in ascending or descending order based on the given parameter "order"
    public static void selectionSort(int data[], int order) {
        int i = 0;
        for (int j = 0; j < data.length; j++) {
            int min = j;
            for (int k = j + 1; k < data.length; k++)
                if (order == ASCENDING_ORDER  && data[k] < data[min]){
                    min = k;
                } else if (order == DESCENDING_ORDER && data[k] > data[min]){
                    min = k;
                }
            int temp = data[i];
            data[i] = data[min];
            data[min] = temp;
            i++;
        }
    }

    // TODO: modify the insertion sort algorithm by adding the ability of sorting in ascending or descending order based on the given parameter "order"
    public static void insertionSort(int data[], int order) {
        for (int i = 1; i < data.length; i++) {
            int j = i;
            int k = i - 1;
            while (k >= 0) {
                if ((order == ASCENDING_ORDER && data[j] < data[k]) || (order == DESCENDING_ORDER && data[j] > data[k])) {
                    int temp = data[j];
                    data[j] = data[k];
                    data[k] = temp;
                    j = k;
                    k--;
                }
                else
                    break;
            }
        }
    }

    // TODO: implement a shuffle procedure that swap elements at random a number of times
    public static void shuffle(int data[]) {
        Random r = new Random();
        int number_of_shuffles = r.nextInt(10);
        for(;number_of_shuffles != 0;number_of_shuffles--) {
            for (int i = data.length - 1; i > 0; i--) {

                // Pick a random index from 0 to i
                int j = r.nextInt(i + 1);

                // Swap arr[i] with the element at random index
                int temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int data[] = {13, 12, 84, 79, 10, 77, 56, 1, 34, 27, 3};
        System.out.println(Arrays.toString(data));

        // testing selection sort
        selectionSort(data, ASCENDING_ORDER);
        System.out.println(Arrays.toString(data)); // should display in ascending order
        shuffle(data);
        selectionSort(data, DESCENDING_ORDER);
        System.out.println(Arrays.toString(data)); // should display in descending order

        // testing insertion sort
        shuffle(data);
        insertionSort(data, ASCENDING_ORDER);
        System.out.println(Arrays.toString(data)); // should display in ascending order
        shuffle(data);
        insertionSort(data, DESCENDING_ORDER);
        System.out.println(Arrays.toString(data)); // should display in descending order
    }
}