package LibraryManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Library {

    // Fields from UML
    private List<String> AvailableBookIds;
    private List<Book> AllBooksInLibrary;
    private Map<String, String> LoanedBooks;  // key: BookID, value: MemberID
    private List<String> MemberIDs;

    // No-arg constructor (not in UML, but needed to initialize collections)
    public Library() {
        this.AvailableBookIds   = new ArrayList<>();
        this.AllBooksInLibrary  = new ArrayList<>();
        this.LoanedBooks        = new HashMap<>();
        this.MemberIDs          = new ArrayList<>();
    }

    /**
     * Adds book to library.
     * @param book to be added
     * @throws IllegalArgumentException valid book wasn't added
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Must enter book.");
        }
        AllBooksInLibrary.add(book);
        AvailableBookIds.add(book.getBookID());
    }

    /**
     * Removes book from library.
     * @param bookID of the book that needs to be removed
     * @throws IllegalArgumentException if book ID is null/empty
     */
    public void removeBook(String bookID) {
        if (bookID == null || bookID.isEmpty()) {
            throw new IllegalArgumentException("Must enter valid book ID.");
        }
        //Checks to see of book exist in library
        boolean exists = AllBooksInLibrary.stream()
                .anyMatch(b -> b.getBookID().equals(bookID));
        if (!exists) {
            throw new IllegalArgumentException("Book with ID " + bookID + " does not exist.");
        }
        // Proceed to remove book
        AllBooksInLibrary.removeIf(b -> b.getBookID().equals(bookID));
        AvailableBookIds.remove(bookID);
        LoanedBooks.remove(bookID);
    }

    /**
     * Checks out book to specified member.
     * @param bookID of the book of the book to be checked out
     * @param memberID the ID of the member checking out book
     * @throws IllegalArgumentException if either IDs are null, empty, or doesn't exist
     */
    public void checkoutBook(String bookID, String memberID) {
        if (bookID == null || memberID == null || bookID.isEmpty() || memberID.isEmpty()) {
            throw new IllegalArgumentException("Valid BookID and MemberID must be entered.");
        }
        if (!AvailableBookIds.contains(bookID)) {
            throw new IllegalArgumentException("Book is not available or doesn't exist in Library.");
        }
        if (!MemberIDs.contains(memberID)) {
            throw new IllegalArgumentException("Member does not exist in system.");
        }
        AvailableBookIds.remove(bookID);
        LoanedBooks.put(bookID, memberID);
        for (Book book : AllBooksInLibrary) {
            if (book.getBookID().equals(bookID)) {
                book.setAvailability(false);
            }
        }
    }

    /**
     * Adds new members to Library System with their memberID
     * @param memberID is the identifier of member
     * @throws IllegalArgumentException is memberID is null/empty
     */
    public void addMember(String memberID) {
        if (memberID == null || memberID.isEmpty()) {
            throw new IllegalArgumentException("Must provide valid memberID.");
        }
        MemberIDs.add(memberID);
    }

    /**
     * Revokes membership of a member, and their ID is removed from member list
     * @param memberID of member to revoke
     * @throws IllegalArgumentException if memberID is null/empty
     */
    public void revokeMembership(String memberID) {
        if (memberID == null || memberID.isEmpty()) {
            throw new IllegalArgumentException("Must provide valid member ID");
        }
        //Checks if the member actually exists
        if (!MemberIDs.contains(memberID)) {
            throw new IllegalArgumentException("Member with ID " + memberID + " does not exist.");
        }
        MemberIDs.remove(memberID);

    }

    /**
     * checks if book is currently available to be checked out
     * @param bookID of available book
     * @return true if available
     */
    public boolean bookAvailability(String bookID) {
        return AvailableBookIds.contains(bookID);
    }

    /**
     * Gets memberID of member who has the booked checked out.
     * @param bookID of book that member has
     * @return memberID of member who has book
     */
    public String whoHasBook(String bookID) {
        return LoanedBooks.get(bookID);
    }

    /**
     * @return list of memberIDs in Library System.
     */
    public List<String> getAllMembers() {
        return MemberIDs;
    }

    /**
     *
     * @return list of Books in Library System.
     */
    public List<Book> getAllBooks() { return AllBooksInLibrary; }

    /**
     * Finds ID of the book given its name.
     * @param bookName of book trying to be found
     * @return ID of matching book
     */
    public String findBookIdByName(String bookName) {
        if (bookName == null || bookName.isEmpty()) {
            throw new IllegalArgumentException("Book name must be provided.");
        }
        for (Book b : AllBooksInLibrary) {
            if (b.getName().equals(bookName)) {
                return b.getBookID();
            }
        }
        return null;
    }

    /**
     * Returns loaned book back to library.
     * If book was loaned it gets removed from LoanedBooks and added to AvailableBookIds
     * @param bookID of book returned
     * @throws IllegalArgumentException is bookID is null/empty
     */
    public void returnBook(String bookID) {
        if (bookID == null || bookID.isEmpty()) {
            throw new IllegalArgumentException("Must provide a valid book ID");
        }
        if (LoanedBooks.containsKey(bookID)) {
            LoanedBooks.remove(bookID);
            AvailableBookIds.add(bookID);
        }
        for (Book book : AllBooksInLibrary) {
            if (book.getBookID().equals(bookID)) {
                book.setAvailability(true);
            }
        }
    }
}
