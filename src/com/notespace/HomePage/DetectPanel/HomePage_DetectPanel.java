package com.notespace.HomePage.DetectPanel;

import com.notespace.FileHandler.BuildNoteSpace;
import com.notespace.HomePage.DetectPanel.DetectBox.HomePage_DetectBox;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import product_out.___Bundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePage_DetectPanel implements Initializable
{
    @FXML
    public Label closeDetectPanel;
    @FXML
    AnchorPane detectAnchor;
    @FXML
    VBox dataVBox;
    HomePage_DetectBox controller;

    private Service < ArrayList < String > > GetPartitionsService = new Service ( )
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

    public HomePage_DetectPanel ( )
    {

    }

    private void ServicesRepo ( )
    {
	  this.setService_GetPartitions ( );
    }

    private void setService_GetPartitions ( )
    {
	  this.GetPartitionsService.setOnSucceeded ( e -> {
		if ( this.dataVBox.getChildren ( ).size ( ) != GetPartitionsService.getValue ( ).size ( ) ) {
		    dataVBox.getChildren ( ).clear ( );
		    for ( String st : GetPartitionsService.getValue ( ) ) {
			  controller = new HomePage_DetectBox ( st );
			  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "HomePage_DetectBox" , controller );
			  dataVBox.getChildren ( ).add ( parent );
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
	  this.GetPartitionsService.restart ( );
    }

    private void CancelServices ( )
    {
	  this.CancelPartitionService ( );
    }

    private void CancelPartitionService ( )
    {
	  this.GetPartitionsService.cancel ( );

    }

    private void UI_Perform ( )
    {
	  this.detectAnchor.setOnMouseEntered ( e -> {
		this.StartServices ( );
	  } );
	  this.detectAnchor.setOnMouseExited ( e -> {
		this.StartServices ( );
	  } );
	  //	  this.closeDetectPanel.setOnMouseClicked ( e -> {
	  //		this.CancelServices ( );
	  //		this.controller.CancelServices ( );
	  //	  } );
    }

    private void UI_Binding ( )
    {

    }

    private void UI_Prepare ( )
    {
	  this.dataVBox.getChildren ( ).clear ( );
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
