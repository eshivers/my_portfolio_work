/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 03 - Bee
 */

public class Bee {

    public static final int WORKER = 0;
    public static final int DRONE  = 1;
    public static final int QUEEN  = 2;

    private String hiveName;
    private int    type;
    private int    x, y;

    public Bee(String hiveName) {
        this.hiveName = hiveName;
        type = WORKER;
        x = y = 0;
    }

    public Bee(String hiveName, int type) {
        this.hiveName = hiveName;
        if (type >= WORKER && type <= QUEEN)
            this.type = type;
        else
            this.type = WORKER;
        x = y = 0;
    }

    public String getHiveName() {
        return hiveName;
    }

    public int getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private String getTypeAsString() {
        if (type == WORKER)
            return "worker";
        else if (type == DRONE)
            return "drone";
        else
            return "queen";
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isInsideHive() {
        return x == 0 && y == 0;
    }

    @Override
    public String toString() {
        if (isInsideHive())
            return getTypeAsString() + " from " + hiveName + " is inside the hive! ";
        else
            return getTypeAsString() + " from " + hiveName + " is outside the hive @ (" + x + ", " + y + ")";
    }
}
