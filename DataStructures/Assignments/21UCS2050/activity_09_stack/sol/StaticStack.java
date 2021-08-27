/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 09 - StaticStack
 */

public class StaticStack<T> {

    private int size;
    private T   data[];
    private static final int MIN_CAPACITY = 2;

    public StaticStack(int capacity) {
        if (capacity >= MIN_CAPACITY)
            data = (T[]) new Object[capacity];
        else
            data = (T[]) new Object[MIN_CAPACITY];
        size = 0;
    }

    public boolean isFull() {
        return size == data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // TODOd: push the given value onto the stack
    // if stack is full, throw an error
    public void push(T value) {
        if (!isFull()) {
            data[size] = value;
            size++;
        }
        else
            throw new Error("Stack is full!");
    }

    // TODOd: returns the element that is currently on top of the stack
    // if stack is empty, throw an error
    public T pop() {
        if (!isEmpty()) {
            size--;
            T temp = data[size];
            data[size] = null;
            return temp;
        }
        else
            throw new Error("Stack is empty!");
    }

    public T peek() {
        if (!isEmpty()) {
            return data[size - 1];
        }
        else
            throw new Error("Stack is empty!");
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    @Override
    public String toString() {
        String out = "(top) ";
        for (int i = size - 1; i >= 0; i--)
            out += data[i] + " ";
        out += "(bottom)";
        return out;
    }
}
