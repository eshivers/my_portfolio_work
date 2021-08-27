/*
 * CS 2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 19 - Insertion Sort
 */

import java.util.Arrays;
import java.util.Random;

public class InsertionSort {

    // TODO: implement the insertion sort algorithm
    public static void insertionSort(int data[]) {
        /*
        [ 1, 3, 10, 56, 77, 79, 84, 13, 34, 27, 12]
        [ 0, 1,  2,  3,  4,  5,  6, 7,  8,  9, 10]
        i = 6
           j = 3
           k = 2
         */
        for (int i = 1; i < data.length; i++) {
            int j = i;
            int k = i - 1;
            while (k >= 0) {
                if (data[j] < data[k]) {
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

    public static void main(String[] args) {

        int data[] = {13, 12, 84, 79, 10, 77, 56, 1, 34, 27, 3};
        System.out.println(Arrays.toString(data));
        insertionSort(data);
        System.out.println(Arrays.toString(data));
    }
}
