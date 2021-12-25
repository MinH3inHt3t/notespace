package com.notespace.Welcome;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import product_out.___Bundle;

public class welcomeUI_Test extends Application
{
    public static void main ( String[] args )
    {
	  Application.launch ( args );
	  System.out.println ( "Programming" );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "welcome" , new welcome ( ) );
	  Scene scene = new Scene ( parent );
	  primaryStage.setTitle ( getClass ( ).getName ( ) );
	  primaryStage.setMaximized ( true );
	  //	  primaryStage.initStyle ( StageStyle.UNDECORATED );
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
