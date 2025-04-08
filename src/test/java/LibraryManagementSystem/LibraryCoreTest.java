package LibraryManagementSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryCoreTest {

    private Library library;
    private Book book;

    @BeforeEach
    public void setup() {
        library = new Library();
        book = new Book("Physics", "Smith", 2022, "123456", "B001", true, "Science");
    }

    @Test
    public void voidLtestAddBook() {
        library.addBook(book);
        assertTrue(library.bookAvailability("B001"));
        assertEquals("B001", library.findBookIdByName("Physics"));
    }

    @Test
    public void testCheckoutBook() {
        library.addBook(book);
        library.checkoutBook("B001", "M123");
        assertFalse(library.bookAvailability("B001"));
        assertEquals("M123", library.whoHasBook("B001"));
    }

    @Test
    public void testReturnBook() {
        library.addBook(book);
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
}
