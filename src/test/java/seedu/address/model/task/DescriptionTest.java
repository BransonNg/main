package seedu.address.model.task;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    // @Test
    // public void constructor_invalidAddress_throwsIllegalArgumentException() {
    //     String invalidAddress = "";
    //     assertThrows(IllegalArgumentException.class, () -> new Description(invalidAddress));
    // }
}
