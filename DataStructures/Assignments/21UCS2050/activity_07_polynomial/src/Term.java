/*
 * CS 2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 07 - Term
 */

public class Term {

    private double coefficient;
    private int    exponent;
    private Term   next;

    private static final double TOLERANCE = 0.00000001;
    private static final double DEFAULT_COEFFICIENT = 0;

    public Term(double coefficient, int exponent) {
        if (Math.abs(coefficient) <= TOLERANCE)
            this.coefficient = DEFAULT_COEFFICIENT;
        else
            this.coefficient = coefficient;
        this.exponent = exponent;
        this.next = null;
    }

    public Term(double coefficient) {
        this(coefficient, 0);
    }

    public Term() {
        this(1, 0);
    }

    public double getCoefficient() {
        return coefficient;
    }

    public int getExponent() {
        return exponent;
    }

    public Term getNext() {
        return next;
    }

    public void setNext(final Term next) {
        this.next = next;
    }

    // TODO: add the given term's coefficient to the callee term's coefficient if and only if they have the same exponent value
    public void add(final Term other) {

    }

    // TODO: two terms are considered to be equal if they have the same values for their coefficient and exponent; note that coefficient is a
    //  floating-point value, so make sure you accept a TOLERANCE when comparing two coefficients for equality
    @Override
    public boolean equals(Object obj) {
        return false;
    }

    // TODO: use the following examples to figure it out the format to use:
    //  8x^3
    //  -2x^2
    //  7x
    //  3.2
    @Override
    public String toString() {

    }

    // TODO: return a new term that has the same coefficient and exponent of the callee term
    @Override
    public Object clone()  {
        return null;
    }
}
