package com.notespace.Page1.TabMode;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import product_out.___Bundle;

public class Page1TabUI_Test extends Application
{
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    public Parent startUI ( )
    {
	  return ___Bundle.__ViewLoader._getInstance ( )._load ( "Page1Tab" , new Page1Tab ( ) );
    }

    public void dragFrame ( Page1Tab controller , Stage stage )
    {
	  Node node[] = { controller.cardModeAnchor };
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

    public void startUINewWindow ( Stage stage )
    {
	  Page1Tab controller = new Page1Tab ( );


	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "Page1Tab" , controller );
	  Scene scene = new Scene ( parent );
	  stage.initStyle ( StageStyle.DECORATED );
	  stage.initModality ( Modality.APPLICATION_MODAL );
	  //	  scene.setFill ( Color.TRANSPARENT );
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

	  //	  dragFrame ( controller , stage );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  startUINewWindow ( new Stage ( ) );
    }
}
