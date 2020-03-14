package seedu.address.model;

import seedu.address.model.task.Task;

public class Pomodoro implements ReadOnlyPomodoro {
    private static final String DEFAULT_TIME= "25.0";
    public String time;
    public String timeLeft;
    public Task runningTask;

    public Pomodoro(String name, String exp, String level) {
        this.exp = exp;
        this.level = level;
        this.name = name;
    }

    public Pomodoro(ReadOnlyPomodoro source) {
        this.exp = source.getExp();
        this.level = source.getLevel();
        this.name = source.getName();
    }

    public Pomodoro() {
        this(DEFAULT_NAME, DEFAULT_EXP, DEFAULT_LEVEL);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String getExp() {
        return exp;
    }

    @Override
    public String toString() {
        return String.format("Hi I'm pet %s! my Exp is %s and my level is %s", name, exp, level);
    }
}
