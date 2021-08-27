/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 12 - CarWashSimulation
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Scanner;

public class CarWashSimulation {

    private static final int    MIN_TIME_WASH = 5;  // minutes
    private static final int    MAX_TIME_WASH = 15;  // minutes
    private static final int    CHANCE_NEW_CAR = 15; // 1-100% chance
    private static final String DATA_FILENAME = "cars.csv";
    private static String[] data = null;

    public static void loadData() {
        try {
            Scanner in = new Scanner(new FileInputStream(DATA_FILENAME));
            // 1st line has the number of cars
            int total = Integer.parseInt(in.nextLine());
            data = new String[total];
            int i = 0;
            while (in.hasNextLine()) {
                String line = in.nextLine();
                data[i++] = line;
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Car getRandomCar() {
        Random r = new Random();
        int i = r.nextInt(data.length);
        String fields[] = data[i].split(",");
        return new Car(fields[0], fields[1], Integer.parseInt(fields[2]));
    }

    public static void run(int minutes) {
        DynamicQueue<Car> queueA = new DynamicQueue<>();
        DynamicQueue<Car> queueB = new DynamicQueue<>();
        Random r = new Random();
        Car carA, carB; // this points to the car being washed
        carA = carB = null;
        int timerA, timerB; // counts the minutes to wash the car
        timerA = timerB = 0;
        System.out.println("Simulation starts now for " + minutes + " minutes!");
        for (int i = 0; i < minutes; i++) {
            String padded = String.format("%03d" , i);
            System.out.print(padded + ". ");
            System.out.println("Queue A: " + queueA);
            System.out.print(padded + ". ");
            System.out.println("Queue B: " + queueB);
            // TODOd: simulate that a new car arrived using CHANCE_NEW_CAR
            if (r.nextInt(100) + 1 <= CHANCE_NEW_CAR) {
                Car newCar = getRandomCar();
                if (queueA.size() < queueB.size())
                    queueA.push(newCar);
                else
                    queueB.push(newCar);
            }

            // TODOd: if no cars is being washed, get the next car to be served from the queue
            if (carA == null) {
                try {
                    carA = queueA.pop();
                    timerA = r.nextInt(MAX_TIME_WASH - MIN_TIME_WASH + 1) + MIN_TIME_WASH;
                }
                catch (Error error) {

                }
            }

            if (carB == null) {
                try {
                    carB = queueB.pop();
                    timerB = r.nextInt(MAX_TIME_WASH - MIN_TIME_WASH + 1) + MIN_TIME_WASH;
                }
                catch (Error error) {

                }
            }

            // TODOd: if a car is currently being served, decrease the timer and finish servicing the car if timer reaches 0
            if (carA != null) {
                System.out.println(carA + " (lane A) is being washed...");
                timerA--;
                if (timerA == 0)
                    carA = null;
            }

            if (carB != null) {
                System.out.println(carB + " (lane B) is being washed...");
                timerB--;
                if (timerB == 0)
                    carB = null;
            }
        }
        System.out.println("Simulation completed!");
    }

    public static void main(String[] args) {
        loadData();
        run(100);
    }
}
