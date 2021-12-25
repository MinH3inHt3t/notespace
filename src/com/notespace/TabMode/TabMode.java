package com.notespace.TabMode;

import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.NoteSpacePathStorage;
import com.notespace.FileHandler.Sorting;
import com.notespace.FileHandler.TextObject;
import com.notespace.TabMode.Tabs.TabsUI_Test;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class TabMode implements Initializable
{
    @FXML
    AnchorPane tabmodeAnchor;
    @FXML
    TabPane tabpane;
    @FXML
    VBox indexingVbox;
    @FXML
    ProgressBar progressBar;
    String currentNoteSpace;

    ArrayList < TextObject > arrayList = new ArrayList <> ( );

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {
	  this.init ( );
	  new NoteListService ( );

    }

    private void init ( )
    {
	  this.prepare ( );
	  this.perform ( );
    }

    private void prepare ( )
    {

	  visibleComponents ( indexingVbox );
	  progressbarIndicatorProgress ( );
    }

    private void visibleComponents ( Node... nodes )
    {
	  for ( Node node : nodes ) {
		node.setVisible ( true );
	  }
    }

    private void invisibleComponents ( Node... nodes )
    {
	  for ( Node node : nodes ) {
		node.setVisible ( false );
	  }
    }

    private void progressbarIndicatorProgress ( )
    {
	  Platform.runLater ( new Runnable ( )
	  {
		@Override
		public void run ( )
		{
		    Timeline timeline = new Timeline (
				new KeyFrame ( Duration.ZERO , new KeyValue ( progressBar.progressProperty ( ) , 0 ) ) ,
				new KeyFrame ( Duration.seconds ( 5 ) , e -> {
				    // do anything you need here on completion..
				} , new KeyValue ( progressBar.progressProperty ( ) , 1 ) )
		    );
		    timeline.setCycleCount ( 1 );
		    timeline.play ( );
		}
	  } );
    }


    private void perform ( )
    {
	  tabpane.setOnMouseEntered ( e -> {
		new NoteListService ( );
	  } );
    }


    private void bindwithUI ( ArrayList < TextObject > textObjectArrayList )
    {
	  tabpane.getTabs ( ).clear ( );

	  Collections.sort ( textObjectArrayList , new Sorting.LatestModifiedDate ( ) );
	  textObjectArrayList.stream ( ).forEach ( e -> {
		DraggableTab tab = new DraggableTab ( e.getName ( ) );
		tab.setClosable ( false );
		//		Tabs controller = new Tabs ( e.getAbsolutePath ( ) );
		//		Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "Tabs" , controller );

		Parent parent = new TabsUI_Test ( e.getAbsolutePath ( ) ).startUI ( );


		tab.setContent ( parent );
		tabpane.getTabs ( ).add ( tab );
	  } );


    }

    class NoteListService extends Service < ArrayList < TextObject > >
    {

	  public NoteListService ( )
	  {
		restart ( );


		setOnSucceeded ( e -> {
		    if ( arrayList.size ( ) != getValue ( ).size ( ) || ! currentNoteSpace.equals ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath ) ) {

			  currentNoteSpace = NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath;
			  Collections.sort ( arrayList , new Sorting.LatestModifiedDate ( ) );
			  arrayList = getValue ( );
			  bindwithUI ( getValue ( ) );

			  invisibleComponents ( indexingVbox );

		    }

		} );
	  }

	  @Override
	  protected Task < ArrayList < TextObject > > createTask ( )
	  {
		return new Task < ArrayList < TextObject > > ( )
		{
		    @Override
		    protected ArrayList < TextObject > call ( ) throws Exception
		    {

			  return FileHandlings.getTextObjectLists ( );
		    }
		};
	  }
    }
}
