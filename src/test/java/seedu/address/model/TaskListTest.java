package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MA1521;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.HOMEWORK10;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;

public class TaskListTest {

    private final TaskList taskList = new TaskList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskList.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTaskList_replacesData() {
        TaskList newData = getTypicalTaskList();
        taskList.resetData(newData);
        assertEquals(newData, taskList);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Task editedAlice =
                new TaskBuilder(HOMEWORK10)
                        .withDescription(VALID_DESCRIPTION_TASK2)
                        .withTags(VALID_TAG_MA1521)
                        .build();
        List<Task> newPersons = Arrays.asList(HOMEWORK10, editedAlice);
        TaskListStub newData = new TaskListStub(newPersons);

        assertThrows(DuplicateTaskException.class, () -> taskList.resetData(newData));
    }

    @Test
    public void hasTask_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.hasTask(null));
    }

    @Test
    public void hasTask_personNotInTaskList_returnsFalse() {
        assertFalse(taskList.hasTask(HOMEWORK10));
    }

    @Test
    public void hasTask_personInTaskList_returnsTrue() {
        taskList.addTask(HOMEWORK10);
        assertTrue(taskList.hasTask(HOMEWORK10));
    }

    @Test
    public void hasTask_personWithSameIdentityFieldsInTaskList_returnsTrue() {
        taskList.addTask(HOMEWORK10);
        Task editedAlice =
                new TaskBuilder(HOMEWORK10)
                        .withDescription(VALID_DESCRIPTION_TASK2)
                        .withTags(VALID_TAG_MA1521)
                        .build();
        assertTrue(taskList.hasTask(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> taskList.getTaskList().remove(0));
    }

    /** A stub ReadOnlyTaskList whose persons list can violate interface constraints. */
    private static class TaskListStub implements ReadOnlyTaskList {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TaskListStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }
}
