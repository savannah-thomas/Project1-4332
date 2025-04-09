package LibraryManagementSystem;

/**
 * A minimal contract for a Command Line Interface (CLI)
 * that lets a librarian perform basic library operations.
 */
public interface Interface {

    //Prompts librarian to enter a book to be added, then adds book to Library Management System.
    void addBook();

    //Prompts librarian to enter a book to be removed, then removes book from Library Management System.
    void removeBook();

    //Prompts librarian to enter a member to be added, then adds member to Library Management System.
    void addMember();

    //Prompts librarian to enter a member to be removed, then removes member from Library Management System.
    void removeMember();

    //Allows librarian to check out a book to a member.
    void checkoutBook();
}
