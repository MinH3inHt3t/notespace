package com.notespace.Minimalist.MatchCase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MinimalistPageMatchCaseHighlight implements Initializable
{

    @FXML
    public AnchorPane highlightAnchorPane;

    @FXML
    public TextField matchCaseTextField;
    @FXML
    public Label matchCaseWordOptLabel, matchCaseCharOptLabel, findMatchCaseCloseLabel, floatModeIconLabel, windowModeIconLabel,noOfFoundLabel,noGrammarLabel;
    @FXML
    public FlowPane matchCaseFlowPane;

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {

    }
}
