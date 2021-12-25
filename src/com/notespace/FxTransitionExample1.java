package com.notespace;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class FxTransitionExample1 extends Application
{
    public static void main(String[] args) 
    {
        Application.launch(args);
    }
     
    @Override
    public void start(Stage stage) 
    {
        // Create a green Rectangle
        Rectangle rect = new Rectangle(400, 200, Color.GREEN);
        // Create the HBox
        HBox root = new HBox(rect);
        // Set the Style-properties of the HBox
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
 
        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("A Fade-in and Fade-out Transition Example");
        // Display the Stage
        stage.show();
         
        // Set up a fade-in and fade-out animation for the rectangle
        FadeTransition trans = new FadeTransition(Duration.seconds(2), rect);
        trans.setFromValue(1.0);
        trans.setToValue(.20);
        // Let the animation run forever
        trans.setCycleCount(FadeTransition.INDEFINITE);
        // Reverse direction on alternating cycles
        trans.setAutoReverse(true);
        // Play the Animation
        trans.play();
    }
}