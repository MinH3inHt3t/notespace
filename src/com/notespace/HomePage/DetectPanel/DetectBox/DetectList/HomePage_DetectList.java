package com.notespace.HomePage.DetectPanel.DetectBox.DetectList;

import com.notespace.FileHandler.NoteSpacePath;
import com.notespace.FileHandler.NoteSpacePathStorage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class HomePage_DetectList implements Initializable
{
    @FXML
    AnchorPane DetectListAnchor;
    @FXML
    Label notespacepathLabel, inuseLabel;

    String noteSpacePathName;      //
    String tempString = "";

    private Service < String > NoteSpacePath_Service = new Service ( )
    {
	  @Override
	  protected Task < String > createTask ( )
	  {
		return new Task < String > ( )
		{
		    @Override
		    protected String call ( ) throws Exception
		    {
			  return NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath;
		    }
		};
	  }
    };

    public HomePage_DetectList ( String noteSpacePathName )      //Constructor
    {
	  this.noteSpacePathName = noteSpacePathName;
    }

    private void ServicesRepo ( )
    {
	  this.setNoteSpacePath_Service ( );
    }

    private void setNoteSpacePath_Service ( )
    {
	  this.NoteSpacePath_Service.setOnSucceeded ( e -> {

		if ( this.noteSpacePathName.equals ( NoteSpacePath_Service.getValue ( ) ) ) {
		    this.inuseLabel.setVisible ( true );
		}
		else {
		    this.inuseLabel.setVisible ( false );
		}
		//		if ( ! this.tempString.equals ( NoteSpacePath_ScheduleService.getValue ( ) ) ) {
		//		    System.out.println ( "Add func" );
		//		    this.inuseLabel.setVisible ( true );
		//		    this.tempString = NoteSpacePath_ScheduleService.getValue ( );
		//		}
		//		else {
		//		    this.inuseLabel.setVisible ( false );
		//		    System.out.println ( "Dont add" );
		//		}
	  } );

    }

    private void StartServices ( )
    {
	  this.StartSpacePathService ( );
    }

    private void StartSpacePathService ( )
    {
	  this.NoteSpacePath_Service.restart ( );
    }

    private void CancelServices ( )
    {
	  this.CancelSpacePathService ( );
    }

    private void CancelSpacePathService ( )
    {
	  this.NoteSpacePath_Service.cancel ( );
    }

    private void UI_Perform ( )            // include UI Listeners
    {
	  this.DetectListAnchor.setOnMouseEntered ( e -> {
		this.StartServices ( );
	  } );
	  this.DetectListAnchor.setOnMouseExited ( e -> {
		this.StartServices ( );
	  } );

	  this.DetectListAnchor.setOnMouseClicked ( e -> {
		this.inuseLabel.setVisible ( true );
		NoteSpacePathStorage.SaveNoteSpacePath ( new NoteSpacePath ( UUID.randomUUID ( ).toString ( ) , this.noteSpacePathName ) );
	  } );
    }

    private void UI_Binding ( )
    {
	  this.notespacepathLabel.setText ( this.noteSpacePathName );
	  //
	  //	  if ( this.noteSpacePathName.equals ( NoteSpacePathStorage.GetNoteSpacePath ( ).spacePath ) ) {      //inuse label visible
	  //		this.inuseLabel.setVisible ( true );
	  //	  }
	  //	  else {
	  //		this.inuseLabel.setVisible ( false );
	  //	  }
    }

    private void UI_Prepare ( )      // include UI before initialize visible not visible etc.
    {

    }

    private void init ( )      // call req methods here.
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
