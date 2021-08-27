/*
 * CS 2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Homework 06 - PolynomialDriver
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

    // TODO #3: write a short program that allows the user to enter two polynomials of any degree. The program should then display the result of adding the 2nd polynomial to the 1st one. Below is a typical run of the PolynomialDriver class for your reference.
    public static void main(String[] args) {

    }
}
