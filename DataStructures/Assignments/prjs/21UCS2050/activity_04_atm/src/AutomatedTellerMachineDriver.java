/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 04 - AutomatedTellerMachineDriver
 */

import java.util.Random;

public class AutomatedTellerMachineDriver {

    public static void main(String[] args) {
        AutomatedTellerMachine my_atm = new AutomatedTellerMachine();
        int total_withdrawals = 0;
        Random rand = new Random();

        while(my_atm.hasMoney()){
            int random_withdrawal = (rand.nextInt(200) + 1) * 5;
            System.out.println(my_atm.toString());
            System.out.println("Trying to withdraw $" + random_withdrawal + " from ATM");

            Boolean result = my_atm.withdraw(random_withdrawal);

            if(result){
                System.out.println("Success!");
            } else {
                System.out.println("Not enough bills for the request!");
            }
        }
    }

}
