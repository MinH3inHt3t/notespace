package com.notespace.Settings;

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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import product_out.___Bundle;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsPage implements Initializable {
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
    public Button btn11;
    @FXML
    AnchorPane settingAnchor, minimizeAnchorPane;
    @FXML
    HBox modalBox, modalminiBox, singleBox, singleminiBox;
    @FXML
    VBox contentVBox;
    @FXML
    ScrollPane contentScroll;
    @FXML
    Label scrollToTopLabel;
    @FXML
    MenuItem scrollupMenuItem, scrollbotMenuItem;

    public SettingsPage() {

    }

    void Sync_Function() {
        syncSettings.setRestartOnFailure(true);
        syncSettings.setPeriod(Duration.seconds(2));

        syncSettings.setOnSucceeded(e -> {
            //		scrollSpeed ( SaveSettings.ReadSettings ().scrollSpeed );


            BindWithUI(SaveSettings.ReadSettings());

        });
    }

    public void terminateSync() {
        syncSettings.cancel();
    }

    void BindWithUI(Settings settings) {

        singleBox.getStyleClass().remove("activeBox");
        singleminiBox.getStyleClass().remove("activeBox");
        modalBox.getStyleClass().remove("activeBox");
        modalminiBox.getStyleClass().remove("activeBox");


    }

    void UI_Actions() {
        scrollToTopLabel.setOnMouseClicked(e -> {
            slowScrollToTop(contentScroll);
        });

        modalBox.setOnMouseClicked(e -> {
            modalBox.getStyleClass().add("activeBox");
            singleBox.getStyleClass().remove("activeBox");


        });
        singleBox.setOnMouseClicked(e -> {
            singleBox.getStyleClass().add("activeBox");
            modalBox.getStyleClass().remove("activeBox");


        });

        modalminiBox.setOnMouseClicked(e -> {
            modalminiBox.getStyleClass().add("activeBox");
            singleminiBox.getStyleClass().remove("activeBox");


        });
        singleminiBox.setOnMouseClicked(e -> {
            singleminiBox.getStyleClass().add("activeBox");
            modalminiBox.getStyleClass().remove("activeBox");


        });


        this.scrollupMenuItem.setOnAction(e -> {
            this.slowScrollToTop(this.contentScroll);
        });
        this.scrollbotMenuItem.setOnAction(e -> {
            this.slowScrollToBot(this.contentScroll);
        });
    }


    void init() {
        contentScroll.vvalueProperty().set(0.0);
        minimizeAnchorPane.setVisible(false);
        slowScrollToTop(contentScroll);
        //        imageView1.setImage ( new Image ( "Image/ex1.png" ) );
        //        imageView2.setImage ( new Image ( "/Image/ex2.png" ) );
        Sync_Function();
        UI_Actions();


        settingAnchor.setPrefSize(1000, 670);


        this.contentScroll.setCache(true);
        this.contentScroll.setCacheHint(CacheHint.SPEED);
        this.contentScroll.setCacheHint(CacheHint.QUALITY);
        this.contentScroll.setCacheHint(CacheHint.SCALE);


        contentScroll.vvalueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //		            fileName.setText(imageNames[(new_val.intValue() - 1)/100]);

                double d = (double) new_val;

                if (d >= 0.19) {
                    minimizeAnchorPane.setVisible(true);
                } else {
                    minimizeAnchorPane.setVisible(false);

                }

            }
        });

        //	  settingAnchor.setOnMouseEntered ( e -> {
        //		this.syncSettings.restart ( );
        //	  } );
        //	  settingAnchor.setOnMouseExited ( e -> {
        //		this.syncSettings.restart ( );
        //	  } );


    }

    private void scrollSpeed(double speedUnit) {
        contentScroll.getContent().setOnScroll(scrollEvent -> { // scrollpane speed
            double deltaY = scrollEvent.getDeltaY() * speedUnit;//0.001
            contentScroll.setVvalue(contentScroll.getVvalue() - deltaY);
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        init();
        syncSettings.restart();

        Parent parent = ___Bundle.__ViewLoader._getInstance()._load("DateSettings", new DateSettings());
        contentVBox.getChildren().add(parent);


    }
}
