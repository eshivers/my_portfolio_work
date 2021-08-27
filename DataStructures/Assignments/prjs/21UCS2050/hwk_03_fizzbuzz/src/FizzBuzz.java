/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Homework 03 - FizzBuzz
 */

/** Represents the FizzBuzz test.
 * @author Erica Shivers
 * @version 1.0
 */

public class FizzBuzz {

    private int current;
    private static final int DEFAULT_START = 1;

    /**
     * Creates a FizzBuzz class with default value
     */
    // TODO: implement a parameter-less constructor that sets the current value to the default start (ie, 1)
    public FizzBuzz(){
        this.current = DEFAULT_START;
    }

    // TODO: implement a parameterized constructor that sets the current value based on the user provided value;
    //  if start is less than 1, set current to the default start (ie, 1)

    /**
     * Creates a FizzBuzz object with the provided value
     * If the provide value is less than 1 it assigns the DEFAULT_START value
     * @param current
     */
    public FizzBuzz(int current){
        this.current = (current < DEFAULT_START ? DEFAULT_START : current);
    }

    // TODO: implement the getter method that should return the value of current
    /**
     * Gets the value of the "current" instance variable
     * @return The integer value stored within the "current" instance variable
     */
    public int getCurrent() {
        return this.current;
    }

    // TODO: implement next which should increment current by 1 unit
    /**Increments the value of the "current" instance variable
     */
    public void next(){
        this.current += 1;
    }

    // TODO: override toString which should return the current number as a string, or the words "Fizz", "Buzz", or
    //  "FizzBuzz" depending whether the current number is a multiple of 3, 5, or both, respectively
    /**Overrides the toString function to generate a customized String conversion for objects of this class
     * @return Depending on the value of "current" instance variable, either the integer value or "Fizz", or "Buzz" or "FizzBuzz" is
     * returned
     */
    @Override
    public String toString() {
        if(this.current % 3 == 0 && this.current % 5 != 0){
            return "Fizz";
        } else if(this.current % 5 == 0 && this.current % 3 != 0){
            return "Buzz";
        } else if(this.current % 5 == 0 && this.current % 3 == 0){
            return "FizzBuzz";
        } else {
            return Integer.toString(this.current);
        }
    }
}