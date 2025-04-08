package aclcbukidnon.com.javafxactivity.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class TrafficLightController {

    private enum TrafficLightColor {
        STOP,
        HOLD,
        GO,
        EMERGENCY,
        WARNING_BLINK
    }

    private TrafficLightColor currentColor = TrafficLightColor.STOP;
    private Timeline timeline;
    private boolean isBlinking = false;

    @FXML
    private Circle redLight, yellowLight, greenLight;
    @FXML
    private Button btnStop, btnHold, btnGo, btnEmergency, btnWarning;

    @FXML
    public void initialize() {
        // Auto-cycle timeline (3s per state)
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> autoCycle())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        updateLights();

        // Button actions
        btnStop.setOnAction(e -> setManualState(TrafficLightColor.STOP));
        btnHold.setOnAction(e -> setManualState(TrafficLightColor.HOLD));
        btnGo.setOnAction(e -> setManualState(TrafficLightColor.GO));
        btnEmergency.setOnAction(e -> toggleEmergency());
        btnWarning.setOnAction(e -> toggleWarning());
    }

    private void autoCycle() {
        if (currentColor == TrafficLightColor.EMERGENCY ||
                currentColor == TrafficLightColor.WARNING_BLINK) return;

        switch (currentColor) {
            case STOP -> currentColor = TrafficLightColor.HOLD;
            case HOLD -> currentColor = TrafficLightColor.GO;
            case GO -> currentColor = TrafficLightColor.STOP;
        }
        updateLights();
    }

    private void updateLights() {
        redLight.setFill(currentColor == TrafficLightColor.STOP ||
                currentColor == TrafficLightColor.EMERGENCY ? Color.RED : Color.GRAY);

        yellowLight.setFill(currentColor == TrafficLightColor.HOLD ? Color.YELLOW :
                (currentColor == TrafficLightColor.WARNING_BLINK && isBlinking) ? Color.YELLOW : Color.GRAY);

        greenLight.setFill(currentColor == TrafficLightColor.GO ? Color.GREEN : Color.GRAY);
    }

    private void setManualState(TrafficLightColor state) {
        timeline.pause();
        currentColor = state;
        updateLights();
    }

    private void toggleEmergency() {
        if (currentColor != TrafficLightColor.EMERGENCY) {
            timeline.pause();
            currentColor = TrafficLightColor.EMERGENCY;
        } else {
            currentColor = TrafficLightColor.STOP;
            timeline.play();
        }
        updateLights();
    }

    private void toggleWarning() {
        if (currentColor != TrafficLightColor.WARNING_BLINK) {
            timeline.pause();
            currentColor = TrafficLightColor.WARNING_BLINK;
            startBlinking();
        } else {
            stopBlinking();
            currentColor = TrafficLightColor.STOP;
            timeline.play();
        }
    }

    private void startBlinking() {
        Timeline blinkTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> {
                    isBlinking = !isBlinking;
                    updateLights();
                })
        );
        blinkTimeline.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline.play();
    }

    private void stopBlinking() {
        isBlinking = false;
        updateLights();
    }
}