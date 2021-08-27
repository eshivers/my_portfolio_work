/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Homework 05 - LinkedList
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

    // returns the # of elements -> assigned as homework!
    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    // return the element at index location
    public int get(int index) {
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        Node current = head;
        for (int i = 0; i < index; i++)
            current = current.getNext();
        return current.getValue();
    }

    // inserts value at the given index location
    // throw an exception if index is invalid
    public void insert(int index, int value) {
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        if (index == 0)
            add(value);
        else {
            Node current = head;
            for (int i = 0; i < index - 1; i++)
                current = current.getNext();
            Node newNode = new Node(value);
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }
    }

    // removes the element at the given index location
    // throw an exception if index is invalid
    public void remove(int index) {
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        if (index == 0) {
            Node temp = head;
            head = head.getNext();
            temp.setNext(null);
        }
        else {
            Node current = head;
            for (int i = 0; i < index - 1; i++)
                current = current.getNext();
            Node temp = current.getNext();
            current.setNext(temp.getNext()); // bypass the node to be removed...
            temp.setNext(null);
        }
    }

    // TODO: reverses the order of elements in the original list.  For example, if the original list is “1, 2, 3”, after calling invert the list should read “3, 2, 1”.
    public void invert() {

    }
}