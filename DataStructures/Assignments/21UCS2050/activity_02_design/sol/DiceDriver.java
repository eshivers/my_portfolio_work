public class DiceDriver {

    public static void main(String[] args) {
        Dice dice = new Dice(1);
        System.out.println(dice.getValue());
        dice.roll();
        System.out.println(dice.getValue());
        dice.roll();
        System.out.println(dice.getValue());
    }
}
