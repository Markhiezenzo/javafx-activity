package aclcbukidnon.com.javafxactivity.controllers;

import aclcbukidnon.com.javafxactivity.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenController {


    private Timer timer;

    private Stage stage;



    @FXML
    public ProgressBar progressInitial;


    @FXML
    public void initialize(){
        var timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> progressInitial.setProgress(0)),
                new KeyFrame(Duration.millis(200), e -> progressInitial.setProgress(0.1)),
                new KeyFrame(Duration.millis(350), e -> progressInitial.setProgress(0.3)),
                new KeyFrame(Duration.millis(400), e -> progressInitial.setProgress(0.5)),
                new KeyFrame(Duration.millis(550), e -> progressInitial.setProgress(0.7)),
                new KeyFrame(Duration.millis(600), e -> progressInitial.setProgress(0.9)),
                new KeyFrame(Duration.millis(1050), e -> progressInitial.setProgress(1.0))
        );
        timeline.play();
        timeline.setOnFinished(_ -> onCompleted());
    }

    public void onCompleted () {
        var dashboardFxml = new FXMLLoader(Main.class.getResource("dashboard-view.fxml"));
        try {
            var scene = new Scene(dashboardFxml.load(), 800, 600);
            System.out.println(stage.toString());
            this.stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setStage(Stage stage) {
        System.out.println("Set Stage");
        this.stage = stage;
        System.out.println(stage.toString());
    }
}