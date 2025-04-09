package aclcbukidnon.com.javafxactivity;

import aclcbukidnon.com.javafxactivity.controllers.SplashScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var splashScreenFxml = new FXMLLoader(Main.class.getResource("splash-screen-view.fxml"));

        var scene = new Scene(splashScreenFxml.load(), 800, 600);

        var splashScreenCtrl = (SplashScreenController)splashScreenFxml.getController();
        splashScreenCtrl.setStage(stage);


        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setTitle("Basic JavaFX Activity");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}