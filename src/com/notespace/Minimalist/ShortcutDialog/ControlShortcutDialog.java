package com.notespace.Minimalist.ShortcutDialog;

import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.TextObject;
import com.notespace.Font.FontRepair;
import com.notespace.Minimalist.ENUM.ControlShortcut;
import com.notespace.Minimalist.Memory.ControlShortcutStorage;
import com.notespace.Minimalist.MinimalistPage;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ControlShortcutDialog implements Initializable {

    @FXML
    public BorderPane shortcutBorderPane;
    ControlShortcut currentControlShortcut;
    @FXML
    AnchorPane anchorPane;
    @FXML
    HBox selectedFilePathHBox;
    @FXML
    HBox controlShortcutHBox1, controlShortcutHBox2, controlShortcutHBox3, controlShortcutHBox4, controlShortcutHBox5, controlShortcutHBox6, controlShortcutHBox7, controlShortcutHBox8, controlShortcutHBox9;
    @FXML
    Label filePathLabel1, filePathLabel2, filePathLabel3, filePathLabel4, filePathLabel5, filePathLabel6, filePathLabel7, filePathLabel8, filePathLabel9;
    @FXML
    Label filePathInsideLabel;
    @FXML
    TextField filePathTextField1, filePathTextField2, filePathTextField3, filePathTextField4, filePathTextField5, filePathTextField6, filePathTextField7, filePathTextField8, filePathTextField9;
    @FXML
    Button chooseFilePathButton, removeFilePathButton, saveFilePathButton;
    @FXML
    Label shortcutLabel;

    WatchCurrentControlShortcutServices watcherService = new WatchCurrentControlShortcutServices();

    ControlShortcut1Watcher controlShortcut1Watcher = new ControlShortcut1Watcher();
    ControlShortcut2Watcher controlShortcut2Watcher = new ControlShortcut2Watcher();
    ControlShortcut3Watcher controlShortcut3Watcher = new ControlShortcut3Watcher();
    ControlShortcut4Watcher controlShortcut4Watcher = new ControlShortcut4Watcher();
    ControlShortcut5Watcher controlShortcut5Watcher = new ControlShortcut5Watcher();
    ControlShortcut6Watcher controlShortcut6Watcher = new ControlShortcut6Watcher();
    ControlShortcut7Watcher controlShortcut7Watcher = new ControlShortcut7Watcher();
    ControlShortcut8Watcher controlShortcut8Watcher = new ControlShortcut8Watcher();
    ControlShortcut9Watcher controlShortcut9Watcher = new ControlShortcut9Watcher();


    public ControlShortcutDialog() {

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


        currentControlShortcut = ControlShortcut.Ctrl1;
        setClickEffect(controlShortcutHBox1);

//        watcherService1.restart();

        watchServicesRestart();

        Arrays.stream(new Node[]{filePathTextField1, filePathTextField2, filePathTextField3, filePathTextField4, filePathTextField5, filePathTextField6, filePathTextField7, filePathTextField8, filePathTextField9}).forEach(node -> {
            node.setVisible(false);
        });


    }       //Prepare

    private void watchServicesRestart() {
        watcherService.restart();

        controlShortcut1Watcher.restart();
        controlShortcut2Watcher.restart();
        controlShortcut3Watcher.restart();
        controlShortcut4Watcher.restart();
        controlShortcut5Watcher.restart();
        controlShortcut6Watcher.restart();
        controlShortcut7Watcher.restart();
        controlShortcut8Watcher.restart();
        controlShortcut9Watcher.restart();

    }

    private void watchServicesStop() {
        watcherService.cancel();

        controlShortcut1Watcher.cancel();
        controlShortcut2Watcher.cancel();
        controlShortcut3Watcher.cancel();
        controlShortcut4Watcher.cancel();
        controlShortcut5Watcher.cancel();
        controlShortcut6Watcher.cancel();
        controlShortcut7Watcher.cancel();
        controlShortcut8Watcher.cancel();
        controlShortcut9Watcher.cancel();

    }

    private void changeCurrentControlShortcut(ControlShortcut controlShortcut) {
        currentControlShortcut = controlShortcut;
    }

    private void setClickEffect(HBox targetHBox) {
        removeClickEffect();
        fadeUp(anchorPane);
        targetHBox.getStyleClass().add("BgColor-f2f2f7");
    }

    private void removeClickEffect() {
        Arrays.stream(new HBox[]{controlShortcutHBox1, controlShortcutHBox2, controlShortcutHBox3, controlShortcutHBox4, controlShortcutHBox5, controlShortcutHBox6, controlShortcutHBox7, controlShortcutHBox8, controlShortcutHBox9}).forEach(hbox -> {
            hbox.getStyleClass().remove("BgColor-f2f2f7");

        });
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

    private void perform() {

//        shortcutBorderPane.setOnMouseEntered(e -> {
//            watchServicesRestart();
//        });
//        shortcutBorderPane.setOnMouseExited(e -> {
//            watchServicesStop();
//        });

        controlShortcutHBox1.setOnMouseClicked(e -> {
            setClickEffect(controlShortcutHBox1);
            changeCurrentControlShortcut(ControlShortcut.Ctrl1);
        });
        controlShortcutHBox2.setOnMouseClicked(e -> {
            setClickEffect(controlShortcutHBox2);
            changeCurrentControlShortcut(ControlShortcut.Ctrl2);
        });
        controlShortcutHBox3.setOnMouseClicked(e -> {
            setClickEffect(controlShortcutHBox3);
            changeCurrentControlShortcut(ControlShortcut.Ctrl3);
        });
        controlShortcutHBox4.setOnMouseClicked(e -> {
            setClickEffect(controlShortcutHBox4);
            changeCurrentControlShortcut(ControlShortcut.Ctrl4);
        });
        controlShortcutHBox5.setOnMouseClicked(e -> {
            setClickEffect(controlShortcutHBox5);
            changeCurrentControlShortcut(ControlShortcut.Ctrl5);
        });
        controlShortcutHBox6.setOnMouseClicked(e -> {
            setClickEffect(controlShortcutHBox6);
            changeCurrentControlShortcut(ControlShortcut.Ctrl6);
        });
        controlShortcutHBox7.setOnMouseClicked(e -> {
            setClickEffect(controlShortcutHBox7);
            changeCurrentControlShortcut(ControlShortcut.Ctrl7);
        });
        controlShortcutHBox8.setOnMouseClicked(e -> {
            setClickEffect(controlShortcutHBox8);
            changeCurrentControlShortcut(ControlShortcut.Ctrl8);
        });
        controlShortcutHBox9.setOnMouseClicked(e -> {
            setClickEffect(controlShortcutHBox9);
            changeCurrentControlShortcut(ControlShortcut.Ctrl9);
        });


        chooseFilePathButton.setOnAction(e -> {
            String filePath = ControlShortcutStorage.GetFilePath(currentControlShortcut);
            System.out.println("File Path " + filePath);
            shortCutSave(currentControlShortcut, filePath);
        });

        removeFilePathButton.setOnAction(e -> {
            ControlShortcutStorage.SaveControlShortcut(currentControlShortcut, null);
        });

        filePathLabel1.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                filePathLabel1.setVisible(false);
                filePathTextField1.setVisible(true);
//                filePathTextField1.setText(FontRepair.fixmyanamrfont(filePathLabel1.getText().trim()));

                watchServicesStop();
            }
        });

        filePathTextField1.setOnMouseExited(e -> {
            filePathLabel1.setVisible(true);
            filePathTextField1.setVisible(false);

            watchServicesRestart();
        });

        filePathTextField1.focusedProperty().addListener((ods, oldValue, newValue) -> {
            if (newValue) {      //focus lost

                watchServicesStop();
            }
        });

        filePathTextField1.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                filePathLabel1.setVisible(true);
                filePathTextField1.setVisible(false);

                File file = new File(filePathTextField1.getText().trim());
                if (file != null) {

                    ControlShortcutStorage.SaveControlShortcut(ControlShortcut.Ctrl1, file.getAbsolutePath());
                }

                watchServicesRestart();
            }
        });


        saveFilePathButton.setOnAction(e -> {
            ControlShortcutStorage.SaveControlShortcut(currentControlShortcut, MinimalistPage.absoluteFilePath);
        });

    }       //Perform

    private void shortCutSave(ControlShortcut shortcut, String filePath) {
        FileChooser fileChooser = new FileChooser();
        System.out.println(filePath);
        fileChooser.setTitle("Choose Text File");
        fileChooser.getExtensionFilters().addAll(

                new FileChooser.ExtensionFilter("TEXT", "*.txt")

        );
        //            fileChooser.showOpenDialog(new Stage());

        if (filePath != null) {
            File f = new File(filePath);
            if (f.exists()) {
                File existDirectory = new File(f.getParent());
                fileChooser.setInitialDirectory(existDirectory);
            }
        }
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            ControlShortcutStorage.SaveControlShortcut(shortcut, file.getAbsolutePath());
        }

    }

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

            });

            labelContainerHBox.getChildren().add(arrowIconLabel);
        }

        Label shortcutIconLabel = new Label("\uEF56");
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

                try {
                    TextObject to = FileHandlings.buildObject(absolutePath);
//                            layoutPosition.copy(to.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                copyLabel.setText("Copied");
//                        Platform.runLater(() -> textArea.selectAll());
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
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


    class WatchCurrentControlShortcutServices extends ScheduledService<String> {

        public WatchCurrentControlShortcutServices() {
//            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {
                shortcutLabel.setText(currentControlShortcut + "");
                if (getValue() != null) {
                    filePathInsideLabel.setText(FontRepair.fixmyanamrfont(String.format(" '%s' ", getValue())));
                } else {
                    filePathInsideLabel.setText(null);
                }

                saveFilePathButton.setText(String.format("save this path as %s", currentControlShortcut));

                selectedFilePathHBox.getChildren().clear();
                selectedFilePathHBox.getChildren().add(buildControlShortcutFilePathHBox(MinimalistPage.absoluteFilePath, true));

            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return ControlShortcutStorage.GetFilePath(currentControlShortcut);
                }
            };
        }
    }

    class ControlShortcut1Watcher extends ScheduledService<String> {

        public ControlShortcut1Watcher() {
            setPeriod(Duration.seconds(1));

            setOnSucceeded(e -> {
                if (getValue() != null) {
                    filePathLabel1.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                    filePathTextField1.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                } else {
                    filePathLabel1.setText(null);
                    filePathTextField2.setText(null);

                }
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

    class ControlShortcut2Watcher extends ScheduledService<String> {

        public ControlShortcut2Watcher() {
            setPeriod(Duration.seconds(1));

            setOnSucceeded(e -> {
                if (getValue() != null) {
                    filePathLabel2.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                    filePathTextField2.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                } else {
                    filePathLabel2.setText(null);
                    filePathTextField2.setText(null);
                }
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

    class ControlShortcut3Watcher extends ScheduledService<String> {

        public ControlShortcut3Watcher() {
            setPeriod(Duration.seconds(1));

            setOnSucceeded(e -> {
                if (getValue() != null) {
                    filePathLabel3.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                    filePathTextField3.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                } else {
                    filePathLabel3.setText(null);
                    filePathTextField3.setText(null);
                }
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

    class ControlShortcut4Watcher extends ScheduledService<String> {

        public ControlShortcut4Watcher() {
            setPeriod(Duration.seconds(1));

            setOnSucceeded(e -> {
                if (getValue() != null) {
                    filePathLabel4.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                    filePathTextField4.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                } else {
                    filePathLabel4.setText(null);
                    filePathTextField4.setText(null);
                }
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

    class ControlShortcut5Watcher extends ScheduledService<String> {

        public ControlShortcut5Watcher() {
            setPeriod(Duration.seconds(1));

            setOnSucceeded(e -> {
                if (getValue() != null) {
                    filePathLabel5.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                    filePathTextField5.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                } else {
                    filePathLabel5.setText(null);
                    filePathTextField5.setText(null);
                }
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

    class ControlShortcut6Watcher extends ScheduledService<String> {

        public ControlShortcut6Watcher() {
            setPeriod(Duration.seconds(1));

            setOnSucceeded(e -> {
                if (getValue() != null) {
                    filePathLabel6.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                    filePathTextField6.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                } else {
                    filePathLabel6.setText(null);
                    filePathTextField6.setText(null);
                }
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

    class ControlShortcut7Watcher extends ScheduledService<String> {

        public ControlShortcut7Watcher() {
            setPeriod(Duration.seconds(1));

            setOnSucceeded(e -> {
                if (getValue() != null) {
                    filePathLabel7.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                    filePathTextField7.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                } else {
                    filePathLabel7.setText(null);
                    filePathTextField7.setText(null);
                }
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

    class ControlShortcut8Watcher extends ScheduledService<String> {

        public ControlShortcut8Watcher() {
            setPeriod(Duration.seconds(1));

            setOnSucceeded(e -> {
                if (getValue() != null) {
                    filePathLabel8.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                    filePathTextField8.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                } else {
                    filePathLabel8.setText(null);
                    filePathTextField8.setText(null);
                }
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

    class ControlShortcut9Watcher extends ScheduledService<String> {

        public ControlShortcut9Watcher() {
            setPeriod(Duration.seconds(1));
            setOnSucceeded(e -> {
                if (getValue() != null) {
                    filePathLabel9.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                    filePathTextField9.setText(FontRepair.fixmyanamrfont(getValue().trim()));
                } else {
                    filePathLabel9.setText(null);
                    filePathTextField9.setText(null);
                }
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
