/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 09 - StaticStackDriver
 */

public class StackDriver {

    public static void main(String[] args) {

        // TODOd: instantiate a stack of int with capacity of 5
//        StaticStack<Integer> stackOfInt = new StaticStack<>(5);
        // push 5, 8, 3, 9, 2, 10 (error)
//        System.out.println(stackOfInt);
//        stackOfInt.push(5);
//        System.out.println(stackOfInt);
//        stackOfInt.push(8);
//        System.out.println(stackOfInt);
//        stackOfInt.push(3);
//        System.out.println(stackOfInt);
//        stackOfInt.push(9);
//        System.out.println(stackOfInt);
//        stackOfInt.push(2);
//        System.out.println(stackOfInt);
        // stackOfInt.push(10);
//        System.out.println(stackOfInt.pop());
//        System.out.println(stackOfInt);
//        System.out.println(stackOfInt.pop());
//        System.out.println(stackOfInt);
//        System.out.println(stackOfInt.pop());
//        System.out.println(stackOfInt);
//        System.out.println(stackOfInt.pop());
//        System.out.println(stackOfInt);
//        System.out.println(stackOfInt.pop());
//        System.out.println(stackOfInt);
//        System.out.println(stackOfInt.pop());

        // TODO: instantiate a stack of string with capacity of 3
        StaticStack<String> stackOfString = new StaticStack<>(3);
        // push "Bob", "Janet", "Mark", "Steve"
        System.out.println(stackOfString);
        stackOfString.push("Bob");
        System.out.println(stackOfString);
        stackOfString.push("Janet");
        System.out.println(stackOfString);
        stackOfString.push("Mark");
        System.out.println(stackOfString);
//        stackOfString.push("Steve");
        stackOfString.pop();
        System.out.println(stackOfString);
        stackOfString.pop();
        System.out.println(stackOfString);
        stackOfString.pop();
        System.out.println(stackOfString);
//        stackOfString.pop();
    }
}
