package com.notespace.Minimalist;

import com.notespace.FileHandler.NoteSpacePathStorage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import product_out.___Bundle;

import javax.swing.*;

public class MinimalistPageUI_Test extends Application {


    public MinimalistPageUI_Test() {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public Parent startUI() {
        MinimalistPage controller = new MinimalistPage();

        Parent parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPage", controller);


        return parent;
        //	  dragFrame ( controller , stage );
    }


    public void startUINewWindow(Stage stage) {
        MinimalistPage controller = new MinimalistPage();

        Parent parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPage",controller);
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.DECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        //	  scene.setFill ( Color.TRANSPARENT );
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.show();


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Indie+Flower");
                scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Material+Icons");

            }
        });


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (NoteSpacePathStorage.CheckNoteSpacePathExists()) {
            startUINewWindow(new Stage());
        } else {
            JOptionPane.showMessageDialog(null, "Go To Choose Dir", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }
}
