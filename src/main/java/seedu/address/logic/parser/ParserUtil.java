package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Reminder;
import seedu.address.model.task.exceptions.InvalidReminderException;

/** Contains utility methods used for parsing strings in the various *Parser classes. */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index[] parseIndices(String oneBasedIndices) throws ParseException {
        String trimmedIndices = oneBasedIndices.trim();
        String[] splitIndices = trimmedIndices.split("\\s*,\\s*");
        Index[] indexes = new Index[splitIndices.length];
        for (int i = 0; i < splitIndices.length; i++) {
            if (!StringUtil.isNonZeroUnsignedInteger(splitIndices[i])) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            indexes[i] = Index.fromOneBased(Integer.parseInt(splitIndices[i]));
        }
        return indexes;
    }

    /**
     * Parses a {@code String reminder} into a {@code Reminder}. Leading and trailing whitespaces
     * will be trimmed. Format to receive is DD/MM/YY@HH:mm eg 04/11/20@10:30
     *
     * @throws ParseException if the given {@code reminder} is invalid.
     * @throws InvalidReminderException
     */
    public static Reminder parseReminder(String reminder)
            throws ParseException, InvalidReminderException {
        requireNonNull(reminder);
        String trimmedReminder = reminder.trim();
        if (!Reminder.isValidReminder(trimmedReminder)) {
            throw new ParseException(Reminder.MESSAGE_CONSTRAINTS);
        }
        String dateString = trimmedReminder.split("@")[0];
        String timeString = trimmedReminder.split("@")[1];
        String[] dateArr = dateString.split("/");
        String[] timeArr = timeString.split(":");
        int dayOfMonth = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int year = Integer.parseInt(dateArr[2]) + 2000;
        int hour = Integer.parseInt(timeArr[0]);
        int minute = Integer.parseInt(timeArr[1]);
        LocalDateTime reminderTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        return new Reminder(reminderTime);
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return new Priority(trimmedPriority);
    }

    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /** Parses {@code Collection<String> tags} into a {@code Set<Tag>}. */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
