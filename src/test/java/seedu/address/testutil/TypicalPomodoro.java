package seedu.address.testutil;

import seedu.address.model.Pomodoro;

/** A utility class containing a list of {@code Pomodoro} objects to be used in tests. */
public class TypicalPomodoro {

    private TypicalPomodoro() {} // prevents instantiation

    /** Returns an {@code Pomdoro} with all the default attributes. */
    public static Pomodoro getTypicalPomodoro() {
        return new Pomodoro();
    }
}
