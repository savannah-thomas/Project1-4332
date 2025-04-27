package LibraryManagementSystem;


public class LibraryAccounts {
    //current cash balance for library ops
    private double operatingCashBalance = 39000;
    //new Purchasing object used to generate book costs
    private final Purchasing purchasing;
    //reference to Librarians class to check librarian stats and activities
    private final Librarians librarians;

    //constructor that initializes a reference to Librarians and creates Purchasing instance
    public LibraryAccounts(Librarians librarians) {
        this.librarians = librarians;
        this.purchasing = new Purchasing();
    }

    //returns current operating balance
    public double getOperatingCashBalance() {
        return operatingCashBalance;
    }

    //adds donation to library's operating balance
    //The amt must be positive or else illegalArg is thrown
    public void addDonation(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Donation must be positive");
        operatingCashBalance += amount;
    }

    //Allows full time librarian to withdraw salary from operating balance
    //Withdrawal amt must be positive and not greater than available balance
    public void withdrawSalary(String librarianName, double amount) {
        if (!librarians.isFullTimeLibrarian(librarianName))
            throw new SecurityException("Only full-time librarians can withdraw salary.");
        if (amount <= 0 || amount > operatingCashBalance)
            throw new IllegalArgumentException("Invalid withdrawal amount");

        //updates cash balance and librarians recordSalary
        operatingCashBalance -= amount;
        librarians.recordSalary(librarianName, amount);
    }

    //Allows full time librarian to purchase a book and deducts cost from operating balance
    //Book cost is randomly generated from Purchasing class
    public double purchaseBook(String librarianName) {
        if (!librarians.isFullTimeLibrarian(librarianName))
            throw new SecurityException("Only full-time librarians can purchase books.");

        //throws exception if there are insufficent funds
        double cost = purchasing.generateBookCost();
        if (cost > operatingCashBalance)
            throw new IllegalStateException("Insufficient funds for purchase");

        //updates cash balance and the librarians recordBookPurchase
        operatingCashBalance -= cost;
        librarians.recordBookPurchase(librarianName);
        return cost;
    }
}