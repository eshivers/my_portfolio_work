/*
 * CS 2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 21 - SortingObjects
 */

import javax.sound.sampled.Line;
import java.io.*;
import java.util.*;

public class SortingObjects {

    private static final String NAMES_FILENAME = "names.txt";

    // TODO: merge the two given lists returning a new list with the elements sorted
    public static LinkedList<Student> merge(LinkedList<Student> left, LinkedList<Student> right) {
        LinkedList<Student> sorted = new LinkedList<>();
        int i = 0; // index to refer to the left list
        int j = 0; // index to refer to the right list
        while (i < left.size() && j < right.size()) {
            Student studentLeft  = left.get(i);
            Student studentRight = right.get(j);
            if (studentLeft.compareTo(studentRight) < 0) {
                sorted.append(studentLeft);
                i++;
            }
            else {
                sorted.append(studentRight);
                j++;
            }
        }
        while (i < left.size()) {
            sorted.append(left.get(i));
            i++;
        }
        while (j < right.size()) {
            sorted.append(right.get(j));
            j++;
        }
        // clear left and right lists
//        left.clear();
//        right.clear();
        return sorted;
    }

    // TODO: implement merge sort using a linked list
    public static LinkedList<Student> mergeSort(LinkedList<Student> students) {

        // TODOd: implement the base case
        if (students.size() <= 1)
            return students;

        // TODOd: divide the students list into two lists: left and right
        int middle = students.size() / 2;
        LinkedList<Student> left = new LinkedList<>();
        LinkedList<Student> right = new LinkedList<>();
        for (int i = 0; i < middle; i++)
            left.append(students.get(i));
        for (int i = middle; i < students.size(); i++)
            right.append(students.get(i));

        // TODOd: recursively call mergeSort w/ left and right linked lists
        left  = mergeSort(left);
        right = mergeSort(right);

        // TODOd: return the result of merging left and right linked lists
        return merge(left, right);
    }

    public static void main(String[] args) throws FileNotFoundException {

        LinkedList<Student> students = new LinkedList<>();
        Scanner in = new Scanner(new FileInputStream(NAMES_FILENAME));
        Random r = new Random();
//        int id = 1;
        while (in.hasNextLine()) {
            String name = in.nextLine();
            int id =  r.nextInt(100) + 1;
            Student student = new Student(id, name);
            students.append(student);
            id++;
        }
        in.close();
        System.out.println(students);
        LinkedList<Student> studentsSorted = mergeSort(students);
        System.out.println(studentsSorted);
    }
}

