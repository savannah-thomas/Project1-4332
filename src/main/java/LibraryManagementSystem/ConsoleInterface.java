package LibraryManagementSystem;

import java.util.List;
import java.util.Scanner;

/**
 * A console-based implementation of the Interface that allows a librarian to
 * add/remove books, add/remove members, and checkout books specific for a certain member.
 */
public class ConsoleInterface implements Interface {

    private final Library library;
    private final Scanner scanner;

    /**
     *Constructor for ConsoleInterface with Library.
     * @param library instance called Library
     * @throws IllegalArgumentException is a library doesn't exist, and must have valid library to continue
     */
    public ConsoleInterface(Library library) {
        if (library == null) {
            throw new IllegalArgumentException("Library doesn't exist.");
        }
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts librarian to enter ALL valid details for book, then adds new book Object to Library.
     * Prompts the librarian through the console interface and once book is successfully added the Library is updated.
     */
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

        boolean isAvailable = true;

        Book book = new Book(name, author, year, isbn, bookID, isAvailable, genre);
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
        System.out.println("Book removed (if found) from the library.");
    }

    /**
     * Adds new member to Library.
     * MemberID must be valid and unique.
     * Prompts the librarian through the console interface and once member is successfully added the Library is updated.
     */
    @Override
    public void addMember() {
        System.out.println("Enter member details:");

        System.out.print("Name: ");
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
        System.out.print("Enter MemberID of the member to remove: ");
        String memberID = scanner.nextLine();
        library.revokeMembership(memberID);
        System.out.println("Member removed (if found) from the library.");
    }

    /**
     * Checks out book to a specified member.
     * BookID must be linked to available book and MemberID must exist.
     * Book is updated as loaned by specified member.
     * Prompts the librarian through the console interface and once book is successfully marked the Library is updated.
     */
    @Override
    public void checkoutBook() {
        System.out.print("Enter BookID to checkout: ");
        String bookID = scanner.nextLine();

        System.out.print("Enter MemberID who is checking out the book: ");
        String memberID = scanner.nextLine();

        library.checkoutBook(bookID, memberID);
        System.out.println("Checkout process completed.");
    }

    @Override
    public void returnBook() {
        System.out.print("Enter BookID to return: ");
        String bookID = scanner.nextLine();

        library.returnBook(bookID);
    }

    @Override
    public void printAllBooks() {
        List<Book> books = library.getAllBooks();
        for (Book book : books) {
            System.out.println(book.getBookInfo());
        }
    }

    /**
     * Method runs the console interface loop for the librarian.
     * Commands execute based on user input until exist is called.
     * Multiple operations to update Library are called here.
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
            System.out.println("8. Exit");
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
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        scanner.close();
    }

    //Instantiates Library and starts console interface operations.
    public static void main(String[] args) {
        Library library = new Library();
        ConsoleInterface cli = new ConsoleInterface(library);
        cli.run();
    }
}
