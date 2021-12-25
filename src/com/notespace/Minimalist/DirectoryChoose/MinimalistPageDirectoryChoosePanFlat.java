package com.notespace.Minimalist.DirectoryChoose;

import com.notespace.FileHandler.BuildNoteSpace;
import com.notespace.FileHandler.NoteSpacePath;
import com.notespace.FileHandler.NoteSpacePathStorage;
import javafx.animation.PauseTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import product_out.___Bundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MinimalistPageDirectoryChoosePanFlat implements Initializable {

    @FXML
    public Label directoryChooseLabel;
    @FXML
    public TextField directoryNameTextField;
    @FXML
    public FlowPane contentFlowPane;
    @FXML
    ProgressBar detectProgressBar;


    @FXML
    public void refreshDetection() {
        new DetectListService("D:");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        prepare();
        perform();

//        new DetectListService("D:");
        refreshUI();
    }

    public void refreshUI() {

        String dirPath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
        directoryNameTextField.setText(dirPath);
        refreshDetection();
    }

    private void prepare() {

    }

    private void perform() {

    }

    class DetectListService extends Service<ArrayList<String>> {
        String partition;
        String currentDirPath;

        public DetectListService(String partition) {
            this.partition = partition;

            currentDirPath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
            ;

            restart();
            setOnRunning(e -> {
                detectProgressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                detectProgressBar.setVisible(true);
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
                detectProgressBar.setProgress(0);
                detectProgressBar.setVisible(false);
                contentFlowPane.getChildren().clear();
                getValue().stream().forEach(dirPath -> {
                    MinimalistPageDirectoryChooseBox controller = new MinimalistPageDirectoryChooseBox(dirPath);
                    Parent parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageDirectoryChooseBox", controller);
                    contentFlowPane.getChildren().add(parent);

//                    if (currentDirPath.equals(dirPath)) {
//                        controller.currentFolderLabel.setVisible(true);
//                    } else {
//                        controller.currentFolderLabel.setVisible(false);
//                    }

                    controller.dirChooseBoxHBox.setOnMouseClicked(event -> {
                        if (event.getButton().equals(MouseButton.PRIMARY)) {
                            String receiveDirPath = controller.returnDirPath();
                            NoteSpacePathStorage.SaveNoteSpacePath(new NoteSpacePath("", receiveDirPath));

                            controller.refresh();
//                            controller.currentFolderLabel.setVisible(true);
                        }
                    });

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
