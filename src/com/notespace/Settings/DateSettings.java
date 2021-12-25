package com.notespace.Settings;

import com.notespace.ENUM.DateFormat;
import com.notespace.ENUM.TimeFormat;
import com.notespace.FileHandler.SaveSettings;
import com.notespace.FileHandler.Settings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class DateSettings implements Initializable {
    public static ScheduledService<Settings> syncSettings = new ScheduledService() {
        @Override
        protected Task<Settings> createTask() {
            return new Task<Settings>() {
                @Override
                protected Settings call() throws Exception {
                    return SaveSettings.ReadSettings();
                }
            };
        }
    };
    @FXML
    public HBox nextHbox, previousHbox;
    @FXML
    RadioButton rd_expand_createDateFormat1, rd_expand_createDateFormat2,
            rd_expand_modifiedDateFormat1, rd_expand_modifiedDateFormat2,
            rd_expand_prettyFormat,
            rd_expand_modifiedTimeFormat1, rd_expand_modifiedTimeFormat2;
    @FXML
    RadioButton rd_notecard_createDateFormat1, rd_notecard_createDateFormat2,
            rd_notecard_modifiedDateFormat1, rd_notecard_modifiedDateFormat2,
            rd_notecard_prettyFormat,
            rd_notecard_modifiedTimeFormat1, rd_notecard_modifiedTimeFormat2;
    @FXML
    AnchorPane DateSettingsAnchor;
    @FXML
    ScrollPane datesettingsScroll;

    void Sync_Functions() {
        syncSettings.setRestartOnFailure(true);
        syncSettings.setPeriod(Duration.seconds(1));
        syncSettings.setOnRunning(e -> {

        });
        syncSettings.setOnSucceeded(e -> {
            Settings settings = syncSettings.getValue();
            //Expand
            if (settings.NoteCard_CreateDateFormat.equals(DateFormat.FORMAT1.getFormat())) {

                rd_expand_createDateFormat1.setSelected(true);
                rd_expand_createDateFormat2.setSelected(false);
            } else if (settings.NoteCard_CreateDateFormat.equals(DateFormat.FORMAT2.getFormat())) {

                rd_expand_createDateFormat2.setSelected(true);
                rd_expand_createDateFormat1.setSelected(false);
            }

            if (settings.NoteCard_ModifiedTimePrettyFormat == false) {      //this means if pretty is false
                rd_expand_prettyFormat.setSelected(false);
                if (settings.NoteCard_ModifiedDateFormat.equals(DateFormat.FORMAT1.getFormat())) {
                    rd_expand_modifiedDateFormat1.setSelected(true);
                    rd_expand_modifiedDateFormat2.setSelected(false);
                } else if (settings.NoteCard_ModifiedDateFormat.equals(DateFormat.FORMAT2.getFormat())) {
                    rd_expand_modifiedDateFormat2.setSelected(true);
                    rd_expand_modifiedDateFormat1.setSelected(false);
                }

                if (settings.NoteCard_ModifiedTimeFormat.equals(TimeFormat.FORMAT1.getFormat())) {
                    rd_expand_modifiedTimeFormat1.setSelected(true);
                    rd_expand_modifiedTimeFormat2.setSelected(false);
                } else if (settings.NoteCard_ModifiedTimeFormat.equals(TimeFormat.FORMAT2.getFormat())) {
                    rd_expand_modifiedTimeFormat2.setSelected(true);
                    rd_expand_modifiedTimeFormat1.setSelected(false);
                }
            } else {
                rd_expand_prettyFormat.setSelected(true);
                rd_expand_modifiedDateFormat1.setSelected(false);
                rd_expand_modifiedDateFormat2.setSelected(false);

                rd_expand_modifiedTimeFormat1.setSelected(false);
                rd_expand_modifiedTimeFormat2.setSelected(false);
            }

            //Notecard
            if (settings.NoteCard_CreateDateFormat.equals(DateFormat.FORMAT1.getFormat())) {

                rd_notecard_createDateFormat1.setSelected(true);
                rd_notecard_createDateFormat2.setSelected(false);
            } else if (settings.NoteCard_CreateDateFormat.equals(DateFormat.FORMAT2.getFormat())) {

                rd_notecard_createDateFormat2.setSelected(true);
                rd_notecard_createDateFormat1.setSelected(false);
            }

            if (settings.NoteCard_ModifiedTimePrettyFormat == false) {      //this means if pretty is false
                rd_notecard_prettyFormat.setSelected(false);
                if (settings.NoteCard_ModifiedDateFormat.equals(DateFormat.FORMAT1.getFormat())) {
                    rd_notecard_modifiedDateFormat1.setSelected(true);
                    rd_notecard_modifiedDateFormat2.setSelected(false);
                } else if (settings.NoteCard_ModifiedDateFormat.equals(DateFormat.FORMAT2.getFormat())) {
                    rd_notecard_modifiedDateFormat2.setSelected(true);
                    rd_notecard_modifiedDateFormat1.setSelected(false);
                }

                if (settings.NoteCard_ModifiedTimeFormat.equals(TimeFormat.FORMAT1.getFormat())) {
                    rd_notecard_modifiedTimeFormat1.setSelected(true);
                    rd_notecard_modifiedTimeFormat2.setSelected(false);
                } else if (settings.NoteCard_ModifiedTimeFormat.equals(TimeFormat.FORMAT2.getFormat())) {
                    rd_notecard_modifiedTimeFormat2.setSelected(true);
                    rd_notecard_modifiedTimeFormat1.setSelected(false);
                }
            } else {
                rd_notecard_prettyFormat.setSelected(true);
                rd_notecard_modifiedDateFormat1.setSelected(false);
                rd_notecard_modifiedDateFormat2.setSelected(false);

                rd_notecard_modifiedTimeFormat1.setSelected(false);
                rd_notecard_modifiedTimeFormat2.setSelected(false);
            }


        });
        syncSettings.setOnCancelled(e -> {

        });


    }

    void UI_Actions() {
        this.nextHbox.setOnMouseClicked(e -> {
            this.slowScrollToRight(this.datesettingsScroll);
        });
//	  this.previousHbox.setOnMouseClicked ( e -> {
//		this.slowScrollToLeft ( this.datesettingsScroll );
//	  } );

        //Expand
        rd_expand_createDateFormat1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_CreateDateFormat = DateFormat.FORMAT1.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });

        rd_expand_createDateFormat2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_CreateDateFormat = DateFormat.FORMAT2.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });

        rd_expand_modifiedDateFormat1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimePrettyFormat = false;
                    settings.NoteCard_ModifiedDateFormat = DateFormat.FORMAT1.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });

        rd_expand_modifiedDateFormat2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimePrettyFormat = false;
                    settings.NoteCard_ModifiedDateFormat = DateFormat.FORMAT2.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });

        rd_expand_prettyFormat.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimePrettyFormat = true;
                    SaveSettings.SaveSettings(settings);

                    rd_expand_modifiedDateFormat1.setDisable(true);
                    rd_expand_modifiedDateFormat2.setDisable(true);

                    rd_expand_modifiedTimeFormat1.setDisable(true);
                    rd_expand_modifiedTimeFormat2.setDisable(true);
                } else {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimePrettyFormat = false;
                    SaveSettings.SaveSettings(settings);

                    rd_expand_modifiedDateFormat1.setDisable(false);
                    rd_expand_modifiedDateFormat2.setDisable(false);

                    rd_expand_modifiedTimeFormat1.setDisable(false);
                    rd_expand_modifiedTimeFormat2.setDisable(false);
                }
            }
        });

        rd_expand_modifiedTimeFormat1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimeFormat = TimeFormat.FORMAT1.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });

        rd_expand_modifiedTimeFormat2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimeFormat = TimeFormat.FORMAT2.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });


        //NoteCard
        rd_notecard_createDateFormat1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_CreateDateFormat = DateFormat.FORMAT1.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });

        rd_notecard_createDateFormat2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_CreateDateFormat = DateFormat.FORMAT2.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });

        rd_notecard_modifiedDateFormat1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimePrettyFormat = false;
                    settings.NoteCard_ModifiedDateFormat = DateFormat.FORMAT1.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });

        rd_notecard_modifiedDateFormat2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimePrettyFormat = false;
                    settings.NoteCard_ModifiedDateFormat = DateFormat.FORMAT2.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });

        rd_notecard_prettyFormat.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimePrettyFormat = true;
                    SaveSettings.SaveSettings(settings);

                    rd_notecard_modifiedDateFormat1.setDisable(true);
                    rd_notecard_modifiedDateFormat2.setDisable(true);

                    rd_notecard_modifiedTimeFormat1.setDisable(true);
                    rd_notecard_modifiedTimeFormat2.setDisable(true);
                } else {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimePrettyFormat = false;
                    SaveSettings.SaveSettings(settings);

                    rd_notecard_modifiedDateFormat1.setDisable(false);
                    rd_notecard_modifiedDateFormat2.setDisable(false);

                    rd_notecard_modifiedTimeFormat1.setDisable(false);
                    rd_notecard_modifiedTimeFormat2.setDisable(false);
                }
            }
        });

        rd_notecard_modifiedTimeFormat1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimeFormat = TimeFormat.FORMAT1.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });

        rd_notecard_modifiedTimeFormat2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    // ...
                    Settings settings = SaveSettings.ReadSettings();
                    settings.NoteCard_ModifiedTimeFormat = TimeFormat.FORMAT2.getFormat();
                    SaveSettings.SaveSettings(settings);
                } else {
                    // ...
                }
            }
        });


    }

    void init() {
        Sync_Functions();
        UI_Actions();

        this.datesettingsScroll.setCache(true);
        this.datesettingsScroll.setCacheHint(CacheHint.SPEED);
        this.datesettingsScroll.setCacheHint(CacheHint.QUALITY);
        this.datesettingsScroll.setCacheHint(CacheHint.SCALE);
        this.datesettingsScroll.setCache(true);


        //	  this.DateSettingsAnchor.setOnMouseEntered ( e -> {
        //		this.syncSettings.restart ( );
        //	  } );
        //	  this.DateSettingsAnchor.setOnMouseExited ( e -> {
        //		this.syncSettings.restart ( );
        //	  } );

    }

    private void slowScrollToRight(ScrollPane scrollPane) {
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
                        new KeyValue(scrollPane.hvalueProperty(), 1),
                        new KeyValue(scrollPane.cacheHintProperty(), CacheHint.QUALITY)));
        animation.play();


    }

    private void slowScrollToLeft(ScrollPane scrollPane) {
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
                        new KeyValue(scrollPane.hvalueProperty(), 0),
                        new KeyValue(scrollPane.cacheHintProperty(), CacheHint.QUALITY)));
        animation.play();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        syncSettings.restart();


    }
}
