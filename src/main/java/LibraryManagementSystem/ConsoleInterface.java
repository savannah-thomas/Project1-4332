package LibraryManagementSystem;

import java.util.List;
import java.util.Scanner;

/**
 * A console-based implementation of the Interface that allows a librarian to
 * add/remove books, add/remove members, and checkout books specific for a certain member.
 */
public class ConsoleInterface implements Interface {
    private final Library library;
    private final LibraryAccounts accounts;
    private final Librarians librarians;
    private final Scanner scanner;
    private String currentLibrarian;
    private boolean isFullTime;

    /**
     *Constructor for ConsoleInterface with Library.
     * @param library instance called Library
     * @throws IllegalArgumentException is a library doesn't exist, and must have valid library to continue
     * Constructor now takes Library, Accounts, and Librarians.
     * Performs authentication at launch to determine librarian access level.
     */
    public ConsoleInterface(Library library, LibraryAccounts accounts, Librarians librarians) {
        if (library == null || accounts == null || librarians == null) {
            throw new IllegalArgumentException("Required components missing.");
        }
        this.library = library;
        this.accounts = accounts;
        this.librarians = librarians;
        this.scanner = new Scanner(System.in);
        authenticateLibrarian(); // New logic for login
    }

    /**
     * Prompts librarian to enter ALL valid details for book, then adds new book Object to Library.
     * Prompts the librarian through the console interface and once book is successfully added the Library is updated.
     * Handles login/authentication of full-time librarians with 6-digit PIN.
     * If user fails or is not in list, they’re treated as part-time.
     */
    private void authenticateLibrarian() {
        System.out.println("Enter your librarian name:");
        currentLibrarian = scanner.nextLine();

        if (librarians.isFullTimeLibrarian(currentLibrarian)) {
            System.out.println("Enter your 6-digit authentication code:");
            String code = scanner.nextLine();
            if (librarians.authenticate(currentLibrarian, code)) {
                isFullTime = true;
                System.out.println("Full-time librarian logged in.");
            } else {
                System.out.println("Invalid code. Access restricted to part-time functions.");
                isFullTime = false;
            }
        } else {
            System.out.println("Logged in as part-time librarian.");
            isFullTime = false;
        }
    }

    // Prompts librarian to enter ALL valid details for book, then adds new book Object to Library.
    @Override
    public void addBook() {
        System.out.println("Enter book details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("BookID: ");
        String bookID = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        Book book = new Book(name, author, year, isbn, bookID, true, genre);
        library.addBook(book);
        System.out.println("Book added successfully.");
    }

    /**
     * Removes book from library using valid BookID.
     * Book is removed from Library, if found (it exists).
     * Prompts the librarian through the console interface and once book is successfully removed the Library is updated.
     */
    @Override
    public void removeBook() {
        System.out.print("Enter BookID of the book to remove: ");
        String bookID = scanner.nextLine();

        if (!library.bookAvailability(bookID)) {
            System.out.println("Loaned books cannot be removed until returned.");
            return;
        }
        library.removeBook(bookID);
        System.out.println("Book removed.");
    }

    /**
     * Adds new member to Library.
     * MemberID must be valid and unique.
     * Prompts the librarian through the console interface and once member is successfully added the Library is updated.
     */
    @Override
    public void addMember() {
        System.out.print("Enter member details:\nName: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("MemberID: ");
        String memberID = scanner.nextLine();
        library.addMember(memberID);
        System.out.println("Member added successfully.");
    }

        /**
     * Removes existing member from Library.
     * MemberID must exist.
     * Prompts the librarian through the console interface and once member is successfully removed the Library is updated.
     */
    @Override
    public void removeMember() {
        if (!isFullTime) {
            System.out.println("Only full-time librarians can remove members.");
            return;
        }
        System.out.print("Enter MemberID to remove: ");
        String memberID = scanner.nextLine();
        library.revokeMembership(memberID);
        System.out.println("Member removed.");
    }

    /**
     * Checks out book to a specified member.
     * BookID must be linked to available book and MemberID must exist.
     * Book is updated as loaned by specified member.
     * Prompts the librarian through the console interface and once book is successfully marked the Library is updated.
     * If book is not available and librarian is full-time, prompts purchase via LibraryAccounts.
     * Part-time librarians are restricted from purchasing.
     */
    @Override
    public void checkoutBook() {
        System.out.print("Enter BookID to checkout: ");
        String bookID = scanner.nextLine();

        if (!library.bookAvailability(bookID)) {
            System.out.println("Book not available or doesn't exist.");
            if (isFullTime) {
                System.out.print("Would you like to purchase this book? (yes/no): ");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    double cost = accounts.purchaseBook(currentLibrarian);
                    System.out.print("Enter new book details for the purchase:\nName: ");
                    String name = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Year: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("BookID: ");
                    bookID = scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();

                    Book newBook = new Book(name, author, year, isbn, bookID, true, genre);
                    library.addBook(newBook);
                    System.out.println("Book purchased and added for checkout.");
                }
            } else {
                System.out.println("Please ask a full-time librarian to approve the purchase.");
                return;
            }
        }

        System.out.print("Enter MemberID for checkout: ");
        String memberID = scanner.nextLine();
        library.checkoutBook(bookID, memberID);
        System.out.println("Checkout process completed.");
    }

    // Allows librarian to return a book from a member.
    @Override
    public void returnBook() {
        System.out.print("Enter BookID to return: ");
        String bookID = scanner.nextLine();
        library.returnBook(bookID);
    }

    // Lists all books in the library.
    @Override
    public void printAllBooks() {
        for (Book book : library.getAllBooks()) {
            System.out.println(book.getBookInfo());
        }
    }

    /**
    * Method runs the console interface loop for the librarian.
     * Commands execute based on user input until exist is called.
     * Multiple operations to update Library are called here.
     * Includes dynamic menu items for financial ops if user is full-time.
     */
    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Add Member");
            System.out.println("4. Remove Member");
            System.out.println("5. Checkout Book");
            System.out.println("6. Return Book");
            System.out.println("7. List Books");
            if (isFullTime) {
                System.out.println("8. Withdraw Salary");
                System.out.println("9. Add Donation");
                System.out.println("10. View Balance");
                System.out.println("11. View My Purchases");
            }
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    removeBook();
                    break;
                case "3":
                    addMember();
                    break;
                case "4":
                    removeMember();
                    break;
                case "5":
                    checkoutBook();
                    break;
                case "6":
                    returnBook();
                    break;
                case "7":
                    printAllBooks();
                    break;
                case "8":
                    if (isFullTime) {
                        System.out.print("Enter salary amount to withdraw: ");
                        double amt = Double.parseDouble(scanner.nextLine());
                        accounts.withdrawSalary(currentLibrarian, amt);
                        System.out.println("Salary withdrawn.");
                    } break;
                case "9":
                    if (isFullTime) {
                        System.out.print("Enter donation amount: ");
                        double amt = Double.parseDouble(scanner.nextLine());
                        accounts.addDonation(amt);
                        System.out.println("Donation received.");
                    } break;
                case "10":
                    if (isFullTime) {
                        System.out.println("Current balance: $" + accounts.getOperatingCashBalance());
                    } break;
                case "11":
                    if (isFullTime) {
                        int count = librarians.getBooksPurchased(currentLibrarian);
                        System.out.println("Books purchased by " + currentLibrarian + ": " + count);
                    } break;
                case "0": exit = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    // Main method initializes Library, Accounts, and Librarian modules and starts interface
    public static void main(String[] args) {
        Library library = new Library();
        Librarians librarians = new Librarians();
        LibraryAccounts accounts = new LibraryAccounts(librarians);
        ConsoleInterface cli = new ConsoleInterface(library, accounts, librarians);
        cli.run();
    }
}