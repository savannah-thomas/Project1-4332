package LibraryManagementSystem;

import java.util.Scanner;

public class ConsoleInterface implements Interface {

    private final Library library;
    private final Scanner scanner;

    public ConsoleInterface(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

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

    @Override
    public void removeBook() {
        System.out.print("Enter BookID of the book to remove: ");
        String bookID = scanner.nextLine();
        library.removeBook(bookID);
        System.out.println("Book removed (if found) from the library.");
    }

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

    @Override
    public void removeMember() {
        System.out.print("Enter MemberID of the member to remove: ");
        String memberID = scanner.nextLine();
        library.revokeMembership(memberID);
        System.out.println("Member removed (if found) from the library.");
    }

    @Override
    public void checkoutBook() {
        System.out.print("Enter BookID to checkout: ");
        String bookID = scanner.nextLine();

        System.out.print("Enter MemberID who is checking out the book: ");
        String memberID = scanner.nextLine();

        library.checkoutBook(bookID, memberID);
        System.out.println("Checkout process completed.");
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Add Member");
            System.out.println("4. Remove Member");
            System.out.println("5. Checkout Book");
            System.out.println("6. Exit");
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
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Library library = new Library();
        ConsoleInterface cli = new ConsoleInterface(library);
        cli.run();
    }
}
