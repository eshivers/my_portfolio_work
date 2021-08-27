/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 22 - BinaryTreeDriver
 */

public class BinaryTreeDriver {

    // TODO: create a binary tree and add the data elements to reproduce the tree described in the lecture about trees; don't forget to print your tree at the end
    public static void main(String[] args) {
        BinaryTree<String> binTree = new BinaryTree<>();
        System.out.println(binTree);
        binTree.add("janet");
        System.out.println(binTree);
        binTree.add("alex");
        binTree.add("paul");
        System.out.println(binTree);
        binTree.add("xavier");
        binTree.add("brenna");
        System.out.println(binTree);
        binTree.add("anthony");
        binTree.add("carla");
        System.out.println(binTree);
        System.out.println(binTree.search("carla"));
        System.out.println(binTree.search("thyago"));

    }
}
