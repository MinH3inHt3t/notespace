package com.notespace.Page1;

import com.notespace.FileHandler.*;
import com.notespace.FileHandler.UISplitMemory.SplitUIMemory;
import com.notespace.FileHandler.UISplitMemory.SplitUIMemoryStorage;
import com.notespace.FileHandler.XMLPack.TempPage1;
import com.notespace.Font.FontRepair;
import com.notespace.NoteCard.NoteCard;
import com.notespace.NoteCard.NoteCardUI_Test;
import com.ocpsoft.pretty.time.PrettyTime;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import product_out.___Bundle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

public class Page1 implements Initializable {

    public static double splitPane1Value = 0;
    public static double splitPane2Value = 0;

    private static double xOffset = 0;
    private static double yOffset = 0;
    @FXML
    public AnchorPane expandPane, page1Anchor, drapanddropscreen;
    @FXML
    SplitPane page1SplitPane, page1SplitPane2;

    TextObject tempObject;
    @FXML
    FlowPane contentFlow;
    @FXML
    VBox dragflowPane, editPane, deletePane, indexingBox, backgroundImageBox, backgroundImageBox1;
    @FXML
    MenuItem refreshMenuItem, scrollupMenuItem, scrollupMenuItem1, scrollbotMenuItem, scrollbotMenuItem1, systemexplorer;

    @FXML
    TextArea expandTextArea;
    @FXML
    Label createDateLabel, modifiedDateLabel, progressingLabel, closeExpandTextArea, ToggleResizeLabel, settingLabel, settingIconLabel, filenamelbl, filenameLabel, BackgroundLabel, TextColorLabel;
    @FXML
    ScrollPane contentScroll, uiAppearanceScroll, settingScrollPane, dragScroll;
    @FXML
    ColorPicker BackgroundcolorPicker, TextcolorPicker;
    @FXML
    Circle bg_circle1, bg_circle2, bg_circle3, bg_circle4, bg_circle5, bg_circle6;
    @FXML
    Circle txt_circle1, txt_circle2, txt_circle3, txt_circle4, txt_circle5, txt_circle6;
    @FXML
    ProgressBar lg_progressBar;
    @FXML
    StackPane contentStack, dragStack;
    @FXML
    ImageView backgroundImage, backgroundImage1;
    @FXML
    Label expandWindowLabel;
    @FXML
    TextField setpasswordTextField;
    VBox refreshVBox;
    int count = -1;
    boolean isSettingScrollVisible = true;
    boolean isExpand = true;
    boolean isProgramStart = true;
    boolean newWindowCalled = false;

    TextObject pinnoteactiontemp;
    String currentNoteSpace;

    private Service<TextObject> TextObject_Service = new Service() {
        @Override
        protected Task<TextObject> createTask() {
            return new Task<TextObject>() {
                @Override
                protected TextObject call() throws Exception {
                    return FileHandlings.buildObject(tempObject.getAbsolutePath());
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
                    return SaveCardBackgroundColor.GetCardBackgroundColor(tempObject.getAbsolutePath());
                }
            };
        }
    };
    private Service<String> TextColor_Service = new Service() {
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return SaveTextColor.GetCardTextColor(tempObject.getAbsolutePath());
                }
            };
        }
    };
    private Service<ArrayList<TextObject>> NoteLists_Service = new Service() {
        @Override
        protected Task<ArrayList<TextObject>> createTask() {
            return new Task<ArrayList<TextObject>>() {
                @Override
                protected ArrayList<TextObject> call() throws Exception {
                    if (isProgramStart) {
                        if (newWindowCalled == false) {
                            //				    Thread.sleep ( 5000 );
                        }

                    }
                    return FileHandlings.getTextObjectLists();
                }
            };
        }
    };
    private Service<ArrayList<TextObject>> Background_PinNoteAction = new Service() {
        @Override
        protected Task<ArrayList<TextObject>> createTask() {
            return new Task<ArrayList<TextObject>>() {
                @Override
                protected ArrayList<TextObject> call() throws Exception {
                    //			  Thread.sleep ( 500 );
                    Settings settings = SaveSettings.ReadSettings();
                    settings.pinPath = pinnoteactiontemp.getAbsolutePath();
                    SaveSettings.SaveSettings(settings);

                    return FileHandlings.getTextObjectLists();
                }
            };
        }
    };
    private Service<ArrayList<TextObject>> RefreshService = new Service() {
        @Override
        protected Task<ArrayList<TextObject>> createTask() {
            return new Task<ArrayList<TextObject>>() {
                @Override
                protected ArrayList<TextObject> call() throws Exception {

                    if (isProgramStart) {
                        //				Thread.sleep ( 100 );
                    } else {
                        //								Thread.sleep ( 2000 );
                        //				Thread.sleep ( 2500 );
                    }

                    return FileHandlings.getTextObjectLists();
                }
            };
        }
    };


    public Page1() {

    }

    public Page1(boolean newWindowCalled) {
        this.newWindowCalled = newWindowCalled;
    }


    private void ServicesRepo() {
        this.setNoteLists_Service();
        this.setTextObject_Service();
        this.setBackgroundColor_Service();
        this.setTextColor_Service();

    }


    private void setNoteLists_Service() {
        this.NoteLists_Service.setOnRunning(e -> {

        });

        this.NoteLists_Service.setOnSucceeded(e -> {


            isProgramStart = false;
            if (this.contentFlow.getChildren().size() - 2 != NoteLists_Service.getValue().size() || !currentNoteSpace.equals(NoteSpacePathStorage.getNoteSpacePathObj().spacePath)) {
                currentNoteSpace = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;

                this.contentScroll.setVvalue(TempPage1.ReadContentScrollPOSValue());
                this.dragScroll.setVvalue(TempPage1.ReadDragScrollPOSValue());
                System.out.println("Add func");

                this.indexingBox.setVisible(false);
                this.contentScroll.setVisible(true);

                this.AddDataToFlowPane(NoteLists_Service.getValue());
                //		    this.loadingVBox.setVisible ( false );


                System.out.println(SaveSettings.ReadSettings().pinPath);
                //		    if ( SaveSettings.ReadSettings ( ).pinPath == null ) {
                //			  this.dragflowPane.getChildren ( ).clear ( );
                //		    }

                if (SaveSettings.ReadSettings().pinPath != null && FileHandlings.checkFileExist(SaveSettings.ReadSettings().pinPath)) {
                    this.dragflowPane.getChildren().clear();

                    //			  TextObject obj = null;
                    //			  try {
                    //				obj = FileHandlings.buildObject ( SaveSettings.ReadSettings ( ).pinPath );
                    //			  }
                    //			  catch ( IOException e1 ) {
                    //				e1.printStackTrace ( );
                    //			  }
                    NoteCard controller = new NoteCard(SaveSettings.ReadSettings().pinPath, true);
                    Parent parent = ___Bundle.__ViewLoader._getInstance()._load("NoteCard", controller);
                    this.dragflowPane.getChildren().add(parent);


                    //			  controller.puttextfield.focusedProperty ( ).addListener ( new ChangeListener < Boolean > ( )
                    //			  {
                    //				@Override
                    //				public void changed ( ObservableValue < ? extends Boolean > arg0 , Boolean oldPropertyValue , Boolean newPropertyValue )
                    //				{
                    //				    if ( newPropertyValue ) {
                    //
                    //				    }
                    //				    else {
                    //					  //			  String text = puttextfield.getText ( ).trim ( );
                    //					  controller.putlabelhbox.setVisible ( true );
                    //					  controller.putlabelhbox2.setVisible ( false );
                    //
                    //					  controller.putlabel.setText ( controller.puttextfield.getText ( ).trim ( ) );
                    //
                    //					  refreshDragflowPane ( );
                    //
                    //
                    //					  //			  putlabel.setVisible ( true );
                    //				    }
                    //				}
                    //			  } );


                    controller.glasswindowmode.setOnAction(e1 -> {
                        glasswindowMode(controller.returnTextObject());
                    });

                    controller.putlabel.setOnMouseClicked(new EventHandler<MouseEvent>()      //-> double click label to change textarea
                    {

                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                                if (mouseEvent.getClickCount() == 1) {
                                    //				putlabel.setVisible ( false );
                                    controller.putlabelhbox.setVisible(false);
                                    controller.putlabelhbox2.setVisible(true);
                                    controller.puttextfield.setVisible(true);
                                    controller.puttextfield.setText(controller.putlabel.getText().trim());
                                }
                            }
                        }
                    });

                    controller.editLabel.setOnMouseClicked(e1 -> {
                        if (e1.getButton() == MouseButton.PRIMARY) {
                            TextObject textObject = controller.returnTextObject();
                            this.tempObject = textObject;
                            this.StartServices();
                            changeScrollPanePosition();
//                            if (SaveSettings.ReadSettings().type.equals(VIEW.MODAL)) {
//                                if (this.expandPane.isVisible()) {
//                                    changeScrollPaneToOriginalPosition();
//                                }
//                                new EditNoteUI_Test(textObject.getAbsolutePath()).startUINewWindow(new Stage());
//
//
//                            } else {
//                                this.StartServices();
//                                changeScrollPanePosition();
//                            }
                        }

                    });


                    controller.pinNoteMenuItem.setOnAction(e1 -> {
                        Service<ArrayList<TextObject>> setPinService = new Service<ArrayList<TextObject>>() {
                            @Override
                            protected Task<ArrayList<TextObject>> createTask() {
                                return new Task<ArrayList<TextObject>>() {
                                    @Override
                                    protected ArrayList<TextObject> call() throws Exception {
                                        //						    Thread.sleep ( 500 );
                                        Settings settings = SaveSettings.ReadSettings();
                                        settings.pinPath = controller.returnTextObject().getAbsolutePath();
                                        SaveSettings.SaveSettings(settings);
                                        return FileHandlings.getTextObjectLists();
                                    }
                                };
                            }
                        };
                        Service<Void> dragRefreshService = new Service<Void>() {
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
                        setPinService.restart();
                        setPinService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), setPinService);
                            controller.pane
                                    .disableProperty()
                                    .bind(
                                            setPinService.runningProperty()
                                    );
                        });
                        setPinService.setOnSucceeded(e2 -> {
                            this.AddDataToFlowPane(setPinService.getValue());
                            //			  this.refreshDragflowPane ( );
                            dragRefreshService.restart();
                        });

                        dragRefreshService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                        });
                        dragRefreshService.setOnSucceeded(e2 -> {
                            this.refreshDragflowPane();
                        });

                        //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , "YouTack" );
                        //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                        //		    this.refreshDragflowPane ( );
                    });

                    controller.unpinNoteMenuItem.setOnAction(e1 -> {
                        Service<ArrayList<TextObject>> setPinService = new Service<ArrayList<TextObject>>() {
                            @Override
                            protected Task<ArrayList<TextObject>> createTask() {
                                return new Task<ArrayList<TextObject>>() {
                                    @Override
                                    protected ArrayList<TextObject> call() throws Exception {
                                        //						    Thread.sleep ( 500 );
                                        Settings settings = SaveSettings.ReadSettings();
                                        settings.pinPath = null;
                                        SaveSettings.SaveSettings(settings);
                                        return FileHandlings.getTextObjectLists();
                                    }
                                };
                            }
                        };
                        Service<Void> dragRefreshService = new Service<Void>() {
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
                        setPinService.restart();
                        setPinService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), setPinService);
                            controller.pane
                                    .disableProperty()
                                    .bind(
                                            setPinService.runningProperty()
                                    );
                        });
                        setPinService.setOnSucceeded(e2 -> {
                            this.AddDataToFlowPane(setPinService.getValue());
                            //			  this.refreshDragflowPane ( );
                            dragRefreshService.restart();
                        });

                        dragRefreshService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                        });
                        dragRefreshService.setOnSucceeded(e2 -> {
                            this.refreshDragflowPane();
                        });

                        //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , "YouTack" );
                        //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                        //		    this.refreshDragflowPane ( );
                    });

                    controller.setPasswordAllNote.setOnAction(e1 -> {
                        Service<ArrayList<TextObject>> removePasswordService = new Service() {
                            @Override
                            protected Task<ArrayList<TextObject>> createTask() {
                                return new Task<ArrayList<TextObject>>() {
                                    @Override
                                    protected ArrayList<TextObject> call() throws Exception {
                                        //						    Thread.sleep ( 500 );

                                        return FileHandlings.getTextObjectLists();
                                    }
                                };
                            }
                        };
                        Service<Void> dragRefreshService = new Service<Void>() {
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
                        removePasswordService.restart();
                        removePasswordService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), removePasswordService);
                        });
                        removePasswordService.setOnSucceeded(e2 -> {
                            removePasswordService.getValue().stream().forEach(textObject -> {
                                NoteCardSettingStorage.setPassword(textObject.getAbsolutePath(), SystemConfigStorage.ReadSettings().password);
                            });
                            this.AddDataToFlowPane(removePasswordService.getValue());
                            dragRefreshService.restart();
                        });
                        dragRefreshService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                        });
                        dragRefreshService.setOnSucceeded(e2 -> {
                            this.refreshDragflowPane();
                        });
                        //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , null );
                        //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                        //		    this.refreshDragflowPane ( );
                    });

                    controller.setPasswordOnlyThisNote.setOnAction(e1 -> {
                        Service<ArrayList<TextObject>> setPasswordService = new Service<ArrayList<TextObject>>() {
                            @Override
                            protected Task<ArrayList<TextObject>> createTask() {
                                return new Task<ArrayList<TextObject>>() {
                                    @Override
                                    protected ArrayList<TextObject> call() throws Exception {
                                        //						    Thread.sleep ( 500 );
                                        NoteCardSettingStorage.setPassword(controller.returnTextObject().getAbsolutePath(), SystemConfigStorage.ReadSettings().password);
                                        return FileHandlings.getTextObjectLists();
                                    }
                                };
                            }
                        };
                        Service<Void> dragRefreshService = new Service<Void>() {
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
                        setPasswordService.restart();
                        setPasswordService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), setPasswordService);
                        });
                        setPasswordService.setOnSucceeded(e2 -> {
                            this.AddDataToFlowPane(setPasswordService.getValue());
                            //			  this.refreshDragflowPane ( );
                            dragRefreshService.restart();
                        });

                        dragRefreshService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                        });
                        dragRefreshService.setOnSucceeded(e2 -> {
                            this.refreshDragflowPane();
                        });

                        //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , "YouTack" );
                        //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                        //		    this.refreshDragflowPane ( );
                    });


                    controller.removePasswordMenuItem.setOnAction(e1 -> {
                        Service<ArrayList<TextObject>> removePasswordService = new Service() {
                            @Override
                            protected Task<ArrayList<TextObject>> createTask() {
                                return new Task<ArrayList<TextObject>>() {
                                    @Override
                                    protected ArrayList<TextObject> call() throws Exception {
                                        //						    Thread.sleep ( 500 );
                                        NoteCardSettingStorage.setPassword(controller.returnTextObject().getAbsolutePath(), null);
                                        return FileHandlings.getTextObjectLists();
                                    }
                                };
                            }
                        };
                        Service<Void> dragRefreshService = new Service<Void>() {
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
                        removePasswordService.restart();
                        removePasswordService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), removePasswordService);
                        });
                        removePasswordService.setOnSucceeded(e2 -> {
                            this.AddDataToFlowPane(removePasswordService.getValue());
                            dragRefreshService.restart();
                        });
                        dragRefreshService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                        });
                        dragRefreshService.setOnSucceeded(e2 -> {
                            this.refreshDragflowPane();
                        });
                        //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , null );
                        //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                        //		    this.refreshDragflowPane ( );
                    });

                    controller.removePasswordAllMenuItem.setOnAction(e1 -> {
                        Service<ArrayList<TextObject>> removePasswordService = new Service() {
                            @Override
                            protected Task<ArrayList<TextObject>> createTask() {
                                return new Task<ArrayList<TextObject>>() {
                                    @Override
                                    protected ArrayList<TextObject> call() throws Exception {
                                        //						    Thread.sleep ( 500 );

                                        return FileHandlings.getTextObjectLists();
                                    }
                                };
                            }
                        };
                        Service<Void> dragRefreshService = new Service<Void>() {
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
                        removePasswordService.restart();
                        removePasswordService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), removePasswordService);
                        });
                        removePasswordService.setOnSucceeded(e2 -> {
                            removePasswordService.getValue().stream().forEach(textObject -> {
                                NoteCardSettingStorage.setPassword(textObject.getAbsolutePath(), null);
                            });
                            this.AddDataToFlowPane(removePasswordService.getValue());
                            dragRefreshService.restart();
                        });
                        dragRefreshService.setOnRunning(e2 -> {
                            bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                        });
                        dragRefreshService.setOnSucceeded(e2 -> {
                            this.refreshDragflowPane();
                        });
                        //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , null );
                        //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                        //		    this.refreshDragflowPane ( );
                    });

                }

            } else {
                //		    this.loadingLabel.setText ( "No Result" );
                //		    this.progressbar.setProgress ( 0 );

                System.out.println("Dont add");
            }
        });


    }


    private void setTextObject_Service() {
        this.TextObject_Service.setOnSucceeded(e -> {
            TextObject textObject = TextObject_Service.getValue();
            expandTextArea.setText(FontRepair.fixmyanamrfont(textObject.getText().trim())); //textarea
            filenameLabel.setText(textObject.getName());

            Date cDate = new Date(textObject.getCreationDate());
            SimpleDateFormat createDateFormat = new SimpleDateFormat(SaveSettings.ReadSettings().NoteCard_CreateDateFormat);
            String createDate = createDateFormat.format(cDate);

            createDateLabel.setText(createDate);

            Date mDate = new Date(textObject.getModifiedDate());        //convert to DateFormat
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

    //setScheSer
    private void StartServices()      //Start All Services
    {

        this.StartNoteListService();
        this.StartTextObjectService();
        this.StartBackgroundColorService();
        this.StartTextColorService();

    }


    private void StartNoteListService() {

        this.NoteLists_Service.restart();


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


    private void CancelServices()//Cancel All Services
    {
        //	  this.NoteLists_ScheduleService.cancel ( );
        //	  this.TextObject_ScheduleService.cancel ( );
        //	  this.BackgroundColor_ScheduleService.cancel ( );
        //	  this.TextColor_ScheduleService.cancel ( );
        this.CancelNoteListService();
        this.CancelTextObjectService();
        this.CancelBackgroundColorService();
        this.CancelTextColorService();
    }


    private void CancelNoteListService() {
        this.NoteLists_Service.cancel();
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

    private void setButtonAccelerator(Button button) {

    }

    private void UI_Perform() {


        setpasswordTextField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty() == false) {
                SystemConfig systemConfig = SystemConfigStorage.ReadSettings();
                systemConfig.password = newText;
                SystemConfigStorage.SaveSystemConfig(systemConfig);
            } else {
                SystemConfig systemConfig = SystemConfigStorage.ReadSettings();
                systemConfig.password = null;
                SystemConfigStorage.SaveSystemConfig(systemConfig);
            }
        });

//        minimalistPageSearchBar.searchTextField.textProperty().addListener((obs, oldText, newText) -> {


        page1SplitPane.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    splitPane1Value = newVal.doubleValue();
                    //	  			  Stage stage = ( Stage ) page1Anchor.getScene ( ).getWindow ( );
                    //	  			  SplitUIMemoryStorage.setPage1Split1Value ( stage.getWidth ( ) , newVal.doubleValue ( ) );
                    //	  			  System.out.println ( "It's a mouse drag to pos: " + oldVal.doubleValue ( ) );
                }
            });
        });
        page1SplitPane2.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    splitPane2Value = newVal.doubleValue();
                    if (newVal.doubleValue() > 0) {
                        slowScrollToTop(uiAppearanceScroll);
                    }
                    System.out.println(newVal.doubleValue());
                    //	  			  Stage stage = ( Stage ) page1Anchor.getScene ( ).getWindow ( );
                    //	  			  SplitUIMemoryStorage.setPage1Split2Value ( stage.getWidth ( ) , newVal.doubleValue ( ) );
                    //	  			  System.out.println ( "It's a mouse drag to pos: " + oldVal.doubleValue ( ) );
                }
            });
        });


        expandWindowLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                new Page1UI_Test().startUINewWindow(new Stage(), true);
            }
        });


        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (indexingBox == null) {
                    System.out.println("Button is null! "); // check that the button was injected properly through your fxml
                }
                Scene scene = indexingBox.getScene();
                if (scene == null) {
                    throw new IllegalArgumentException("setSaveAccelerator must be called when a button is attached to a scene");
                }

                scene.getAccelerators().put(
                        new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN),
                        new Runnable() {
                            @FXML
                            public void run() {
                                backgroundImagePrepare();
                                RefreshService.restart();
                                RefreshService.setOnRunning(e1 -> {
                                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), RefreshService);
                                    //				contentScroll
                                    //					  .disableProperty ( )
                                    //					  .bind (
                                    //						    RefreshService.runningProperty ( )
                                    //					  );
                                    contentFlow.getChildren().clear();
                                    dragflowPane.getChildren().clear();

                                    Label syncLabel = new Label("syncing files");
                                    syncLabel.getStyleClass().add("font-sansserif");
                                    syncLabel.getStyleClass().add("gradientColor3Fill");
                                    //				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                                    ProgressBar progressbar = new ProgressBar();
                                    progressbar.getStyleClass().add("Progressbar");
                                    progressbar.setPrefSize(200, 3);
                                    HBox vb = new HBox(syncLabel, progressbar);

                                    vb.setSpacing(5);
                                    vb.setPadding(new Insets(5));
                                    vb.setPrefSize(contentScroll.getWidth(), contentScroll.getHeight());
                                    vb.setAlignment(Pos.CENTER);

                                    contentFlow.getChildren().add(vb);


                                    Label syncLabelforPin = new Label("indexing pin note..");
                                    syncLabelforPin.setAlignment(Pos.CENTER_LEFT);
                                    syncLabelforPin.setPrefSize(250, 20);
                                    syncLabelforPin.getStyleClass().add("font-sansserif");
                                    syncLabelforPin.getStyleClass().add("gradientColor3Fill");
                                    //				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                                    ProgressBar progressbarforPin = new ProgressBar();
                                    progressbarforPin.getStyleClass().add("Progressbar");
                                    progressbarforPin.setPrefSize(250, 3);
                                    VBox vbforPin = new VBox(syncLabelforPin, progressbarforPin);
                                    drapanddropscreen.setVisible(false);

                                    SystemConfig sc = SystemConfigStorage.ReadSettings();
                                    vbforPin.getStyleClass().add("radius20");

                                    if (sc.isBackgroundImageSet) {

                                        vbforPin.setStyle("-fx-background-color:" + sc.glassTheme + ";");
                                        contentScroll.getStyleClass().remove("solidScrollBarColor");
                                        contentScroll.getStyleClass().add("glassScrollBarColor");
                                    } else {
                                        vbforPin.setStyle("-fx-background-color:" + "#f2f2f7" + ";");
                                        contentScroll.getStyleClass().remove("glassScrollBarColor");
                                        contentScroll.getStyleClass().add("solidScrollBarColor");
                                    }

                                    vbforPin.setSpacing(5);
                                    vbforPin.setPadding(new Insets(10));
                                    vbforPin.setPrefSize(250, 50);
                                    vbforPin.setAlignment(Pos.CENTER);

                                    dragflowPane.getChildren().add(vbforPin);
                                    dragflowPane.setPadding(new Insets(10));

                                    Timeline timeline = new Timeline(
                                            new KeyFrame(Duration.ZERO, new KeyValue(progressbar.progressProperty(), 0)),
                                            new KeyFrame(Duration.seconds(4), e -> {
                                                // do anything you need here on completion...
                                                System.out.println("Minute over");
                                            }, new KeyValue(progressbar.progressProperty(), 1))
                                    );
                                    timeline.setCycleCount(Animation.INDEFINITE);
                                    timeline.play();


                                    Timeline pinTimeline = new Timeline(
                                            new KeyFrame(Duration.ZERO, new KeyValue(progressbarforPin.progressProperty(), 0)),
                                            new KeyFrame(Duration.seconds(4), e -> {
                                                // do anything you need here on completion...
                                                System.out.println("Minute over");
                                            }, new KeyValue(progressbarforPin.progressProperty(), 1))
                                    );
                                    pinTimeline.setCycleCount(1);
                                    pinTimeline.play();

                                    PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(0.5));
                                    pauseTransition1.setOnFinished(e -> {
                                                syncLabel.setText("indexing colors..");

                                            }
                                    );
                                    pauseTransition1.play();

                                    PauseTransition pauseTransition2 = new PauseTransition(Duration.seconds(1));
                                    pauseTransition2.setOnFinished(e -> syncLabel.setText("indexing notes.."));
                                    pauseTransition2.play();

                                    PauseTransition pauseTransition3 = new PauseTransition(Duration.seconds(1.5));
                                    pauseTransition3.setOnFinished(e -> {
                                        syncLabel.setText("indexing save caches..");

                                        syncLabelforPin.setText("performing indexing..");
                                    });
                                    pauseTransition3.play();

                                    PauseTransition pauseTransition4 = new PauseTransition(Duration.seconds(1.8));
                                    pauseTransition4.setOnFinished(e -> {
                                        syncLabel.setText("loading layouts..");
                                        syncLabelforPin.setText("loading layouts..");
                                    });
                                    pauseTransition4.play();


                                    //				progressbar.setStyle ( "-fx-text-fill: gray;" + "-fx-font-size: 13;" );
                                    //		    VBox hb = new VBox (  );

                                });
                                RefreshService.setOnSucceeded(e1 -> {
                                    AddDataToFlowPane(RefreshService.getValue());
                                    refreshDragflowPane();
                                });
                            }
                        }
                );
            }
        });

        final KeyCombination keyComb1 = new KeyCodeCombination(KeyCode.R,
                KeyCombination.CONTROL_DOWN);
        page1Anchor.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
            @Override
            public void handle(Event event) {
                if (keyComb1.match((KeyEvent) event)) {

                }
            }


        });


        this.refreshMenuItem.setText("Refresh (Ctrl+R) ");
        this.refreshMenuItem.setOnAction(e0 ->

        {
            this.backgroundImagePrepare();
            RefreshService.restart();
            RefreshService.setOnRunning(e1 -> {
                bindUIandService((Stage) page1Anchor.getScene().getWindow(), RefreshService);
                //				contentScroll
                //					  .disableProperty ( )
                //					  .bind (
                //						    RefreshService.runningProperty ( )
                //					  );
                contentFlow.getChildren().clear();
                dragflowPane.getChildren().clear();

                Label syncLabel = new Label("syncing files");
                syncLabel.getStyleClass().add("font-sansserif");
                syncLabel.getStyleClass().add("gradientColor3Fill");

                //				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                ProgressBar progressbar = new ProgressBar();
                progressbar.getStyleClass().add("Progressbar");
                progressbar.setPrefSize(200, 3);
                HBox vb = new HBox(syncLabel, progressbar);

                vb.setSpacing(5);
                vb.setPadding(new Insets(5));
                vb.setPrefSize(contentScroll.getWidth(), contentScroll.getHeight());
                vb.setAlignment(Pos.CENTER);

                contentFlow.getChildren().add(vb);


                Label syncLabelforPin = new Label("indexing pin note..");
                syncLabelforPin.setAlignment(Pos.CENTER_LEFT);
                syncLabelforPin.setPrefSize(250, 20);
                syncLabelforPin.getStyleClass().add("font-sansserif");
                syncLabelforPin.getStyleClass().add("gradientColor3Fill");

                //				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                ProgressBar progressbarforPin = new ProgressBar();
                progressbarforPin.getStyleClass().add("Progressbar");
                progressbarforPin.setPrefSize(250, 3);
                VBox vbforPin = new VBox(syncLabelforPin, progressbarforPin);
                drapanddropscreen.setVisible(false);

                SystemConfig sc = SystemConfigStorage.ReadSettings();
                vbforPin.getStyleClass().add("radius20");

                if (sc.isBackgroundImageSet) {

                    vbforPin.setStyle("-fx-background-color:" + sc.glassTheme + ";");
                } else {
                    vbforPin.setStyle("-fx-background-color:" + "#f2f2f7" + ";");
                }
                vbforPin.setSpacing(5);
                vbforPin.setPadding(new Insets(10));
                vbforPin.setPrefSize(250, 50);
                vbforPin.setAlignment(Pos.CENTER);

                dragflowPane.getChildren().add(vbforPin);
                dragflowPane.setPadding(new Insets(10));

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(progressbar.progressProperty(), 0)),
                        new KeyFrame(Duration.seconds(4), e -> {
                            // do anything you need here on completion...
                            System.out.println("Minute over");
                        }, new KeyValue(progressbar.progressProperty(), 1))
                );
                timeline.setCycleCount(1);
                timeline.play();


                Timeline pinTimeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(progressbarforPin.progressProperty(), 0)),
                        new KeyFrame(Duration.seconds(4), e -> {
                            // do anything you need here on completion...
                            System.out.println("Minute over");
                        }, new KeyValue(progressbarforPin.progressProperty(), 1))
                );
                pinTimeline.setCycleCount(1);
                pinTimeline.play();


                PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(0.5));
                pauseTransition1.setOnFinished(e -> {
                            syncLabel.setText("indexing colors..");

                        }
                );
                pauseTransition1.play();

                PauseTransition pauseTransition2 = new PauseTransition(Duration.seconds(1));
                pauseTransition2.setOnFinished(e -> syncLabel.setText("indexing notes.."));
                pauseTransition2.play();

                PauseTransition pauseTransition3 = new PauseTransition(Duration.seconds(1.5));
                pauseTransition3.setOnFinished(e -> {
                    syncLabel.setText("indexing save caches..");

                    syncLabelforPin.setText("performing indexing..");
                });
                pauseTransition3.play();

                PauseTransition pauseTransition4 = new PauseTransition(Duration.seconds(1.8));
                pauseTransition4.setOnFinished(e -> {
                    syncLabel.setText("loading layouts..");
                    syncLabelforPin.setText("loading layouts..");
                });
                pauseTransition4.play();


                //				progressbar.setStyle ( "-fx-text-fill: gray;" + "-fx-font-size: 13;" );
                //		    VBox hb = new VBox (  );

            });
            RefreshService.setOnSucceeded(e1 -> {
                //	  				AddDataToFlowPane ( RefreshService.getValue ( ) );
                //		    	  				refreshDragflowPane ( );
            });
        });

        this.scrollupMenuItem.setOnAction(e ->

        {
            this.slowScrollToTop(this.contentScroll);
        });
        this.scrollupMenuItem1.setOnAction(e ->

        {
            this.slowScrollToTop(this.dragScroll);
        });
        this.scrollbotMenuItem.setOnAction(e ->

        {
            this.slowScrollToBot(this.contentScroll);
        });
        this.scrollbotMenuItem1.setOnAction(e ->

        {
            this.slowScrollToBot(this.dragScroll);
        });
        this.systemexplorer.setOnAction(e ->

        {
            try {
                File file = new File(NoteSpacePathStorage.getNoteSpacePathObj().spacePath);
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Platform.runLater(new

                                  Runnable() {
                                      @Override
                                      public void run() {
                                          //		    Stage stage = ( Stage ) contentScroll.getScene ( ).getWindow ( );

                                          page1Anchor.widthProperty().addListener((obs, oldVal, newVal) -> {

                                              splitPaneOriginalPosition();
                                              //								backgroundImage.setFitHeight ( backgroundImage.getFitHeight () +Double.parseDouble ( newVal.toString () ));
                                          });

                                          page1Anchor.heightProperty().addListener((obs, oldVal, newVal) -> {
                                              Platform.runLater(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      page1SplitPane2.setDividerPositions(0);
                                                  }
                                              });

                                              //								backgroundImage.setFitHeight ( backgroundImage.getFitHeight () +Double.parseDouble ( newVal.toString () ));
                                          });


                                          contentScroll.widthProperty().addListener((obs, oldVal, newVal) -> {
                                              backgroundImage.setFitWidth(contentScroll.getWidth() + 100);

                                              //								backgroundImage.setFitHeight ( backgroundImage.getFitHeight () +Double.parseDouble ( newVal.toString () ));
                                          });
                                          contentScroll.heightProperty().addListener((obs, oldVal, newVal) -> {
                                              backgroundImage.setFitHeight(contentScroll.getHeight() + 100);
                                              //								backgroundImage.setFitHeight ( backgroundImage.getFitHeight () +Double.parseDouble ( newVal.toString () ));
                                          });

                                          //							  contentScroll.widthProperty ( ).addListener ( ( obs , oldVal , newVal ) -> {
                                          //								contentFlow.getChildren ( ).clear ( );
                                          //								dragflowPane.getChildren ( ).clear ( );
                                          //
                                          //
                                          //								Service < ArrayList < TextObject > > servicelist = new Service < ArrayList < TextObject > > ( )
                                          //								{
                                          //								    @Override
                                          //								    protected Task < ArrayList < TextObject > > createTask ( )
                                          //								    {
                                          //									  return new Task < ArrayList < TextObject > > ( )
                                          //									  {
                                          //										@Override
                                          //										protected ArrayList < TextObject > call ( ) throws Exception
                                          //										{
                                          //										    Thread.sleep ( 1000 );
                                          //										    return FileHandlings.getTextObjectLists ( );
                                          //										}
                                          //									  };
                                          //								    }
                                          //								};
                                          //
                                          //
                                          //								servicelist.restart ( );
                                          //								servicelist.setOnRunning ( e -> {
                                          //								    Platform.runLater ( new Runnable ( )
                                          //								    {
                                          //									  @Override
                                          //									  public void run ( )
                                          //									  {
                                          //
                                          //										bindUIandService ( ( Stage ) page1Anchor.getScene ( ).getWindow ( ) , servicelist );
                                          //
                                          //										contentFlow.getChildren ( ).clear ( );
                                          //										dragflowPane.getChildren ( ).clear ( );
                                          //
                                          //										Label syncLabel = new Label ( "syncing files" );
                                          //										syncLabel.getStyleClass ( ).add ( "font-sansserif" );
                                          //										//				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                                          //										ProgressBar progressbar = new ProgressBar ( );
                                          //										progressbar.getStyleClass ( ).add ( "Progressbar" );
                                          //										progressbar.setPrefSize ( 200 , 3 );
                                          //										HBox vb = new HBox ( syncLabel , progressbar );
                                          //
                                          //										vb.setSpacing ( 5 );
                                          //										vb.setPadding ( new Insets ( 5 ) );
                                          //										vb.setPrefSize ( contentScroll.getWidth ( ) , contentScroll.getHeight ( ) );
                                          //										vb.setAlignment ( Pos.CENTER );
                                          //
                                          //										contentFlow.getChildren ( ).add ( vb );
                                          //
                                          //										Timeline timeline = new Timeline (
                                          //											  new KeyFrame ( Duration.ZERO , new KeyValue ( progressbar.progressProperty ( ) , 0 ) ) ,
                                          //											  new KeyFrame ( Duration.seconds ( 4 ) , e2 -> {
                                          //												// do anything you need here on completion...
                                          //												System.out.println ( "Minute over" );
                                          //											  } , new KeyValue ( progressbar.progressProperty ( ) , 1 ) )
                                          //										);
                                          //										timeline.setCycleCount ( Animation.INDEFINITE );
                                          //										timeline.play ( );
                                          //
                                          //										syncLabel.setText ( "Resizing interface.." );
                                          //
                                          //										PauseTransition pauseTransition1 = new PauseTransition ( Duration.seconds ( .5 ) );
                                          //										pauseTransition1.setOnFinished ( e2 -> syncLabel.setText ( "indexing colors.." ) );
                                          //										pauseTransition1.play ( );
                                          //
                                          //										PauseTransition pauseTransition2 = new PauseTransition ( Duration.seconds ( 1 ) );
                                          //										pauseTransition2.setOnFinished ( e2 -> syncLabel.setText ( "indexing notes.." ) );
                                          //										pauseTransition2.play ( );
                                          //
                                          //										PauseTransition pauseTransition3 = new PauseTransition ( Duration.seconds ( 1.5 ) );
                                          //										pauseTransition3.setOnFinished ( e2 -> syncLabel.setText ( "indexing save caches.." ) );
                                          //										pauseTransition3.play ( );
                                          //
                                          //										PauseTransition pauseTransition4 = new PauseTransition ( Duration.seconds ( 1.8 ) );
                                          //										pauseTransition4.setOnFinished ( e2 -> syncLabel.setText ( "complete indexing.." ) );
                                          //										pauseTransition4.play ( );
                                          //
                                          //
                                          //									  }
                                          //								    } );
                                          //								} );
                                          //								servicelist.setOnSucceeded ( e -> {
                                          //								    AddDataToFlowPane ( servicelist.getValue ( ) );
                                          //								    refreshDragflowPane ( );
                                          //								} );
                                          //
                                          //
                                          //							  } );
                                          //		    contentScroll.heightProperty ( ).addListener ( ( obs , oldVal , newVal ) -> {
                                          //		    			  contentFlow.getChildren ( ).clear ( );
                                          //		    			  dragflowPane.getChildren ( ).clear ( );
                                          //
                                          //		    			  Service < ArrayList < TextObject > > servicelist = new Service < ArrayList < TextObject > > ( )
                                          //		    			  {
                                          //		    				@Override
                                          //		    				protected Task < ArrayList < TextObject > > createTask ( )
                                          //		    				{
                                          //		    				    return new Task < ArrayList < TextObject > > ( )
                                          //		    				    {
                                          //		    					  @Override
                                          //		    					  protected ArrayList < TextObject > call ( ) throws Exception
                                          //		    					  {
                                          //		    					      Thread.sleep ( 1000 );
                                          //		    						return FileHandlings.getTextObjectLists ( );
                                          //		    					  }
                                          //		    				    };
                                          //		    				}
                                          //		    			  };
                                          //
                                          //		    			  servicelist.restart ( );
                                          //		    			  servicelist.setOnRunning ( e -> {
                                          //		    				Platform.runLater ( new Runnable ( )
                                          //		    				{
                                          //		    				    @Override
                                          //		    				    public void run ( )
                                          //		    				    {
                                          //
                                          //		    					  bindUIandService ( ( Stage ) page1Anchor.getScene ( ).getWindow ( ) , servicelist );
                                          //
                                          //		    					  contentFlow.getChildren ( ).clear ( );
                                          //		    					  dragflowPane.getChildren ( ).clear ( );
                                          //
                                          //		    					  Label syncLabel = new Label ( "syncing files" );
                                          //		    					  syncLabel.getStyleClass ( ).add ( "font-sansserif" );
                                          //		    					  //				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                                          //		    					  ProgressBar progressbar = new ProgressBar ( );
                                          //		    					  progressbar.getStyleClass ( ).add ( "Progressbar" );
                                          //		    					  progressbar.setPrefSize ( 200 , 3 );
                                          //		    					  HBox vb = new HBox ( syncLabel , progressbar );
                                          //
                                          //		    					  vb.setSpacing ( 5 );
                                          //		    					  vb.setPadding ( new Insets ( 5 ) );
                                          //		    					  vb.setPrefSize ( contentScroll.getWidth ( ) , contentScroll.getHeight ( ) );
                                          //		    					  vb.setAlignment ( Pos.CENTER );
                                          //
                                          //		    					  contentFlow.getChildren ( ).add ( vb );
                                          //
                                          //		    					  Timeline timeline = new Timeline (
                                          //		    						    new KeyFrame ( Duration.ZERO , new KeyValue ( progressbar.progressProperty ( ) , 0 ) ) ,
                                          //		    						    new KeyFrame ( Duration.seconds ( 4 ) , e2 -> {
                                          //		    							  // do anything you need here on completion...
                                          //		    							  System.out.println ( "Minute over" );
                                          //		    						    } , new KeyValue ( progressbar.progressProperty ( ) , 1 ) )
                                          //		    					  );
                                          //		    					  timeline.setCycleCount ( Animation.INDEFINITE );
                                          //		    					  timeline.play ( );
                                          //
                                          //		    					  PauseTransition pauseTransition1 = new PauseTransition ( Duration.seconds ( .5 ) );
                                          //		    					  pauseTransition1.setOnFinished ( e2 -> syncLabel.setText ( "indexing colors.." ) );
                                          //		    					  pauseTransition1.play ( );
                                          //
                                          //		    					  PauseTransition pauseTransition2 = new PauseTransition ( Duration.seconds ( 1 ) );
                                          //		    					  pauseTransition2.setOnFinished ( e2 -> syncLabel.setText ( "indexing notes.." ) );
                                          //		    					  pauseTransition2.play ( );
                                          //
                                          //		    					  PauseTransition pauseTransition3 = new PauseTransition ( Duration.seconds ( 1.5 ) );
                                          //		    					  pauseTransition3.setOnFinished ( e2 -> syncLabel.setText ( "indexing save caches.." ) );
                                          //		    					  pauseTransition3.play ( );
                                          //
                                          //		    					  PauseTransition pauseTransition4 = new PauseTransition ( Duration.seconds ( 1.8 ) );
                                          //		    					  pauseTransition4.setOnFinished ( e2 -> syncLabel.setText ( "complete indexing.." ) );
                                          //		    					  pauseTransition4.play ( );
                                          //
                                          //
                                          //		    				    }
                                          //		    				} );
                                          //		    			  } );
                                          //		    			  servicelist.setOnSucceeded ( e -> {
                                          //		    				AddDataToFlowPane ( servicelist.getValue ( ) );
                                          //		    				refreshDragflowPane ( );
                                          //		    			  } );
                                          //
                                          //
                                          //		    		    } );
                                      }
                                  });


        //	  this.dragflowPane.setOnMouseEntered ( e -> {
        //
        //		if ( SaveSettings.ReadSettings ( ).pinPath == null ) {
        //		    this.dragflowPane.getChildren ().clear ();
        //		    dragflowLists.clear ();
        ////		    NoteCard controller = new NoteCard ( obj );
        ////		    Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "NoteCard" , controller );
        ////		    this.dragflowPane.getChildren ( ).add ( parent );
        //		}
        //	  } );

        //	  	  	  this.dragflowPane.setOnMouseExited ( e -> {
        //	  	  		if ( SaveSettings.ReadSettings ( ).pinPath == null ) {
        //	  	  		    this.dragflowPane.getChildren ( ).clear ( );
        //	  	  		}
        //	  	  	  } );


        this.closeExpandTextArea.setOnMouseClicked(e ->

        {      //Close ExpandTextArea Action
            this.dragScroll.setVisible(true);
            this.contentScroll.setEffect(null);
            this.fadeUp(this.contentScroll);
            this.CancelServices();

            AnchorPane.setRightAnchor(this.expandPane, null);
            this.changeScrollPaneToOriginalPosition();
        });


        this.page1Anchor.setOnMouseEntered(e ->
        {
            this.StartServices();
            String getPassword = SystemConfigStorage.ReadSettings().password;
            if (getPassword != null) {
                setpasswordTextField.setText(getPassword);
            }
        });
        this.page1Anchor.setOnMouseExited(e ->

        {

            this.StartServices();
        });

        this.dragflowPane.setOnMouseEntered(e ->

        {
            if (SaveSettings.ReadSettings().pinPath == null) {
                this.refreshDragflowPane();
            } else {

            }

        });
        this.dragflowPane.setOnMouseExited(e ->

        {
            if (SaveSettings.ReadSettings().pinPath == null) {
                this.refreshDragflowPane();
            }
        });


        this.ToggleResizeLabel.setOnMouseClicked(e ->

        {      //Toggle Resize Action
            if (this.isSettingScrollVisible) {
                this.isSettingScrollVisible = false;
                this.transitionUpDown(this.settingScrollPane, -1000, 0);
                AnchorPane.setRightAnchor(this.expandTextArea, 284.0);
            } else {
                this.isSettingScrollVisible = true;
                this.transitionUpDown(this.settingScrollPane, 0, -1000);
                AnchorPane.setRightAnchor(this.expandTextArea, 20.0);
            }
            if (this.isExpand) {
                this.contentScroll.setEffect(getFrostEffect(200, 3));
                this.dragScroll.setVisible(false);
                this.isExpand = false;
                this.translateWidth(this.expandPane, 1000);
                this.settingIconLabel.setVisible(true);
                AnchorPane.setRightAnchor(this.expandTextArea, 284.0);
            } else {
                this.isExpand = true;
                this.translateWidth(this.expandPane, 286);
                this.settingIconLabel.setVisible(false);
                AnchorPane.setRightAnchor(this.expandTextArea, 20.0);
            }
        });

        this.expandTextArea.focusedProperty().

                addListener(new ChangeListener<Boolean>() {
                    //TODO Here
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean
                            newPropertyValue) {
                        if (newPropertyValue) {
                            //			  System.out.println ( "Textfield on focus" );
                            CancelTextObjectService();


                        } else {
                            //			  System.out.println ( "Textfield out focus" );
                            Save(tempObject.getAbsolutePath(), expandTextArea.getText().trim());

                            StartTextObjectService();


                        }
                    }
                });

        //	  this.expandTextArea.textProperty ( ).addListener ( ( obs , old , niu ) ->
        //	  {      //Textarea typing action
        //		// TODO here
        //		Save ( this.tempObject.getAbsolutePath ( ) , this.expandTextArea.getText ( ).trim ( ) );
        //	  } );

        this.expandTextArea.setOnMouseExited(e -> {
            Save(this.tempObject.getAbsolutePath(), this.expandTextArea.getText().trim());
        });


        this.settingIconLabel.setOnMouseClicked(e ->

        {
            //No Action Yet
        });


        BackgroundcolorPicker.setOnAction(
                event ->

                {
                    event.consume();

                    Color value = BackgroundcolorPicker.getValue();
                    if (value == null) {
                        //	             root.setStyle(null);
                    } else {
                        changeTheme(toHexString(value));
                        SaveCardBackgroundColor.SaveCardBackgroundColor(this.tempObject.getAbsolutePath(), toHexString(value));

                    }
                });

        TextcolorPicker.setOnAction(
                event ->

                {
                    event.consume();

                    Color value = TextcolorPicker.getValue();
                    if (value == null) {
                        //	             root.setStyle(null);
                    } else {
                        changeFont(toHexString(value));
                        SaveTextColor.SaveCardTextColor(this.tempObject.getAbsolutePath(), toHexString(value));

                    }
                });

        Circle[] bgCircleList1 = new Circle[]{bg_circle1, bg_circle2, bg_circle3, bg_circle4, bg_circle5, bg_circle6};
        for (
                int i = 0;
                i < bgCircleList1.length;
                ++i
        ) {
            Circle c = bgCircleList1[i];
            c.setOnMouseClicked(e -> {
                String hex = getCircleColor(c);
                changeTheme(hex);
                SaveCardBackgroundColor.SaveCardBackgroundColor(this.tempObject.getAbsolutePath(), hex);

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
                String hex = getCircleColor(c);
                changeFont(hex);
                SaveTextColor.SaveCardTextColor(this.tempObject.getAbsolutePath(), hex);

            });
        }

        Platform.runLater(new

                                  Runnable() {
                                      @Override
                                      public void run() {
                                          contentScroll.setVvalue(1);
                                          //		    contentScroll.vvalueProperty ( ).addListener ( new ChangeListener < Number > ( )
                                          //		    {
                                          //			  public void changed ( ObservableValue < ? extends Number > ov , Number old_val , Number new_val )
                                          //			  {
                                          //				//		            fileName.setText(imageNames[(new_val.intValue() - 1)/100]);
                                          //				double d = ( double ) new_val;
                                          //				refreshVBox.setVisible ( true );
                                          //				fadeUp ( refreshVBox );
                                          //
                                          //				if(d>0.10){
                                          //				    count = -1;
                                          //				}
                                          //
                                          //				if ( d == 0 ) {
                                          //				    count++;
                                          //
                                          //				    Platform.runLater ( new Runnable ( )
                                          //				    {
                                          //					  @Override
                                          //					  public void run ( )
                                          //					  {
                                          //						if ( count == 2 ) {
                                          //						    count = 0;
                                          //						    Service<ArrayList<TextObject>> refreshService  = new Service < ArrayList < TextObject > > ( ) {
                                          //							  @Override
                                          //							  protected Task < ArrayList < TextObject > > createTask ( )
                                          //							  {
                                          //								return new Task < ArrayList < TextObject > > ( ) {
                                          //								    @Override
                                          //								    protected ArrayList < TextObject > call ( ) throws Exception
                                          //								    {
                                          //								        Thread.sleep ( 200 );
                                          //									  return FileHandlings.getTextObjectLists ();
                                          //								    }
                                          //								};
                                          //							  }
                                          //						    };
                                          //						    refreshService.restart ( );
                                          //						    refreshService.setOnRunning ( e1 -> {
                                          //							  bindUIandService ( ( Stage ) page1Anchor.getScene ( ).getWindow ( ) , refreshService );
                                          //							  refreshVBox
                                          //								    .disableProperty ( )
                                          //								    .bind (
                                          //										refreshService.runningProperty ( )
                                          //								    );
                                          //							  contentFlow.getChildren ( ).clear ( );
                                          //							  dragflowPane.getChildren ( ).clear ( );
                                          //
                                          //							  Label syncLabel = new Label ( "syncing files" );
                                          //							  syncLabel.getStyleClass ( ).add ( "font-sansserif" );
                                          //							  //				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                                          //							  ProgressBar progressbar = new ProgressBar ( );
                                          //							  progressbar.getStyleClass ( ).add ( "Progressbar" );
                                          //							  progressbar.setPrefSize ( 200 , 3 );
                                          //							  HBox vb = new HBox ( syncLabel , progressbar );
                                          //
                                          //							  vb.setSpacing ( 5 );
                                          //							  vb.setPadding ( new Insets ( 5 ) );
                                          //							  vb.setPrefSize ( contentScroll.getWidth ( ) , contentScroll.getHeight ( ) );
                                          //							  vb.setAlignment ( Pos.CENTER );
                                          //
                                          //							  contentFlow.getChildren ( ).add ( vb );
                                          //
                                          //							  Timeline timeline = new Timeline (
                                          //								    new KeyFrame ( Duration.ZERO , new KeyValue ( progressbar.progressProperty ( ) , 0 ) ) ,
                                          //								    new KeyFrame ( Duration.seconds ( 4 ) , e2 -> {
                                          //									  // do anything you need here on completion...
                                          //									  System.out.println ( "Minute over" );
                                          //								    } , new KeyValue ( progressbar.progressProperty ( ) , 1 ) )
                                          //							  );
                                          //							  timeline.setCycleCount ( Animation.INDEFINITE );
                                          //							  timeline.play ( );
                                          //
                                          //							  PauseTransition pauseTransition1 = new PauseTransition ( Duration.seconds ( .5 ) );
                                          //							  pauseTransition1.setOnFinished ( e2 -> syncLabel.setText ( "indexing colors.." ) );
                                          //							  pauseTransition1.play ( );
                                          //
                                          //							  PauseTransition pauseTransition2 = new PauseTransition ( Duration.seconds ( 1 ) );
                                          //							  pauseTransition2.setOnFinished ( e2 -> syncLabel.setText ( "indexing notes.." ) );
                                          //							  pauseTransition2.play ( );
                                          //
                                          //							  PauseTransition pauseTransition3 = new PauseTransition ( Duration.seconds ( 1.5 ) );
                                          //							  pauseTransition3.setOnFinished ( e2 -> syncLabel.setText ( "indexing save caches.." ) );
                                          //							  pauseTransition3.play ( );
                                          //
                                          //							  PauseTransition pauseTransition4 = new PauseTransition ( Duration.seconds ( 1.8 ) );
                                          //							  pauseTransition4.setOnFinished ( e2 -> syncLabel.setText ( "complete indexing.." ) );
                                          //							  pauseTransition4.play ( );
                                          //
                                          //						    } );
                                          //						    refreshService.setOnSucceeded ( e1 -> {
                                          //							  AddDataToFlowPane ( refreshService.getValue ( ) );
                                          //							  refreshDragflowPane ( );
                                          //						    } );
                                          //						}
                                          //
                                          //					  }
                                          //				    } );
                                          //				}
                                          //				//				else {
                                          //				//				    Platform.runLater ( new Runnable ( )
                                          //				//				    {
                                          //				//					  @Override
                                          //				//					  public void run ( )
                                          //				//					  {
                                          //				//						refreshVBox.setVisible ( false );
                                          //				//						//						fadeDown ( refreshVBox );
                                          //				//
                                          //				//					  }
                                          //				//				    } );
                                          //				//				}
                                          //				//				TempPage1.ReplaceContentScrollPOS ( d );
                                          //
                                          //			  }
                                          //		    } );
                                      }
                                  });


        //
        //	  dragScroll.vvalueProperty ( ).addListener ( new ChangeListener < Number > ( )
        //	  {
        //		public void changed ( ObservableValue < ? extends Number > ov , Number old_val , Number new_val )
        //		{
        //		    //		            fileName.setText(imageNames[(new_val.intValue() - 1)/100]);
        //
        //		    double d = ( double ) new_val;
        //
        //		    TempPage1.ReplaceDragScrollPOS ( d );
        //
        //		}
        //	  } );

    }

    private void UI_Binding() {

    }

    private void bindUIandService(Stage stage, Service servicepara) {
        //           label.textProperty()
        //                   .bind(
        //                           service.stateProperty().asString()
        //                   );

        stage.getScene()
                .getRoot()
                .cursorProperty()
                .bind(
                        Bindings
                                .when(servicepara.runningProperty())
                                .then(Cursor.WAIT)
                                .otherwise(Cursor.DEFAULT)
                );

        //	             runButton
        //	                     .disableProperty()
        //	                     .bind(
        //	                             service.runningProperty()
        //	                     );

        //           runButton.setOnAction(new EventHandler<ActionEvent>() {
        //               @Override
        //               public void handle(ActionEvent event) {
        //                   service.restart();
        //               }
        //           });
    }


    private void AddDataToFlowPane(ArrayList<TextObject> noteList) {
        this.contentFlow.getChildren().clear();
        Collections.sort(noteList, new Sorting.LatestModifiedDate());


        //	  List<Node> newChildren = computeNewChildren();
        //	  vbox.getChildren().addAll(newChidren);

        Label label = new Label("Refresh ( Ctrl+R )");
        label.getStyleClass().add("refreshIconLabel");
        label.getStyleClass().add("gradientColor3Fill");

        //	  label.setStyle ( "-fx-text-fill: rgba(0,0,0,0.7);" );
        label.setOnMouseClicked(e0 -> {
            this.backgroundImagePrepare();
            this.contentFlow.getChildren().clear();
            this.dragflowPane.getChildren().clear();

            RefreshService.restart();
            RefreshService.setOnRunning(e1 -> {
                bindUIandService((Stage) page1Anchor.getScene().getWindow(), RefreshService);
                label
                        .disableProperty()
                        .bind(
                                RefreshService.runningProperty()
                        );
                this.contentFlow.getChildren().clear();
                this.dragflowPane.getChildren().clear();

                Label syncLabel = new Label("syncing files");
                syncLabel.getStyleClass().add("font-sansserif");
                syncLabel.getStyleClass().add("gradientColor3Fill");
                //				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                ProgressBar progressbar = new ProgressBar();
                progressbar.getStyleClass().add("Progressbar");
                progressbar.setPrefSize(200, 3);
                HBox vb = new HBox(syncLabel, progressbar);

                vb.setSpacing(5);
                vb.setPadding(new Insets(5));
                vb.setPrefSize(contentScroll.getWidth(), contentScroll.getHeight());
                vb.setAlignment(Pos.CENTER);

                contentFlow.getChildren().add(vb);


                Label syncLabelforPin = new Label("indexing pin note..");
                syncLabelforPin.setAlignment(Pos.CENTER_LEFT);
                syncLabelforPin.setPrefSize(250, 20);
                syncLabelforPin.getStyleClass().add("font-sansserif");
                syncLabelforPin.getStyleClass().add("gradientColor3Fill");

                //				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                ProgressBar progressbarforPin = new ProgressBar();
                progressbarforPin.getStyleClass().add("Progressbar");
                progressbarforPin.setPrefSize(250, 3);
                VBox vbforPin = new VBox(syncLabelforPin, progressbarforPin);
                drapanddropscreen.setVisible(false);

                SystemConfig sc = SystemConfigStorage.ReadSettings();
                vbforPin.getStyleClass().add("radius20");

                if (sc.isBackgroundImageSet) {

                    vbforPin.setStyle("-fx-background-color:" + sc.glassTheme + ";");
                } else {
                    vbforPin.setStyle("-fx-background-color:" + "#f2f2f7" + ";");
                }
                vbforPin.setSpacing(5);
                vbforPin.setPadding(new Insets(10));
                vbforPin.setPrefSize(250, 50);
                vbforPin.setAlignment(Pos.CENTER);

                dragflowPane.getChildren().add(vbforPin);
                dragflowPane.setPadding(new Insets(10));

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(progressbar.progressProperty(), 0)),
                        new KeyFrame(Duration.seconds(4), e -> {
                            // do anything you need here on completion...
                            System.out.println("Minute over");
                        }, new KeyValue(progressbar.progressProperty(), 1))
                );
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();


                Timeline pinTimeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(progressbarforPin.progressProperty(), 0)),
                        new KeyFrame(Duration.seconds(4), e -> {
                            // do anything you need here on completion...
                            System.out.println("Minute over");
                        }, new KeyValue(progressbarforPin.progressProperty(), 1))
                );
                pinTimeline.setCycleCount(1);
                pinTimeline.play();

                PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(0.5));
                pauseTransition1.setOnFinished(e -> {
                            syncLabel.setText("indexing colors..");

                        }
                );
                pauseTransition1.play();

                PauseTransition pauseTransition2 = new PauseTransition(Duration.seconds(1));
                pauseTransition2.setOnFinished(e -> syncLabel.setText("indexing notes.."));
                pauseTransition2.play();

                PauseTransition pauseTransition3 = new PauseTransition(Duration.seconds(1.5));
                pauseTransition3.setOnFinished(e -> {
                    syncLabel.setText("indexing save caches..");

                    syncLabelforPin.setText("performing indexing..");
                });
                pauseTransition3.play();

                PauseTransition pauseTransition4 = new PauseTransition(Duration.seconds(1.8));
                pauseTransition4.setOnFinished(e -> {
                    syncLabel.setText("loading layouts..");
                    syncLabelforPin.setText("loading layouts..");
                });
                pauseTransition4.play();


            });
            RefreshService.setOnSucceeded(e1 -> {
                AddDataToFlowPane(RefreshService.getValue());
                refreshDragflowPane();
            });
        });


        Label refreshIconLabel = new Label("\ue627");
        //	  refreshIconLabel.setStyle ( "-fx-text-fill:  rgba(0,0,0,0.7);" );

        refreshIconLabel.getStyleClass().add("gradientColor3Fill");
        refreshIconLabel.setOnMouseClicked(e0 -> {
            this.backgroundImagePrepare();
            this.contentFlow.getChildren().clear();
            this.dragflowPane.getChildren().clear();

            RefreshService.restart();
            RefreshService.setOnRunning(e1 -> {
                bindUIandService((Stage) page1Anchor.getScene().getWindow(), RefreshService);
                label
                        .disableProperty()
                        .bind(
                                RefreshService.runningProperty()
                        );
                this.contentFlow.getChildren().clear();
                this.dragflowPane.getChildren().clear();

                Label syncLabel = new Label("syncing files");
                syncLabel.getStyleClass().add("font-sansserif");
                syncLabel.getStyleClass().add("gradientColor3Fill");
                //				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                ProgressBar progressbar = new ProgressBar();
                progressbar.getStyleClass().add("Progressbar");
                progressbar.setPrefSize(200, 3);
                HBox vb = new HBox(syncLabel, progressbar);

                vb.setSpacing(5);
                vb.setPadding(new Insets(5));
                vb.setPrefSize(contentScroll.getWidth(), contentScroll.getHeight());
                vb.setAlignment(Pos.CENTER);

                contentFlow.getChildren().add(vb);


                Label syncLabelforPin = new Label("indexing pin note..");
                syncLabelforPin.setAlignment(Pos.CENTER_LEFT);
                syncLabelforPin.setPrefSize(250, 20);
                syncLabelforPin.getStyleClass().add("font-sansserif");
                syncLabelforPin.getStyleClass().add("gradientColor3Fill");
                //				syncLabel.setStyle ( "-fx-text-fill: gray;" + "-fx-font-weight:bold;" + "-fx-font-size: 20;" );
                ProgressBar progressbarforPin = new ProgressBar();
                progressbarforPin.getStyleClass().add("Progressbar");
                progressbarforPin.setPrefSize(250, 3);
                VBox vbforPin = new VBox(syncLabelforPin, progressbarforPin);
                drapanddropscreen.setVisible(false);
                SystemConfig sc = SystemConfigStorage.ReadSettings();
                vbforPin.getStyleClass().add("radius20");


                if (sc.isBackgroundImageSet) {

                    vbforPin.setStyle("-fx-background-color:" + sc.glassTheme + ";");

                } else {
                    vbforPin.setStyle("-fx-background-color:" + "#f2f2f7" + ";");
                }


                vbforPin.setSpacing(5);
                vbforPin.setPadding(new Insets(10));
                vbforPin.setPrefSize(250, 50);
                vbforPin.setAlignment(Pos.CENTER);

                dragflowPane.getChildren().add(vbforPin);
                dragflowPane.setPadding(new Insets(10));

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(progressbar.progressProperty(), 0)),
                        new KeyFrame(Duration.seconds(4), e -> {
                            // do anything you need here on completion...
                            System.out.println("Minute over");
                        }, new KeyValue(progressbar.progressProperty(), 1))
                );
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();


                Timeline pinTimeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(progressbarforPin.progressProperty(), 0)),
                        new KeyFrame(Duration.seconds(4), e -> {
                            // do anything you need here on completion...
                            System.out.println("Minute over");
                        }, new KeyValue(progressbarforPin.progressProperty(), 1))
                );
                pinTimeline.setCycleCount(1);
                pinTimeline.play();

                PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(0.5));
                pauseTransition1.setOnFinished(e -> {
                            syncLabel.setText("indexing colors..");

                        }
                );
                pauseTransition1.play();

                PauseTransition pauseTransition2 = new PauseTransition(Duration.seconds(1));
                pauseTransition2.setOnFinished(e -> syncLabel.setText("indexing notes.."));
                pauseTransition2.play();

                PauseTransition pauseTransition3 = new PauseTransition(Duration.seconds(1.5));
                pauseTransition3.setOnFinished(e -> {
                    syncLabel.setText("indexing save caches..");

                    syncLabelforPin.setText("performing indexing..");
                });
                pauseTransition3.play();

                PauseTransition pauseTransition4 = new PauseTransition(Duration.seconds(1.8));
                pauseTransition4.setOnFinished(e -> {
                    syncLabel.setText("loading layouts..");
                    syncLabelforPin.setText("loading layouts..");
                });
                pauseTransition4.play();


            });
            RefreshService.setOnSucceeded(e1 -> {
                AddDataToFlowPane(RefreshService.getValue());
                refreshDragflowPane();
            });
        });
        refreshIconLabel.getStyleClass().add("icon-fill");
        refreshIconLabel.getStyleClass().add("font-25");
        refreshIconLabel.getStyleClass().add("refreshIconLabel");


        HBox hb = new HBox(refreshIconLabel);

        hb.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(hb, label);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("radius20");
        SystemConfig config = SystemConfigStorage.ReadSettings();
        if (config.isBackgroundImageSet) {
            vbox.getStyleClass().remove("BgColorWhite");
            vbox.getStyleClass().add("BgColorRgbaDark");

        } else {
            vbox.getStyleClass().remove("BgColorRgbaDark");
            vbox.getStyleClass().add("BgColorWhite");
        }
        vbox.setMaxSize(150, 5);

        vbox.setOnMouseEntered(e -> {
            SystemConfig config1 = SystemConfigStorage.ReadSettings();
            if (config1.isBackgroundImageSet) {
                vbox.getStyleClass().remove("BgColorWhite");
                vbox.getStyleClass().add("BgColorRgbaDark");

            } else {
                vbox.getStyleClass().remove("BgColorRgbaDark");
                vbox.getStyleClass().add("BgColorWhite");
            }

        });
        refreshVBox = new VBox(vbox);

        refreshVBox.setSpacing(5);
        refreshVBox.setPadding(new Insets(5));
        refreshVBox.setPrefSize(this.contentScroll.getWidth() - 10, 5);
        refreshVBox.setAlignment(Pos.CENTER);


        this.contentFlow.getChildren().add(refreshVBox);

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
        rotateEditLabel.setPeriod(Duration.seconds(3));

        refreshIconLabel.setOnMouseEntered(e1 -> {
            rotateEditLabel.cancel();
        });
        refreshIconLabel.setOnMouseExited(e1 -> {
            rotateEditLabel.restart();
        });
        refreshIconLabel.setOnMouseEntered(e1 -> {
            rotateEditLabel.cancel();
        });
        refreshIconLabel.setOnMouseExited(e1 -> {
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
            RotateTransition editrotate = new RotateTransition(Duration.seconds(2.5), refreshIconLabel);
            editrotate.setFromAngle(0.0);
            //		    editrotate.setToAngle ( 1080.0 );
            editrotate.setToAngle(1080.0);
            // Let the animation run two times
            editrotate.setCycleCount(1);

            // Reverse direction on alternating cycles
            editrotate.setAutoReverse(false);
            editrotate.play();


            RotateTransition editrotate1 = new RotateTransition(Duration.seconds(2.5), refreshIconLabel);
            editrotate1.setFromAngle(0.0);
            //		    editrotate.setToAngle ( 1080.0 );
            editrotate1.setToAngle(1080.0);
            // Let the animation run two times
            editrotate1.setCycleCount(1);

            // Reverse direction on alternating cycles
            editrotate1.setAutoReverse(false);
            editrotate1.play();

            //	  		TranslateTransition translateTransition1 = new TranslateTransition ( Duration.seconds ( 0.5 ) , hb );
            //	  		translateTransition1.interpolatorProperty ( ).set ( Interpolator.SPLINE ( .1 , .1 , .7 , .7 ) );
            //	  		translateTransition1.setByY ( - 2 );
            //	  		translateTransition1.setAutoReverse ( true );
            //	  		translateTransition1.setCycleCount ( Timeline.INDEFINITE );
            //	  		translateTransition1.play ( );


        });


        contentFlow.getChildren().add(new Hyperlink("Hyper"));
        for (TextObject obj : noteList) {
            NoteCard controller = new NoteCard(obj.getAbsolutePath(), true);
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("NoteCard", controller);
            this.contentFlow.getChildren().add(parent);


            //		controller.puttextfield.focusedProperty ( ).addListener ( new ChangeListener < Boolean > ( )
            //		{
            //		    @Override
            //		    public void changed ( ObservableValue < ? extends Boolean > arg0 , Boolean oldPropertyValue , Boolean newPropertyValue )
            //		    {
            //			  if ( newPropertyValue ) {
            //
            //			  }
            //			  else {
            //				//			  String text = puttextfield.getText ( ).trim ( );
            //				controller.putlabelhbox.setVisible ( true );
            //				controller.putlabelhbox2.setVisible ( false );
            //
            //				controller.putlabel.setText ( controller.puttextfield.getText ( ).trim ( ) );
            //
            //				refreshDragflowPane ( );
            //
            //
            //				//			  putlabel.setVisible ( true );
            //			  }
            //		    }
            //		} );
            //		controller.puttextfield.textProperty ( ).addListener ( ( obs , old , niu ) -> {
            //		    if ( controller.puttextfield.getText ( ).isEmpty ( ) ) {
            //			  NoteCardSettingStorage.setLabel ( controller.returnTextObject ( ).getAbsolutePath ( ) , null );
            //		    }
            //		    else {
            //			  NoteCardSettingStorage.setLabel ( controller.returnTextObject ( ).getAbsolutePath ( ) , controller.puttextfield.getText ( ).trim ( ) );
            //		    }
            //
            //		} );

            controller.glasswindowmode.setOnAction(e1 -> {
                glasswindowMode(controller.returnTextObject());
            });

            controller.editLabel.setOnMouseClicked(e1 -> {
                if (e1.getButton() == MouseButton.PRIMARY) {
                    TextObject textObject = controller.returnTextObject();
                    this.tempObject = textObject;
                    this.StartServices();
                    changeScrollPanePosition();
//                    if (SaveSettings.ReadSettings().type.equals(VIEW.MODAL)) {
//                        if (this.expandPane.isVisible()) {
//                            changeScrollPaneToOriginalPosition();
//                        }
//                        new EditNoteUI_Test(textObject.getAbsolutePath()).startUINewWindow(new Stage());
//
//                    } else {
//                        this.StartServices();
//                        changeScrollPanePosition();
//                    }
                }

            });
            //		controller.pinNoteMenuItem.setOnAction ( e -> {
            //
            //		    //		    Settings settings = SaveSettings.ReadSettings ( );
            //		    //		    settings.pinPath = obj.getAbsolutePath ( );
            //		    //		    SaveSettings.SaveSettings ( settings );
            //		    //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
            //		    //		    this.refreshDragflowPane ( );
            //		    pinnoteactiontemp = obj;
            //		    Background_PinNoteAction.restart ( );
            //		    Background_PinNoteAction.setOnRunning ( e1 -> {
            //			  bindUIandService ( ( Stage ) page1Anchor.getScene ( ).getWindow ( ) , Background_PinNoteAction );

            //		    } );
            //		    Background_PinNoteAction.setOnSucceeded ( e1 -> {
            //			  AddDataToFlowPane ( Background_PinNoteAction.getValue ( ) );
            //			  refreshDragflowPane ( );
            //		    } );
            //
            //
            //		} );

            controller.pinNoteMenuItem.setOnAction(e -> {
                Service<ArrayList<TextObject>> setPinService = new Service<ArrayList<TextObject>>() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //					  Thread.sleep ( 500 );
                                Settings settings = SaveSettings.ReadSettings();
                                settings.pinPath = obj.getAbsolutePath();
                                SaveSettings.SaveSettings(settings);
                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                setPinService.restart();
                setPinService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), setPinService);
                    controller.pane
                            .disableProperty()
                            .bind(
                                    setPinService.runningProperty()
                            );
                });
                setPinService.setOnSucceeded(e1 -> {
                    this.AddDataToFlowPane(setPinService.getValue());
                    //			  this.refreshDragflowPane ( );
                    dragRefreshService.restart();
                });

                dragRefreshService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e1 -> {
                    this.refreshDragflowPane();
                });

                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , "YouTack" );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });
            controller.unpinNoteMenuItem.setOnAction(e -> {
                Service<ArrayList<TextObject>> setPinService = new Service<ArrayList<TextObject>>() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //					  Thread.sleep ( 500 );
                                Settings settings = SaveSettings.ReadSettings();
                                settings.pinPath = null;
                                SaveSettings.SaveSettings(settings);
                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                setPinService.restart();
                setPinService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), setPinService);
                    controller.pane
                            .disableProperty()
                            .bind(
                                    setPinService.runningProperty()
                            );
                });
                setPinService.setOnSucceeded(e1 -> {
                    this.AddDataToFlowPane(setPinService.getValue());
                    //			  this.refreshDragflowPane ( );
                    dragRefreshService.restart();
                });

                dragRefreshService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e1 -> {
                    this.refreshDragflowPane();
                });

                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , "YouTack" );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });
            controller.setPasswordAllNote.setOnAction(e -> {
                Service<ArrayList<TextObject>> removePasswordService = new Service() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //						    Thread.sleep ( 500 );

                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                removePasswordService.restart();
                removePasswordService.setOnRunning(e2 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), removePasswordService);
                });
                removePasswordService.setOnSucceeded(e2 -> {
                    removePasswordService.getValue().stream().forEach(textObject -> {
                        NoteCardSettingStorage.setPassword(textObject.getAbsolutePath(), SystemConfigStorage.ReadSettings().password);
                    });
                    this.AddDataToFlowPane(removePasswordService.getValue());
                    dragRefreshService.restart();
                });
                dragRefreshService.setOnRunning(e2 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e2 -> {
                    this.refreshDragflowPane();
                });
                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , null );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });
            controller.setPasswordOnlyThisNote.setOnAction(e -> {
                Service<ArrayList<TextObject>> setPasswordService = new Service<ArrayList<TextObject>>() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //					  Thread.sleep ( 500 );
                                NoteCardSettingStorage.setPassword(obj.getAbsolutePath(), SystemConfigStorage.ReadSettings().password);
                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                setPasswordService.restart();
                setPasswordService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), setPasswordService);
                });
                setPasswordService.setOnSucceeded(e1 -> {
                    this.AddDataToFlowPane(setPasswordService.getValue());
                    //			  this.refreshDragflowPane ( );
                    dragRefreshService.restart();
                });

                dragRefreshService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e1 -> {
                    this.refreshDragflowPane();
                });

                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , "YouTack" );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });
            controller.removePasswordMenuItem.setOnAction(e -> {
                Service<ArrayList<TextObject>> removePasswordService = new Service() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //					  Thread.sleep ( 500 );
                                NoteCardSettingStorage.setPassword(obj.getAbsolutePath(), null);
                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                removePasswordService.restart();
                removePasswordService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), removePasswordService);
                });
                removePasswordService.setOnSucceeded(e1 -> {
                    this.AddDataToFlowPane(removePasswordService.getValue());
                    dragRefreshService.restart();
                });
                dragRefreshService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e1 -> {
                    this.refreshDragflowPane();
                });
                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , null );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });

            controller.removePasswordAllMenuItem.setOnAction(e1 -> {
                Service<ArrayList<TextObject>> removePasswordService = new Service() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //						    Thread.sleep ( 500 );

                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                removePasswordService.restart();
                removePasswordService.setOnRunning(e2 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), removePasswordService);
                });
                removePasswordService.setOnSucceeded(e2 -> {
                    removePasswordService.getValue().stream().forEach(textObject -> {
                        NoteCardSettingStorage.setPassword(textObject.getAbsolutePath(), null);
                    });
                    this.AddDataToFlowPane(removePasswordService.getValue());
                    dragRefreshService.restart();
                });
                dragRefreshService.setOnRunning(e2 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e2 -> {
                    this.refreshDragflowPane();
                });
                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , null );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });

            controller.putlabel.setOnMouseClicked(new EventHandler<MouseEvent>()      //-> double click label to change textarea
            {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 1) {
                            //				putlabel.setVisible ( false );
                            controller.putlabelhbox.setVisible(false);
                            controller.putlabelhbox2.setVisible(true);
                            controller.puttextfield.setVisible(true);
                            controller.puttextfield.setText(controller.putlabel.getText().trim());
                        }
                    }
                }
            });


        }

    }

    private void refreshDragflowPane() {
        if (SaveSettings.ReadSettings().pinPath == null) {
            this.dragflowPane.getChildren().clear();
            drapanddropscreen.setVisible(false);

        }
        if (SaveSettings.ReadSettings().pinPath != null && FileHandlings.checkFileExist(SaveSettings.ReadSettings().pinPath)) {
            this.dragflowPane.getChildren().clear();
            drapanddropscreen.setVisible(false);


            NoteCard controller = new NoteCard(SaveSettings.ReadSettings().pinPath, true);


            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("NoteCard", controller);
            // Set up a Fade Transition
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), parent);
            fadeTransition.setFromValue(0.20);
            fadeTransition.setToValue(1.0);
            // Let the animation run two times
            fadeTransition.setCycleCount(1);
            // Reverse direction on alternating cycles
            fadeTransition.setAutoReverse(false);
            // Set up a Rotate Transition
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), parent);
            rotateTransition.setFromAngle(0.0);
            rotateTransition.setToAngle(1440.0);
            // Let the animation run two times
            rotateTransition.setCycleCount(1);
            // Reverse direction on alternating cycles
            rotateTransition.setAutoReverse(false);
            // Scale
            ScaleTransition trans = new ScaleTransition(Duration.seconds(4), parent);
            trans.setFromX(0.005);
            trans.setToX(1.0);
            trans.setFromY(0.005);
            trans.setToY(1.0);
            // Let the animation run forever
            trans.setCycleCount(1);
            // Reverse direction on alternating cycles
            trans.setAutoReverse(true);
            // Play the Animation
            trans.play();
            // Create and start a Parallel Transition
            ParallelTransition parTransition = new ParallelTransition();
            parTransition.setNode(parent);
            // Add the Children to the ParallelTransition
            parTransition.getChildren().addAll(fadeTransition, rotateTransition, trans);
            // Let the animation run forever
            //        parTransition.setCycleCount(PathTransition.INDEFINITE);
            parTransition.setAutoReverse(false);
            parTransition.setCycleCount(1);
            // Play the Animation
            parTransition.play();
            this.dragflowPane.getChildren().add(parent);


            controller.glasswindowmode.setOnAction(e1 -> {
                glasswindowMode(controller.returnTextObject());
            });

            controller.putlabel.setOnMouseClicked(new EventHandler<MouseEvent>()      //-> double click label to change textarea
            {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 1) {
                            //				putlabel.setVisible ( false );
                            controller.putlabelhbox.setVisible(false);
                            controller.putlabelhbox2.setVisible(true);
                            controller.puttextfield.setVisible(true);
                            controller.puttextfield.setText(controller.putlabel.getText().trim());
                        }
                    }
                }
            });


            controller.editLabel.setOnMouseClicked(e1 -> {      //dragflow
                if (e1.getButton() == MouseButton.PRIMARY) {
                    TextObject textObject = controller.returnTextObject();
                    this.tempObject = textObject;
                    this.StartServices();
                    changeScrollPanePosition();
//                    if (SaveSettings.ReadSettings().type.equals(VIEW.MODAL)) {
//                        if (this.expandPane.isVisible()) {
//                            changeScrollPaneToOriginalPosition();
//                        }
//                        new EditNoteUI_Test(textObject.getAbsolutePath()).startUINewWindow(new Stage());
//
//
//                    } else {
//                        this.StartServices();
//                        changeScrollPanePosition();
//                    }
                }

            });
            controller.setPasswordAllNote.setOnAction(e -> {
                Service<ArrayList<TextObject>> removePasswordService = new Service() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //						    Thread.sleep ( 500 );

                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                removePasswordService.restart();
                removePasswordService.setOnRunning(e2 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), removePasswordService);
                });
                removePasswordService.setOnSucceeded(e2 -> {
                    removePasswordService.getValue().stream().forEach(textObject -> {
                        NoteCardSettingStorage.setPassword(textObject.getAbsolutePath(), SystemConfigStorage.ReadSettings().password);
                    });
                    this.AddDataToFlowPane(removePasswordService.getValue());
                    dragRefreshService.restart();
                });
                dragRefreshService.setOnRunning(e2 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e2 -> {
                    this.refreshDragflowPane();
                });
                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , null );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });
            controller.setPasswordOnlyThisNote.setOnAction(e -> {      //dragflow
                Service<ArrayList<TextObject>> setPasswordService = new Service<ArrayList<TextObject>>() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //					  Thread.sleep ( 500 );
                                NoteCardSettingStorage.setPassword(controller.returnTextObject().getAbsolutePath(), SystemConfigStorage.ReadSettings().password);
                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                setPasswordService.restart();
                setPasswordService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), setPasswordService);
                });
                setPasswordService.setOnSucceeded(e1 -> {
                    this.AddDataToFlowPane(setPasswordService.getValue());
                    //			  this.refreshDragflowPane ( );
                    dragRefreshService.restart();
                });

                dragRefreshService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e1 -> {
                    this.refreshDragflowPane();
                });

                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , "YouTack" );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });


            controller.removePasswordMenuItem.setOnAction(e -> {      //dragflow
                Service<ArrayList<TextObject>> removePasswordService = new Service() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //					  Thread.sleep ( 500 );
                                NoteCardSettingStorage.setPassword(controller.returnTextObject().getAbsolutePath(), null);
                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                removePasswordService.restart();
                removePasswordService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), removePasswordService);
                });
                removePasswordService.setOnSucceeded(e1 -> {
                    this.AddDataToFlowPane(removePasswordService.getValue());
                    dragRefreshService.restart();
                });
                dragRefreshService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e1 -> {
                    this.refreshDragflowPane();
                });
                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , null );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });

            controller.removePasswordAllMenuItem.setOnAction(e1 -> {
                Service<ArrayList<TextObject>> removePasswordService = new Service() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //						    Thread.sleep ( 500 );

                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                removePasswordService.restart();
                removePasswordService.setOnRunning(e2 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), removePasswordService);
                });
                removePasswordService.setOnSucceeded(e2 -> {
                    removePasswordService.getValue().stream().forEach(textObject -> {
                        NoteCardSettingStorage.setPassword(textObject.getAbsolutePath(), null);
                    });
                    this.AddDataToFlowPane(removePasswordService.getValue());
                    dragRefreshService.restart();
                });
                dragRefreshService.setOnRunning(e2 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e2 -> {
                    this.refreshDragflowPane();
                });
                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , null );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });


            controller.pinNoteMenuItem.setOnAction(e -> {            //dragflow
                Service<ArrayList<TextObject>> setPinService = new Service<ArrayList<TextObject>>() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //					  Thread.sleep ( 500 );
                                Settings settings = SaveSettings.ReadSettings();
                                settings.pinPath = controller.returnTextObject().getAbsolutePath();
                                SaveSettings.SaveSettings(settings);
                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                setPinService.restart();
                setPinService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), setPinService);
                    controller.pane
                            .disableProperty()
                            .bind(
                                    setPinService.runningProperty()
                            );
                });
                setPinService.setOnSucceeded(e1 -> {
                    this.AddDataToFlowPane(setPinService.getValue());
                    //			  this.refreshDragflowPane ( );
                    dragRefreshService.restart();
                });

                dragRefreshService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e1 -> {
                    this.refreshDragflowPane();
                });

                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , "YouTack" );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });
            controller.unpinNoteMenuItem.setOnAction(e -> {
                Service<ArrayList<TextObject>> setPinService = new Service<ArrayList<TextObject>>() {
                    @Override
                    protected Task<ArrayList<TextObject>> createTask() {
                        return new Task<ArrayList<TextObject>>() {
                            @Override
                            protected ArrayList<TextObject> call() throws Exception {
                                //					  Thread.sleep ( 500 );
                                Settings settings = SaveSettings.ReadSettings();
                                settings.pinPath = null;
                                SaveSettings.SaveSettings(settings);
                                return FileHandlings.getTextObjectLists();
                            }
                        };
                    }
                };
                Service<Void> dragRefreshService = new Service<Void>() {
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
                setPinService.restart();
                setPinService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), setPinService);
                    controller.pane
                            .disableProperty()
                            .bind(
                                    setPinService.runningProperty()
                            );
                });
                setPinService.setOnSucceeded(e1 -> {
                    this.AddDataToFlowPane(setPinService.getValue());
                    //			  this.refreshDragflowPane ( );
                    dragRefreshService.restart();
                });

                dragRefreshService.setOnRunning(e1 -> {
                    bindUIandService((Stage) page1Anchor.getScene().getWindow(), dragRefreshService);
                });
                dragRefreshService.setOnSucceeded(e1 -> {
                    this.refreshDragflowPane();
                });

                //		    NoteCardSettingStorage.setPassword ( obj.getAbsolutePath ( ) , "YouTack" );
                //		    this.AddDataToFlowPane ( FileHandlings.getTextObjectLists ( ) );
                //		    this.refreshDragflowPane ( );
            });


        } else {
            drapanddropscreen.setVisible(true);
            dragflowPane.getChildren().clear();
        }
    }


    private void UI_Prepare() {


        //	  Parent parent = new TabModeUI_Test ().startUI ();
        //	  AnchorPane.setTopAnchor ( parent , 0.0);
        //	  AnchorPane.setRightAnchor ( parent , 0.0);
        //	  AnchorPane.setBottomAnchor ( parent , 0.0);
        //	  AnchorPane.setLeftAnchor ( parent , 0.0);
        //	  split2Replace.getChildren ().add ( parent );

        progressingLabel.getStyleClass().add("gradientColor3Fill");


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lg_progressBar.setProgress(0);
                page1SplitPane2.setDividerPositions(0);
            }
        });


        this.splitPaneOriginalPosition();


        this.indexingBox.setVisible(true);
        this.contentScroll.setVisible(false);

        this.contentScroll.setCache(true);
        this.contentScroll.setCacheHint(CacheHint.SPEED);
        this.contentScroll.setCacheHint(CacheHint.QUALITY);
        this.contentScroll.setCacheHint(CacheHint.SCALE);
        this.contentFlow.setCache(true);
        this.contentFlow.setCacheHint(CacheHint.SPEED);
        this.contentFlow.setCacheHint(CacheHint.QUALITY);
        this.contentFlow.setCacheHint(CacheHint.SCALE);
        this.dragScroll.setCache(true);
        this.dragScroll.setCacheHint(CacheHint.SPEED);
        this.dragScroll.setCacheHint(CacheHint.QUALITY);
        this.dragScroll.setCacheHint(CacheHint.SCALE);
        this.expandPane.setCache(true);
        this.expandPane.setCacheHint(CacheHint.SPEED);
        this.expandPane.setCacheHint(CacheHint.QUALITY);
        this.expandPane.setCacheHint(CacheHint.SCALE);


        //	  contentScroll.setVvalue ( TempPage1.ReadContentScrollPOSValue ( ) );
        //	  this.dragScroll.setVvalue ( TempPage1.ReadDragScrollPOSValue ( ) );


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                backgroundImage.setFitWidth(contentScroll.getWidth());
                backgroundImage.setFitHeight(contentScroll.getHeight());
                backgroundImage1.setFitWidth(dragScroll.getWidth());
                backgroundImage1.setFitHeight(dragScroll.getHeight());

                backgroundImage.setImage(null);
                backgroundImage1.setImage(null);

                backgroundImage.setEffect(null);
                backgroundImage1.setEffect(null);

                backgroundImagePrepare();


            }
        });


        //	  this.refreshDragflowPane ( );


        this.expandPane.setPrefSize(286, 320);      //Size
        this.expandPane.setVisible(false);                              //Invisible
        this.settingIconLabel.setVisible(false);

        this.transitionUpDown(settingScrollPane, 0, -1000);      //settting scroll to up
        this.scrollSpeed(0.001);


    }

    private void splitPaneOriginalPosition() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = (Stage) page1Anchor.getScene().getWindow();
                SplitUIMemory sum = SplitUIMemoryStorage.getSplitMemory(stage.getWidth());

                if (sum != null) {
                    page1SplitPane.setDividerPositions(sum.page1SplitPane1Value);
                    page1SplitPane2.setDividerPositions(sum.page1SplitPane2Value);
                } else {
                    page1SplitPane.setDividerPositions(0.80);
                    page1SplitPane2.setDividerPositions(0);
                }
            }
        });

    }

    private void backgroundImagePrepare() {
        Service<SystemConfig> service = new Service<SystemConfig>() {
            @Override
            protected Task<SystemConfig> createTask() {
                return new Task<SystemConfig>() {
                    @Override
                    protected SystemConfig call() throws Exception {
                        return SystemConfigStorage.ReadSettings();
                    }
                };
            }
        };
        service.restart();
        service.setOnSucceeded(e -> {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    SystemConfig sysconf = service.getValue();
                    if (sysconf.isBackgroundImageSet) {
                        if (sysconf.imagePath1 != null) {
                            backgroundImageBox.setStyle("-fx-background-color:" + sysconf.imagePath1BgColor + ";");
                            File file = new File(sysconf.imagePath1);
                            Image image = new Image(file.toURI().toString());
                            backgroundImage.setImage(image);
                            backgroundImage.setEffect(getFrostEffect(sysconf.imagePath1effectWidth, sysconf.imagePath1effectIteration));
                        }

                        if (sysconf.imagePath2 != null) {
                            backgroundImageBox1.setStyle("-fx-background-color:" + sysconf.imagePath2BgColor + ";");
                            File file1 = new File(sysconf.imagePath2);
                            Image image1 = new Image(file1.toURI().toString());
                            backgroundImage1.setImage(image1);
                            backgroundImage1.setEffect(getFrostEffect(sysconf.imagePath2effectWidth, sysconf.imagePath2effectIteration));
                        }
                    } else {
                        backgroundImageBox.setStyle("-fx-background-color:" + "transparent" + ";");
                        backgroundImageBox1.setStyle("-fx-background-color:" + "transparent" + ";");
                        backgroundImage.setImage(null);
                        backgroundImage.setEffect(null);
                        backgroundImage1.setImage(null);
                        backgroundImage1.setEffect(null);
                    }
                }
            });

        });


    }

    private void changeTheme(String hex) {
        String style = String.format("-fx-background-color: %s;", hex);
        expandPane.setStyle(style);
        settingScrollPane.setStyle(style + "-fx-border-radius: 20;" + "-fx-background-radius: 20;");

    }

    private Effect getFrostEffect(double blur_amount, int iteration) {
        Effect frostEffect =
                new BoxBlur(blur_amount, blur_amount, iteration);

        return frostEffect;
    }

    private void changeFont(String hex) {
        Label[] labels = new Label[]{createDateLabel, settingIconLabel, ToggleResizeLabel, modifiedDateLabel, closeExpandTextArea, settingLabel, filenamelbl, filenameLabel, BackgroundLabel, TextColorLabel};
        for (
                int i = 0;
                i < labels.length;
                ++i
        ) {
            Label c = labels[i];
            if (hex != null) {
                //		    c.setTextFill ( Color.valueOf ( hex ) );
                //		    c.setTextFill ( Color.web ( hex ) );
                c.setStyle("-fx-text-fill:" + hex + ";");
            }


        }
        expandTextArea.setStyle("-fx-text-fill:" + hex + ";");
    }


    private void transitionUpDown(Node node, double startValue, double endValue)      //UI Support
    {

        node.setCache(true);
        node.setCacheHint(CacheHint.SPEED);
        node.setCacheHint(CacheHint.QUALITY);
        node.setCacheHint(CacheHint.SCALE);


        KeyValue start1 = new KeyValue(node.translateYProperty(), startValue);
        KeyValue end1 = new KeyValue(node.translateYProperty(), endValue);
        KeyValue cache = new KeyValue(node.cacheHintProperty(), CacheHint.SPEED);
        KeyValue cache2 = new KeyValue(node.cacheHintProperty(), CacheHint.QUALITY);
        KeyValue cache3 = new KeyValue(node.cacheHintProperty(), CacheHint.SCALE);

        KeyFrame startFrame1 = new KeyFrame(Duration.ZERO, start1, cache, cache2, cache3);
        KeyFrame endFrame1 = new KeyFrame(Duration.seconds(2.5), end1, cache, cache2, cache3);

        Timeline timeline1 = new Timeline(startFrame1, endFrame1);
        timeline1.setAutoReverse(false);
        timeline1.setCycleCount(1);
        timeline1.play();
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

    private void scrollSpeed(double speedUnit)    //UI Support
    {
        contentScroll.getContent().setOnScroll(scrollEvent -> { // scrollpane speed
            double deltaY = scrollEvent.getDeltaY() * speedUnit;//0.001
            contentScroll.setVvalue(contentScroll.getVvalue() - deltaY);
        });
        dragScroll.getContent().setOnScroll(scrollEvent -> { // scrollpane speed
            double deltaY = scrollEvent.getDeltaY() * speedUnit;//0.001
            dragScroll.setVvalue(dragScroll.getVvalue() - deltaY);
        });
        uiAppearanceScroll.getContent().setOnScroll(scrollEvent -> { // scrollpane speed
            double deltaY = scrollEvent.getDeltaY() * speedUnit;//0.001
            uiAppearanceScroll.setVvalue(uiAppearanceScroll.getVvalue() - deltaY);
        });
    }

    private void slowScrollToTop(ScrollPane scrollPane) {
        scrollPane.setCache(true);
        scrollPane.setCacheHint(CacheHint.QUALITY);
        scrollPane.setCacheHint(CacheHint.SPEED);
        scrollPane.setCacheHint(CacheHint.SCALE);

        //        KeyValue start =new KeyValue ( scrollPane.vvalueProperty (),0 );
        //        KeyValue cache  = new KeyValue ( scrollPane.cacheHintProperty (),CacheHint.QUALITY );
        //
        //	  KeyFrame keyFrame = new KeyFrame ( Duration.ZERO, start , cache);
        //
        //	  Timeline timeline = new Timeline ( keyFrame );
        //	  timeline.setAutoReverse ( false );
        //	  timeline.setCycleCount ( 1 );
        //	  timeline.play ();

        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(scrollPane.vvalueProperty(), 0),
                        new KeyValue(scrollPane.cacheHintProperty(), CacheHint.QUALITY)));
        animation.play();


    }


    private void slowScrollToBot(ScrollPane scrollPane) {
        scrollPane.setCache(true);
        scrollPane.setCacheHint(CacheHint.QUALITY);
        scrollPane.setCacheHint(CacheHint.SPEED);
        scrollPane.setCacheHint(CacheHint.SCALE);

        //        KeyValue start =new KeyValue ( scrollPane.vvalueProperty (),0 );
        //        KeyValue cache  = new KeyValue ( scrollPane.cacheHintProperty (),CacheHint.QUALITY );
        //
        //	  KeyFrame keyFrame = new KeyFrame ( Duration.ZERO, start , cache);
        //
        //	  Timeline timeline = new Timeline ( keyFrame );
        //	  timeline.setAutoReverse ( false );
        //	  timeline.setCycleCount ( 1 );
        //	  timeline.play ();

        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(scrollPane.vvalueProperty(), 1),
                        new KeyValue(scrollPane.cacheHintProperty(), CacheHint.QUALITY)));
        animation.play();


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

    private void changeScrollPaneToOriginalPosition()      //change to original position
    {
        //	  Service_TextObject.cancel ( );
        //	  Service_BackgroundColor.cancel ( );
        //	  Service_TextColor.cancel ( );

        this.transitionLeftRight(this.contentScroll, 270, 0);
        this.transitionUpDown(this.contentScroll, -50, 0);
        //	  AnchorPane.setTopAnchor ( contentScroll , 157.0 );
        AnchorPane.setRightAnchor(this.contentScroll, 365.0);//25.0
        this.expandPane.setVisible(false);


        this.slowScrollToTop(contentScroll);


    }

    private void changeScrollPanePosition()      //change scrollPane to rightTop
    {

        //	  Service_TextObject.restart ( );
        //	  Service_BackgroundColor.restart ( );
        //	  Service_TextColor.restart ( );
        this.dragScroll.setVisible(false);
        this.contentScroll.setEffect(getFrostEffect(200, 3));
        this.transitionLeftRight(contentScroll, 0, 270);
        this.transitionUpDown(contentScroll, 0, -50);
        AnchorPane.setRightAnchor(contentScroll, 200.0);
        this.fadeUp(expandPane);
        this.expandPane.setVisible(true);

    }

    private String toHexString(Color color) {
        int r = ((int) Math.round(color.getRed() * 255)) << 24;
        int g = ((int) Math.round(color.getGreen() * 255)) << 16;
        int b = ((int) Math.round(color.getBlue() * 255)) << 8;
        int a = ((int) Math.round(color.getOpacity() * 255));

        return String.format("#%08X", (r + g + b + a));
    }

    private String getCircleColor(Circle circle) {
        Color c = (Color) circle.getFill();
        String hex = String.format("#%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
        return hex;
    }


    private void Service_Exchange() {
        //	  if ( SaveSettings.ReadSettings ( ).SYNC_DELAY_PERMIT ) {      //Permit
        //		this.StartScheduleServices ( );
        //	  }
        //	  else {
        //
        //		this.page1Anchor.setOnMouseEntered ( e -> {
        //		    this.CancelScheduleServices ( );
        //		    this.StartServices ( );
        //		} );
        //		this.page1Anchor.setOnMouseExited ( e -> {
        //		    this.CancelScheduleServices ( );
        //		    this.StartServices ( );
        //		} );
        //	  }


    }

    //    private void glasswindowMode ( TextObject object )
    //    {
    //
    //	  NoteCard controller = new NoteCard ( object.getAbsolutePath ( ) , false , 500 , 500 );
    //	  RecentNoteStorage.setRecentNote ( object.getAbsolutePath ( ) , object.getAbsolutePath ( ) );
    //	  Parent parent = ___Bundle.__ViewLoader._getInstance ( )._load ( "NoteCard" , controller );
    //	  Scene scene = new Scene ( parent );
    //	  Stage stage = new Stage ( );
    //	  stage.initStyle ( StageStyle.TRANSPARENT );
    //	  scene.setFill ( Color.TRANSPARENT );
    //	  stage.initModality ( Modality.NONE );
    //
    //
    //	  MenuItem[] disableNodes = { controller.cardsizeMenu , controller.pinNoteMenuItem , controller.unpinNoteMenuItem , controller.deleteNoteMenuItem , controller.glasswindowmode , controller.setpasswordMenu , controller.removePasswordMenuItem };
    //	  int k = 0;
    //	  while ( k < disableNodes.length ) {
    //		disableNodes[ k ].setVisible ( false );
    //		k++;
    //	  }
    //
    //	  //	  controller.cardsizeMenu.setDisable ( true );
    //	  controller.cardsizeMenu.setText ( "Card Size ( disable in this mode. )" );
    //	  //	  controller.pinNoteMenuItem.setDisable ( true );
    //	  controller.pinNoteMenuItem.setText ( "Pin Note ( disable in this mode. )" );
    //	  //	  controller.unpinNoteMenuItem.setDisable ( true );
    //	  controller.unpinNoteMenuItem.setText ( "UnPin Note ( disable in this mode. )" );
    //	  //	  controller.glasswindowmode.setDisable ( true );
    //	  controller.setpasswordMenu.setText ( "Set Password ( disable in this mode. )" );
    //
    //
    //	  controller.closeNoteCardLabel.setVisible ( true );
    //
    //	  controller.closeNoteCardLabel.setOnMouseClicked ( new EventHandler < MouseEvent > ( )      //-> double click label to change textarea
    //	  {
    //
    //		@Override
    //		public void handle ( MouseEvent mouseEvent )
    //		{
    //		    if ( mouseEvent.getButton ( ).equals ( MouseButton.PRIMARY ) ) {
    //			  stage.close ( );
    //		    }
    //		}
    //	  } );
    //
    //
    //	  controller.editLabel.setOnMouseClicked ( e -> {
    //
    //		controller.textLabel.setVisible ( false );      //change label to textarea
    //		controller.textArea.setVisible ( true );
    //		//				textArea.setText ( textLabel.getText ( ) );
    //		controller.textArea.setText ( controller.TextObject_Service.getValue ( ).getText ( ) );
    //
    //		Platform.runLater ( new Runnable ( )
    //		{
    //		    @Override
    //		    public void run ( )
    //		    {
    //			  controller.textArea.requestFocus ( );
    //		    }
    //		} );
    //	  } );
    //
    //
    //	  Node node[] = { controller.pane , controller.optionsHbox , controller.textLabel , controller.imageView };
    //	  for (
    //		    int i = 0 ;
    //		    i < node.length ;
    //		    i++
    //	  ) {
    //		Node n = node[ i ];
    //		n.setOnMousePressed ( new EventHandler < MouseEvent > ( )
    //		{
    //		    @Override
    //		    public void handle ( MouseEvent event )
    //		    {
    //			  n.setCursor ( Cursor.CLOSED_HAND );
    //			  xOffset = stage.getX ( ) - event.getScreenX ( );
    //			  yOffset = stage.getY ( ) - event.getScreenY ( );
    //		    }
    //		} );
    //		n.setOnMouseDragged ( new EventHandler < MouseEvent > ( )
    //		{
    //		    @Override
    //		    public void handle ( MouseEvent event )
    //		    {
    //			  n.setCursor ( Cursor.CLOSED_HAND );
    //			  stage.setX ( event.getScreenX ( ) + xOffset );
    //			  stage.setY ( event.getScreenY ( ) + yOffset );
    //		    }
    //		} );
    //		n.setOnMouseReleased ( e -> {
    //		    n.setCursor ( Cursor.HAND );
    //		} );
    //	  }
    //
    //
    //	  stage.setScene ( scene );
    //	  stage.show ( );
    //
    //
    //	  controller.putlabel.setOnMouseClicked ( new EventHandler < MouseEvent > ( )      //-> double click label to change textarea
    //	  {
    //
    //		@Override
    //		public void handle ( MouseEvent mouseEvent )
    //		{
    //		    if ( mouseEvent.getButton ( ).equals ( MouseButton.PRIMARY ) ) {
    //			  if ( mouseEvent.getClickCount ( ) == 1 ) {
    //				//				putlabel.setVisible ( false );
    //				controller.putlabelhbox.setVisible ( false );
    //				controller.putlabelhbox2.setVisible ( true );
    //				controller.puttextfield.setVisible ( true );
    //				controller.puttextfield.setText ( controller.putlabel.getText ( ).trim ( ) );
    //			  }
    //		    }
    //		}
    //	  } );
    //
    //    }

    private void glasswindowMode(TextObject object) {
        new NoteCardUI_Test(object.getAbsolutePath()).startUINewWindow(new Stage());
    }


    private void init() {
        this.ServicesRepo();
        this.UI_Perform();
        this.UI_Binding();
        this.UI_Prepare();

        this.Service_Exchange();
        this.StartServices();


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(lg_progressBar.progressProperty(), 0)),
                        new KeyFrame(Duration.seconds(3), e -> {
                            // do anything you need here on completion...
                            System.out.println("Minute over");
                        }, new KeyValue(lg_progressBar.progressProperty(), 1))
                );
                timeline.setCycleCount(1);
                timeline.play();
            }
        });

        //
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(1));
                pauseTransition1.setOnFinished(e -> progressingLabel.setText("refreshing caches.."));
                pauseTransition1.play();
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                PauseTransition pauseTransition2 = new PauseTransition(Duration.seconds(2));
                pauseTransition2.setOnFinished(e -> progressingLabel.setText("refreshing UI values.."));
                pauseTransition2.play();
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                PauseTransition pauseTransition3 = new PauseTransition(Duration.seconds(2.5));
                pauseTransition3.setOnFinished(e -> progressingLabel.setText("indexing save caches.."));
                pauseTransition3.play();
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                PauseTransition pauseTransition4 = new PauseTransition(Duration.seconds(4));
                pauseTransition4.setOnFinished(e -> progressingLabel.setText("preparing notespace.."));
                pauseTransition4.play();
            }
        });


    }

    private void Save(String absoultePath, String text) {
        try {
            FileHandlings.FileWrite(absoultePath, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.init();
    }
}
