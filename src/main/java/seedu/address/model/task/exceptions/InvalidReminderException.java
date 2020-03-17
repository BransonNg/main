package seedu.address.model.task.exceptions;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Signals that the reminder's time is before the current time
 */
public class InvalidReminderException extends ParseException {
    public InvalidReminderException() {
        super("Operation would result in invalid reminder due to the wrong timing");
    }
}
