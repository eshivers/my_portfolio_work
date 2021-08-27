/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 25 - Decision
 */

public class Decision extends DecisionTreeData {

    private int    attribute;
    private double value;

    public Decision(int attribute, double value) {
        this.attribute = attribute;
        this.value = value;
    }

    public int getAttribute() {
        return attribute;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "<" + attribute + ", " + String.format("%.2f", value) + ">";
    }
}
