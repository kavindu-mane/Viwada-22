package com.hexa2zero.viwada_22;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class TimerScene {

    @FXML
    public Label team1 ,team2 , time;
    @FXML
    public Button start , stop , reset , min4 , min5 , back1 , back2 , next1 , next2;
    @FXML
    public VBox mainVbox;
    private int teamOneIndex = 0 , teamTwoIndex = 1 , timerScaleValue = 2 , secondsCount = 299;
    private static final DecimalFormat df = new DecimalFormat("00");
    MediaPlayer mediaPlayer;
    boolean running = true;
    ScaleTransition timerScale;

    public void initialize(){
        team1.setText(StartController.teams.get(0));
        team2.setText(StartController.teams.get(1));
        String bip = "audio.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
    }

    @FXML
    public void onTeamOneBackClick(){
        if (teamOneIndex == 0){
            int index = StartController.teams.size() - 1;
            team1.setText(StartController.teams.get(index));
            teamOneIndex = index;
        }else {
            team1.setText(StartController.teams.get(teamOneIndex - 1));
            teamOneIndex -=1;
        }
    }

    @FXML
    public void onTeamOneNextClick(){
        int index = StartController.teams.size() - 1;
        if (teamOneIndex == index){
            team1.setText(StartController.teams.get(0));
            teamOneIndex = 0;
        }else {
            team1.setText(StartController.teams.get(teamOneIndex + 1));
            teamOneIndex +=1;
        }
    }

    @FXML
    public void onTeamTwoBackClick(){
        if (teamTwoIndex == 0){
            int index = StartController.teams.size() - 1;
            team2.setText(StartController.teams.get(index));
            teamTwoIndex = index;
        }else {
            team2.setText(StartController.teams.get(teamTwoIndex - 1));
            teamTwoIndex -= 1;
        }
    }

    @FXML
    public void onTeamTwoNextClick(){
        int index = StartController.teams.size() - 1;
        if (teamTwoIndex == index){
            team2.setText(StartController.teams.get(0));
            teamTwoIndex = 0;
        }else {
            team2.setText(StartController.teams.get(teamTwoIndex + 1));
            teamTwoIndex +=  1;
        }
    }

    @FXML
    public void onStartClick(){

        if (timerScaleValue == 2 || !running) {
            running = true;
            animation();

            timerScale.setOnFinished((e) ->
                new Thread(() -> {
                    while (secondsCount > 0 && running) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        Platform.runLater(() -> {
                            time.setText(secondsCount / 60 + " : " + df.format(secondsCount % 60));
                            if (secondsCount == 60) {
                                playBell(1);
                            } else if (secondsCount == 0) {
                                playBell(3);
                            }
                            secondsCount -= 1;
                        });
                    }
                }).start());
        }
    }

    @FXML
    public void onStopClick(){
        running = false;
        timerScaleValue = 2;
    }

    @FXML
    public void onResetClick() throws InterruptedException {
        if (!running || secondsCount == -1) {
            commonFunction();
        }
    }

    @FXML
    public void onFiveMinClick() throws InterruptedException {
        if (!running || secondsCount == -1 || secondsCount == 239) {
            commonFunction();
        }
    }

    @FXML
    public void onFourMinClick() throws InterruptedException {
        if (!running || secondsCount == -1 || secondsCount == 299) {
            commonFunction();
            time.setText("4 : 00");
            secondsCount = 239;
        }
    }

    private void playBell(int count){
        mediaPlayer.stop();
        new Thread(() ->{
            mediaPlayer.setCycleCount(count);
            mediaPlayer.play();
        }).start();
    }
    private void commonFunction() throws InterruptedException {
        timerScaleValue = 1;
        animation();
        time.setText("5 : 00");
        secondsCount = 299;
        timerScaleValue = 2;
        running = true;
    }

    private void animation(){
        timerScale = new ScaleTransition(Duration.millis(200) , time);

        timerScale.setToY(timerScaleValue);
        timerScale.setToX(timerScaleValue);

        timerScale.play();
        timerScaleValue = 1;
    }

    @FXML
    public void onBack(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.B) {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("start-view.fxml")));
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            Scene scene = new Scene(parent, screenBounds.getWidth(), screenBounds.getHeight());
            Application.mainStage.setScene(scene);
        }
    }
}
