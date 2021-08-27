/*
 * CS2050 - Computer Science II - Summer 2020
 * Instructor: Thyago Mota
 * Description: Prg03 - Leaderboard class
 * Name(s): Erica Shivers
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

public class Leaderboard {

    private static final String FILE_NAME = "players.csv";
    private BinaryTree<Player> leaderboard;

    public Leaderboard() throws FileNotFoundException {

        // TODO: initialize the leaderboard
        this.leaderboard = new BinaryTree<Player>();
        // TODO: add all players in "players.csv" to the leaderboard (hint: split the line by comma and call the add method passing the name and the score
        try {
            Scanner sc = new Scanner(new FileInputStream(FILE_NAME));
            while (sc.hasNext()) {
                String[] player = sc.next().split(",");
                this.add(player[0],Integer.parseInt(player[1]));
            }
            sc.close();
        } catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    // TODO: add the player with the given name and score to the leaderboard (hint: use the binary tree's add method
    public void add(final String name, int score) {
        this.leaderboard.add(new Player(name,score));
    }

    // TODO: display the leaderboard (hint: use the binary tree's toString method)
    public void list() {
        System.out.println(this.leaderboard);
    }

    // TODO: save the leaderboard into "players.csv" (hint: use the leaderboard's iterator)
    public void save() throws FileNotFoundException {
        Iterator<Player> it = this.leaderboard.iterator();
        PrintStream out = new PrintStream(new FileOutputStream(FILE_NAME));
        while(it.hasNext()) {
            Player curr_player = (Player) it.next();
            String name = curr_player.getName();
            int score = curr_player.getScore();
            out.println(name + "," + score);
        }
        out.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Leaderboard lb = new Leaderboard();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Options: [list | add | save | exit]");
            System.out.print("? ");
            String option = sc.nextLine();
            if (option.equals("list")) {
                // TODO: figure it out what to do!
                lb.list();
            }
            else if (option.equals("add")) {
                // TODO: ask the user for a name and a score, then figure it out what to do!
                System.out.println("Enter the name: ");
                String player_name = sc.nextLine();
                System.out.println("Enter the score of player: ");
                int player_score= Integer.parseInt(sc.nextLine());
                lb.add(player_name,player_score);
            }
            else if (option.equals("save")) {
                // TODO: figure it out what to do!
                lb.save();
            }
            else
                break;
        }
    }
}
