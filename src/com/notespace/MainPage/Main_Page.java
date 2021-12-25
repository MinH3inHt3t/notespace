package com.notespace.MainPage;


import com.notespace.CLipBoard;
import com.notespace.Detect.AutoDetect;
import com.notespace.FileHandler.*;
import com.notespace.FileHandler.UISave.MainUISettings;
import com.notespace.FileHandler.UISave.MainUISettingsStorage;
import com.notespace.FileHandler.UISplitMemory.SplitUIMemory;
import com.notespace.FileHandler.UISplitMemory.SplitUIMemoryStorage;
import com.notespace.FileHandler.XMLPack.TempAddNewNote;
import com.notespace.FileHandler.XMLPack.TempMainPage;
import com.notespace.Font.FontRepair;
import com.notespace.MainPage.ChooseMenuComponents.LayoutMove;
import com.notespace.Minimalist.MinimalistPageUI_Test;
import com.notespace.NoteCard.NoteCardUI_Test;
import com.notespace.Page1.Page1UI_Test;
import com.notespace.RecentNoteStorage;
import com.notespace.Settings.DateSettings;
import com.notespace.Settings.SettingsPage;
import com.notespace.TabMode.TabModeUI_Test;
import com.notespace.TabMode.Tabs.TabsUI_Test;
import com.notespace.TreeMode.TreeModeUI_Test;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import product_out.___Bundle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.UUID;

public class Main_Page implements Initializable {
    public static double splitPane1Value = 0;
    public String tempBgColor;
    public String tempTextColor;
    @FXML
    public AnchorPane innerAnchor, replaceMainContent;
    /**
     * Sun property pointing the main class and its arguments.
     * Might not be defined on non Hotspot VM implementations.
     */
    String[] noteExtensions = {".txt", ".ns"};
    String[] imageExtensions = {".png", ".jpg", ".jpeg", ".webp"};
    String[] videoExtensions = {".mp4"};
    boolean PopupEditable = true;

    @FXML
    TextField txtSearch, filenametextfield;
    @FXML
    TextArea addTextArea;
    @FXML
    Label clearSearch, closeAddNotePaneLabel, saveIconLabel, saveLabel, TextColorLabel, countnoLabel, limitnoLabel;
    @FXML
    AnchorPane mainPageAnchor, changeStageReceiver, searchResultAnchor, addNewNoteAnchor;
    @FXML
    HBox saveHBox;
    @FXML
    VBox resultVBox;
    @FXML
    Button btn;
    @FXML
    Circle bg_circle1, bg_circle2, bg_circle3, bg_circle4, bg_circle5, bg_circle6;
    @FXML
    Circle txt_circle1, txt_circle2, txt_circle3, txt_circle4, txt_circle5, txt_circle6;
    @FXML
    ColorPicker BackgroundcolorPicker, TextcolorPicker;
    @FXML
    Label setingsLabel, plusLabel, goprevipane, clearlabel, savefileLabel;
    @FXML
    AnchorPane previpane, nextpane;
    @FXML
    CheckBox generatecheckbox;
    @FXML
    VBox modeMenuVBox;
    @FXML
    Label hideModeMenuLabel;

    @FXML
    MenuBar menuBar;
    @FXML
    Menu recentNoteSpaceMenu, recentNoteMenu, detectNoteSpaceMenu;
    @FXML
    MenuItem newTextMenuItem, openTextMenuItem, settingsMenuItem, closeMenuBarMenuItem, openModeMenuMenuItem;

    @FXML
    HBox modeMenuHBox, cardModeHBox, tabModeHBox, minimalistModeHBox;
    @FXML
    Label cardModeLabel, cardModeReplaceMainContentLabel, cardModeNewWindowLabel;
    @FXML
    Label tabModeLabel, tabModeReplaceMainContentLabel, tabModeNewWindowLabel;

    @FXML
    Label minimalistModeLabel, minimalistModeReplaceMainContentLabel, minimalistModeNewWindowLabel;
    //    @FXML
    //    SplitPane splitPane;
    private Service<String> SpacePath_Service = new Service() {
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
                }
            };
        }
    };

    public Main_Page() {

    }

    private static String getFileExtension(File file) {
        String extension = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = "";
        }

        return extension;

    }

    @FXML
    public void addBgImage() {
        SystemConfig config = SystemConfigStorage.ReadSettings();
        config.isBackgroundImageSet = true;
        SystemConfigStorage.SaveSystemConfig(config);
    }

    @FXML
    public void removeBgImage() {
        SystemConfig config = SystemConfigStorage.ReadSettings();
        config.isBackgroundImageSet = false;
        SystemConfigStorage.SaveSystemConfig(config);
    }

    @FXML
    public void showModeMenu() {
        LayoutMove.showModeMenu(replaceMainContent, modeMenuVBox);
    }

    @FXML
    public void hideModeMenu() throws IOException {
        LayoutMove.hideModeMenu(replaceMainContent, modeMenuVBox);
        //	  this.callTreeMode ();

        //	  new TabModeUI_Test ( ).startUINewWindow ( new Stage ( ) );


        //	  	  Platform.runLater ( new Runnable ( )
        //	  	  {
        //	  		@Override
        //	  		public void run ( )
        //	  		{
        //	  		    NoteCardTab controller = new NoteCardTab ( );
        //	  		    Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "NoteCardTab" , controller );
        //	  		    AnchorPane.setTopAnchor ( parent , 0.0D );
        //	  		    AnchorPane.setBottomAnchor ( parent , 0.0D );
        //	  		    AnchorPane.setLeftAnchor ( parent , 0.0D );
        //	  		    AnchorPane.setRightAnchor ( parent , 0.0D );
        //	  		    Tab tab = new Tab ( "Notes ( CURRENT FOLDER )" );
        //	  		    tab.setContent ( parent );
        //	  		    tab.setClosable ( true );
        //	  		    tabpane.getTabs ( ).add ( tab );
        //	  		}
        //	  	  } );


    }

    private void ServicesRepo() {
        this.setSpacePath_Service();
    }

    private void setSpacePath_Service() {
        this.SpacePath_Service.setOnSucceeded(e -> {
            btn.setText(SpacePath_Service.getValue());
        });
    }

    private void StartServices() {
        this.StartSpacePathService();
    }

    private void StartSpacePathService() {
        this.SpacePath_Service.restart();
    }

    private void CancelServices() {
        this.CancelSpacePathService();
    }

    private void CancelSpacePathService() {
        this.SpacePath_Service.cancel();
    }

    private void callNoteFromExternalPage() {
        //	  TabMode controller = new TabMode ( );
        //	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "TabMode" , controller );
        //
        //	  AnchorPane.setTopAnchor ( parent , 0.0D );
        //	  AnchorPane.setBottomAnchor ( parent , 0.0D );
        //	  AnchorPane.setLeftAnchor ( parent , 0.0D );
        //	  AnchorPane.setRightAnchor ( parent , 0.0D );
        //	  notefromexternalPlacePage.getChildren ( ).add ( parent );
    }

    private void cardModeHBoxFocused() {
        clearFocus();
        cardModeHBox.getStyleClass().add("modeHBox-active");
    }

    private void tabModeHBoxFocused() {
        clearFocus();
        tabModeHBox.getStyleClass().add("modeHBox-active");
    }


    private void minimalistModeHBoxFocused() {
        clearFocus();
        minimalistModeHBox.getStyleClass().add("modeHBox-active");

    }

    private void clearFocus() {
        Arrays.stream(new HBox[]{cardModeHBox, tabModeHBox, minimalistModeHBox}).forEach(e -> {
            e.getStyleClass().remove("modeHBox-active");
        });
    }

    private void shortCutsAdded() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Scene scene = mainPageAnchor.getScene();
                scene.getAccelerators().put(
                        new KeyCodeCombination(KeyCode.F2),
                        new Runnable() {
                            @FXML
                            public void run() {
                                openModeMenuMenuItem.fire();
                            }
                        }
                );
                scene.getAccelerators().put(
                        new KeyCodeCombination(KeyCode.F1),
                        new Runnable() {
                            @FXML
                            public void run() {
                                closeMenuBarMenuItem.fire();
                            }
                        }
                );


            }
        });
    }


    private void UI_Perform() {

        //	  cardModeHBox.setUserData ( 1 );
        //	  tabModeHBox.setUserData ( 2 );
        //	  treeModeHBox.setUserData ( 3 );

        cardModeLabel.setUserData(1);
        cardModeReplaceMainContentLabel.setUserData(11);
        cardModeNewWindowLabel.setUserData(111);

        tabModeLabel.setUserData(2);
        tabModeReplaceMainContentLabel.setUserData(22);
        tabModeNewWindowLabel.setUserData(222);


        minimalistModeLabel.setUserData(3);
        minimalistModeReplaceMainContentLabel.setUserData(33);
        minimalistModeNewWindowLabel.setUserData(333);


        cardModeHBoxFocused();
        this.callPage1ToMainContent();
        //	  tabModeHBoxFocused ( );
        //	  this.callTabModeToMainContent ( );
        //	  treeModeHBoxFocused ( );
        //	  this.callTreeModeToMainContent ( );

        //	  this.callMinimalistToMainContent ( );


        Arrays.stream(new Label[]{cardModeLabel, tabModeLabel, minimalistModeLabel}).forEach(e -> {
            e.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    int i = (int) e.getUserData();
                    switch (i) {
                        case 1:      //card mode
                            cardModeHBoxFocused();
                            this.callPage1ToMainContent();
                            break;
                        case 2:      //tab mode
                            tabModeHBoxFocused();
                            this.callTabModeToMainContent();
                            break;
                        case 3:      //minimalist mode
                            minimalistModeHBoxFocused();
                            this.callMinimalistModeToMainContent();
                            break;


                        default:
                    }
                }
            });
        }); //

        Arrays.stream(new Label[]{cardModeReplaceMainContentLabel, cardModeNewWindowLabel, tabModeReplaceMainContentLabel, tabModeNewWindowLabel, minimalistModeLabel, minimalistModeReplaceMainContentLabel, minimalistModeNewWindowLabel}).forEach(e -> {
            e.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    int i = (int) e.getUserData();
                    switch (i) {
                        case 11:
                            cardModeHBoxFocused();
                            this.callPage1ToMainContent();
                            break;
                        case 111:
                            new Page1UI_Test().startUINewWindow(new Stage(), true);
                            break;
                        case 22:
                            tabModeHBoxFocused();
                            this.callTabModeToMainContent();
                            break;
                        case 222:
                            new TabModeUI_Test().startUINewWindow(new Stage());
                            break;
                        case 33:
                            minimalistModeHBoxFocused();
                            this.callMinimalistModeToMainContent();
                            break;
                        case 333:
                            new MinimalistPageUI_Test().startUINewWindow(new Stage());
                            break;


                        default:
                    }
                }
            });
        });

        hideModeMenuLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                LayoutMove.hideModeMenu(replaceMainContent, modeMenuVBox);
            }
        });

        closeMenuBarMenuItem.setOnAction(e -> {

            if (menuBar.isVisible()) {
                menuBar.setVisible(false);
                MainUISettings mus = MainUISettingsStorage.readMainUISettings();
                mus.mainPageMenuBarVisible = false;
                MainUISettingsStorage.saveMainUISettings(mus);
            } else {
                menuBar.setVisible(true);
                MainUISettings mus = MainUISettingsStorage.readMainUISettings();
                mus.mainPageMenuBarVisible = true;
                MainUISettingsStorage.saveMainUISettings(mus);
            }
        });

        openModeMenuMenuItem.setOnAction(e -> {
            if (modeMenuVBox.isVisible()) {
                LayoutMove.hideModeMenu(replaceMainContent, modeMenuVBox);

            } else {
                LayoutMove.showModeMenu(replaceMainContent, modeMenuVBox);

            }
        });


        menuBar.setOnMouseEntered(e -> {
            new RecentNoteSpaceInsertData();
            new RecentNoteDataInsert();
        });


        KeyCombination openTextKeyCombination = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
        openTextMenuItem.setAccelerator(openTextKeyCombination);
        openTextMenuItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Upload File Path");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("ALL FILES", "*.*"),
                    //			  new FileChooser.ExtensionFilter ( "ZIP" , "*.zip" ) ,
                    //			  new FileChooser.ExtensionFilter ( "PDF" , "*.pdf" ) ,
                    new FileChooser.ExtensionFilter("NoteSpaceNote", ".ns"),
                    new FileChooser.ExtensionFilter("TEXT", "*.txt"),
                    new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", ".jpeg")
            );

            File file = fileChooser.showOpenDialog(new Stage());

            if (file != null) {
                // pickUpPathField it's your TextField fx:id
                System.out.println(file.getAbsolutePath());
                //		    new TabsUI_Test ( file.getPath () ).startUINewWindow ( new Stage () );
                //		    new NoteCardUI_Test ( file.getAbsolutePath ().trim ( ) ).startUINewWindow ( new Stage ( ) );
                new TabsUI_Test(file.getAbsolutePath().trim()).startUINewWindow(new Stage());
            } else {
                System.out.println("error"); // or something else
            }
        });

        KeyCombination gotoKeyCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN);
        settingsMenuItem.setAccelerator(gotoKeyCombination);
        settingsMenuItem.setOnAction(event -> {
            //		System.out.println ( "CTRL+ALT+S event triggered" );
            //		Page1 controller = new Page1 ( true );
            //		Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "Page1" , controller );
            //		Stage stage = new Stage ( );
            //		stage.setScene ( new Scene ( parent ) );
            //		stage.show ( );

            SettingsPage controller = new SettingsPage();
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("SettingsPage", controller);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            stage.show();

        });


        //	  	  EventHandler < Event > e1 = new EventHandler < Event > ( )
        //	  	  {
        //
        //	  		public void handle ( Event e )
        //	  		{
        //	  		    if ( notefromexternalTab.isSelected ( ) ) {
        //	  			  System.out.println ( notefromexternalTab.getText ( ) );
        //	  		    }
        //	  		}
        //	  	  };


        //	  notefromexternalTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
        //	        if (newValue) {
        //	            comboBoxPresYear.setVisible(true);
        //	            lblPresYear.setVisible(true);}
        //	        }
        //	    });


        //	  	  		tab.selectedProperty ( ).addListener ( ( observable , oldValue , newValue ) -> {
        //	  	  		    if ( newValue ) {
        //	  	  			  callNoteFromExternalPage ( );
        //	  	  		    }


        this.savefileLabel.setOnMouseClicked(e -> {
            String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + filenametextfield.getText().trim() + ".txt";
            boolean exist = FileHandlings.checkFileExist(filepath);
            if (!exist) {
                if (!filenametextfield.getText().isEmpty()) {
                    SaveCardBackgroundColor.SaveCardBackgroundColor(filepath, TempAddNewNote.ReadBgColor());
                    SaveTextColor.SaveCardTextColor(filepath, TempAddNewNote.ReadTextColor());
                    FileHandlings.createNewFile(filepath);
                    System.out.println("path " + filepath);
                    try {
                        FileHandlings.FileWrite(filepath, addTextArea.getText().trim());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    addTextArea.clear();
                    filenametextfield.clear();
                    nextpane.setVisible(false);


                    previpane.setVisible(true);
                    addNewNoteAnchor.setVisible(false);


                    menuBar.setVisible(true);
                    replaceMainContent.setVisible(true);


                    TempAddNewNote.DeleteTempFile();
                }

            } else {
                //
                Label label = new Label("name already exists\n use another name");
                label.setStyle("-fx-font-family: SansSerif");
                HBox hBox = new HBox(label);
                hBox.setPadding(new Insets(10));
                PopOver popOver = new PopOver(hBox);
                popOver.show(this.nextpane);
            }

            //	  System.out.println ( NoteSpacePathStorage.GetNoteSpacePath ().spacePath );
        });

        this.saveHBox.setOnMouseClicked(e -> {
            //		TempAddNewNote.DeleteTempFile ( );
            AnchorPane.setTopAnchor(this.clearlabel, 207.0);
            this.nextpane.setVisible(true);
            this.previpane.setVisible(false);
            this.fadeUp(this.nextpane);

        });
        this.goprevipane.setOnMouseClicked(e -> {
            AnchorPane.setTopAnchor(this.clearlabel, 268.0);
            this.nextpane.setVisible(false);
            this.previpane.setVisible(true);
            this.fadeUp(this.previpane);
        });

        this.generatecheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //	          chk2.setSelected(!newValue);
                if (newValue) {//selected
                    String generateName = UUID.randomUUID().toString();
                    filenametextfield.setText(generateName);
                    filenametextfield.setDisable(true);
                } else if (!newValue) {
                    filenametextfield.clear();
                    filenametextfield.setDisable(false);
                }

            }
        });
        this.clearlabel.setOnMouseClicked(e -> {
            TempAddNewNote.DeleteTempFile();
            TempAddNewNote.ReplaceVisibility(true);
            this.addTextArea.setText(TempAddNewNote.ReadText());
            String style = String.format("-fx-background-color: %s;", TempAddNewNote.ReadBgColor());
            this.addNewNoteAnchor.setStyle(style + "-fx-border-radius:8;" + "-fx-background-radius: 8;");
            this.addNewNoteAnchor.setVisible(true);
            this.fadeUp(this.addNewNoteAnchor);
            changeFont(TempAddNewNote.ReadTextColor());
        });

        this.plusLabel.setOnMouseClicked(e -> {
            //		this.replacePage.getChildren ( ).clear ( );
            generatecheckbox.setSelected(false);
            TempAddNewNote.ReplaceVisibility(true);
            this.addTextArea.setText(TempAddNewNote.ReadText());
            String style = String.format("-fx-background-color: %s;", TempAddNewNote.ReadBgColor());
            this.addNewNoteAnchor.setStyle(style + "-fx-border-radius:8;" + "-fx-background-radius: 8;");
            this.addNewNoteAnchor.setVisible(true);
            this.fadeUp(this.addNewNoteAnchor);
            changeFont(TempAddNewNote.ReadTextColor());


            menuBar.setVisible(false);
            replaceMainContent.setVisible(false);


        });

        Circle[] bgCircleList1 = new Circle[]{bg_circle1, bg_circle2, bg_circle3, bg_circle4, bg_circle5, bg_circle6};
        for (
                int i = 0;
                i < bgCircleList1.length;
                ++i
        ) {
            Circle c = bgCircleList1[i];
            c.setOnMouseClicked(e -> {
                generatecheckbox.setSelected(false);
                TempAddNewNote.ReplaceVisibility(true);
                String hex = getCircleColor(c);
                tempBgColor = hex;
                TempAddNewNote.ReplaceBackgroundColor(hex);
                String style = String.format("-fx-background-color: %s;", hex);
                this.addNewNoteAnchor.setStyle(style + "-fx-border-radius:8;" + "-fx-background-radius: 8;");
                this.addNewNoteAnchor.setVisible(true);
                this.fadeUp(this.addNewNoteAnchor);


                menuBar.setVisible(false);
                replaceMainContent.setVisible(false);

            });
        }

        Circle[] txtCircleList1 = new Circle[]{txt_circle1, txt_circle2, txt_circle3, txt_circle4, txt_circle5, txt_circle6};
        for (
                int i = 0;
                i < txtCircleList1.length;
                ++i
        ) {
            Circle c = txtCircleList1[i];
            c.setOnMouseClicked(e -> {
                generatecheckbox.setSelected(false);
                TempAddNewNote.ReplaceVisibility(true);
                String hex = getCircleColor(c);
                tempTextColor = hex;
                TempAddNewNote.ReplaceTextColor(hex);
                changeFont(hex);


            });
        }


        BackgroundcolorPicker.setOnAction(
                event -> {
                    event.consume();

                    Color value = BackgroundcolorPicker.getValue();
                    if (value == null) {
                        //	             root.setStyle(null);
                    } else {
                        generatecheckbox.setSelected(false);
                        TempAddNewNote.ReplaceVisibility(true);
                        String hex = toHexString(value);
                        TempAddNewNote.ReplaceBackgroundColor(hex);
                        String style = String.format("-fx-background-color: %s;", hex);
                        this.addNewNoteAnchor.setStyle(style + "-fx-border-radius:8;" + "-fx-background-radius: 8;");
                        this.addNewNoteAnchor.setVisible(true);
                        this.fadeUp(this.addNewNoteAnchor);

                        menuBar.setVisible(false);
                        replaceMainContent.setVisible(false);
                    }
                });

        TextcolorPicker.setOnAction(
                event -> {
                    event.consume();

                    Color value = TextcolorPicker.getValue();
                    if (value == null) {
                        //	             root.setStyle(null);
                    } else {
                        TempAddNewNote.ReplaceVisibility(true);
                        String hex = toHexString(value);
                        TempAddNewNote.ReplaceTextColor(hex);
                        changeFont(hex);

                    }
                });

        this.closeAddNotePaneLabel.setOnMouseClicked(e -> {
            TempAddNewNote.ReplaceVisibility(false);
            this.addNewNoteAnchor.setVisible(false);


            menuBar.setVisible(true);
            replaceMainContent.setVisible(true);

        });


        this.mainPageAnchor.setOnMouseEntered(e -> {
            this.StartServices();

            this.addNewNoteAnchor.setVisible(TempAddNewNote.ReadVisibility());
            this.txtSearch.setText(TempMainPage.ReadSearchValue());
            this.addTextArea.setText(TempAddNewNote.ReadText());
            String style = String.format("-fx-background-color: %s;", TempAddNewNote.ReadBgColor());
            this.addNewNoteAnchor.setStyle(style + "-fx-border-radius:8;" + "-fx-background-radius: 8;");

            //		this.fadeUp ( this.addNewNoteAnchor );
            changeFont(TempAddNewNote.ReadTextColor());


        });
        this.mainPageAnchor.setOnMouseExited(e -> {
            this.StartServices();

            this.addNewNoteAnchor.setVisible(TempAddNewNote.ReadVisibility());
            this.txtSearch.setText(TempMainPage.ReadSearchValue());
            this.addTextArea.setText(TempAddNewNote.ReadText());
            String style = String.format("-fx-background-color: %s;", TempAddNewNote.ReadBgColor());
            this.addNewNoteAnchor.setStyle(style + "-fx-border-radius:8;" + "-fx-background-radius: 8;");

            //		this.fadeUp ( this.addNewNoteAnchor );
            changeFont(TempAddNewNote.ReadTextColor());

        });

        this.innerAnchor.setOnMouseEntered(e -> {
            this.StartServices();

            this.addNewNoteAnchor.setVisible(TempAddNewNote.ReadVisibility());
            this.txtSearch.setText(TempMainPage.ReadSearchValue());
            this.addTextArea.setText(TempAddNewNote.ReadText());
            String style = String.format("-fx-background-color: %s;", TempAddNewNote.ReadBgColor());
            this.addNewNoteAnchor.setStyle(style + "-fx-border-radius:8;" + "-fx-background-radius: 8;");

            //		this.fadeUp ( this.addNewNoteAnchor );
            changeFont(TempAddNewNote.ReadTextColor());
        });
        this.innerAnchor.setOnMouseExited(e -> {
            this.StartServices();

            this.addNewNoteAnchor.setVisible(TempAddNewNote.ReadVisibility());
            this.txtSearch.setText(TempMainPage.ReadSearchValue());
            this.addTextArea.setText(TempAddNewNote.ReadText());
            String style = String.format("-fx-background-color: %s;", TempAddNewNote.ReadBgColor());
            this.addNewNoteAnchor.setStyle(style + "-fx-border-radius:8;" + "-fx-background-radius: 8;");

            //		this.fadeUp ( this.addNewNoteAnchor );
            changeFont(TempAddNewNote.ReadTextColor());
        });


        this.setingsLabel.setOnMouseClicked(e -> {
            this.changeStateSettings_MODAL();

        });


        this.btn.setOnAction(e -> {

        });
        this.txtSearch.textProperty().addListener(new ChangeListener<String>() {
            Service<ArrayList<TextObject>> searchService = new Service<ArrayList<TextObject>>() {
                @Override
                protected Task<ArrayList<TextObject>> createTask() {
                    return new Task<ArrayList<TextObject>>() {
                        @Override
                        protected ArrayList<TextObject> call() throws Exception {
                            return FileHandlings.getTextObjectLists();
                        }
                    };
                }
            };


            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    searchResultAnchor.setVisible(false);
                    clearSearch.setVisible(false);
                } else {
                    searchService.restart();
                    searchService.setOnSucceeded(e -> {
                        TempMainPage.ReplaceSearchValue(txtSearch.getText().trim());

                        clearSearch.setVisible(true);
                        searchResultAnchor.setVisible(true);
                        resultVBox.getChildren().clear();
                        String result = txtSearch.getText().trim();
                        Label label = new Label("Result found in");
                        label.setStyle("-fx-alignment:center;" + "-fx-text-fill: #666666;" + "-fx-font-weight: bold;");
                        resultVBox.getChildren().add(label);
                        for (TextObject textObject : FileHandlings.getTextObjectLists()) {

                            if (textObject.getText().trim().toLowerCase().contains(result.toLowerCase())) {
                                Button button = new Button(textObject.getName());
                                button.getStyleClass().add("font-pyidaungsu");
                                button.getStyleClass().add("font-bold");
                                button.setPrefSize(187, 25);

                                //				    button.setStyle ( "-fx-background-color:white;" );
                                String bgcolor = SaveCardBackgroundColor.GetCardBackgroundColor(textObject.getAbsolutePath());
                                if (bgcolor == null) {
                                    bgcolor = SaveSettings.ReadSettings().default_BackgroundColor;
                                }
                                String txtcolor = SaveTextColor.GetCardTextColor(textObject.getAbsolutePath());
                                if (txtcolor == null) {
                                    txtcolor = SaveSettings.ReadSettings().default_TextColor;
                                }
                                button.setStyle("-fx-background-color:" + bgcolor + ";" + "-fx-text-fill:" + txtcolor + ";");

                                //				    button.setOnMouseEntered ( e -> {
                                //					  button.setStyle ( "-fx-background-color: #F8F9FA;" );
                                //				    } );
                                //				    button.setOnMouseExited ( e -> {
                                //					  button.setStyle ( "-fx-background-color:white;" );
                                //				    } );

                                //					  button.setOnAction ( e1 -> {
                                //
                                //						if ( ! textObject.getFileExtension ( ).equals ( ".jpg" ) && ! textObject.getFileExtension ( ).equals ( ".html" ) && ! textObject.getFileExtension ( ).equals ( ".png" ) ) {
                                //						    String bgcolor1 = SaveCardBackgroundColor.GetCardBackgroundColor ( textObject.getAbsolutePath ( ) );
                                //						    if ( bgcolor1 == null ) {
                                //							  bgcolor1 = SaveSettings.ReadSettings ( ).default_BackgroundColor;
                                //						    }
                                //						    String txtcolor1 = SaveTextColor.GetCardTextColor ( textObject.getAbsolutePath ( ) );
                                //						    if ( txtcolor1 == null ) {
                                //							  txtcolor1 = SaveSettings.ReadSettings ( ).default_TextColor;
                                //						    }
                                //						    previewPopOver ( button , textObject , bgcolor1 , txtcolor1 );
                                //						}
                                //						else {
                                //						    if ( textObject.getFileExtension ( ).equals ( ".mp4" ) ) {
                                //							  try {
                                //								File file = new File ( textObject.getAbsolutePath ( ) );
                                //								Desktop.getDesktop ( ).open ( file );
                                //							  }
                                //							  catch ( IOException ex ) {
                                //								ex.printStackTrace ( );
                                //							  }
                                //						    }
                                //
                                //						}
                                //
                                //
                                //					  } );

                                button.setOnAction(e1 -> {
                                    String extension = textObject.getFileExtension();
                                    if (bagOfWords(extension)) {
                                        System.out.println("PrintHer " + extension);
                                        boolean noteExt = Arrays.stream(noteExtensions).anyMatch(extension::equals);
                                        if (noteExt) {
                                            String bgcolor1 = SaveCardBackgroundColor.GetCardBackgroundColor(textObject.getAbsolutePath());
                                            if (bgcolor1 == null) {
                                                bgcolor1 = SaveSettings.ReadSettings().default_BackgroundColor;
                                            }
                                            String txtcolor1 = SaveTextColor.GetCardTextColor(textObject.getAbsolutePath());
                                            if (txtcolor1 == null) {
                                                txtcolor1 = SaveSettings.ReadSettings().default_TextColor;
                                            }
                                            previewPopOver(button, textObject, bgcolor1, txtcolor1);
                                        }

                                        boolean imgExt = Arrays.stream(imageExtensions).anyMatch(extension::equals);
                                        if (imgExt) {
                                            try {
                                                Desktop.getDesktop().open(new File(textObject.getAbsolutePath()));
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                        }

                                        boolean vdoExt = Arrays.stream(videoExtensions).anyMatch(extension::equals);
                                        if (vdoExt) {
                                            try {
                                                Desktop.getDesktop().open(new File(textObject.getAbsolutePath()));
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }
                                });

                                button.setCursor(Cursor.HAND);

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        resultVBox.getChildren().add(button);
                                    }
                                });

                                System.out.println(textObject.getName());


                            }
                        }
                        clearSearch.setOnMouseClicked(e1 -> {
                            TempMainPage.ReplaceSearchValue("");
                            txtSearch.clear();

                        });
                    });

                }


            }
        });

        this.txtSearch.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    //			  searchResultAnchor.setVisible ( true );
                    //			  System.out.println ( "Textfield on focus" );
                } else {
                    //			  searchResultAnchor.setVisible ( false );
                    //			  System.out.println ( "Textfield out focus" );
                }
            }
        });


        this.addTextArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 130 ? change : null));

        this.addTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                int s = addTextArea.getText().trim().length();
                countnoLabel.setText(s + "");
                TempAddNewNote.ReplaceText(addTextArea.getText().trim());


                //		    if ( newValue.isEmpty ( ) ) {
                //			  TempAddNewNote.ReplaceText ( newValue.trim ( ) );
                //		    }
                //		    else {
                //			  TempAddNewNote.ReplaceText ( newValue.trim ( ) );
                //		    }


            }
        });

        mainPageAnchor.widthProperty().addListener((obs, oldVal, newVal) -> {

            splitPaneOriginalPosition();
            //								backgroundImage.setFitHeight ( backgroundImage.getFitHeight () +Double.parseDouble ( newVal.toString () ));
        });


        this.shortCutsAdded();

    }//End Perform()

    public boolean bagOfWords(String str) {
        String[] words = {".txt", ".png", ".jpg"};
        return (Arrays.asList(words).contains(str));
    }

    private void UI_Binding() {

    }

    private void UI_Prepare() {


        this.splitPaneOriginalPosition();

        this.nextpane.setVisible(false);

        this.addNewNoteAnchor.setVisible(TempAddNewNote.ReadVisibility());

        if (!TempMainPage.ReadSearchValue().contains("")) {
            this.txtSearch.setText(TempMainPage.ReadSearchValue());
            this.searchResultAnchor.setVisible(true);
        }
        this.addTextArea.setText(TempAddNewNote.ReadText());
        String style = String.format("-fx-background-color: %s;", TempAddNewNote.ReadBgColor());
        this.addNewNoteAnchor.setStyle(style + "-fx-border-radius:8;" + "-fx-background-radius: 8;");

        //		this.fadeUp ( this.addNewNoteAnchor );
        changeFont(TempAddNewNote.ReadTextColor());

        this.addNewNoteAnchor.setVisible(TempAddNewNote.ReadVisibility());

        this.searchResultAnchor.setVisible(false);

        //	  this.translateWidth ( this.addNewNoteAnchor , 50 );
        this.clearSearch.setVisible(false);


        //	  	  this.callTreeModeToTabPane ( );
        //	  	  this.callTabModeToTabPane ( );
        //	  	  this.callPage1ToTabPane ( );

        //	  this.callPage1ToMainContent ( );

        menuBar.setVisible(false);
        modeMenuVBox.setVisible(false);
        new ReadMainUISettings();
        new setDetectPartitionToMenu();

    } // End Prepare


    void previewPopOver(Node node, TextObject txtObj, String bgcolor, String txtcolor) {

        TextArea popUpTextArea = new TextArea();

        Service<Void> saveService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        return null;
                    }
                };
            }
        };
        saveService.setOnSucceeded(e -> {
            Save(txtObj.getAbsolutePath(), popUpTextArea.getText().trim());
        });

        Service<TextObject> getTextObjectService = new Service<TextObject>() {
            @Override
            protected Task<TextObject> createTask() {
                return new Task<TextObject>() {
                    @Override
                    protected TextObject call() throws Exception {
                        return FileHandlings.buildObject(txtObj.getAbsolutePath());
                    }
                };
            }
        };
        getTextObjectService.restart();
        getTextObjectService.setOnSucceeded(e -> {
            popUpTextArea.setText(FontRepair.fixmyanamrfont(getTextObjectService.getValue().getText().trim()));
        });


        popUpTextArea.setOnMouseEntered(e -> {
            getTextObjectService.restart();
        });
        popUpTextArea.setOnMouseExited(e -> {
            saveService.restart();
        });

        //	  popUpTextArea.textProperty ( ).addListener ( ( obs , old , niu ) -> { //save when typing
        //		saveService.restart ( );
        //	  } );


        popUpTextArea.setCache(true);
        popUpTextArea.setCacheHint(CacheHint.SPEED);
        popUpTextArea.setCacheHint(CacheHint.QUALITY);
        popUpTextArea.setCacheHint(CacheHint.SCALE);
        String style = String.format("-fx-background-color: %s;", bgcolor);
        popUpTextArea.setStyle(style + "-fx-txtObj-fill:" + txtcolor + ";");
        popUpTextArea.setWrapText(true);
        //		popUpTextArea.setStyle ( "-fx-background-color:" + BackgroundColor_Service.getValue ( ) + ";" );
        popUpTextArea.setEditable(false);

        AnchorPane.setTopAnchor(popUpTextArea, 0.0);
        AnchorPane.setRightAnchor(popUpTextArea, 0.0);
        AnchorPane.setBottomAnchor(popUpTextArea, 0.0);
        AnchorPane.setLeftAnchor(popUpTextArea, 0.0);
        AnchorPane anchor = new AnchorPane(popUpTextArea);
        anchor.setPrefSize(300, 300);
        Label readOnlyLabel = new Label("Read Only");
        readOnlyLabel.setStyle("-fx-txtObj-fill:" + txtcolor + ";");
        readOnlyLabel.setCursor(Cursor.HAND);
        readOnlyLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (PopupEditable) {
                    readOnlyLabel.setText("Editable");
                    popUpTextArea.setEditable(true);
                    PopupEditable = false;
                } else {
                    readOnlyLabel.setText("Read Only");
                    popUpTextArea.setEditable(false);
                    PopupEditable = true;
                }

            }
        });
        Label copyNoteLabel = new Label("Copy Note");
        copyNoteLabel.setStyle("-fx-txtObj-fill:" + txtcolor + ";");
        copyNoteLabel.setCursor(Cursor.HAND);
        copyNoteLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                CLipBoard.SaveToCLip(popUpTextArea.getText().trim());
                copyNoteLabel.setText("Copied");
                popUpTextArea.selectAll();
                PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(2));
                pauseTransition1.setOnFinished(e1 -> {
                            copyNoteLabel.setText("Copy Note");
                        }
                );
                pauseTransition1.play();
            }
        });
        Label openNoteCardLabel = new Label("Open In CardMode");
        openNoteCardLabel.setStyle("-fx-txtObj-fill:" + txtcolor + ";");
        openNoteCardLabel.setCursor(Cursor.HAND);
        openNoteCardLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                new NoteCardUI_Test(txtObj.getAbsolutePath()).startUINewWindow(new Stage());
            }
        });
        HBox hBox = new HBox(readOnlyLabel, copyNoteLabel, openNoteCardLabel);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(20);
        VBox vBox = new VBox(anchor, hBox);
        vBox.setPadding(new Insets(5));
        vBox.setStyle(style);
        //Create PopOver and add look and feel

        Label header = new Label("Preview (Read Only)");
        header.setStyle("-fx-txtObj-fill:" + txtcolor + ";");

        HBox topBox = new HBox(header);
        topBox.setStyle(style);
        topBox.setPadding(new Insets(5));
        topBox.setAlignment(Pos.CENTER);
        VBox outerBox = new VBox(topBox, vBox);
        outerBox.setPrefSize(300, 300);

        PopOver popOver = new PopOver(outerBox);
        popOver.setPrefSize(300, 300);
        popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
        popOver.setDetachable(true);
        popOver.setCornerRadius(15);
        popOver.setAutoFix(true);
        //	  popOver.setTitle ( "Preview (Editable)" );
        popOver.setHideOnEscape(true);
        if (popOver.isShowing() == false) {
            popOver.show(node);

        } else {
            popOver.hide();
        }
    }

    private void callPage1ToMainContent() {
        replaceMainContent.getChildren().clear();
        Parent parent = new Page1UI_Test().startUI(true);
        AnchorPane.setTopAnchor(parent, 0.0);
        AnchorPane.setRightAnchor(parent, 0.0);
        AnchorPane.setBottomAnchor(parent, 0.0);
        AnchorPane.setLeftAnchor(parent, 0.0);
        replaceMainContent.getChildren().add(parent);
    }

    private void callTabModeToMainContent() {
        replaceMainContent.getChildren().clear();
        Parent parent = new TabModeUI_Test().startUI();
        AnchorPane.setTopAnchor(parent, 0.0);
        AnchorPane.setRightAnchor(parent, 0.0);
        AnchorPane.setBottomAnchor(parent, 0.0);
        AnchorPane.setLeftAnchor(parent, 0.0);
        replaceMainContent.getChildren().add(parent);
    }

    private void callTreeModeToMainContent() {
        replaceMainContent.getChildren().clear();
        Parent parent = new TreeModeUI_Test().startUI();
        AnchorPane.setTopAnchor(parent, 0.0);
        AnchorPane.setRightAnchor(parent, 0.0);
        AnchorPane.setBottomAnchor(parent, 0.0);
        AnchorPane.setLeftAnchor(parent, 0.0);
        replaceMainContent.getChildren().add(parent);
    }

    private void callMinimalistModeToMainContent() {
        replaceMainContent.getChildren().clear();
        Parent parent = new MinimalistPageUI_Test().startUI();
        AnchorPane.setTopAnchor(parent, 0.0);
        AnchorPane.setRightAnchor(parent, 0.0);
        AnchorPane.setBottomAnchor(parent, 0.0);
        AnchorPane.setLeftAnchor(parent, 0.0);
        replaceMainContent.getChildren().add(parent);
    }

    private void callMinimalistToMainContent() {
        replaceMainContent.getChildren().clear();
        Parent parent = new MinimalistPageUI_Test().startUI();
        AnchorPane.setTopAnchor(parent, 0.0);
        AnchorPane.setRightAnchor(parent, 0.0);
        AnchorPane.setBottomAnchor(parent, 0.0);
        AnchorPane.setLeftAnchor(parent, 0.0);
        replaceMainContent.getChildren().add(parent);

        //	  new MinimalistPageUI_Test ().startUINewWindow ( new Stage () );

    }


    private void Save(String absoultePath, String text) {
        try {
            FileHandlings.FileWrite(absoultePath, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void changeStateSettings_MODAL() {

        SettingsPage settingsPage = new SettingsPage();
        Parent parent = ___Bundle.__ViewLoader._getInstance()._load("SettingsPage", settingsPage);

        ___Bundle.__ViewLoader._getInstance()._showNewApplicationScene(parent, StageStyle.DECORATED, "Settings", true);

        settingsPage.btn11.setOnAction(e1 -> {
            Stage stage = (Stage) settingsPage.btn11.getScene().getWindow();
            stage.close();

            SettingsPage.syncSettings.cancel();
            DateSettings.syncSettings.cancel();

        });


        //	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "com.notespace.AddNewNote",null);
        //	  ___Bundle.__ViewLoader._getInstance ( )._showNewApplicationScene ( parent , StageStyle.DECORATED , "ADD NEW NOTE" , false );


    }

    private void changeStateDetect_MODAL() {
        AutoDetect controller = new AutoDetect();
        Parent parent = ___Bundle.__ViewLoader._getInstance()._load("AutoDetect", controller);
        ___Bundle.__ViewLoader._getInstance()._showNewApplicationScene(parent, StageStyle.DECORATED, "Detect", true);

        controller.closeDetectPane.setOnMouseClicked(e1 -> {

            Stage stage = (Stage) controller.closeDetectPane.getScene().getWindow();
            stage.close();
        });
    }

    private Effect getFrostEffect(double blur_amount, int iteration) {
        Effect frostEffect =
                new BoxBlur(blur_amount, blur_amount, iteration);

        return frostEffect;
    }

    private void fadeUp(Node node) {
        node.setCache(true);
        node.setCacheHint(CacheHint.QUALITY);
        node.setCacheHint(CacheHint.SPEED);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.seconds(0.5));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setNode(node);
        fade.play();
    }

    private String getCircleColor(Circle circle) {
        Color c = (Color) circle.getFill();
        String hex = String.format("#%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
        return hex;
    }

    private String toHexString(Color color) {
        int r = ((int) Math.round(color.getRed() * 255)) << 24;
        int g = ((int) Math.round(color.getGreen() * 255)) << 16;
        int b = ((int) Math.round(color.getBlue() * 255)) << 8;
        int a = ((int) Math.round(color.getOpacity() * 255));

        return String.format("#%08X", (r + g + b + a));
    }

    private void changeFont(String hex) {

        Label[] labels = new Label[]{closeAddNotePaneLabel};
        for (
                int i = 0;
                i < labels.length;
                ++i
        ) {
            Label c = labels[i];
            if (hex != null) {
                //		    c.setTextFill ( Color.valueOf ( hex ) );
                c.setStyle("-fx-text-fill:" + hex + ";");
            }


        }

        addTextArea.setStyle("-fx-text-fill:" + hex + ";");
    }

    private void transitionLeftRight(Node node, double startValue, double endValue) {

        node.setCache(true);
        node.setCacheHint(CacheHint.SPEED);
        node.setCacheHint(CacheHint.QUALITY);
        node.setCacheHint(CacheHint.SCALE);

        KeyValue start = new KeyValue(node.translateXProperty(), startValue);
        KeyValue end = new KeyValue(node.translateXProperty(), endValue);
        KeyValue cache = new KeyValue(node.cacheHintProperty(), CacheHint.SPEED);
        KeyValue cache2 = new KeyValue(node.cacheHintProperty(), CacheHint.QUALITY);
        KeyValue cache3 = new KeyValue(node.cacheHintProperty(), CacheHint.SCALE);

        KeyFrame startFrame = new KeyFrame(Duration.ZERO, start, cache, cache2, cache3);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(0.4), end, cache, cache2, cache3);

        Timeline timeline = new Timeline(startFrame, endFrame);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(1);
        timeline.play();
    }


    private void init() {
        this.ServicesRepo();
        this.UI_Perform();
        this.UI_Binding();
        this.UI_Prepare();
        this.StartServices();

        //	  this.callTabMode ( );
        //	  Service service = new Service ( )
        //	  {
        //		@Override
        //		protected Task createTask ( )
        //		{
        //		    return new Task ( )
        //		    {
        //			  @Override
        //			  protected Object call ( ) throws Exception
        //			  {
        //				for (
        //					  int i = 0 ;
        //					  i < 500 ;
        //					  i++
        //				) {
        //				    updateProgress ( i , 500 );
        //				    try {
        //					  Thread.sleep ( 20 );
        //				    }
        //				    catch ( InterruptedException e ) {
        //					  e.printStackTrace ( );
        //				    }
        //				}
        //				return null;
        //			  }
        //		    };
        //		}
        //	  };
        //	  lg_progressBar.progressProperty ( ).bind ( service.progressProperty ( ) );
        //	  service.start ( );


    }

    private void splitPaneOriginalPosition() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = (Stage) mainPageAnchor.getScene().getWindow();
                SplitUIMemory sum = SplitUIMemoryStorage.getSplitMemory(stage.getWidth());
                if (sum != null) {


                } else {


                }

            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.init();
    }


    class RecentNoteDataInsert extends Service<ArrayList<RecentNote>> {

        public RecentNoteDataInsert() {
            restart();
            setOnSucceeded(e -> {

                ArrayList<RecentNote> arrayList = getValue();

                recentNoteMenu.getItems().clear();
//                Collections.sort(arrayList, new Comparator<RecentNote>() {
//                    public int compare(RecentNote o1, RecentNote o2) {
//                        return (int) (o2.date - o1.date);
//                    }
//                });
                arrayList.stream().forEach(recentNote -> {


                    Menu menu = new Menu(recentNote.notePath.trim());
                    MenuItem cardModeMenuItem = new MenuItem("Card Mode");
                    cardModeMenuItem.setOnAction(e1 -> {
                        new NoteCardUI_Test(recentNote.notePath.trim()).startUINewWindow(new Stage());
                    });
                    MenuItem tabModeMenuItem = new MenuItem("Tab Mode");
                    tabModeMenuItem.setOnAction(e1 -> {
                        new TabsUI_Test(recentNote.notePath.trim()).startUINewWindow(new Stage());
                    });
                    MenuItem removeRecentNoteMenuItem = new MenuItem("Remove this note from recent");
                    removeRecentNoteMenuItem.setOnAction(e1 -> {
                        RecentNoteStorage.deleteRecentNote(recentNote.notePath.trim());
                    });

                    menu.getItems().addAll(cardModeMenuItem, tabModeMenuItem, removeRecentNoteMenuItem);

                    //			  if ( recentNote.notePath.trim ( ).equals ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath.trim ( ) ) ) {
                    //				menu.setText ( recentNote.notePath.trim ( ) + " ( Folder Opened )" );
                    //				menu.setDisable ( true );
                    //			  }
                    File f = new File(recentNote.notePath.trim());
                    if (f.isDirectory()) {
                        menu.setText(recentNote.notePath.trim() + " ( Folder )");
                        removeRecentNoteMenuItem.setText("Remove this folder from recent");
                        cardModeMenuItem.setVisible(false);
                    }

                    File file = new File(recentNote.notePath.trim());
                    if (file.exists()) {
                        cardModeMenuItem.setDisable(false);
                        tabModeMenuItem.setDisable(false);
                    } else {
                        cardModeMenuItem.setDisable(true);
                        tabModeMenuItem.setDisable(true);
                        menu.setText("( " + recentNote.notePath.trim() + " )" + " is missing in your system.");
                    }

                    menu.setOnAction(e1 -> {
                        //				NoteSpacePathStorage.SaveNoteSpacePath ( new NoteSpacePath ( UUID.randomUUID ( ).toString ( ) , recentNoteSpace.spaceName.trim ( ) ) );

                    });
                    recentNoteMenu.getItems().add(menu);


                });
                MenuItem clearMenuItem = new MenuItem("Clear All Recent Notes");
                clearMenuItem.setOnAction(e1 -> {
                    arrayList.stream().forEach(strem -> {
                        RecentNoteStorage.deleteRecentNote(strem.notePath);
                    });
                });
                recentNoteMenu.getItems().add(clearMenuItem);

            });
        }

        @Override
        protected Task<ArrayList<RecentNote>> createTask() {
            return new Task<ArrayList<RecentNote>>() {
                @Override
                protected ArrayList<RecentNote> call() throws Exception {
                    return RecentNoteStorage.getRecentNoteList();
                }
            };
        }


    }

    class RecentNoteSpaceInsertData extends Service<ArrayList<RecentNoteSpace>> {


        public RecentNoteSpaceInsertData() {
            restart();
            setOnSucceeded(e -> {
                recentNoteSpaceMenu.getItems().clear();
                getValue().stream().forEach(recentNoteSpace -> {


                    MenuItem menuItem = new MenuItem();
                    menuItem.setText(recentNoteSpace.spaceName.trim());


                    if (recentNoteSpace.spaceName.trim().equals(NoteSpacePathStorage.getNoteSpacePathObj().spacePath.trim())) {
                        menuItem.setText(recentNoteSpace.spaceName.trim() + " ( in use )");
                    }
                    File file = new File(recentNoteSpace.spaceName.trim());
                    if (file.exists()) {
                        menuItem.setDisable(false);
                    } else {
                        menuItem.setDisable(true);
                        menuItem.setText(recentNoteSpace.spaceName.trim() + " is missing in your system.");
                    }

                    //			  			  if ( RecentNoteSpaceStorage.checkIfPathExist ( recentNoteSpace.spaceName.trim ( ) ) ) {
                    //			  				menuItem.setDisable ( true );
                    //			  			  }
                    //			  			  else {
                    //			  				menuItem.setDisable ( false );
                    //
                    //			  			  }


                    menuItem.setOnAction(e1 -> {
                        NoteSpacePathStorage.SaveNoteSpacePath(new NoteSpacePath(UUID.randomUUID().toString(), recentNoteSpace.spaceName.trim()));
                    });
                    recentNoteSpaceMenu.getItems().add(menuItem);


                });

            });
        }

        @Override
        protected Task<ArrayList<RecentNoteSpace>> createTask() {
            return new Task<ArrayList<RecentNoteSpace>>() {
                @Override
                protected ArrayList<RecentNoteSpace> call() throws Exception {
                    return RecentNoteSpaceStorage.getRecentNoteSpaceList();
                }
            };
        }


    }

    class ReadMainUISettings extends ScheduledService<MainUISettings> {

        public ReadMainUISettings() {
            restart();
            setPeriod(Duration.seconds(2));
            setOnSucceeded(e -> {
                menuBar.setVisible(getValue().mainPageMenuBarVisible);
                if (getValue().mainPageModeMenuVisible) {
                    LayoutMove.showModeMenu(replaceMainContent, modeMenuVBox);
                    openModeMenuMenuItem.setText("Close ModeMenu (F2)");
                } else {
                    LayoutMove.hideModeMenu(replaceMainContent, modeMenuVBox);
                    openModeMenuMenuItem.setText("Open ModeMenu (F2)");

                }

            });
        }


        @Override
        protected Task<MainUISettings> createTask() {
            return new Task<MainUISettings>() {
                @Override
                protected MainUISettings call() throws Exception {
                    return MainUISettingsStorage.readMainUISettings();
                }
            };
        }

    }

    class setDetectPartitionToMenu extends Service<ArrayList<String>> {

        public setDetectPartitionToMenu() {
            restart();
            setOnSucceeded(e -> {
                detectNoteSpaceMenu.getItems().clear();
                Menu cmi = new Menu("C:\\ can't use for detect");
                cmi.setDisable(true);
                detectNoteSpaceMenu.getItems().add(cmi);
                getValue().stream().forEach(partition -> {
                    Menu m = new Menu(partition);
                    detectNoteSpaceMenu.getItems().add(m);
                    new setDetectNoteSpaceToMenu(partition, m);

                });
                MenuItem menu = new MenuItem("Refresh");
                menu.setOnAction(event -> {
                    new setDetectPartitionToMenu();
                });
                detectNoteSpaceMenu.getItems().add(menu);


            });
        }

        @Override
        protected Task<ArrayList<String>> createTask() {
            return new Task<ArrayList<String>>() {
                @Override
                protected ArrayList<String> call() throws Exception {
                    return BuildNoteSpace.getPartitionsList();
                }
            };
        }
    }

    class setDetectNoteSpaceToMenu extends Service<ArrayList<String>> {

        String partition;


        public setDetectNoteSpaceToMenu(String partition, Menu menu) {
            this.partition = partition;
            restart();
            setOnRunning(e -> {
                menu.setText(partition + " detecting notespace");
                menu.setDisable(true);
            });
            setOnSucceeded(e -> {
                getValue().stream().forEach(e1 -> {

                    menu.setText(partition);
                    menu.setDisable(false);
                    String text = e1.equals(NoteSpacePathStorage.getNoteSpacePathObj().spacePath) ? e1 + " (in use) " : e1;
                    MenuItem mi = new MenuItem(text);

                    mi.setOnAction(e2 -> {
                        NoteSpacePathStorage.SaveNoteSpacePath(new NoteSpacePath("1", e1));
                        new setDetectPartitionToMenu();
                    });
                    menu.getItems().add(mi);

                });

            });
        }


        @Override
        protected Task<ArrayList<String>> createTask() {
            return new Task<ArrayList<String>>() {
                @Override
                protected ArrayList<String> call() throws Exception {
                    return BuildNoteSpace.getDetectedNoteSpacePath(partition);
                }
            };
        }
    }


}
