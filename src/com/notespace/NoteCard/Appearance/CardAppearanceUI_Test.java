package com.notespace.NoteCard.Appearance;

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

public class CardAppearanceUI_Test extends Application
{
    private static double xOffset = 0;
    private static double yOffset = 0;

    private static CardAppearanceUI_Test obj;

    public CardAppearanceUI_Test ( )
    {

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

    public static void dragFrame ( CardAppearance controller , Stage stage )
    {
	  Node node[] = { controller.cardAppearanceAnchor };
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
	  CardAppearance controller = new CardAppearance ( "D:\\NoteSpaceHere\\Space2\\Student.java" );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "CardAppearance" , controller );

	  controller.closeNoteAppearanceLabel.setVisible ( false );

	  return parent;
	  //	  dragFrame ( controller , stage );
    }

    public void startUINewWindow ( Stage stage )
    {
	  CardAppearance controller = new CardAppearance ( "D:\\NoteSpaceHere\\Space2\\Student.java" );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "CardAppearance" , controller );
	  Scene scene = new Scene ( parent );
	  stage.initStyle ( StageStyle.TRANSPARENT );
	  stage.initModality ( Modality.APPLICATION_MODAL );
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

	  controller.closeNoteAppearanceLabel.setVisible ( true );
	  controller.closeNoteAppearanceLabel.setOnMouseClicked ( new EventHandler < MouseEvent > ( )
	  {
		@Override
		public void handle ( MouseEvent event )
		{
		    if ( event.getButton ( ) == MouseButton.PRIMARY ) {
			  stage.close ( );
		    }
		}
	  } );

	  stage.setScene ( scene );
	  stage.show ( );

	  dragFrame ( controller , stage );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  startUINewWindow ( new Stage ( ) );
    }
}
