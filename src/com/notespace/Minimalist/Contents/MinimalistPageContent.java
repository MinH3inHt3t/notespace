package com.notespace.Minimalist.Contents;

import com.notespace.ENUM.DateFormat;
import com.notespace.ENUM.TimeFormat;
import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.TextObject;
import com.notespace.Font.FontRepair;
import com.notespace.Minimalist.ENUM.ControlShortcut;
import com.notespace.Minimalist.Memory.ControlShortcutStorage;
import com.notespace.Minimalist.MinimalistPage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class MinimalistPageContent implements Initializable {
    @FXML
    public AnchorPane minimalistPageContentAnchorPane;
    @FXML
    public ProgressBar progressBar;

    public String absolutePath;
    public TextObjectService textObjectService = new TextObjectService();
    public ShowShortcutService showShortcutService = new ShowShortcutService();
    @FXML
    HBox shortcutHBox;
    @FXML
    Label textLabel, createDateLabel, modifiedDateLabel, shortcutLabel;
    @FXML
    TextArea textArea;


    public MinimalistPageContent() {

    }

    public MinimalistPageContent(String absolutePath) {
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
        invisible(textArea, shortcutHBox);
        textObjectServicesRefresh();

        addCLickedEffect();

    }

    public void addCLickedEffect() {
        if (absolutePath.equals(MinimalistPage.absoluteFilePath)) {
            minimalistPageContentAnchorPane.getStyleClass().remove("BgColor-f2f2f7");
            minimalistPageContentAnchorPane.getStyleClass().add("BgColor-f2f2f7");
        }
    }

    public void textObjectServicesRefresh() {
        textObjectService.run();
    }


    private void perform() {
        textLabel.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Make editable come back
                invisible(textLabel);
                visible(textArea);
                translateHeight(minimalistPageContentAnchorPane, 500.0);
            }
        });

        textArea.setOnMouseExited(e -> {
            if (absolutePath != null && textArea.getText() != null) {
                try {
                    FileHandlings.FileWrite(absolutePath, textArea.getText().trim());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        textArea.focusedProperty().addListener(new ChangeListener<Boolean>()      //lost focus
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    System.out.println("Textfield on focus");
                } else {
                    invisible(textArea);
                    visible(textLabel);
                    translateHeight(minimalistPageContentAnchorPane, 0);
                    System.out.println("Textfield out focus");
                }
            }
        });


    }

    private void visible(Node... n) {
        Arrays.stream(n).forEach(e -> {
            e.setVisible(true);
        });
    }

    private void invisible(Node... n) {
        Arrays.stream(n).forEach(e -> {
            e.setVisible(false);
        });
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
            setOnRunning(e -> {
                progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                progressBar.setVisible(true);
            });
            setOnSucceeded(e -> {
                progressBar.setProgress(0);
                progressBar.setVisible(false);
                addCLickedEffect();
                if (bagOfWords(getValue().getFileExtension())) {


                    String textData = getValue().getText().trim();
                    if (textData != null) {
                        textLabel.setText(FontRepair.fixmyanamrfont(textData));
                        textArea.setText(FontRepair.fixmyanamrfont(textData));
                    }

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.FORMAT2.getFormat());
                    String formattedCreateDate = simpleDateFormat.format(new Date(getValue().getCreationDate()));
                    createDateLabel.setText(formattedCreateDate);

                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(TimeFormat.FORMAT1.getFormat());
                    String formattedModifiedDate = simpleDateFormat2.format(new Date(getValue().getModifiedDate()));
                    modifiedDateLabel.setText(formattedModifiedDate);

                } else {
                    textLabel.setText("Not supported extension!");
                    Arrays.stream(new Pane[]{minimalistPageContentAnchorPane}).forEach(p -> {
                        p.setDisable(true);
                    });
                    Arrays.stream(new Label[]{textLabel, createDateLabel, modifiedDateLabel}).forEach(label -> {
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

    public class ShowShortcutService extends ScheduledService<ControlShortcut> {

        public ShowShortcutService() {
            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {

                if (getValue() != null) {
                    shortcutHBox.setVisible(true);
                    shortcutLabel.setText(getValue() + "");
                } else {
                    shortcutHBox.setVisible(false);
                    shortcutLabel.setText(null);
                }
            });
        }


        @Override
        protected Task<ControlShortcut> createTask() {
            return new Task<ControlShortcut>() {
                @Override
                protected ControlShortcut call() throws Exception {
                    return ControlShortcutStorage.GetControlShortcut(absolutePath);
                }
            };
        }
    }

}
