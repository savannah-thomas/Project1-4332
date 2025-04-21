package LibraryManagementSystem;
//


public class LibraryAccounts {
    private double operatingCashBalance = 39000;
    private final Purchasing purchasing;
    private final Librarians librarians;

    public LibraryAccounts(Librarians librarians) {
        this.librarians = librarians;
        this.purchasing = new Purchasing();
    }

    public double getOperatingCashBalance() {
        return operatingCashBalance;
    }

    public void addDonation(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Donation must be positive");
        operatingCashBalance += amount;
    }

    public void withdrawSalary(String librarianName, double amount) {
        if (!librarians.isFullTimeLibrarian(librarianName))
            throw new SecurityException("Only full-time librarians can withdraw salary.");
        if (amount <= 0 || amount > operatingCashBalance)
            throw new IllegalArgumentException("Invalid withdrawal amount");

        operatingCashBalance -= amount;
        librarians.recordSalary(librarianName, amount);
    }

    public double purchaseBook(String librarianName) {
        if (!librarians.isFullTimeLibrarian(librarianName))
            throw new SecurityException("Only full-time librarians can purchase books.");

        double cost = purchasing.generateBookCost();
        if (cost > operatingCashBalance)
            throw new IllegalStateException("Insufficient funds for purchase");

        operatingCashBalance -= cost;
        librarians.recordBookPurchase(librarianName);
        return cost;
    }
}