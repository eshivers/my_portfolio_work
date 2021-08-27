/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 04 - AutomatedTellerMachine
 */

public class AutomatedTellerMachine {
    int bills_50;
    int bills_20;
    int bills_10;
    int bills_5;

    AutomatedTellerMachine(){
        this.bills_50 = 10;
        this.bills_20 = 10;
        this.bills_10 = 10;
        this.bills_5 = 10;
    }

    AutomatedTellerMachine(int bills_50,int bills_20,int bills_10,int bills_5){
        this.bills_50 = bills_50;
        this.bills_20 = bills_20;
        this.bills_10 = bills_10;
        this.bills_5 = bills_5;
    }

    public int getBalance(){
        return (this.bills_50 * 50) + (this.bills_20 * 20) + (this.bills_10 * 10) + (this.bills_5 * 5);
    }

    public Boolean hasMoney(){
        if(this.bills_50 > 0 || this.bills_20 > 0 || this.bills_10 > 0  || this.bills_5 > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "ATM has " + getBalance() +
                ": " +  bills_50 + " x $50 + " +
                         bills_20 + " x $20 + "+
                        bills_10 + " x $10 + " +
                        bills_5 + " x $5";
    }

    public Boolean withdraw(int amount){
        Boolean result = null;
        int calc_amount = 0;
        int temp_amount = amount;
        int initial_values[] = {this.bills_50,this.bills_20,this.bills_10,this.bills_5};
        while(hasMoney() && calc_amount != amount){
            System.out.println(calc_amount);
            System.out.println("Temp: " + temp_amount);
            if(temp_amount >= 50 && this.bills_50 > 0){
                calc_amount += 50;
                temp_amount -= 50;
                this.bills_50--;
            } else if(temp_amount >= 20 && (this.bills_50 == 0 || temp_amount < 50) && this.bills_20 > 0){
                calc_amount += 20;
                temp_amount -= 20;
                this.bills_20--;
            } else if(temp_amount >= 10 && (this.bills_20 == 0 || temp_amount < 20) && this.bills_10 > 0){
                calc_amount += 10;
                temp_amount -= 10;
                this.bills_10--;
            } else if(temp_amount >= 5 && (this.bills_10 == 0 || temp_amount < 10) && this.bills_5 > 0) {
                calc_amount += 5;
                temp_amount -= 5;
                this.bills_5--;
            } else {
                break;
            }
        }
        if(calc_amount == amount){
            result = true;
        } else {
            this.bills_50 = initial_values[0];
            this.bills_20 = initial_values[1];
            this.bills_10 = initial_values[2];
            this.bills_5 = initial_values[3];
            result = false;
        }
        return result;
    }
}
