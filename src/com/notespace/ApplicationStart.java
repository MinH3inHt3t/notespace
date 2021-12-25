package com.notespace;

import com.notespace.FileHandler.BuildNoteSpace;
import com.notespace.FileHandler.NoteSpacePathStorage;
import com.notespace.HomePage.HomePage;
import com.notespace.MainPage.Main_Page;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import product_out.PURPOSE;
import product_out.___Bundle;

public class ApplicationStart extends Application
{
    public static void main ( String[] args )
    {
	  //        System.out.println ( ___Bundle.__Helper_Database._getInstance ()._GetConnection ( GUI_Start.class ) );
	  //	  new com.notespace.FileHandler.SaveCardBackgroundColor ( );
	  ___Bundle._getInstance ( PURPOSE.TESTER );
	  Application.launch ( args );


	  //	  System.out.println ( com.notespace.Settings.NoteCard_CreateDateFormat);
    }

    static void startGame ( Stage primaryStage )
    {

	  // initialisation from start method goes here

	  if ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath != null && NoteSpacePathStorage.CheckNoteSpacePathExists ( ) && BuildNoteSpace.CheckNoteSpaceFormat ( ) ) { //if notespace storage null and
		Scene scene = new Scene ( ___Bundle.__ViewLoader._getInstance ( )._load ( "Main_Page" , new Main_Page ( ) ) );

//		primaryStage.setMaximized ( true );
		primaryStage.setScene ( scene );
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
	  else {
		//		JOptionPane.showMessageDialog ( null , "Recent NoteSpace is missing" );
		//Go to choice directory UI

		Scene scene = new Scene ( ___Bundle.__ViewLoader._getInstance ( )._load ( "HomePage" , new HomePage ( ) ) );

		primaryStage.setScene ( scene );
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


	  primaryStage.setOnCloseRequest ( e -> {
		//		Platform.exit ( );
	  } );
	  primaryStage.show ( );
    }

    public static void restart ( Stage stage )
    {
	  //	  cleanup ( );
	  startGame ( stage );
    }


    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  startGame ( primaryStage );

    }
}
