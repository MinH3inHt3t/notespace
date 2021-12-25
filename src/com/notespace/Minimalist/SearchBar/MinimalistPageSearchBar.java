package com.notespace.Minimalist.SearchBar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MinimalistPageSearchBar implements Initializable
{
    @FXML
    VBox searchVBox;
    @FXML
    public TextField searchTextField;
    @FXML
    public Button  newNoteButton,nextNoteButton,previousNoteButton,floatModeButton,dockModeButton,windowModeButton,closeSearchBoxButton;


    public MinimalistPageSearchBar ( )
    {

    }

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {

    }
}
