package com.notespace.ScheduleTest;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GetPixelColorOfBgImgUnderMouse extends Application
{

    int bgW = 210;
    int bgH = 210;

    public static void main ( String[] args )
    {
	  launch ( args );
    }

    @Override
    public void start ( Stage primaryStage )
    {
	  StackPane root = new StackPane ( );


	  // background image is set by css
	  //	  ImageView imageView = new ImageView ( new Image ( "com/notespace/ScheduleTest/grid.png" ) );

	  Scene scene = new Scene ( root , bgW , bgH );
	  scene.getStylesheets ( ).addAll ( this.getClass ( ).getResource ( "style1.css" ).toExternalForm ( ) );
	  primaryStage.setScene ( scene );
	  primaryStage.show ( );
	  Canvas canvas = new Canvas ( bgW , bgH );
	  root.getChildren ( ).add ( canvas );
	  canvas.addEventHandler ( MouseEvent.MOUSE_CLICKED , new EventHandler < MouseEvent > ( )
	  {
		@Override
		public void handle ( MouseEvent e )
		{
		    int x = new Double ( e.getSceneX ( ) ).intValue ( );
		    int y = new Double ( e.getSceneY ( ) ).intValue ( );
		    // detect pixel color of background grid
		    Image image = root.getBackground ( ).getImages ( ).get ( 0 ).getImage ( );
		    PixelReader r = image.getPixelReader ( );
		    Color argb = r.getColor ( x , y );
		    System.out.println ( argb );
		}
	  } );
    }
}