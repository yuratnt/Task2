package org.tnt.task2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import org.tnt.task2.AudioManager.AudioManager;

public class MainController {

    private AudioManager audioManager = new AudioManager();


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
}