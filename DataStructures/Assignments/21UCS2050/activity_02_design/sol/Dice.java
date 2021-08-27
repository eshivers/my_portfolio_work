/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 02 - Dice
 */

import java.util.Random;

/**
A dice is defined by a number of sides (at least 2) and a value that keeps the dice’s current value (always set to 1 upon initialization).
 */
public class Dice {

    private static final int MIN_SIDES     = 2;
    private static final int DEFAULT_SIDES = 6;
    private static final int INITIAL_VALUE = 1;
    private int sides;
    private int value;

    /**
     * Creates a dice given its number of sides. If the informed value is < 2, set it to 2.
     * @param sides number of sides
     */
    public Dice(int sides) {
        if (sides >= MIN_SIDES)
            this.sides = sides;
        else
            this.sides = MIN_SIDES;
        value = INITIAL_VALUE;
    }

    /**
     * Creates a dice without informing its number of sides (in that case, the number of sides is set to 6).
     */
   public Dice() {
        this.sides = DEFAULT_SIDES;
        value = INITIAL_VALUE;
   }

    /**
     * Returns the dice’s current value.
     * @return the dice's current value
     */
   public int getValue() {
        return value;
   }

    /**
     * Randomly picks another value for the dice, based on the number of sides.
     */
   public void roll() {
       value = new Random().nextInt(sides) + 1;
   }
}
