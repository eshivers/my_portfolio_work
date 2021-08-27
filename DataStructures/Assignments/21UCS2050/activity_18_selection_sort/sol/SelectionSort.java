/*
 * CS 2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 18 - Selection Sort
 */

import java.util.Arrays;
import java.util.Random;

public class SelectionSort {

    // TODO: implement the selection sort algorithm
    public static void selectionSort(int data[]) {
        /*
        [ 1, 3, 84, 79, 10, 77, 56, 13, 34, 27, 12]
        [ 0,  1,  2,  3,  4,  5,  6, 7,  8,  9, 10]
        i = 1
        j = 1
           min = 10
           k = 2
        temp = 12
         */
        int i = 0; // points to the location where the smallest element is to be placed
        for (int j = 0; j < data.length; j++) {
            int min = j; // assuming that j points to the smallest element
            // the inner loop checks if there is an element that is smaller than data[min]
            // if yes, update min
            for (int k = j + 1; k < data.length; k++)
                if (data[k] < data[min])
                    min = k;
            // at this point, min points to the smallest element
            // now swap element index i and min
            int temp = data[i];
            data[i] = data[min];
            data[min] = temp;
            i++;
        }
    }

    public static void main(String[] args) {

        int data[] = {13, 12, 84, 79, 10, 77, 56, 1, 34, 27, 3};
        System.out.println(Arrays.toString(data));
        selectionSort(data);
        System.out.println(Arrays.toString(data));
    }
}
