/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 22 - BinaryTree
 */

public class BinaryTree<T extends Comparable<T>> {

    private BinNode<T> root;

    public BinaryTree() {
        root = null;
    }

    // TODOd: implement the isEmpty method
    public boolean isEmpty() {
        return root == null;
    }

    // TODOd: implement the addRecursively private method
    private BinNode<T> addRecursively(BinNode<T> current, final T data) {

        // TODOd: check if current is null, if that is the case, return a new BinNode with the data
        if (current == null)
            return new BinNode<>(data);

        // TODOd: if current is not null...
        // TODOd: if given data is smaller than current data, call addRecursively to the left, making sure to use the method's return to set current's left node
        if (data.compareTo(current.getData()) < 0) {
            BinNode<T> returnedNode = addRecursively(current.getLeft(), data);
            current.setLeft(returnedNode);
        }

        // TODOd: if given data is greater than current data, call addRecursively to the right, making sure to use the method's return to set current's right node
        else {
            BinNode<T> returnedNode = addRecursively(current.getRight(), data);
            current.setRight(returnedNode);
        }

        // TODOd: don't forget to return current
        return current;
    }

    // TODOd: implement the add method
    public void add(final T data) {
        if (isEmpty())
            root = new BinNode<>(data);
        else
            addRecursively(root, data);
    }

    public boolean searchRecursively(final BinNode<T> current, final T data) {
        if (current == null)
            return false;
        if (data.compareTo(current.getData()) == 0)
            return true;
        else if (data.compareTo(current.getData()) < 0)
            return searchRecursively(current.getLeft(), data);
        else
            return searchRecursively(current.getRight(), data);
    }

    // TODO: implement the search method
    public boolean search(final T data) {
        return searchRecursively(root, data);
    }

    // TODOd: override the toString method using a breadth first tree traversal using a queue of binary nodes
    @Override
    public String toString() {
        if (isEmpty())
            return "";
        DynamicQueue<BinNode<T>> queue = new DynamicQueue<>();
        queue.push(root); // root is NOT null...
        String out = "";
        while (!queue.isEmpty()) {
            BinNode<T> current = queue.pop();
            out += current.toString() + " ";
            if (current.getLeft() != null)
                queue.push(current.getLeft());
            if (current.getRight() != null)
                queue.push(current.getRight());
        }
        return out.strip();
    }
}
