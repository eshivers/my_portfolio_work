/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Student(s) Name(s): Erica Shivers
 * Description: Prg02 - KeyedStack
 */

import java.security.InvalidKeyException;

/** A keyed stack is a dynamic stack with a key (always of type string);  all of the objects in a stack must implement the HasKey interface (like keyed objects) and they must share the same key */
public class KeyedStack<T extends HasKey> {

    private String  key;
    private Node<T> top;

    public KeyedStack() {
        top = null;
        key = null;
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

    // TODO: push the given value onto the stack ONLY if the value has the same key, throw an "Invalid key" error otherwise; set the stack's key upon the 1st push
    public void push(T value) throws Error {
        if(!this.isEmpty() && value.getKey().equals(this.getKey())){
            this.add(value);
        } else if(this.isEmpty()) {
            this.key = value.getKey();
            this.add(value);
        } else {
            throw new Error("Invalid Key!");
        }
    }

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

    // TODO: return the stack's current key
    public String getKey() {
        return this.key;
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
