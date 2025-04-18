package aclcbukidnon.com.javafxactivity.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class CalculatorController {
    @FXML private Label displayLabel;
    @FXML private Button button7, button8, button9, button4, button5, button6;
    @FXML private Button button1, button2, button3, button0, buttonDot;
    @FXML private Button buttonPlus, buttonMinus, buttonMultiply, buttonDivide;
    @FXML private Button buttonClear, buttonEqual, buttonBackspace;
    @FXML private Button buttonSpeaker;

    private String currentInput = "";
    private MediaPlayer clickSoundPlayer;
    private MediaPlayer equalSoundPlayer;
    private boolean isSoundOn = true;

    @FXML
    public void initialize() {
        // Load normal click sound
        Media clickSound = new Media(getClass().getResource("/aclcbukidnon/com/javafxactivity/sound/click_sound.mp3").toExternalForm());
        clickSoundPlayer = new MediaPlayer(clickSound);

        // Load equal button sound
        Media equalSound = new Media(getClass().getResource("/aclcbukidnon/com/javafxactivity/sound/click_sound.mp3").toExternalForm());
        equalSoundPlayer = new MediaPlayer(equalSound);

        // Handle speaker toggle button
        buttonSpeaker.setOnAction(event -> toggleSound());

        // Set actions for buttons with sound
        button7.setOnAction(event -> playClickSoundAndAppendNumber("7"));
        button8.setOnAction(event -> playClickSoundAndAppendNumber("8"));
        button9.setOnAction(event -> playClickSoundAndAppendNumber("9"));
        button4.setOnAction(event -> playClickSoundAndAppendNumber("4"));
        button5.setOnAction(event -> playClickSoundAndAppendNumber("5"));
        button6.setOnAction(event -> playClickSoundAndAppendNumber("6"));
        button1.setOnAction(event -> playClickSoundAndAppendNumber("1"));
        button2.setOnAction(event -> playClickSoundAndAppendNumber("2"));
        button3.setOnAction(event -> playClickSoundAndAppendNumber("3"));
        button0.setOnAction(event -> playClickSoundAndAppendNumber("0"));
        buttonDot.setOnAction(event -> playClickSoundAndAppendDot());

        buttonPlus.setOnAction(event -> playClickSoundAndAppendOperator("+"));
        buttonMinus.setOnAction(event -> playClickSoundAndAppendOperator("-"));
        buttonMultiply.setOnAction(event -> playClickSoundAndAppendOperator("*"));
        buttonDivide.setOnAction(event -> playClickSoundAndAppendOperator("/"));
        buttonClear.setOnAction(event -> playClickSoundAndClearDisplay());
        buttonEqual.setOnAction(event -> playEqualSoundAndCalculateResult());
        buttonBackspace.setOnAction(event -> playClickSoundAndBackspace());
    }

    private void toggleSound() {
        isSoundOn = !isSoundOn;
        buttonSpeaker.setText(isSoundOn ? "🔊" : "🔇");
    }

    private void playClickSound() {
        if (isSoundOn) {
            clickSoundPlayer.stop();
            clickSoundPlayer.play();
        }
    }

    private void playEqualSound() {
        if (isSoundOn) {
            equalSoundPlayer.stop();
            equalSoundPlayer.play();
        }
    }

    private void playClickSoundAndAppendNumber(String number) {
        playClickSound();
        appendNumber(number);
    }

    private void playClickSoundAndAppendDot() {
        playClickSound();
        appendDot();
    }

    private void playClickSoundAndAppendOperator(String operator) {
        playClickSound();
        appendOperator(operator);
    }

    private void playClickSoundAndClearDisplay() {
        playClickSound();
        clearDisplay();
    }

    private void playEqualSoundAndCalculateResult() {
        playEqualSound();
        calculateResult();
    }

    private void playClickSoundAndBackspace() {
        playClickSound();
        backspace();
    }

    private void appendNumber(String number) {
        currentInput += number;
        updateDisplay();
    }

    private void appendDot() {
        if (!currentInput.endsWith(".")) {
            currentInput += ".";
        }
        updateDisplay();
    }

    private void appendOperator(String operator) {
        if (currentInput.isEmpty()) return;
        currentInput += " " + operator + " ";
        updateDisplay();
    }

    private void clearDisplay() {
        currentInput = "";
        updateDisplay();
    }

    private void backspace() {
        if (currentInput.length() > 0) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        }
        updateDisplay();
    }

    private void calculateResult() {
        try {
            String result = String.valueOf(eval(currentInput));
            currentInput = result;
            updateDisplay();
        } catch (Exception e) {
            currentInput = "Error";
            updateDisplay();
        }
    }

    private void updateDisplay() {
        displayLabel.setText(currentInput.isEmpty() ? "0" : currentInput);
    }

    private double eval(String expression) {
        String[] tokens = expression.split(" ");
        double num1 = Double.parseDouble(tokens[0]);
        double num2 = Double.parseDouble(tokens[2]);
        String operator = tokens[1];

        switch (operator) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/":
                if (num2 != 0) return num1 / num2;
                else throw new ArithmeticException("Cannot divide by zero");
            default: throw new IllegalArgumentException("Invalid operator");
        }
    }
}
