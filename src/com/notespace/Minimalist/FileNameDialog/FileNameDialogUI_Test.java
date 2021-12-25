package com.notespace.Minimalist.FileNameDialog;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import product_out.___Bundle;

public class FileNameDialogUI_Test extends Application
{



    public FileNameDialogUI_Test ( )
    {
    }

    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    public Parent startUI ( )
    {
	  FileNameDialog controller = new FileNameDialog ( );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "FileNameDialog" , controller );

	  return parent;
	  //	  dragFrame ( controller , stage );
    }

    public void startUINewWindow ( Stage stage )
    {

	  FileNameDialog controller = new FileNameDialog ( );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "FileNameDialog" , controller );
	  Scene scene = new Scene ( parent );
	  stage.setResizable ( false );
	  stage.initStyle ( StageStyle.DECORATED );
	  stage.initModality ( Modality.APPLICATION_MODAL );
	  //	  scene.setFill ( Color.TRANSPARENT );

	  controller.OKButton.setOnAction ( e -> {
		String fileName = controller.fileNameTextField.getText ( ).trim ( );
	  } );

	  controller.CancelButton.setOnAction ( e -> {
		stage.close ( );
	  } );

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
	    startUINewWindow ( new Stage ( )   );
    }
}
