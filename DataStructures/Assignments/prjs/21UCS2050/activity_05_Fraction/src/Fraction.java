/*
 * CS 2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 05 - Fraction
 */

public class Fraction {

    // instance variables
    private int numerator;
    private int denominator;

    // class variable
    private static final int DEFAULT_VALUE = 1;

    // TODO: implement the parameter-less constructor
    Fraction() {
        this.numerator = 1;
        this.denominator = 1;
    }

    // TODO: implement the parametrized constructor with one parameter
    Fraction(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    // TODO: implement the parametrized constructor with two parameters
    Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator == 0 ? 1: denominator;
    }

    // TODO: implement getNumerator
    int getNumerator() {
        return this.numerator;
    }

    // TODO: implement getDenominator
    int getDenominator() {
        return this.denominator;
    }

    // TODO: implement setNumerator
    void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    // TODO: implement setDenominator (do not to allow having the denominator set to zero)
    void setDenominator(int denominator) {
        this.denominator = denominator == 0 ? 1 : denominator;
    }

    // TODO: implement getValue
    double getValue() {
        return (double) this.numerator/this.denominator;
    }

    // TODO: correct the error in the code
    int gcd() {
        int a = Math.abs(this.numerator);
        int b = Math.abs(this.denominator);
        int temp = 0;
        while (b != 0) {
            temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // nothing to do here (code is given to you, enjoy)
    void simplify() {
        int gcd = this.gcd();
        this.numerator /= gcd;
        this.denominator /= gcd;
        if (numerator == 0)
            denominator = 1;
        else if ((this.numerator > 0 && this.denominator < 0) || (this.numerator < 0 && this.denominator < 0)) {
            this.numerator *= -1;
            this.denominator *= -1;
        }
    }

    // TODO: implement isNegative
    public boolean isNegative() {
        return this.getValue() < 0;
    }

    // TODO: implement isProper
    public boolean isProper() {
        return Math.abs(this.numerator) > Math.abs(this.denominator);
    }

    // nothing to do here (code is given to you, enjoy)
    @Override
    public String toString() {
        String str = "";
        if (this.numerator == 0)
            str += this.numerator;
        else
        if (this.isProper())
            str += this.numerator + "/" + this.denominator;
        else {
            int whole = this.numerator / this.denominator;
            str += whole;
            if (this.numerator % this.denominator != 0)
                str += " " + (this.numerator - whole * this.denominator) + "/" + this.denominator;
        }
        return str;
    }
}