package com.notespace.Minimalist.ContentContainer.FileNameFlatBox;

import com.notespace.ENUM.TimeFormat;
import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.TextObject;
import com.notespace.Font.FontRepair;
import com.notespace.Minimalist.MinimalistPage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class FileNameFlatBox implements Initializable {

    @FXML
    public HBox fileNameFlatHBox;
    @FXML
    public Label fileNameLabel, modifiedDateLabel;

    public String absolutePath;
    public TextObjectService textObjectService = new TextObjectService();

    public FileNameFlatBox() {

    }

    public FileNameFlatBox(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String returnAbsolutePath() {
        return this.absolutePath;
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

        textObjectServicesRefresh();


        addCLickedEffect();

    }

    public void addCLickedEffect() {
        if (absolutePath.equals(MinimalistPage.absoluteFilePath)) {
            fileNameFlatHBox.getStyleClass().remove("BgColor-f2f2f7");
            fileNameFlatHBox.getStyleClass().add("BgColor-f2f2f7");
        }
    }

    public void textObjectServicesRefresh() {
        textObjectService.run();
    }

    private void perform() {

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

    public boolean bagOfWords(String str) {
        String[] words = {".txt", ".ns"};
        return (Arrays.asList(words).contains(str));
    }


    public class TextObjectService extends Service<TextObject> {

        private TextObjectService() {
            run();
        }

        public void run() {
            restart();

            setOnSucceeded(e -> {
                addCLickedEffect();
                if (bagOfWords(getValue().getFileExtension())) {


                    fileNameLabel.setText(FontRepair.fixmyanamrfont(absolutePath.substring(absolutePath.lastIndexOf("\\") + 1)));


//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.FORMAT2.getFormat());
//                    String formattedCreateDate = simpleDateFormat.format(new Date(getValue().getCreationDate()));
//                    createDateLabel.setText(formattedCreateDate);
//
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(TimeFormat.FORMAT1.getFormat());
                    String formattedModifiedDate = simpleDateFormat2.format(new Date(getValue().getModifiedDate()));
                    modifiedDateLabel.setText(formattedModifiedDate);

                } else {
                    fileNameLabel.setText("Not supported extension!");
                    Arrays.stream(new Pane[]{fileNameFlatHBox}).forEach(p -> {
                        p.setDisable(true);
                    });
                    Arrays.stream(new Label[]{fileNameLabel}).forEach(label -> {
                        label.setDisable(true);
                    });
                }


            });
        }

        @Override
        protected Task<TextObject> createTask() {
            return new Task<TextObject>() {
                @Override
                protected TextObject call() throws Exception {
                    return FileHandlings.buildObject(absolutePath);
                }
            };
        }
    }
}
