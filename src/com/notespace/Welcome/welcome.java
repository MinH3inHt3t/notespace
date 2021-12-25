package com.notespace.Welcome;

import com.notespace.Interfaces.Controller_Necessary_Impl;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class welcome implements Initializable, Controller_Necessary_Impl
{
    @FXML
    AnchorPane logoAnchor;
    @FXML
    Label label1;


    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {

	  final String content = "NoteSpace";
	  final Animation animation = new Transition ( )
	  {
		{
		    setCycleCount ( INDEFINITE );
		    setAutoReverse ( true );
		    setCycleDuration ( Duration.millis ( 3000 ) );
		}

		protected void interpolate ( double frac )
		{
		    final int length = content.length ( );
		    final int n = Math.round ( length * ( float ) frac );
		    label1.setText ( content.substring ( 0 , n ) );
		}

	  };

	  animation.play ( );

	  this.logoAnchor.setOnMouseClicked ( e -> {
		//go to about pane
	  } );


    }


    @Override
    public void init ( )
    {

    }

    @Override
    public void GUI_Perform ( )
    {

    }

    @Override
    public void GUI_Binding ( )
    {

    }

    @Override
    public void GUI_Prepare ( )
    {

    }

    @Override
    public void GUI_Animation ( )
    {

    }

    @Override
    public void Schedule_Services_Repo ( )
    {

    }

    @Override
    public void Start_Schedule_Services ( )
    {

    }

    @Override
    public void Cancel_Schedule_Services ( )
    {

    }

    @Override
    public void Services_Repo ( )
    {

    }

    @Override
    public void Start_Services ( )
    {

    }

    @Override
    public void Cancel_Services ( )
    {

    }
}
