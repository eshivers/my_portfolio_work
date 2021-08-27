/*
A sandwich is defined by a name, number of calories (integer is fine), and price.  Users should be able to create a sandwich given its name, number of calories, and price.  If the informed number of calories or price is negative, make sure those values are initialized to zero. Define “toString” and a method called “isHealthy” that returns true if the number of calories is less than 250, false otherwise. Define 250 as a class variable (static) constant (final).
 */
public class Sandwich {

    // instance variables
    private String name;
    private int    calories;
    private double price;

    // class variable
    private static final int HEALTHY_CALORIES = 250;

    // constructor (special method to create object)
    public Sandwich(String name, int calories, double price) {
        if (calories < 0)
            this.calories = 0;
        else
            this.calories = calories;
        if (price < 0)
            this.price = 0;
        else
            this.price = price;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " has " + calories + " calories and it costs US$" + price;
    }

    public boolean isHealthy() {
        if (calories <= HEALTHY_CALORIES)
            return true;
        else
            return false;
    }
}
