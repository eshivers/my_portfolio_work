/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Student Names: Erica Shivers
 * Description: Prg01 - Node Class
 */

public class Node {

    private Element element;
    private int     amount;
    private Node    next;

    public static final int DEFAULT_AMOUNT = 1;

    public Node(final Element element, int amount) {
        this.element = element;
        if (amount < DEFAULT_AMOUNT)
            this.amount = DEFAULT_AMOUNT;
        else
            this.amount = amount;
        next = null;
    }

    public Node(final Element element) {
        this(element, DEFAULT_AMOUNT);
    }

    public Element getElement() {
        return element;
    }

    public int getAmount() {
        return amount;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
