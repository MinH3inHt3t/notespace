package com.notespace.ScheduleTest;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ProgressBar progressBar = new ProgressBar();
        StackPane root = new StackPane(progressBar);
        Scene scene = new Scene(root, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        for(int i=0; i<100; i++){
                            updateProgress(i, 100);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                };
            }
        };
        progressBar.progressProperty().bind(service.progressProperty());
        service.start();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}