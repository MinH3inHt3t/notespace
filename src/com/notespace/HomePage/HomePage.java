package com.notespace.HomePage;

import com.notespace.FileHandler.BuildNoteSpace;
import com.notespace.HomePage.DetectPanel.HomePage_DetectPanel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import product_out.___Bundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePage implements Initializable
{
    @FXML
    AnchorPane baseAnchor, rightAnchor;
    @FXML
    Label helpLabel;
    @FXML
    VBox contentVBox;
    @FXML
    HBox partitionHBox;

    private ScheduledService < ArrayList < String > > Service_PartitionsList = new ScheduledService ( )
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

    public HomePage ( )
    {

    }

    private void ServicesRepo ( )
    {
	  this.setService_PartitionsList ( );
    }

    private void setService_PartitionsList ( )
    {
	  this.Service_PartitionsList.setRestartOnFailure ( true );
	  this.Service_PartitionsList.setPeriod ( Duration.seconds ( 1 ) );
	  this.Service_PartitionsList.setOnSucceeded ( e -> {
		this.partitionHBox.getChildren ( ).clear ( );
		for ( String st : Service_PartitionsList.getValue ( ) ) {
		    Label label = new Label ( st );
		    label.setPrefSize ( 48 , 26 );
		    label.setAlignment ( Pos.CENTER );
		    label.setStyle ( "-fx-background-color:  #C08DFA;" + "-fx-text-fill:white;" + "-fx-border-radius: 30;" + "-fx-background-radius: 30;" + "-fx-font-size: 17;" + "-fx-font-weight: bold;" );
		    this.partitionHBox.getChildren ( ).add ( label );
		}
	  } );
    }

    private void StartServices ( )
    {
	  this.StartPartitionService ( );
    }

    private void StartPartitionService ( )
    {
	  this.Service_PartitionsList.restart ( );
    }

    private void CancelServices ( )
    {
	  this.CancelPartitionService ( );
    }

    private void CancelPartitionService ( )
    {
	  this.Service_PartitionsList.cancel ( );
    }

    private void UI_Perform ( )
    {
	  this.helpLabel.setOnMouseClicked ( e -> {

		rightAnchor.setVisible ( true );
		final Timeline timeline = new Timeline ( );            //reduce size of textarea
		timeline.setCycleCount ( 1 );
		timeline.setAutoReverse ( false );
		timeline.getKeyFrames ( ).add ( new KeyFrame ( Duration.millis ( 1000 ) ,
			  new KeyValue ( rightAnchor.prefWidthProperty ( ) , 300 ) ) );
		timeline.play ( );

		HomePage_DetectPanel controller = new HomePage_DetectPanel ( );
		AnchorPane anchorPane = ( AnchorPane ) ___Bundle.__ViewLoader._getInstance ( )._load ( "HomePage_DetectPanel" , controller );
		AnchorPane.setTopAnchor ( anchorPane , 0.0D );
		AnchorPane.setBottomAnchor ( anchorPane , 0.0D );
		AnchorPane.setLeftAnchor ( anchorPane , 0.0D );
		AnchorPane.setRightAnchor ( anchorPane , 0.0D );
		rightAnchor.getChildren ( ).add ( anchorPane );

		controller.closeDetectPanel.setOnMouseClicked ( e1 -> {

		    this.rightAnchor.setPrefWidth ( 0 );
		    final Timeline timeline1 = new Timeline ( );            //reduce size of textarea
		    timeline1.setCycleCount ( 1 );
		    timeline1.setAutoReverse ( false );
		    timeline1.getKeyFrames ( ).add ( new KeyFrame ( Duration.millis ( 1000 ) ,
				new KeyValue ( rightAnchor.prefWidthProperty ( ) , 0 ) ) );
		    timeline1.play ( );
		    this.rightAnchor.setVisible ( false );


		} );

	  } );
    }

    private void UI_Binding ( )
    {

    }

    private void UI_Prepare ( )
    {
	  this.partitionHBox.getChildren ( ).clear ( );

	  this.rightAnchor.setPrefWidth ( 0 );
	  final Timeline timeline = new Timeline ( );            //reduce size of textarea
	  timeline.setCycleCount ( 1 );
	  timeline.setAutoReverse ( false );
	  timeline.getKeyFrames ( ).add ( new KeyFrame ( Duration.millis ( 1000 ) ,
		    new KeyValue ( rightAnchor.prefWidthProperty ( ) , 0 ) ) );
	  timeline.play ( );
	  this.rightAnchor.setVisible ( false );
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
