package com.notespace.EditNoteModal;

import com.notespace.FileHandler.*;
import com.notespace.Font.FontRepair;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditNote implements Initializable
{

    @FXML
    public Label closeAddNewNoteLabel;
    TextObject txtObject;
    @FXML
    Label filenameTitle, fileNameLabel, settingsLabel, filepathLabel, SettingheaderLabel;
    @FXML
    Label AddNewNoteLabel, filenameLabel, BackgroundcolorLabel, BackgroundcolorLabel1, custom_BackgroundcolorLabel, TextcolorLabel, TextcolorLabel1, custom_TextcolorLabel, closeSettingsPaneLabel;
    @FXML
    TextArea textArea;
    @FXML
    AnchorPane editnoteAnchor, expandPane, anchor;
    @FXML
    ColorPicker BackgroundcolorPicker, BackgroundcolorPicker1, TextcolorPicker, TextcolorPicker1;
    @FXML
    Circle bg_circle1, bg_circle2, bg_circle3, bg_circle4, bg_circle5, bg_circle6;
    @FXML
    Circle bg_circle11, bg_circle21, bg_circle31, bg_circle41, bg_circle51, bg_circle61;
    @FXML
    Circle txt_circle1, txt_circle2, txt_circle3, txt_circle4, txt_circle5, txt_circle6;
    @FXML
    Circle txt_circle11, txt_circle21, txt_circle31, txt_circle41, txt_circle51, txt_circle61;

    boolean isSettingShown = true;

    private Service < TextObject > TextObjectService = new Service ( )
    {
	  @Override
	  protected Task < TextObject > createTask ( )
	  {
		return new Task < TextObject > ( )
		{
		    @Override
		    protected TextObject call ( ) throws Exception
		    {
			  return FileHandlings.buildObject ( txtObject.getAbsolutePath ( ) );
		    }
		};
	  }
    };


    private Service < String > BackgroundColorService = new Service ( )
    {
	  @Override
	  protected Task < String > createTask ( )
	  {
		return new Task < String > ( )
		{
		    @Override
		    protected String call ( ) throws Exception
		    {
			  return SaveCardBackgroundColor.GetCardBackgroundColor ( txtObject.getAbsolutePath ( ) );
		    }
		};
	  }
    };
    private Service < String > TextColorService = new Service ( )
    {
	  @Override
	  protected Task < String > createTask ( )
	  {
		return new Task < String > ( )
		{
		    @Override
		    protected String call ( ) throws Exception
		    {
			  return SaveTextColor.GetCardTextColor ( txtObject.getAbsolutePath ( ) );
		    }
		};
	  }
    };


    public EditNote ( TextObject textObject )
    {
	  this.txtObject = textObject;
    }

    private void ServicesRepo ( )
    {
	  this.setTextObjectService ( );
	  this.setBackgroundColorService ( );
	  this.setTextColorService ( );
    }

    private void setTextObjectService ( )
    {
	  this.TextObjectService.setOnSucceeded ( e -> {
		TextObject textObject = TextObjectService.getValue ( );

		fileNameLabel.setText ( FontRepair.fixmyanamrfont ( textObject.getName ( ).trim ( ) ) );
		filenameLabel.setText ( FontRepair.fixmyanamrfont ( textObject.getName ( ).trim ( ) ) );
		textArea.setText ( FontRepair.fixmyanamrfont ( textObject.getText ( ).trim ( ) ) );
		filepathLabel.setText ( FontRepair.fixmyanamrfont ( "File Path : " + textObject.getAbsolutePath ( ).trim () ) );

	  } );
    }

    private void setBackgroundColorService ( )
    {
	  this.BackgroundColorService.setOnSucceeded ( e -> {

		SystemConfig config = SystemConfigStorage.ReadSettings ( );
		if ( config.isBackgroundImageSet ) {
		    this.changeTheme ( config.glassTheme );
		}
		else {
		    if ( this.BackgroundColorService.getValue ( ) != null ) {

			  this.changeTheme ( this.BackgroundColorService.getValue ( ) );
		    }
		    else {
			  this.changeTheme ( SaveSettings.ReadSettings ( ).default_BackgroundColor );
		    }
		}


	  } );
    }

    private void setTextColorService ( )
    {
	  this.TextColorService.setOnSucceeded ( e -> {

		SystemConfig config = SystemConfigStorage.ReadSettings ( );
		if ( config.isBackgroundImageSet ) {
		    this.changeFont ( returnRandomGradientColors ( ) );
		}
		else {
		    if ( this.TextColorService.getValue ( ) != null ) {
			  this.changeFont ( this.TextColorService.getValue ( ) );
		    }
		    else {
			  this.changeFont ( SaveSettings.ReadSettings ( ).default_TextColor );
		    }
		}


	  } );
    }

    String returnRandomGradientColors ( )
    {

	  ArrayList < String > gColors = new ArrayList <> ( );
	  gColors.add ( "#D8D3CF" );
	  gColors.add ( " linear-gradient(to top,   derive(#3980F4, 50%) , derive(#BB8EF5, 50%))" );
	  gColors.add ( "linear-gradient(to right top, #9b7eea, #a37de6, #aa7ce3, #b07cdf, #b67bdb, #bc7bd8, #c27ad4, #c77ad1, #ce79cd, #d479c9, #da78c5, #df78c1)" );
	  gColors.add ( " linear-gradient(to top,   derive(#2E274D, 50%) , derive(#8478C5, 50%))" );
	  gColors.add ( " linear-gradient(to right top, #a782e0, #ba7fd9, #cb7dd2, #da7bc9, #e67abf, #ea7abb, #ee7ab8, #f27ab4, #f179b7, #f078b9, #ef77bc, #ee76bf)" );
	  gColors.add ( " linear-gradient(to top,   derive(#02796b, 50%) , derive(#73e57b, 50%))" );
	  //	  Collections.shuffle ( gColors );

	  return gColors.get ( 0 );

    }

    private void StartServices ( )
    {
	  this.StartTextObjectService ( );
	  this.StartBackgroundColorService ( );
	  this.StartTextColorService ( );
    }

    private void StartTextObjectService ( )
    {
	  this.TextObjectService.restart ( );
    }

    private void StartBackgroundColorService ( )
    {
	  this.BackgroundColorService.restart ( );
    }

    private void StartTextColorService ( )
    {
	  this.TextColorService.restart ( );
    }

    private void CancelServices ( )
    {
	  this.CancelTextObjectService ( );
	  this.CancelBackgroundColorService ( );
	  this.CancelTextColorService ( );
    }

    private void CancelTextObjectService ( )
    {
	  this.TextObjectService.cancel ( );
    }

    private void CancelBackgroundColorService ( )
    {
	  this.BackgroundColorService.cancel ( );
    }

    private void CancelTextColorService ( )
    {
	  this.TextColorService.cancel ( );
    }

    private void UI_Perform ( )
    {
	  this.editnoteAnchor.setOnMouseEntered ( e -> {
		this.StartServices ( );
	  } );
	  this.editnoteAnchor.setOnMouseExited ( e -> {
		this.StartServices ( );
	  } );

	  closeSettingsPaneLabel.setOnMouseClicked ( e -> {      //close settingpane
		isSettingShown = true;
		expandPane.setVisible ( false );
		filepathLabel.setVisible ( false );

		this.translateWidth ( this.textArea , 981 );
		//		final Timeline timeline = new Timeline ( );
		//		timeline.setCycleCount ( 1 );
		//		timeline.setAutoReverse ( false );
		//		timeline.getKeyFrames ( ).add ( new KeyFrame ( Duration.seconds ( 1 ) ,
		//			  new KeyValue ( textArea.prefWidthProperty ( ) , 981 ) ) );
		//		timeline.play ( );
	  } );

	  settingsLabel.setOnMouseClicked ( e -> {      //open settingpane

		if ( isSettingShown ) {
		    isSettingShown = false;
		    fadeUp ( expandPane );
		    expandPane.setVisible ( true );
		    fadeUp ( filepathLabel );
		    filepathLabel.setVisible ( true );

		    this.translateWidth ( this.textArea , 715 );
		    //		    final Timeline timeline = new Timeline ( );            //reduce size of textarea
		    //		    timeline.setCycleCount ( 1 );
		    //		    timeline.setAutoReverse ( false );
		    //		    timeline.getKeyFrames ( ).add ( new KeyFrame ( Duration.millis ( 1000 ) ,
		    //				new KeyValue ( textArea.prefWidthProperty ( ) , 715 ) ) );
		    //		    timeline.play ( );
		}
		else {//for close
		    isSettingShown = true;
		    expandPane.setVisible ( false );
		    filepathLabel.setVisible ( false );

		    this.translateWidth ( this.textArea , 981 );
		    //		    final Timeline timeline = new Timeline ( );            //increase size of textarea
		    //		    timeline.setCycleCount ( 1 );
		    //		    timeline.setAutoReverse ( false );
		    //		    timeline.getKeyFrames ( ).add ( new KeyFrame ( Duration.millis ( 1000 ) ,
		    //				new KeyValue ( textArea.prefWidthProperty ( ) , 981 ) ) );
		    //		    timeline.play ( );
		}


	  } );

	  BackgroundcolorPicker.setOnAction (
		    event -> {
			  event.consume ( );

			  Color value = BackgroundcolorPicker.getValue ( );
			  if ( value == null ) {
				//	             root.setStyle(null);
			  }
			  else {
				changeTheme ( toHexString ( value ) );
				SaveCardBackgroundColor.SaveCardBackgroundColor ( this.txtObject.getAbsolutePath ( ) , toHexString ( value ) );

			  }
		    } );

	  BackgroundcolorPicker1.setOnAction (
		    event -> {
			  event.consume ( );

			  Color value = BackgroundcolorPicker1.getValue ( );
			  if ( value == null ) {
				//	             root.setStyle(null);
			  }
			  else {
				changeTheme ( toHexString ( value ) );
				SaveCardBackgroundColor.SaveCardBackgroundColor ( this.txtObject.getAbsolutePath ( ) , toHexString ( value ) );
			  }
		    } );

	  TextcolorPicker.setOnAction (
		    event -> {
			  event.consume ( );

			  Color value = TextcolorPicker.getValue ( );
			  if ( value == null ) {
				//	             root.setStyle(null);
			  }
			  else {
				changeFont ( toHexString ( value ) );
				SaveTextColor.SaveCardTextColor ( this.txtObject.getAbsolutePath ( ) , toHexString ( value ) );

			  }
		    } );

	  TextcolorPicker1.setOnAction (
		    event -> {
			  event.consume ( );

			  Color value = TextcolorPicker1.getValue ( );
			  if ( value == null ) {
				//	             root.setStyle(null);
			  }
			  else {
				changeFont ( toHexString ( value ) );
				SaveTextColor.SaveCardTextColor ( this.txtObject.getAbsolutePath ( ) , toHexString ( value ) );
			  }
		    } );

	  Circle[] bgCircleList1 = new Circle[] { bg_circle1 , bg_circle2 , bg_circle3 , bg_circle4 , bg_circle5 , bg_circle6 };
	  for (
		    int i = 0 ;
		    i < bgCircleList1.length ;
		    ++ i
	  ) {
		Circle c = bgCircleList1[ i ];
		c.setOnMouseClicked ( e -> {
		    String hex = getCircleColor ( c );
		    changeTheme ( hex );
		    SaveCardBackgroundColor.SaveCardBackgroundColor ( this.txtObject.getAbsolutePath ( ) , hex );

		} );
	  }

	  Circle[] bgCircleList2 = new Circle[] { bg_circle11 , bg_circle21 , bg_circle31 , bg_circle41 , bg_circle51 , bg_circle61 };
	  for (
		    int i = 0 ;
		    i < bgCircleList2.length ;
		    ++ i
	  ) {
		Circle c = bgCircleList2[ i ];
		c.setOnMouseClicked ( e -> {
		    String hex = getCircleColor ( c );
		    changeTheme ( hex );
		    SaveCardBackgroundColor.SaveCardBackgroundColor ( this.txtObject.getAbsolutePath ( ) , hex );

		} );
	  }

	  Circle[] txtCircleList1 = new Circle[] { txt_circle1 , txt_circle2 , txt_circle3 , txt_circle4 , txt_circle5 , txt_circle6 };
	  for (
		    int i = 0 ;
		    i < txtCircleList1.length ;
		    ++ i
	  ) {
		Circle c = txtCircleList1[ i ];
		c.setOnMouseClicked ( e -> {
		    String hex = getCircleColor ( c );
		    changeFont ( hex );
		    SaveTextColor.SaveCardTextColor ( this.txtObject.getAbsolutePath ( ) , hex );

		} );
	  }

	  Circle[] txtCircleList2 = new Circle[] { txt_circle11 , txt_circle21 , txt_circle31 , txt_circle41 , txt_circle51 , txt_circle61 };
	  for (
		    int i = 0 ;
		    i < txtCircleList2.length ;
		    ++ i
	  ) {
		Circle c = txtCircleList2[ i ];
		c.setOnMouseClicked ( e -> {
		    String hex = getCircleColor ( c );
		    changeFont ( hex );
		    SaveTextColor.SaveCardTextColor ( this.txtObject.getAbsolutePath ( ) , hex );

		} );
	  }

	  this.textArea.focusedProperty ( ).addListener ( new ChangeListener < Boolean > ( )      //textArea on focus
	  {
		//TODO Here
		@Override
		public void changed ( ObservableValue < ? extends Boolean > arg0 , Boolean oldPropertyValue , Boolean newPropertyValue )
		{
		    if ( newPropertyValue ) {
			  //			  System.out.println ( "Textfield on focus" );
			  CancelTextObjectService ( );
		    }
		    else {
			  //			  System.out.println ( "Textfield out focus" );
			  try {
				FileHandlings.FileWrite ( txtObject.getAbsolutePath ( ) , textArea.getText ( ).trim ( ) );
			  }
			  catch ( IOException e ) {
				e.printStackTrace ( );
			  }

			  StartTextObjectService ( );

		    }
		}
	  } );

	  //	  this.textArea.textProperty ( ).addListener ( ( obs , old , niu ) -> {
	  //		// TODO here
	  //		Save ( txtObject.getAbsolutePath ( ) , textArea.getText ( ).trim ( ) );
	  //	  } );

	  this.textArea.setOnMouseExited ( e -> {
		Save ( txtObject.getAbsolutePath ( ) , textArea.getText ( ).trim ( ) );
	  } );

    }

    private void UI_Binding ( )
    {

    }

    private void UI_Prepare ( )
    {
	  this.expandPane.setVisible ( false );
	  this.filepathLabel.setVisible ( false );
    }

    private void translateWidth ( TextArea node , double endValue )
    {
	  node.setCache ( true );
	  node.setCacheHint ( CacheHint.QUALITY );
	  node.setCacheHint ( CacheHint.SPEED );

	  KeyValue start = new KeyValue ( node.prefWidthProperty ( ) , node.getWidth ( ) );
	  KeyValue end = new KeyValue ( node.prefWidthProperty ( ) , endValue );
	  KeyValue cache = new KeyValue ( node.cacheHintProperty ( ) , CacheHint.QUALITY );
	  KeyValue cache2 = new KeyValue ( node.cacheHintProperty ( ) , CacheHint.ROTATE );

	  KeyFrame startFrame = new KeyFrame ( Duration.ZERO , start , cache , cache2 );
	  KeyFrame endFrame = new KeyFrame ( Duration.seconds ( 1 ) , end , cache , cache2 );

	  Timeline timeline = new Timeline ( startFrame , endFrame );
	  timeline.setAutoReverse ( true );
	  timeline.setCycleCount ( 1 );
	  timeline.play ( );


	  //	  Timeline timeline = new Timeline (
	  //		    new KeyFrame ( Duration.ZERO , new KeyValue ( node.prefWidthProperty ( ) , node.getWidth ( ) ) ) ,
	  //		    new KeyFrame ( Duration.seconds ( 0.5 ) , new KeyValue ( node.prefWidthProperty ( ) , endValue ) ) );
	  //	  timeline.play ( );
    }

    private void fadeUp ( Node node )
    {
	  node.setCache ( true );
	  node.setCacheHint ( CacheHint.QUALITY );
	  node.setCacheHint ( CacheHint.ROTATE );
	  FadeTransition fade = new FadeTransition ( );
	  fade.setDuration ( Duration.seconds ( 1 ) );
	  fade.setFromValue ( 0 );
	  fade.setToValue ( 10 );
	  fade.setNode ( node );
	  fade.play ( );
    }


    private String getCircleColor ( Circle circle )
    {
	  Color c = ( Color ) circle.getFill ( );
	  String hex = String.format ( "#%02X%02X%02X" ,
		    ( int ) ( c.getRed ( ) * 255 ) ,
		    ( int ) ( c.getGreen ( ) * 255 ) ,
		    ( int ) ( c.getBlue ( ) * 255 ) );
	  return hex;
    }

    private void changeTheme ( String hex )
    {
	  String style = String.format ( "-fx-background-color: %s;" , hex );
	  expandPane.setStyle ( style + "-fx-border-radius: 20;" + "-fx-background-radius: 20;" );
	  anchor.setStyle ( style + "-fx-border-radius: 20;" + "-fx-background-radius: 20;" );
	  //	  BackgroundcolorPicker.setStyle ( anchor.getStyle ( ) );
	  //	  BackgroundcolorPicker1.setStyle ( anchor.getStyle ( ) );
	  //	  TextcolorPicker.setStyle ( anchor.getStyle ( ) );
	  //	  TextcolorPicker1.setStyle ( anchor.getStyle ( ) );
    }

    private void changeFont ( String hex )
    {

	  Label[] labels = new Label[] { AddNewNoteLabel , SettingheaderLabel , filenameLabel , filepathLabel , settingsLabel , closeAddNewNoteLabel , BackgroundcolorLabel , BackgroundcolorLabel1 , custom_BackgroundcolorLabel , TextcolorLabel , TextcolorLabel1 , custom_TextcolorLabel , closeSettingsPaneLabel };
	  for (
		    int i = 0 ;
		    i < labels.length ;
		    ++ i
	  ) {
		Label c = labels[ i ];
		if ( hex != null ) {
		    //		    c.setTextFill ( Color.valueOf ( hex ) );
		    c.setStyle ( "-fx-text-fill:" + hex + ";" );
		}


	  }

	  textArea.setStyle ( "-fx-text-fill:" + hex + ";" );
    }

    private String toHexString ( Color color )
    {
	  int r = ( ( int ) Math.round ( color.getRed ( ) * 255 ) ) << 24;
	  int g = ( ( int ) Math.round ( color.getGreen ( ) * 255 ) ) << 16;
	  int b = ( ( int ) Math.round ( color.getBlue ( ) * 255 ) ) << 8;
	  int a = ( ( int ) Math.round ( color.getOpacity ( ) * 255 ) );

	  return String.format ( "#%08X" , ( r + g + b + a ) );
    }

    private void init ( )
    {
	  this.ServicesRepo ( );
	  this.UI_Perform ( );
	  this.UI_Binding ( );
	  this.UI_Prepare ( );
	  this.StartServices ( );
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

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {
	  this.init ( );
    }
}
