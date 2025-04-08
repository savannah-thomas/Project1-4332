package LibraryManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class Member {
    // Fields that were directly specified from UML
    private String Name;
    private String Email;
    private String MemberID;
    private List<Book> BorrowedBookList;

    // A constructor to initialize a new member along with a list of books to be filled
    public Member(String Name, String Email, String MemberID) {
        this.Name = Name;
        this.Email = Email;
        this.MemberID = MemberID;
        this.BorrowedBookList = new ArrayList<>();
    }


    public void printMemberInfo() {
        System.out.println("Name: " + Name
                + ", Email: " + Email
                + ", MemberID: " + MemberID
                + ", Borrowed books count: " + BorrowedBookList.size());
    }


    public List<Book> getBorrowedBookList() {
        return BorrowedBookList;
    }


    public void addBorrowedBook(Book book) {
        BorrowedBookList.add(book);
    }


    public void UpdateMemberInfo(String newName, String newEmail) {
        this.Name = newName;
        this.Email = newEmail;
    }

    public void removeBorrowedBook(Book book) {
        BorrowedBookList.remove(book);
    }
}
