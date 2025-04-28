package LibraryManagementSystem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

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

    //Tests that part-time librarians are rejected from utilizing this methods.
    @Test
    public void testPurchaseBookOnlyFullTimeLibrarians() {
        Librarians librarians = new Librarians();
        LibraryAccounts accounts = new LibraryAccounts(librarians);
        assertThrows(SecurityException.class, () -> accounts.purchaseBook("Carl"));
    }

    //Tests that the cost of the book purchased is equal to the cost reducted from operating balance.
    @Test
    public void testPurchaseBookCosts() {
        Librarians librarians = new Librarians();
        LibraryAccounts accounts = new LibraryAccounts(librarians);
        double initialBalance = accounts.getOperatingCashBalance();
        double cost = accounts.purchaseBook("Alice");
        assertEquals(initialBalance - cost, accounts.getOperatingCashBalance());
    }

    //Tests that when the operating balance is too low a book purchase fails accordingly.
    @Test
    public void testPurchaseBookInsufficient() {
        Librarians librarians = new Librarians();
        LibraryAccounts accounts = new LibraryAccounts(librarians);
        accounts.withdrawSalary("Alice", 38999);
        assertThrows(IllegalStateException.class, () -> accounts.purchaseBook("Alice"));
    }

    // addDonation: zero or negative amounts should throw
    @Test
    public void testAddDonationInvalidAmount() {
        LibraryAccounts accounts = new LibraryAccounts(new Librarians());
        assertThrows(IllegalArgumentException.class, () -> accounts.addDonation(0));
        assertThrows(IllegalArgumentException.class, () -> accounts.addDonation(-50.0));
    }

    // withdrawSalary: part-time librarians cannot withdraw
    @Test
    public void testWithdrawSalaryPartTimeLibrarian() {
        LibraryAccounts accounts = new LibraryAccounts(new Librarians());
        assertThrows(SecurityException.class, () -> accounts.withdrawSalary("Carl", 1000.0));
    }

    // withdrawSalary: zero or negative amount should throw
    @Test
    public void testWithdrawSalaryInvalidAmounts() {
        Librarians libs = new Librarians();
        LibraryAccounts accounts = new LibraryAccounts(libs);
        assertThrows(IllegalArgumentException.class, () -> accounts.withdrawSalary("Alice", 0));
        assertThrows(IllegalArgumentException.class, () -> accounts.withdrawSalary("Alice", -500));
    }

    // withdrawSalary: successful withdrawal updates balance and records salary
    @Test
    public void testWithdrawSalarySuccess() {
        Librarians libs = new Librarians();
        LibraryAccounts accounts = new LibraryAccounts(libs);
        double initial = accounts.getOperatingCashBalance();

        accounts.withdrawSalary("Alice", 1500.0);

        assertEquals(initial - 1500.0, accounts.getOperatingCashBalance(), 0.001,
                "Balance should decrease by withdrawn amount");
        assertEquals(1500.0, libs.getTotalSalaryWithdrawn("Alice"), 0.001,
                "Recorded salary should match withdrawal");
    }

    //LIBRARIANS CLASS TEST BELOW

    //Tests that the authentication function returns correct values.
    @Test
    public void testAuthenticate() {
        Librarians librarians = new Librarians();
        //Correct+incorrect, correct+incorrect, incorrect+correct, incorrect+incorrect.
        assertEquals(librarians.authenticate("Alice", "123456"), true);
        assertEquals(librarians.authenticate("Alice", "654321"), false);
        assertEquals(librarians.authenticate("Carl", "123456"), false);
        assertEquals(librarians.authenticate("Carl", "4"), false);
    }

    //Tests if the full-time librarian check works.
    @Test
    public void testFullTime() {
        Librarians librarians = new Librarians();
        assertEquals(librarians.isFullTimeLibrarian("Alice"), true);
        assertEquals(librarians.isFullTimeLibrarian("Carl"), false);
    }


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
    ///ConsoleInterface tests

    //Helper function to simulate console input/output
    private String runWithIO(String simulatedInput, Runnable codeUnderTest) {
        InputStream  origIn  = System.in;
        PrintStream  origOut = System.out;
        ByteArrayOutputStream outputResult = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            System.setOut(new PrintStream(outputResult));
            codeUnderTest.run();
        } finally {
            System.setIn(origIn);
            System.setOut(origOut);
        }
        return outputResult.toString();
    }

    // Covers constructor null-guard in ConsoleInterface
    @Test
    public void consoleConstructorThrowsWhenDepsMissing() {
        assertThrows(IllegalArgumentException.class,
                () -> new ConsoleInterface(null,
                        new LibraryAccounts(new Librarians()),
                        new Librarians()));

        assertThrows(IllegalArgumentException.class,
                () -> new ConsoleInterface(
                        new Library(),
                        null,
                        new Librarians()));

        assertThrows(IllegalArgumentException.class,
                () -> new ConsoleInterface(
                        new Library(),
                        new LibraryAccounts(new Librarians()),
                        null));

    }



    // Full-time librarian with wrong PIN should downgrade to part-time
    @Test
    public void invalidPinFallsBackToPartTime() {
        String output = runWithIO("""
            Alice
            000000
            4
            M999
            0
            """, () -> new ConsoleInterface(
                new Library(),
                new LibraryAccounts(new Librarians()),
                new Librarians()).run());

        assertEquals(true, output.contains("Invalid code"));
        assertEquals(true, output.contains("Only full-time librarians"));
    }

    // Part-time checkout on unavailable book shows restriction message
    @Test
    public void partTimeCheckoutShowsRestriction() {
        String output = runWithIO("""
            Carl
            5
            X001
            0
            """, () -> new ConsoleInterface(
                new Library(),
                new LibraryAccounts(new Librarians()),
                new Librarians()).run());

        assertEquals(true, output.contains("Please ask a full-time librarian"));
    }

    // Return-book menu path restores availability
    @Test
    public void menuReturnBookRestoresAvailability() {
        Library lib = new Library();
        runWithIO("""
            Alice
            123456
            1
            RBook
            RAuthor
            2025
            ISBN-R
            R1
            Drama
            3
            Mike
            mike@ex.com
            M10
            5
            R1
            M10
            6
            R1
            0
            """, () -> new ConsoleInterface(lib,
                new LibraryAccounts(new Librarians()),
                new Librarians()).run());

        assertEquals(true, lib.bookAvailability("R1"));
        assertEquals(null, lib.whoHasBook("R1"));
    }

    // Purchase branch inside checkoutBook
    @Test
    public void fullTimePurchaseBranch() {
        Library lib = new Library();
        runWithIO("""
            Alice
            123456
            3
            Jane Roe
            jane@ex.com
            M222
            5
            B222
            yes
            New Book
            New Author
            2025
            ISBN222
            B222
            Sci-Fi
            M222
            0
            """, () -> new ConsoleInterface(lib,
                new LibraryAccounts(new Librarians()),
                new Librarians()).run());

        assertEquals(false, lib.bookAvailability("B222"));
        assertEquals("M222", lib.whoHasBook("B222"));
    }

    // Covers full-time decline-purchase path in checkoutBook without hanging
    @Test
    public void fullTimeDeclinePurchaseBranchThrows() {
        String input = """
        Alice
        123456
        3
        Jane Roe
        jane@ex.com
        M333
        5
        B333
        no
        M333
        0
        """;

        // Instantiate inside the lambda so its Scanner sees our ByteArrayInputStream
        assertThrows(IllegalArgumentException.class, () ->
                runWithIO(input, () -> {
                    ConsoleInterface cli = new ConsoleInterface(
                            new Library(),
                            new LibraryAccounts(new Librarians()),
                            new Librarians()
                    );
                    cli.run();
                })
        );
    }

    // Exercise most menu items as full-time librarian (except purchase dialogue)
    @Test
    public void fullTimeMenuPathCoverage() {
        Librarians      libs     = new Librarians();
        LibraryAccounts accounts = new LibraryAccounts(libs);

        runWithIO("""
            Alice
            123456
            1
            Book
            Author
            2025
            ISBN001
            B001
            Fiction
            3
            John
            john@ex.com
            M001
            5
            B001
            M001
            7
            8
            500
            10
            9
            100
            11
            2
            B001
            4
            M001
            0
            """, () -> new ConsoleInterface(new Library(), accounts, libs).run());

        assertEquals(true, accounts.getOperatingCashBalance() >= 100);
    }

    // Smoke-test ConsoleInterface.main()
    @Test
    public void mainMethodSmokeTest() {
        String output = runWithIO("""
            Carl
            0
            """, () -> ConsoleInterface.main(new String[0]));

        assertEquals(true, output.contains("Library Menu:"));
    }

    @Test
    public void consoleRemoveBookSuccess() {
        Library lib = new Library();
        String output = runWithIO("""
                Carl
                1
                Test Book
                Test Author
                2025
                ISBN123
                ID123
                Fiction
                2
                ID123
                0
                """, () -> {
            ConsoleInterface cli = new ConsoleInterface(
                    lib,
                    new LibraryAccounts(new Librarians()),
                    new Librarians());
            cli.run();
        });
        assertEquals(true, output.contains("Book removed."));
        assertEquals(false, lib.bookAvailability("ID123"));
    }

    /**
     * Part-time librarians should not be able to access full-time commands (8–11).
     * This covers the branch where isFullTime == false for those menu options.
     */
    @Test
    public void partTimeCannotAccessFullTimeCommands() {
        Librarians libs = new Librarians();
        Library lib = new Library();
        LibraryAccounts accounts = new LibraryAccounts(libs);

        String output = runWithIO("""
                Carl
                8
                9
                10
                11
                0
                """, () -> new ConsoleInterface(lib, accounts, libs).run());

        assertEquals(false, output.contains("Enter salary amount"));
        assertEquals(false, output.contains("Donation received."));
        assertEquals(false, output.contains("Current balance:"));
        assertEquals(false, output.contains("Books purchased by"));
        assertEquals(39000.0, accounts.getOperatingCashBalance());
        assertEquals(0, libs.getBooksPurchased("Carl"));
    }

}