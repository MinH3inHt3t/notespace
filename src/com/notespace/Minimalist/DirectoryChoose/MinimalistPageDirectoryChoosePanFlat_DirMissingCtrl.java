package com.notespace.Minimalist.DirectoryChoose;

import com.notespace.FileHandler.BuildNoteSpace;
import javafx.animation.PauseTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import product_out.___Bundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MinimalistPageDirectoryChoosePanFlat_DirMissingCtrl implements Initializable {

    @FXML
    public Label directoryChooseLabel;
    @FXML
    public TextField directoryNameTextField;
    @FXML
    public FlowPane contentFlowPane;


    @FXML
    public void detectFunct() {
        new DetectListService("D:");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        prepare();
        perform();
    }

    private void prepare() {

    }

    private void perform() {

    }

    class DetectListService extends Service<ArrayList<String>> {
        String partition;

        public DetectListService(String partition) {
            this.partition = partition;

            restart();
            setOnRunning(e -> {
                contentFlowPane.getChildren().clear();
                Label label = new Label("Preparing to detect folders...");
                label.getStyleClass().add("font-12");
                contentFlowPane.getChildren().add(label);

                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1.5));
                pauseTransition.setOnFinished(e1 -> {
                            label.setText("Start Detecting. Wait a few seconds to finish.");
                        }
                );
                pauseTransition.play();

            });
            setOnSucceeded(e -> {
                contentFlowPane.getChildren().clear();
                getValue().stream().forEach(dirPath -> {
                    MinimalistPageDirectoryChooseBox controller = new MinimalistPageDirectoryChooseBox(dirPath);
                    Parent parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageDirectoryChooseBox", controller);
                    contentFlowPane.getChildren().add(parent);
                });
            });
        }

        @Override
        protected Task<ArrayList<String>> createTask() {
            return new Task<ArrayList<String>>() {
                @Override
                protected ArrayList<String> call() throws Exception {
                    return BuildNoteSpace.getDetectedNoteSpacePath(partition);
                }
            };
        }
    }

}
