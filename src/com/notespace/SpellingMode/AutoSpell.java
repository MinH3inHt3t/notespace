package com.notespace.SpellingMode;

import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.TextObject;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AutoSpell implements Initializable
{

    @FXML
    AnchorPane textareaAnchor, autospellAnchor;
    String absolutePath;
    private LaunchClass launch;
//    private AutoSpellingTextArea textBox;
    private double width, height;

    private Service dictionaryService = new Service ( )
    {
	  @Override
	  protected Task createTask ( )
	  {
		return new Task ( )
		{
		    @Override
		    protected Object call ( ) throws Exception
		    {
			  launch = new LaunchClass ( );
			  com.notespace.SpellingMode.spelling.Dictionary dic = launch.getDictionary ( );
//			  textBox = new AutoSpellingTextArea ( launch.getAutoComplete ( ) , launch.getSpellingSuggest ( dic ) , dic );
			  return null;
		    }
		};
	  }
    };

    private Service < TextObject > textObjectService = new Service < TextObject > ( )
    {
	  @Override
	  protected Task < TextObject > createTask ( )
	  {
		return new Task < TextObject > ( )
		{
		    @Override
		    protected TextObject call ( ) throws Exception
		    {
			  return FileHandlings.buildObject ( absolutePath );
		    }
		};
	  }
    };

    public AutoSpell ( String absolutePath , double width , double height )
    {
	  this.absolutePath = absolutePath;
	  this.width = width;
	  this.height = height;
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {
	  autospellAnchor.setPrefSize ( this.width , this.height );

	  dictionaryService.restart ( );
	  dictionaryService.setOnSucceeded ( e -> {
//		textBox.setWrapText ( true );
//		textBox.setAutoComplete ( true );
//		textBox.setSpelling ( true );
//		textBox.setStyle ( "-fx-background-color:transparent;" + "-fx-font-size: 13;" + "-fx-text-fill:  linear-gradient(to right top, #a782e0, #ba7fd9, #cb7dd2, #da7bc9, #e67abf, #ea7abb, #ee7ab8, #f27ab4, #f179b7, #f078b9, #ef77bc, #ee76bf);" );
//		textBox.setPrefSize ( 570 , 492 );
//
//		textBox.textProperty ( ).addListener ( ( obs , old , niu ) -> {
//		    Save ( absolutePath , textBox.getText ( ).trim ( ) );
//		} );
//
//
//		AnchorPane.setTopAnchor ( textBox , 0.0 );
//		AnchorPane.setRightAnchor ( textBox , 0.0 );
//		AnchorPane.setBottomAnchor ( textBox , 0.0 );
//		AnchorPane.setLeftAnchor ( textBox , 0.0 );
//		textareaAnchor.getChildren ( ).add ( textBox );


		textObjectService.restart ( );
		textObjectService.setOnSucceeded ( e1 -> {
		    BindWithUI ( textObjectService.getValue ( ) );
		} );
	  } );


    }

    public void BindWithUI ( TextObject textObject )
    {
	  if ( textObject != null ) {
//		textBox.appendText ( textObject.getText ( ) );
	  }

    }

    private void Save ( String absoultePath , String text )
    {
	  try {
		FileHandlings.FileWrite ( absoultePath , text );
	  }
	  catch ( IOException e ) {
		e.printStackTrace ( );
	  }
    }


}
