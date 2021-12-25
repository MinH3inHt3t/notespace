package com.notespace.Minimalist.EditHistory.ViewEditHistory;

import com.notespace.Font.FontRepair;
import com.notespace.Minimalist.Memory.EditHistoryObj;
import com.ocpsoft.pretty.time.PrettyTime;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewEditHistory implements Initializable {

    @FXML
    public TextArea textArea;
    @FXML
    Label prettyTimeLabel;
    @FXML
    Button copyTextButton;

    EditHistoryObj editHistoryObj;

    public ViewEditHistory(EditHistoryObj editHistoryObj) {
        this.editHistoryObj = editHistoryObj;
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
        textArea.setText(FontRepair.fixmyanamrfont(editHistoryObj.getTextData()));

        PrettyTime prettyTime = new PrettyTime();
        String time = prettyTime.format(new Date(editHistoryObj.getEditTime()));
        prettyTimeLabel.setText(time);
    }

    private void perform() {
        copyTextButton.setOnAction(e -> {
            copy(textArea.getText().trim());
            copyTextButton.setText("Copied");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textArea.selectAll();
                }
            });
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
            pauseTransition.setOnFinished(e1 -> {
                        copyTextButton.setText("Copy Text");
                        textArea.deselect();
                    }
            );
            pauseTransition.play();
        });
    }

    public void copy(String clipBoardText) {                  //Clipboard operations
        String text = clipBoardText;

        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        Clipboard systemClipboard = Clipboard.getSystemClipboard();

        systemClipboard.setContent(content);
    }
}
