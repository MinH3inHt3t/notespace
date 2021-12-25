package com.notespace.NoteCard;

import com.notespace.CLipBoard;
import com.notespace.FileHandler.*;
import com.notespace.Font.FontRepair;
import com.notespace.HighLight;
import com.notespace.NoteCard.Appearance.CardAppearanceUI_Test;
import com.notespace.Settings.DateSettingsUI_Test;
import com.notespace.TabMode.Tabs.TabsUI_Test;
import com.ocpsoft.pretty.time.PrettyTime;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class NoteCard implements Initializable {
    @FXML
    public Label minNoteCardLabel, closeNoteCardLabel, editLabel, createDateLabel, modifiedDateLabel, textLabel, filenameLabel, showPasswordLabel, hidePasswordLabel, pinlabel, lockiconlabel, hashlabel, attlabel, hyperlinklabel, previewlabel, closeatthasReceiveAnchorLabel, atthasTypeLabel;
    @FXML
    public Menu cardsizeMenu, cardshapeMenu, cardanimationMenu, setpasswordMenu;
    @FXML
    public MenuItem pinNoteMenuItem, unpinNoteMenuItem, removePasswordMenuItem, removePasswordAllMenuItem, glasswindowmode;
    @FXML
    public CheckMenuItem defaultSizeMenuItem, setPasswordAllNote, setPasswordOnlyThisNote, size1MenuItem, size2MenuItem, size3MenuItem, size4MenuItem;
    @FXML
    public AnchorPane pane, webviewAnchor, atthasReceiveAnchorPane;
    @FXML
    public Label putlabel, foldeLabel, reopenLabel;
    @FXML
    public HBox putlabelhbox, putlabelhbox2, putlabelfoldbox, previewHBox, optionsHbox;
    @FXML
    public TextField passwordTextfield, puttextfield;
    @FXML
    public TextArea textArea, atthashReceiveTextArea;
    @FXML
    public AnchorPane maskerPane;
    @FXML
    public MenuItem tabmode, currentsizeMenuItem, cardappearanceMenuItem, openOptionsMenuItem, refreshMenuItem, editNoteMenuItem, adjustSizeMenuItem, putlabelMenuItem, deleteNoteMenuItem, previewNoteMenuItem, openNotePad, openExplorer, previewmode, copyNoteMenuItem, copyNoteAbsolutePathMenuItem;
    @FXML
    public AnchorPane verticalScroll, horizontalScroll;
    @FXML
    public ImageView imageView;
    @FXML
    Label smalliconCopyLabel, smalliconPutLabel;
    @FXML
    AnchorPane notecardAnchor;
    @FXML
    VBox passwordVBox, editVBox, imageViewVbox, mediaViewVbox;
    @FXML
    HBox passwordbox, textfieldpasswordBox, buttonBox;
    @FXML
    MediaView mediaView;
    @FXML
    CustomMenuItem shape1;
    @FXML
    WebView webview;


    @FXML
    Label clearLabel, openinbrowserLabel, typefigLabel, browsericonLabel, applyLabel, applyLabelIcon, enterPasswordLabel, incorrectLabel, closesizePrefixPaneLabel, sizePrefix;
    @FXML
    PasswordField passwordfield;

    boolean isProgramStart = true;
    boolean iscardOptionsOpen = false;
    boolean PopupEditable = true;
    double prewidth = 0;
    double preheight = 0;
    double width = 308;
    double height = 336;

    String[] noteExtensions = {".txt", ".ns"};
    String[] imageExtensions = {".png", ".jpg", ".jpeg", ".webp"};
    String[] videoExtensions = {".mp4"};

    PopOver popOver;
    TextObject object;
    EventHandler<KeyEvent> handler;
    String string;
    boolean startAnimation = true;
    private String absolutePath;
    public Service<TextObject> TextObject_Service = new Service() {
        @Override
        protected Task<TextObject> createTask() {
            return new Task<TextObject>() {
                @Override
                protected TextObject call() throws Exception {

                    return FileHandlings.buildObject(absolutePath);
                }
            };
        }
    };
    private Service<String> BackgroundColor_Service = new Service() {
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return SaveCardBackgroundColor.GetCardBackgroundColor(absolutePath);
                }
            };
        }
    };
    private Service<String> TextColor_Service = new Service<String>() {
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return SaveTextColor.GetCardTextColor(absolutePath);
                }
            };
        }
    };
    private Service wait_Service1 = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    Thread.sleep(500);
                    return null;
                }
            };
        }
    };
    private Service wait_Service2 = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    Thread.sleep(2000);
                    return null;
                }
            };
        }
    };
    private Service wait_Service3 = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    Thread.sleep(3000);
                    return null;
                }
            };
        }
    };
    private Service wait_Service4 = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    Thread.sleep(4000);
                    return null;
                }
            };
        }
    };

    public NoteCard(String absolutePath) {
        this.absolutePath = absolutePath;
        //	  RecentNoteStorage.setRecentNote ( absolutePath , absolutePath );
    }

    public NoteCard(String absolutePath, double prewidth, double preheight) {
        this.absolutePath = absolutePath;
        this.width = prewidth;
        this.height = preheight;
        this.prewidth = prewidth;
        this.preheight = preheight;
        //	  RecentNoteStorage.setRecentNote ( absolutePath , absolutePath );
    }

    public NoteCard(String absolutePath, boolean animation) {
        this.absolutePath = absolutePath;
        this.startAnimation = animation;
        //	  RecentNoteStorage.setRecentNote ( absolutePath , absolutePath );
    }

    public NoteCard(String absolutePath, boolean animation, double prewidth, double preheight) {
        this.absolutePath = absolutePath;
        this.startAnimation = animation;
        this.width = prewidth;
        this.height = preheight;
        this.prewidth = prewidth;
        this.preheight = preheight;
        //	  RecentNoteStorage.setRecentNote ( absolutePath , absolutePath );
    }


    private void ServicesRepo() {

        this.setTextObject_Service();
        this.setBackgroundColor_Service();
        this.setTextColor_Service();
    }

    private void setTextObject_Service() {

        this.TextObject_Service.setOnFailed(e -> {
            this.textLabel.setText("File Path is missing.");
            this.filenameLabel.setText("");
            Node node[] = {this.editLabel, this.previewlabel, this.textLabel, this.passwordVBox, this.attlabel, this.hashlabel, this.hyperlinklabel};
            int i = 0;
            while (i < node.length) {
                node[i].setDisable(true);
                i++;
            }

        });

        this.TextObject_Service.setOnSucceeded(e -> {
            Node node[] = {this.editLabel, this.previewlabel, this.textLabel, this.passwordVBox, this.attlabel, this.hashlabel, this.hyperlinklabel};
            int i = 0;
            while (i < node.length) {
                node[i].setDisable(false);
                i++;
            }

            //		this.textLabel.setVisible ( true );

            object = this.TextObject_Service.getValue();

            if (bagOfWords(object.getFileExtension())) {
                boolean noteExt = Arrays.stream(noteExtensions).anyMatch(object.getFileExtension()::equals);
                if (noteExt) {
                    typefigLabel.setText("\ue06f");


                }
                boolean imgExt = Arrays.stream(imageExtensions).anyMatch(object.getFileExtension()::equals);
                if (imgExt) {
                    typefigLabel.setText("\uE3F4");

                }

            } else {
                typefigLabel.setText("\ue86f");
            }
            //		this.textLabel.setText ( object.getText ( ) );
            List<String> hashlist = HighLight.getHashHightLight(object.getText());
            List<String> atlist = HighLight.getAtHightLight(object.getText());
            List<String> hyperlist = HighLight.getHyperLinkHightLight(object.getText());

            StringBuilder msg = new StringBuilder();


            if (hashlist.size() == 0) {
                if (object.getFileExtension().equals(".html")) {
                    this.setContentToWebView(object.getAbsolutePath());

                }


                if (!object.getFileExtension().equals(".jpg") && !object.getFileExtension().equals(".png")) {
                    this.textLabel.setText(FontRepair.fixmyanamrfont(object.getText().trim()));
                } else {
                    this.setImageToImageView(object.getAbsolutePath());
                }
                this.hashlabel.setVisible(false);
            } else {
                this.hashlabel.setVisible(true);
                if (isProgramStart) {
                    this.fadeUp(this.hashlabel, 5);

                }


                for (String s : hashlist) {
                    msg.append(s);
                    msg.append("\n");  //this is the new line you need
                }

            }
            if (hyperlist.size() == 0) {
                if (object.getFileExtension().equals(".html")) {
                    this.setContentToWebView(object.getAbsolutePath());

                }
                boolean imgExt = Arrays.stream(imageExtensions).anyMatch(object.getFileExtension()::equals);
                if (imgExt) {
                    this.setImageToImageView(object.getAbsolutePath());
                } else {
                    this.textLabel.setText(FontRepair.fixmyanamrfont(object.getText().trim()));
                }
                boolean vdoExt = Arrays.stream(videoExtensions).anyMatch(object.getFileExtension()::equals);
                if (vdoExt) {
                    textLabel.setText("Video can only view in other mode.");
                    this.filenameLabel.setText("");
                    Node n1[] = {this.editLabel, this.previewlabel, this.textLabel, this.passwordVBox, this.attlabel, this.hashlabel, this.hyperlinklabel};
                    int i1 = 0;
                    while (i1 < n1.length) {
                        n1[i1].setDisable(true);
                        i1++;
                    }
                    modifiedDateLabel.setVisible(false);
                    createDateLabel.setVisible(false);
                    previewlabel.setVisible(false);
                    editVBox.setVisible(false);
                }


                this.hyperlinklabel.setVisible(false);
            } else {
                this.hyperlinklabel.setVisible(true);
                if (isProgramStart) {
                    this.fadeUp(this.hyperlinklabel, 5);

                }

                for (String s : hyperlist) {
                    msg.append(s);
                    msg.append("\n");  //this is the new line you need
                }

            }//end
            if (atlist.size() == 0) {
                if (object.getFileExtension().equals(".html")) {
                    this.setContentToWebView(object.getAbsolutePath());

                }
                if (!object.getFileExtension().equals(".jpg") && !object.getFileExtension().equals(".png")) {
                    this.textLabel.setText(FontRepair.fixmyanamrfont(object.getText().trim()));
                } else {
                    this.setImageToImageView(object.getAbsolutePath());
                }
                this.attlabel.setVisible(false);
            } else {

                this.attlabel.setVisible(true);
                if (isProgramStart) {
                    this.fadeUp(this.attlabel, 5);
                }

                for (String s : atlist) {
                    msg.append(s);
                    msg.append("\n");
                }
            }
            if (msg.length() != 0) {
                if (object.getFileExtension().equals(".html")) {
                    this.setContentToWebView(object.getAbsolutePath());

                }
                boolean imgExt = Arrays.stream(imageExtensions).anyMatch(object.getFileExtension()::equals);
                if (imgExt) {
                    this.setImageToImageView(object.getAbsolutePath());
                } else {
                    this.textLabel.setText(FontRepair.fixmyanamrfont(msg.toString()));
                }

                boolean vdoExt = Arrays.stream(videoExtensions).anyMatch(object.getFileExtension()::equals);
                if (vdoExt) {
                    textLabel.setText("Video can only view in other mode.");
                    this.filenameLabel.setText("");
                    Node n1[] = {this.editLabel, this.previewlabel, this.textLabel, this.passwordVBox, this.attlabel, this.hashlabel, this.hyperlinklabel};
                    int i1 = 0;
                    while (i1 < n1.length) {
                        n1[i1].setDisable(true);
                        i1++;
                    }
                    modifiedDateLabel.setVisible(false);
                    createDateLabel.setVisible(false);
                    previewlabel.setVisible(false);
                    editVBox.setVisible(false);
                }


            } else {
                if (object.getFileExtension().equals(".html")) {
                    this.setContentToWebView(object.getAbsolutePath());

                }
                if (!object.getFileExtension().equals(".jpg") && !object.getFileExtension().equals(".png")) {
                    this.textLabel.setText(FontRepair.fixmyanamrfont(object.getText().trim()));
                } else {
                    this.setImageToImageView(object.getAbsolutePath());
                }

            }
            isProgramStart = false;


            this.filenameLabel.setText(FontRepair.fixmyanamrfont(object.getName().trim()));

            if (object.getText().isEmpty()) {
                //		    this.emptytextLabel.setVisible ( true );
            } else {
                //		    this.emptytextLabel.setVisible ( false );
            }
            Date cDate = new Date(object.getCreationDate());      //convert to DateFormat
            SimpleDateFormat createDateFormat = new SimpleDateFormat(SaveSettings.ReadSettings().NoteCard_CreateDateFormat);
            String createDate = createDateFormat.format(cDate);
            createDateLabel.setText(createDate);

            Date mDate = new Date(object.getModifiedDate());        //convert to DateFormat
            SimpleDateFormat modifiedDateFormat = new SimpleDateFormat(SaveSettings.ReadSettings().NoteCard_ModifiedDateFormat);
            SimpleDateFormat modifiedTimeFormat = new SimpleDateFormat(SaveSettings.ReadSettings().NoteCard_ModifiedTimeFormat);
            String modifiedDate = modifiedDateFormat.format(mDate);
            String modifiedTime = modifiedTimeFormat.format(mDate);

            PrettyTime p = new PrettyTime();
            if (SaveSettings.ReadSettings().NoteCard_ModifiedTimePrettyFormat) {
                modifiedDateLabel.setText("Modified:" + p.format(mDate));
            } else {
                modifiedDateLabel.setText("Modified:" + modifiedDate + " " + modifiedTime);
            }

        });
    }

    private void setBackgroundColor_Service() {
        this.BackgroundColor_Service.setOnSucceeded(e -> {

            SystemConfig config = SystemConfigStorage.ReadSettings();
            if (config.isBackgroundImageSet) {
                this.changeTheme(config.glassTheme);

            } else {
                if (this.BackgroundColor_Service.getValue() != null) {

                    this.changeTheme(this.BackgroundColor_Service.getValue());
                } else {
                    this.changeTheme(SaveSettings.ReadSettings().default_BackgroundColor);
                }
            }

        });
    }

    private void setTextColor_Service() {
        this.TextColor_Service.setOnSucceeded(e -> {
            //		if ( this.TextColor_Service.getValue ( ) != null ) {
            //		    this.changeFont ( this.TextColor_Service.getValue ( ) );
            //		}
            //		else {
            //		    this.changeFont ( SaveSettings.ReadSettings ( ).default_TextColor );
            //		}
            SystemConfig config = SystemConfigStorage.ReadSettings();
            if (config.isBackgroundImageSet) {
                this.changeFont(returnRandomGradientColors());
            } else {
                if (this.TextColor_Service.getValue() != null) {
                    this.changeFont(this.TextColor_Service.getValue());
                } else {
                    this.changeFont(SaveSettings.ReadSettings().default_TextColor);
                }
            }


        });
    }

    private void StartServices() {
        this.StartTextObjectService();
        this.StartBackgroundColorService();
        this.StartTextColorService();


    }

    private void StartTextObjectService() {
        this.TextObject_Service.restart();
    }

    private void StartBackgroundColorService() {
        this.BackgroundColor_Service.restart();
    }

    private void StartTextColorService() {
        this.TextColor_Service.restart();
    }

    private void CancelServices() {
        this.CancelTextObjectService();
        this.CancelBackgroundColorService();
        this.CancelTextColorService();
    }

    private void CancelTextObjectService() {
        this.TextObject_Service.cancel();
    }

    private void CancelBackgroundColorService() {
        this.BackgroundColor_Service.cancel();
    }

    private void CancelTextColorService() {
        this.TextColor_Service.cancel();
    }

    private void UI_Perform() {


        lockiconlabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                this.lockiconlabel.setText("\ue897");
                this.visibleComponents(false);
                this.passwordVBox.setVisible(true);
            }
        });

        typefigLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                getDesktopOpen(new File(object.getAbsolutePath()));
            }
        });


        modifiedDateLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                new DateSettingsUI_Test().startUINewWindow(new Stage());
            }
        });


        closeatthasReceiveAnchorLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    atthasReceiveAnchorPane.setVisible(false);

                    textLabel.setEffect(null);
                }
            }
        });

        this.hashlabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {

                    atthasTypeLabel.setText("# ");
                    atthasReceiveAnchorPane.setVisible(true);

                    textLabel.setEffect(getFrostEffect(60, 3));

                    List<String> hashtaglist = HighLight.getHashHightLight(object.getText());
                    StringBuilder msg = new StringBuilder();

                    for (String s : hashtaglist) {
                        msg.append(s);
                        msg.append("\n");  //this is the new line you need
                    }
                    atthashReceiveTextArea.setText(FontRepair.fixmyanamrfont(msg.toString().trim()));

                }
            }
        });

        this.attlabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {

                    atthasTypeLabel.setText("@ ");
                    atthasReceiveAnchorPane.setVisible(true);

                    textLabel.setEffect(getFrostEffect(60, 3));

                    List<String> attList = HighLight.getAtHightLight(object.getText());
                    StringBuilder msg = new StringBuilder();

                    for (String s : attList) {
                        msg.append(s);
                        msg.append("\n");  //this is the new line you need
                    }
                    atthashReceiveTextArea.setText(FontRepair.fixmyanamrfont(msg.toString().trim()));

                }
            }
        });


        this.hyperlinklabel.setOnMouseClicked(e ->
        {
            if (e.getButton().equals(MouseButton.PRIMARY)) {

                atthasTypeLabel.setText("\ue157 ");
                atthasReceiveAnchorPane.setVisible(true);

                textLabel.setEffect(getFrostEffect(60, 3));

                List<String> hyperlinklist = HighLight.getHyperLinkHightLight(object.getText());
                StringBuilder msg = new StringBuilder();

                for (String s : hyperlinklist) {
                    msg.append(s);
                    msg.append("\n");  //this is the new line you need
                }
                atthashReceiveTextArea.setText(FontRepair.fixmyanamrfont(msg.toString().trim()));

            }

        });

        this.smalliconCopyLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    copyNoteMethod();
                }
            }
        });

        this.smalliconPutLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                putLabelMethod();
            }
        });


        this.browsericonLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {

                    try {
                        File file = new File(absolutePath);
                        Desktop.getDesktop().open(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        this.imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {


                        Service<Void> service = new Service<Void>() {
                            @Override
                            protected Task<Void> createTask() {
                                return new Task<Void>() {
                                    @Override
                                    protected Void call() throws Exception {
                                        try {
                                            File file = new File(absolutePath);
                                            Desktop.getDesktop().open(file);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                        return null;
                                    }
                                };
                            }
                        };//Service
                        service.restart();
                        service.setOnRunning(e -> {
                            imageView
                                    .disableProperty()
                                    .bind(service.runningProperty());
                        });
                        service.setOnSucceeded(e -> {

                        });

                    }
                }
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (NoteCardSettingStorage.getNoteCardSetting(absolutePath) != null) {
                    if (NoteCardSettingStorage.getNoteCardSetting(absolutePath).labelfolded && NoteCardSettingStorage.getNoteCardSetting(absolutePath).label != null) {
                        putlabelhbox.setVisible(false);
                        putlabelfoldbox.setVisible(true);
                        Service waitforClose = new Service() {
                            @Override
                            protected Task createTask() {
                                return new Task() {
                                    @Override
                                    protected Object call() throws Exception {
                                        Thread.sleep(2000);
                                        return null;
                                    }
                                };
                            }
                        };
                        waitforClose.restart();
                        waitforClose.setOnSucceeded(e1 -> {
                            translateWidth(putlabelfoldbox, 40);
                        });
                    }
                }

            }
        });

        this.closesizePrefixPaneLabel.setOnMouseClicked(new EventHandler<MouseEvent>()      //-> double click label to change textarea
        {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                    maskerPane.setVisible(false);
                    verticalScroll.setVisible(false);
                    horizontalScroll.setVisible(false);
                }
            }
        });

        this.cardappearanceMenuItem.setOnAction(e -> {
            notecardAnchor.getScaleX();

            new CardAppearanceUI_Test().startUINewWindow(new Stage());
        });

        this.openOptionsMenuItem.setOnAction(e -> {
            if (iscardOptionsOpen) {
                openOptionsMenuItem.setText("Open Card Options");
                iscardOptionsOpen = false;
                //		    optionsHbox.setVisible ( false );
                fadeDown(optionsHbox);
                //		    translateHeight ( optionsHbox , 0 );


            } else {
                openOptionsMenuItem.setText("Close Card Options");
                iscardOptionsOpen = true;
                optionsHbox.setVisible(true);
                fadeUp(optionsHbox, 1);

                //		    translateHeight ( optionsHbox , 34 );

                //		    Node[] nodes = { copyConentLabel };
                //		    int i = 0;
                //		    while ( i < nodes.length ) {
                //			  nodes[ i ].setVisible ( true );
                //			  i++;
                //		    }
            }


        });

        this.tabmode.setOnAction(e -> {
            new TabsUI_Test(object.getAbsolutePath()).startUINewWindow(new Stage());
        });

        this.refreshMenuItem.setOnAction(e -> {
            this.StartServices();
        });
        this.adjustSizeMenuItem.setOnAction(e -> {
            this.maskerPane.setCursor(Cursor.SE_RESIZE);
            this.maskerPane.setVisible(true);
            this.verticalScroll.setVisible(true);
            this.horizontalScroll.setVisible(true);
        });
        this.defaultSizeMenuItem.setOnAction(e -> {
            translateWidth(notecardAnchor, 320);
            translateHeight(notecardAnchor, 336);

            //				    translateHeight ( notecardAnchor , notecardAnchor.getPrefHeight ( ) - 25 );
            SaveCardWidth.SaveCardWidth(absolutePath, 320 + "");
            SaveCardHeight.SaveCardHeight(absolutePath, 336 + "");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String currentSizeText = 320 + "  X  " + 336;
                    sizePrefix.setText(currentSizeText);
                    currentsizeMenuItem.setText("Current Size : " + currentSizeText);

                }
            });

        });
        this.size1MenuItem.setOnAction(e -> {
            translateWidth(notecardAnchor, 400);
            translateHeight(notecardAnchor, 500);

            //				    translateHeight ( notecardAnchor , notecardAnchor.getPrefHeight ( ) - 25 );
            SaveCardWidth.SaveCardWidth(absolutePath, 400 + "");
            SaveCardHeight.SaveCardHeight(absolutePath, 500 + "");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String currentSizeText = 400 + "  X  " + 500;
                    sizePrefix.setText(currentSizeText);
                    currentsizeMenuItem.setText("Current Size : " + currentSizeText);

                }
            });

        });
        this.size2MenuItem.setOnAction(e -> {
            translateWidth(notecardAnchor, 500);
            translateHeight(notecardAnchor, 600);

            //				    translateHeight ( notecardAnchor , notecardAnchor.getPrefHeight ( ) - 25 );
            SaveCardWidth.SaveCardWidth(absolutePath, 500 + "");
            SaveCardHeight.SaveCardHeight(absolutePath, 600 + "");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String currentSizeText = 500 + "  X  " + 600;
                    sizePrefix.setText(currentSizeText);
                    currentsizeMenuItem.setText("Current Size : " + currentSizeText);

                }
            });

        });
        this.size3MenuItem.setOnAction(e -> {
            translateWidth(notecardAnchor, 600);
            translateHeight(notecardAnchor, 700);

            //				    translateHeight ( notecardAnchor , notecardAnchor.getPrefHeight ( ) - 25 );
            SaveCardWidth.SaveCardWidth(absolutePath, 600 + "");
            SaveCardHeight.SaveCardHeight(absolutePath, 700 + "");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String currentSizeText = 600 + "  X  " + 700;
                    sizePrefix.setText(currentSizeText);
                    currentsizeMenuItem.setText("Current Size : " + currentSizeText);

                }
            });

        });

        final KeyCombination keyComb1 = new KeyCodeCombination(KeyCode.R,
                KeyCombination.CONTROL_DOWN);
        textLabel.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            @Override
            public void handle(Event event) {
                if (keyComb1.match((KeyEvent) event)) {
                    CLipBoard.SaveToCLip(textLabel.getText().trim());
                }
            }


        });


        this.foldeLabel.setOnMouseClicked(e -> {


            NoteCardSettingStorage.setLabelFold(absolutePath);

            this.putlabelhbox.setVisible(false);
            this.putlabelfoldbox.setVisible(true);
            this.reopenLabel.setPrefSize(this.putlabelhbox.getWidth(), 30);

            Service waitforClose = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            Thread.sleep(1000);
                            return null;
                        }
                    };
                }
            };
            waitforClose.restart();
            waitforClose.setOnSucceeded(e1 -> {
                translateWidth(putlabelfoldbox, 40);
            });
        });

        this.reopenLabel.setOnMouseClicked(e -> {
            NoteCardSettingStorage.removeLabelFold(absolutePath);

            translateWidth(putlabelfoldbox, this.putlabelhbox.getWidth());
            Service waitforReopen = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            Thread.sleep(1000);
                            return null;
                        }
                    };
                }
            };
            waitforReopen.restart();
            waitforReopen.setOnSucceeded(e1 -> {
                this.putlabelhbox.setVisible(true);
                this.fadeUp(this.foldeLabel, 0.5);
                this.fadeUp(this.putlabel, 0.5);
                this.putlabelfoldbox.setVisible(false);
            });
        });


        //	  final KeyCombination keyComb1 = new KeyCodeCombination ( KeyCode.D ,
        //		    KeyCombination.CONTROL_DOWN );
        //	  this.pane.addEventHandler ( KeyEvent.KEY_RELEASED , new EventHandler ( )
        //	  {
        //		@Override
        //		public void handle ( Event event )
        //		{
        //		    if ( keyComb1.match ( ( KeyEvent ) event ) ) {
        //			  translateWidth ( notecardAnchor , notecardAnchor.getPrefHeight ( ) + 200 );
        //			  translateHeight ( notecardAnchor , notecardAnchor.getPrefWidth ( ) + 50 );
        //
        //		    }
        //		}
        //
        //	  } );
        if (startAnimation) {

            //		this.pane.setOnScroll (
            //			  new EventHandler < ScrollEvent > ( )
            //			  {
            //				@Override
            //				public void handle ( ScrollEvent event )
            //				{
            //				    double zoomFactor = 1.05;
            //				    double deltaY = event.getDeltaY ( );
            //				    if ( deltaY < 0 ) {
            //					  zoomFactor = 2.0 - zoomFactor;
            //
            //					  //				    SaveCardHeight.SaveCardHeight ( textObject.getAbsolutePath ( ) , notecardAnchor.getPrefHeight ( ) - 25 + "" );
            //					  pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
            //					  pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );
            //
            //				    }
            //				    else {
            //
            //
            //					  //				    SaveCardHeight.SaveCardHeight ( textObject.getAbsolutePath ( ) , notecardAnchor.getPrefHeight ( ) + 25 + "" );
            //					  pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
            //					  pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );
            //				    }
            //
            //				    //								pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
            //				    //								pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );
            //
            //
            //				    event.consume ( );
            //				}
            //			  } );

            this.maskerPane.setOnScroll(
                    new EventHandler<ScrollEvent>() {
                        @Override
                        public void handle(ScrollEvent event) {
                            double zoomFactor = 1.05;
                            double deltaY = event.getDeltaY();
                            if (deltaY < 0) {
                                zoomFactor = 2.0 - zoomFactor;

                                translateHeight(notecardAnchor, notecardAnchor.getPrefHeight() - 75);
                                translateWidth(notecardAnchor, notecardAnchor.getPrefWidth() - 75);
                                //				    translateHeight ( notecardAnchor , notecardAnchor.getPrefHeight ( ) - 25 );
                                SaveCardWidth.SaveCardWidth(absolutePath, notecardAnchor.getPrefWidth() - 75 + "");
                                SaveCardHeight.SaveCardHeight(absolutePath, notecardAnchor.getPrefHeight() - 75 + "");

                                maskerPane.setVisible(true);


                                //				    SaveCardHeight.SaveCardHeight ( textObject.getAbsolutePath ( ) , notecardAnchor.getPrefHeight ( ) - 25 + "" );

                                //				    pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
                                //				    pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );

                            } else {
                                translateHeight(notecardAnchor, notecardAnchor.getPrefHeight() + 75);
                                translateWidth(notecardAnchor, notecardAnchor.getPrefWidth() + 75);
                                //				    translateHeight ( notecardAnchor , notecardAnchor.getPrefHeight ( ) + 25 );
                                SaveCardWidth.SaveCardWidth(absolutePath, notecardAnchor.getPrefWidth() + 75 + "");
                                SaveCardHeight.SaveCardHeight(absolutePath, notecardAnchor.getPrefHeight() + 75 + "");

                                maskerPane.setVisible(true);


                                //				    SaveCardHeight.SaveCardHeight ( textObject.getAbsolutePath ( ) , notecardAnchor.getPrefHeight ( ) + 25 + "" );
                                //				    pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
                                //				    pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );
                            }
                            System.out.println(zoomFactor);
                            //								pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
                            //								pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );


                            event.consume();
                        }
                    });

            this.verticalScroll.setOnScroll(
                    new EventHandler<ScrollEvent>() {
                        @Override
                        public void handle(ScrollEvent event) {
                            double zoomFactor = 1.05;
                            double deltaY = event.getDeltaY();
                            if (deltaY < 0) {
                                zoomFactor = 2.0 - zoomFactor;

                                translateHeight(notecardAnchor, notecardAnchor.getPrefHeight() - 75);
                                //				    translateHeight ( notecardAnchor , notecardAnchor.getPrefHeight ( ) - 25 );
                                SaveCardHeight.SaveCardHeight(absolutePath, notecardAnchor.getPrefHeight() - 75 + "");

                                maskerPane.setVisible(true);


                                //				    SaveCardHeight.SaveCardHeight ( textObject.getAbsolutePath ( ) , notecardAnchor.getPrefHeight ( ) - 25 + "" );

                                //				    pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
                                //				    pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );

                            } else {
                                translateHeight(notecardAnchor, notecardAnchor.getPrefHeight() + 75);
                                //				    translateHeight ( notecardAnchor , notecardAnchor.getPrefHeight ( ) + 25 );
                                SaveCardHeight.SaveCardHeight(absolutePath, notecardAnchor.getPrefHeight() + 75 + "");

                                maskerPane.setVisible(true);


                                //				    SaveCardHeight.SaveCardHeight ( textObject.getAbsolutePath ( ) , notecardAnchor.getPrefHeight ( ) + 25 + "" );
                                //				    pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
                                //				    pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );
                            }
                            System.out.println(zoomFactor);
                            //								pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
                            //								pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );


                            event.consume();
                        }
                    });

            this.horizontalScroll.setOnScroll(
                    new EventHandler<ScrollEvent>() {
                        @Override
                        public void handle(ScrollEvent event) {
                            double zoomFactor = 1.05;
                            double deltaY = event.getDeltaY();
                            if (deltaY < 0) {
                                zoomFactor = 2.0 - zoomFactor;

                                translateWidth(notecardAnchor, notecardAnchor.getPrefWidth() - 75);
                                //				    translateHeight ( notecardAnchor , notecardAnchor.getPrefHeight ( ) - 25 );
                                SaveCardWidth.SaveCardWidth(absolutePath, notecardAnchor.getPrefWidth() - 75 + "");

                                maskerPane.setVisible(true);


                                //				    SaveCardHeight.SaveCardHeight ( textObject.getAbsolutePath ( ) , notecardAnchor.getPrefHeight ( ) - 25 + "" );

                                //				    pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
                                //				    pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );

                            } else {
                                translateWidth(notecardAnchor, notecardAnchor.getPrefWidth() + 75);
                                //				    translateHeight ( notecardAnchor , notecardAnchor.getPrefHeight ( ) + 25 );
                                SaveCardWidth.SaveCardWidth(absolutePath, notecardAnchor.getPrefWidth() + 75 + "");

                                maskerPane.setVisible(true);


                                //				    SaveCardHeight.SaveCardHeight ( textObject.getAbsolutePath ( ) , notecardAnchor.getPrefHeight ( ) + 25 + "" );
                                //				    pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
                                //				    pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );
                            }
                            System.out.println(zoomFactor);
                            //								pane.setScaleX ( pane.getScaleX ( ) * zoomFactor );
                            //								pane.setScaleY ( pane.getScaleY ( ) * zoomFactor );


                            event.consume();
                        }
                    });
        } else {

        }


        this.puttextfield.setOnMouseExited(e -> {
            putlabelhbox.setVisible(true);
            putlabelhbox2.setVisible(false);
            putlabel.setText(FontRepair.fixmyanamrfont(puttextfield.getText().trim()));
            //		putlabel.setVisible ( true );
        });


        this.showPasswordLabel.setOnMouseClicked(e -> {
            String text = this.passwordfield.getText().trim();
            this.passwordTextfield.setText(FontRepair.fixmyanamrfont(text.trim()));
            this.passwordbox.setVisible(false);
            this.textfieldpasswordBox.setVisible(true);
        });

        this.hidePasswordLabel.setOnMouseClicked(e -> {
            String text = this.passwordTextfield.getText().trim();
            this.passwordfield.setText(FontRepair.fixmyanamrfont(text.trim()));
            this.passwordbox.setVisible(true);
            this.textfieldpasswordBox.setVisible(false);
        });

        this.pane.setOnMouseEntered(e -> {
            this.StartServices();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (SaveSettings.ReadSettings().pinPath != null) {
                        if (SaveSettings.ReadSettings().pinPath.equals(absolutePath)) {
                            pinlabel.setVisible(true);
                        } else {
                            pinlabel.setVisible(false);
                        }
                    } else {
                        pinlabel.setVisible(false);
                    }
                }
            });

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String currentSizeText = notecardAnchor.getWidth() + "  X  " + notecardAnchor.getHeight();
                    sizePrefix.setText(currentSizeText);
                    currentsizeMenuItem.setText("Current Size : " + currentSizeText);

                    if (notecardAnchor.getWidth() == 320 && notecardAnchor.getHeight() == 336) {
                        defaultSizeMenuItem.setSelected(true);
                    } else {
                        defaultSizeMenuItem.setSelected(false);
                    }

                    if (notecardAnchor.getWidth() == 400 && notecardAnchor.getHeight() == 500) {
                        size1MenuItem.setSelected(true);
                    } else {
                        size1MenuItem.setSelected(false);
                    }
                    if (notecardAnchor.getWidth() == 500 && notecardAnchor.getHeight() == 600) {
                        size2MenuItem.setSelected(true);
                    } else {
                        size2MenuItem.setSelected(false);
                    }
                    if (notecardAnchor.getWidth() == 600 && notecardAnchor.getHeight() == 700) {
                        size3MenuItem.setSelected(true);
                    } else {
                        size3MenuItem.setSelected(false);
                    }


                }
            });


        });

        this.pane.setOnMouseExited(e -> {
            this.StartServices();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (SaveSettings.ReadSettings().pinPath != null) {
                        if (SaveSettings.ReadSettings().pinPath.equals(absolutePath)) {
                            pinlabel.setVisible(true);


                        } else {
                            pinlabel.setVisible(false);
                        }
                    } else {
                        pinlabel.setVisible(false);
                    }
                }
            });

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String currentSizeText = notecardAnchor.getWidth() + "  X  " + notecardAnchor.getHeight();
                    sizePrefix.setText(currentSizeText);
                    currentsizeMenuItem.setText("Current Size : " + currentSizeText);

                    if (notecardAnchor.getWidth() == 320 && notecardAnchor.getHeight() == 336) {
                        defaultSizeMenuItem.setSelected(true);
                    } else {
                        defaultSizeMenuItem.setSelected(false);
                    }
                }
            });


        });


        //	  this.textLabel.setOnMouseEntered ( e -> {//
        //
        //		if ( NoteCardSettingStorage.getNoteCardSetting ( textObject.getAbsolutePath ( ) ) != null ) {
        //
        //		    if ( NoteCardSettingStorage.getNoteCardSetting ( textObject.getAbsolutePath ( ) ).label != null ) {
        //
        //		   				putlabel.setText ( NoteCardSettingStorage.getNoteCardSetting ( textObject.getAbsolutePath ( ) ).label );
        //
        //		   				Service waitforReopen = new Service ( )
        //		   				{
        //		   				    @Override
        //		   				    protected Task createTask ( )
        //		   				    {
        //		   					  return new Task ( )
        //		   					  {
        //		   						@Override
        //		   						protected Object call ( ) throws Exception
        //		   						{
        //		   						    //					  Thread.sleep ( 1000 );
        //		   						    return null;
        //		   						}
        //		   					  };
        //		   				    }
        //		   				};
        //		   				waitforReopen.restart ( );
        //		   				waitforReopen.setOnSucceeded ( e1 -> {
        //		   				    translateWidth ( putlabelfoldbox , this.putlabelhbox.getWidth ( ) );
        //		   				    this.putlabelhbox.setVisible ( true );
        //		   				    //			  this.fadeUp ( this.foldeLabel , 0.5 );
        //		   				    //			  this.fadeUp ( this.putlabel , 0.5 );
        //		   				    this.putlabelfoldbox.setVisible ( false );
        //		   				} );
        //		   			  }
        //
        //
        //		    if ( NoteCardSettingStorage.getNoteCardSetting ( textObject.getAbsolutePath ( ) ).labelfolded ) {
        //			  this.putlabelhbox.setVisible ( false );
        //			  this.putlabelfoldbox.setVisible ( true );
        //			  this.reopenLabel.setPrefSize ( this.putlabelhbox.getWidth ( ) , 30 );
        //			  Service waitforClose = new Service ( )
        //			  {
        //				@Override
        //				protected Task createTask ( )
        //				{
        //				    return new Task ( )
        //				    {
        //					  @Override
        //					  protected Object call ( ) throws Exception
        //					  {
        //						//					  Thread.sleep ( 1000 );
        //						return null;
        //					  }
        //				    };
        //				}
        //			  };
        //			  if(NoteCardSettingStorage.getNoteCardSetting ( textObject.getAbsolutePath () ).label!=null){
        //				waitforClose.restart ( );
        //			  }
        //
        //			  waitforClose.setOnSucceeded ( e1 -> {
        //				translateWidth ( putlabelfoldbox , 40 );
        //			  } );
        //		    }
        //		    else {
        //
        //
        //
        //		    }
        //		}
        //
        //	  } );

        this.textLabel.setOnMouseClicked(e -> {
            //		TextArea textArea = new TextArea ( TextObject_Service.getValue ().getText () );
            //		textArea.setCache ( true );
            //		textArea.setCacheHint ( CacheHint.SPEED );
            //		textArea.setCacheHint ( CacheHint.QUALITY );
            //		textArea.setCacheHint ( CacheHint.SCALE );
            //		String style = String.format ( "-fx-background-color: %s;" , BackgroundColor_Service.getValue ( ) );
            //		textArea.setStyle ( style + "-fx-text-fill:" + TextColor_Service.getValue ( ) + ";" );
            //		textArea.setWrapText ( true );
            //		//		textArea.setStyle ( "-fx-background-color:" + BackgroundColor_Service.getValue ( ) + ";" );
            //		textArea.setEditable ( false );
            //		AnchorPane.setTopAnchor ( textArea , 0.0 );
            //		AnchorPane.setRightAnchor ( textArea , 0.0 );
            //		AnchorPane.setBottomAnchor ( textArea , 0.0 );
            //		AnchorPane.setLeftAnchor ( textArea , 0.0 );
            //		AnchorPane vBox = new AnchorPane ( textArea );
            //		vBox.setPrefSize ( 400 , 400 );
            //		//Create PopOver and add look and feel
            //		popOver = new PopOver ( vBox );
            //		popOver.setArrowLocation ( PopOver.ArrowLocation.LEFT_CENTER );
            //		popOver.setDetachable ( true );
            //		popOver.setCornerRadius ( 15 );
            //		popOver.setAutoFix ( true );
            //		popOver.setTitle ( "Preview" );
            //		popOver.setHideOnEscape ( true );
            //		if ( popOver.isShowing ( ) == false ) {
            //		    popOver.show ( pane );
            //		}
            //		else {
            //		    popOver.hide ( );
            //		}

            //		popOver.setArrowSize ( 15 );
        });
        //	  this.pane.setOnMouseExited ( e -> {
        //		popOver.hide ( );
        //	  } );

        //	  this.pinNoteMenuItem.setOnAction ( e -> {
        //
        //		Settings settings = SaveSettings.ReadSettings ( );
        //		settings.pinPath = this.textObject.getAbsolutePath ( );
        //		SaveSettings.SaveSettings ( settings );
        //	  } );

        this.previewNoteMenuItem.setOnAction(e -> {
            this.previewPopOver1();
        });

        this.editNoteMenuItem.setOnAction(e -> {
            textLabel.setVisible(false);      //change label to textarea
            textArea.setVisible(true);
            //		textArea.setText ( textLabel.getText ( ) );
            textArea.setText(FontRepair.fixmyanamrfont(TextObject_Service.getValue().getText().trim()));

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textArea.requestFocus();
                }
            });
        });


        this.deleteNoteMenuItem.setOnAction(e -> {
            FileHandlings.deleteFile(absolutePath);
        });
        this.putlabelMenuItem.setOnAction(e -> {
            this.putLabelMethod();
        });

        this.previewmode.setOnAction(e -> {
            previewPopOver();
        });

        this.copyNoteMenuItem.setOnAction(e -> {
            copyNoteMethod();

        });
        this.copyNoteAbsolutePathMenuItem.setOnAction(e -> {
            CLipBoard.SaveToCLip(TextObject_Service.getValue().getAbsolutePath());
        });
        this.shape1.setOnAction(e -> {
            this.pane.getStyleClass().add("shape2");
        });


        //	  this.refreshMenu.setOnAction ( e->{
        //	      this.StartServices ();
        //	  } );


        this.textLabel.setOnMouseClicked(new EventHandler<MouseEvent>()      //-> double click label to change textarea
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.isControlDown()) {
                        previewPopOver1();
                    }
                    if (mouseEvent.getClickCount() == 2) {
                        textLabel.setVisible(false);      //change label to textarea
                        textArea.setVisible(true);
                        //				textArea.setText ( textLabel.getText ( ) );
                        String tex = FontRepair.fixmyanamrfont(TextObject_Service.getValue().getText().trim());

                        if (tex != null) {
                            textArea.setText(tex);
                        }


                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                textArea.requestFocus();
                            }
                        });

                    }
                }
//                if (mouseEvent.getButton().equals(MouseButton.MIDDLE)) {
//                    if (mouseEvent.isControlDown()) {
//                        spellingMode();
//                    }
//                }


            }
        });

        //	  this.textArea.focusedProperty ( ).addListener ( new ChangeListener < Boolean > ( )
        //	  {
        //		@Override
        //		public void changed ( ObservableValue < ? extends Boolean > arg0 , Boolean oldPropertyValue , Boolean newPropertyValue )
        //		{
        //		    if ( newPropertyValue ) {
        //			  CancelTextObjectService ( );
        //		    }
        //		    else {
        //			  textLabel.setVisible ( true );      //change textarea to label
        //			  textArea.setVisible ( false );
        //			  textLabel.setText ( textArea.getText ( ) );
        //
        //			  Save ( textObject.getAbsolutePath ( ) , textArea.getText ( ).trim ( ) );
        //
        //			  StartTextObjectService ( );
        //		    }
        //		}
        //	  } );

        TextField[] textfields = {passwordfield, passwordTextfield};      //Fields
        Arrays.stream(textfields).forEach(e -> {
            e.textProperty().addListener((obs, oldText, newText) -> {      //textListener
                // do what you need with newText here, e.g.
                if (newText != null) {
                    applyLabel.setDisable(newText.isEmpty());
                    clearLabel.setDisable(newText.isEmpty());
                }
            });

            e.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    applyMethod();
                }
            });

        });


        this.clearLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                passwordfield.clear();
                passwordTextfield.clear();
            }
        });

        this.applyLabel.setOnMouseClicked(e -> {
            applyMethod();

        });

        this.textArea.setOnMouseExited(e -> {
            textLabel.setVisible(true);      //change textarea to label
            textArea.setVisible(false);
            //		textLabel.setText ( textArea.getText ( ) );
            //		textLabel.setText ( "Hello" );

            Save(absolutePath, textArea.getText().trim());

            StartTextObjectService();
        });

        //	  this.textArea.textProperty ( ).addListener ( ( obs , old , niu ) -> {
        //		Service < Void > saveService = new Service < Void > ( )
        //		{
        //		    @Override
        //		    protected Task < Void > createTask ( )
        //		    {
        //			  return new Task < Void > ( )
        //			  {
        //				@Override
        //				protected Void call ( ) throws Exception
        //				{
        //
        //				    return null;
        //				}
        //			  };
        //		    }
        //		};
        //		saveService.restart ( );
        //		saveService.setOnSucceeded ( e -> {
        //		    Save ( absolutePath , textArea.getText ( ).trim ( ) );
        //		} );
        //	  } );

        this.puttextfield.textProperty().addListener((obs, old, niu) -> {
            if (puttextfield.getText().trim().isEmpty()) {
                NoteCardSettingStorage.setLabelText(absolutePath, null);
                NoteCardSettingStorage.setLabelFold(absolutePath);
            } else {
                NoteCardSettingStorage.setLabelText(absolutePath, this.puttextfield.getText().trim());
                NoteCardSettingStorage.removeLabelFold(absolutePath);
            }

        });

        this.previewlabel.setOnMouseClicked(e -> {
            //		this.previewPopOver ( this.previewlabel , "Hello World" ,"black" , "white" );
            previewPopOver1();
        });

        //	  this.previewbtn.setOnAction ( e->{
        //		this.previewPopOver ( this.previewbtn , "Hello World" ,"black" , "white" );
        //	  } );


        this.ContextActions();
    }

    private Effect getFrostEffect(double blur_amount, int iteration) {
        Effect frostEffect =
                new BoxBlur(blur_amount, blur_amount, iteration);

        return frostEffect;
    }

    private void copyNoteMethod() {
        this.maskerPane.setVisible(true);
        this.sizePrefix.setText("Copied");
        this.closesizePrefixPaneLabel.setVisible(false);
        this.pane.setDisable(true);
        PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(2));
        pauseTransition1.setOnFinished(e1 -> {
                    this.maskerPane.setVisible(false);
                    this.closesizePrefixPaneLabel.setVisible(true);
                    this.pane.setDisable(false);

                }
        );
        pauseTransition1.play();

        CLipBoard.SaveToCLip(object.getText().trim());
    }

    private void putLabelMethod() {
        this.maskerPane.setVisible(true);
        this.sizePrefix.setText("Put Label");
        this.closesizePrefixPaneLabel.setVisible(false);
        this.pane.setDisable(true);
        PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(2));
        pauseTransition1.setOnFinished(e1 -> {
                    this.maskerPane.setVisible(false);
                    this.closesizePrefixPaneLabel.setVisible(true);
                    this.pane.setDisable(false);

                }
        );
        pauseTransition1.play();
        this.putlabelhbox.setVisible(true);
    }


    private void ContextActions() {


        this.openNotePad.setOnAction(e -> {
            try {
                File file = new File(absolutePath);
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        this.openExplorer.setOnAction(e -> {
            try {
                File file = new File(absolutePath);
                Desktop.getDesktop().open(file.getParentFile());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void UI_Binding() {
        this.notecardAnchor.setUserData(absolutePath);
    }

    private void fadeUp(Node node, double rate) {
        node.setCache(true);
        node.setCacheHint(CacheHint.QUALITY);
        node.setCacheHint(CacheHint.SPEED);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.seconds(rate));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setNode(node);
        fade.play();
    }

    private void fadeDown(Node node) {
        node.setCache(true);
        node.setCacheHint(CacheHint.QUALITY);
        node.setCacheHint(CacheHint.SPEED);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.seconds(1));
        fade.setFromValue(10);
        fade.setToValue(0);
        fade.setNode(node);
        fade.play();
    }

    private void applyMethod() {
        if (!passwordfield.getText().isEmpty()) {
            String text = passwordfield.getText().trim();
//            if (text.equals(NoteCardSettingStorage.getNoteCardSetting(absolutePath).password)) {
            if (text.equals(SystemConfigStorage.ReadSettings().password)) {
                this.passwordVBox.setVisible(false);
                this.visibleComponents(true);
                this.lockiconlabel.setText("\ue898");
                //			  this.waitServices ( );
                //			  this.passwordVBox.setVisible ( false );
            } else {
                this.incorrectLabel.setText("Your password is incorrect.");
                this.incorrectLabel.setVisible(true);
            }
        }

        if (!passwordTextfield.getText().isEmpty()) {
            String text = passwordTextfield.getText().trim();
            if (text.equals(NoteCardSettingStorage.getNoteCardSetting(absolutePath).password)) {
                this.passwordVBox.setVisible(false);
                this.visibleComponents(true);
                this.lockiconlabel.setText("\ue898");

                //			  this.waitServices ( );
                //			  this.passwordVBox.setVisible ( false );
            } else {
                this.incorrectLabel.setText("Your password is incorrect.");
                this.incorrectLabel.setVisible(true);
            }
        }

    }

    private void setImageToImageView(String absolutePath) {
        Service<Image> service = new Service<Image>() {
            @Override
            protected Task<Image> createTask() {
                return new Task<Image>() {
                    @Override
                    protected Image call() throws Exception {
                        File file = new File(absolutePath.trim());
                        Image image = new Image(file.toURI().toString());
                        return image;
                    }
                };
            }
        };
        service.restart();
        service.setOnRunning(e -> {
            this.textLabel.setText("fixing image..");
        });
        service.setOnSucceeded(e -> {
            this.pane.setVisible(false);
            this.imageView.setVisible(true);
            this.imageViewVbox.setVisible(true);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    imageView.setFitWidth(pane.getWidth());
                    imageView.setFitHeight(pane.getHeight());
                    Rectangle clip = new Rectangle(
                            imageView.getFitWidth(), imageView.getFitHeight()
                    );
                    clip.setArcWidth(20);
                    clip.setArcHeight(20);
                    imageView.setClip(clip);


                    imageView.setImage(service.getValue());

                }
            });
        });

    }

    private void setVideoToMediaView(String absolutePath) {
        Service<MediaPlayer> mediaPlayerService = new Service<MediaPlayer>() {
            @Override
            protected Task<MediaPlayer> createTask() {
                return new Task<MediaPlayer>() {
                    @Override
                    protected MediaPlayer call() throws Exception {
                        Media media = new Media(new File(absolutePath).toURI().toString());

                        MediaPlayer mediaplayer = new MediaPlayer(media);
                        return mediaplayer;
                    }
                };
            }
        };
        mediaPlayerService.restart();
        mediaPlayerService.setOnSucceeded(e -> {
            mediaViewVbox.setVisible(true);
            imageViewVbox.setVisible(false);
            webviewAnchor.setVisible(false);
            maskerPane.setVisible(false);


            MediaPlayer mediaPlayer = mediaPlayerService.getValue();
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.autoPlayProperty().set(true);
            mediaPlayer.setVolume(20);
        });
    }

    private void setContentToWebView(String absolutePath) {
        pane.setVisible(false);
        imageViewVbox.setVisible(false);
        mediaViewVbox.setVisible(false);
        webviewAnchor.setVisible(true);
        webview.setVisible(true);
        openinbrowserLabel.setVisible(true);
        browsericonLabel.setVisible(true);

        WebEngine webEngine = webview.getEngine();

        File f = new File(absolutePath);
        webEngine.load(f.toURI().toString());
    }

    public boolean bagOfWords(String str) {
        String[] words = {".txt", ".png", ".jpg"};
        return (Arrays.asList(words).contains(str));
    }

    public void getDesktopOpen(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void UI_Prepare() {

        //ChangeTypeIcon


        //	  translateHeight ( optionsHbox , 0 );
        optionsHbox.setVisible(false);
        mediaViewVbox.setVisible(false);


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String currentSizeText = notecardAnchor.getWidth() + "  X  " + notecardAnchor.getHeight();
                sizePrefix.setText(currentSizeText);
                currentsizeMenuItem.setText("Current Size : " + currentSizeText);

                if (notecardAnchor.getWidth() == 320 && notecardAnchor.getHeight() == 336) {
                    defaultSizeMenuItem.setSelected(true);
                } else {
                    defaultSizeMenuItem.setSelected(false);
                }
            }
        });

        Node node[] = {webviewAnchor, openinbrowserLabel, browsericonLabel, webview, imageViewVbox, imageView, verticalScroll, horizontalScroll, minNoteCardLabel, closeNoteCardLabel, maskerPane, hashlabel, attlabel, lockiconlabel, putlabelfoldbox, atthasReceiveAnchorPane};
        int i = 0;
        while (i < node.length) {
            node[i].setVisible(false);
            i++;
        }


        this.pane.getStyleClass().add("radius35");


        this.editLabel.setLayoutX(-10);


        if (SaveCardWidth.GetCardWidth(absolutePath) != null) {
            width = Double.parseDouble(SaveCardWidth.GetCardWidth(absolutePath));
            //		this.notecardAnchor.setPrefWidth ( width );
        }
        if (SaveCardHeight.GetCardHeight(absolutePath) != null) {
            height = Double.parseDouble(SaveCardHeight.GetCardHeight(absolutePath));
            //		this.notecardAnchor.setPrefWidth ( height );
        }

        if (this.prewidth != 0) {
            this.notecardAnchor.setPrefSize(prewidth, preheight);
        } else {
            this.notecardAnchor.setPrefSize(width, height);
        }


        //	  this.fadeUp ( this.notecardAnchor , 2 );
        //	  this.fadeUp ( this.textLabel , 5 );


        // Set up a Fade Transition


        NoteCardSetting ns = NoteCardSettingStorage.getNoteCardSetting(absolutePath);
        if (ns != null) {
            if (ns != null) {
                if (ns.password == null) {
                    if (ns.label != null) {
                        this.putlabel.setText(ns.label);
                        this.putlabelhbox.setVisible(true);
                    } else {
                        this.putlabelhbox.setVisible(false);
                    }

                } else {
                    this.putlabelhbox.setVisible(false);
                }

            } else {
                this.putlabelhbox.setVisible(false);
            }
        } else {
            this.putlabelhbox.setVisible(false);
        }
        //	  this.putlabelhbox.setVisible ( true );


        if (SaveSettings.ReadSettings().pinPath != null) {
            if (SaveSettings.ReadSettings().pinPath.equals(absolutePath)) {
                this.pinNoteMenuItem.setDisable(true);
                this.pinNoteMenuItem.setText("Pinned (Drag outside to Unpin)");
                this.unpinNoteMenuItem.setDisable(false);
            } else {
                this.pinNoteMenuItem.setDisable(false);
                this.pinNoteMenuItem.setText("Pin Note");
                this.unpinNoteMenuItem.setDisable(true);
            }
        } else {
            this.pinNoteMenuItem.setText("Pin Note");
            this.unpinNoteMenuItem.setDisable(true);
        }


        //	  if ( SaveControlShortcut.GetFilePath ( this.textObject.getAbsolutePath ( ) ) != null && SaveCardHeight.GetCardHeight ( this.textObject.getAbsolutePath ( ) ) != null ) {
        //		double width = Double.parseDouble ( SaveControlShortcut.GetFilePath ( this.textObject.getAbsolutePath ( ) ) );
        //		double height = Double.parseDouble ( SaveCardHeight.GetCardHeight ( this.textObject.getAbsolutePath ( ) ) );
        //		this.notecardAnchor.setPrefSize ( width , height );
        //	  }
        //	this.notecardAnchor.setPrefHeight ( 500 );

        //

        this.pinlabel.setVisible(false);

        // TODO: 20/9/2021
        //	  NoteCardSettingStorage.set
        //	  NoteCardSetting ns = NoteCardSettingStorage.getNoteCardSetting ( this.textObject.getAbsolutePath ( ) );
        if (ns != null) {
            if (ns.password == null) {
                setPasswordAllNote.setDisable(false);
                setPasswordOnlyThisNote.setDisable(false);
                removePasswordMenuItem.setDisable(true);
                removePasswordAllMenuItem.setDisable(true);
            } else {
                setPasswordAllNote.setDisable(false);
                setPasswordOnlyThisNote.setDisable(true);
                removePasswordMenuItem.setDisable(false);
                removePasswordAllMenuItem.setDisable(false);
            }
        } else {
            removePasswordMenuItem.setDisable(true);
            removePasswordAllMenuItem.setDisable(true);
        }


        if (SaveSettings.ReadSettings().pinPath != null) {
            if (SaveSettings.ReadSettings().pinPath.equals(absolutePath)) {
                this.pinlabel.setVisible(true);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ScaleTransition trans = new ScaleTransition(Duration.seconds(4), pinlabel);
                        trans.setFromX(0.50);
                        trans.setToX(1.0);
                        trans.setFromY(0.50);
                        trans.setToY(1.0);
                        // Let the animation run forever
                        trans.setCycleCount(1);
                        // Reverse direction on alternating cycles
                        trans.setAutoReverse(true);
                        // Play the Animation
                        trans.play();
                        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), pinlabel);
                        rotateTransition.setFromAngle(0.0);
                        rotateTransition.setToAngle(28800.0);
                        // Let the animation run two times
                        rotateTransition.setCycleCount(1);
                        // Reverse direction on alternating cycles
                        rotateTransition.setAutoReverse(false);

                        // Create and start a Parallel Transition
                        ParallelTransition parTransition = new ParallelTransition();
                        parTransition.setNode(pinlabel);
                        // Add the Children to the ParallelTransition
                        parTransition.getChildren().addAll(trans, rotateTransition);
                        // Let the animation run forever
                        //        parTransition.setCycleCount(PathTransition.INDEFINITE);
                        //		parTransition.setCycleCount ( PathTransition.INDEFINITE );
                        parTransition.setAutoReverse(false);
                        parTransition.setCycleCount(1);
                        // Play the Animation
                        parTransition.play();
                    }
                });

            } else {
                this.pinlabel.setVisible(false);
            }
        }


        this.notecardAnchor.setCache(true);
        this.notecardAnchor.setCacheHint(CacheHint.QUALITY);
        //	  this.notecardAnchor.setCacheHint ( CacheHint.SPEED );
        //	  this.notecardAnchor.setCacheHint ( CacheHint.SCALE );
        //	  this.emptytextLabel.setVisible ( false );

        //	  this.Settings_ScheduleService.setRestartOnFailure ( true );
        //	  this.Settings_ScheduleService.setPeriod ( Duration.seconds ( SaveSettings.ReadSettings ( ).SYNC_DELAY ) );
        //	  this.Settings_ScheduleService.setOnSucceeded ( e -> {
        //		if ( SaveSettings.ReadSettings ( ).SYNC_DELAY_PERMIT ) {
        //		    this.StartScheduleServices ( );
        //		}
        //
        //	  } );

        //	  if ( SaveSettings.ReadSettings ( ).SYNC_DELAY_PERMIT ) {
        //		this.StartScheduleServices ();
        //	  }else {
        //		this.StartServices ( );
        //	  }
        NoteCardSetting noteCardSetting = NoteCardSettingStorage.getNoteCardSetting(absolutePath);

        if (noteCardSetting != null) {
            if (noteCardSetting.password != null) {
                System.out.println(noteCardSetting.password);
                this.lockiconlabel.setVisible(true);

                this.visibleComponents(false);
                this.passwordVBox.setVisible(true);

            } else {
                this.visibleComponents(true);
                this.passwordVBox.setVisible(false);
            }
        } else {
            this.visibleComponents(true);
            this.passwordVBox.setVisible(false);
        }


    }//Prepare

    private void translateWidth(Pane node, double endValue) {
        node.setCache(true);
        node.setCacheHint(CacheHint.SPEED);
        node.setCacheHint(CacheHint.QUALITY);
        node.setCacheHint(CacheHint.SCALE);

        KeyValue start = new KeyValue(node.prefWidthProperty(), node.getWidth());
        KeyValue end = new KeyValue(node.prefWidthProperty(), endValue);
        KeyValue cache = new KeyValue(node.cacheHintProperty(), CacheHint.SPEED);
        KeyValue cache2 = new KeyValue(node.cacheHintProperty(), CacheHint.QUALITY);
        KeyValue cache3 = new KeyValue(node.cacheHintProperty(), CacheHint.SCALE);

        KeyFrame startFrame = new KeyFrame(Duration.ZERO, start, cache, cache2, cache3);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(0.4), end, cache, cache2, cache3);

        Timeline timeline = new Timeline(startFrame, endFrame);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(1);
        timeline.play();


        //	  Timeline timeline = new Timeline (
        //		    new KeyFrame ( Duration.ZERO , new KeyValue ( node.prefWidthProperty ( ) , node.getWidth ( ) ) ) ,
        //		    new KeyFrame ( Duration.seconds ( 0.5 ) , new KeyValue ( node.prefWidthProperty ( ) , endValue ) ) );
        //	  timeline.play ( );
    }

    private void translateHeight(Pane node, double endValue) {
        node.setCache(true);
        node.setCacheHint(CacheHint.SPEED);
        node.setCacheHint(CacheHint.QUALITY);
        node.setCacheHint(CacheHint.SCALE);

        KeyValue start = new KeyValue(node.prefHeightProperty(), node.getHeight());
        KeyValue end = new KeyValue(node.prefHeightProperty(), endValue);
        KeyValue cache = new KeyValue(node.cacheHintProperty(), CacheHint.SPEED);
        KeyValue cache2 = new KeyValue(node.cacheHintProperty(), CacheHint.QUALITY);
        KeyValue cache3 = new KeyValue(node.cacheHintProperty(), CacheHint.SCALE);

        KeyFrame startFrame = new KeyFrame(Duration.ZERO, start, cache, cache2, cache3);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(0.3), end, cache, cache2, cache3);

        Timeline timeline = new Timeline(startFrame, endFrame);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(1);
        timeline.play();


        //	  Timeline timeline = new Timeline (
        //		    new KeyFrame ( Duration.ZERO , new KeyValue ( node.prefWidthProperty ( ) , node.getWidth ( ) ) ) ,
        //		    new KeyFrame ( Duration.seconds ( 0.5 ) , new KeyValue ( node.prefWidthProperty ( ) , endValue ) ) );
        //	  timeline.play ( );
    }

    void changeTheme(String hex) {
        Node[] nodes = {pane, optionsHbox, passwordVBox, maskerPane, atthasReceiveAnchorPane};
        int i = 0;
        while (i < nodes.length) {
            Node node = nodes[i];
            node.setStyle("-fx-background-color: " + hex + ";");

            i++;
        }

        //	  pane.setStyle ( "-fx-background-color: " + hex + ";" );
        //	  pane.getStyleClass ( ).add ( "shape2" );
        //	  passwordVBox.setStyle ( "-fx-background-color: " + hex + ";" );
        //	  passwordVBox.getStyleClass ( ).add ( "shape1" );

    }

    void previewPopOver() {
        String bgcolor = SaveCardBackgroundColor.GetCardBackgroundColor(absolutePath);
        if (bgcolor == null) {
            bgcolor = SaveSettings.ReadSettings().default_BackgroundColor;
        }
        String txtcolor = SaveTextColor.GetCardTextColor(absolutePath);
        if (txtcolor == null) {
            txtcolor = SaveSettings.ReadSettings().default_TextColor;
        }

        //	  LaunchClass launch = new LaunchClass ( );
        //	  com.nxotespace.DictionaryMode.spelling.Dictionary dic = launch.getDictionary ( );
        //	  AutoSpellingTextArea popUpTextArea = new AutoSpellingTextArea ( launch.getAutoComplete ( ) , launch.getSpellingSuggest ( dic ) , dic );
        //	  popUpTextArea.setWrapText ( true );
        //	  popUpTextArea.setAutoComplete ( true );
        //	  popUpTextArea.setSpelling ( true );
        //	  popUpTextArea.appendText ( TextObject_Service.getValue ().getText () );
        TextArea popUpTextArea = new TextArea(FontRepair.fixmyanamrfont(TextObject_Service.getValue().getText().trim()));
        popUpTextArea.getStyleClass().add("font-pyidaungsu");
        popUpTextArea.setCache(true);
        popUpTextArea.setCacheHint(CacheHint.SPEED);
        popUpTextArea.setCacheHint(CacheHint.QUALITY);
        popUpTextArea.setCacheHint(CacheHint.SCALE);
        String style = String.format("-fx-background-color: %s;", bgcolor);
        popUpTextArea.setStyle(style + "-fx-text-fill:" + txtcolor + ";");
        popUpTextArea.setWrapText(true);
        //		popUpTextArea.setStyle ( "-fx-background-color:" + BackgroundColor_Service.getValue ( ) + ";" );
        popUpTextArea.setEditable(true);
        //	  popUpTextArea.textProperty ( ).addListener ( ( obs , old , niu ) -> {  //Save when typing
        //		Save ( absolutePath , popUpTextArea.getText ( ).trim ( ) );
        //	  } );
        popUpTextArea.setOnMouseExited(e -> {
            Save(absolutePath, popUpTextArea.getText().trim());
        });


        AnchorPane.setTopAnchor(popUpTextArea, 0.0);
        AnchorPane.setRightAnchor(popUpTextArea, 0.0);
        AnchorPane.setBottomAnchor(popUpTextArea, 0.0);
        AnchorPane.setLeftAnchor(popUpTextArea, 0.0);
        AnchorPane anchor = new AnchorPane(popUpTextArea);
        anchor.setPrefSize(this.notecardAnchor.getWidth(), this.notecardAnchor.getHeight());
        Label label = new Label("Editable");
        label.setStyle("-fx-text-fill:" + txtcolor + ";");
        Label label1 = new Label("Copy Note");
        label1.setStyle("-fx-text-fill:" + txtcolor + ";");
        label1.setCursor(Cursor.HAND);
        label1.setOnMouseClicked(e -> {
            CLipBoard.SaveToCLip(popUpTextArea.getText().trim());
            label1.setText("Copied");
            popUpTextArea.selectAll();

            PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(2));
            pauseTransition1.setOnFinished(e1 -> {
                        label1.setText("Copy Note");

                    }
            );
            pauseTransition1.play();

        });
        HBox hBox = new HBox(label, label1);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(20);
        VBox vBox = new VBox(anchor, hBox);
        vBox.setPadding(new Insets(5));
        vBox.setStyle(style);
        //Create PopOver and add look and feel

        Label header = new Label("Preview (Editable)");
        header.setStyle("-fx-text-fill:" + txtcolor + ";");
        HBox topBox = new HBox(header);
        topBox.setStyle(style);
        topBox.setPadding(new Insets(5));
        topBox.setAlignment(Pos.CENTER);
        VBox outerBox = new VBox(topBox, vBox);


        PopOver popOver = new PopOver(outerBox);
        popOver.setPrefSize(this.notecardAnchor.getWidth(), this.notecardAnchor.getHeight());
        popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_CENTER);
        popOver.setDetachable(true);
        popOver.setCornerRadius(15);
        popOver.setAutoFix(true);
        //	  popOver.setTitle ( "Preview (Editable)" );
        popOver.setHideOnEscape(true);

        if (popOver.isShowing() == false) {
            popOver.show(pane);

        } else {
            popOver.hide();
        }
    }

    String returnRandomGradientColors() {

        ArrayList<String> gColors = new ArrayList<>();
        gColors.add("#D8D3CF");
        gColors.add(" linear-gradient(to top,   derive(#3980F4, 50%) , derive(#BB8EF5, 50%))");
        gColors.add("linear-gradient(to right top, #9b7eea, #a37de6, #aa7ce3, #b07cdf, #b67bdb, #bc7bd8, #c27ad4, #c77ad1, #ce79cd, #d479c9, #da78c5, #df78c1)");
        gColors.add(" linear-gradient(to top,   derive(#2E274D, 50%) , derive(#8478C5, 50%))");
        gColors.add(" linear-gradient(to right top, #a782e0, #ba7fd9, #cb7dd2, #da7bc9, #e67abf, #ea7abb, #ee7ab8, #f27ab4, #f179b7, #f078b9, #ef77bc, #ee76bf)");
        gColors.add(" linear-gradient(to top,   derive(#02796b, 50%) , derive(#73e57b, 50%))");
        //	  Collections.shuffle ( gColors );

        return gColors.get(0);

    }

    void previewPopOver1() {
        String bgcolor = SaveCardBackgroundColor.GetCardBackgroundColor(absolutePath);
        if (bgcolor == null) {
            bgcolor = SaveSettings.ReadSettings().default_BackgroundColor;
        }
        String txtcolor = SaveTextColor.GetCardTextColor(absolutePath);
        if (txtcolor == null) {
            txtcolor = SaveSettings.ReadSettings().default_TextColor;
        }


        TextArea popUpTextArea = new TextArea(FontRepair.fixmyanamrfont(TextObject_Service.getValue().getText().trim()));
        popUpTextArea.getStyleClass().add("font-pyidaungsu");
        popUpTextArea.setCache(true);
        popUpTextArea.setCacheHint(CacheHint.SPEED);
        popUpTextArea.setCacheHint(CacheHint.QUALITY);
        popUpTextArea.setCacheHint(CacheHint.SCALE);
        String style = String.format("-fx-background-color: %s;", bgcolor);
        popUpTextArea.setStyle(style + "-fx-text-fill:" + txtcolor + ";");
        popUpTextArea.setWrapText(true);
        //		popUpTextArea.setStyle ( "-fx-background-color:" + BackgroundColor_Service.getValue ( ) + ";" );
        popUpTextArea.setEditable(false);
        //	  popUpTextArea.textProperty ( ).addListener ( ( obs , old , niu ) -> {
        //		Save ( absolutePath , popUpTextArea.getText ( ).trim ( ) );
        //	  } );
        popUpTextArea.setOnMouseExited(e -> {
            Save(absolutePath, popUpTextArea.getText().trim());
        });


        AnchorPane.setTopAnchor(popUpTextArea, 0.0);
        AnchorPane.setRightAnchor(popUpTextArea, 0.0);
        AnchorPane.setBottomAnchor(popUpTextArea, 0.0);
        AnchorPane.setLeftAnchor(popUpTextArea, 0.0);
        AnchorPane anchor = new AnchorPane(popUpTextArea);
        anchor.setPrefSize(this.notecardAnchor.getWidth(), this.notecardAnchor.getHeight());

        Label label = new Label("Read Only ");
        label.setStyle("-fx-text-fill:" + txtcolor + ";");
        label.setCursor(Cursor.HAND);
        label.setOnMouseClicked(e -> {
            if (PopupEditable) {
                label.setText("Editable");
                popUpTextArea.setEditable(true);
                PopupEditable = false;
            } else {
                label.setText("Read Only");
                popUpTextArea.setEditable(false);
                PopupEditable = true;
            }
        });
        Label label1 = new Label("Copy Note");
        label1.setStyle("-fx-text-fill:" + txtcolor + ";");
        label1.setCursor(Cursor.HAND);
        label1.setOnMouseClicked(e -> {
            CLipBoard.SaveToCLip(popUpTextArea.getText().trim());
            label1.setText("Copied");
            popUpTextArea.selectAll();

            PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(2));
            pauseTransition1.setOnFinished(e1 -> {
                        label1.setText("Copy Note");

                    }
            );
            pauseTransition1.play();

        });
        //	  Label label2 = new Label ( "Make Editable" );
        //	  label2.setStyle ( "-fx-text-fill:" + txtcolor + ";" );
        //	  label2.setCursor ( Cursor.HAND );
        //	  label2.setOnMouseClicked ( e->{
        //		label.setText ( "Editable" );
        //		popUpTextArea.setEditable ( true );
        //		label2.setDisable ( true );
        //	  } );
        HBox hBox = new HBox(label, label1);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(20);
        VBox vBox = new VBox(anchor, hBox);
        vBox.setPadding(new Insets(5));
        vBox.setStyle(style);
        //Create PopOver and add look and feel

        Label header = new Label("Preview (Read Only)");
        header.setStyle("-fx-text-fill:" + txtcolor + ";");
        HBox topBox = new HBox(header);
        topBox.setStyle(style);
        topBox.setPadding(new Insets(5));
        topBox.setAlignment(Pos.CENTER);
        VBox outerBox = new VBox(topBox, vBox);

        PopOver popOver = new PopOver(outerBox);
        popOver.setPrefSize(this.notecardAnchor.getWidth(), this.notecardAnchor.getHeight());
        popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_CENTER);
        popOver.setDetachable(true);
        popOver.setCornerRadius(15);
        popOver.setAutoFix(true);
        //	  popOver.setTitle ( "Preview (Read Only)" );
        popOver.setHideOnEscape(true);

        if (popOver.isShowing() == false) {
            popOver.show(pane);

        } else {
            popOver.hide();
        }
    }


    void changeFont(String hex) {
        verticalScroll.setStyle("-fx-background-color:" + hex + ";");
        horizontalScroll.setStyle("-fx-background-color:" + hex + ";");

        Label[] labels = new Label[]{typefigLabel, openinbrowserLabel, browsericonLabel, filenameLabel, editLabel, smalliconCopyLabel, smalliconPutLabel, textLabel, closesizePrefixPaneLabel, sizePrefix, createDateLabel, modifiedDateLabel, showPasswordLabel, hidePasswordLabel, pinlabel, lockiconlabel, hashlabel, attlabel, hyperlinklabel, previewlabel, clearLabel, applyLabel, applyLabelIcon, enterPasswordLabel, incorrectLabel, minNoteCardLabel, closeNoteCardLabel, closeatthasReceiveAnchorLabel, atthasTypeLabel};
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
        textArea.setStyle("-fx-text-fill:" + hex + ";");
        atthashReceiveTextArea.setStyle("-fx-text-fill:" + hex + ";");

        passwordTextfield.setStyle("-fx-text-fill:" + hex + ";" + "-fx-prompt-text-fill:" + hex + ";");
        passwordfield.setStyle("-fx-text-fill:" + hex + ";" + "-fx-prompt-text-fill:" + hex + ";");

    }


    private void waitServices() {
        notecardAnchor.setVisible(false);

        this.editLabel.setVisible(false);
        this.buttonBox.setVisible(false);
        wait_Service1.restart();
        wait_Service1.setOnSucceeded(e -> {
            this.fadeUp(this.editLabel, 1);
            this.fadeUp(this.buttonBox, 1);
            this.editLabel.setVisible(true);
            this.buttonBox.setVisible(true);

            notecardAnchor.setVisible(true);


        });
        this.createDateLabel.setVisible(false);
        this.passwordbox.setVisible(false);
        wait_Service2.restart();
        wait_Service2.setOnSucceeded(e -> {
            this.fadeUp(this.createDateLabel, 1);
            this.fadeUp(this.passwordbox, 1);
            this.createDateLabel.setVisible(true);
            this.passwordbox.setVisible(true);

            //		RotateTransition previewRotate = new RotateTransition ( Duration.seconds ( 3 ) , previewlabel );
            //		previewRotate.setFromAngle ( 0.0 );
            //		previewRotate.setToAngle ( 28800.0 );
            //		// Let the animation run two times
            //		previewRotate.setCycleCount ( 1 );
            //		// Reverse direction on alternating cycles
            //		previewRotate.setAutoReverse ( false );
            //		previewRotate.play ( );


        });

        this.modifiedDateLabel.setVisible(false);
        wait_Service3.restart();
        wait_Service3.setOnSucceeded(e -> {
            this.fadeUp(this.modifiedDateLabel, 2);
            this.modifiedDateLabel.setVisible(true);

        });
        this.textLabel.setVisible(false);
        this.enterPasswordLabel.setVisible(false);
        wait_Service4.restart();
        wait_Service4.setOnSucceeded(e -> {
            this.fadeUp(this.textLabel, 2);
            this.fadeUp(this.enterPasswordLabel, 2);
            this.textLabel.setVisible(true);
            this.enterPasswordLabel.setVisible(true);


            RotateTransition rt = new RotateTransition(Duration.seconds(2.5), previewlabel);
            rt.setFromAngle(0.0);
            rt.setToAngle(14400.0);
            // Let the animation run two times
            rt.setCycleCount(1);
            // Reverse direction on alternating cycles
            rt.setAutoReverse(false);
            rt.play();

            RotateTransition rt1 = new RotateTransition(Duration.seconds(2.5), editLabel);
            rt1.setFromAngle(0.0);
            rt1.setToAngle(14400.0);
            // Let the animation run two times
            rt1.setCycleCount(1);
            // Reverse direction on alternating cycles
            rt1.setAutoReverse(false);
            rt1.play();

            Service translateService = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            Thread.sleep(4000);
                            return null;
                        }
                    };
                }
            };
            translateService.restart();
            translateService.setOnSucceeded(e1 -> {


                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), editVBox);
                translateTransition1.interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
                translateTransition1.setByY(-5);
                translateTransition1.setAutoReverse(true);
                translateTransition1.setCycleCount(Timeline.INDEFINITE);
                translateTransition1.play();


            });

            ScheduledService rotateEditLabel = new ScheduledService() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            return null;
                        }
                    };

                }
            };
            rotateEditLabel.restart();

            this.previewlabel.setOnMouseEntered(e1 -> {
                rotateEditLabel.cancel();
            });
            this.previewlabel.setOnMouseExited(e1 -> {
                rotateEditLabel.restart();
            });
            this.editLabel.setOnMouseEntered(e1 -> {
                rotateEditLabel.cancel();
            });
            this.editLabel.setOnMouseExited(e1 -> {
                rotateEditLabel.restart();
            });

            rotateEditLabel.setPeriod(Duration.seconds(5));

            rotateEditLabel.setOnSucceeded(e1 -> {
                //		    TranslateTransition translateTransition = new TranslateTransition ( Duration.seconds ( 0.5 ) , previewlabel );
                //		    translateTransition.interpolatorProperty ( ).set ( Interpolator.SPLINE ( .1 , .1 , .7 , .7 ) );
                //		    translateTransition.setByX ( - 5 );
                //		    translateTransition.setAutoReverse ( true );
                //		    translateTransition.setCycleCount ( Timeline.INDEFINITE );
                //		    translateTransition.play ( );
                //
                RotateTransition editrotate = new RotateTransition(Duration.seconds(2.5), previewlabel);
                editrotate.setFromAngle(0.0);
                //		    editrotate.setToAngle ( 1080.0 );
                editrotate.setToAngle(1080.0);
                // Let the animation run two times
                editrotate.setCycleCount(1);

                // Reverse direction on alternating cycles
                editrotate.setAutoReverse(false);
                editrotate.play();


                RotateTransition editrotate1 = new RotateTransition(Duration.seconds(2.5), editLabel);
                editrotate1.setFromAngle(0.0);
                //		    editrotate.setToAngle ( 1080.0 );
                editrotate1.setToAngle(1080.0);
                // Let the animation run two times
                editrotate1.setCycleCount(1);

                // Reverse direction on alternating cycles
                editrotate1.setAutoReverse(false);
                editrotate1.play();

            });


        });
    }

    private void init() {
        this.ServicesRepo();

        this.UI_Perform();
        this.UI_Binding();
        this.UI_Prepare();

        this.StartServices();


        //	  this.waitServices ( );
        if (startAnimation) {

            //		// Scale
            //		int int_random = new Random ( ).nextInt ( 3 ) + 1; //   1 2 3
            //		ScaleTransition trans = new ScaleTransition ( Duration.seconds ( int_random ) , notecardAnchor );
            //		trans.setFromX ( 0.05 );
            //		trans.setToX ( 1.0 );
            //		trans.setFromY ( 0.05 );
            //		trans.setToY ( 1.0 );
            //		// Let the animation run forever
            //		trans.setCycleCount ( 1 );
            //		// Reverse direction on alternating cycles
            //		trans.setAutoReverse ( true );
            //		// Play the Animation
            //		trans.play ( );
        } else {
            // Set up a Rotate Transition
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2.5));
            rotateTransition.setFromAngle(0.0);
            rotateTransition.setToAngle(720.0);
            // Let the animation run two times
            rotateTransition.setCycleCount(1);
            // Reverse direction on alternating cycles
            rotateTransition.setAutoReverse(false);
            // Scale
            ScaleTransition trans = new ScaleTransition(Duration.seconds(2), notecardAnchor);
            trans.setFromX(0.05);
            trans.setToX(1.0);
            trans.setFromY(0.05);
            trans.setToY(1.0);
            // Let the animation run forever
            trans.setCycleCount(1);
            // Reverse direction on alternating cycles
            trans.setAutoReverse(false);
            // Play the Animation
            trans.play();
            ScaleTransition scaltran = new ScaleTransition(Duration.seconds(4), notecardAnchor);
            scaltran.setFromX(0.05);
            scaltran.setToX(1.0);
            scaltran.setFromY(0.05);
            scaltran.setToY(1.0);
            // Let the animation run forever
            scaltran.setCycleCount(1);
            // Reverse direction on alternating cycles
            scaltran.setAutoReverse(true);
            // Play the Animation
            scaltran.play();


            // Create and start a Parallel Transition
            ParallelTransition parTransition = new ParallelTransition();
            parTransition.setNode(pane);
            // Add the Children to the ParallelTransition
            parTransition.getChildren().addAll(rotateTransition, trans, scaltran);
            // Let the animation run forever
            //        parTransition.setCycleCount(PathTransition.INDEFINITE);
            parTransition.setAutoReverse(false);
            parTransition.setCycleCount(1);
            // Play the Animation
            parTransition.play();
        }


    }

    public TextObject returnTextObject() {
        return this.object;
    }

    private void Save(String absoultePath, String text) {
        try {
            FileHandlings.FileWrite(absoultePath, text.trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void visibleComponents(boolean flag) {
        Node label[] = {textLabel, createDateLabel, modifiedDateLabel, previewHBox, editVBox};
        for (
                int i = 0;
                i < label.length;
                ++i
        ) {
            Node c = label[i];
            c.setVisible(flag);
            fadeUp(c, 2);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.init();
    }
}
