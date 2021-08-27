/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 06 - LinkedListDriver
 */

public class LinkedListDriver {

    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        System.out.println("Is empty? " + ll.isEmpty());
        System.out.println(ll);
        ll.add(9);
        System.out.println("Is empty? " + ll.isEmpty());
        System.out.println(ll);
        ll.add(3);
        System.out.println("Is empty? " + ll.isEmpty());
        System.out.println(ll);
        ll.add(8);
        System.out.println("Is empty? " + ll.isEmpty());
        System.out.println(ll);
        ll.add(5);
        System.out.println("Is empty? " + ll.isEmpty());
        System.out.println(ll);
    }
}
