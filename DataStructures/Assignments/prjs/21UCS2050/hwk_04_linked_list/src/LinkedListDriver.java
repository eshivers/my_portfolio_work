/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Homework 04 - LinkedListDriver
 */
// importing library for generating random numbers
import java.util.Random;

public class LinkedListDriver {

    public static void main(String[] args) {
        // TODO #2: create a linked list and add 100 random integers ranging from 0-99 (must use a loop)
        
        Random randomMaker = new Random();
        LinkedList newList = new LinkedList();
        for (int i = 0; i < 100; i++) {
            int random = randomMaker.nextInt(100);
            newList.append(random);
        }
        
        // TODO #3: show the linked list elements using toString
        
        System.out.println(newList.toString());
        
        // TODO #4: show the number of elements of the linked list using size
        
        System.out.println(newList.size());
        
    }
}
