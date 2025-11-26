package org.tnt.task2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    public void handleToggle(ActionEvent event) {
        if (toggleBtn.isSelected()) {
            audioManager.start();
            System.out.println("Кнопка зажата");
        } else {
            audioManager.stop();
            System.out.println("Кнопка отжата");
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