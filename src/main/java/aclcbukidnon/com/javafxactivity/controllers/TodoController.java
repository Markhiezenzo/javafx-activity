package aclcbukidnon.com.javafxactivity.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TodoController {

    @FXML
    private ListView<String> todoList;
    private ObservableList<String> todos;

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