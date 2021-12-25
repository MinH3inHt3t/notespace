package com.notespace.ScheduleTest;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Ui implements Initializable
{
    @FXML
    StackPane root;
    @FXML
    Canvas canva;

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {
	  canva.addEventHandler ( MouseEvent.MOUSE_CLICKED , new EventHandler < MouseEvent > ( )
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
