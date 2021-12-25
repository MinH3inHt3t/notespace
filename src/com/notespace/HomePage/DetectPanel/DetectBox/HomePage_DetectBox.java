package com.notespace.HomePage.DetectPanel.DetectBox;

import com.notespace.FileHandler.BuildNoteSpace;
import com.notespace.HomePage.DetectPanel.DetectBox.DetectList.HomePage_DetectList;
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

public class HomePage_DetectBox implements Initializable
{

    @FXML
    AnchorPane detectBoxAnchor;
    @FXML
    VBox contentVBox, loadingVBox;
    @FXML
    ProgressBar progressbar;
    @FXML
    Label partitionLabel, cancelScheduleServiceLabel, loadingLabel;
    String partitionName;

    ArrayList < String > templist = new ArrayList <> ( );      //0

    private Service < ArrayList < String > > Partitions_Service = new Service ( )
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

    public HomePage_DetectBox ( String partitionName )
    {
	  this.partitionName = partitionName;
    }

    private void ServicesRepo ( )
    {
	  this.setPartitions_Service ( );
    }

    private void setPartitions_Service ( )
    {
	  this.Partitions_Service.setOnSucceeded ( e -> {

		System.out.println ( "templist " + this.templist.size ( ) );
		System.out.println ( "Servicelist " + Partitions_Service.getValue ( ).size ( ) );

		if ( this.templist.size ( ) != Partitions_Service.getValue ( ).size ( ) ) {
		    System.out.println ( "Add func" );

		    this.loadingVBox.setVisible ( true );
		    this.progressbar.setProgress ( ProgressIndicator.INDETERMINATE_PROGRESS );
		    this.AddDataToVBox ( Partitions_Service.getValue ( ) );
		    this.loadingVBox.setVisible ( false );
		    this.templist = Partitions_Service.getValue ( );

		}
		else {


		    this.loadingLabel.setText ( "No Result" );
		    //		    this.loadingVBox.setVisible ( false );
		    this.progressbar.setProgress ( 0 );
		    //		    AddDataToVBox ( Partitions_ScheduleService.getValue () );
		    System.out.println ( "Dont add" );
		}

		//		if ( this.templist.size ( ) != Partitions_ScheduleService.getValue ( ).size ( ) ) {	//0!=3
		//		    AddDataToVBox ( Partitions_ScheduleService.getValue ( ) );
		//		    this.loadingVBox.setVisible ( false );
		//		    System.out.println ( "Add" );
		//		}
		//		else {
		//
		//		    System.out.println ( "Dont add" );
		//		    this.loadingVBox.setVisible ( false );
		//		}

		//		else {
		//		    this.loadingVBox.setVisible ( true );
		//		    this.loadingLabel.setText ( "No Result" );
		//		    this.progressbar.setProgress ( 0 );
		//		}

		//				AddDataToVBox ( Partitions_ScheduleService.getValue () );
		//				this.loadingVBox.setVisible ( false );


	  } );
	  this.Partitions_Service.setOnRunning ( e -> {
		//		System.out.println ( "Detect Panel" + this.Partitions_ScheduleService.getState ( ) );
	  } );
	  this.Partitions_Service.setOnCancelled ( e -> {
		this.Partitions_Service.cancel ( );
		//		System.out.println ( "Detect Panel" + this.Partitions_ScheduleService.getState ( ) );
	  } );


    }


    private void StartServices ( )      //Start Service through method
    {
	  this.StartPartitionService ( );
    }

    private void StartPartitionService ( )
    {
	  this.Partitions_Service.restart ( );
    }


    private void CancelServices ( ) //Cancel Service through method
    {
	  this.CancelPartitionService ( );
    }

    private void CancelPartitionService ( )
    {
	  this.Partitions_Service.cancel ( );
    }


    private void UI_Perform ( )
    {
	  this.detectBoxAnchor.setOnMouseEntered ( e -> {
		this.StartServices ( );
	  } );
	  this.detectBoxAnchor.setOnMouseExited ( e -> {
		this.StartServices ( );
	  } );

	  this.cancelScheduleServiceLabel.setOnMouseClicked ( e -> {
		this.CancelServices ( );
	  } );
    }

    private void UI_Binding ( )
    {
	  this.partitionLabel.setText ( this.partitionName );
    }

    private void AddDataToVBox ( ArrayList < String > arrayList )
    {
	  this.contentVBox.getChildren ( ).clear ( );
	  for ( String s : arrayList ) {
		HomePage_DetectList controller = new HomePage_DetectList ( s );
		Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "HomePage_DetectList" , controller );

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
