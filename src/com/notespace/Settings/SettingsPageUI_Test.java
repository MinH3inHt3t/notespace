package com.notespace.Settings;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import product_out.___Bundle;

public class SettingsPageUI_Test extends Application
{
    private static double xOffset = 0;
    private static double yOffset = 0;

    public SettingsPageUI_Test ( )
    {

    }

    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    public Parent startUI ( )
    {
	  SettingsPage controller = new SettingsPage ( );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "SettingsPage" , controller );

	  return parent;

    }


    public void startUINewWindow ( Stage stage )
    {
	  SettingsPage controller = new SettingsPage ( );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "SettingsPage" , controller );
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

	  //	  dragFrame ( controller , stage );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  startUINewWindow ( new Stage ( ) );
    }
}
