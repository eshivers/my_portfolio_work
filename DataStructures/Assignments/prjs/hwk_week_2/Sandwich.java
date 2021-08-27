public class Sandwich {
    String name;
    int calories;
    float price;
    static final int healthyLimit = 250;

    public Sandwich(String name,int calories,float price){
        this.name = name;
        if(calories < 0){
            this.calories = 0;
        } else {
            this.calories = calories;
        }
        if(price < 0){
            this.price = 0;
        } else {
            this.price = price;
        }
    }
    public String toString(){
        return "The name of the sandwich is " + this.name + ". The price is " + this.price + ", while the calories are " + this.calories;
    }
    public Boolean isHealthy(){
        return this.calories < healthyLimit ? true : false;
    }

    public static void main(String[] args) {
        Sandwich test_sandwich = new Sandwich("New Sandwich",251,100);
        System.out.println(test_sandwich.toString() +" and the sandwich is "  + (test_sandwich.isHealthy() ? "healthy" : "not healthy"));

        Sandwich test_sandwich1 = new Sandwich("New Sandwich 2",-251,100);
        System.out.println(test_sandwich1.toString() + " and the sandwich is "  + (test_sandwich1.isHealthy() ? "healthy" : "not healthy"));

    }
}
