package LibraryManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class Member {
    // Fields that were directly specified from UML
    private String Name;
    private String Email;
    private String MemberID;
    private List<Book> BorrowedBookList;

    /**
     *  A constructor to initialize a new member with name, email, amd ID along with a list of books to be filled.
     *  A member object is created with borrowed book list.
     * @param Name of members name
     * @param MemberID for member
     * @param Email member's email address
     * @throws NullPointerException if any parameter above is invalid
     */
    public Member(String Name, String Email, String MemberID) {
        if (Name == null || Email == null || MemberID == null) {
            throw new NullPointerException("Member name, email, and ID must be valid.");
        }
        this.Name = Name;
        this.Email = Email;
        this.MemberID = MemberID;
        this.BorrowedBookList = new ArrayList<>();
    }

    //prints members info to console
    public void printMemberInfo() {
        System.out.println("Name: " + Name
                + ", Email: " + Email
                + ", MemberID: " + MemberID
                + ", Borrowed books count: " + BorrowedBookList.size());
    }

    /**
     * Gets list of books borrowed by member.
     * @return list of borrowed books
     */
    public List<Book> getBorrowedBookList() {
        return BorrowedBookList;
    }

    /**
     * Adds book to a member's list of borrowed books.
     * @param book added to borrowed list
     * @throws NullPointerException if book isn't valid
     */
    public void addBorrowedBook(Book book) {
        if (book == null) {
            throw new NullPointerException("Book must be valid.");
        }
        BorrowedBookList.add(book);
    }

    /**
     * Updates member's name and email.
     * @param newName of member
     * @param newEmail of member
     * @throws NullPointerException is name or email isn't valid
     */
    public void UpdateMemberInfo(String newName, String newEmail) {
        if (newName == null || newEmail == null) {
            throw new NullPointerException("Name and Email must be valid.");
        }
        this.Name = newName;
        this.Email = newEmail;
    }

    /**
     * Removes book from member's list of borrowed books.
     * @param book to remove from borrowed list
     * @throws NullPointerException if invalid book is given
     */
    public void removeBorrowedBook(Book book) {
        if (book == null) {
            throw new NullPointerException("Book must be valid.");
        }
        BorrowedBookList.remove(book);
    }
}
