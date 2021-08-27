/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 03 - BeeDriver
 */

import java.util.Random;

public class BeeDriver {

    public static void main(String[] args) {
        final int TOTAL_BEES = 15;
        
        Bee bees[] = new Bee[TOTAL_BEES];
        bees[0] = new Bee("Crazy for Nectar", Bee.QUEEN);
        System.out.println(bees[0]);
        Random r = new Random();
        for (int i = 1; i < TOTAL_BEES; i++) {
            if (r.nextInt(100) < 10)
                bees[i] = new Bee("Crazy for Nectar", Bee.DRONE);
            else
                bees[i] = new Bee("Crazy for Nectar");
            int x = r.nextInt(21) - 10;
            int y = r.nextInt(21) - 10;
            bees[i].move(x, y);
            System.out.println(bees[i]);
        }
    }
}