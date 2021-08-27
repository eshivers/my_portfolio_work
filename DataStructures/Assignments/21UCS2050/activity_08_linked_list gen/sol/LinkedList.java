/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 08 - LinkedList
 */

public class LinkedList<T> {

    private Node<T> head;

    public LinkedList() {
        head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    // add a new element (with the value) in front of the list!
    public void add(T value) {
        Node<T> newNode = new Node<T>(value);
        newNode.setNext(head);
        head = newNode;
    }

    // add a new element (with the value) at the end of the list!
    public void append(T value) {
        Node<T> newNode = new Node<T>(value);
        // special case (list is empty)
        if (isEmpty())
            head = newNode;
            // all other cases
        else {
            Node<T> current = head;
            // have current point to the tail node
            while (current.getNext() != null)
                current = current.getNext();
            current.setNext(newNode);
        }
    }

    @Override
    public String toString() {
        String out = "";
        Node<T> current = head;
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
        Node<T> current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    // return the element at index location
    public T get(int index) {
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        Node<T> current = head;
        for (int i = 0; i < index; i++)
            current = current.getNext();
        return current.getValue();
    }

    // inserts value at the given index location
    // throw an exception if index is invalid
    public void insert(int index, T value) {
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        if (index == 0)
            add(value);
        else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++)
                current = current.getNext();
            Node<T> newNode = new Node<T>(value);
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
            Node<T> temp = head;
            head = head.getNext();
            temp.setNext(null);
        }
        else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++)
                current = current.getNext();
            Node<T> temp = current.getNext();
            current.setNext(temp.getNext()); // bypass the node to be removed...
            temp.setNext(null);
        }
    }
}
