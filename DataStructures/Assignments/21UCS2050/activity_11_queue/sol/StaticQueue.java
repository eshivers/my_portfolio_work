/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 11 - DynamicQueue
 */

public class StaticQueue<T> {

    private int size;
    private T   data[];
    private int front, rear; // refactor to get rid off rear
    private static final int MIN_CAPACITY = 2;

    public StaticQueue(int capacity) {
        if (capacity >= MIN_CAPACITY)
            data = (T[]) new Object[capacity];
        else
            data = (T[]) new Object[MIN_CAPACITY];
        size = 0;
        front = rear = 0;
    }

    public boolean isFull() {
        return size == data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(T value) {
        if (!isFull()) {
            data[rear] = value;
//            rear++;
//            if (rear == data.length)
//                rear = 0;
            rear = (rear + 1) % data.length; // more elegant way of doing it...
            size++;
        }
        else
            throw new Error("Queue is full!");
    }

    public T pop() {
        if (!isEmpty()) {
            T temp = data[front];
            front = (front + 1) % data.length;
            size--;
            return temp;
        }
        else
            throw new Error("Queue is empty!");
    }

    public T peek() {
        if (!isEmpty()) {
            return data[front];
        }
        else
            throw new Error("Queue is empty!");
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    @Override
    public String toString() {
        String out = "(front) ";
        int pos = front;
        for (int i = 0; i < size; i++) {
            out += data[pos] + " ";
            pos = (pos + 1) % data.length;
        }
        out += "(rear)";
        return out;
    }
}
