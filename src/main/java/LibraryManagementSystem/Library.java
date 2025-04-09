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
     */
    public void addBook(Book book) {
        AllBooksInLibrary.add(book);
        AvailableBookIds.add(book.getBookID());
    }

    public void removeBook(String bookID) {

        AllBooksInLibrary.removeIf(b -> b.getBookID().equals(bookID));

        AvailableBookIds.remove(bookID);

        LoanedBooks.remove(bookID);
    }

    public void checkoutBook(String bookID, String memberID) {

        if (AvailableBookIds.contains(bookID)) {
            AvailableBookIds.remove(bookID);
            LoanedBooks.put(bookID, memberID);
        }
    }

    public void addMember(String memberID) {
        MemberIDs.add(memberID);
    }

    public void revokeMembership(String memberID) {
        MemberIDs.remove(memberID);

    }

    public boolean bookAvailability(String bookID) {
        return AvailableBookIds.contains(bookID);
    }

    public String whoHasBook(String bookID) {
        return LoanedBooks.get(bookID);
    }

    public List<String> getAllMembers() {
        return MemberIDs;
    }

    public String findBookIdByName(String bookName) {
        for (Book b : AllBooksInLibrary) {
            if (b.getName().equals(bookName)) {
                return b.getBookID();
            }
        }
        return null;
    }

    public void returnBook(String bookID) {
        if (LoanedBooks.containsKey(bookID)) {
            LoanedBooks.remove(bookID);
            AvailableBookIds.add(bookID);
        }
    }
}
