/*
 * CS 2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 20 - Merge Sort (solved)
 */

import java.util.Arrays;

public class MergeSortSolved {

    // TODOd: implement the merge portion of merge sort
    public static void merge(int data[], int begin, int middle, int end) {
        int i = begin;
        int j = middle + 1;
        int size = end - begin + 1;
        int sorted[] = new int[size];
        int k = 0;
        while (i <= middle && j <= end)
            if (data[i] < data[j])
                sorted[k++] = data[i++];
            else
                sorted[k++] = data[j++];
        while (i <= middle)
            sorted[k++] = data[i++];
        while (j <= end)
            sorted[k++] = data[j++];
        for (i = begin, k = 0; k < size; i++, k++)
            data[i] = sorted[k];
    }

    // TODOd: implement the merge sort algorithm
    public static void mergeSort(int data[], int begin, int end) {
        // base case
        if (begin >= end)
            return;

        // divide
        int middle = (begin + end) / 2;
        mergeSort(data, begin, middle);
        mergeSort(data, middle + 1, end);

        // conquer (merge)
        merge(data, begin, middle, end);
    }

    public static void main(String[] args) {

        int data[] = {13, 12, 84, 79, 10, 77, 56, 1, 34, 27, 3};
        System.out.println(Arrays.toString(data));
        mergeSort(data, 0, data.length - 1);
        System.out.println(Arrays.toString(data));
    }
}

