package com.notespace.ScheduleTest.TestingProcessHelper.DragAndDrop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application
{

    public static void main ( String[] args )
    {
	  launch ( args );
    }

    @Override
    public void start ( Stage primaryStage )
    {
	  BorderPane root = new BorderPane ( );

	  try {

		Scene scene = new Scene ( root , 640 , 480 );
//		scene.getStylesheets ( ).add ( getClass ( ).getResource ( "com/notespace/ScheduleTest/TestingProcessHelper/DragAndDrop/application.css" ).toExternalForm ( ) );
		primaryStage.setScene ( scene );
		primaryStage.show ( );

	  }
	  catch ( Exception e ) {
		e.printStackTrace ( );
	  }

	  root.setCenter ( new RootLayout ( ) );
    }
}