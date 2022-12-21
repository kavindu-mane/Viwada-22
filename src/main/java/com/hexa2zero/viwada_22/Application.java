package com.hexa2zero.viwada_22;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {

    private boolean firstLoading = true;
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("start-view.fxml")));
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(parent , screenBounds.getWidth() , screenBounds.getHeight());
        if (firstLoading){
            scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Charm:wght@700&family=Raleway:wght@400;700&display=swap");
            firstLoading = false;
        }
        stage.initStyle(StageStyle.UNDECORATED);
        mainStage = stage;
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}