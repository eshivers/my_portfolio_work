/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Student(s) Name(s): Erica Shivers
 * Description: Prg 02 - VersioningSystem Class
 */

import java.security.InvalidKeyException;

/** this class demonstrates how a versioning system can be implemented using KeyedObject and KeyedStack */
public class VersioningSystem {

    public static void main(String[] args) {

        // TODO: create a keyed object with key "grocery list" and content "bananas"
        KeyedObject my_grocery = new KeyedObject("grocery list","bananas");

        // TODO: create a keyed stack and push onto it the grocery list keyed object
        KeyedStack grocery_stack = new KeyedStack();
        try{
            grocery_stack.push(my_grocery);
        } catch (Error e){
            System.out.println(e);
        }

        // TODO: display the keyed stack using toString
        System.out.println(grocery_stack);

        // TODO: using peek, clone the latest version of objects in the stack and set its content by appending strawberry to the list
        KeyedObject new_list = (KeyedObject) grocery_stack.peek();
        new_list = (KeyedObject) new_list.clone();
        new_list.setContent(new_list.getContent() + ", strawberry");

        // TODO: push the new keyed object onto the stack
        try{
            grocery_stack.push(new_list);
        } catch (Error e){
            System.out.println(e);
        }

        // TODO: display the keyed stack using toString
        System.out.println(grocery_stack);

        // TODO: create a keyed object with key "books to read" and content "Mobi Dick"
        KeyedObject books_to_read = new KeyedObject("books to read","Mobi Dick");

        // TODO: attempt to push books to read onto the stack; the "Invalid Key" error should be thrown, but the program should NOT crash; instead, the program should display the error message (hint: define a try - catch block)
        try{
            grocery_stack.push(books_to_read);
        } catch (Error e){
            System.out.println(e);
        }

        // TODO: using pop, remove the latest version of the keyed objects from the stack, displaying it on the screen
        System.out.println(grocery_stack.pop());

        // TODO: perform another pop, displaying the resulting keyed object on the screen
        System.out.println(grocery_stack.pop());

        // TODO: now the stack is empty, try to push books to read onto the stack (it should work this time)
        try{
            grocery_stack.push(books_to_read);
        } catch (Error e){
            System.out.println(e);
        }

        // TODO: display the keyed stack using toString
        System.out.println(grocery_stack);
    }
}
