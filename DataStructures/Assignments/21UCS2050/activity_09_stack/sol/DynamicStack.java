/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 09 - DynamicStack
 */

public class DynamicStack<T> {

    private Node<T> top;

    public DynamicStack() {
        top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    // add is a helper method
    private void add(T value) {
        Node<T> newNode = new Node<T>(value);
        newNode.setNext(top);
        top = newNode;
    }

    // TODOd: push the given value onto the stack
    public void push(T value) {
        add(value);
    }

    // TODOd: returns the element that is currently on top of the stack
    // if stack is empty, throw an error
    public T pop() {
        if (!isEmpty()) {
            Node<T> topNode = top;
            top = top.getNext();
            topNode.setNext(null);
            return topNode.getValue();
        }
        else
            throw new Error("Stack is empty!");
    }

    public T peek() {
        if (!isEmpty())
            return top.getValue();
        else
            throw new Error("Stack is empty!");
    }

    public int size() {
        int count = 0;
        Node<T> current = top;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    @Override
    public String toString() {
        String out = "(top) ";
        Node<T> current = top;
        while (current != null) {
            out += current + " ";
            current = current.getNext();
        }
        out += "(bottom)";
        return out;
    }
}
