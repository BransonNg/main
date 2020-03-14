package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Timer;
import seedu.address.model.ReadOnlyTimer;

@JsonRootName(value = "pomodoro")
class JsonAdaptedPomodoro {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Pet's %s field is missing!";

    private final String name;
    private final String exp;
    private final String level;

    /** Constructs a {@code JsonAdaptedTask} with the given person details. */
    @JsonCreator
    public JsonAdaptedPomodoro(
            @JsonProperty("name") String name,
            @JsonProperty("exp") String exp,
            @JsonProperty("level") String level) {
        this.name = name;
        this.exp = exp;
        this.level = level;
    }

    /** Converts a given {@code Task} into this class for Jackson use. */
    public JsonAdaptedPet(ReadOnlyPet source) {
        name = source.getName();
        exp = source.getExp();
        level = source.getLevel();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *     person.
     */
    public ReadOnlyPet toModelType() throws IllegalValueException {
        
        return new Pet(name, exp, level);
    }
}
