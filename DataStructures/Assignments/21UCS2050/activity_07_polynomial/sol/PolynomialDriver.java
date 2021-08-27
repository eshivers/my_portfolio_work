/*
 * CS 2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 07 - PolynomialDriver
 */

import java.util.Scanner;

public class PolynomialDriver {

    static final Polynomial readPolynomial(Scanner sc) {
        Polynomial p = new Polynomial();
        System.out.print("Enter the degree of the polynomial: ");
        int degree = sc.nextInt();
        for (int e = degree; e >= 0; e--) {
            System.out.print("Enter coefficient for x^" + e + " term: ");
            double c = sc.nextDouble();
            Term aTerm = new Term(c, e);
            p.addTerm(aTerm);
        }
        return p;
    }

    // TODO: see activity instructions for more information about what to do here
    public static void main(String[] args) {
        Term a = new Term(5, 2);
        System.out.println(a);
        Term b = new Term(5, 2);
        System.out.println(b);
        System.out.println("is " + a + " equal to " + b + "? " + a.equals(b));
        Term aClone = (Term) a.clone();
        System.out.println(aClone);
    }
}
