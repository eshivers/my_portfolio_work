/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 02 - Account
 */

public class Account {

    int    number;
    String owner;
    double balance;

    Account(int number, String owner) {
        this.number = number;
        this.owner  = owner;
        balance = 0;
    }

    void deposit(double amount) {
        balance += amount;
    }

    boolean withdraw(double amount) {
        if (balance - amount >= 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return number + ", " + owner + ", US$ " + balance;
    }

}
