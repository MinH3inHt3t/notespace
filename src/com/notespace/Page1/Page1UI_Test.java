package com.notespace.Page1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import product_out.___Bundle;

public class Page1UI_Test extends Application
{


    public Page1UI_Test ( )
    {
    }

    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    public Parent startUI ( boolean newWindowCalled )
    {
	  Page1 controller = new Page1 ( newWindowCalled );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "Page1" , controller );

	  return parent;
	  //	  dragFrame ( controller , stage );
    }


    public void startUINewWindow ( Stage stage , boolean newWindowCalled )
    {
	  Page1 controller = new Page1 ( newWindowCalled );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "Page1" , controller );
	  Scene scene = new Scene ( parent );
	  stage.initStyle ( StageStyle.DECORATED );
	  stage.initModality ( Modality.WINDOW_MODAL );
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


    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  startUINewWindow ( new Stage ( ) , true );
    }
}
