package com.notespace.Minimalist.Backup;

import com.notespace.Font.FontRepair;
import com.notespace.Minimalist.Memory.Backup;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class BackupContainer implements Initializable {

    @FXML
    public Label fileNameLabel, deletedTimeLabel, restoreLabel, copyTextLabel,deleteBackupLabel;
    @FXML
    TextArea textArea;

    Backup backup;

    public BackupContainer(Backup backup) {
        this.backup = backup;
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
        fileNameLabel.setText(FontRepair.fixmyanamrfont(backup.getFileName()));
        deletedTimeLabel.setText(backup.getFormattedTime());
        textArea.setText(FontRepair.fixmyanamrfont(backup.getTextData()));
    }       //Prepare

    private void perform() {


        restoreLabel.setOnMouseEntered(e -> {
            restoreLabel.setText(FontRepair.fixmyanamrfont("RESTORE as ' " + backup.getBackupFileName() + " ' ".trim()));
        });
        restoreLabel.setOnMouseExited(e -> {
            restoreLabel.setText("RESTORE");
        });
        copyTextLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {

                copy(textArea.getText().trim());
                copyTextLabel.setText("Copied");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.selectAll();
                    }
                });
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                pauseTransition.setOnFinished(e1 -> {
                            copyTextLabel.setText("COPY TEXT");
                            textArea.deselect();
                        }
                );
                pauseTransition.play();

            }
        });


    }   //Perform

    public void copy(String clipBoardText) {                  //Clipboard operations
        String text = clipBoardText;

        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        Clipboard systemClipboard = Clipboard.getSystemClipboard();

        systemClipboard.setContent(content);
    }
}
