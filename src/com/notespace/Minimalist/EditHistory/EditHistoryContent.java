package com.notespace.Minimalist.EditHistory;

import com.notespace.Font.FontRepair;
import com.notespace.Minimalist.EditHistory.ViewEditHistory.ViewEditHistory;
import com.notespace.Minimalist.Memory.EditHistoryObj;
import com.ocpsoft.pretty.time.PrettyTime;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import product_out.___Bundle;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class EditHistoryContent implements Initializable {


    @FXML
    public Label textDataLabel, prettyTimeLabel,viewLabel;

    EditHistoryObj editHistoryObj;

    public EditHistoryContent(EditHistoryObj editHistoryObj) {
        this.editHistoryObj = editHistoryObj;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.init();
    }

    private void init() {
        prepare();
        perform();
    }

    private void prepare() {
        textDataLabel.setText(FontRepair.fixmyanamrfont(editHistoryObj.getTextData()));

        PrettyTime prettyTime = new PrettyTime();
        String time = prettyTime.format(new Date(editHistoryObj.getEditTime()));
        prettyTimeLabel.setText(time);
    }

    private void perform() {
        viewLabel.setOnMouseClicked(e->{
            if(e.getButton().equals(MouseButton.PRIMARY)){

                ViewEditHistory controller = new ViewEditHistory(editHistoryObj);
                Parent parent = ___Bundle.__ViewLoader._getInstance()._load("ViewEditHistory",controller);

                Stage stage = new Stage();
                stage.setTitle("Edit History Details");
                stage.setScene(new Scene(parent));
                stage.show();

            }
        });
    }

}
