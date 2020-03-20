package seedu.address.testutil;

import seedu.address.model.Pet;

/** A utility class containing a variety of {@code Pet} objects to be used in tests. */
public class TypicalPet {

    private TypicalPet() {} // prevents instantiation

    /** Returns an {@code Pet} with all the default attributes */
    public static Pet getTypicalPet() {
        Pet pet = new Pet();
        return pet;
    }
}
