/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 11 - DynamicQueue
 */

public class QueueDriver {

    public static void main(String[] args) {
        DynamicQueue<Integer> queue = new DynamicQueue<>();
        System.out.println(queue);
        queue.push(5);
        queue.push(8);
        queue.push(3);
        System.out.println(queue);
        System.out.println("pop returned: " + queue.pop());
        System.out.println(queue);
        queue.push(2);
        System.out.println(queue);
    }
}