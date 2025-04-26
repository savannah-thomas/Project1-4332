package LibraryManagementSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Librarians {
    private final Map<String, String> fullTimeLibrarianAuthCodes = new HashMap<>();
    private final Map<String, Double> salaries = new HashMap<>();
    private final Map<String, Integer> booksPurchased = new HashMap<>();

    public Librarians() {
        fullTimeLibrarianAuthCodes.put("Alice", "123456");
        fullTimeLibrarianAuthCodes.put("Bob", "654321");
        fullTimeLibrarianAuthCodes.put("Clara", "111222");
        for (String name : fullTimeLibrarianAuthCodes.keySet()) {
            salaries.put(name, 0.0);
            booksPurchased.put(name, 0);
        }
    }

    public boolean authenticate(String name, String code) {
        return fullTimeLibrarianAuthCodes.containsKey(name) && fullTimeLibrarianAuthCodes.get(name).equals(code);
    }

    public boolean isFullTimeLibrarian(String name) {
        return fullTimeLibrarianAuthCodes.containsKey(name);
    }

    public void recordSalary(String name, double amount) {
        salaries.put(name, salaries.getOrDefault(name, 0.0) + amount);
    }

    public void recordBookPurchase(String name) {
        booksPurchased.put(name, booksPurchased.getOrDefault(name, 0) + 1);
    }

    public int getBooksPurchased(String name) {
        return booksPurchased.getOrDefault(name, 0);
    }

    //added in order to test record salary
    public double getTotalSalaryWithdrawn(String name) {
        return salaries.getOrDefault(name, 0.0);
    }


}