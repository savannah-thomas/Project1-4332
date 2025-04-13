package LibraryManagementSystem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.InputStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryCoreTest {

    private Library library;
    private Book book;
    private Member member;

    @BeforeEach
    public void setup() {
        library = new Library();
        book = new Book("Physics", "Smith", 2022, "123456", "B001", true, "Science");
        member = new Member("Cole", "colemail", "M123");
    }

    @Test
    public void voidTestAddBook() {
        library.addBook(book);
        assertTrue(library.bookAvailability("B001"));
        assertEquals("B001", library.findBookIdByName("Physics"));
    }
    @Test
    public void testCheckoutBook() {
        library.addBook(book);
        library.addMember("M123");
        library.checkoutBook("B001", "M123");
        assertFalse(library.bookAvailability("B001"));
        assertEquals("M123", library.whoHasBook("B001"));
    }
    @Test
    public void testReturnBook() {
        library.addBook(book);
        library.addMember("M123");
        library.checkoutBook("B001", "M123");
        library.returnBook("B001");
        assertTrue(library.bookAvailability("B001"));
        assertNull(library.whoHasBook("B001"));
    }

    @Test
    public void testAddAndRevokeMember() {
        library.addMember("M001");
        List<String> members = library.getAllMembers();
        assertTrue(members.contains("M001"));

        library.revokeMembership("M001");
        assertFalse(library.getAllMembers().contains("M001"));
    }


    //Tests for Library Class Below!

    //Test removes book w/ invalid ID, throws IllegalArg
    //Specification: Method contract specifies bookID must be null/empty
    @Test
    public void testRemoveBookWithInvalidId() {
        Library library = new Library();
        assertThrows(IllegalArgumentException.class, () -> library.removeBook(""));
    }

    //Test tries to check out book w/ nonexistent member, throws IllegalArg
    //Specification: Library should only allow valid members books
    @Test
    public void testCheckoutWithNonexistentMember() {
        Library library = new Library();
        Book b = new Book("Title", "Author", 2025, "1234", "B001", true, "Fiction");
        library.addBook(b);
        assertThrows(IllegalArgumentException.class, () -> library.checkoutBook("B001", "M999"));
    }

    //Test tries to find book w/ Null name, throw IllegalArg
    //Specification: Method requires valid book name
    @Test
    public void testFindBookIdByNullName() {
        Library library = new Library();
        assertThrows(IllegalArgumentException.class, () -> library.findBookIdByName(null));
    }

    //Test that checkoutBook throws IllegalArg when null book or member ID given
    //Edge Case: Null Book ID or Member ID
    //Specification: checkoutBook requires valid non-null BookID and MemberID
    @Test
    public void testCheckoutWithNullValues() {
        assertThrows(IllegalArgumentException.class, () -> library.checkoutBook(null, "A001"));
        assertThrows(IllegalArgumentException.class, () -> library.checkoutBook("B001", null));
    }

    //Tests for Member Class Below!

    //Test tries to create member w/ null name, email, or ID, throws NullPtr
    //Specification: Constructor requires that all member info (name, email, ID) can't be null
    @Test
    public void testConstructorWithNullParams() {
        assertThrows(NullPointerException.class, () -> new Member(null, "email", "ID001"));
        assertThrows(NullPointerException.class, () -> new Member("Name", null, "ID001"));
        assertThrows(NullPointerException.class, () -> new Member("Name", "email", null));
    }

    //Tests adding a null book to a members borrowed book list, throws NullPtre
    //Specification: Only valid books should be able to be added to borrowed list
    @Test
    public void testAddNullBook() {
        Member member = new Member("Savannah", "savannah@example.com", "M001");
        assertThrows(NullPointerException.class, () -> member.addBorrowedBook(null));
    }

    //Tests that updating member info w/ null name/email throws NullPtr
    //Specification: Update method requires valid info
    @Test
    public void testUpdateMemberInfoWithNullValues() {
        Member member = new Member("Cole", "Cole@example.com", "M002");
        assertThrows(NullPointerException.class, () -> member.UpdateMemberInfo(null, "new@example.com"));
    }


    //Structural: Tests that addBorrowedBook throws on null
    @Test
    public void testAddBorrowedBookThrowsOnNull() {
        Member member = new Member("Jane", "josh@example.com", "A001");
        assertThrows(NullPointerException.class, () -> member.addBorrowedBook(null));
    }

    //Structural: Tests that removeBorrowedBook throws on null
    @Test
    public void testRemoveBorrowedBookThrowsOnNull() {
        Member member = new Member("Jane", "josh@example.com", "A002");
        assertThrows(NullPointerException.class, () -> member.removeBorrowedBook(null));
    }

    //Specification: Adds a book and ensures it's in borrowed list
    @Test
    public void testAddBorrowedBookUpdatesList() {
        Member member = new Member("Sav", "sav@example.com", "A003");
        Book book = new Book("Title", "Author", 2025, "ISBN1", "B001", true, "Fiction");

        member.addBorrowedBook(book);
        assertTrue(member.getBorrowedBookList().contains(book));
    }

    //Specification: Adds and removes book show list should no longer contain it
    @Test
    public void testRemoveBorrowedBookUpdatesList() {
        Member member = new Member("Sav", "sav@example.com", "A004");
        Book book = new Book("Title2", "Author", 2025, "ISBN2", "B002", true, "Drama");

        member.addBorrowedBook(book);
        member.removeBorrowedBook(book);
        assertFalse(member.getBorrowedBookList().contains(book));
    }

    //Property-Based: Tests that adding null book shouldn't corrupt list
    @Test
    public void testBorrowedListNeverContainsNull() {
        Member member = new Member("Quinn", "quinn@example.com", "A006");
        try {
            member.addBorrowedBook(null);
        } catch (Exception ignored) {
        }

        assertFalse(member.getBorrowedBookList().contains(null));
    }

    //Property-Based: Tests size after add/remove returns to original state
    @Test
    public void testBorrowedListSizeRestoredAfterAddRemove() {
        Member member = new Member("Quinn", "quinn@example.com", "A007");
        int initialSize = member.getBorrowedBookList().size();

        Book book = new Book("Title3", "Author", 2025, "ISBN3", "B003", true, "Mystery");
        member.addBorrowedBook(book);
        member.removeBorrowedBook(book);

        assertEquals(initialSize, member.getBorrowedBookList().size());
    }
}