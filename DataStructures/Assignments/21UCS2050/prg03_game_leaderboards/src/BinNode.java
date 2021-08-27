/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Prg03 - BinNode
 */

public class BinNode<T> {

    private BinNode<T> left, right;
    private T data;

    public BinNode(T data) {
        left = right = null;
        this.data = data;
    }

    public BinNode<T> getLeft() {
        return left;
    }

    public BinNode<T> getRight() {
        return right;
    }

    public T getData() {
        return data;
    }

    public void setLeft(BinNode<T> left) {
        this.left = left;
    }

    public void setRight(BinNode<T> right) {
        this.right = right;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}