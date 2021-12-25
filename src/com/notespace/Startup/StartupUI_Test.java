package com.notespace.Startup;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import product_out.___Bundle;

public class StartupUI_Test extends Application
{
    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  Startup controller = new Startup ( );
	  Scene scene = new Scene ( ___Bundle.__ViewLoader._getInstance ( )._load ( "Startup" , controller ) );
	  primaryStage.setScene ( scene );
	  //	  primaryStage.initStyle ( StageStyle. );
	  primaryStage.setWidth ( 1290 );
	  primaryStage.setHeight ( 750 );
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
