/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 03 - Bee
 */

public class Bee {

    public static final int WORKER = 0;
    public static final int DRONE  = 1;
    public static final int QUEEN  = 2;

    int type;
    String beehive;
    int x;
    int y;

    Bee(String beehive){
        this.type = WORKER;
        this.beehive = beehive;
        this.x = 0;
        this.y = 0;
    }

    Bee(String beehive,int type){
        if(type != WORKER && type != DRONE  && type != QUEEN){
            this.type = WORKER;
        } else {
            this.type = type;
        }
        this.beehive = beehive;
        this.x = 0;
        this.y = 0;
    }

    public String getTypeAsString(int type){
        String result = null;
        if(type == 0){
            result = "Worker";
        } else if (type == 1){
            result = "Drone";
        } else if (type == 2){
            result = "Queen";
        }
        return result;
    }

    public void move(int x,int y){
        this.x = x;
        this.y = y;
    }

    public Boolean isInsideHive(){
        if(x == 0 && y ==0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getTypeAsString(this.type) + " from \"" + this.beehive + "\" is " +
                (this.isInsideHive() ? "inside " : "outside ") + "the hive @ " + "(" +
                this.x + ", " + this.y + ")";
    }
}
