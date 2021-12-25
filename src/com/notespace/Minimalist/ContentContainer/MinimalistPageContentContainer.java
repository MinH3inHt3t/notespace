package com.notespace.Minimalist.ContentContainer;

import com.notespace.Minimalist.Contents.MinimalistPageContent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MinimalistPageContentContainer implements Initializable {
    @FXML
    public AnchorPane contentAnchorPane, noResultAnchorPane;
    @FXML
    public ScrollPane contentScrollPane;
    @FXML
    public FlowPane contentFlowPane;
    @FXML
    public Label contentModeLabel, floatModeIconLabel, windowModeIconLabel, dockModeIconLabel, contentSettingsLabel, closeContentContainerLabel, quickNoteIconLabel;
    @FXML
    public VBox contentShowCaseVBox, contentSettingsVBox;
    @FXML
    public HBox flatContentHBox, boxContentHBox, boxContentWithFileNameHBox, boxContentWithFileNameOptionsHBox, contentHBox, onecolHBox, twocolHBox, threecolHBox, swipeRightHBox, swipeLeftHBox, scrollbarHBox, floatModeHBox, windowModeHBox, dockModeHBox;
    @FXML
    public CheckBox scrollbarHBoxCheck;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Button createNewNoteButton, newNoteButton;
    @FXML
    Label scrollTopLabel, scrollBotLabel;
//    ArrayList<TextObject> textObjectArrayList = new ArrayList<>();
    //    TextObjectListService textObjectListService = new TextObjectListService();
//    TextObjectService textObjectService = new TextObjectService();

    Map<MinimalistPageContent, Parent> contentMap = new HashMap<>();

    String currentNoteSpace;

    public MinimalistPageContentContainer() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        prepare();
        perform();
    }

    private void prepare() {

    } //Prepare


    private void perform() {
        contentScrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //		            fileName.setText(imageNames[(new_val.intValue() - 1)/100]);

                double d = (double) new_val;
                if (d == 0) {
                    scrollTopLabel.setDisable(true);
                } else if (d == 1) {
                    scrollBotLabel.setDisable(true);
                } else {
                    scrollTopLabel.setDisable(false);
                    scrollBotLabel.setDisable(false);
                }
            }
        });

        scrollTopLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                slowScrollToTop(contentScrollPane);
            }
        });
        scrollBotLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                slowScrollToBot(contentScrollPane);
            }
        });


//        noResultAnchorPane.setOnMouseClicked(e->{
//            if(e.getButton().equals(MouseButton.PRIMARY)){
//                translateHeight(addNewNoteAnchorPane,20);
//            }
//        });

    }  //Perform


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
                new KeyFrame(Duration.seconds(1.5),
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


}
