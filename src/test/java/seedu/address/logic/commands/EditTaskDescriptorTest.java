package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASK1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MA1521;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_TASK1);
        assertTrue(DESC_TASK1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_TASK1.equals(DESC_TASK1));

        // null -> returns false
        assertFalse(DESC_TASK1.equals(null));

        // different types -> returns false
        assertFalse(DESC_TASK1.equals(5));

        // different values -> returns false
        assertFalse(DESC_TASK1.equals(DESC_TASK2));

        // different name -> returns false
        EditTaskDescriptor editedAmy =
                new EditTaskDescriptorBuilder(DESC_TASK1).withName(VALID_NAME_TASK2).build();
        assertFalse(DESC_TASK1.equals(editedAmy));

        // different priority -> returns false
        editedAmy =
                new EditTaskDescriptorBuilder(DESC_TASK1)
                        .withPriority(VALID_PRIORITY_TASK2)
                        .build();
        assertFalse(DESC_TASK1.equals(editedAmy));

        // different address -> returns false
        editedAmy =
                new EditTaskDescriptorBuilder(DESC_TASK1)
                        .withDescription(VALID_DESCRIPTION_TASK2)
                        .build();
        assertFalse(DESC_TASK1.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_TASK1).withTags(VALID_TAG_MA1521).build();
        assertFalse(DESC_TASK1.equals(editedAmy));
    }
}
