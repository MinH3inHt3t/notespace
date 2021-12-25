package com.notespace.Minimalist.FileNameDialog;

import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.NoteSpacePathStorage;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class FileNameDialog implements Initializable {
    @FXML
    public Button OKButton, CancelButton;
    @FXML
    public TextField fileNameTextField;
    @FXML
    Label IconLabel, fileNameLabel;
    @FXML
    CheckBox randomCheckbox;

    String tempFileName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileNameTextField.getStyleClass().add("borderBlue");

        fileNameTextField.textProperty().addListener((obs, old, niu) -> {
            String fileName = fileNameTextField.getText().trim();
            String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + fileName + ".txt";
            boolean exist = FileHandlings.checkFileExist(filepath);
            if (exist) {
                makeDuplicateFileNameWarning();
            } else {
                makeOriginalUI();
            }
        });

        randomCheckbox.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    if (new_val) {

                        if (!fileNameTextField.getText().isEmpty()) {
                            tempFileName = fileNameTextField.getText().trim();
                        }
                        String randomName = UUID.randomUUID().toString().substring(0, 17);
                        fileNameTextField.setText(randomName);
                    } else {
                        fileNameTextField.clear();
                        if (tempFileName != null) {
                            fileNameTextField.setText(tempFileName);
                        }

                    }
                    Platform.runLater(new Runnable() {      //focus
                        @Override
                        public void run() {
                            fileNameTextField.requestFocus();
                        }
                    });
                });

    }

    public void makeOriginalUI() {
        fileNameLabel.setText("Enter file name");

        fileNameTextField.getStyleClass().remove("borderRed");
        fileNameTextField.getStyleClass().add("borderBlue");

        IconLabel.setText("\ue873");
        IconLabel.getStyleClass().remove("iconRed");
        IconLabel.getStyleClass().add("iconBlue");

        OKButton.setDisable(false);
    }

    public void makeDuplicateFileNameWarning() {
        fileNameLabel.setText("Duplicate File Name");
        fileNameTextField.getStyleClass().add("borderRed");

        IconLabel.setText("\ue000");
        IconLabel.getStyleClass().remove("iconBlue");
        IconLabel.getStyleClass().add("iconRed");

        OKButton.setDisable(true);
    }

    public void makeGiveFileNameNoti() {
        fileNameLabel.setText("Please give file name!");
        fileNameTextField.getStyleClass().add("borderRed");

        IconLabel.setText("\ue000");
        IconLabel.getStyleClass().remove("iconBlue");
        IconLabel.getStyleClass().add("iconRed");

        OKButton.setDisable(true);
    }
}
