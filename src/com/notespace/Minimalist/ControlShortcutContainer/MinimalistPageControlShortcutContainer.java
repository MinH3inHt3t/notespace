package com.notespace.Minimalist.ControlShortcutContainer;

import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.NoteSpacePathStorage;
import com.notespace.FileHandler.TextObject;
import com.notespace.Font.FontRepair;
import com.notespace.Minimalist.ENUM.ControlShortcut;
import com.notespace.Minimalist.Memory.ControlShortcutStorage;
import com.notespace.Minimalist.MinimalistPage;
import javafx.animation.PauseTransition;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MinimalistPageControlShortcutContainer implements Initializable {

    @FXML
    public AnchorPane filePathAnchorPane1, filePathAnchorPane2, filePathAnchorPane3, filePathAnchorPane4, filePathAnchorPane5, filePathAnchorPane6, filePathAnchorPane7, filePathAnchorPane8, filePathAnchorPane9;
    @FXML
    public HBox filePathHBoxContainerHBox1, filePathHBoxContainerHBox2, filePathHBoxContainerHBox3, filePathHBoxContainerHBox4, filePathHBoxContainerHBox5,
            filePathHBoxContainerHBox6, filePathHBoxContainerHBox7, filePathHBoxContainerHBox8, filePathHBoxContainerHBox9;

    @FXML
    public Label addShortcutFilePathLabel1, addShortcutFilePathLabel2, addShortcutFilePathLabel3, addShortcutFilePathLabel4, addShortcutFilePathLabel5, addShortcutFilePathLabel6, addShortcutFilePathLabel7, addShortcutFilePathLabel8, addShortcutFilePathLabel9;
    @FXML
    public Label removeShortcutFilePathLabel1, removeShortcutFilePathLabel2, removeShortcutFilePathLabel3, removeShortcutFilePathLabel4, removeShortcutFilePathLabel5, removeShortcutFilePathLabel6, removeShortcutFilePathLabel7, removeShortcutFilePathLabel8, removeShortcutFilePathLabel9;

    RefreshService1 refreshService1 = new RefreshService1();
    RefreshService2 refreshService2 = new RefreshService2();
    RefreshService3 refreshService3 = new RefreshService3();
    RefreshService4 refreshService4 = new RefreshService4();
    RefreshService5 refreshService5 = new RefreshService5();
    RefreshService6 refreshService6 = new RefreshService6();
    RefreshService7 refreshService7 = new RefreshService7();
    RefreshService8 refreshService8 = new RefreshService8();
    RefreshService9 refreshService9 = new RefreshService9();

    public MinimalistPageControlShortcutContainer() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        prepare();
        perform();
    }

    public void refresh() {
        refreshService1.restart();
        refreshService2.restart();
        refreshService3.restart();
        refreshService4.restart();
        refreshService5.restart();
        refreshService6.restart();
        refreshService7.restart();
        refreshService8.restart();
        refreshService9.restart();
    }

    private void prepare() {
//        refresh();

        refresh();


    }

    private void perform() {


//        filePathHBoxContainerHBox1.setOnMouseEntered(e -> {     //enter
//            refreshService1.cancel();
//        });
//        filePathHBoxContainerHBox1.setOnMouseExited(e -> {      //exit
//            refreshService1.restart();
//        });
//        filePathHBoxContainerHBox2.setOnMouseEntered(e -> {     //enter
//            refreshService2.cancel();
//        });
//        filePathHBoxContainerHBox2.setOnMouseExited(e -> {      //exit
//            refreshService2.restart();
//        });
//        filePathHBoxContainerHBox3.setOnMouseEntered(e -> {     //enter
//            refreshService3.cancel();
//        });
//        filePathHBoxContainerHBox3.setOnMouseExited(e -> {      //exit
//            refreshService3.restart();
//        });
//        filePathHBoxContainerHBox4.setOnMouseEntered(e -> {     //enter
//            refreshService4.cancel();
//        });
//        filePathHBoxContainerHBox4.setOnMouseExited(e -> {      //exit
//            refreshService4.restart();
//        });
//        filePathHBoxContainerHBox5.setOnMouseEntered(e -> {     //enter
//            refreshService5.cancel();
//        });
//        filePathHBoxContainerHBox5.setOnMouseExited(e -> {      //exit
//            refreshService5.restart();
//        });
//        filePathHBoxContainerHBox6.setOnMouseEntered(e -> {     //enter
//            refreshService6.cancel();
//        });
//        filePathHBoxContainerHBox6.setOnMouseExited(e -> {      //exit
//            refreshService6.restart();
//        });
//        filePathHBoxContainerHBox7.setOnMouseEntered(e -> {     //enter
//            refreshService7.cancel();
//        });
//        filePathHBoxContainerHBox7.setOnMouseExited(e -> {      //exit
//            refreshService7.restart();
//        });
//        filePathHBoxContainerHBox8.setOnMouseEntered(e -> {     //enter
//            refreshService8.cancel();
//        });
//        filePathHBoxContainerHBox8.setOnMouseExited(e -> {      //exit
//            refreshService8.restart();
//        });
//        filePathHBoxContainerHBox9.setOnMouseEntered(e -> {     //enter
//            refreshService9.cancel();
//        });
//        filePathHBoxContainerHBox9.setOnMouseExited(e -> {      //exit
//            refreshService9.restart();
//        });


        removeShortcutFilePathLabel1.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ControlShortcutStorage.SaveControlShortcut(ControlShortcut.Ctrl1, null);
//                refresh();
            }
        });
        removeShortcutFilePathLabel2.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ControlShortcutStorage.SaveControlShortcut(ControlShortcut.Ctrl2, null);
//                refresh();
            }
        });
        removeShortcutFilePathLabel3.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ControlShortcutStorage.SaveControlShortcut(ControlShortcut.Ctrl3, null);
//                refresh();
            }
        });
        removeShortcutFilePathLabel4.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ControlShortcutStorage.SaveControlShortcut(ControlShortcut.Ctrl4, null);
//                refresh();
            }
        });
        removeShortcutFilePathLabel5.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ControlShortcutStorage.SaveControlShortcut(ControlShortcut.Ctrl5, null);
//                refresh();
            }
        });
        removeShortcutFilePathLabel6.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ControlShortcutStorage.SaveControlShortcut(ControlShortcut.Ctrl6, null);
//                refresh();
            }
        });
        removeShortcutFilePathLabel7.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ControlShortcutStorage.SaveControlShortcut(ControlShortcut.Ctrl7, null);
//                refresh();
            }
        });
        removeShortcutFilePathLabel8.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ControlShortcutStorage.SaveControlShortcut(ControlShortcut.Ctrl8, null);
//                refresh();
            }
        });
        removeShortcutFilePathLabel9.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                ControlShortcutStorage.SaveControlShortcut(ControlShortcut.Ctrl9, null);
//                refresh();
            }
        });


        addShortcutFilePathLabel1.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shortCutSave(ControlShortcut.Ctrl1);
            }
        });
        addShortcutFilePathLabel2.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shortCutSave(ControlShortcut.Ctrl2);
            }
        });
        addShortcutFilePathLabel3.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shortCutSave(ControlShortcut.Ctrl3);
            }
        });
        addShortcutFilePathLabel4.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shortCutSave(ControlShortcut.Ctrl4);
            }
        });
        addShortcutFilePathLabel5.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shortCutSave(ControlShortcut.Ctrl5);
            }
        });
        addShortcutFilePathLabel6.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shortCutSave(ControlShortcut.Ctrl6);
            }
        });
        addShortcutFilePathLabel7.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shortCutSave(ControlShortcut.Ctrl7);
            }
        });
        addShortcutFilePathLabel8.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shortCutSave(ControlShortcut.Ctrl8);
            }
        });
        addShortcutFilePathLabel9.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shortCutSave(ControlShortcut.Ctrl9);
            }
        });


    }

//    public void refresh() {
//
//        String filePath1 = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl1);
////        if (filePath1 != null) {
//        VBox filePathHBox1 = buildControlShortcutFilePathHBox(ControlShortcut.Ctrl1, filePath1, true);
//        filePathHBoxContainerHBox1.getChildren().add(filePathHBox1);
////        }
//
//        String filePath2 = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl2);
////        if (filePath2 != null) {
//        VBox filePathHBox2 = buildControlShortcutFilePathHBox(ControlShortcut.Ctrl2, filePath2, true);
//        filePathHBoxContainerHBox2.getChildren().add(filePathHBox2);
////        }
//
//        String filePath3 = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl3);
////        if (filePath3 != null) {
//        VBox filePathHBox3 = buildControlShortcutFilePathHBox(ControlShortcut.Ctrl3, filePath3, true);
//        filePathHBoxContainerHBox3.getChildren().add(filePathHBox3);
////        }
//        String filePath4 = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl4);
////        if (filePath4 != null) {
//        VBox filePathHBox4 = buildControlShortcutFilePathHBox(ControlShortcut.Ctrl4, filePath4, true);
//        filePathHBoxContainerHBox4.getChildren().add(filePathHBox4);
////        }
//
//        String filePath5 = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl5);
////        if (filePath5 != null) {
//        VBox filePathHBox5 = buildControlShortcutFilePathHBox(ControlShortcut.Ctrl5, filePath5, true);
//        filePathHBoxContainerHBox5.getChildren().add(filePathHBox5);
////        }
//        String filePath6 = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl6);
////        if (filePath6 != null) {
//        VBox filePathHBox6 = buildControlShortcutFilePathHBox(ControlShortcut.Ctrl6, filePath6, true);
//        filePathHBoxContainerHBox6.getChildren().add(filePathHBox6);
////        }
//        String filePath7 = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl7);
////        if (filePath7 != null) {
//        VBox filePathHBox7 = buildControlShortcutFilePathHBox(ControlShortcut.Ctrl7, filePath7, true);
//        filePathHBoxContainerHBox7.getChildren().add(filePathHBox7);
////        }
//        String filePath8 = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl8);
////        if (filePath8 != null) {
//        VBox filePathHBox8 = buildControlShortcutFilePathHBox(ControlShortcut.Ctrl8, filePath8, true);
//        filePathHBoxContainerHBox8.getChildren().add(filePathHBox8);
////        }
//        String filePath9 = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl9);
////        if (filePath9 != null) {
//        VBox filePathHBox9 = buildControlShortcutFilePathHBox(ControlShortcut.Ctrl9, filePath9, true);
//        filePathHBoxContainerHBox9.getChildren().add(filePathHBox9);
////        }
//    }

    private HBox buildFilePathHBox(String filePath, boolean labelDisable) {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] splittedFileName = filePath.split(pattern);
        HBox hBox = new HBox();
        hBox.setCursor(Cursor.HAND);
        hBox.setPrefWidth(305);
        hBox.setMinWidth(305);
        hBox.setMaxHeight(30);

        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(2);
        hBox.setPadding(new Insets(5, 10, 5, 10));
        hBox.setStyle("-fx-background-color:  #F9F8FD;" + "-fx-border-color: #D3D6D8;");
        hBox.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {


            }
        });


        for (String splitValue : splittedFileName) {

            Label label = new Label(FontRepair.fixmyanamrfont(splitValue.trim()));
            label.setDisable(labelDisable);
            label.setCursor(Cursor.HAND);
            label.setStyle("-fx-text-fill: #9286DF ;");
            label.getStyleClass().addAll("fontPyidaungsu", "opacityHover");
            label.setOnMouseClicked(event -> {

                int arrowIndex = hBox.getChildren().indexOf(label) + 2;
                String[] split = filePath.split("\\\\");
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
            hBox.getChildren().add(label);
            Label rightArrow = new Label("\ue5e1");
            rightArrow.setDisable(labelDisable);
            rightArrow.setCursor(Cursor.HAND);

            rightArrow.setStyle("-fx-text-fill:  #EA4C89 ;");
            rightArrow.getStyleClass().addAll("icon-fill", "font12", "opacityHover");
            rightArrow.setOnMouseClicked(event -> {

                int arrowIndex = hBox.getChildren().indexOf(rightArrow) + 1;
                String[] split = filePath.split("\\\\");

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

            hBox.getChildren().add(rightArrow);
        }
        return hBox;
    }

    private VBox buildControlShortcutFilePathHBox(ControlShortcut shortcut, String absolutePath, boolean labelDisable) {
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
        labelContainerHBox.setSpacing(0);
        labelContainerHBox.setAlignment(Pos.CENTER_LEFT);
        labelContainerHBox.setPadding(new Insets(5, 5, 5, 10));

        HBox bottomHBox = new HBox();
        bottomHBox.setPrefWidth(305);
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
            label.setOnMouseClicked(e -> {

                shortCutSave(shortcut);

//                int arrowIndex = labelContainerHBox.getChildren().indexOf(label) + 2;
//                String[] split = absolutePath.split("\\\\");
//                ArrayList<String> arrayList = new ArrayList<>();
//                for (int j = 0; j < arrowIndex / 2; j++) {
//                    //                            System.out.print(split[j]);
//                    String filePath = split[j] + "\\";
//                    arrayList.add(filePath);
//                    //                            System.out.print(filePath);
//                }
//                String listString = arrayList.stream().map(Object::toString)
//                        .collect(Collectors.joining(""));
//                System.out.println(listString);
//                try {
//                    Desktop.getDesktop().open(new File(listString));
//                } catch (IOException ioException) {
//                    ioException.printStackTrace();
//                }


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

                    String filePath = split[j] + "\\";
                    arrayList.add(filePath);

                }

                String listString = arrayList.stream().map(Object::toString)
                        .collect(Collectors.joining(""));
                System.out.println(listString);


                shortCutSave(shortcut);
            });

            labelContainerHBox.getChildren().add(arrowIconLabel);
        }

        Label shortcutIconLabel = new Label("\uEF56");
        shortcutIconLabel.setStyle("-fx-text-fill: #9286DF;");
        shortcutIconLabel.getStyleClass().addAll("icon-fill", "font12");

        Label shortcutLabel = new Label(shortcut + "");
        shortcutLabel.setCursor(Cursor.HAND);
        shortcutLabel.setTooltip(new Tooltip(shortcut + " " + absolutePath));
        shortcutLabel.setStyle("-fx-text-fill: #9286DF ;");
        shortcutLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                shortCutSave(shortcut);
            }
        });

        HBox h = new HBox(shortcutIconLabel, shortcutLabel);
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

                try {
                    TextObject to = FileHandlings.buildObject(absolutePath);
//                            layoutPosition.copy(to.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                copyLabel.setText("Copied");
//                        Platform.runLater(() -> textArea.selectAll());
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                pauseTransition.setOnFinished(e1 -> {
                            copyLabel.setText("Copy");
//                                    textArea.deselect();
                        }
                );
                pauseTransition.play();

            }
        });


        bottomIconContainerHBox.getChildren().addAll(deleteIconHbox, copyIconHbox);
        bottomHBox.getChildren().add(bottomIconContainerHBox);
        outermostVBox.getChildren().add(topHBox);


        //            outermostVBox.getChildren().add(bottomHBox);


        return outermostVBox;
    }

    private void shortCutSave(ControlShortcut shortcut) {

        FileChooser fileChooser = new FileChooser();

        File f = new File(NoteSpacePathStorage.getNoteSpacePathObj().spacePath);
        if (f.exists()) {

            fileChooser.setInitialDirectory(f);

            fileChooser.setTitle("Choose Text File");
            fileChooser.getExtensionFilters().addAll(

                    new FileChooser.ExtensionFilter("TEXT", "*.txt")

            );
            //            fileChooser.showOpenDialog(new Stage());
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                ControlShortcutStorage.SaveControlShortcut(shortcut, file.getAbsolutePath());
            }
        }
    }


    private void selectEffect(AnchorPane targetPane, String filePath) {
        if (filePath != null && MinimalistPage.absoluteFilePath != null && filePath.equals(MinimalistPage.absoluteFilePath)) {      //check selected path
            targetPane.setStyle("-fx-background-color: #f2f2f7;");
        } else {
            targetPane.setStyle("-fx-background-color:  #F9F8FD;");
        }
    }

    private void serviceSucceed(ControlShortcut shortcut, String filePath, HBox filePathContainer, AnchorPane filePathAnchorPane) {
//        String filePath = ControlShortcutStorage.GetFilePath(shortcut);

        VBox filePathVBox = buildControlShortcutFilePathHBox(shortcut, filePath, true);
        filePathContainer.getChildren().clear();
        filePathContainer.getChildren().add(filePathVBox);

        selectEffect(filePathAnchorPane, filePath);

        filePathAnchorPane.setUserData(filePath);
    }

    class RefreshService1 extends ScheduledService<String> {


        public RefreshService1() {
//            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {

                serviceSucceed(ControlShortcut.Ctrl1, getValue(), filePathHBoxContainerHBox1, filePathAnchorPane1);

            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl1);
                }
            };
        }

    }

    class RefreshService2 extends ScheduledService<String> {

        String temp;

        public RefreshService2() {
//            restart();

            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {
                temp = getValue();
//                if (!temp.equals(getValue())) {
                serviceSucceed(ControlShortcut.Ctrl2, getValue(), filePathHBoxContainerHBox2, filePathAnchorPane2);

//                }
            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl2);
                }
            };
        }

    }

    class RefreshService3 extends ScheduledService<String> {
        String temp;

        public RefreshService3() {
//            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {
                temp = getValue();

//                if (!temp.equals(getValue())) {

                serviceSucceed(ControlShortcut.Ctrl3, getValue(), filePathHBoxContainerHBox3, filePathAnchorPane3);
//                }
            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl3);
                }
            };
        }

    }

    class RefreshService4 extends ScheduledService<String> {
        String temp;

        public RefreshService4() {
//            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {
                temp = getValue();

//                if (!temp.equals(getValue())) {
                serviceSucceed(ControlShortcut.Ctrl4, getValue(), filePathHBoxContainerHBox4, filePathAnchorPane4);

//                }
            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl4);
                }
            };
        }

    }

    class RefreshService5 extends ScheduledService<String> {

        public RefreshService5() {
//            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {
                serviceSucceed(ControlShortcut.Ctrl5, getValue(), filePathHBoxContainerHBox5, filePathAnchorPane5);
            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl5);
                }
            };
        }

    }

    class RefreshService6 extends ScheduledService<String> {

        public RefreshService6() {
//            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {
                serviceSucceed(ControlShortcut.Ctrl6, getValue(), filePathHBoxContainerHBox6, filePathAnchorPane6);

            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl6);
                }
            };
        }

    }

    class RefreshService7 extends ScheduledService<String> {

        public RefreshService7() {
//            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {
                serviceSucceed(ControlShortcut.Ctrl7, getValue(), filePathHBoxContainerHBox7, filePathAnchorPane7);

            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl7);
                }
            };
        }

    }

    class RefreshService8 extends ScheduledService<String> {

        public RefreshService8() {
//            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {
                serviceSucceed(ControlShortcut.Ctrl8, getValue(), filePathHBoxContainerHBox8, filePathAnchorPane8);

            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl8);
                }
            };
        }

    }

    class RefreshService9 extends ScheduledService<String> {

        public RefreshService9() {
//            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {
                serviceSucceed(ControlShortcut.Ctrl9, getValue(), filePathHBoxContainerHBox9, filePathAnchorPane9);
            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl9);
                }
            };
        }

    }


}

