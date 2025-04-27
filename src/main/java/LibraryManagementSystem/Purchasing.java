package LibraryManagementSystem;

import java.util.Random;

public class Purchasing {
    private static final Random rand = new Random();
    //Generates a random value between $10 and $100 for a new book
    //returns an int from 10 to 100 inclusively
    public double generateBookCost() {
        //Returns the value of the book
        return 10 + rand.nextInt(91); //gives random int b/t 0 to 90 inclusive
        //And adding 10 shifts the scale by 10

        //Alternative Case (for decimals b/t 10.0 and <100,0:
        //90 * random.nextDouble -> scales the val to be in the range 0.0 to 90.0 (exclusively, so includes 89.999)
        //return 10 + (90 * rand.nextDouble());
    }
}