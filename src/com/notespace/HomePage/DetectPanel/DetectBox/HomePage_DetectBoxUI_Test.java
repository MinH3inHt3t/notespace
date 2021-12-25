package com.notespace.HomePage.DetectPanel.DetectBox;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import product_out.___Bundle;

public class HomePage_DetectBoxUI_Test extends Application
{
    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  HomePage_DetectBox controller = new HomePage_DetectBox ( "D:" );
	  Scene scene = new Scene ( ___Bundle.__ViewLoader._getInstance ( )._load ( "HomePage_DetectBox" , controller ) );
	  primaryStage.setScene ( scene );
	  primaryStage.show ( );

	  Platform.runLater ( new Runnable ( )
	  {
		@Override
		public void run ( )
		{
		    scene.getStylesheets ( ).add ( "http://fonts.googleapis.com/css?family=Indie+Flower" );
		    scene.getStylesheets ( ).add ( "http://fonts.googleapis.com/css?family=Material+Icons" );

		}
	  } );
    }
}
