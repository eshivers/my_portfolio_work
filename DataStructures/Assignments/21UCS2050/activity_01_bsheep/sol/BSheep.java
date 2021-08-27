/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 01 - BSheep
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BSheep {

    static final int MIN_NUM_SHEEP = 2;

    public static void main(String[] args) {

        // validate the command-line
        if (args.length >= 1) {
            String fileName = args[0];
            System.out.println("Informed file name: " + fileName);

            try {
                Scanner in = new Scanner(new FileInputStream(fileName));
                // at this point, we can assume that we were able to open the file
                int nSheep = in.nextInt();
                if (nSheep >= MIN_NUM_SHEEP) {
                    System.out.println("#sheep: " + nSheep);
                    // how to read the sheep coordinates...
                    int minX, maxX, minY, maxY;
                    minX = maxX = in.nextInt();
                    minY = maxY = in.nextInt();
                    for (int i = 1; i < nSheep; i++) {
                        int x = in.nextInt();
                        int y = in.nextInt();
                        if (x < minX)
                            minX = x;
                        if (x > maxX)
                            maxX = x;
                        if (y < minY)
                            minY = y;
                        if (y > maxY)
                            maxY = y;
                    }
                    System.out.println("Fence Coordinates: {(" +
                            minX + "," + minY + "), (" +
                            maxX + "," + minY + "), (" +
                            maxX + "," + maxY + "), (" +
                            minX + "," + maxY + ")}");
                }
                else
                    System.out.println("#sheep has to be at least " + MIN_NUM_SHEEP);
            } catch (FileNotFoundException e) {
                System.out.println(fileName + " couldn't be opened!");
            }
        }
        else
            System.out.println("You need to pass a file containing the sheep coordinates through the command-line!");
    }
} // end of class definition
