/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Prg03 - Player class
 * Name(s): Erica Shivers
 */

public class Player implements Comparable<Player> {

    private String name;
    private int    score;
    private static final int MIN_SCORE = 0;

    public Player(final String name, int score) {
        this.name = name;
        if (score < MIN_SCORE)
            this.score = 0;
        else
            this.score = score;
    }

    public Player(final String name) {
        this(name, MIN_SCORE);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }

    // TODO: compare player objects based on their scores in descending order
    @Override
    public int compareTo(Player other) {
        if(this.getScore() > other.getScore()){
            return 1;
        } else if(this.getScore() == other.getScore()){
            return 0;
        } else {
            return -1;
        }
    }
}
