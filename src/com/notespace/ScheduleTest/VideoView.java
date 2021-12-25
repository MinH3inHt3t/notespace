package com.notespace.ScheduleTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;


public class VideoView extends Application
{
    public static void main ( String args[] )
    {
	  launch ( args );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {

	  //	  String file = "C://Users/Home/Desktop/Movie.mp4";
	  String file = "C:\\Users\\Hp\\Downloads\\Video\\mfp.mp4";

	  Media media = new Media ( new File ( file ).toURI ( ).toString ( ) );

	  MediaPlayer mediaplayer = new MediaPlayer ( media );

	  MediaView mediaView = new MediaView ( mediaplayer );

	  Button button1 = new Button ( "Play" );
	  Button button2 = new Button ( "Pause" );
	  Button button3 = new Button ( "Stop" );

	  button1.setOnAction ( e -> {
		mediaplayer.play ( );
	  } );

	  button2.setOnAction ( e -> {
		mediaplayer.pause ( );
	  } );

	  button3.setOnAction ( e -> {
		mediaplayer.stop ( );
	  } );


	  GridPane layout = new GridPane ( );
	  layout.setHgap ( 10 );
	  layout.setVgap ( 10 );

	  //layout.add(mediaView, 0, 0);
	  layout.add ( mediaView , 1 , 0 );
	  layout.add ( button1 , 0 , 1 );
	  layout.add ( button2 , 1 , 1 );
	  layout.add ( button3 , 2 , 1 );

	  Scene scene = new Scene ( layout , 300 , 200 );

	  primaryStage.setTitle ( "CodersLegacy" );
	  primaryStage.setScene ( scene );
	  primaryStage.show ( );
    }
}