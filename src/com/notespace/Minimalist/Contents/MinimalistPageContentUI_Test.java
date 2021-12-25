package com.notespace.Minimalist.Contents;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import product_out.___Bundle;

public class MinimalistPageContentUI_Test extends Application
{


    public MinimalistPageContentUI_Test ( )
    {
    }

    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    public Parent startUI ( )
    {
	  MinimalistPageContent controller = new MinimalistPageContent ( );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "MinimalistPageContent" , controller );

	  return parent;
	  //	  dragFrame ( controller , stage );
    }


    public void startUINewWindow ( Stage stage )
    {
	  MinimalistPageContent controller = new MinimalistPageContent ( "D:\\NoteSpaceHere\\T3\\MinGun.jpg");

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "MinimalistPageContent" , controller );
	  Scene scene = new Scene ( parent );
	  stage.initStyle ( StageStyle.DECORATED );
	  stage.initModality ( Modality.WINDOW_MODAL );
	  //	  scene.setFill ( Color.TRANSPARENT );


	  stage.setScene ( scene );
	  stage.show ( );

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

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  startUINewWindow ( new Stage ( ) );
    }
}
