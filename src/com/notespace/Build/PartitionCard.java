package com.notespace.Build;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PartitionCard implements Initializable
{
    @FXML
    AnchorPane partitionAnchor;
    @FXML
    private Label partitionLabel;

    private String partitionString;

    public PartitionCard ( String partitionString )
    {
	  this.partitionString = partitionString;
    }

    public String throwPartitionString ( )
    {
	  return this.partitionString;
    }

    private void UI_Actions ( )
    {
	  BindWithUI ( );
    }

    private void BindWithUI ( )
    {
	  partitionLabel.setText ( this.partitionString );
    }

    private void init ( )
    {
	  UI_Actions ( );
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {
	  init ( );
    }
}
