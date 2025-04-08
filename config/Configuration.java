package LibraryManagementSystem;

import nl.tudelft.cse1110.andy.config.RunConfiguration;
import java.util.List;
import java.util.Map;

/**
 * Configuration for the Library Management System project.
 * This setup tests the Library class, with balanced grading weights.
 */
public class Configuration extends RunConfiguration {

    @Override
    public Map<String, Float> weights() {
        return Map.of(
                "coverage",   0.25f,
                "mutation",   0.25f,
                "meta",       0.25f,
                "codechecks", 0.25f
        );
    }

    @Override
    public List<String> classesUnderTest() {
        return List.of("LibraryManagementSystem.Library");
    }
}
