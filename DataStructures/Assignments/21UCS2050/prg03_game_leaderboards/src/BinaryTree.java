/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Prg03 - BinaryTree
 */

import java.util.Iterator;

public class BinaryTree<T extends Comparable<T>> implements Iterable<T> {

    private BinNode<T> root;

    public BinaryTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private BinNode<T> addRecursively(BinNode<T> current, final T data) {
        if (current == null)
            return new BinNode<>(data);
        if (data.compareTo(current.getData()) < 0) {
            BinNode<T> returnedNode = addRecursively(current.getLeft(), data);
            current.setLeft(returnedNode);
        }
        else {
            BinNode<T> returnedNode = addRecursively(current.getRight(), data);
            current.setRight(returnedNode);
        }
        return current;
    }

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

    public boolean search(final T data) {
        return searchRecursively(root, data);
    }

    private String preOrder(final BinNode<T> current) {
        if (current != null)
            return current.getData() + "\n" + preOrder(current.getLeft()) + preOrder(current.getRight());
        return "";
    }

    private String inOrder(final BinNode<T> current) {
        if (current != null)
            return inOrder(current.getLeft()) + current.getData() + "\n" + inOrder(current.getRight());
        return "";
    }

    private String postOrder(final BinNode<T> current) {
        if (current != null)
            return postOrder(current.getLeft()) + postOrder(current.getRight()) + current.getData() + "\n";
        return "";
    }

    @Override
    public String toString() {
        return inOrder(root);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private DynamicQueue<BinNode<T>> queue = root == null ? new DynamicQueue<>() : new DynamicQueue<>(root);

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                if (hasNext()) {
                    BinNode<T> current = queue.pop();
                    if (current.getLeft() != null)
                        queue.push(current.getLeft());
                    if (current.getRight() != null)
                        queue.push(current.getRight());
                    return current.getData();
                }
                return null;
            }
        };
    }
}
