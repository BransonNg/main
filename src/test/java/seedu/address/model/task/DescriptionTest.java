package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DescriptionTest {
    @Test
    public void isValidDescription() {
        // valid descriptions
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription("^")); // only non-alphanumeric characters
        assertTrue(
                Description.isValidDescription("peter*")); // contains non-alphanumeric characters
        assertTrue(Description.isValidDescription("peter jack")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("peter the 2nd")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Capital Tan")); // with capital letters
        assertTrue(Description.isValidDescription("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
