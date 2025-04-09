package aclcbukidnon.com.javafxactivity.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

public class CounterController {
    @FXML private Button decrementButton;
    @FXML private Label counterLabel;
    @FXML private Button incrementButton;
    @FXML private HBox root;

    private int counter = 0;
    private AudioClip incrementSound;
    private AudioClip decrementSound;
    private Timeline currentAnimation;

    // Color palettes
    private final Color[] incrementColors = {
            Color.web("#4FACFE"), // Blue
            Color.web("#00F2FE"), // Teal
            Color.web("#A6C1EE"), // Light blue
            Color.web("#FBC2EB")  // Pink
    };

    private final Color[] decrementColors = {
            Color.web("#FF9A8B"), // Coral
            Color.web("#FF6B95"), // Pink-red
            Color.web("#FF9671"), // Orange
            Color.web("#FF8E53")  // Dark orange
    };

    @FXML
    public void initialize() {
        try {
            incrementSound = new AudioClip(getClass().getResource("/aclcbukidnon/com/javafxactivity/sound/click_sound.mp3").toExternalForm());
            decrementSound = new AudioClip(getClass().getResource("/aclcbukidnon/com/javafxactivity/sound/click_sound.mp3").toExternalForm());
        } catch (Exception e) {
            System.err.println("Sound files not found: " + e.getMessage());
        }

        updateCounterDisplay();

        // Initial gradient
        setGradientBackground(incrementColors[0], incrementColors[1]);

        // Resize listener for fullscreen/resizing
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            Background bg = root.getBackground();
            if (bg != null && !bg.getFills().isEmpty()) {
                LinearGradient lg = (LinearGradient) bg.getFills().get(0).getFill();
                root.setBackground(createGradientBackground(lg.getStops().get(0).getColor(), lg.getStops().get(1).getColor()));
            }
        });

        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            Background bg = root.getBackground();
            if (bg != null && !bg.getFills().isEmpty()) {
                LinearGradient lg = (LinearGradient) bg.getFills().get(0).getFill();
                root.setBackground(createGradientBackground(lg.getStops().get(0).getColor(), lg.getStops().get(1).getColor()));
            }
        });
    }

    private void incrementCounter() {
        counter++;
        updateCounterDisplay();
        playSound(incrementSound);
        animateGradientTransition(incrementColors);
    }

    private void decrementCounter() {
        counter--;
        updateCounterDisplay();
        playSound(decrementSound);
        animateGradientTransition(decrementColors);
    }

    private void playSound(AudioClip sound) {
        if (sound != null) {
            sound.play();
        }
    }

    private void updateCounterDisplay() {
        counterLabel.setText(String.valueOf(counter));
    }

    private void animateGradientTransition(Color[] colorPalette) {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }

        Color startColor1 = Color.web("#4FACFE");
        Color startColor2 = Color.web("#00F2FE");

        Background currentBg = root.getBackground();
        if (currentBg != null && !currentBg.getFills().isEmpty()) {
            if (currentBg.getFills().get(0).getFill() instanceof LinearGradient lg) {
                startColor1 = lg.getStops().get(0).getColor();
                startColor2 = lg.getStops().get(1).getColor();
            }
        }

        Color targetColor1 = colorPalette[Math.abs(counter) % colorPalette.length];
        Color targetColor2 = colorPalette[Math.abs(counter + 1) % colorPalette.length];

        ObjectProperty<Color> color1 = new SimpleObjectProperty<>(startColor1);
        ObjectProperty<Color> color2 = new SimpleObjectProperty<>(startColor2);

        color1.addListener((obs, oldVal, newVal) -> {
            root.setBackground(createGradientBackground(newVal, color2.get()));
        });

        color2.addListener((obs, oldVal, newVal) -> {
            root.setBackground(createGradientBackground(color1.get(), newVal));
        });

        currentAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(color1, startColor1),
                        new KeyValue(color2, startColor2)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(color1, targetColor1),
                        new KeyValue(color2, targetColor2)
                )
        );

        currentAnimation.play();
    }

    private Background createGradientBackground(Color color1, Color color2) {
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, color1),
                new Stop(1, color2)
        );
        return new Background(new BackgroundFill(gradient, null, null));
    }

    private void setGradientBackground(Color color1, Color color2) {
        root.setBackground(createGradientBackground(color1, color2));
    }

    @FXML
    private void handleIncrement() {
        incrementCounter();
    }

    @FXML
    private void handleDecrement() {
        decrementCounter();
    }
}
