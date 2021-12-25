package com.notespace.TabMode.Tabs;

import com.notespace.CLipBoard;
import com.notespace.FileHandler.CaptureText;
import com.notespace.FileHandler.CaptureTextStorage;
import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.TextObject;
import com.notespace.Font.FontRepair;
import com.notespace.NoteCard.NoteCardUI_Test;
import com.notespace.RecentNoteStorage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Tabs implements Initializable
{
    public static String oldStringControl;
    public static String newStringControl;
    @FXML
    AnchorPane tabsAnchor, insideAnchorPane, createNewTextAnchorPane, folderNotifyAnchorPane;
    @FXML
    HBox progressHBox;
    @FXML
    TextField txtfield;
    @FXML
    TextArea textArea;
    @FXML
    Label progressStatusBar, folderNotifyLabel;
    @FXML
    ProgressBar progressBar;
    @FXML
    ImageView imageView;
    @FXML
    MediaView mediaView;
    @FXML
    VBox imageVBox, mediaVBox, operateVBox;
    @FXML
    ToggleButton editToggle;
    @FXML
    SplitMenuButton filePathSplitMenuButton;
    @FXML
    Button openFolderButton;
    TextObject textObject;
    @FXML
    MenuBar menuBar;

    @FXML
    MenuItem newNoteMenuItem, copyPathMenuItem, openInNotePadMenuItem, openInCardModeMenuItem, openExplorerMenuItem, editNoteMenuItem, captureNoteMenuItem;
    String absolutePath;

    String[] noteExtensions = { ".txt" , ".ns" , ".html" , ".css" , ".js" , ".xml" , ".java" };
    String[] imageExtensions = { ".png" , ".jpg" , ".jpeg" , ".webp" };
    String[] videoExtensions = { ".mp4" };


    public Tabs ( String absolutePath )
    {
	  this.absolutePath = absolutePath;

	  RecentNoteStorage.setRecentNote ( absolutePath , absolutePath );
    }

    public Tabs ( )
    {

    }

    private static String getFileExtension ( File file )
    {
	  String extension = "";

	  try {
		if ( file != null && file.exists ( ) ) {
		    String name = file.getName ( );
		    extension = name.substring ( name.lastIndexOf ( "." ) );
		}
	  }
	  catch ( Exception e ) {
		extension = "";
	  }

	  return extension;

    }


    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {


	  this.init ( );


    }

    private void init ( )
    {

	  this.prepare ( );
	  this.perform ( );
    }

    private void prepare ( )
    {
	  //	  this.callNoteCard ( );
	  textArea.setEditable ( false );
	  new VisibleComponents ( false , progressStatusBar , progressBar , operateVBox , imageVBox , mediaVBox );

	  test ( );

    }

    private void test ( )
    {
	  if ( this.absolutePath != null ) {
		File file = new File ( this.absolutePath );
		if ( file.isDirectory ( ) ) {
		    folderNotifyLabel.setText ( absolutePath );
		    new VisibleComponents ( false , insideAnchorPane , mediaVBox , imageVBox , createNewTextAnchorPane );
		    new VisibleComponents ( true , operateVBox , folderNotifyAnchorPane );
		}
		else {
		    boolean noteExt = Arrays.stream ( noteExtensions ).anyMatch ( getFileExtension ( file ) :: equals );
		    boolean imgExt = Arrays.stream ( imageExtensions ).anyMatch ( getFileExtension ( file ) :: equals );
		    boolean vdoExt = Arrays.stream ( videoExtensions ).anyMatch ( getFileExtension ( file ) :: equals );


		    if ( noteExt ) {
			  System.out.println ( "Note Process" );
			  new TextObjectService ( );
		    }
		    else {
			  if ( imgExt ) {
				System.out.println ( "Image Process" );
				new setImageService ( );
			  }
			  else if ( vdoExt ) {
				System.out.println ( "Vdo Process" );
				new setVideoService ( );
			  }
		    }
		}
	  }
    }

    private void refresh ( )
    {
	  if ( this.absolutePath != null ) {
		File file = new File ( this.absolutePath );
		//	  System.out.println ( file.getAbsolutePath ( ) );
		if ( file.isDirectory ( ) ) {
		    folderNotifyLabel.setText ( absolutePath );
		    new VisibleComponents ( false , insideAnchorPane , mediaVBox , imageVBox , createNewTextAnchorPane );
		    new VisibleComponents ( true , operateVBox , folderNotifyAnchorPane );
		}
		else {
		    //		new VisibleComponents ( false , insideAnchorPane , mediaVBox , imageVBox );
		    if ( bagOfWords ( getFileExtension ( file ) ) ) {
			  System.out.println ( "PrintHer " + getFileExtension ( file ) );
			  boolean noteExt = Arrays.stream ( noteExtensions ).anyMatch ( getFileExtension ( file ) :: equals );
			  if ( noteExt ) {
				new TextObjectService ( );
			  }

			  boolean imgExt = Arrays.stream ( imageExtensions ).anyMatch ( getFileExtension ( file ) :: equals );
			  if ( imgExt ) {
				new setImageService ( );
			  }

			  boolean vdoExt = Arrays.stream ( videoExtensions ).anyMatch ( getFileExtension ( file ) :: equals );
			  if ( vdoExt ) {
				new setVideoService ( );
			  }
		    }

		}
	  }
    }


    private Effect getFrostEffect ( double blur_amount , int iteration )
    {
	  Effect frostEffect =
		    new BoxBlur ( blur_amount , blur_amount , iteration );

	  return frostEffect;
    }

    public void pauseTransition ( )
    {


	  new VisibleComponents ( true , progressStatusBar , progressBar,progressHBox );
	  progressStatusBar.setText ( "opening files..." );
	  progressBar.setProgress ( 0.3 );

	  PauseTransition pauseTransition1 = new PauseTransition ( Duration.seconds ( 0.5 ) );
	  pauseTransition1.setOnFinished ( e1 -> {
			  progressBar.setProgress ( 0 );
			  new VisibleComponents ( false , progressStatusBar , progressBar,progressHBox );
		    }
	  );
	  pauseTransition1.play ( );


	  //	  PauseTransition pauseTransition2 = new PauseTransition ( Duration.seconds ( 0.5 ) );
	  //	  pauseTransition2.setOnFinished ( e1 -> {
	  //			  progressStatusBar.setText ( "opening file.." );
	  //		    }
	  //	  );
	  //	  pauseTransition2.play ( );


	  Timeline timeline = new Timeline (
		    new KeyFrame ( Duration.ZERO , new KeyValue ( progressBar.progressProperty ( ) , 0 ) ) ,
		    new KeyFrame ( Duration.seconds ( 3 ) , e -> {
			  // do anything you need here on completion...

		    } , new KeyValue ( progressBar.progressProperty ( ) , 1 ) )
	  );
	  timeline.setCycleCount ( 1 );
	  timeline.play ( );


    }


    private void perform ( )
    {
	  shortCutsAdded ( );


	  insideAnchorPane.widthProperty ( ).addListener ( ( obs , oldVal , newVal ) -> {
		Platform.runLater ( new Runnable ( )
		{
		    @Override
		    public void run ( )
		    {
			  adjustMediaViewSize ( );
		    }
		} );
		//								backgroundImage.setFitHeight ( backgroundImage.getFitHeight () +Double.parseDouble ( newVal.toString () ));
	  } );


	  insideAnchorPane.heightProperty ( ).addListener ( ( obs , oldVal , newVal ) -> {
		Platform.runLater ( new Runnable ( )
		{
		    @Override
		    public void run ( )
		    {
			  adjustMediaViewSize ( );
		    }
		} );
		//								backgroundImage.setFitHeight ( backgroundImage.getFitHeight () +Double.parseDouble ( newVal.toString () ));
	  } );


	  this.newNoteMenuItem.setOnAction ( e -> {

		new VisibleComponents ( false , folderNotifyAnchorPane );
		new VisibleComponents ( true , operateVBox , createNewTextAnchorPane );
		textArea.setEffect ( getFrostEffect ( 60 , 3 ) );
	  } );

	  this.copyPathMenuItem.setOnAction ( e -> {
		CLipBoard.SaveToCLip ( this.filePathSplitMenuButton.getText ( ).trim ( ) );
	  } );

	  this.openInNotePadMenuItem.setOnAction ( e -> {
		try {
		    Desktop.getDesktop ( ).open ( new File ( absolutePath ) );
		}
		catch ( IOException ex ) {
		    ex.printStackTrace ( );
		}
	  } );

	  this.openInCardModeMenuItem.setOnAction ( e -> {
		new NoteCardUI_Test ( absolutePath ).startUINewWindow ( new Stage ( ) );
	  } );

	  this.openExplorerMenuItem.setOnAction ( e -> {
		File file = new File ( absolutePath );
		try {
		    Desktop.getDesktop ( ).open ( file.getParentFile ( ) );
		}
		catch ( IOException ex ) {
		    ex.printStackTrace ( );
		}
	  } );

	  this.editNoteMenuItem.setOnAction ( e -> {
		editToggle.setSelected ( true );
		textArea.setEditable ( true );


		textArea.setOnMouseExited ( event -> {
		    if ( textArea.editableProperty ( ).getValue ( ) ) {
			  try {
				FileHandlings.FileWrite ( absolutePath , textArea.getText ( ) );
			  }
			  catch ( IOException e1 ) {
				e1.printStackTrace ( );
			  }
		    }

		} );

		focusTextArea ( );

	  } );


	  this.captureNoteMenuItem.setOnAction ( e -> {
		if ( textObject != null ) {
		    CaptureTextStorage.addCaptureText ( new CaptureText ( absolutePath , textObject.getText ( ) ) );
		}
	  } );


	  this.textArea.setOnMouseEntered ( e -> {
		new TextObjectService ( );
	  } );


	  this.editToggle.selectedProperty ( ).addListener ( new ChangeListener < Boolean > ( )
	  {
		@Override
		public void changed ( ObservableValue < ? extends Boolean > observable , Boolean oldValue , Boolean newValue )
		{

		    if ( newValue ) {
			  if ( editToggle.isSelected ( ) ) {
				textArea.setEditable ( true );


				textArea.setOnMouseExited ( event -> {
				    if ( textArea.editableProperty ( ).getValue ( ) ) {
					  try {
						FileHandlings.FileWrite ( absolutePath , textArea.getText ( ) );
					  }
					  catch ( IOException e ) {
						e.printStackTrace ( );
					  }
				    }

				} );

				focusTextArea ( );
			  }

		    }
		    else {
			  textArea.setEditable ( false );
		    }

		}
	  } );


	  openFolderButton.setOnAction ( e -> {
		try {
		    Desktop.getDesktop ( ).open ( Paths.get ( absolutePath ).toFile ( ) );
		}
		catch ( IOException ex ) {
		    ex.printStackTrace ( );
		}
	  } );


    }// end of perform

    private void focusTextArea ( )
    {
	  Platform.runLater ( new Runnable ( )
	  {
		@Override
		public void run ( )
		{
		    textArea.requestFocus ( );
		    textArea.selectAll ( );
		}
	  } );
    }

    private void shortCutsAdded ( )
    {
	  Platform.runLater ( new Runnable ( )
	  {
		@Override
		public void run ( )
		{
		    KeyCombination openTextKeyCombination = new KeyCodeCombination ( KeyCode.E , KeyCombination.ALT_DOWN );
		    editNoteMenuItem.setAccelerator ( openTextKeyCombination );
		    Scene scene = tabsAnchor.getScene ( );
		    scene.getAccelerators ( ).put (
				new KeyCodeCombination ( KeyCode.E , KeyCombination.ALT_DOWN ) ,
				new Runnable ( )
				{
				    @FXML
				    public void run ( )
				    {
					  editNoteMenuItem.fire ( );
				    }
				}
		    );
		}
	  } );

	  Platform.runLater ( new Runnable ( )
	  {
		@Override
		public void run ( )
		{
		    KeyCombination openTextKeyCombination = new KeyCodeCombination ( KeyCode.C , KeyCombination.ALT_DOWN );
		    captureNoteMenuItem.setAccelerator ( openTextKeyCombination );
		    Scene scene = tabsAnchor.getScene ( );
		    scene.getAccelerators ( ).put (
				new KeyCodeCombination ( KeyCode.C , KeyCombination.ALT_DOWN ) ,
				new Runnable ( )
				{
				    @FXML
				    public void run ( )
				    {
					  captureNoteMenuItem.fire ( );
				    }
				}
		    );
		}
	  } );
    }


    public boolean bagOfWords ( String str )
    {
	  String[] words = { ".txt" , ".png" , ".jpg" , ".jpeg" , ".mp4" };
	  return ( Arrays.asList ( words ).contains ( str ) );
    }

    private void bindwithUI ( TextObject object )
    {


	  if ( object == null ) {

	  }
	  else {

		newStringControl = object.getText ( );
		new VisibleComponents ( true , editToggle );

		textArea.setText ( FontRepair.fixmyanamrfont ( object.getText ( ).trim ( ) ) );


	  }

    }

    private void adjustMediaViewSize ( )
    {
	  Platform.runLater ( new Runnable ( )
	  {
		@Override
		public void run ( )
		{
		    mediaView.setFitWidth ( insideAnchorPane.getWidth ( ) - 30 );
		    mediaView.setFitHeight ( insideAnchorPane.getHeight ( ) - 30 );

		    imageView.setFitWidth ( insideAnchorPane.getWidth ( ) - 30 );
		    imageView.setFitHeight ( insideAnchorPane.getHeight ( ) - 30 );


		}
	  } );
    }

    class TextObjectService extends Service < TextObject >
    {

	  public TextObjectService ( )
	  {

		restart ( );
		//		setPeriod ( Duration.seconds ( 1 ) );
		setOnRunning ( e -> {

		} );
		setOnFailed ( e -> {


		    bindwithUI ( null );

		    textArea.setText ( "File Path " + '"' + absolutePath + '"' + " is not found in your system. " );

		    new VisibleComponents ( false , editToggle , filePathSplitMenuButton );

		} );
		setOnSucceeded ( e -> {
		    textObject = getValue ( );
		    new VisibleComponents ( false , imageVBox , mediaVBox );
		    new VisibleComponents ( true , insideAnchorPane , filePathSplitMenuButton );


		    filePathSplitMenuButton.setText ( absolutePath );

		    oldStringControl = getValue ( ).getText ( );

		    //		    bindwithUI ( getValue ( ) );


		    if ( oldStringControl != null ) {            //come back here TODO
			  if ( ! oldStringControl.equals ( newStringControl ) ) {
				oldStringControl = getValue ( ).getText ( );
				pauseTransition ( );
				bindwithUI ( getValue ( ) );
			  }
		    }
		    else {
			  int i = 0; //work only once
			  do {
				oldStringControl = getValue ( ).getText ( );
				pauseTransition ( );
				bindwithUI ( getValue ( ) );
				i++;
			  }
			  while ( i <= 0 );


			  //			  if ( i == 0 ) {
			  //				oldStringControl = getValue ( ).getText ( );
			  //				pauseTransition ( );
			  //				bindwithUI ( getValue ( ) );
			  //				i++;
			  //			  }
		    }


		} );
	  }

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
    }

    class setImageService extends Service < Image >
    {

	  public setImageService ( )
	  {

		restart ( );
		setOnRunning ( e -> {

		} );
		setOnFailed ( e -> {

		} );
		setOnSucceeded ( e -> {

		    new VisibleComponents ( false , insideAnchorPane , mediaVBox );
		    new VisibleComponents ( true , imageVBox );


		    Platform.runLater ( new Runnable ( )
		    {
			  @Override
			  public void run ( )
			  {
				//				imageView.setFitWidth ( textArea.getWidth ( ) );
				//				imageView.setFitHeight ( textArea.getHeight ( ) );

				imageView.setOnMouseReleased ( new EventHandler < MouseEvent > ( )
				{
				    @Override
				    public void handle ( MouseEvent event )
				    {
					  if ( event.getButton ( ).equals ( MouseButton.PRIMARY ) ) {
						if ( event.getClickCount ( ) == 1 ) {

						    File imgFile = new File ( absolutePath );
						    if ( imgFile.exists ( ) ) {


							  PauseTransition pauseTransition1 = new PauseTransition ( Duration.seconds ( 1.5 ) );
							  pauseTransition1.setOnFinished ( e1 -> {
									  progressBar.setProgress ( 0 );
									  new VisibleComponents ( false , progressStatusBar , progressBar );
								    }
							  );
							  pauseTransition1.play ( );


							  PauseTransition pauseTransition2 = new PauseTransition ( Duration.seconds ( 0.5 ) );
							  pauseTransition2.setOnFinished ( e1 -> {
									  progressStatusBar.setText ( "opening image.." );
								    }
							  );
							  pauseTransition2.play ( );


							  new VisibleComponents ( true , progressStatusBar , progressBar );
							  Timeline timeline = new Timeline (
								    new KeyFrame ( Duration.ZERO , new KeyValue ( progressBar.progressProperty ( ) , 0 ) ) ,
								    new KeyFrame ( Duration.seconds ( 0.5 ) , e -> {
									  // do anything you need here on completion...

								    } , new KeyValue ( progressBar.progressProperty ( ) , 1 ) )
							  );
							  timeline.setCycleCount ( 1 );
							  timeline.play ( );

							  try {

								Desktop.getDesktop ( ).open ( imgFile );
							  }
							  catch ( IOException ex ) {
								ex.printStackTrace ( );
							  }
						    }
						    else {
							  imageView.setImage ( null );
							  new VisibleComponents ( true , textArea );
							  new VisibleComponents ( false , imageVBox );
							  textArea.setText ( "Cannot Open The Image. \n Some Major Problems." );
						    }

						}
					  }
				    }
				} );
				imageView.setImage ( getValue ( ) );
			  }
		    } );

		} );
	  }

	  @Override
	  protected Task < Image > createTask ( )
	  {
		return new Task < Image > ( )
		{
		    @Override
		    protected Image call ( ) throws Exception
		    {
			  File file = new File ( absolutePath.trim ( ) );
			  Image image = new Image ( file.toURI ( ).toString ( ) );
			  return image;
		    }
		};
	  }
    }

    class setVideoService extends Service < MediaPlayer >
    {
	  public setVideoService ( )
	  {

		restart ( );
		setOnRunning ( e -> {

		} );
		setOnSucceeded ( e -> {
		    new VisibleComponents ( false , insideAnchorPane , imageVBox );
		    new VisibleComponents ( true , mediaVBox );
		    //		    mediaView.fitWidthProperty ( ).bind ( insideAnchorPane.widthProperty ( ) );
		    //		    mediaView.fitHeightProperty ( ).bind ( insideAnchorPane.heightProperty ( ) );


		    MediaPlayer mediaPlayer = getValue ( );
		    mediaView.setMediaPlayer ( mediaPlayer );
		    mediaPlayer.autoPlayProperty ( ).set ( true );
		    mediaPlayer.setVolume ( 20 );


		} );
	  }

	  @Override
	  protected Task < MediaPlayer > createTask ( )
	  {
		return new Task < MediaPlayer > ( )
		{
		    @Override
		    protected MediaPlayer call ( ) throws Exception
		    {
			  Media media = new Media ( new File ( absolutePath ).toURI ( ).toString ( ) );

			  MediaPlayer mediaplayer = new MediaPlayer ( media );
			  return mediaplayer;
		    }
		};
	  }
    }

    class VisibleComponents
    {
	  public VisibleComponents ( boolean flag , Node... n )
	  {
		int i = 0;
		while ( i < n.length ) {
		    n[ i ].setVisible ( flag );
		    i++;
		}
	  }
    }


}
