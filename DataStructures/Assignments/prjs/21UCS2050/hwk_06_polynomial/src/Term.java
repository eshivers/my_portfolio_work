/*
 * CS 2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Homework 06 - Term
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

    // TODOd: add the given term's coefficient to the callee term's coefficient if and only if they have the same exponent value
    /*
    Term a = new Term(5, 2); // 5x^2
    Term b = new Term(3, 2); // 3x^2
    a.add(b); // effect of this call is a becoming 8x^2
     */
    public void add(final Term other) {
        if (exponent == other.exponent)
            coefficient += other.coefficient;
    }

    // TODOd: two terms are considered to be equal if they have the same values for their coefficient and exponent; note that coefficient is a floating-point value, so make sure you accept a TOLERANCE when comparing two coefficients for equality
    /*
    Term a = new Term(5, 2); // 5x^2
    Term b = new Term(3, 2); // 3x^2
    a.equals(b); // return false
     */
    @Override
    public boolean equals(Object obj) {
        Term other = (Term) obj; // type cast
        return exponent == other.exponent && Math.abs(coefficient - other.coefficient) <= TOLERANCE;
    }

    // TODOd: use the following examples to figure it out the format to use:
    //  8x^3
    //  -2x^2
    //  7x
    //  3.2
    @Override
    public String toString() {
        if (exponent == 0)
           return coefficient + "";
        else if (exponent == 1)
            return coefficient + "x";
        else
            return coefficient + "x^" + exponent;
    }

    // TODOd: return a new term that has the same coefficient and exponent of the callee term
    // Term a = new Term(5, 2); // 5x^2
    // Term aClone = a.clone(); // the effect of that is aClone references a new term that is an exact copy of the term referenced by a
    @Override
    public Object clone()  {
        return new Term(coefficient, exponent);
    }
}
