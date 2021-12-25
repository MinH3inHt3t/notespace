package com.notespace.TreeMode;

import com.notespace.FileHandler.FileBrowserCourier;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class FileBrowser extends Application
{

    SimpleDateFormat dateFormat = new SimpleDateFormat ( );
    NumberFormat numberFormat = NumberFormat.getIntegerInstance ( );

    Label label;
    TreeTableView < File > treeTableView;
    String folderPath = "D://NoteSpaceHere";


    public FileBrowser ( )
    {


    }

    public FileBrowser ( String folderPath )
    {
	  this.folderPath = folderPath;
    }

    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    public Parent startUI ( )
    {
	  label = new Label ( );

	  treeTableView = createFileBrowserTreeTableView ( );

	  treeTableView.setOnMouseEntered ( e -> {

	  } );

	  BorderPane layout = new BorderPane ( );
	  layout.setCenter ( treeTableView );
	  layout.setBottom ( label );
	  return layout;


    }

    public void startUINewWindow ( Stage stage )
    {
	  label = new Label ( );
	  treeTableView = createFileBrowserTreeTableView ( );

	  BorderPane layout = new BorderPane ( );
	  layout.setCenter ( treeTableView );
	  layout.setBottom ( label );

	  Scene scene = new Scene ( layout , 600 , 400 );
	  scene.getStylesheets ( ).add ( "sample//base.css" );
	  scene.getStylesheets ( ).add ( "sample//base_extras.css" );
	  scene.getStylesheets ( ).add ( "sample//panes.css" );
	  scene.getStylesheets ( ).add ( "sample//dark_theme.css" );
	  scene.getStylesheets ( ).add ( "sample//tabPane.css" );
	  stage.setScene ( scene );
	  stage.show ( );
    }


    @Override
    public void start ( Stage stage )
    {

	  startUINewWindow ( new Stage ( ) );
    }

    private TreeTableView < File > createFileBrowserTreeTableView ( )
    {
	  FileTreeItem root = new FileTreeItem ( new File ( folderPath ) );
	  final TreeTableView < File > treeTableView = new TreeTableView <> ( );
	  treeTableView.setShowRoot ( true );
	  treeTableView.setRoot ( root );
	  root.setExpanded ( true );
	  treeTableView.setColumnResizePolicy ( TreeTableView.CONSTRAINED_RESIZE_POLICY );

	  TreeTableColumn < File, FileTreeItem > nameColumn = new TreeTableColumn <> ( "Name" );

	  nameColumn.setCellValueFactory ( cellData ->
		    new ReadOnlyObjectWrapper < FileTreeItem > ( ( FileTreeItem ) cellData.getValue ( ) )
	  );
	  //	  Image image1 = getImageResource ( "Image/Diesel_productv2_heather_home_EGS_RockstarGames_RedDeadRedemption2_G1A_02-1920x1080-4b6978a5abd9d179eda514bc8853c8ffcdacd690.jpg" );
	  //	  Image image2 = getImageResource ( "Image/mg1.jpg" );
	  //	  Image image3 = getImageResource ( "Image/Diesel_productv2_heather_home_EGS_RockstarGames_RedDeadRedemption2_G1A_03-1920x1080-efec0a8683b70a14f5de2d281caad6267a85de44.jpg" );
	  nameColumn.setCellFactory ( column -> {
		TreeTableCell < File, FileTreeItem > cell = new TreeTableCell < File, FileTreeItem > ( )
		{


		    @Override
		    protected void updateItem ( FileTreeItem item , boolean empty )
		    {
			  super.updateItem ( item , empty );

			  if ( item == null || empty || item.getValue ( ) == null ) {
				setText ( null );
				setGraphic ( null );
				setStyle ( "" );
			  }
			  else {
				File f = item.getValue ( );
				String text = f.getParentFile ( ) == null ? File.separator : f.getName ( );
				setText ( text );
				String style = item.isHidden ( ) && f.getParentFile ( ) != null ? "-fx-accent" : "-fx-text-base-color";
//				setStyle ( "-fx-text-fill: " + style );
				//				if ( item.isLeaf ( ) ) {
				//				    setGraphic ( imageView1 );
				//				}
				//				else {
				//				    setGraphic ( item.isExpanded ( ) ? imageView2 : imageView3 );
				//				}
			  }
		    }
		};
		return cell;
	  } );

	  nameColumn.setPrefWidth ( 300 );
	  nameColumn.setSortable ( false );
	  treeTableView.getColumns ( ).add ( nameColumn );

	  //	  TreeTableColumn < File, String > sizeColumn = new TreeTableColumn <> ( "Size" );

	  //	  sizeColumn.setCellValueFactory ( cellData -> {
	  //		FileTreeItem item = ( ( FileTreeItem ) cellData.getValue ( ) );
	  //		String s = item.isLeaf ( ) ? numberFormat.format ( item.length ( ) ) : "";
	  //		return new ReadOnlyObjectWrapper < String > ( s );
	  //	  } );
	  //
	  //	  Callback < TreeTableColumn < File, String >, TreeTableCell < File, String > > sizeCellFactory = sizeColumn.getCellFactory ( );
	  //	  sizeColumn.setCellFactory ( column -> {
	  //		TreeTableCell < File, String > cell = sizeCellFactory.call ( column );
	  //		cell.setAlignment ( Pos.CENTER_RIGHT );
	  //		cell.setPadding ( new Insets ( 0 , 8 , 0 , 0 ) );
	  //		return cell;
	  //	  } );
	  //
	  //	  sizeColumn.setPrefWidth ( 100 );
	  //	  sizeColumn.setSortable ( false );
	  //	  treeTableView.getColumns ( ).add ( sizeColumn );

	  //	  TreeTableColumn < File, String > lastModifiedColumn = new TreeTableColumn <> ( "Last Modified" );
	  //	  lastModifiedColumn.setCellValueFactory ( cellData -> {
	  //		FileTreeItem item = ( FileTreeItem ) cellData.getValue ( );
	  //		String s = dateFormat.format ( new Date ( item.lastModified ( ) ) );
	  //		return new ReadOnlyObjectWrapper < String > ( s );
	  //	  } );
	  //
	  //	  lastModifiedColumn.setPrefWidth ( 130 );
	  //	  lastModifiedColumn.setSortable ( false );
	  //	  treeTableView.getColumns ( ).add ( lastModifiedColumn );


	  treeTableView.setOnMouseClicked ( e -> {

		TreeItem < File > text = treeTableView.getSelectionModel ( ).getSelectedItem ( );
		if ( text != null ) {
		    FileBrowserCourier.newSelectedFileControl = null;
		    FileBrowserCourier.newSelectedFileControl = text.getValue ( ).getAbsolutePath ( );
		}
		else {
		    FileBrowserCourier.newSelectedFileControl = null;
		}

	  } );

	  treeTableView.getSelectionModel ( ).selectedItemProperty ( ).addListener ( ( observable , oldValue , newValue ) -> {
		label.setText ( newValue != null ? newValue.getValue ( ).getAbsolutePath ( ) : "" );
		TreeItem < File > text = treeTableView.getSelectionModel ( ).getSelectedItem ( );
		if ( text != null ) {
		    FileBrowserCourier.newSelectedFileControl = null;
		    FileBrowserCourier.newSelectedFileControl = text.getValue ( ).getAbsolutePath ( );
		}
		else {
		    FileBrowserCourier.newSelectedFileControl = null;
		}

	  } );

	  treeTableView.getSelectionModel ( ).selectFirst ( );


	  return treeTableView;
    }

    private Image getImageResource ( String name )
    {
	  Image img = null;
	  try { img = new Image ( getClass ( ).getResourceAsStream ( name ) ); } catch ( Exception e ) { }
	  return img;
    }

    private class FileTreeItem extends TreeItem < File >
    {
	  private boolean expanded = false;
	  private boolean directory;
	  private boolean hidden;
	  private long length;
	  private long lastModified;

	  FileTreeItem ( File file )
	  {
		super ( file );
		EventHandler < TreeModificationEvent < File > > eventHandler = event -> changeExpand ( );
		addEventHandler ( TreeItem.branchExpandedEvent ( ) , eventHandler );
		addEventHandler ( TreeItem.branchCollapsedEvent ( ) , eventHandler );

		directory = getValue ( ).isDirectory ( );
		hidden = getValue ( ).isHidden ( );
		length = getValue ( ).length ( );
		lastModified = getValue ( ).lastModified ( );
	  }

	  private void changeExpand ( )
	  {
		if ( expanded != isExpanded ( ) ) {
		    expanded = isExpanded ( );
		    if ( expanded ) {
			  createChildren ( );
		    }
		    else {
			  getChildren ( ).clear ( );
		    }
		    if ( getChildren ( ).size ( ) == 0 ) {
			  Event.fireEvent ( this , new TreeItem.TreeModificationEvent <> ( TreeItem.valueChangedEvent ( ) , this , getValue ( ) ) );
		    }
		}
	  }

	  @Override
	  public boolean isLeaf ( )
	  {
		return ! isDirectory ( );
	  }

	  public boolean isDirectory ( ) { return directory; }

	  public long lastModified ( ) { return lastModified; }

	  public long length ( ) { return length; }

	  public boolean isHidden ( ) { return hidden; }

	  private void createChildren ( )
	  {
		if ( isDirectory ( ) && getValue ( ) != null ) {
		    File[] files = getValue ( ).listFiles ( );
		    if ( files != null && files.length > 0 ) {
			  getChildren ( ).clear ( );
			  for ( File childFile : files ) {
				getChildren ( ).add ( new FileTreeItem ( childFile ) );
			  }
			  getChildren ( ).sort ( ( ti1 , ti2 ) -> {
				return ( ( FileTreeItem ) ti1 ).isDirectory ( ) == ( ( FileTreeItem ) ti2 ).isDirectory ( ) ?
					  ti1.getValue ( ).getName ( ).compareToIgnoreCase ( ti2.getValue ( ).getName ( ) ) :
					  ( ( FileTreeItem ) ti1 ).isDirectory ( ) ? - 1 : 1;
			  } );
		    }
		}
	  }
    }  //end class FileTreeItem

}  //end class FileBrowser‌