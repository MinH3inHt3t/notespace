package com.notespace.Detect.DetectBox;

import com.notespace.Detect.DetectBox.ListBox.ListBox;
import com.notespace.FileHandler.BuildNoteSpace;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import product_out.___Bundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DetectBox implements Initializable
{

    @FXML
    VBox contentVBox, loadingVBox;
    @FXML
    Label partitionLabel, countLabel, loadingLabel;
    @FXML
    AnchorPane detectBoxAnchor;
    @FXML
    ProgressBar progressbar;
    ArrayList < String > templist = new ArrayList <> ( );      //0
    private String partitionName;
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
			  return BuildNoteSpace.getDetectedNoteSpacePath ( partitionName );
		    }
		};
	  }
    };


    public DetectBox ( String partitionName )
    {
	  this.partitionName = partitionName;
    }

    private void ServicesRepo ( )
    {
	  this.setPartitionService ( );
    }

    private void setPartitionService ( )
    {


	  this.PartitionService.setOnSucceeded ( e -> {
		//		if ( PartitionService.getValue ( ).size ( ) != 0 ) {
		//
		//		    contentVBox.getChildren ( ).clear ( );
		//		    countLabel.setText ( this.PartitionService.getValue ( ).size ( ) + " " + "found" );
		//		    for ( String s : this.PartitionService.getValue ( ) ) {
		//			  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "ListBox" , new ListBox ( s ) );
		//			  contentVBox.getChildren ( ).add ( parent );
		//		    }
		//		}
		//		else {
		//
		//		}

		if ( this.templist.size ( ) != PartitionService.getValue ( ).size ( ) ) {
		    System.out.println ( "Add func" );

		    this.loadingVBox.setVisible ( true );
		    this.progressbar.setProgress ( ProgressIndicator.INDETERMINATE_PROGRESS );
		    this.AddDataToVBox ( PartitionService.getValue ( ) );
		    this.loadingVBox.setVisible ( false );
		    this.templist = PartitionService.getValue ( );

		}
		else {


		    this.loadingLabel.setText ( "No Result" );
		    //		    this.loadingVBox.setVisible ( false );
		    this.progressbar.setProgress ( 0 );
		    //		    AddDataToVBox ( Partitions_ScheduleService.getValue () );
		    System.out.println ( "Dont add" );
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
	  this.detectBoxAnchor.setOnMouseEntered ( e -> {
		this.StartServices ( );
	  } );
	  this.detectBoxAnchor.setOnMouseExited ( e -> {
		this.StartServices ( );
	  } );
    }

    private void UI_Binding ( )
    {
	  this.partitionLabel.setText ( this.partitionName );
    }

    private void AddDataToVBox ( ArrayList < String > arrayList )
    {
	  this.countLabel.setText ( arrayList.size ( ) + " found" );
	  this.contentVBox.getChildren ( ).clear ( );
	  for ( String s : arrayList ) {
		ListBox controller = new ListBox ( s );
		Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "ListBox" , controller );

		this.contentVBox.getChildren ( ).add ( parent );
	  }
    }


    private void UI_Prepare ( )
    {
	  this.progressbar.setProgress ( ProgressIndicator.INDETERMINATE_PROGRESS );
	  this.contentVBox.getChildren ( ).clear ( ); //clear contentVbox first

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
