package org.tnt.task2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;
import org.tnt.task2.AudioManager.AudioManager;

public class MainController {
    private static AudioManager audioManager = new AudioManager();
    private String text;
    @FXML
    private Label outputText;
    @FXML
    private ToggleButton toggleBtn;
    @FXML
    private Button button;
    @FXML
    public void handleToggle(ActionEvent event) throws InterruptedException {
        if (toggleBtn.isSelected()) {
            audioManager.start();
            toggleBtn.setText("Выключить запись");
        } else {
            audioManager.stop();
            toggleBtn.setText("Включить запись");
        }
    }

    @FXML
    public void language(ActionEvent event) throws InterruptedException {
        switch (button.getText()) {
            case "Русский" -> {
                audioManager.stop();
                toggleBtn.setSelected(false);
                audioManager.setLanguage("EN");
                button.setText("Английский");
            }
            case "Английский" -> {
                audioManager.stop();
                toggleBtn.setSelected(false);
                audioManager.setLanguage("RU");
                button.setText("Русский");}
        }
    }


    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {
                            outputText.setText(audioManager.getText());
                        }
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}