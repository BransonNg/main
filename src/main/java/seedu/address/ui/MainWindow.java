package seedu.address.ui;

import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.nio.file.Paths;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.PomodoroManager;
import seedu.address.logic.PomodoroManager.PROMPT_STATE;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.PomCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Reminder;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where
 * other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private PomodoroManager pomodoro;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private PetDisplayHandler petDisplayHandler;
    private PomodoroDisplay pomodoroDisplay;
    private StatisticsDisplay statisticsDisplay;

    @FXML private StackPane commandBoxPlaceholder;

    @FXML private MenuItem helpMenuItem;

    @FXML private StackPane personListPanelPlaceholder;

    @FXML private StackPane resultDisplayPlaceholder;

    @FXML private StackPane statusbarPlaceholder;

    @FXML private StackPane petPlaceholder;

    @FXML private StackPane pomodoroPlaceholder;

    @FXML private StackPane statisticsPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic, PomodoroManager pomodoro) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.pomodoro = pomodoro;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666 is fixed in later version of
         * SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will not
         * work when the focus is in them because the key event is consumed by the
         * TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is in
         * CommandBox or ResultDisplay.
         */
        getRoot()
                .addEventFilter(
                        KeyEvent.KEY_PRESSED,
                        event -> {
                            if (event.getTarget() instanceof TextInputControl
                                    && keyCombination.match(event)) {
                                menuItem.getOnAction().handle(new ActionEvent());
                                event.consume();
                            }
                        });
    }

    /** Fills up all the placeholders of this window. */
    void fillInnerParts() {
        personListPanel = new TaskListPanel(logic.getFilteredTaskList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        petDisplayHandler = getPetDisplayHandler();
        petPlaceholder.getChildren().add(petDisplayHandler.getPetDisplay().getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTaskListFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        pomodoroDisplay = new PomodoroDisplay("No task in progress.", "25:00");
        pomodoroPlaceholder.getChildren().add(pomodoroDisplay.getRoot());
        pomodoro.setTimerLabel(pomodoroDisplay.getTimerLabel());
        pomodoro.setResultDisplay(resultDisplay);

        statisticsDisplay =
                new StatisticsDisplay("Time spent on Pomodoro over the last 7 days",
                        null,
                        Paths.get("images", "statistics", "progressBarDaily50%.png"),
                        "50 mins / 100 mins",
                        "Medals earned: 0");
        statisticsPlaceholder.getChildren().add(statisticsDisplay.getRoot());
       
    }

    /** Sets the default size based on {@code guiSettings}. */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /** Opens the help window or focuses on it if it's already opened. */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /** Closes the application. */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings =
                new GuiSettings(
                        primaryStage.getWidth(),
                        primaryStage.getHeight(),
                        (int) primaryStage.getX(),
                        (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public TaskListPanel getTaskListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText)
            throws CommandException, ParseException {

        PomodoroManager.PROMPT_STATE pomPromptState = pomodoro.getPromptState();
        switch (pomPromptState) {
            case CHECK_DONE:
                if (commandText.toLowerCase().equals("y")) {
                    CommandResult commandResult =
                            new CommandResult(
                                    "Good job! " + pomodoro.CHECK_TAKE_BREAK_MESSAGE, false, false);
                    resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
                    pomodoro.doneTask();
                    pomodoro.checkBreakActions();
                    return commandResult;
                    // Continue to next prompt from break-timer
                } else if (commandText.toLowerCase().equals("n")) {
                    CommandResult commandResult =
                            new CommandResult(
                                    "Alright, lets try again the next round! "
                                            + pomodoro.CHECK_TAKE_BREAK_MESSAGE,
                                    false,
                                    false);
                    resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
                    pomodoro.checkBreakActions();
                    return commandResult;
                } else {
                    throw new ParseException(
                            "(Please confirm) Did you manage to finish the last task?\n"
                                    + "(Y) - Task will be set to done. (N) - No changes");
                }
            case CHECK_TAKE_BREAK:
                if (commandText.toLowerCase().equals("y")) {
                    CommandResult commandResult =
                            new CommandResult("Okie doke! Rest easy now...", false, false);
                    resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
                    pomodoro.takeABreak();
                    pomodoro.setPromptState(PROMPT_STATE.NONE);
                    return commandResult;
                    // Continue to next prompt from break-timer
                } else if (commandText.toLowerCase().equals("n")) {
                    CommandResult commandResult =
                            new CommandResult("Alright, back to neutral!", false, false);
                    resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
                    pomodoro.setPromptState(PROMPT_STATE.NONE);
                    return commandResult;
                } else {
                    throw new ParseException(
                            "(Please confirm) Shall we take a 5-min break?\n"
                                    + "(Y) - 5-min timer begins. (N) - App goes neutral.");
                }
            case NONE:
                break;
        }

        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            // Pomodoro related results
            try {
                PomCommandResult pomCommandResult = (PomCommandResult) commandResult;
                if (pomCommandResult.getIsPause()) {
                    pomodoro.pause();
                } else if (pomCommandResult.getIsContinue()) {
                    pomodoro.unpause();
                } else {
                    pomodoroDisplay.setTaskInProgressText(pomCommandResult.getPommedTask());
                    pomodoro.start(pomCommandResult.getTimerAmountInMin());
                    pomodoro.setDoneParams(
                            pomCommandResult.getModel(),
                            pomCommandResult.getOriginList(),
                            pomCommandResult.getTaskIndex());
                }
            } catch (ClassCastException ce) {

            } catch (NullPointerException ne) {
                resultDisplay.setFeedbackToUser("Sorry, you've got no tasks being POMmed.");
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    @FXML
    public static void triggerReminder(Reminder reminder, String name, String description) {
        long delay = reminder.getDelay();
        Timeline timeline =
                new Timeline(
                        new KeyFrame(
                                Duration.seconds(delay),
                                ae -> {
                                    MainWindow.showReminder(name, description);
                                    reminder.setHasFired();
                                }));
        timeline.play();
    }

    @FXML
    /**
     * Is triggered at the delayed time in Duke itself.
     * https://thecodinginterface.com/blog/javafx-alerts-and-dialogs/#informational-alert
     */
    public static void showReminder(String name, String description) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reminder");
        alert.setHeaderText(name);
        alert.setContentText(description);
        alert.show();
    }

    private PetDisplayHandler getPetDisplayHandler() {
        return logic.getPetDisplayHandler();
    }
}
