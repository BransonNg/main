package seedu.address.model;

public interface ReadOnlyPomodoro {
    public String getRunningTask();

    public String getDefaultTime();

    public String getTimeLeft();
}
