package seedu.address.model;

public class Pet implements ReadOnlyPet {
    public static String currName = "BB Productive";
    public static String currExp = "100";
    public static String currLevel = "1";

    public String exp;
    public String level;
    public String name;

    public Pet(String name, String exp, String level) {
        this.exp = exp;
        this.level = level;
        this.name = name;
    }

    public Pet() {
        this.exp = currExp;
        this.level = currLevel;
        this.name = currName;
    }

    @Override
    public String toString() {
        return String.format("Hi I'm pet %s! my Exp is %s and my level is %s", name, exp, level);
    }

    public Pet getPet() {
        return new Pet(currName, currExp, currLevel);
    }
}