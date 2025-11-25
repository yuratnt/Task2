package org.tnt.task2.AudioManager;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioManager {

    private AudioFormat format = new AudioFormat(
            44100,
            16,
            2,
            true,
            true
    );
    private DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

    private TargetDataLine targetLine ;
    private AudioInputStream audioInputStream;
    Boolean audioStarted = false;
    public AudioManager() {
        try {
            targetLine  = (TargetDataLine) AudioSystem.getLine(info);
            if (!AudioSystem.isLineSupported(info)) {
                System.err.println("Микрофон с таким форматом не поддерживается");
                return;
            }
            targetLine  = (TargetDataLine) AudioSystem.getLine(info);
            audioInputStream = new AudioInputStream(targetLine);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }


    }
    public void start() {
        new Thread(() -> {
            try {
                targetLine.open(format);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            targetLine.start();

            int bufferSize = (int) ((double) (44100 * 16 * 2) / 8 * 0.1);
            byte[] buffer = new byte[bufferSize];

            System.out.println("Захват аудио произведён");
            audioStarted = true;
            while (audioStarted) {
                int bytesRead = targetLine.read(buffer, 0, buffer.length);
            }
        }).start();
    }

    public void stop() {
        audioStarted = false;
        targetLine.stop();
        try {
            audioInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        targetLine.close();
    }
}
