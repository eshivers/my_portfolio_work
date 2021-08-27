/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 25 - DataPoint
 */

import java.util.Arrays;

public class DataPoint {

    private double attributes[];
    private int    target;
    private int    numberTargets;

    public DataPoint(int numberAttributes, int target, int numberTargets) {
        attributes = new double[numberAttributes];
        this.target = target;
        this.numberTargets = numberTargets;
    }

    public DataPoint(int numberAttributes, int target) {
        this(numberAttributes, target, 1);
    }

    public DataPoint(int numberAttributes) {
        this(numberAttributes, 0, 0);
    }

    public void setAttribute(int index, double value) {
        attributes[index] = value;
    }

    public int getNumberAttributes() {
        return attributes.length;
    }

    public int getTarget() {
        return target;
    }

    public int getNumberTargets() {
        return numberTargets;
    }

    public double getAttribute(int index) {
        return attributes[index];
    }

    public void setNumberTargets(int numberTargets) {
        this.numberTargets = numberTargets;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "attributes=" + Arrays.toString(attributes) +
                ", target=" + target +
                ", #targets=" + numberTargets +
                '}';
    }
}
