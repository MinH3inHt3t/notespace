package com.notespace.Minimalist.Settings;

import com.notespace.Minimalist.ShortcutDialog.ControlShortcutDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import product_out.___Bundle;

import java.net.URL;
import java.util.ResourceBundle;

public class MinimalistPageSettings implements Initializable {

    @FXML
    public Label windowModeIconLabel;
    @FXML
    public Button contentContainerDockButton, contentContainerFloatButton, contentContainerWindowButton, contentContainerHideShowButton;
    @FXML
    public Button searchBarDockButton, searchBarFloatButton, searchBarWindowButton, searchBarHideShowButton;
    @FXML
    public Button restoreDefaultLayoutButton, contentContainerDefaultPositionButton, searchBarDefaultPositionButton;
    @FXML
    public HBox boxContentHBox, flatContentHBox, boxContentWithFileNameHBox, boxContentWithFileNameOptionsHBox;
    @FXML
    public Slider columnSlider;
    @FXML
    VBox settingsSectionVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.init();
    }

    private void init() {
        prepare();
        perform();
    }

    private void prepare() {
        Shortcuts_Section shortcuts_section = new Shortcuts_Section();
        Parent parent = ___Bundle.__ViewLoader._getInstance()._load("Shortcuts_Section", shortcuts_section);
        settingsSectionVBox.getChildren().add(parent);

        ControlShortcutDialog controlShortcutDialog = new ControlShortcutDialog();
        Parent parent1 = ___Bundle.__ViewLoader._getInstance()._load("ControlShortcutDialog", controlShortcutDialog);
        settingsSectionVBox.getChildren().add(parent1);
        controlShortcutDialog.shortcutBorderPane.setTop(null);


    }

    private void perform() {

    }

}
