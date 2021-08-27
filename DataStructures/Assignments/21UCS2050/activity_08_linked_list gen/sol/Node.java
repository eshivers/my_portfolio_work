/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 08 - Node
 */

public class Node<T> {

    private T    value;
    private Node next;

    public Node(T value) {
        this.value = value;
        next = null;
    }

    public T getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
