package com.notespace.Minimalist.WindowTextArea;

import com.notespace.CLipBoard;
import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.TextObject;
import com.notespace.Font.FontRepair;
import com.notespace.Minimalist.Memory.EditHistoryObj;
import com.notespace.Minimalist.Memory.EditHistoryObjStorage;
import com.notespace.Minimalist.MinimalistPage;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WindowTextArea implements Initializable {
    public int undoIndex = 1, redoIndex = 1;
    @FXML
    AnchorPane windowTextAreaAnchorPane, filePathAnchorPane;
    @FXML
    TextArea textArea;
    @FXML
    VBox recentNoteContainerVBox, suggestResultVBox;
    @FXML
    TextField searchSuggestTextField;
    @FXML
    Label processingLabel;
    @FXML
    BorderPane outerBorderPane;
    @FXML
    VBox failStateVBox;
    @FXML
    HBox processingHBox, failStateHBox;
    @FXML
    SplitPane textAreaSplitPane;
    @FXML
    ProgressBar processingProgressBar;
    Clipboard systemClipboard = Clipboard.getSystemClipboard();
    Map<String, String> filterMap = new HashMap<>();
//    String absolutePath;

    TextAreaService textAreaService = new TextAreaService();
    MaxSizedContextMenu contextMenu = new MaxSizedContextMenu();
    String textData;

    public WindowTextArea(String textData) {
        this.textData = textData;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        prepare();
        perform();
    }

    private void addShortcuts() {

        textArea.setOnKeyReleased(e -> {


            if (e.getCode() == KeyCode.RIGHT && e.isControlDown()) {
                textArea.requestFocus();
                textArea.end();
            }

            if (e.getCode() == KeyCode.LEFT && e.isControlDown()) {
                textArea.requestFocus();
                textArea.positionCaret(0);
            }


        });

        Platform.runLater(() -> {
            Scene scene = windowTextAreaAnchorPane.getScene();

            scene.getAccelerators().put(        //Ctrl S
                    new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            if (MinimalistPage.absoluteFilePath != null && textArea.getText() != null) {
                                showSaveProcessing();
//                                layoutPosition.showProgressing("saving..", true, ProgressIndicator.INDETERMINATE_PROGRESS);
                                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
                                pauseTransition.setOnFinished(e -> {
//                                    layoutPosition.showProgressing("", false, 0);
                                    save();
                                    saveEditHistory();

                                    textAreaService.restart();
                                });
                                pauseTransition.play();
                                PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(0.3));
//                                pauseTransition1.setOnFinished(e -> layoutPosition.showProgressing("saved", true, 1));

                                pauseTransition1.play();
                            }
                        }
                    }
            );

        });
    }

    private void prepare() {

        outerBorderPane.setBottom(null);
        failStateVBox.setVisible(false);
//        textArea.setVisible(true);
        textAreaSplitPane.setVisible(true);

        suggest();


        addShortcuts();

        if (this.textData != null) {
            textArea.setText(this.textData);

        }

//        textAreaService.restart();

        bindRecentNoteToVBox();
        buildFilePathBox();


    }       //Prepare

    private void perform() {
        Platform.runLater(() -> {

//            final ContextMenu contextMenu = new ContextMenu();

//            contextMenu.setPrefSize(200, 300);
//            contextMenu.setMaxSize(300,400);
            contextMenu.setOnShowing(e ->
            {
                System.out.println("showing");
            });
            contextMenu.setOnShown(e ->
            {
                System.out.println("shown");
            });
//            MenuItem item1 = new MenuItem("About");
//            MenuItem item2 = new MenuItem("Preferences");

//            contextMenu.getItems().addAll(item1, item2);
            //            final TextField textField = new TextField("Type Something");

            textArea.setContextMenu(contextMenu);

            textArea.setOnContextMenuRequested(e -> {

            });


            Stage stage = (Stage) windowTextAreaAnchorPane.getScene().getWindow();
            stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {     //focus stage
//                    layoutPosition.DirectoryMissingBlock();
//                    textObjectArrayList.clear();
//                    new MinimalistPage.AllRefreshServicesHandlerBuilder().service_textObjectList().service_textAreaService().service_panFlatRefreshService().service_contentService().service_editHistoryService().build();
                    textAreaService.restart();

                } else {        //lost focus stage
//                    saveNote();
//                    new MinimalistPage.AllRefreshServicesHandlerBuilder().service_textAreaService().service_contentService().build();
//                    layoutPosition.DirectoryMissingBlock();
                    //                            saveAll();


                }
            });

            textArea.setOnKeyReleased(e -> {        //textarea shortcuts

                if (e.getCode() == KeyCode.SPACE && e.isControlDown()) {    //Ctrl Space
//                    textArea.setContextMenu(contextMenu);
//                    suggest();
                    contextMenu.getItems().clear();
                    for (Map.Entry<String, String> m : filterMap.entrySet()) {
                        String showData = String.format("%s        %s", m.getKey(), m.getValue());
                        MenuItem menuItem = new MenuItem(showData);

                        Label resultLabel = new Label(m.getKey());
                        HBox resultLabelHbox = new HBox(resultLabel);
                        resultLabelHbox.setPadding(new Insets(1, 1, 1, 10));
                        resultLabelHbox.setAlignment(Pos.CENTER_LEFT);
                        resultLabelHbox.setPrefWidth(300);

                        Label fileLabel = new Label(m.getValue());
                        fileLabel.setStyle("-fx-text-fill: #000000;" + "-fx-font-size: 11;");
                        HBox fileLabelHbox = new HBox(fileLabel);
                        fileLabelHbox.setPadding(new Insets(1, 10, 1, 1));
                        fileLabelHbox.setAlignment(Pos.CENTER_RIGHT);
                        fileLabelHbox.setPrefWidth(100);

                        HBox hBox = new HBox(resultLabelHbox, fileLabelHbox);
                        hBox.setMaxHeight(4);
//                        hBox.setAlignment(Pos.CENTER_LEFT);
//                        hBox.setSpacing(20);
                        CustomMenuItem customMenuItem = new CustomMenuItem(hBox);
                        contextMenu.getItems().add(customMenuItem);
                    }

                    contextMenu.show(textArea.getScene().getWindow());
                }

            });


            textArea.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {

                if (textArea.getText() != null) {
                    String text = textArea.getText().substring(0, newPosition.intValue());
                    System.out.println("Caret Positon " + textArea.getCaretPosition());
                    int index;

                    for (
                            index = text.length() - 1;
                            index >= 0 && !Character.isWhitespace(text.charAt(index));
                            index--
                    )
                        ;
                    String prefix = text.substring(index + 1);

                    for (
                            index = newPosition.intValue();
                            index < textArea.getLength() && !Character.isWhitespace(textArea.getText().charAt(index));
                            index++
                    )
                        ;
                    String suffix = textArea.getText().substring(newPosition.intValue(), index);

                    // replace regex wildcards (literal ".") with "\.". Looks weird but correct...
                    prefix = prefix.replaceAll("\\.", "\\.");
                    suffix = suffix.replaceAll("\\.", "\\.");

                    if (prefix != null) {
//                                System.out.println("prefix " + prefix);
                        //                    textArea.selectRange(index, index++);            //selectRangeWord

//                                System.out.println("suffix " + suffix);

                        Scene scene = windowTextAreaAnchorPane.getScene();
                        String finalPrefix = prefix;
                        int finalIndex = index;
                        scene.getAccelerators().put(
                                new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN),     //Ctrl ENTER
                                new Runnable() {
                                    @FXML
                                    public void run() {
                                        System.out.println(finalIndex);

                                    }
                                }
                        );

                        contextMenu.getItems().clear();
                        for (Map.Entry<String, String> m : filterMap.entrySet()) {
                            if (m.getKey().trim().toLowerCase().contains(finalPrefix.toLowerCase())) {
                                String showData = String.format("%s        %s", m.getKey(), m.getValue());
                                MenuItem menuItem = new MenuItem(showData);

                                Label resultLabel = new Label(m.getKey());
                                HBox resultLabelHbox = new HBox(resultLabel);
                                resultLabelHbox.setPadding(new Insets(1, 1, 1, 10));
                                resultLabelHbox.setAlignment(Pos.CENTER_LEFT);
                                resultLabelHbox.setPrefWidth(300);

                                Label fileLabel = new Label(m.getValue());
                                fileLabel.setStyle("-fx-text-fill: #000000;" + "-fx-font-size: 11;");
                                HBox fileLabelHbox = new HBox(fileLabel);
                                fileLabelHbox.setPadding(new Insets(1, 10, 1, 1));
                                fileLabelHbox.setAlignment(Pos.CENTER_RIGHT);
                                fileLabelHbox.setPrefWidth(100);

                                HBox hBox = new HBox(resultLabelHbox, fileLabelHbox);
                                hBox.setMaxHeight(4);
                                //                        hBox.setAlignment(Pos.CENTER_LEFT);
                                //                        hBox.setSpacing(20);
                                CustomMenuItem customMenuItem = new CustomMenuItem(hBox);
                                contextMenu.getItems().add(customMenuItem);
                            }

                        }
//                        suggestResultVBox.getChildren().clear();
//                        for (Map.Entry<String, String> m : filterMap.entrySet()) {
//                            if (m.getKey().trim().toLowerCase().contains(finalPrefix.toLowerCase())) {
//                                suggestResultVBox.getChildren().add(buildSuggestResultHBox(m.getKey(), m.getValue()));
//                            }
//                        }
                    }

                }

            });            //textArea events


            searchSuggestTextField.textProperty().addListener((obs, oldText, newText) -> {            //SearchBar
                // do what you need with newText here, e.g.
                if (newText.isEmpty()) {
                    //                    //			  System.out.println ( "empty" );
                    suggestResultVBox.getChildren().clear();
                    for (Map.Entry<String, String> m : filterMap.entrySet()) {
                        suggestResultVBox.getChildren().add(buildSuggestResultHBox(m.getKey(), m.getValue()));
                    }
                } else {
                    //			  System.out.println ( "not empty" );
                    suggestResultVBox.getChildren().clear();
                    for (Map.Entry<String, String> m : filterMap.entrySet()) {
                        if (m.getKey().trim().toLowerCase().contains(searchSuggestTextField.getText().trim().toLowerCase())) {
                            suggestResultVBox.getChildren().add(buildSuggestResultHBox(m.getKey(), m.getValue()));
                        }
                    }
                }
            });

            //lost focus		//textArea events
            searchSuggestTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                if (newPropertyValue) {
                    suggestResultVBox.getChildren().clear();
                    for (Map.Entry<String, String> m : filterMap.entrySet()) {
                        if (m.getKey().trim().toLowerCase().contains(searchSuggestTextField.getText().trim().toLowerCase())) {
                            suggestResultVBox.getChildren().add(buildSuggestResultHBox(m.getKey(), m.getValue()));
                        }
                    }
                } //textarea focus

            });

            searchSuggestTextField.setOnKeyReleased(e -> {            //SearchBar
                if (e.getCode() == KeyCode.ENTER) {
                    if (suggestResultVBox.getChildren().size() != 0) {
                        HBox hb = (HBox) suggestResultVBox.getChildren().get(0);
                        if (hb != null) {
                            Label resultlabel = (Label) hb.getChildren().get(1);
                            copy(resultlabel.getText().trim());
                            paste();
                            textArea.requestFocus();
                        }
                    }
                }

                if (e.getCode() == KeyCode.ESCAPE) {
                    Platform.runLater(() -> textArea.requestFocus());
                }

            });

        });
    }       //Perform


    private VBox buildControlShortcutFilePathHBox(String absolutePath, boolean labelDisable) {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] splittedFileName = new String[0];
        if (absolutePath != null) {
            splittedFileName = absolutePath.split(pattern);
        }
        HBox topHBox = new HBox();

        topHBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        //                topHBox.setPrefHeight(80);
        topHBox.setAlignment(Pos.CENTER_LEFT);
        topHBox.setPadding(new Insets(5, 10, 5, 10));

        topHBox.getStyleClass().add("topRightBottomLeftBorder");
        topHBox.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                //                        chooseNote(absolutePath);
            }
        });
        HBox labelContainerHBox = new HBox();
        labelContainerHBox.setSpacing(10);
        labelContainerHBox.setAlignment(Pos.CENTER_LEFT);
        labelContainerHBox.setPadding(new Insets(5, 5, 5, 10));

        HBox bottomHBox = new HBox();
        bottomHBox.setPrefWidth(305);
        bottomHBox.setSpacing(10);
        //                bottomHBox.setPrefHeight(40);
        bottomHBox.setAlignment(Pos.CENTER_LEFT);
        bottomHBox.setPadding(new Insets(5, 10, 5, 5));
        bottomHBox.getStyleClass().add("topRightBottomLeftBorder");

        HBox bottomIconContainerHBox = new HBox();
        bottomIconContainerHBox.setSpacing(10);
        bottomIconContainerHBox.setAlignment(Pos.CENTER_LEFT);
        bottomIconContainerHBox.setPadding(new Insets(5, 5, 5, 5));

        VBox outermostVBox = new VBox();
        outermostVBox.setAlignment(Pos.CENTER_LEFT);


        for (String splitValue : splittedFileName) {

            Label label = new Label(FontRepair.fixmyanamrfont(splitValue.trim()));
            label.setCursor(Cursor.HAND);
            label.setDisable(labelDisable);
            label.setStyle("-fx-text-fill: #9286DF ;");

            label.getStyleClass().addAll("fontPyidaungsu", "opacityHover");
            label.setOnMouseClicked(event -> {

                int arrowIndex = labelContainerHBox.getChildren().indexOf(label) + 2;
                String[] split = absolutePath.split("\\\\");
                ArrayList<String> arrayList = new ArrayList<>();
                for (int j = 0; j < arrowIndex / 2; j++) {
                    //                            System.out.print(split[j]);
                    String fp = split[j] + "\\";
                    arrayList.add(fp);
                    //                            System.out.print(fp);
                }
                String listString = arrayList.stream().map(Object::toString)
                        .collect(Collectors.joining(""));
                System.out.println(listString);

                File checkFile = new File(listString);
                if (checkFile.exists()) {
                    label.setDisable(false);
                    try {
                        Desktop.getDesktop().open(new File(listString));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    label.setDisable(true);
                }
            });
            labelContainerHBox.getChildren().add(label);
            Label arrowIconLabel = new Label("\ue5e1");
            arrowIconLabel.setCursor(Cursor.HAND);
            arrowIconLabel.setDisable(labelDisable);
            arrowIconLabel.setStyle("-fx-text-fill:  #EA4C89 ;");
            arrowIconLabel.getStyleClass().addAll("icon-fill", "font12", "opacityHover");
            arrowIconLabel.setOnMouseClicked(e -> {

                int arrowIndex = labelContainerHBox.getChildren().indexOf(arrowIconLabel) + 1;
                String[] split = absolutePath.split("\\\\");

                ArrayList<String> arrayList = new ArrayList<>();
                for (int j = 0; j < arrowIndex / 2; j++) {
                    String fp = split[j] + "\\";
                    arrayList.add(fp);
                }
                String listString = arrayList.stream().map(Object::toString)
                        .collect(Collectors.joining(""));
                System.out.println(listString);

                File checkFile = new File(listString);
                if (checkFile.exists()) {
                    arrowIconLabel.setDisable(false);
                    try {
                        Desktop.getDesktop().open(new File(listString));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    arrowIconLabel.setDisable(true);
                }
            });

            labelContainerHBox.getChildren().add(arrowIconLabel);
        }

        Label shortcutIconLabel = new Label("\ue873");
        shortcutIconLabel.setStyle("-fx-text-fill: #9286DF;");
        shortcutIconLabel.getStyleClass().addAll("icon-fill", "font12");


        HBox h = new HBox(shortcutIconLabel);
        h.setAlignment(Pos.CENTER_LEFT);
        h.setSpacing(10);

        topHBox.getChildren().addAll(h, labelContainerHBox);

        Label deleteIconLabel = new Label("\ue92e");
        deleteIconLabel.setStyle("-fx-text-fill: #EA4C89;");
        deleteIconLabel.getStyleClass().addAll("icon-fill", "font13");
        Label deleteLabel = new Label("Delete");
        deleteLabel.setStyle("-fx-text-fill: #EA4C89;");
        deleteLabel.getStyleClass().addAll("font12");
        HBox deleteIconHbox = new HBox(deleteIconLabel, deleteLabel);
        deleteIconHbox.setSpacing(5);
        deleteIconHbox.setCursor(Cursor.HAND);
        deleteIconHbox.setAlignment(Pos.CENTER_LEFT);
        deleteIconHbox.getStyleClass().add("opacityHover");
        deleteIconHbox.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (deleteLabel.getText().equals("Confirm?")) {
                    //                            deleteNote(absolutePath);
                } else {
                    deleteLabel.setText("Confirm?");
                }
            }
        });


        Label copyIconLabel = new Label("\ue14d");
        copyIconLabel.setStyle("-fx-text-fill: #9286DF;");
        copyIconLabel.getStyleClass().addAll("icon-fill", "font13");
        Label copyLabel = new Label("Copy");
        copyLabel.setStyle("-fx-text-fill: #9286DF;");
        copyLabel.getStyleClass().addAll("font12");
        HBox copyIconHbox = new HBox(copyIconLabel, copyLabel);
        copyIconHbox.setSpacing(5);
        copyIconHbox.setCursor(Cursor.HAND);
        copyIconHbox.setAlignment(Pos.CENTER_LEFT);
        copyIconHbox.getStyleClass().add("opacityHover");
        copyIconHbox.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {

                CLipBoard.SaveToCLip(textArea.getText().trim());

                copyLabel.setText("Copied");
                Platform.runLater(() -> textArea.selectAll());
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                pauseTransition.setOnFinished(e1 -> {
                            copyLabel.setText("Copy");
                            Platform.runLater(() -> textArea.deselect());
                        }
                );
                pauseTransition.play();

            }
        });


        bottomIconContainerHBox.getChildren().addAll(deleteIconHbox, copyIconHbox);
        bottomHBox.getChildren().add(bottomIconContainerHBox);
        outermostVBox.getChildren().addAll(topHBox, bottomHBox);


        //            outermostVBox.getChildren().add(bottomHBox);


        return outermostVBox;
    }

    private VBox buildRecentFilePathHBox(String absolutePath, boolean labelDisable) {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] splittedFileName = new String[0];
        if (absolutePath != null) {
            splittedFileName = absolutePath.split(pattern);
        }
        HBox topHBox = new HBox();

        topHBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        //                topHBox.setPrefHeight(80);
        topHBox.setAlignment(Pos.CENTER_LEFT);
        topHBox.setPadding(new Insets(5, 10, 5, 10));

        topHBox.getStyleClass().add("topRightBottomLeftBorder");
        topHBox.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                //                        chooseNote(absolutePath);
            }
        });
        HBox labelContainerHBox = new HBox();
//        labelContainerHBox.setSpacing(10);
        labelContainerHBox.setAlignment(Pos.CENTER_LEFT);
        labelContainerHBox.setPadding(new Insets(5, 5, 5, 10));

        HBox bottomHBox = new HBox();
        bottomHBox.setPrefWidth(305);
        bottomHBox.setSpacing(10);
        //                bottomHBox.setPrefHeight(40);
        bottomHBox.setAlignment(Pos.CENTER_LEFT);
        bottomHBox.setPadding(new Insets(5, 10, 5, 5));
        bottomHBox.getStyleClass().add("topRightBottomLeftBorder");

        HBox bottomIconContainerHBox = new HBox();
        bottomIconContainerHBox.setSpacing(10);
        bottomIconContainerHBox.setAlignment(Pos.CENTER_LEFT);
        bottomIconContainerHBox.setPadding(new Insets(5, 5, 5, 5));

        VBox outermostVBox = new VBox();
        outermostVBox.setAlignment(Pos.CENTER_LEFT);


        for (String splitValue : splittedFileName) {

            Label label = new Label(FontRepair.fixmyanamrfont(splitValue.trim()));
            label.setCursor(Cursor.HAND);
            label.setDisable(labelDisable);
            label.setStyle("-fx-text-fill: #9286DF ;");

            label.getStyleClass().addAll("fontPyidaungsu", "opacityHover");
            label.setOnMouseClicked(event -> {

                int arrowIndex = labelContainerHBox.getChildren().indexOf(label) + 2;
                String[] split = absolutePath.split("\\\\");
                ArrayList<String> arrayList = new ArrayList<>();
                for (int j = 0; j < arrowIndex / 2; j++) {
                    //                            System.out.print(split[j]);
                    String fp = split[j] + "\\";
                    arrayList.add(fp);
                    //                            System.out.print(fp);
                }
                String listString = arrayList.stream().map(Object::toString)
                        .collect(Collectors.joining(""));
                System.out.println(listString);
                try {
                    Desktop.getDesktop().open(new File(listString));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            labelContainerHBox.getChildren().add(label);
            Label arrowIconLabel = new Label("\ue5e1");
            arrowIconLabel.setCursor(Cursor.HAND);
            arrowIconLabel.setDisable(labelDisable);
            arrowIconLabel.setStyle("-fx-text-fill:  #EA4C89 ;");
            arrowIconLabel.getStyleClass().addAll("icon-fill", "font12", "opacityHover");
            arrowIconLabel.setOnMouseClicked(e -> {

                int arrowIndex = labelContainerHBox.getChildren().indexOf(arrowIconLabel) + 1;
                String[] split = absolutePath.split("\\\\");

                ArrayList<String> arrayList = new ArrayList<>();
                for (int j = 0; j < arrowIndex / 2; j++) {
                    String fp = split[j] + "\\";
                    arrayList.add(fp);
                }
                String listString = arrayList.stream().map(Object::toString)
                        .collect(Collectors.joining(""));
                System.out.println(listString);
                try {
                    Desktop.getDesktop().open(new File(listString));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            labelContainerHBox.getChildren().add(arrowIconLabel);
        }

        Label shortcutIconLabel = new Label("\ue873");
        shortcutIconLabel.setStyle("-fx-text-fill: #9286DF;");
        shortcutIconLabel.getStyleClass().addAll("icon-fill", "font12");


        HBox h = new HBox(shortcutIconLabel);
        h.setAlignment(Pos.CENTER_LEFT);
        h.setSpacing(10);

        topHBox.getChildren().addAll(h, labelContainerHBox);

        Label deleteIconLabel = new Label("\ue92e");
        deleteIconLabel.setStyle("-fx-text-fill: #EA4C89;");
        deleteIconLabel.getStyleClass().addAll("icon-fill", "font13");
        Label deleteLabel = new Label("Delete");
        deleteLabel.setStyle("-fx-text-fill: #EA4C89;");
        deleteLabel.getStyleClass().addAll("font12");
        HBox deleteIconHbox = new HBox(deleteIconLabel, deleteLabel);
        deleteIconHbox.setSpacing(5);
        deleteIconHbox.setCursor(Cursor.HAND);
        deleteIconHbox.setAlignment(Pos.CENTER_LEFT);
        deleteIconHbox.getStyleClass().add("opacityHover");
        deleteIconHbox.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (deleteLabel.getText().equals("Confirm?")) {
                    //                            deleteNote(absolutePath);
                } else {
                    deleteLabel.setText("Confirm?");
                }
            }
        });


        Label copyIconLabel = new Label("\ue14d");
        copyIconLabel.setStyle("-fx-text-fill: #9286DF;");
        copyIconLabel.getStyleClass().addAll("icon-fill", "font13");
        Label copyLabel = new Label("Copy");
        copyLabel.setStyle("-fx-text-fill: #9286DF;");
        copyLabel.getStyleClass().addAll("font12");
        HBox copyIconHbox = new HBox(copyIconLabel, copyLabel);
        copyIconHbox.setSpacing(5);
        copyIconHbox.setCursor(Cursor.HAND);
        copyIconHbox.setAlignment(Pos.CENTER_LEFT);
        copyIconHbox.getStyleClass().add("opacityHover");
        copyIconHbox.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {

                CLipBoard.SaveToCLip(textArea.getText().trim());

                copyLabel.setText("Copied");
                Platform.runLater(() -> textArea.selectAll());
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                pauseTransition.setOnFinished(e1 -> {
                            copyLabel.setText("Copy");
                            Platform.runLater(() -> textArea.deselect());
                        }
                );
                pauseTransition.play();

            }
        });


        bottomIconContainerHBox.getChildren().addAll(deleteIconHbox, copyIconHbox);
        bottomHBox.getChildren().add(bottomIconContainerHBox);
        outermostVBox.getChildren().addAll(topHBox);


        //            outermostVBox.getChildren().add(bottomHBox);


        return outermostVBox;
    }

    private void bindRecentNoteToVBox() {
        recentNoteContainerVBox.getChildren().clear();
        ArrayList<String> filePathArrayList = MinimalistPage.tempFilePaths;
        Collections.reverse(filePathArrayList);
        filePathArrayList.forEach(filePath -> {
            VBox vBox = buildRecentFilePathHBox(filePath, true);
            vBox.setCursor(Cursor.HAND);
            recentNoteContainerVBox.getChildren().add(vBox);

            if (filePath.equals(MinimalistPage.absoluteFilePath)) {
                vBox.setStyle("-fx-background-color: #f2f2f7;");
            }

            vBox.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    MinimalistPage.absoluteFilePath = filePath;
                    MinimalistPage.addTempFilePaths(filePath);


                    bindRecentNoteToVBox();
                    buildFilePathBox();
                    textAreaService.restart();
                }
            });

        });
    }

    private HBox buildSuggestResultHBox(String resultText, String fileName) {
        HBox hBox = new HBox();
        hBox.setPrefSize(146, 28);
        hBox.setPadding(new Insets(0, 0, 0, 10));
        hBox.setMinHeight(28);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
//        hBox.setStyle("-fx-background-color: #F9F8FD;");
        hBox.getStyleClass().addAll("topRightBottomLeftBorder", "C5DFFCHover", "opacityHover");

//        Label iconLabel = new Label("\ue892");
//        iconLabel.setPrefSize(28, 29);
//        iconLabel.setAlignment(Pos.CENTER);
//        iconLabel.setDisable(false);
////            iconLabel.setStyle("-fx-text-fill:  #A7B0F5;");
//        iconLabel.setStyle("-fx-text-fill: #F5CC88 ;");
//        iconLabel.getStyleClass().addAll("icon-fill", "font-17");

        Label iconLabel = new Label("w");
        iconLabel.setPrefSize(15, 15);
//        iconLabel.setPadding(new Insets(3));
        iconLabel.setAlignment(Pos.CENTER);
        iconLabel.setStyle("-fx-font-size: 11;" + "-fx-text-fill: #000000;" + "-fx-background-color: #F5CC88;" + "-fx-background-radius: 100;");
        HBox iconBox = new HBox(iconLabel);
        iconBox.setAlignment(Pos.CENTER);


        Label resultTextLabel = new Label(FontRepair.fixmyanamrfont(resultText.trim()));
        resultTextLabel.setPrefSize(251, 17);
        resultTextLabel.setAlignment(Pos.CENTER_LEFT);
//            resultTextLabel.setStyle("-fx-text-fill:  #A7B0F5;");
        resultTextLabel.setStyle("-fx-text-fill:  #000000;");
        resultTextLabel.getStyleClass().addAll("fontPyidaungsu", "font12");

        HBox fileNameLabelContainerHBox = new HBox();
        fileNameLabelContainerHBox.setPrefSize(133, 25);
        fileNameLabelContainerHBox.setPadding(new Insets(5));
        fileNameLabelContainerHBox.setAlignment(Pos.CENTER_LEFT);

        Label fileNameLabel = new Label(FontRepair.fixmyanamrfont(fileName.trim()));
        //		fileNameLabel.setPrefSize ( 198 , 17 );
        fileNameLabel.setAlignment(Pos.CENTER_RIGHT);
        fileNameLabel.getStyleClass().addAll("fontPyidaungsu", "font12");


        fileNameLabelContainerHBox.getChildren().add(fileNameLabel);

        hBox.getChildren().addAll(iconBox, resultTextLabel, fileNameLabelContainerHBox);

        hBox.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                //			  System.out.println (textArea.getCaretPosition () );
                copy(resultTextLabel.getText().trim());
                paste();

                textArea.requestFocus();
            }
        });

        hBox.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                System.out.println(textArea.getCaretPosition());
            }
        });

        return hBox;
    }

    private void buildFilePathBox() {
        VBox builedVbox = buildControlShortcutFilePathHBox(MinimalistPage.absoluteFilePath, false);
        filePathAnchorPane.getChildren().clear();
        filePathAnchorPane.getChildren().add(builedVbox);
        AnchorPane.setTopAnchor(builedVbox, 0.0);
        AnchorPane.setRightAnchor(builedVbox, 0.0);
        AnchorPane.setBottomAnchor(builedVbox, 0.0);
        AnchorPane.setLeftAnchor(builedVbox, 0.0);
    }

    public void save() {
        try {
            FileHandlings.FileWrite(MinimalistPage.absoluteFilePath, textArea.getText());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void copy(String clipBoardText) {                  //Clipboard operations
        if (clipBoardText != null) {

            ClipboardContent content = new ClipboardContent();
            content.putString(clipBoardText);
            systemClipboard.setContent(content);
        }
    }

    public void paste()                  //Clipboard operations
    {

        if (!systemClipboard.hasContent(DataFormat.PLAIN_TEXT)) {
            //       adjustForEmptyClipboard();
            return;
        }

        String clipboardText = systemClipboard.getString();

        TextArea focusedTF = textArea;
        IndexRange range = focusedTF.getSelection();

        String origText = focusedTF.getText();

        int endPos;
        String updatedText;
        String firstPart = StringUtils.substring(origText, 0, range.getStart());
        String lastPart = StringUtils.substring(origText, range.getEnd(), StringUtils.length(origText));


        updatedText = firstPart + clipboardText + lastPart;

        if (range.getStart() == range.getEnd()) {
            endPos = range.getEnd() + StringUtils.length(clipboardText);
        } else {
            endPos = range.getStart() + StringUtils.length(clipboardText);
        }

        focusedTF.setText(updatedText);
        focusedTF.positionCaret(endPos);
    }


    private void showSaveProcessing() {
        outerBorderPane.setBottom(processingHBox);
        processingLabel.setText("saved...");
        processingProgressBar.setProgress(-1);
//        processingProgressBar.setProgress(0.6);

//        PauseTransition transition1 = new PauseTransition(Duration.seconds(0.5));
//        transition1.setOnFinished(e -> {
//            processingProgressBar.setProgress(0.3);
//        });
//        transition1.play();
//
//        PauseTransition transition2 = new PauseTransition(Duration.seconds(0.8));
//        transition2.setOnFinished(e -> {
//            processingProgressBar.setProgress(0.6);
//        });
//        transition2.play();
//
//        PauseTransition transition3 = new PauseTransition(Duration.seconds(1.2));
//        transition3.setOnFinished(e -> {
//            processingProgressBar.setProgress(0.9);
//        });
//        transition3.play();


        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1.5));
        pauseTransition.setOnFinished(e -> {
            outerBorderPane.setBottom(null);
        });
        pauseTransition.play();
    }

    private void saveEditHistory() {
        boolean empty = textArea.getText().trim().isEmpty();
        if (!empty) {
            EditHistoryObjStorage.addEditHistoryObj(new EditHistoryObj(MinimalistPage.absoluteFilePath, textArea.getText().trim()));
        }
    }

    public void suggest() {

//        SplitPane.Divider divider = textAreaSplitPane.getDividers().get(0);
//        if(divider.getPosition()==1){
//            divider.setPosition(0.5);
//        }else{
//            divider.setPosition(1);
//        }


        FileHandlings.getTextObjectLists();
        Service<HashMap<String, String>> getMapService = new Service<HashMap<String, String>>() {
            @Override
            protected Task<HashMap<String, String>> createTask() {
                return new Task<HashMap<String, String>>() {
                    @Override
                    protected HashMap<String, String> call() {
                        return (HashMap<String, String>) FileHandlings.getLineWordMap();
                    }
                };
            }
        };
        getMapService.restart();
        getMapService.setOnSucceeded(e -> {
            suggestResultVBox.getChildren().clear();

            if (getMapService.getValue() != null) {
                Map<String, String> map = getMapService.getValue();

                filterMap = getMapService.getValue();
                for (Map.Entry<String, String> m : map.entrySet()) {

                    Platform.runLater(() -> suggestResultVBox.getChildren().add(buildSuggestResultHBox(m.getKey(), m.getValue())));
                }
            }


        });


    }

    class TextAreaService extends Service<TextObject> {

        public TextAreaService() {

            setOnFailed(e -> {
                failStateVBox.setVisible(true);
//                textArea.setVisible(false);
                textAreaSplitPane.setVisible(false);

                failStateHBox.getChildren().clear();
                failStateHBox.getChildren().add(buildRecentFilePathHBox(MinimalistPage.absoluteFilePath, true));
            });
            setOnSucceeded(e -> {
                if (getValue().getText() != null) {
                    failStateVBox.setVisible(false);
//                    textArea.setVisible(true);
                    textAreaSplitPane.setVisible(true);
                    textArea.setText(FontRepair.fixmyanamrfont(getValue().getText().trim()));
                }
            });
        }

        @Override
        protected Task<TextObject> createTask() {
            return new Task<TextObject>() {
                @Override
                protected TextObject call() throws Exception {
                    return FileHandlings.buildObject(MinimalistPage.absoluteFilePath);
                }
            };
        }
    }

    class MaxSizedContextMenu extends ContextMenu {
        public MaxSizedContextMenu() {
            addEventHandler(Menu.ON_SHOWING, e -> {
                Node content = getSkin().getNode();
                if (content instanceof Region) {
                    ((Region) content).setMaxHeight(400);
                    ((Region) content).setMinWidth(400);
                }
            });
        }

    }


}
