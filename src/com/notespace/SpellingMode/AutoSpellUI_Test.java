package com.notespace.SpellingMode;

import com.notespace.NoteCard.Appearance.CardAppearance;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import product_out.___Bundle;

public class AutoSpellUI_Test extends Application
{
    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }
    String absolutePath;
    public AutoSpellUI_Test ( String absolutePath)
    {
	  this.absolutePath = absolutePath;
    }

    public Parent startUI ( )
    {
	  AutoSpell controller = new AutoSpell ( absolutePath ,50,50);

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "AutoSpell" , controller );


	  return parent;
	  //	  dragFrame ( controller , stage );
    }

    public void startUINewWindow ( Stage stage )
    {
	  AutoSpell controller = new AutoSpell ( absolutePath , 50 , 50 );

	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "AutoSpell" , controller );
	  Scene scene = new Scene ( parent );
	  stage.initStyle ( StageStyle.TRANSPARENT );
	  stage.initModality ( Modality.APPLICATION_MODAL );
	  scene.setFill ( Color.TRANSPARENT );
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
	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "AutoSpell" , new AutoSpell ( null , 600 , 500 ) );
	  Scene scene = new Scene ( parent );
	  //	  JMetro metro = new JMetro ( Style.LIGHT );
	  //	  metro.setScene ( scene );
	  primaryStage.setScene ( scene );
	  primaryStage.show ( );
    }
}
