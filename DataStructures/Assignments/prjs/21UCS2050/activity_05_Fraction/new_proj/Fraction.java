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
    public Fraction() {
        this.numerator = 1;
        this.denominator = 1;
    }

    // TODO: implement the parametrized constructor with one parameter
    public Fraction(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    // TODO: implement the parametrized constructor with two parameters
    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator == 0 ? 1: denominator;
    }

    // TODO: implement getNumerator
    public int getNumerator() {
        return this.numerator;
    }

    // TODO: implement getDenominator
    public int getDenominator() {
        return this.denominator;
    }

    // TODO: implement setNumerator
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    // TODO: implement setDenominator (do not to allow having the denominator set to zero)
    void setDenominator(int denominator) {
        if(denominator == 0){
            return;
        } else  {
            this.denominator = denominator;
        }
    }

    // TODO: implement getValue
    public double getValue() {
        return (double) this.numerator/this.denominator;
    }

    // TODO: correct the error in the code
    public int gcd() {
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
    public void simplify() {
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
        return Math.abs(this.getValue()) < 1;
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