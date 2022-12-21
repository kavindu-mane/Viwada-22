package com.hexa2zero.viwada_22;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class StartController {
    @FXML
    public StackPane stackPane;
    @FXML
    public HBox hbox;
    @FXML
    public VBox detailsVbox;
    @FXML
    public TextField nameLabel;
    @FXML
    public VBox imageBox;
    private static int teamCount = 1;
    protected static final ArrayList<String> teams = new ArrayList<>();

    protected static String fxml = "timer-view.fxml";

    public void initialize(){
        Platform.runLater(() -> hbox.requestFocus());
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        imageBox.setStyle("-fx-padding:" + (screenBounds.getWidth() - 1510)/2 + "px");
    }

    @FXML
    protected void nextButtonClick() throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Scene scene = detailsVbox.getScene();

        parent.translateXProperty().set(scene.getWidth());
        stackPane.getChildren().add(parent);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(parent.translateXProperty() , 0 , Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(500),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished((event) -> stackPane.getChildren().remove(hbox));
        timeline.play();

    }
    @FXML
    protected void addButtonClick(){
        String name = nameLabel.getText();
        if (!name.equals("")) {
            Label label = new Label(teamCount + ". " + name);
            teams.add(name);
            nameLabel.clear();
            label.getStyleClass().add("teamNameList");
            detailsVbox.getChildren().add(label);
            teamCount++;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Name is required");
            alert.showAndWait();
        }
    }

    @FXML
    public void keys(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            addButtonClick();
        }else if (event.getCode() == KeyCode.E){
            System.exit(0);
        }
    }

    @FXML
    public void onExitClick(){
        System.exit(0);
    }
}