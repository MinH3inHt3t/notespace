package com.notespace.Settings;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import product_out.___Bundle;

public class DateSettingsUI_Test extends Application
{
    private static double xOffset = 0;
    private static double yOffset = 0;

    public DateSettingsUI_Test ( )
    {

    }

    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    public Parent startUI ( )
    {
	  DateSettings controller = new DateSettings ( );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "DateSettings" , controller );

	  return parent;

    }


    public void startUINewWindow ( Stage stage )
    {
	  DateSettings controller = new DateSettings ( );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "DateSettings" , controller );
	  controller.previousHbox.setOnMouseClicked ( e -> {
		if ( e.getButton ( ).equals ( MouseButton.PRIMARY ) ) {
		    stage.close ( );
		}
	  } );


	  Scene scene = new Scene ( parent );
	  stage.initStyle ( StageStyle.UTILITY );
	  stage.setResizable ( false );
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

	  //	  dragFrame ( controller , stage );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  startUINewWindow ( new Stage ( ) );
    }
}
