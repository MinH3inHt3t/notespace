package com.notespace.MainPage.ChooseMenuComponents;

import com.notespace.FileHandler.UISave.MainUISettings;
import com.notespace.FileHandler.UISave.MainUISettingsStorage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LayoutMove
{
    public LayoutMove ( )
    {

    }

    public static void hideModeMenu ( Pane pane , VBox vb )
    {
	  MainUISettings mus = MainUISettingsStorage.readMainUISettings ( );
	  mus.mainPageModeMenuVisible = false;
	  MainUISettingsStorage.saveMainUISettings ( mus );

	  AnchorPane.setTopAnchor ( pane , 64.0 );
	  vb.setVisible ( false );

    }

    public static void showModeMenu ( Pane pane , VBox vb )
    {
	  MainUISettings mus = MainUISettingsStorage.readMainUISettings ( );
	  mus.mainPageModeMenuVisible = true;
	  MainUISettingsStorage.saveMainUISettings ( mus );


	  AnchorPane.setTopAnchor ( pane , 115.0 );
	  vb.setVisible ( true );

    }

}
