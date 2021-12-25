package com.notespace.EditNoteModal;

import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.TextObject;
import com.notespace.TabMode.Tabs.Tabs;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import product_out.___Bundle;

import java.io.IOException;

public class EditNoteUI_Test extends Application
{
    private static double xOffset = 0;
    private static double yOffset = 0;

    private String absolutePath;


    public EditNoteUI_Test ( )
    {
	  //	  this.absolutePath = "D:\\MyPhotoshop\\33.mp4";
	  this.absolutePath = "D:\\MyPhotoshop\\New Text Document.txt";
    }

    public EditNoteUI_Test ( String absolutePath )
    {
	  this.absolutePath = absolutePath;

    }

    //    public static CardAppearanceUI_Test getInstance ( )
    //    {
    //	  if ( obj == null ) {
    //		synchronized ( CardAppearanceUI_Test.class ) {
    //		    if ( obj == null ) {
    //			  obj = new CardAppearanceUI_Test ( );//instance will be created at request time
    //		    }
    //		}
    //	  }
    //	  return obj;
    //    }


    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    public static void dragFrame ( EditNote controller , Stage stage )
    {
	  Node node[] = { controller.anchor };
	  for (
		    int i = 0 ;
		    i < node.length ;
		    i++
	  ) {
		Node n = node[ i ];
		n.setOnMousePressed ( new EventHandler < MouseEvent > ( )
		{
		    @Override
		    public void handle ( MouseEvent event )
		    {
			  n.setCursor ( Cursor.CLOSED_HAND );
			  xOffset = stage.getX ( ) - event.getScreenX ( );
			  yOffset = stage.getY ( ) - event.getScreenY ( );
		    }
		} );
		n.setOnMouseDragged ( new EventHandler < MouseEvent > ( )
		{
		    @Override
		    public void handle ( MouseEvent event )
		    {
			  n.setCursor ( Cursor.CLOSED_HAND );
			  stage.setX ( event.getScreenX ( ) + xOffset );
			  stage.setY ( event.getScreenY ( ) + yOffset );
		    }
		} );
		n.setOnMouseReleased ( e -> {
		    n.setCursor ( Cursor.HAND );
		} );
	  }
    }

    public Parent startUI ( )
    {
	  //	  CardAppearance controller = new CardAppearance ( "D:\\NoteSpaceHere\\Space2\\Student.java" );

	  Tabs controller = new Tabs ( absolutePath );
	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "Tabs" , controller );


	  return parent;
	  //	  dragFrame ( controller , stage );
    }

    public void startUINewWindow ( Stage stage )
    {
	  Parent parent = null;
	  try {
		TextObject textObject = FileHandlings.buildObject ( absolutePath );
		EditNote controller = new EditNote ( textObject );
		parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "EditNote" , controller );
		controller.closeAddNewNoteLabel.setOnMouseClicked ( e->{
		    if(e.getButton ().equals ( MouseButton.PRIMARY )){
			  stage.close ();
		    }
		} );

		dragFrame ( controller , stage );
	  }
	  catch ( IOException e ) {
		e.printStackTrace ( );
	  }


	  Scene scene = new Scene ( parent );
	  stage.initStyle ( StageStyle.TRANSPARENT );
	  stage.initModality ( Modality.NONE );
	  scene.setFill ( Color.TRANSPARENT );
	  Platform.runLater ( new Runnable ( )
	  {
		@Override
		public void run ( )
		{
		    scene.getStylesheets ( ).add ( "http://fonts.googleapis.com/css?family=Indie+Flower" );
		    scene.getStylesheets ( ).add ( "http://fonts.googleapis.com/css?family=Material+Icons" );

		}
	  } );


	  stage.setScene ( scene );
	  stage.show ( );


    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  startUINewWindow ( new Stage ( ) );
    }
}
