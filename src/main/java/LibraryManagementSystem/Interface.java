package LibraryManagementSystem;

/**
 * A minimal contract for a Command Line Interface (CLI)
 * that lets a librarian perform basic library operations.
 */
public interface Interface {


    void addBook();

    void removeBook();

    void addMember();

    void removeMember();

    void checkoutBook();
}
