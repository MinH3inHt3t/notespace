package com.notespace.Minimalist.DirectoryChoose;

import com.notespace.FileHandler.NoteSpacePathStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MinimalistPageDirectoryChooseBox implements Initializable {

    @FXML
    public HBox dirChooseBoxHBox;
    @FXML
    Label directoryPathLabel, currentFolderLabel;
    String dirPath;

    public MinimalistPageDirectoryChooseBox(String dirPath) {
        this.dirPath = dirPath;
    }

    public String returnDirPath() {
        return this.dirPath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        prepare();
        perform();

        refresh();
    }

    private void prepare() {
        directoryPathLabel.setText(this.dirPath);
//        currentFolderLabel.setVisible(false);

    }

    public void refresh() {
        String getDirPath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
        if (getDirPath.equals(dirPath)) {
//            currentFolderLabel.setVisible(true);
            currentFolderLabel.setText("Current Folder");
        } else {
//            currentFolderLabel.setVisible(false);
            currentFolderLabel.setText("");
        }
    }

    private void perform() {

    }

}
