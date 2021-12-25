package com.notespace.Minimalist.MatchCase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MinimalistPageLineCase implements Initializable {

    @FXML
    public VBox lineObjReceiveVBox,lineNumberShowVBox;
    @FXML
    public AnchorPane lineCaseAnchorPane;
    @FXML
    public Label floatModeIconLabel, windowModeIconLabel, findMatchCaseCloseLabel;
    @FXML
    public TextField lineCaseTextField;
    @FXML
    public Label noOfFoundLabel,noGrammarLabel;

    public MinimalistPageLineCase() {

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
}
