package aclcbukidnon.com.javafxactivity.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

public class TodoController {

    @FXML
    private ListView<String> todoList;
    private ObservableList<String> todos;

    @FXML
    private VBox root;  // root is the VBox that holds everything

    private Timeline gradientTimeline;

    @FXML
    public void initialize() {
        // Initialize with sample todos
        todos = FXCollections.observableArrayList(
                "Buy milk",
                "Finish JavaFX project",
                "Pet the cat"
        );

        todoList.setItems(todos);
        todoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Double-click to edit
        todoList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                editSelectedTodo();
            }
        });

        // Start the gradient background animation
        startGradientAnimation();
    }

    private void startGradientAnimation() {
        // Initial Colors for the gradient background
        Color color1 = Color.web("#FF9A8B"); // Coral
        Color color2 = Color.web("#FF6B95"); // Pink-red
        Color color3 = Color.web("#FF9671"); // Orange
        Color color4 = Color.web("#FF8E53"); // Dark orange

        // Timeline to animate the gradient change smoothly over time
        gradientTimeline = new Timeline();

        // Create KeyFrames to animate the blending of colors over time
        for (int i = 0; i < 100; i++) { // Create a smooth transition with 100 frames
            final double progress = i / 100.0;

            // Interpolate between the colors (blend the colors over time)
            Color blendedColor = blendColors(color1, color2, progress);

            // Add KeyFrame at the progress point (from 0 to 1)
            gradientTimeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(i * 0.05), // Slight delay between keyframes for smooth transition
                            new KeyValue(root.backgroundProperty(), createGradientBackground(blendedColor, blendedColor))
                    )
            );
        }

        gradientTimeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        gradientTimeline.setRate(0.5); // Slow down the animation for smoother transition
        gradientTimeline.play(); // Start animation
    }

    private Color blendColors(Color color1, Color color2, double progress) {
        // Interpolate each color component (red, green, blue, alpha)
        double red = interpolate(color1.getRed(), color2.getRed(), progress);
        double green = interpolate(color1.getGreen(), color2.getGreen(), progress);
        double blue = interpolate(color1.getBlue(), color2.getBlue(), progress);
        double alpha = interpolate(color1.getOpacity(), color2.getOpacity(), progress);

        return new Color(red, green, blue, alpha);
    }

    private double interpolate(double startValue, double endValue, double progress) {
        return startValue + (endValue - startValue) * progress;
    }

    private Background createGradientBackground(Color startColor, Color endColor) {
        // Create a smooth gradient from one color to the other
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, startColor),
                new Stop(1, endColor)
        );
        return new Background(new BackgroundFill(gradient, null, null));
    }

    // ==== CRUD OPERATIONS ==== //
    @FXML
    protected void onCreateClick() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New Todo");
        dialog.setHeaderText("Add a new task");
        dialog.setContentText("Todo:");

        dialog.showAndWait().ifPresent(text -> {
            if (!text.trim().isEmpty()) {
                todos.add(text);
            }
        });
    }

    @FXML
    protected void onDeleteClick() {
        String selected = todoList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Todo");
        confirm.setHeaderText("Delete '" + selected + "'?");
        confirm.setContentText("This action cannot be undone.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                todos.remove(selected);
            }
        });
    }

    @FXML
    protected void onListEdit() {
        editSelectedTodo();
    }

    // ==== HELPER METHODS ==== //
    private void editSelectedTodo() {
        String selected = todoList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        TextInputDialog dialog = new TextInputDialog(selected);
        dialog.setTitle("Edit Todo");
        dialog.setHeaderText("Update your task");
        dialog.setContentText("Todo:");

        dialog.showAndWait().ifPresent(newText -> {
            if (!newText.trim().isEmpty()) {
                int index = todos.indexOf(selected);
                todos.set(index, newText);
            }
        });
    }

    // ==== BONUS: KEYBOARD SHORTCUTS ==== //
    @FXML
    protected void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            onDeleteClick();
        } else if (event.isControlDown() && event.getCode() == KeyCode.N) {
            onCreateClick();
        } else if (event.getCode() == KeyCode.ENTER) {
            onListEdit();
        }
    }
}
