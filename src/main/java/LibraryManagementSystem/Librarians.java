package LibraryManagementSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Librarians {
    private final Map<String, String> fullTimeLibrarianAuthCodes = new HashMap<>();
    private final Map<String, Double> salaries = new HashMap<>();
    private final Map<String, Integer> booksPurchased = new HashMap<>();


    //initializes a group of three full-time librarians with their names and 6-digit authentication codes
    public Librarians() {
        fullTimeLibrarianAuthCodes.put("Alice", "123456");
        fullTimeLibrarianAuthCodes.put("Bob", "654321");
        fullTimeLibrarianAuthCodes.put("Clara", "111222");
        for (String name : fullTimeLibrarianAuthCodes.keySet()) {
            salaries.put(name, 0.0);
            booksPurchased.put(name, 0);
        }
    }

    /**
     * Checks if the provided name and code match a full-time librarian record
     *
     * @param name the librarian's name
     * @param code the 6-digit authentication code
     * @return true if credentials are valid, false otherwise
     */
    public boolean authenticate(String name, String code) {
        return fullTimeLibrarianAuthCodes.containsKey(name) && fullTimeLibrarianAuthCodes.get(name).equals(code);
    }

    /**
     * Checks if the given name matches a name of a full-time librarian
     *
     * @param name the librarian's name
     * @return true if the name is in the full-time list
     */
    public boolean isFullTimeLibrarian(String name) {
        return fullTimeLibrarianAuthCodes.containsKey(name);
    }


    /**
     * Records a salary withdrawal amount for the specified librarian
     *
     * @param name   the librarian's name
     * @param amount the amount withdrawn
     */
    public void recordSalary(String name, double amount) {
        salaries.put(name, salaries.getOrDefault(name, 0.0) + amount);
    }

    /**
     * Increases the count of books purchased by the specified librarian
     *
     * @param name the librarian's name
     */
    public void recordBookPurchase(String name) {
        booksPurchased.put(name, booksPurchased.getOrDefault(name, 0) + 1);
    }

    /**
     * Returns the total number of books purchased by the given librarian
     *
     * @param name the librarian's name
     * @return purchase count or zero if none is found
     */
    public int getBooksPurchased(String name) {
        return booksPurchased.getOrDefault(name, 0);
    }

    /**
     * Returns the total salary recorded for the given librarian
     *
     * @param name the librarian's name
     * @return total withdrawn salary or zero if none is found
     */
    public double getTotalSalaryWithdrawn(String name) {
        return salaries.getOrDefault(name, 0.0);
    }


}