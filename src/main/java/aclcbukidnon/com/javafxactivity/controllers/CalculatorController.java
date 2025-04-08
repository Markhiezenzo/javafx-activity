package aclcbukidnon.com.javafxactivity.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorController {
    @FXML private Label displayLabel; // Label to display the result
    @FXML private Button button7, button8, button9, button4, button5, button6, button1, button2, button3, button0, buttonDot;
    @FXML private Button buttonPlus, buttonMinus, buttonMultiply, buttonDivide, buttonClear, buttonEqual, buttonBackspace;

    private String currentInput = "";

    @FXML
    public void initialize() {
        // Set actions for buttons
        button7.setOnAction(event -> appendNumber("7"));
        button8.setOnAction(event -> appendNumber("8"));
        button9.setOnAction(event -> appendNumber("9"));
        button4.setOnAction(event -> appendNumber("4"));
        button5.setOnAction(event -> appendNumber("5"));
        button6.setOnAction(event -> appendNumber("6"));
        button1.setOnAction(event -> appendNumber("1"));
        button2.setOnAction(event -> appendNumber("2"));
        button3.setOnAction(event -> appendNumber("3"));
        button0.setOnAction(event -> appendNumber("0"));
        buttonDot.setOnAction(event -> appendDot());

        buttonPlus.setOnAction(event -> appendOperator("+"));
        buttonMinus.setOnAction(event -> appendOperator("-"));
        buttonMultiply.setOnAction(event -> appendOperator("*"));
        buttonDivide.setOnAction(event -> appendOperator("/"));
        buttonClear.setOnAction(event -> clearDisplay());
        buttonEqual.setOnAction(event -> calculateResult());
        buttonBackspace.setOnAction(event -> backspace());
    }

    // Append a number to the current input
    private void appendNumber(String number) {
        currentInput += number;
        updateDisplay();
    }

    // Append a dot for decimal numbers
    private void appendDot() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
        }
        updateDisplay();
    }

    // Append an operator (+, -, *, /)
    private void appendOperator(String operator) {
        if (currentInput.isEmpty()) return; // Prevent adding operator at the beginning
        currentInput += " " + operator + " ";
        updateDisplay();
    }

    // Clear the display
    private void clearDisplay() {
        currentInput = "";
        updateDisplay();
    }

    // Backspace function
    private void backspace() {
        if (currentInput.length() > 0) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        }
        updateDisplay();
    }

    // Calculate the result
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

    // Update the display label
    private void updateDisplay() {
        displayLabel.setText(currentInput.isEmpty() ? "0" : currentInput);
    }

    // Simple eval method to evaluate the expression (basic operations)
    private double eval(String expression) {
        String[] tokens = expression.split(" ");
        double num1 = Double.parseDouble(tokens[0]);
        double num2 = Double.parseDouble(tokens[2]);
        String operator = tokens[1];

        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    throw new ArithmeticException("Cannot divide by zero");
                }
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
}
