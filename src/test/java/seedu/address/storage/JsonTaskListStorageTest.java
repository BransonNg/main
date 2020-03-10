package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.HOMEWORK10;
import static seedu.address.testutil.TypicalTasks.OPTIONAL_HOMEWORK;
import static seedu.address.testutil.TypicalTasks.OPTIONAL_LAB;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.TaskList;
import seedu.address.model.ReadOnlyTaskList;

public class JsonTaskListStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonTaskListStorageTest");

    @TempDir public Path testFolder;

    @Test
    public void readTaskList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTaskList(null));
    }

    private java.util.Optional<ReadOnlyTaskList> readTaskList(String filePath)
            throws Exception {
        return new JsonTaskListStorage(Paths.get(filePath))
                .readTaskList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(
                DataConversionException.class,
                () -> readTaskList("notJsonFormatTaskList.json"));
    }

    @Test
    public void readTaskList_invalidPersonTaskList_throwDataConversionException() {
        assertThrows(
                DataConversionException.class,
                () -> readTaskList("invalidPersonTaskList.json"));
    }

    @Test
    public void readTaskList_invalidAndValidPersonTaskList_throwDataConversionException() {
        assertThrows(
                DataConversionException.class,
                () -> readTaskList("invalidAndValidPersonTaskList.json"));
    }

    @Test
    public void readAndSaveTaskList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTaskList.json");
        TaskList original = getTypicalTaskList();
        JsonTaskListStorage jsonTaskListStorage = new JsonTaskListStorage(filePath);

        // Save in new file and read back
        jsonTaskListStorage.saveTaskList(original, filePath);
        ReadOnlyTaskList readBack = jsonTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTask(OPTIONAL_LAB);
        original.removeTask(HOMEWORK10);
        jsonTaskListStorage.saveTaskList(original, filePath);
        readBack = jsonTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        // Save and read without specifying file path
        original.addTask(OPTIONAL_HOMEWORK);
        jsonTaskListStorage.saveTaskList(original); // file path not specified
        readBack = jsonTaskListStorage.readTaskList().get(); // file path not specified
        assertEquals(original, new TaskList(readBack));
    }

    @Test
    public void saveTaskList_nullTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaskList(null, "SomeFile.json"));
    }

    /** Saves {@code taskList} at the specified {@code filePath}. */
    private void saveTaskList(ReadOnlyTaskList taskList, String filePath) {
        try {
            new JsonTaskListStorage(Paths.get(filePath))
                    .saveTaskList(taskList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTaskList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaskList(new TaskList(), null));
    }
}
