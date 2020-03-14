package seedu.address.ui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/** An UI component that displays image of a Pet {@code Pet}. */
public class PetDisplay extends UiPart<Region> {

    private static final String FXML = "PetDisplay.fxml";

    public Path filePath; // mutable

    @FXML private HBox petPane;
    @FXML private ImageView pic;

    public PetDisplay(Path filepath) {
        super(FXML);
        this.filePath = filepath;

        // set up image
        Image image = new Image(String.valueOf(filePath));

        //javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResource(filepath).toExternalForm());
        pic.setImage(image);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PetDisplay)) {
            return false;
        }

        // state check
        PetDisplay card = (PetDisplay) other;
        return pic.getImage().equals(card.pic.getImage());
    }

}
