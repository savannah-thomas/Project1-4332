package LibraryManagementSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    //Specification Based Tests
    //Specifically, shown w/ Exception Handling Tests

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
}

