/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 11 - DynamicQueue
 */

public class DynamicQueue<T> {

    private Node<T> front;

    public DynamicQueue() {
        front = null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    // like a linked list append...
    public void push(T value) {
        Node newNode = new Node(value);
        // special case (list is empty)
        if (isEmpty())
            front = newNode;
            // all other cases
        else {
            Node current = front;
            // have current point to the rear node
            while (current.getNext() != null)
                current = current.getNext();
            current.setNext(newNode);
        }
    }

    public T pop() {
        if (!isEmpty()) {
            Node<T> topNode = front;
            front = front.getNext();
            topNode.setNext(null);
            return topNode.getValue();
        }
        else
            throw new Error("Queue is empty!");
    }

    public T peek() {
        if (!isEmpty())
            return front.getValue();
        else
            throw new Error("Queue is empty!");
    }

    public int size() {
        int count = 0;
        Node<T> current = front;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    @Override
    public String toString() {
        String out = "(front) ";
        Node<T> current = front;
        while (current != null) {
            out += current + " ";
            current = current.getNext();
        }
        out += "(rear)";
        return out;
    }
}