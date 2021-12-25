package com.notespace.Detect;

import com.notespace.Detect.DetectBox.DetectBox;
import com.notespace.FileHandler.BuildNoteSpace;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import product_out.___Bundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AutoDetect implements Initializable
{
    @FXML
    public Label closeDetectPane;
    @FXML
    AnchorPane autoDetectAnchor;
    @FXML
    HBox contentHbox;

    DetectBox controller;

    private Service < ArrayList < String > > PartitionService = new Service ( )
    {
	  @Override
	  protected Task < ArrayList < String > > createTask ( )
	  {
		return new Task < ArrayList < String > > ( )
		{
		    @Override
		    protected ArrayList < String > call ( ) throws Exception
		    {
			  return BuildNoteSpace.getPartitionsList ( );
		    }
		};
	  }
    };

    public AutoDetect ( )
    {

    }

    private void ServicesRepo ( )
    {
	  this.setPartitionService ( );
    }

    private void setPartitionService ( )
    {
	  this.PartitionService.setOnSucceeded ( e -> {
		if ( this.contentHbox.getChildren ( ).size ( ) != this.PartitionService.getValue ( ).size ( ) ) {
		    contentHbox.getChildren ( ).clear ( );
		    for ( String s : PartitionService.getValue ( ) ) {
			  System.out.println ( s );
			  controller = new DetectBox ( s );
			  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "DetectBox" , controller );
			  contentHbox.getChildren ( ).add ( parent );


		    }
		}
	  } );
    }

    private void StartServices ( )
    {
	  this.StartPartitionService ( );
    }

    private void StartPartitionService ( )
    {
	  this.PartitionService.restart ( );
    }

    private void CancelServices ( )
    {
	  this.CancelPartitionService ( );
    }

    private void CancelPartitionService ( )
    {
	  this.PartitionService.cancel ( );
    }

    private void UI_Perform ( )
    {
	  this.autoDetectAnchor.setOnMouseEntered ( e -> {
		this.StartServices ( );
	  } );
	  this.autoDetectAnchor.setOnMouseExited ( e -> {
		this.StartServices ( );
	  } );
    }

    private void UI_Binding ( )
    {

    }

    private void UI_Prepare ( )
    {

    }

    private void init ( )
    {
	  this.ServicesRepo ( );
	  this.UI_Perform ( );
	  this.UI_Binding ( );
	  this.UI_Prepare ( );
	  this.StartServices ( );
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {
	  this.init ( );
    }
}
