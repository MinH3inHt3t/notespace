package com.notespace.Page1.TabMode;

import com.notespace.Page1.Page1;
import com.notespace.TabMode.TabModeUI_Test;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import product_out.___Bundle;

import java.net.URL;
import java.util.ResourceBundle;

public class Page1Tab implements Initializable
{
    @FXML
    public AnchorPane cardModeAnchor, replacePage, tabmodeReplace;


    public Page1Tab ( )
    {

    }

    private void setPage1TocardModeAnchor ( )
    {

	  Page1 controller = new Page1 ( );
	  AnchorPane anchorPane = ( AnchorPane ) ___Bundle.__ViewLoader._getInstance ( )._load ( "Page1" , controller );
	  AnchorPane.setTopAnchor ( anchorPane , 0.0 );
	  AnchorPane.setRightAnchor ( anchorPane , 0.0 );
	  AnchorPane.setBottomAnchor ( anchorPane , 0.0 );
	  AnchorPane.setLeftAnchor ( anchorPane , 0.0 );
	  replacePage.getChildren ( ).add ( anchorPane );

    }

    private void setTabMode ( )
    {
	  Parent parent = new TabModeUI_Test ( ).startUI ( );
	  AnchorPane.setTopAnchor ( parent , 0.0 );
	  AnchorPane.setRightAnchor ( parent , 0.0 );
	  AnchorPane.setBottomAnchor ( parent , 0.0 );
	  AnchorPane.setLeftAnchor ( parent , 0.0 );
	  tabmodeReplace.getChildren ( ).add ( parent );
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {
	  this.setPage1TocardModeAnchor ( );
	  this.setTabMode ( );
    }
}
