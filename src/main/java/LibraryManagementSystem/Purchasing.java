package LibraryManagementSystem;

import java.util.Random;

public class Purchasing {
    private static final Random rand = new Random();

    public double generateBookCost() {
        return 10 + rand.nextInt(91); // $10 to $100
    }
}