package com.notespace.Minimalist.Logs.LogsContainer;

import com.notespace.Font.FontRepair;
import com.notespace.Minimalist.Logs.REASON;
import com.notespace.Minimalist.Memory.LogsObj;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LogsContainer implements Initializable {


    @FXML
    public Label removeLogsLabel, restoreLabel;
    @FXML
    Label descriLabel, fileNameLabel, folderNameLabel, logsTimeLabel, absolutePathLabel, openingStatusLabel, reasonLabel;
    @FXML
    VBox openingstatusVBox;
    @FXML
    ProgressBar openingStatusProgressBar;
    LogsObj logsObj;
    String absolutePath;

    public LogsContainer(LogsObj logsObj) {
        this.logsObj = logsObj;
        this.absolutePath = logsObj.getFolderName() + "\\" + logsObj.getFileName();
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

//        FileHandlings.getTextObjectLists().stream().forEach(tobj -> {
//            if (tobj.getName().equals(logsObj.getFileName())) {
//                restoreLabel.setDisable(true);
//            } else {
//                restoreLabel.setDisable(false);
//            }
//        });


        restoreLabel.setVisible(false);
        openingstatusVBox.setVisible(false);

        fileNameLabel.setText(FontRepair.fixmyanamrfont(logsObj.getFileName()));
        folderNameLabel.setText(FontRepair.fixmyanamrfont(logsObj.getFolderName()));
        reasonLabel.setText(logsObj.getReason() + "");
        if (logsObj.getReason().equals(REASON.CREATED)) {
            descriLabel.setText("You created");
            reasonLabel.setStyle("-fx-text-fill:  #A7B0F5;");     //blue color
        }
        if (logsObj.getReason().equals(REASON.DELETED)) {
            descriLabel.setText("You deleted");
            reasonLabel.setStyle("-fx-text-fill:   #EA4C89;");      //red color

            restoreLabel.setVisible(true);
        }


        logsTimeLabel.setText(logsObj.getFormattedTime());
        absolutePathLabel.setText(FontRepair.fixmyanamrfont(logsObj.getFolderName() + "\\" + logsObj.getFileName()));
        if (new File(absolutePath).exists() && new File(logsObj.getFolderName()).exists()) {

            Arrays.stream(new Label[]{fileNameLabel, folderNameLabel, absolutePathLabel}).forEach(label -> {
                label.setDisable(false);
            });
        } else {
            Arrays.stream(new Label[]{fileNameLabel, folderNameLabel, absolutePathLabel}).forEach(label -> {
                label.setDisable(true);
            });
        }


    }//prepare

    private void perform() {

//        Backup backup = BackupStorage.getLogsObjWithFileName(logsObj.getFileName());
//        String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + backup.getBackupFileName();
//        restoreLabel.setTooltip(new Tooltip("File Backup as " + filepath));

        folderNameLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                openingstatusVBox.setVisible(true);
                openingStatusLabel.setText("opening... " + logsObj.getFolderName());
                openingStatusProgressBar.setProgress(1);
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
                pauseTransition.setOnFinished(event -> {
                    openingStatusLabel.setVisible(false);
                    openingStatusProgressBar.setProgress(0);
                });

                try {
                    Desktop.getDesktop().open(new File(logsObj.getFolderName()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        Arrays.stream(new Label[]{fileNameLabel, absolutePathLabel}).forEach(label -> {
            label.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    openingstatusVBox.setVisible(true);
                    openingStatusLabel.setText("opening... " + logsObj.getFileName());
                    openingStatusProgressBar.setProgress(1);
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
                    pauseTransition.setOnFinished(event -> {
                        openingStatusLabel.setVisible(false);
                        openingStatusProgressBar.setProgress(0);
                        try {
                            Desktop.getDesktop().open(new File(absolutePath));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                    pauseTransition.play();

                }
            });
        });
    }
}
