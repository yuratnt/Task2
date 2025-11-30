package org.tnt.task2.AudioManager;

import org.json.JSONObject;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;


public class AudioManager {
    private String language = "RU";

    Thread thread;
    private boolean status = false;
    private String text;
    private StringBuilder stringBuilder = new StringBuilder();
    public void start() {
        thread = new Thread(() -> {
            LibVosk.setLogLevel(LogLevel.DEBUG);
            AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            try (Model model = new Model("src/main/resources/model/" + language);
                 Recognizer recognizer = new Recognizer(model, 16000)) {

                TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(format);
                microphone.start();

                byte[] buffer = new byte[4096];
                int bytesRead;

                status = true;
                while (status) {
                    bytesRead = microphone.read(buffer, 0, buffer.length);
                    if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                        text = recognizer.getResult();
                        JSONObject json = new JSONObject(text);
                        text = json.getString("text");
                        if (!text.isEmpty()) {
                            stringBuilder.append(" ");
                            text = null;
                        }
                        stringBuilder.append(json.getString("text"));
                    }
                }
                microphone.stop();
                microphone.close();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }
    public void stop() throws InterruptedException {
        status = false;
        stringBuilder = new StringBuilder();
    }

    public String getText() {
        return stringBuilder.toString();
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
