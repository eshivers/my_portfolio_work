/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 03 - BeeDriver
 */

import java.util.Random;

public class BeeDriver {

    public static void main(String[] args) {
        final int TOTAL_BEES = 15;

        Bee my_beehive[] = new Bee[TOTAL_BEES];
        String beehive_name = "Crazy_bees";
        my_beehive[0] = new Bee(beehive_name,2);

        for(int i = 1; i < 14;i++){
            my_beehive[i] = new Bee(beehive_name,0);
        }
        my_beehive[14] = new Bee(beehive_name,1);



    }
}