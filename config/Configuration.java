package LibraryManagementSystem;

import nl.tudelft.cse1110.andy.codechecker.checks.*;
import nl.tudelft.cse1110.andy.codechecker.engine.CheckScript;
import nl.tudelft.cse1110.andy.codechecker.engine.SingleCheck;
import nl.tudelft.cse1110.andy.config.MetaTest;
import nl.tudelft.cse1110.andy.config.RunConfiguration;

import java.util.List;
import java.util.Map;

/**
 *  Andy configuration for the Library‑Management‑System project.
 *
 *  – Focuses testing on the ReleaseEditions class.
 *  – Requires BookService & EmailService to be mocked.
 *  – Forbids mocking / spying ReleaseEditions and any spies in general.
 *  – Enforces that tests verify addBook() is invoked, but do **not** verify retrieveBooks().
 *  – Provides a metamorphic test that flips the keyword‑matching condition
 *    (should make a good test suite fail if it is written correctly).
 */
public class Configuration extends RunConfiguration {

    /* ---------- grading weights ---------- */
    @Override
    public Map<String, Float> weights() {
        return Map.of(
                "coverage",   0.25f,
                "mutation",   0.25f,
                "meta",       0.25f,
                "codechecks", 0.25f
        );
    }

    /* ---------- class(es) under test ---------- */
    @Override
    public List<String> classesUnderTest() {
        return List.of("LibraryManagementSystem.ReleaseEditions");
    }

    /* ---------- static code‑quality checks ---------- */
    @Override
    public CheckScript checkScript() {
        return new CheckScript(List.of(
                /* mocks that MUST appear */
                new SingleCheck("BookService should be mocked",
                        new MockClass("BookService")),
                new SingleCheck("EmailService should be mocked",
                        new MockClass("EmailService")),

                /* things that MUST *not* appear */
                new SingleCheck("ReleaseEditions should not be mocked", true,
                        new MockClass("ReleaseEditions")),
                new SingleCheck("Spies should not be used", true,
                        new MockitoSpy()),

                /* behavioural verifications that must / must‑not be present */
                new SingleCheck(2, "retrieveBooks should not be verified", true,
                        new MockitoVerify("retrieveBooks",
                                MockitoVerify.MethodType.TEST,
                                Comparison.GTE, 1)),
                new SingleCheck("addBook should be verified",
                        new MockitoVerify("addBook",
                                MockitoVerify.MethodType.TEST,
                                Comparison.GTE, 1, true))
        ));
    }

    /* ---------- metamorphic (mutation‑style) tests ---------- */
    @Override
    public List<MetaTest> metaTests() {
        return List.of(
                /* invert the keyword condition — a good test suite must fail */
                MetaTest.withLineReplacement(
                        "invert keyword‑matching condition",
                        45, 50,
                        """
                        List<String> allBooks = bookService.retrieveBooks(author);
                        for (String bookTitle : allBooks) {
                            if (!bookTitle.contains(keyword)) {
                                bookService.addBook(author, bookTitle + " - edition " + edition);
                            }
                        }
                        """
                )
        );
    }
}
