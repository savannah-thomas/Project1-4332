package LibraryManagementSystem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.InputStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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


    //LIBRARY EXCEPTION TESTS BELOW

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

    //removeBook requires book to be in library
    //Specification: removeBook throws IllegalArg when book doesn't exist
    @Test
    public void testRemoveNonExistentBook() {
        library.addBook(book);
        assertThrows(IllegalArgumentException.class, () -> library.removeBook("B999"));
    }

    //Specification: ensures returnBook doesn't change book's availability when book wasn't checked out
    @Test
    public void testReturnUnborrowedBook() {
        library.addBook(book);
        library.returnBook("B001"); // Not checked out
        assertTrue(library.bookAvailability("B001"));
    }

    //revokeMembership requires that member be registered
    //Specification: revokeMembership throws IllegalArg when member doesn't exist
    @Test
    public void testRevokeNonExistentMember() {
        assertThrows(IllegalArgumentException.class, () -> library.revokeMembership("M999"));
    }

    //MEMBER CLASS TESTS BELOW

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
        Member member = new Member("Josh", "josh@example.com", "A001");
        assertThrows(NullPointerException.class, () -> member.addBorrowedBook(null));
    }

    //Structural: Tests that removeBorrowedBook throws on null
    @Test
    public void testRemoveBorrowedBookThrowsOnNull() {
        Member member = new Member("Josh", "josh@example.com", "A002");
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

    //Ensures a member can't borrow same book twice
    //Specification: checkOutBook should throw IllegalArg is book is already out
    @Test
    public void testMemberCannotBorrowSameBookTwice() {
        library.addBook(book);
        library.addMember("M123");
        library.checkoutBook("B001", "M123");
        assertThrows(IllegalArgumentException.class, () -> library.checkoutBook("B001", "M123"));
    }

    //BOOK CLASSES TEST BELOW

    //Specification: Ensures newly created book that is available returns true
    @Test
    public void testCheckAvailability() {
        Book b = new Book("The Hunger Games", "Collins", 2016, "123456", "A001", true, "Science");
        assertTrue(b.checkAvailability());
    }

    ////////// TESTS FOR PROJECT 2 //////////

    //PURCHASING CLASS TESTS BELOW

    //Property-Based: Checks that numerous outputs of generateBookCost() satisfies that price be in b/t $10 and $100
    @Test
    public void testPurchaseBookPriceRange() {
        Purchasing purchasing = new Purchasing();

        //tests up to 100 purchases to ensure all are within the expected range
        for (int i = 0; i < 100; i++) {
            double price = purchasing.generateBookCost();
            assertTrue(price >= 10.0 && price <= 100.0,
                    "Book price out of range: " + price);
        }
    }

    //LIBRARY ACCOUNT CLASS TESTS BELOW

    //Specification: verifies that IllegalArg is thrown when a salary withdrawal is over operating cash balance
    //and ensures operating cash balance doesn't change after a failed withdrawal
    @Test
    public void testSalaryWithdrawalDoesNotOverdraw() {
        Librarians librarians = new Librarians();
        LibraryAccounts accounts = new LibraryAccounts(librarians);
        double initialBalance = accounts.getOperatingCashBalance();
        double withdrawAmount = initialBalance + 1000;

        try {
            accounts.withdrawSalary("Alice", withdrawAmount);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
        }

        //Balance should remain unchanged
        assertEquals(initialBalance, accounts.getOperatingCashBalance(), 0.001,
                "Balance should remain unchanged after failed withdrawal.");
    }

    //Specification: verifies that a donation added to the operating cash balance increases by donated amt
    //ensures correctness of the addDonation method and confirms that donations are correct in the library's financial records
    @Test
    public void testDonationsIncreaseBalance() {
        Librarians librarians = new Librarians();
        LibraryAccounts accounts = new LibraryAccounts(librarians);
        double before = accounts.getOperatingCashBalance();
        accounts.addDonation(500.00);
        double after = accounts.getOperatingCashBalance();

        assertEquals(before + 500.00, after, 0.001, "Donation must increase balance.");
    }

    //LIBRARIANS CLASS TEST BELOW

    //Specification: verifies when full-time librarian purchases a book, their record of purchased books increases
    @Test
    public void testBookPurchaseIsLogged() {
        Librarians librarians = new Librarians();
        int before = librarians.getBooksPurchased("Alice");

        librarians.recordBookPurchase("Alice"); // Correct method based on your class
        int after = librarians.getBooksPurchased("Alice");

        assertEquals(before + 1, after, "Book purchase log should increase by 1");
    }

    //Specification: verifies when a librarian withdraws salary multiple times, the total should increase correctly
    @Test
    public void testWithdrawnSalaryAccumulates() {
        Librarians librarians = new Librarians();
        librarians.recordSalary("Alice", 1000.00);
        librarians.recordSalary("Alice", 500.00);

        assertEquals(1500.00, librarians.getTotalSalaryWithdrawn("Alice"), 0.001,
                "Total salary increased correctly.");
    }
}