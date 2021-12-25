package com.notespace.Build;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import product_out.___Bundle;

public class DirectoryChooseUI_Test extends Application
{
    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  Scene scene = new Scene ( ___Bundle.__ViewLoader._getInstance ( )._load ( "DirectoryChoose" , new DirectoryChoose ( ) ) );
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
