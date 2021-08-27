/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Homework 04 - LinkedList
 */

public class LinkedList {

    private Node head;

    public LinkedList() {
        head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    // add a new element (with the value) in front of the list!
    public void add(int value) {
        Node newNode = new Node(value);
        newNode.setNext(head);
        head = newNode;
    }

    // add a new element (with the value) at the end of the list!
    public void append(int value) {
        Node newNode = new Node(value);
        // special case (list is empty)
        if (isEmpty())
            head = newNode;
            // all other cases
        else {
            Node current = head;
            // have current point to the tail node
            while (current.getNext() != null)
                current = current.getNext();
            current.setNext(newNode);
        }
    }

    @Override
    public String toString() {
        String out = "";
        Node current = head;
        while (current != null) {
            out += current + " ";
            current = current.getNext();
        }
        // optional (get rid off the last space)
        out = out.trim();
        return out;
    }

    // TODO #1: return the # of elements in the linked list
    public int size() {
        return 0;
    }
}
