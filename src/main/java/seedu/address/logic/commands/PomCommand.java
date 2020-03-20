package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMER;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

public class PomCommand extends Command {

    public static final int DEFAULT_TIMER = 25;

    public static final String COMMAND_WORD = "pom";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Starts the pomodoro timer, focusing on "
                    + "the task identified by the index number used in the displayed task list.\n"
                    + "Parameters: 1-INDEXed (must be a positive integer)\n"
                    + "Example: "
                    + COMMAND_WORD
                    + " 1 "
                    + PREFIX_TIMER
                    + " 10";

    public static final String PAUSE_MESSAGE = "Pomodoro paused.";

    public static final String CONTINUE_MESSAGE = "Pomodoro continuing.";

    private final Index targetIndex;
    private final float timerAmount;
    private final boolean isPause;
    private final boolean isContinue;

    public PomCommand(Index targetIndex, float timerAmount) {
        this.targetIndex = targetIndex;
        this.timerAmount = timerAmount;
        this.isPause = false;
        this.isContinue = false;
    }

    public PomCommand(boolean isPause, boolean isContinue) {
        this.targetIndex = null;
        this.timerAmount = 0;
        this.isPause = isPause;
        this.isContinue = isContinue;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isPause) {
            return new PomCommandResult(
                    PAUSE_MESSAGE, null, 0, model, -1, null, isPause, isContinue);
        }

        if (isContinue) {
            return new PomCommandResult(
                    COMMAND_WORD, null, 0, model, -1, null, isPause, isContinue);
        }

        List<Task> lastShownList = model.getFilteredTaskList();
        int index = targetIndex.getZeroBased();
        if (index >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Task taskToPom = lastShownList.get(index);

        return new PomCommandResult(
                "Pomming task: " + taskToPom.toString(),
                taskToPom.getName().toString(),
                timerAmount,
                model,
                index,
                lastShownList,
                isPause,
                isContinue);
    }
}
