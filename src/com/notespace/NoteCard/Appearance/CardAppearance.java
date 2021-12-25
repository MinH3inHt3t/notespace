package com.notespace.NoteCard.Appearance;

import com.notespace.FileHandler.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CardAppearance implements Initializable, interfaceUI
{
    @FXML
    public AnchorPane cardAppearanceAnchor;
    @FXML
    public Label appearance_Label,closeNoteAppearanceLabel;
    String absolutePath;



    public CardAppearance ( String absolutePath )
    {
	  this.absolutePath = absolutePath;
    }


    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {
	  this.UI_Init ( );
    }

    @Override
    public void UI_Init ( )
    {
	  UI_Prepare ( );
	  UI_Action ( );
    }

    @Override
    public void UI_Prepare ( )
    {
	  new SetBackgroundColorService ( );
	  new SetTextColorService ( );
    }

    @Override
    public void UI_Action ( )
    {
	  this.cardAppearanceAnchor.setOnMouseEntered ( e -> {
		new SetBackgroundColorService ( );
		new SetTextColorService ( );
	  } );


    }

    private void changeBackgroundColor ( String hex )
    {
	  Node node[] = { cardAppearanceAnchor };
	  int i = 0;
	  while ( i < node.length ) {
		String bgStyle = String.format ( "-fx-background-color: %s;" , hex );
		node[ i ].setStyle ( bgStyle );
		//		node[ i ].setStyle ( "-fx-background-color: " + hex + ";" );
		i++;
	  }
    }

    private void changeTextColor ( String hex )
    {
	  Node node[] = { appearance_Label,closeNoteAppearanceLabel };
	  int i = 0;
	  while ( i < node.length ) {
		String txtStyle = String.format ( "-fx-text-fill: %s;" , hex );
		node[ i ].setStyle ( txtStyle );
		i++;
	  }
    }

    class SetBackgroundColorService extends Service < String >
    {

	  public SetBackgroundColorService ( )
	  {

		restart ( );
		setOnSucceeded ( e -> {
		    //		    changeBackgroundColor ( getValue ( ) );

		    SystemConfig config = SystemConfigStorage.ReadSettings ( );
		    if ( config.isBackgroundImageSet ) {
			  changeBackgroundColor ( config.glassTheme );
		    }
		    else {
			  String bgHex = getValue ( );
			  if ( bgHex == null ) {
				bgHex = SaveSettings.ReadSettings ( ).default_BackgroundColor;
			  }
			  changeBackgroundColor ( bgHex );
		    }


		} );
	  }


	  @Override
	  protected Task < String > createTask ( )
	  {
		return new Task < String > ( )
		{
		    @Override
		    protected String call ( ) throws Exception
		    {
			  return SaveCardBackgroundColor.GetCardBackgroundColor ( absolutePath );
		    }
		};
	  }
    }

    class SetTextColorService extends Service < String >
    {

	  public SetTextColorService ( )
	  {
		restart ( );
		setOnSucceeded ( e -> {
		    String txtHex = getValue ( );
		    if ( txtHex == null ) {
			  txtHex = SaveSettings.ReadSettings ( ).default_TextColor;
		    }
		    changeTextColor ( txtHex );
		} );
	  }

	  @Override
	  protected Task < String > createTask ( )
	  {
		return new Task < String > ( )
		{
		    @Override
		    protected String call ( ) throws Exception
		    {
			  return SaveTextColor.GetCardTextColor ( absolutePath );
		    }
		};
	  }
    }
}



