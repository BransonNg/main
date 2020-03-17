package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.nio.file.Path;

/** An UI component that displays the Pomodoro {@code Pomodoro}. */
public class StatisticsDisplay extends UiPart<Region> {

    private static final String FXML = "StatisticsDisplay.fxml";

    public String barChartTitleText; // mutable
    public int[][] data; //
    public Path progressBarDailyFilepath;
    public String progressDailyText;
    public String medalsText;

    @FXML private VBox statisticsPane;
    @FXML private Label barChartTitle;
    @FXML private BarChart<String, Integer> barChart;

    @FXML private Label progressDaily;
    @FXML private ImageView progressBarDaily;
    @FXML private Label medals;


    public StatisticsDisplay(String barChartTitleText, int[][] data,
                             Path progressBarDailyFilepath,
                             String progressDailyText,
                             String medalsText) {
        super(FXML);
        this.barChartTitleText = barChartTitleText;
        this.data = data;
        this.progressBarDailyFilepath = progressBarDailyFilepath;
        this.progressDailyText = progressDailyText;
        this.medalsText = medalsText;

        progressDaily.setText(progressDailyText);
        Image progressBarDailyImage = new Image(String.valueOf(progressBarDailyFilepath));
        progressBarDaily.setImage(progressBarDailyImage);
        medals.setText(medalsText);

        barChartTitle.setText(barChartTitleText);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Pomodoro Runtime");

        XYChart.Series<String, Integer> dataSeries1 = new XYChart.Series<>();
        dataSeries1.setName("You");

        dataSeries1.getData().add(new XYChart.Data<>("Day 1", 20));
        dataSeries1.getData().add(new XYChart.Data<>("Day 2", 40));
        dataSeries1.getData().add(new XYChart.Data<>("Day 3", 60));
        dataSeries1.getData().add(new XYChart.Data<>("Day 4", 40));
        dataSeries1.getData().add(new XYChart.Data<>("Day 5", 20));
        dataSeries1.getData().add(new XYChart.Data<>("Day 6", 40));
        dataSeries1.getData().add(new XYChart.Data<>("Day 7", 80));

        barChart.getData().add(dataSeries1);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatisticsDisplay)) {
            return false;
        }

        // state check
        StatisticsDisplay card = (StatisticsDisplay) other;
        return barChartTitle.getText().equals(card.barChartTitle.getText());
    }
}
