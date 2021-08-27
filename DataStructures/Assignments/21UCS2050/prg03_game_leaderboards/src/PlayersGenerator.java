/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Prg03 - PlayersGenerator class
 * Name(s): Erica Shivers
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class PlayersGenerator {

    private static final String FILE_NAME = "players.csv";
    private static final String[] PLAYERS = {"GandulaMaster", "DnknDonuts", "EdgarAllenPoo", "AllGoodNamesRGone", "Avocadorable", "FrostedCupcake", "Unnecessary", "PuppiesnKittens", "TheKidsCallMeBoss", "ToastCrunch", "IsaacAsiMove", "IRobot"};
    private static final int MAX_SCORES = 1000;

    public static void main(String[] args) throws FileNotFoundException {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_NAME));

            Random r = new Random();
            for (int i = 0; i < PLAYERS.length; i++) {
                String name = PLAYERS[i];
                int score = r.nextInt(MAX_SCORES) + 1;
                out.println(name + "," + score);
            }
            out.close();
        } catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

}
