package com.notespace.ScheduleTest;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectRange extends Application

{
    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  TextField textField = new TextField ( );
	  textField.setPromptText ( "Search" );
	  Button button = new Button ( "Search" );


	  HBox hBox = new HBox ( textField , button );
	  TextArea textArea = new TextArea ( );
	  textArea.setPromptText ( "TextArea" );
	  VBox vBox = new VBox ( hBox , textArea );
	  Scene scene = new Scene ( vBox );
	  primaryStage.setScene ( scene );
	  primaryStage.show ( );

//	  button.setOnAction ( e->{
//	  	    findAndSelectString ( textArea,"World" );
//	  	} );
	  button.addEventHandler ( MouseEvent.MOUSE_CLICKED , new EventHandler < MouseEvent > ( )
	  {
		@Override
		public void handle ( MouseEvent e )
		{

		    if ( textField.getText ( ) != null && ! textField.getText ( ).isEmpty ( ) ) {
			  int index = textArea.getText ( ).indexOf ( textField.getText ( ) );
//			  if ( index == - 1 ) {
//				//	                        errorText.setText("Search key Not in the text");
//			  }
//			  else {
//				//  errorText.setText("Found");
//				textArea.selectRange ( index , index + textField.getLength ( ) );
//			  }
			  textArea.selectRange ( index , index + textField.getLength ( ) );
		    }
		    else {
			  //	                    errorText.setText("Missing search key");
			  //  errorText.setFill(Color.RED);

		    }
		}
	  } );


    }
    private void findAndSelectString(TextArea input,String lookingFor)
    {
        Pattern pattern = Pattern.compile("\\b" + lookingFor + "\\b");
        Matcher matcher = pattern.matcher(input.getText()); //Where input is a TextInput class
        boolean found = matcher.find(0);
        if(found){
            input.selectRange(matcher.start(), matcher.end());
        }
    }
}
