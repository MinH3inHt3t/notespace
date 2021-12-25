package com.notespace.Detect.DetectBox.ListBox;

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

public class ListBox implements Initializable
{

    @FXML
    AnchorPane listboxAnchor;
    @FXML
    Label spaceLabel, inuseLabel;


    String spacePath;

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


    public ListBox ( String spacePath )
    {
	  this.spacePath = spacePath;
    }

    private void ServicesRepo ( )
    {
        this.setNoteSpacePath_Service ();
    }

    private void setNoteSpacePath_Service ( )
    {
	  this.NoteSpacePath_Service.setOnSucceeded ( e -> {

		if ( this.spacePath.equals ( NoteSpacePath_Service.getValue ( ) ) ) {
		    this.inuseLabel.setVisible ( true );
		}
		else {
		    this.inuseLabel.setVisible ( false );
		}

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

    private void UI_Perform ( )
    {
	  this.listboxAnchor.setOnMouseEntered ( e -> {
		this.StartServices ( );
	  } );
	  this.listboxAnchor.setOnMouseExited ( e -> {
		this.StartServices ( );
	  } );
	  this.listboxAnchor.setOnMouseClicked ( e -> {
		this.inuseLabel.setVisible ( true );
		NoteSpacePathStorage.SaveNoteSpacePath ( new NoteSpacePath ( UUID.randomUUID ( ).toString ( ) , this.spacePath ) );
	  } );
    }

    private void UI_Binding ( )
    {
	  this.spaceLabel.setText ( this.spacePath );
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
