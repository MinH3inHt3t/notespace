package com.notespace.TreeMode;

import com.notespace.FileHandler.*;
import com.notespace.Font.FontRepair;
import com.notespace.RecentNoteStorage;
import com.notespace.TabMode.Tabs.TabsUI_Test;
import com.ocpsoft.pretty.time.PrettyTime;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class TreeMode implements Initializable
{

    @FXML
    public TableColumn < CaptureModel, String > dateCol;
    @FXML
    public TableColumn < CaptureModel, String > captextCol;

    @FXML
    public AnchorPane treeModeAnchorPane, leftSideAnchorPane, tabReceiveAnchorPane, fileBrowserReceiveAnchorPane;
    @FXML
    BorderPane borderPane;
    @FXML
    CheckBox reverseListViewCheckBox;
    @FXML
    ListView listView;
    @FXML
    TextArea previewTextArea;
    @FXML
    Label replaceTextLabel;
    @FXML
    MenuBar menuBar;
    @FXML
    Button openNoteButton;
    @FXML
    MenuItem refreshMenuItem, clearHistoryMenuItem;
    @FXML
    Menu recentNoteSpaceMenu, detectNoteSpaceMenu, recentNoteMenu;
    String currentNoteSpace;
    ArrayList < TextObject > arrayList = new ArrayList <> ( );
    ArrayList < EditHistory > editHistoryArrayList = new ArrayList <> ( );
    ScheduledService < String > getSelectedFileFromFileBrowser = new ScheduledService < String > ( )
    {
	  @Override
	  protected Task < String > createTask ( )
	  {
		return new Task < String > ( )
		{
		    @Override
		    protected String call ( ) throws Exception
		    {

			  return FileBrowserCourier.newSelectedFileControl;
			  //			  return null;
		    }
		};
	  }
    };
    ScheduledService < String > getNoteSpaceService = new ScheduledService < String > ( )
    {
	  @Override
	  protected Task < String > createTask ( )
	  {
		return new Task < String > ( )
		{
		    @Override
		    protected String call ( ) throws Exception
		    {
			  return NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath;
		    }
		};
	  }
    };
    ScheduledService < ArrayList < TextObject > > getTextObjectListService = new ScheduledService < ArrayList < TextObject > > ( )
    {
	  @Override
	  protected Task < ArrayList < TextObject > > createTask ( )
	  {
		return new Task < ArrayList < TextObject > > ( )
		{
		    @Override
		    protected ArrayList < TextObject > call ( ) throws Exception
		    {
			  return FileHandlings.getTextObjectLists ( );
		    }
		};
	  }
    };
    Stage stageForFileBrowser;
    ArrayList < CaptureModel > captureModelArrayList = new ArrayList <> ( );
    @FXML
    private TableView < CaptureModel > tbData;

    public TreeMode ( )
    {

    }

    @FXML
    public void click ( )
    {
	  callFileBrowser ( );
    }

    @FXML
    public void refreshListView ( )
    {
	  new setEditHistoryToListView ( FileBrowserCourier.newSelectedFileControl );
    }

    @FXML
    public void closeLeftPanel ( )
    {
	  borderPane.setLeft ( null );
	  stageForFileBrowser = new Stage ( );
	  new FileBrowser ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath.trim ( ) ).startUINewWindow ( stageForFileBrowser );
    }

    @FXML
    public void openLeftPanel ( )
    {
	  borderPane.setLeft ( leftSideAnchorPane );
	  stageForFileBrowser.close ( );
	  callFileBrowser ( );

    }

    @FXML
    public void moveToTop ( )
    {
	  borderPane.setRight ( null );
	  borderPane.setTop ( new TabsUI_Test ( ).startUI ( ) );
    }

    @FXML
    public void moveToRight ( )
    {
	  borderPane.setLeft ( null );
	  borderPane.setRight ( leftSideAnchorPane );
    }

    @FXML
    public void moveToLeft ( )
    {
	  borderPane.setRight ( null );
	  borderPane.setLeft ( leftSideAnchorPane );
    }

    @FXML
    public void openNote ( )
    {
	  FileChooser fileChooser = new FileChooser ( );

	  fileChooser.setTitle ( "Upload File Path" );
	  fileChooser.getExtensionFilters ( ).addAll (
		    new FileChooser.ExtensionFilter ( "ALL FILES" , "*.*" ) ,
		    //			  new FileChooser.ExtensionFilter ( "ZIP" , "*.zip" ) ,
		    //			  new FileChooser.ExtensionFilter ( "PDF" , "*.pdf" ) ,
		    new FileChooser.ExtensionFilter ( "NoteSpaceNote" , ".ns" ) ,
		    new FileChooser.ExtensionFilter ( "TEXT" , "*.txt" ) ,
		    new FileChooser.ExtensionFilter ( "IMAGE FILES" , "*.jpg" , "*.png" , ".jpeg" )
	  );

	  File file = fileChooser.showOpenDialog ( new Stage ( ) );

	  if ( file != null ) {
		// pickUpPathField it's your TextField fx:id

		//		    new TabsUI_Test ( file.getPath () ).startUINewWindow ( new Stage () );
		//		    new NoteCardUI_Test ( file.getAbsolutePath ().trim ( ) ).startUINewWindow ( new Stage ( ) );

		//		new TabsUI_Test ( file.getAbsolutePath ( ).trim ( ) ).startUINewWindow ( new Stage ( ) );
		FileBrowserCourier.newSelectedFileControl = null;
		FileBrowserCourier.newSelectedFileControl = file.getAbsolutePath ( ).trim ( );
	  }
	  else {
		System.out.println ( "error" ); // or something else
	  }
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {

	  servicesAction ( );
	  perform ( );
	  prepare ( );
    }

    private void servicesAction ( )
    {
	  getSelectedFileFromFileBrowser.restart ( );
	  getSelectedFileFromFileBrowser.setPeriod ( Duration.seconds ( 0.5 ) );
	  getSelectedFileFromFileBrowser.setOnSucceeded ( e -> {

		//		System.out.println ( "old " + oldSelectedFileControl + "  " + "new " + newSelectedFileControl );

		if ( FileBrowserCourier.oldSelectedFileControl != null ) {
		    if ( ! FileBrowserCourier.oldSelectedFileControl.equals ( FileBrowserCourier.newSelectedFileControl ) ) {
			  //			  oldSelectedFileControl = getSelectedFileFromFileBrowser.getValue ( );
			  //			  setTabsToReceiveAnchor ( getSelectedFileFromFileBrowser.getValue ( ) );

			  FileBrowserCourier.oldSelectedFileControl = null;
			  FileBrowserCourier.oldSelectedFileControl = FileBrowserCourier.newSelectedFileControl;
			  setTabsToReceiveAnchor ( FileBrowserCourier.newSelectedFileControl );

		    }
		}
		else {
		    int i = 0; //work only once
		    if ( i == 0 ) {
			  FileBrowserCourier.oldSelectedFileControl = null;
			  FileBrowserCourier.oldSelectedFileControl = getSelectedFileFromFileBrowser.getValue ( );
			  setTabsToReceiveAnchor ( getSelectedFileFromFileBrowser.getValue ( ) );
			  i++;
		    }
		}
	  } );

	  getTextObjectListService.restart ( );
	  getTextObjectListService.setPeriod ( Duration.seconds ( 3 ) );
	  getTextObjectListService.setOnSucceeded ( e -> {
		if ( arrayList.size ( ) != getTextObjectListService.getValue ( ).size ( ) || ! currentNoteSpace.equals ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath ) ) {

		    currentNoteSpace = NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath;
		    Collections.sort ( arrayList , new Sorting.LatestModifiedDate ( ) );
		    arrayList = getTextObjectListService.getValue ( );

		    callFileBrowser ( );
		}
	  } );


	  //	  getNoteSpaceService.restart ( );
	  //	  getNoteSpaceService.setPeriod ( Duration.seconds ( 5 ) );
	  //	  getNoteSpaceService.setOnSucceeded ( e -> {
	  //		if ( currentNoteSpace != null ) {
	  //		    if ( ! currentNoteSpace.equals ( getNoteSpaceService.getValue ( ) ) ) {
	  //			  currentNoteSpace = getNoteSpaceService.getValue ( );
	  //			  callFileBrowser ( );
	  //		    }
	  //		}
	  //		else {
	  //		    int i = 0; //work only once
	  //		    if ( i == 0 ) {
	  //			  currentNoteSpace = getNoteSpaceService.getValue ( );
	  //			  callFileBrowser ( );
	  //			  i++;
	  //		    }
	  //		}
	  //	  } );

    }

    public void perform ( )
    {


	  replaceTextLabel.setOnMouseClicked ( e -> {
		if ( e.getButton ( ).equals ( MouseButton.PRIMARY ) ) {
		    try {
			  FileHandlings.FileWrite ( FileBrowserCourier.newSelectedFileControl , previewTextArea.getText ( ).trim ( ) );
		    }
		    catch ( IOException ex ) {
			  ex.printStackTrace ( );
		    }

		    TextObject textObject = null;
		    try {
			  textObject = FileHandlings.buildObject ( FileBrowserCourier.newSelectedFileControl );
		    }
		    catch ( IOException ex ) {
			  ex.printStackTrace ( );
		    }

		    if ( textObject != null ) {
			  CaptureTextStorage.addCaptureText ( new CaptureText ( textObject.getAbsolutePath ( ) , textObject.getText ( ) ) );
		    }

		}
	  } );

	  menuBar.setOnMouseEntered ( e -> {
		new setRecentNoteSpaceToMenu ( );
		//		new setDetectPartitionToMenu ( );
		new setRecentNoteToMenu ( );
	  } );

	  refreshMenuItem.setOnAction ( e -> {
		new setEditHistoryToListView ( FileBrowserCourier.newSelectedFileControl );
	  } );

	  clearHistoryMenuItem.setOnAction ( e ->
	  {
		EditHistoryStorage.delete_EditHistory ( FileBrowserCourier.newSelectedFileControl );
		new setEditHistoryToListView ( FileBrowserCourier.newSelectedFileControl );
	  } );

	  reverseListViewCheckBox.selectedProperty ( ).addListener (
		    ( ObservableValue < ? extends Boolean > ov , Boolean old_val , Boolean new_val ) -> {
			  if ( new_val ) {
				Collections.reverse ( editHistoryArrayList );
				bindtoListView ( );
			  }
			  else {
				Collections.reverse ( editHistoryArrayList );
				bindtoListView ( );
			  }
		    } );


	  listView.setOnMouseClicked ( e ->
	  {
		if ( listView.getSelectionModel ( ).getSelectedItem ( ) != null ) {
		    Label selectedLabel = ( Label ) listView.getSelectionModel ( ).getSelectedItem ( );
		    EditHistory st = ( EditHistory ) selectedLabel.getUserData ( );
		    if ( st != null ) {
			  replaceTextLabel.setVisible ( true );
			  previewTextArea.setText ( FontRepair.fixmyanamrfont ( st.text.trim ( ) ) );
		    }
		}

	  } );

	  listView.getSelectionModel ( ).selectedItemProperty ( ).addListener ( ( observable , oldValue , newValue ) -> {
		if ( listView.getSelectionModel ( ).getSelectedItem ( ) != null ) {
		    Label selectedLabel = ( Label ) listView.getSelectionModel ( ).getSelectedItem ( );
		    EditHistory st = ( EditHistory ) selectedLabel.getUserData ( );
		    if ( st != null ) {
			  replaceTextLabel.setVisible ( true );
			  previewTextArea.setText ( FontRepair.fixmyanamrfont ( st.text.trim () ) );
		    }

		}
	  } );

	  listView.setOnMouseEntered ( e -> {
		if ( reverseListViewCheckBox.isSelected ( ) == false ) {
		    new setEditHistoryToListView ( FileBrowserCourier.newSelectedFileControl );
		}
	  } );

	  tbData.setOnMouseEntered ( e -> {
		new setCaptureToTableView ( FileBrowserCourier.newSelectedFileControl );
	  } );

	  tbData.getSelectionModel ( ).selectedItemProperty ( ).addListener ( ( observable , oldValue , newValue ) -> {
		if ( tbData.getSelectionModel ( ).getSelectedItem ( ) != null ) {
		    CaptureModel st = ( CaptureModel ) tbData.getSelectionModel ( ).getSelectedItem ( );
		    if ( st != null ) {
			  previewTextArea.setText ( st.getCaptureText ( ) );
		    }

		}
	  } );

	  tbData.setOnMouseClicked ( e -> {
		if ( tbData.getSelectionModel ( ).getSelectedItem ( ) != null ) {

		    CaptureModel st = ( CaptureModel ) tbData.getSelectionModel ( ).getSelectedItem ( );
		    if ( st != null ) {
			  replaceTextLabel.setVisible ( true );
			  previewTextArea.setText ( st.getCaptureText ( ) );
		    }

		}
	  } );

	  treeModeAnchorPane.setOnMouseEntered ( e -> {
		getSelectedFileFromFileBrowser.restart ( );
		getTextObjectListService.restart ( );
	  } );


	  openNoteButton.setOnAction ( e -> {
		openNote ( );
	  } );

	  //	  treeModeAnchorPane.setOnMouseExited ( e -> {
	  //		getSelectedFileFromFileBrowser.cancel ( );
	  //		getTextObjectListService.cancel ();
	  //	  } );

	  //	  fileBrowserReceiveAnchorPane.setOnMouseEntered ( e -> {
	  //		getNoteSpaceService.restart ( );
	  //
	  //	  } );
	  //	  fileBrowserReceiveAnchorPane.setOnMouseExited ( e -> {
	  //		getNoteSpaceService.cancel ( );
	  //	  } );

    }

    public void prepare ( )
    {


	  shortCutsAdded ( );

	  callFileBrowser ( );

	  new setRecentNoteSpaceToMenu ( );
	  new setDetectPartitionToMenu ( );
	  new setRecentNoteToMenu ( );

    }

    private void callFileBrowser ( )
    {
	  fileBrowserReceiveAnchorPane.getChildren ( ).clear ( );
	  Parent parent = new FileBrowser ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath.trim ( ) ).startUI ( );
	  AnchorPane.setTopAnchor ( parent , 0.0 );
	  AnchorPane.setRightAnchor ( parent , 0.0 );
	  AnchorPane.setBottomAnchor ( parent , 0.0 );
	  AnchorPane.setLeftAnchor ( parent , 0.0 );

	  fileBrowserReceiveAnchorPane.getChildren ( ).add ( parent );
    }

    private void setTabsToReceiveAnchor ( String absolutePath )
    {
	  previewTextArea.clear ( );
	  new setEditHistoryToListView ( absolutePath );
	  new setCaptureToTableView ( absolutePath );

	  tabReceiveAnchorPane.getChildren ( ).clear ( );
	  replaceTextLabel.setVisible ( false );

	  Parent parent = new TabsUI_Test ( absolutePath ).startUI ( );
	  AnchorPane.setTopAnchor ( parent , 0.0 );
	  AnchorPane.setRightAnchor ( parent , 0.0 );
	  AnchorPane.setBottomAnchor ( parent , 0.0 );
	  AnchorPane.setLeftAnchor ( parent , 0.0 );

	  tabReceiveAnchorPane.getChildren ( ).add ( parent );
    }

    private void shortCutsAdded ( )
    {
	  Platform.runLater ( new Runnable ( )
	  {
		@Override
		public void run ( )
		{

		    Scene scene = treeModeAnchorPane.getScene ( );
		    scene.getAccelerators ( ).put (
				new KeyCodeCombination ( KeyCode.O , KeyCombination.ALT_ANY ) ,
				new Runnable ( )
				{
				    @FXML
				    public void run ( )
				    {
					  openNote ( );
				    }
				}
		    );
		}
	  } );
    }

    private void bindtoListView ( )
    {
	  if ( editHistoryArrayList.size ( ) == 0 ) {
		listView.getItems ( ).clear ( );
		Label label = new Label ( "No Edit History" );
		label.setUserData ( null );
		label.setDisable ( true );
		listView.getItems ( ).add ( label );
	  }
	  else {
		listView.getItems ( ).clear ( );
		editHistoryArrayList.stream ( ).forEach ( e1 -> {
		    SimpleDateFormat dateFormat = new SimpleDateFormat ( "MM/dd/yy HH:mm:ss a" );
		    Date date = new Date ( e1.date );

		    PrettyTime p = new PrettyTime ( );
		    Label label = new Label ( dateFormat.format ( date ) + "   " + p.format ( date ) );
		    label.setUserData ( e1 );
		    listView.getItems ( ).add ( label );

		} );
	  }

    }

    class setDetectPartitionToMenu extends Service < ArrayList < String > >
    {

	  public setDetectPartitionToMenu ( )
	  {
		restart ( );
		setOnSucceeded ( e -> {
		    detectNoteSpaceMenu.getItems ( ).clear ( );
		    Menu cmi = new Menu ( "C:\\ can't use for detect" );
		    cmi.setDisable ( true );
		    detectNoteSpaceMenu.getItems ( ).add ( cmi );
		    getValue ( ).stream ( ).forEach ( partition -> {
			  Menu m = new Menu ( partition );
			  detectNoteSpaceMenu.getItems ( ).add ( m );
			  new setDetectNoteSpaceToMenu ( partition , m );

		    } );

		} );
	  }

	  @Override
	  protected Task < ArrayList < String > > createTask ( )
	  {
		return new Task < ArrayList < String > > ( )
		{
		    @Override
		    protected ArrayList < String > call ( ) throws Exception
		    {
			  return BuildNoteSpace.getPartitionsList ( );
		    }
		};
	  }
    }

    class setDetectNoteSpaceToMenu extends Service < ArrayList < String > >
    {

	  String partition;


	  public setDetectNoteSpaceToMenu ( String partition , Menu menu )
	  {
		this.partition = partition;
		restart ( );
		setOnRunning ( e -> {
		    menu.setText ( partition + " detecting notespace" );
		    menu.setDisable ( true );
		} );
		setOnSucceeded ( e -> {
		    getValue ( ).stream ( ).forEach ( e1 -> {

			  menu.setText ( partition );
			  menu.setDisable ( false );
			  String text = e1.equals ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath ) ? e1 + " in use" : e1;
			  MenuItem mi = new MenuItem ( text );
			  mi.setOnAction ( e2 -> {
				NoteSpacePathStorage.SaveNoteSpacePath ( new NoteSpacePath ( "1" , e1 ) );
			  } );
			  menu.getItems ( ).add ( mi );

		    } );

		} );
	  }


	  @Override
	  protected Task < ArrayList < String > > createTask ( )
	  {
		return new Task < ArrayList < String > > ( )
		{
		    @Override
		    protected ArrayList < String > call ( ) throws Exception
		    {
			  return BuildNoteSpace.getDetectedNoteSpacePath ( partition );
		    }
		};
	  }
    }

    class setRecentNoteToMenu extends Service < ArrayList < RecentNote > >
    {

	  public setRecentNoteToMenu ( )
	  {
		restart ( );
		setOnSucceeded ( e -> {

		    ArrayList < RecentNote > arrayList = getValue ( );

		    recentNoteMenu.getItems ( ).clear ( );
		    Collections.sort ( arrayList , new Comparator < RecentNote > ( )
		    {
			  public int compare ( RecentNote o1 , RecentNote o2 )
			  {
				return ( int ) ( o2.date - o1.date );
			  }
		    } );
		    arrayList.stream ( ).forEach ( recentNote -> {


			  Menu menu = new Menu ( recentNote.notePath.trim ( ) );
			  menu.setOnAction ( e1 -> {

			  } );

			  MenuItem openRecentNoteMenuItem = new MenuItem ( "Open file" );
			  openRecentNoteMenuItem.setOnAction ( e1 -> {
				FileBrowserCourier.newSelectedFileControl = null;
				FileBrowserCourier.newSelectedFileControl = recentNote.notePath.trim ( );
			  } );

			  MenuItem removeRecentNoteMenuItem = new MenuItem ( "Remove this note from recent" );
			  removeRecentNoteMenuItem.setOnAction ( e1 -> {
				RecentNoteStorage.deleteRecentNote ( recentNote.notePath.trim ( ) );
			  } );

			  menu.getItems ( ).addAll ( openRecentNoteMenuItem , removeRecentNoteMenuItem );

			  //			  if ( recentNote.notePath.trim ( ).equals ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath.trim ( ) ) ) {
			  //				menu.setText ( recentNote.notePath.trim ( ) + " ( Folder Opened )" );
			  //				menu.setDisable ( true );
			  //			  }


			  File file = new File ( recentNote.notePath.trim ( ) );
			  if ( file.exists ( ) ) {

				if ( file.isDirectory ( ) ) {
				    menu.setText ( recentNote.notePath.trim ( ) + " ( Folder )" );
				    openRecentNoteMenuItem.setText ( "Open Folder" );
				    openRecentNoteMenuItem.setOnAction ( e1 -> {
					  try {
						Desktop.getDesktop ( ).open ( file );
					  }
					  catch ( IOException ex ) {
						ex.printStackTrace ( );
					  }
				    } );

				    removeRecentNoteMenuItem.setText ( "Remove this folder from recent" );

				}
			  }
			  else {
				openRecentNoteMenuItem.setVisible ( false );
				menu.setText ( "( " + recentNote.notePath.trim ( ) + " )" + " is missing in your system." );
			  }


			  menu.setOnAction ( e1 -> {
				//				NoteSpacePathStorage.SaveNoteSpacePath ( new NoteSpacePath ( UUID.randomUUID ( ).toString ( ) , recentNoteSpace.spaceName.trim ( ) ) );

			  } );
			  recentNoteMenu.getItems ( ).add ( menu );


		    } );
		    MenuItem clearMenuItem = new MenuItem ( "Clear All Recent Notes" );
		    clearMenuItem.setOnAction ( e1 -> {
			  arrayList.stream ( ).forEach ( strem -> {
				RecentNoteStorage.deleteRecentNote ( strem.notePath );
			  } );
		    } );
		    recentNoteMenu.getItems ( ).add ( clearMenuItem );

		} );
	  }

	  @Override
	  protected Task < ArrayList < RecentNote > > createTask ( )
	  {
		return new Task < ArrayList < RecentNote > > ( )
		{
		    @Override
		    protected ArrayList < RecentNote > call ( ) throws Exception
		    {
			  return RecentNoteStorage.getRecentNoteList ( );
		    }
		};
	  }


    }

    class setRecentNoteSpaceToMenu extends Service < ArrayList < RecentNoteSpace > >
    {


	  public setRecentNoteSpaceToMenu ( )
	  {
		restart ( );
		setOnSucceeded ( e -> {
		    recentNoteSpaceMenu.getItems ( ).clear ( );
		    getValue ( ).stream ( ).forEach ( recentNoteSpace -> {


			  MenuItem menuItem = new MenuItem ( );
			  menuItem.setText ( recentNoteSpace.spaceName.trim ( ) );


			  if ( recentNoteSpace.spaceName.trim ( ).equals ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath.trim ( ) ) ) {
				menuItem.setText ( recentNoteSpace.spaceName.trim ( ) + " ( in use )" );
			  }
			  File file = new File ( recentNoteSpace.spaceName.trim ( ) );
			  if ( file.exists ( ) ) {
				menuItem.setDisable ( false );
			  }
			  else {
				menuItem.setDisable ( true );
				menuItem.setText ( recentNoteSpace.spaceName.trim ( ) + " is missing in your system." );
			  }

			  //			  			  if ( RecentNoteSpaceStorage.checkIfPathExist ( recentNoteSpace.spaceName.trim ( ) ) ) {
			  //			  				menuItem.setDisable ( true );
			  //			  			  }
			  //			  			  else {
			  //			  				menuItem.setDisable ( false );
			  //
			  //			  			  }


			  menuItem.setOnAction ( e1 -> {
				NoteSpacePathStorage.SaveNoteSpacePath ( new NoteSpacePath ( UUID.randomUUID ( ).toString ( ) , recentNoteSpace.spaceName.trim ( ) ) );
			  } );
			  recentNoteSpaceMenu.getItems ( ).add ( menuItem );


		    } );

		} );
	  }

	  @Override
	  protected Task < ArrayList < RecentNoteSpace > > createTask ( )
	  {
		return new Task < ArrayList < RecentNoteSpace > > ( )
		{
		    @Override
		    protected ArrayList < RecentNoteSpace > call ( ) throws Exception
		    {
			  return RecentNoteSpaceStorage.getRecentNoteSpaceList ( );
		    }
		};
	  }


    }

    class setEditHistoryToListView extends Service < ArrayList < EditHistory > >
    {

	  String filePath;

	  public setEditHistoryToListView ( String filePath )
	  {

		this.filePath = filePath;

		restart ( );
		setOnSucceeded ( e -> {


		    editHistoryArrayList = getValue ( );
		    Collections.reverse ( editHistoryArrayList );

		    bindtoListView ( );

		} );

	  }

	  @Override
	  protected Task < ArrayList < EditHistory > > createTask ( )
	  {
		return new Task < ArrayList < EditHistory > > ( )
		{
		    @Override
		    protected ArrayList < EditHistory > call ( ) throws Exception
		    {
			  return EditHistoryStorage.getEditHistoryListByFilePath ( filePath );
		    }
		};
	  }
    }

    class setCaptureToTableView extends Service < ArrayList < CaptureText > >
    {

	  String filePath;

	  public setCaptureToTableView ( String filePath )
	  {
		this.filePath = filePath;

		restart ( );
		setOnSucceeded ( e -> {
		    captureModelArrayList.clear ( );
		    getValue ( ).stream ( ).forEach ( captureText -> {
			  captureModelArrayList.add ( new CaptureModel ( captureText.getCaptureText ( ) , captureText.getDate ( ) ) );
		    } );

		    captureModelArrayList.stream ( ).forEach ( System.out :: println );

		    ObservableList < CaptureModel > studentsModels = FXCollections.observableArrayList (
				captureModelArrayList );
		    //make sure the property value factory should be exactly same as the e.g getStudentId from your model class
		    //		    dateCol.setCellValueFactory ( new PropertyValueFactory <> ( "date" ) );
		    //		    captextCol.setCellValueFactory ( new PropertyValueFactory <> ( "captureText" ) );
		    dateCol.setCellValueFactory ( new PropertyValueFactory < CaptureModel, String > ( "date" ) );
		    captextCol.setCellValueFactory ( new PropertyValueFactory < CaptureModel, String > ( "captureText" ) );

		    //add your data to the table here.
		    tbData.setItems ( studentsModels );

		    captextCol.setCellFactory ( tc -> {
			  TableCell < CaptureModel, String > cell = new TableCell <> ( );
			  Text text = new Text ( );
			  cell.setGraphic ( text );
			  cell.setPrefHeight ( Control.USE_COMPUTED_SIZE );
			  text.wrappingWidthProperty ( ).bind ( captextCol.widthProperty ( ) );
			  text.textProperty ( ).bind ( cell.itemProperty ( ) );
			  return cell;
		    } );
		} );
	  }

	  @Override
	  protected Task < ArrayList < CaptureText > > createTask ( )
	  {
		return new Task < ArrayList < CaptureText > > ( )
		{
		    @Override
		    protected ArrayList < CaptureText > call ( ) throws Exception
		    {

			  return CaptureTextStorage.getCaptureTextListByFilePath ( filePath );
		    }
		};
	  }
    }


}
