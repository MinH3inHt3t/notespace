package com.notespace.Minimalist;

import com.notespace.FileHandler.*;
import com.notespace.Font.FontRepair;
import com.notespace.Minimalist.Backup.BackupContainer;
import com.notespace.Minimalist.ContentContainer.FileNameFlatBox.FileNameFlatBox;
import com.notespace.Minimalist.ContentContainer.MinimalistPageContentContainer;
import com.notespace.Minimalist.Contents.MinimalistPageContent;
import com.notespace.Minimalist.ControlShortcutContainer.MinimalistPageControlShortcutContainer;
import com.notespace.Minimalist.DirectoryChoose.MinimalistPageDirectoryChoosePanFlat;
import com.notespace.Minimalist.ENUM.ContentMode;
import com.notespace.Minimalist.ENUM.ControlShortcut;
import com.notespace.Minimalist.ENUM.MatchCaseMode;
import com.notespace.Minimalist.EditHistory.EditHistoryContent;
import com.notespace.Minimalist.FileNameDialog.FileNameDialog;
import com.notespace.Minimalist.FileNameDialog.FileNameDialogWithText.FileNameDialogWithText;
import com.notespace.Minimalist.Logs.LogsContainer.LogsContainer;
import com.notespace.Minimalist.MatchCase.MinimalistPageLineCase;
import com.notespace.Minimalist.MatchCase.MinimalistPageMatchCaseHighlight;
import com.notespace.Minimalist.Memory.*;
import com.notespace.Minimalist.SearchBar.MinimalistPageSearchBar;
import com.notespace.Minimalist.Settings.MinimalistPageSettings;
import com.notespace.Minimalist.Settings.Shortcuts_Section;
import com.notespace.Minimalist.ShortcutDialog.ControlShortcutDialog;
import com.notespace.Minimalist.WindowTextArea.WindowTextArea;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;
import product_out.___Bundle;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.text.BreakIterator;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MinimalistPage implements Initializable {
    public static boolean textAreaCaretPermit = true;
    public static String absoluteFilePath;
    public static ArrayList<String> tempFilePaths = new ArrayList<>();
    public int absoluteFilePathPositionIndex = -1;
    public int undoIndex = 1, redoIndex = 1;
    @FXML
    ScrollPane homePageScrollPane, recentNoteResultScrollPane, recentNoteResultScrollPane1, textAreaSettingsScrollPane, editHistoryContentScrollPane;
    @FXML
    Label closeTextAreLabel, textAreaSettingsLabel, ctrlEnterLabel, currentFilePathLabel, duplicateWarningLabel, createNewFileLabel, shortcutDialogIconLabel, shortcutLabel;
    @FXML
    VBox textAreaSettingsVBox, suggestVBox, suggestResultVBox, notifyChooseNoteVBox;
    @FXML
    HBox suggestDragHandlerHBox, editHistoryDraghandlerHBox, recentNoteResultHBox, recentNoteResultHBox1;
    @FXML
    AnchorPane filePathHBoxAnchorPane, editHistoryAnchorPane, controlShortcutValueSpecifyAnchorPane;
    @FXML
    Label suggestRefreshLabel, suggestContainerCloseLabel;
    @FXML
    Label goHomeLabel, notifyChooseNotePathLabel, controlShortcutValueSpecifyLabel;
    @FXML
    Button folderLocationBtn, logsPaneBtn, backupPaneBtn;
    @FXML
    SplitPane dockContainerSplitPane;
    @FXML
    VBox logsResultVBox, backupResultVBox, editHistoryContentVBox;
    @FXML
    Label initialPreparingLabel;
    @FXML
    ProgressBar initialPreparingProgressBar;

    LogsService logsService = new LogsService();
    BackupService backupService = new BackupService();
    EditHistoryService editHistoryService = new EditHistoryService();

    ControlShortcutWatcherService controlShortcutWatcherService = new ControlShortcutWatcherService();


    int cursorX = 0;
    int cursorY = 0;
    @FXML
    HBox shortcutHBox;
    @FXML
    HBox textAreaNewWindowHBox, copyClipboardHBox, suggestHBox, lineCaseHBox, matchCaseHBox, forceToSaveHBox, deleteHBox, notePadHBox, recentlyOpenedHBox, editHistoryHBox, nextNoteHBox, controlShortcutsHBox, previousNoteHBox, undoHBox, redoHBox;
    @FXML
    Label copyClipboardHBoxLabel, deleteHBoxInsideLabel, closeEditHistoryAnchorPaneLabel;
    @FXML
    AnchorPane minimalistPageAnchorPane, imageViewAnchorPane;


    boolean selectRangeAvailable = false;
    @FXML
    Label directoryChooseLabel, allUIRefreshLabel, redirectHomePageLabel, deleteAllLogsLabel;
    @FXML
    TextField directoryNameTextField, searchSuggestWordTextField, fileNameTextField, backupSearchTextField;
    @FXML
    TextArea textArea;


    @FXML
    AnchorPane settingsAnchorPane, textAreaContainerAnchorPane, homeAnchorPane, homePageAnchorPane, directoryChooseAnchorPane, logsAnchorPane, backupAnchorPane, fileNotExistAnchorPane;
    @FXML
    BorderPane innerBorderPane, outerBorderPane, homePageBorderPane, textAreaBorderPane, textAreaContainerBorderPane;
    @FXML
    HBox progressBarHBox;
    @FXML
    Label progressLabel;
    @FXML
    ProgressBar progressBar;


    @FXML
    ImageView imageView;
    @FXML
    VBox floatContentVBox, floatSearchBarVBox;
    @FXML
    AnchorPane lineMatchCaseAnchorPane, floatMatchCaseAnchorPane;
    @FXML
    Button openHomeButton, newNoteButton, openContentContainerButton, openSearchBarButton, openControlShortcutContainerButton, openSettingsButton, openNoteFromDirButton, openBackupButton, openDirectoryButton, openLogsButton;
    LayoutPosition layoutPosition = new LayoutPosition();

    MinimalistPageContentContainer minimalistPageContentContainer = new MinimalistPageContentContainer();       //UI
    ContentContainer contentContainer = new ContentContainer();     //UI
    MinimalistPageSearchBar minimalistPageSearchBar = new MinimalistPageSearchBar();        //UI
    SearchBar searchBar = new SearchBar();      //UI
    SuggestContainer suggestContainer = new SuggestContainer();     //UI
    MinimalistPageMatchCaseHighlight minimalistPageMatchCaseHighlight = new MinimalistPageMatchCaseHighlight();     //UI
    MatchCaseContainer matchCaseContainer = new MatchCaseContainer();       //UI

    MinimalistPageLineCase minimalistPageLineCase = new MinimalistPageLineCase();      //UI
    LineCaseContainer lineCaseContainer = new LineCaseContainer();      //UI


    MinimalistPageSettings minimalistPageSettings = new MinimalistPageSettings();       //UI
    MinimalistSettings minimalistSettings = new MinimalistSettings();       //UI

    HomePageControlClass homePageControlClass = new HomePageControlClass();     //UI
    MinimalistPageDirectoryChoosePanFlat minimalistPageDirectoryChoosePanFlat = new MinimalistPageDirectoryChoosePanFlat();     //UI

    ControlShortcutContainer controlShortcutContainer = new ControlShortcutContainer();        //UI
    MinimalistPageControlShortcutContainer minimalistPageControlShortcutContainer = new MinimalistPageControlShortcutContainer();       //UI

    DirectoryChoosePanFlat directoryChoosePanFlat = new DirectoryChoosePanFlat();   //UI


    TextObjectListService textObjectListService = new TextObjectListService();
    TextAreaService textAreaRefreshService = new TextAreaService();


    ArrayList<TextObject> textObjectArrayList = new ArrayList<>();
    ArrayList<TextObject> searchLists = new ArrayList<>();
    ArrayList<String> dictionaryArrayList = new ArrayList<>();
    ArrayList<Backup> backupArrayList = new ArrayList<>();
    String currentNoteSpace;
    Map<MinimalistPageContent, Parent> contentMap = new HashMap<>();
    Map<FileNameFlatBox, Parent> contentFileNameFLatMap = new HashMap<>();
    ArrayList<Label> matchCaseArrayList = new ArrayList<>();
    ArrayList<HBox> lineCaseArrayList = new ArrayList<>();
    Clipboard systemClipboard = Clipboard.getSystemClipboard();


    ToggleGroup toggleGroup = new ToggleGroup();
    int undIndx = 0;

    public MinimalistPage() {

    }

    public static void addTempFilePaths(String filePath) {


        if (!tempFilePaths.contains(filePath)) {
        } else {
            tempFilePaths.removeIf(b -> b.equals(filePath));
        }
        tempFilePaths.add(filePath);


    }

    public void removeEffect() {
        for (Map.Entry<MinimalistPageContent, Parent> entry : contentMap.entrySet()) {
            entry.getKey().minimalistPageContentAnchorPane.getStyleClass().remove("BgColor-f2f2f7");
        }

        for (Map.Entry<FileNameFlatBox, Parent> entry : contentFileNameFLatMap.entrySet()) {
            entry.getKey().fileNameFlatHBox.getStyleClass().remove("BgColor-f2f2f7");
        }


    }

    @FXML
    public void moveSearchBarRight() {

    }

    @FXML
    public void openSettings() {
        layoutPosition.openSettings();

    }

    @FXML
    public void openHome() {
        layoutPosition.openHome();
    }

    @FXML
    public void openContentContainer() {
        contentContainer.dockContainer();
    }

    @FXML
    public void openSearchBar() {
        searchBar.dockSearchBar();
    }

    @FXML
    public void openRecentlyInnerBorder() {
        layoutPosition.openRecentlyInnerBorder();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        prepare();
        perform();
    }

    private void fakeLoading() {


        PauseTransition transition1 = new PauseTransition(Duration.seconds(5));
        transition1.setOnFinished(e -> {
            initialPreparingLabel.setText("preparing...");
            initialPreparingProgressBar.setProgress(0.3);
        });
        transition1.play();

        PauseTransition transition2 = new PauseTransition(Duration.seconds(3.5));
        transition2.setOnFinished(e -> {
            initialPreparingLabel.setText("loading dictionary...");
            initialPreparingProgressBar.setProgress(0.6);
        });
        transition2.play();

        PauseTransition transition3 = new PauseTransition(Duration.seconds(4));
        transition3.setOnFinished(e -> {
            initialPreparingLabel.setText("finished...");
            initialPreparingProgressBar.setProgress(0.9);
        });
        transition3.play();

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(e -> {
            initialPreparingLabel.setText(null);
            initialPreparingProgressBar.setProgress(1);

            outerBorderPane.setBottom(null);
        });
        pauseTransition.play();

//        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
//               pauseTransition.setOnFinished(e -> {
//                   outerBorderPane.setBottom(null);
//               });
//               pauseTransition.play();
    }

    private void prepare() {

        innerBorderPane.setBottom(null);


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fakeLoading();
            }
        });

        duplicateWarningLabel.setVisible(false);
        notifyChooseNoteVBox.setVisible(false);
        controlShortcutValueSpecifyAnchorPane.setVisible(false);

        layoutPosition.showProgressing("", false, 0);
        new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_logsService(true).service_editHistoryService(true).build();

//        controlShortcutWatcherService.restart();        //watcher push start

        layoutPosition.openHome();


        dictionaryArrayList = FileHandlings.getDictionaryList();        //load dictionary

        matchCaseContainer.floatMatchCaseContainer();
        lineCaseContainer.floatLineCaseContainer();
        layoutPosition.suggestVBoxVisibleProperty(false);
        layoutPosition.matchCaseVBoxVisibleProperty(false);
        layoutPosition.lineCaseVBoxVisibleProperty(false);
        layoutPosition.editHistoryBoxVisibleProperty(false);

        AddShortcuts();


        layoutPosition.scrollBarVisibleProperty(true);      //Read from settings
        layoutPosition.columnAdjustSize(1);
        layoutPosition.adjustMediaViewSize();
        layoutPosition.contentShowCaseVBoxVisibleProperty(false);
        layoutPosition.contentSettingsVBoxVisibleProperty(false);      //Read from settings

//        layoutPosition.textAreaSettingsVBoxVisibleProperty( );

        matchCaseContainer.mode = MatchCaseMode.WORDS;
        contentContainer.contentMode = ContentMode.Box;


//        contentContainer.dockContainer();
        controlShortcutContainer.dockControlShortcutContainer();

        searchBar.dockSearchBar();
//        searchBar.hideSearchBar();


    }      //Prepare

    private void AddShortcuts() {       //Keyboard shortcuts

        Platform.runLater(() -> {
            Scene scene = minimalistPageAnchorPane.getScene();

            scene.getAccelerators().put(        //Ctrl N
                    new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN),
                    () -> layoutPosition.showFileNameDialog()

            );

            scene.getAccelerators().put(        //Ctrl Q
                    new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN),
                    () -> quickNote()

            );

            scene.getAccelerators().put(        //Ctrl Shift N
                    new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> layoutPosition.showFileNameDialogWithTextArea()

            );

            scene.getAccelerators().put(        //Ctrl1
                    new KeyCodeCombination(KeyCode.DIGIT1
                            , KeyCombination.CONTROL_DOWN),
                    () -> {
                        String filePath = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl1);
                        if (filePath != null) {
                            chooseNote(filePath);
                        } else {      //shortcut empty noti
                            layoutPosition.controlShortcutNoValueSpecifyWarning(ControlShortcut.Ctrl1);
                        }
                    }
            );
            scene.getAccelerators().put(        //Ctrl 2
                    new KeyCodeCombination(KeyCode.DIGIT2
                            , KeyCombination.CONTROL_DOWN),
                    () -> {
                        String filePath = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl2);
                        if (filePath != null) {
                            chooseNote(filePath);
                        } else {
                            layoutPosition.controlShortcutNoValueSpecifyWarning(ControlShortcut.Ctrl2);
                        }
                    }
            );
            scene.getAccelerators().put(        //Ctrl 3
                    new KeyCodeCombination(KeyCode.DIGIT3
                            , KeyCombination.CONTROL_DOWN),
                    () -> {
                        String filePath = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl3);
                        if (filePath != null) {
                            chooseNote(filePath);
                        } else {
                            layoutPosition.controlShortcutNoValueSpecifyWarning(ControlShortcut.Ctrl3);
                        }
                    }
            );
            scene.getAccelerators().put(        //Ctrl 4
                    new KeyCodeCombination(KeyCode.DIGIT4
                            , KeyCombination.CONTROL_DOWN),
                    () -> {
                        String filePath = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl4);
                        if (filePath != null) {
                            chooseNote(filePath);
                        } else {
                            layoutPosition.controlShortcutNoValueSpecifyWarning(ControlShortcut.Ctrl4);
                        }
                    }
            );
            scene.getAccelerators().put(        //Ctrl 5
                    new KeyCodeCombination(KeyCode.DIGIT5
                            , KeyCombination.CONTROL_DOWN),
                    () -> {
                        String filePath = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl5);
                        if (filePath != null) {
                            chooseNote(filePath);
                        } else {
                            layoutPosition.controlShortcutNoValueSpecifyWarning(ControlShortcut.Ctrl5);
                        }
                    }
            );
            scene.getAccelerators().put(        //Ctrl 6
                    new KeyCodeCombination(KeyCode.DIGIT6
                            , KeyCombination.CONTROL_DOWN),
                    () -> {
                        String filePath = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl6);
                        if (filePath != null) {
                            chooseNote(filePath);
                        } else {
                            layoutPosition.controlShortcutNoValueSpecifyWarning(ControlShortcut.Ctrl6);
                        }
                    }
            );
            scene.getAccelerators().put(        //Ctrl 7
                    new KeyCodeCombination(KeyCode.DIGIT7
                            , KeyCombination.CONTROL_DOWN),
                    () -> {
                        String filePath = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl7);
                        if (filePath != null) {
                            chooseNote(filePath);
                        } else {
                            layoutPosition.controlShortcutNoValueSpecifyWarning(ControlShortcut.Ctrl7);
                        }
                    }
            );
            scene.getAccelerators().put(        //Ctrl 8
                    new KeyCodeCombination(KeyCode.DIGIT8
                            , KeyCombination.CONTROL_DOWN),
                    () -> {
                        String filePath = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl8);
                        if (filePath != null) {
                            chooseNote(filePath);
                        } else {
                            layoutPosition.controlShortcutNoValueSpecifyWarning(ControlShortcut.Ctrl8);
                        }
                    }
            );
            scene.getAccelerators().put(        //Ctrl 9
                    new KeyCodeCombination(KeyCode.DIGIT9
                            , KeyCombination.CONTROL_DOWN),
                    () -> {
                        String filePath = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl9);
                        if (filePath != null) {
                            chooseNote(filePath);
                        } else {
                            layoutPosition.controlShortcutNoValueSpecifyWarning(ControlShortcut.Ctrl9);
                        }
                    }
            );
//            scene.getAccelerators().put(        //Ctrl 0
//                    new KeyCodeCombination(KeyCode.DIGIT0
//                            , KeyCombination.CONTROL_DOWN),
//                    () -> {
//                        String filePath = ControlShortcutStorage.GetFilePath(ControlShortcut.Ctrl0);
//                        if (filePath != null) {
//                            chooseNote(filePath);
//                        }
//                    }
//            );

            scene.getAccelerators().put(        //Ctrl Shift 1
                    new KeyCodeCombination(KeyCode.DIGIT1
                            , KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        shortCutSave(ControlShortcut.Ctrl1, absoluteFilePath);
                    }
            );
            scene.getAccelerators().put(        //Ctrl Shift 2
                    new KeyCodeCombination(KeyCode.DIGIT2
                            , KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        shortCutSave(ControlShortcut.Ctrl2, absoluteFilePath);
                    }
            );
            scene.getAccelerators().put(        //Ctrl Shift 3
                    new KeyCodeCombination(KeyCode.DIGIT3
                            , KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        shortCutSave(ControlShortcut.Ctrl3, absoluteFilePath);
                    }
            );
            scene.getAccelerators().put(        //Ctrl Shift 4
                    new KeyCodeCombination(KeyCode.DIGIT4
                            , KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        shortCutSave(ControlShortcut.Ctrl4, absoluteFilePath);
                    }
            );
            scene.getAccelerators().put(        //Ctrl Shift 5
                    new KeyCodeCombination(KeyCode.DIGIT5
                            , KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        shortCutSave(ControlShortcut.Ctrl5, absoluteFilePath);
                    }
            );
            scene.getAccelerators().put(        //Ctrl Shift 6
                    new KeyCodeCombination(KeyCode.DIGIT6
                            , KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        shortCutSave(ControlShortcut.Ctrl6, absoluteFilePath);
                    }
            );
            scene.getAccelerators().put(        //Ctrl Shift 7
                    new KeyCodeCombination(KeyCode.DIGIT7
                            , KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        shortCutSave(ControlShortcut.Ctrl7, absoluteFilePath);
                    }
            );
            scene.getAccelerators().put(        //Ctrl Shift 8
                    new KeyCodeCombination(KeyCode.DIGIT8
                            , KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        shortCutSave(ControlShortcut.Ctrl8, absoluteFilePath);
                    }
            );
            scene.getAccelerators().put(        //Ctrl Shift 9
                    new KeyCodeCombination(KeyCode.DIGIT9
                            , KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        shortCutSave(ControlShortcut.Ctrl9, absoluteFilePath);
                    }
            );


//            scene.getAccelerators().put(        //Ctrl Shift 0
//                    new KeyCodeCombination(KeyCode.DIGIT0
//                            , KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
//                    () -> {
//                        shortCutSave(ControlShortcut.Ctrl0, absoluteFilePath);
//                    }
//            );


            scene.getAccelerators().put(        //Alt 1
                    new KeyCodeCombination(KeyCode.DIGIT1
                            , KeyCombination.ALT_DOWN),
                    () -> {

                        if (dockContainerSplitPane.getItems().size() > 1) {
                            contentContainer.dockContainer();
                        } else {
                            contentContainer.dockContainerExistControl();
                        }
//                        contentContainer.dockContainerExistControl();
                    }
            );

            scene.getAccelerators().put(        //Alt 2
                    new KeyCodeCombination(KeyCode.DIGIT2
                            , KeyCombination.ALT_DOWN),
                    () -> {
//                                layoutPosition.reformatComponents();
                        searchBar.dockSearchBarExistControl();
                    }
            );
            scene.getAccelerators().put(        //Alt 3
                    new KeyCodeCombination(KeyCode.DIGIT3
                            , KeyCombination.ALT_DOWN),
                    () -> {
//                        contentContainer.dockContainer();

                        controlShortcutContainer.dockControlShortcutContainer();

                    }
            );

            scene.getAccelerators().put(        //Alt 4
                    new KeyCodeCombination(KeyCode.DIGIT4
                            , KeyCombination.ALT_DOWN),
                    () -> {
                        chooseNoteFromDir();


//                        if (dockContainerSplitPane.getItems().size() > 1) {
//                            contentContainer.dockContainer();
//                        } else {
//                            contentContainer.dockContainer();
//
//                            TextArea textArea = new TextArea("Hello");
//                            AnchorPane anchorPane = new AnchorPane(textArea);
//
//                            AnchorPane.setTopAnchor(textArea, 0.0);
//                            AnchorPane.setRightAnchor(textArea, 0.0);
//                            AnchorPane.setBottomAnchor(textArea, 0.0);
//                            AnchorPane.setLeftAnchor(textArea, 0.0);
//
//                            dockContainerSplitPane.getItems().add(anchorPane);
//
//                            Platform.runLater(() -> dockContainerSplitPane.setDividerPositions(0.2));
//                        }

                    }
            );


            scene.getAccelerators().put(        //Alt 5
                    new KeyCodeCombination(KeyCode.DIGIT5
                            , KeyCombination.ALT_DOWN),
                    () -> {
                        for (Map.Entry<String, String> m : FileHandlings.getLineObjMap().entrySet()) {
                            System.out.println(String.format("key %s value %s", m.getKey(), m.getValue()));
                        }

                    }
            );
//            scene.getAccelerators().put(        //Shift Down
//                    new KeyCodeCombination(KeyCode.UP, KeyCombination.SHIFT_ANY),
//                    () -> {
//                        undo();
//                    }
//            );

            scene.getAccelerators().put(        //Alt Shift D
                    new KeyCodeCombination(KeyCode.D
                            , KeyCombination.ALT_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        //                                layoutPosition.reformatComponents();
                        layoutPosition.restoreDefaultLayouts();
                    }
            );
            scene.getAccelerators().put(        //Alt Shift S
                    new KeyCodeCombination(KeyCode.S
                            , KeyCombination.ALT_DOWN, KeyCombination.SHIFT_DOWN),
                    () -> {
                        //                                layoutPosition.reformatComponents();
                        layoutPosition.openSettings();
                    }
            );
            scene.getAccelerators().put(        //Alt Shift Ctrl S
                    new KeyCodeCombination(KeyCode.S
                            , KeyCombination.ALT_DOWN, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN),
                    () -> {
                        layoutPosition.openShortcutsSettingSection();
                    }
            );


            scene.getAccelerators().put(        //Alt HOME
                    new KeyCodeCombination(KeyCode.HOME
                            , KeyCombination.ALT_DOWN),
                    () -> layoutPosition.openHome()
            );

            scene.getAccelerators().put(        //Ctrl S
                    new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            if (absoluteFilePath != null && textArea.getText() != null) {
                                layoutPosition.showProgressing("saving..", true, ProgressIndicator.INDETERMINATE_PROGRESS);
                                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
                                pauseTransition.setOnFinished(e -> {
                                    layoutPosition.showProgressing("", false, 0);
                                    try {
                                        FileHandlings.FileWrite(absoluteFilePath, textArea.getText());
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    saveEditHistory();
                                    FileHandlings.getTextObjectLists();     // need to refresh lineObjList
                                    new AllRefreshServicesHandlerBuilder().service_contentService(true).service_textAreaService(true).service_editHistoryService(true).build();
                                });
                                pauseTransition.play();
                                PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(0.3));
                                pauseTransition1.setOnFinished(e -> layoutPosition.showProgressing("saved", true, 1));

                                pauseTransition1.play();
                            }
                        }
                    }
            );
            scene.getAccelerators().put(        //Ctrl F
                    new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            if (absoluteFilePath != null && textArea.getText() != null) {
                                layoutPosition.matchCaseVBox();
                                layoutPosition.bindWords();
                                layoutPosition.searchAndHighLight();
                            }
                        }
                    }
            );

            scene.getAccelerators().put(        //Alt Right
                    new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.ALT_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            nextNote();
                        }
                    }
            );
            scene.getAccelerators().put(        //Alt Left
                    new KeyCodeCombination(KeyCode.LEFT, KeyCombination.ALT_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            previousNote();
                        }
                    }
            );
            scene.getAccelerators().put(        //Alt Up
                    new KeyCodeCombination(KeyCode.UP, KeyCombination.ALT_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            double scrollValue = minimalistPageContentContainer.contentScrollPane.getVvalue();
//                                minimalistPageContentContainer.contentScrollPane.setVvalue(scrollValue);
                            Animation animation = new Timeline(
                                    new KeyFrame(Duration.seconds(0.5),
                                            new KeyValue(minimalistPageContentContainer.contentScrollPane.vvalueProperty(), scrollValue - 0.090),
                                            new KeyValue(minimalistPageContentContainer.contentScrollPane.cacheHintProperty(), CacheHint.QUALITY)));
                            animation.play();
                        }
                    }
            );
            scene.getAccelerators().put(        //Alt Down
                    new KeyCodeCombination(KeyCode.DOWN, KeyCombination.ALT_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            double scrollValue = minimalistPageContentContainer.contentScrollPane.getVvalue();
//                                minimalistPageContentContainer.contentScrollPane.setVvalue(scrollValue);

                            Animation animation = new Timeline(
                                    new KeyFrame(Duration.seconds(0.5),
                                            new KeyValue(minimalistPageContentContainer.contentScrollPane.vvalueProperty(), scrollValue + 0.090),
                                            new KeyValue(minimalistPageContentContainer.contentScrollPane.cacheHintProperty(), CacheHint.QUALITY)));
                            animation.play();
                        }
                    }
            );

            scene.getAccelerators().put(        //Alt Shift up
                    new KeyCodeCombination(KeyCode.UP, KeyCombination.ALT_DOWN, KeyCombination.SHIFT_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            Animation animation = new Timeline(
                                    new KeyFrame(Duration.seconds(0.5),
                                            new KeyValue(minimalistPageContentContainer.contentScrollPane.vvalueProperty(), 0),
                                            new KeyValue(minimalistPageContentContainer.contentScrollPane.cacheHintProperty(), CacheHint.QUALITY)));
                            animation.play();
                        }
                    }
            );
            scene.getAccelerators().put(        //Alt Shift Down
                    new KeyCodeCombination(KeyCode.DOWN, KeyCombination.ALT_DOWN, KeyCombination.SHIFT_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            Animation animation = new Timeline(
                                    new KeyFrame(Duration.seconds(0.5),
                                            new KeyValue(minimalistPageContentContainer.contentScrollPane.vvalueProperty(), 1),
                                            new KeyValue(minimalistPageContentContainer.contentScrollPane.cacheHintProperty(), CacheHint.QUALITY)));
                            animation.play();


                        }
                    }
            );


        });

    }       //ADD Shortcuts

    private void perform() {
        //Stage Event Priority 1
        Platform.runLater(() -> {
            Stage stage = (Stage) minimalistPageAnchorPane.getScene().getWindow();
            stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {     //focus stage
                    layoutPosition.DirectoryMissingBlock();
                    textObjectArrayList.clear();
                    new AllRefreshServicesHandlerBuilder().service_textObjectList().service_textAreaService().service_panFlatRefreshService().service_contentService().service_editHistoryService().build();
                } else {        //lost focus stage
                    saveNote();
                    new AllRefreshServicesHandlerBuilder().service_textAreaService().service_contentService().build();
                    layoutPosition.DirectoryMissingBlock();
//                            saveAll();

                }
            });
        });


    }      //Perform

    public void searchResults(String text) {
        minimalistPageContentContainer.contentFlowPane.getChildren().clear();
        searchLists.clear();
        textObjectArrayList.forEach(e -> {

            if (e.getText().toLowerCase().trim().contains(text)) {
                searchLists.add(e);            //for Enter Key
                contentContainer.callContent(e.getAbsolutePath().trim());
            }

        });
        VBox vb = new VBox();
        vb.setPrefWidth(305);
        Button btn = new Button("\ue89c");
        btn.getStyleClass().addAll("icon-fill", "font-15");
        btn.setOnAction(e -> layoutPosition.showFileNameDialog());
        vb.getChildren().addAll(btn);
        vb.setAlignment(Pos.CENTER);
        VBox.setMargin(btn, new Insets(15, 0, 15, 0));
        minimalistPageContentContainer.contentFlowPane.getChildren().add(vb);


//        Label label = new Label("Shortcuts ");
//        label.setStyle("-fx-text-fill: #9286DF ;");
//        minimalistPageContentContainer.contentFlowPane.getChildren().add(label);
//        ControlShortcutStorage.ReturnControlShortcutHashMap().forEach((key, value) -> {
////            minimalistPageContentContainer.contentFlowPane.getChildren().add(layoutPosition.buildControlShortcutFilePathHBox(key, value, false));
//        });

    }

    public boolean bagOfWords(String str) {
        String[] words = {".txt", ".ns"};
        return (Arrays.asList(words).contains(str));
    }

    public String getFileNameOnly(String filePath) {
        return filePath.substring(filePath.lastIndexOf("\\") + 1);
    }

    public void chooseNote(String filePath) {
        absoluteFilePath = filePath;
        addTempFilePaths(filePath);


        notifyChooseNotePathLabel.setText(FontRepair.fixmyanamrfont(getFileNameOnly(filePath).trim()));
        notifyChooseNoteVBox.setVisible(true);
        PauseTransition pauseForCloseTransition = new PauseTransition(Duration.seconds(2));
        pauseForCloseTransition.setOnFinished(e -> notifyChooseNoteVBox.setVisible(false));
        pauseForCloseTransition.play();


        removeEffect();
        new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_contentService(true).service_textAreaService(true).service_suggestService(true).service_editHistoryService(true).build();
        textObjectArrayList.forEach(textObj -> {
            if (textObj.getAbsolutePath().equals(absoluteFilePath)) {
                absoluteFilePathPositionIndex = textObjectArrayList.indexOf(textObj);
            }
        });


        layoutPosition.bindWords();
        layoutPosition.searchAndHighLight();
        layoutPosition.bindLineObj();
        layoutPosition.searchLineObjHighlight();
        //comeback

    }

    public void chooseNoteFromDir() {
        FileChooser fileChooser = new FileChooser();

        File f = new File(NoteSpacePathStorage.getNoteSpacePathObj().spacePath);
        if (f.exists()) {

//            fileChooser.setInitialDirectory(f);
            fileChooser.setTitle("Choose Text File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TEXT", "*.txt")

            );
            //            fileChooser.showOpenDialog(new Stage());
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
//                ControlShortcutStorage.SaveControlShortcut(shortcut, file.getAbsolutePath());
                chooseNote(file.getAbsolutePath());
            }
        }
    }


    public void quickNote() {
        System.out.println("Quick Note ");
        //QuickNote
        String randomName = UUID.randomUUID().toString().substring(0, 7);
        String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + "QuickNote " + randomName + ".txt";
        boolean exist = FileHandlings.checkFileExist(filepath);
        if (!exist) {
            FileHandlings.createNewFile(filepath);

            minimalistPageContentContainer.contentScrollPane.setVvalue(0);

            absoluteFilePath = filepath;
            absoluteFilePathPositionIndex = -1;
            addTempFilePaths(filepath);
            new AllRefreshServicesHandlerBuilder().service_textObjectList().service_textAreaService().service_contentService().service_logsService().service_suggestService().service_editHistoryService().build();
        }
    }

    public void undo() {
        ArrayList<RuntimeNoteTemp> noteTempArrayList = RuntimeNoteTempStorage.getRunTimeTempObj_listByFileName(absoluteFilePath);
        Collections.reverse(noteTempArrayList);

        if (undoIndex > noteTempArrayList.size()) undoIndex = 1;

        if (undoIndex < noteTempArrayList.size()) {

            String undoText = noteTempArrayList.get(undoIndex++).getText();
            textArea.setText(undoText);

            Platform.runLater(() -> textArea.end());

            noteTempArrayList.forEach(e -> {
                System.out.println("Print " + e);
            });


            undoIndex = undoIndex + 1;
        }
    }

    public void redo() {
        ArrayList<RuntimeNoteTemp> noteTempArrayList = RuntimeNoteTempStorage.getRunTimeTempObj_listByFileName(absoluteFilePath);
//        Collections.reverse(noteTempArrayList);

        if (redoIndex > noteTempArrayList.size()) redoIndex = 1;

        if (redoIndex < noteTempArrayList.size()) {

            String undoText = noteTempArrayList.get(redoIndex++).getText();
            textArea.setText(undoText);

            Platform.runLater(() -> textArea.end());

            noteTempArrayList.forEach(e -> {
                System.out.println("Print " + e);
            });


            redoIndex = redoIndex + 1;
        }
    }

//    public void undo() {
//        ArrayList<RuntimeNoteTemp> noteTempArrayList = RuntimeNoteTempStorage.getRunTimeTempObj_listByFileName(absoluteFilePath);
//        Collections.reverse(noteTempArrayList);
//        int size = noteTempArrayList.size();
//
//        if (undoIndex < size) {
//
//            System.out.println(noteTempArrayList.get(undoIndex));
//            String undoText = noteTempArrayList.get(undoIndex).getText();
//            textArea.setText(undoText);
//
//            undoIndex++;
//
//            System.out.println("");
////            noteTempArrayList.forEach(noteTemp -> {
////                System.out.println(noteTemp);
////            });
//        }
//    }
//
//    public void redo() {
//        ArrayList<RuntimeNoteTemp> noteTempArrayList = RuntimeNoteTempStorage.getRunTimeTempObj_listByFileName(absoluteFilePath);
////        Collections.reverse(noteTempArrayList);
//        int size = noteTempArrayList.size() - 1;
//
//
//        if (undoIndex < size) {
//
//            System.out.println(noteTempArrayList.get(undoIndex));
//            String undoText = noteTempArrayList.get(undoIndex++).getText();
//            textArea.setText(undoText);
//
////            undoIndex++;
//
//            System.out.println("");
////            noteTempArrayList.forEach(noteTemp -> {
////                System.out.println(noteTemp);
////            });
//        }
//    }

    public void nextNote() {
        absoluteFilePathPositionIndex = absoluteFilePathPositionIndex + 1;
        if (absoluteFilePathPositionIndex < textObjectArrayList.size()) {

            TextObject to = textObjectArrayList.get(absoluteFilePathPositionIndex);
            chooseNote(to.getAbsolutePath());

        } else {
            absoluteFilePathPositionIndex = 0;
            TextObject to = textObjectArrayList.get(absoluteFilePathPositionIndex);
            chooseNote(to.getAbsolutePath());
        }
    }

    public void previousNote() {
        if (absoluteFilePathPositionIndex == 0) {
            absoluteFilePathPositionIndex = textObjectArrayList.size() - 1;
            TextObject to = textObjectArrayList.get(absoluteFilePathPositionIndex);
            chooseNote(to.getAbsolutePath());
            System.out.println(absoluteFilePathPositionIndex);
        } else {
            if (absoluteFilePathPositionIndex != -1) {

                absoluteFilePathPositionIndex = absoluteFilePathPositionIndex - 1;
                TextObject to = textObjectArrayList.get(absoluteFilePathPositionIndex);
                chooseNote(to.getAbsolutePath());
                System.out.println(absoluteFilePathPositionIndex);
            }
        }
    }

    public void saveNote() {
        if (absoluteFilePath != null && textArea.getText() != null) {
            try {
                FileHandlings.FileWrite(absoluteFilePath, textArea.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            textObjectArrayList.clear();
            new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_contentService(true).service_editHistoryService(true).build();

        }

    }

    public void saveEditHistory() {
        boolean empty = textArea.getText().trim().isEmpty();
        if (!empty) {
            EditHistoryObjStorage.addEditHistoryObj(new EditHistoryObj(absoluteFilePath, textArea.getText().trim()));
        }
    }

    public void deleteNote() {


        layoutPosition.showProgressing("preparing to delete..", true, ProgressIndicator.INDETERMINATE_PROGRESS);
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
        pauseTransition.setOnFinished(e -> {
            layoutPosition.showProgressing("", false, 0);

            FileHandlings.deleteFile(absoluteFilePath);
            textArea.clear();
            layoutPosition.stackLayoutVisiblePriority(homeAnchorPane);

            absoluteFilePath = null;

            new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_logsService(true).build();

            layoutPosition.openHome();
        });
        pauseTransition.play();
        PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(0.3));
        pauseTransition1.setOnFinished(e -> layoutPosition.showProgressing("deleted", true, 1));
        pauseTransition1.play();


    }

    public void deleteNote(String absolutePath) {
        FileHandlings.deleteFile(absolutePath);
        textArea.clear();
        layoutPosition.stackLayoutVisiblePriority(homeAnchorPane);
        absoluteFilePath = null;

        new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_logsService(true).build();
        layoutPosition.openHome();
    }

    public void shortCutSave(ControlShortcut shortcut, String filePath) {
        ControlShortcutStorage.SaveControlShortcut(shortcut, filePath);
        textObjectArrayList.clear();
        new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_contentShortcutService(true).service_shortcutDockContainerService(true).build();


    }


    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }


    class AllRefreshServicesHandler {


        public AllRefreshServicesHandler(AllRefreshServicesHandlerBuilder builder) {
            boolean runTextObjectListService = builder.runTextObjectListService;
            boolean runTextAreaService = builder.runTextAreaService;
            boolean runContentServices = builder.runContentServices;
            boolean runContentShortcutServices = builder.runContentShortcutServices;
            boolean runShortcutDockContainerService = builder.runShortcutDockContainerService;
            boolean runPanFlatDetectServices = builder.runPanFlatDetectServices;
            boolean runPanFlatRefreshService = builder.runPanFlatRefreshService;
            boolean runSuggestService = builder.runSuggestService;
            boolean runLogsService = builder.runLogsService;
            boolean runBackupService = builder.runBackupService;
            boolean runEditHistoryService = builder.runEditHistoryService;


            if (runTextObjectListService) {
                textObjectListService.run();
            }
            if (runTextAreaService) {
                textAreaRefreshService.run();
            }
            if (runContentServices) {
                for (Map.Entry<MinimalistPageContent, Parent> entry : contentMap.entrySet()) {
                    entry.getKey().textObjectServicesRefresh();
                }
                for (Map.Entry<FileNameFlatBox, Parent> entry : contentFileNameFLatMap.entrySet()) {
                    entry.getKey().textObjectServicesRefresh();
                }
            }
//            if (runContentShortcutServices) {
//                for (Map.Entry<MinimalistPageContent, Parent> entry : contentMap.entrySet()) {
//                    entry.getKey().shortcutServiceRefresh();
//                }
//            }
//            if (runShortcutDockContainerService) {
//                for (Map.Entry<MinimalistPageContent, Parent> entry : contentMap.entrySet()) {
//                    entry.getKey().shortcutServiceRefresh();
//                }
//            }

            if (runPanFlatDetectServices) {
                minimalistPageDirectoryChoosePanFlat.refreshDetection();
            }
            if (runPanFlatRefreshService) {
                minimalistPageDirectoryChoosePanFlat.refreshUI();
                String dirPath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
                directoryNameTextField.setText(dirPath);

            }

            if (runSuggestService) {
                ctrlEnterLabel.setText("All Notes");
                FileHandlings.getTextObjectLists();
                suggestContainer.loadSuggestions();
            }
            if (runLogsService) {
//                new LogsService();
                logsService.restart();
            }
            if (runBackupService) {
                backupService.restart();
            }

            if (runEditHistoryService) {
                editHistoryService.restart();
            }

        }

    }

    class AllRefreshServicesHandlerBuilder {
        private boolean runTextObjectListService;
        private boolean runTextAreaService;
        private boolean runContentServices;
        private boolean runContentShortcutServices;
        private boolean runShortcutDockContainerService;
        private boolean runPanFlatDetectServices;
        private boolean runPanFlatRefreshService;
        private boolean runSuggestService;
        private boolean runLogsService;
        private boolean runBackupService;
        private boolean runEditHistoryService;

        public AllRefreshServicesHandlerBuilder service_textObjectList(boolean run) {
            this.runTextObjectListService = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_textObjectList() {
            this.runTextObjectListService = true;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_textAreaService(boolean run) {
            this.runTextAreaService = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_textAreaService() {
            this.runTextAreaService = true;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_contentService(boolean run) {
            this.runContentServices = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_contentService() {
            this.runContentServices = true;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_contentShortcutService(boolean run) {
            this.runContentShortcutServices = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_contentShortcutService() {
            this.runContentShortcutServices = true;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_shortcutDockContainerService(boolean run) {
            this.runShortcutDockContainerService = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_shortcutDockContainerService() {
            this.runShortcutDockContainerService = true;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_panFlatDetectService(boolean run) {
            this.runPanFlatDetectServices = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_panFlatDetectService() {
            this.runPanFlatDetectServices = true;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_panFlatRefreshService(boolean run) {
            this.runPanFlatRefreshService = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_panFlatRefreshService() {
            this.runPanFlatRefreshService = true;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_suggestService(boolean run) {
            this.runSuggestService = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_suggestService() {
            this.runSuggestService = true;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_logsService(boolean run) {
            this.runLogsService = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_logsService() {
            this.runLogsService = true;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_backupService(boolean run) {
            this.runBackupService = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_backupService() {
            this.runBackupService = true;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_editHistoryService(boolean run) {
            this.runEditHistoryService = run;
            return this;
        }

        public AllRefreshServicesHandlerBuilder service_editHistoryService() {
            this.runEditHistoryService = true;
            return this;
        }


        public AllRefreshServicesHandler build() {
            return new AllRefreshServicesHandler(this);
        }
    }

    class LayoutPosition {
        private final BooleanProperty dragModeActiveProperty =
                new SimpleBooleanProperty(this, "dragModeActive", false);
        Stage reopenStageForShortcusSection;
        int foundCount = 0;
        int foundCount1 = 0;
        int lineNumber = 1;
        private boolean scrollBarVisible;
        private boolean suggestVBoxVisible;
        private boolean matchCaseVBoxVisible;
        private boolean lineCaseVBoxVisible;
        private boolean editHistoryBoxVisble;
        private int column = 1;

        private LayoutPosition() {

        }

        private int returnAdjustedSize() {
            int calcValue = 321;
            int[] oneColumn = {301, 321};
            int[] twoColumn = {612, 627};
            int[] threeColumn = {917, 932};


            if (scrollBarVisible && column == 1) {
                return oneColumn[1];
            }
            if (scrollBarVisible && column == 2) {
                return twoColumn[1];
            }
            if (scrollBarVisible && column == 3) {
                return threeColumn[1];
            }

            if (!scrollBarVisible && column == 1) {
                return oneColumn[0];
            }
            if (!scrollBarVisible && column == 2) {
                return twoColumn[0];
            }
            if (!scrollBarVisible && column == 3) {
                return threeColumn[0];
            }


            return calcValue;
        }

        private void clearSettingsVBoxEffect() {
            Arrays.stream(new HBox[]{minimalistPageContentContainer.onecolHBox, minimalistPageContentContainer.twocolHBox, minimalistPageContentContainer.threecolHBox}).forEach(hbox -> hbox.getStyleClass().remove(contentColumnModeClickEffectHex()));

        }

        private String highlightYellowClickEffectHex() {
            return "highlightYellowClickEffect";
        }

        private String highlightYellowTextEffect() {
            return "highlightYellowTextEffect";
        }

        private String highlightYellowBorderEffect() {
            return "highlightYellowBorderEffect";
        }

        private String suggestVBoxDragClickEffectHex() {
            return "BgColor-f2f2f7";
        }

        private String contentColumnModeClickEffectHex() {
            return "BgColor-f2f2f7";
        }

        private String contentViewModeClickEffectHex() {
            return "BgColor-f2f2f7";
        }

        private String contentViewModeIconClickEffectHex() {
            return "vboxClickEffect";
        }

        private String searchBarViewModeClickEffectHex() {
            return "buttonBlueBorderBorderEffect";
        }

        private Effect getFrostEffect(double blur_amount, int iteration) {
            Effect frostEffect =
                    new BoxBlur(blur_amount, blur_amount, iteration);

            return frostEffect;
        }

        private void clearViewModeEffect() {
            Arrays.stream(new HBox[]{minimalistPageContentContainer.floatModeHBox, minimalistPageContentContainer.windowModeHBox, minimalistPageContentContainer.dockModeHBox}).forEach(hbox -> hbox.getStyleClass().remove(contentViewModeClickEffectHex()));            //Hbox
            Arrays.stream(new Label[]{minimalistPageContentContainer.floatModeIconLabel, minimalistPageContentContainer.windowModeIconLabel, minimalistPageContentContainer.dockModeIconLabel}).forEach(label -> label.getStyleClass().remove(contentViewModeIconClickEffectHex()));            //IconLabel
        }

        private void clearSearchBarViewModeEffect() {
            Arrays.stream(new Button[]{minimalistPageSearchBar.floatModeButton, minimalistPageSearchBar.windowModeButton, minimalistPageSearchBar.dockModeButton, minimalistPageSearchBar.closeSearchBoxButton}).forEach(hbox -> hbox.getStyleClass().remove(searchBarViewModeClickEffectHex()));
        }

        private void clearMatchCaseContainerViewModeEffect() {
            Arrays.stream(new Label[]{minimalistPageMatchCaseHighlight.floatModeIconLabel, minimalistPageMatchCaseHighlight.windowModeIconLabel}).forEach(label -> label.getStyleClass().remove(searchBarViewModeClickEffectHex()));
        }

        private void clearLineCaseContainerViewModeEffect() {
            Arrays.stream(new Label[]{minimalistPageLineCase.floatModeIconLabel, minimalistPageLineCase.windowModeIconLabel}).forEach(label -> label.getStyleClass().remove(searchBarViewModeClickEffectHex()));
        }

        private void clearMatchCaseContainerHighlightModeEffect() {
            Arrays.stream(new Label[]{minimalistPageMatchCaseHighlight.matchCaseWordOptLabel, minimalistPageMatchCaseHighlight.matchCaseCharOptLabel}).forEach(label -> {
                label.getStyleClass().remove(contentViewModeIconClickEffectHex());
            });
        }

        private void clearContentShowCaseEffect() {
            Arrays.stream(new HBox[]{minimalistPageContentContainer.flatContentHBox, minimalistPageContentContainer.boxContentHBox, minimalistPageContentContainer.boxContentWithFileNameHBox, minimalistPageContentContainer.boxContentWithFileNameOptionsHBox}).forEach(label -> {
                label.getStyleClass().remove(contentColumnModeClickEffectHex());
            });

            Arrays.stream(new HBox[]{minimalistPageSettings.flatContentHBox, minimalistPageSettings.boxContentHBox, minimalistPageSettings.boxContentWithFileNameHBox, minimalistPageSettings.boxContentWithFileNameOptionsHBox}).forEach(label -> {
                label.getStyleClass().remove(contentColumnModeClickEffectHex());
            });
        }

        private void columnAdjustSize(int col) {
            column = col;
            clearSettingsVBoxEffect();
            switch (col) {
                case 1:
                    Platform.runLater(() -> {
                        minimalistPageContentContainer.onecolHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());
                        translateWidth(dockContainerSplitPane, returnAdjustedSize());
                        //				    contentAnchorPane.setPrefWidth ( returnAdjustedSize ( ) );
                    });
                    break;
                case 2:
                    Platform.runLater(() -> {
                        minimalistPageContentContainer.twocolHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());
                        translateWidth(dockContainerSplitPane, returnAdjustedSize());
                        //				    contentAnchorPane.setPrefWidth ( returnAdjustedSize ( ) );
                    });
                    break;
                case 3:
                    Platform.runLater(() -> {
                        minimalistPageContentContainer.threecolHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());
                        translateWidth(dockContainerSplitPane, returnAdjustedSize());
                        //				    contentAnchorPane.setPrefWidth ( returnAdjustedSize ( ) );
                    });
                    break;
                default:
            }
        }

        private void scrollBar() {
            scrollBarVisibleProperty(!scrollBarVisible);
        }

        private void scrollBarVisibleProperty(boolean visible) {
            scrollBarVisible = visible;
            if (visible) {
                Platform.runLater(() -> {

                    minimalistPageContentContainer.scrollbarHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());
                    minimalistPageContentContainer.scrollbarHBoxCheck.setSelected(true);
                    minimalistPageContentContainer.contentScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                    //								contentAnchorPane.setPrefWidth ( returnAdjustedSize ( ) );
                    translateWidth(dockContainerSplitPane, returnAdjustedSize());
                });
            } else {
                Platform.runLater(() -> {

                    minimalistPageContentContainer.scrollbarHBox.getStyleClass().remove(layoutPosition.contentColumnModeClickEffectHex());
                    minimalistPageContentContainer.scrollbarHBoxCheck.setSelected(false);
                    minimalistPageContentContainer.contentScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    //								contentAnchorPane.setPrefWidth ( returnAdjustedSize ( ) );
                    translateWidth(dockContainerSplitPane, returnAdjustedSize());
                });
            }
        }

        private void suggestVBox() {
            if (suggestVBoxVisible) {
                selectRangeAvailable = false;
                suggestVBoxVisibleProperty(false);
            } else {
                selectRangeAvailable = true;
                suggestVBoxVisibleProperty(true);
            }
        }

        private void suggestVBoxVisibleProperty(boolean visible) {
            suggestVBoxVisible = visible;
            if (visible) {      //if true add extra number shortcuts


                //		    suggestHBoxLabel.setText ( "Close Word Suggestions" );
                suggestHBox.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> {
                    suggestVBox.setVisible(true);
//                        suggestRefresh();
                    new AllRefreshServicesHandlerBuilder().service_suggestService(true).build();
                });
            } else {
                //		    suggestHBoxLabel.setText ( "Open Word Suggestions" );
                suggestHBox.getStyleClass().remove(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> suggestVBox.setVisible(false));
            }
        }

        private void editHistoryBox() {
            editHistoryBoxVisibleProperty(!editHistoryBoxVisble);
        }

        private void editHistoryBoxVisibleProperty(boolean visible) {
            editHistoryBoxVisble = visible;
            if (visible) {      //if true add extra number shortcuts
                //		    suggestHBoxLabel.setText ( "Close Word Suggestions" );
                editHistoryHBox.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> editHistoryAnchorPane.setVisible(true));
            } else {
                //		    suggestHBoxLabel.setText ( "Open Word Suggestions" );
                editHistoryHBox.getStyleClass().remove(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> editHistoryAnchorPane.setVisible(false));
            }
        }

        private void matchCaseVBox() {
            matchCaseVBoxVisibleProperty(!matchCaseVBoxVisible);
        }

        private void matchCaseVBoxVisibleProperty(boolean visible) {
            matchCaseVBoxVisible = visible;
            if (visible) {

                matchCaseHBox.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> {
                    floatMatchCaseAnchorPane.setVisible(true);
//                    textArea.setEffect(layoutPosition.getFrostEffect(100, 3));
                });

                Platform.runLater(() -> minimalistPageMatchCaseHighlight.matchCaseTextField.requestFocus());

            } else {

                matchCaseHBox.getStyleClass().remove(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> {
                    floatMatchCaseAnchorPane.setVisible(false);
                    textArea.setEffect(null);
                });

                Platform.runLater(() -> textArea.requestFocus());

            }
        }

        private void lineCaseVBox() {
            lineCaseVBoxVisibleProperty(!lineCaseVBoxVisible);
        }

        private void lineCaseVBoxVisibleProperty(boolean visible) {
            lineCaseVBoxVisible = visible;
            if (visible) {

                lineCaseHBox.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> {
                    lineMatchCaseAnchorPane.setVisible(true);
//                    textArea.setEffect(layoutPosition.getFrostEffect(100, 3));
                });

                Platform.runLater(() -> minimalistPageLineCase.lineCaseTextField.requestFocus());

            } else {

                lineCaseHBox.getStyleClass().remove(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> {
                    lineMatchCaseAnchorPane.setVisible(false);
                    textArea.setEffect(null);
                });

                Platform.runLater(() -> textArea.requestFocus());

            }
        }

        private void contentSettingsVBox() {
            contentSettingsVBoxVisibleProperty(!minimalistPageContentContainer.contentSettingsVBox.isVisible());
        }

        private void contentSettingsVBoxVisibleProperty(boolean visible) {
            if (visible) {
                minimalistPageContentContainer.contentSettingsLabel.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> minimalistPageContentContainer.contentSettingsVBox.setVisible(true));
            } else {
                minimalistPageContentContainer.contentSettingsLabel.getStyleClass().remove(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> minimalistPageContentContainer.contentSettingsVBox.setVisible(false));
            }
        }

        private void contentShowCaseVBox() {
            contentShowCaseVBoxVisibleProperty(!minimalistPageContentContainer.contentShowCaseVBox.isVisible());
        }

        private void contentShowCaseVBoxVisibleProperty(boolean visible) {
            if (visible) {
                minimalistPageContentContainer.contentModeLabel.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> minimalistPageContentContainer.contentShowCaseVBox.setVisible(true));
            } else {
                minimalistPageContentContainer.contentModeLabel.getStyleClass().remove(layoutPosition.contentViewModeIconClickEffectHex());
                Platform.runLater(() -> minimalistPageContentContainer.contentShowCaseVBox.setVisible(false));
            }
        }

        private void textAreaSettingsVBox() {
            textAreaSettingsVBoxVisibleProperty();
        }

        private void textAreaSettingsVBoxVisibleProperty() {
            if (textAreaContainerBorderPane.getRight() == null) {
                textAreaSettingsLabel.getStyleClass().add("vboxClickEffect");
                Platform.runLater(() -> {
                    //                    textAreaSettingsVBox.setVisible(true);
                    //                    textAreaSettingsScrollPane.setVisible(true);
                    textAreaContainerBorderPane.setRight(textAreaSettingsScrollPane);
                });
            } else {
                textAreaSettingsLabel.getStyleClass().remove("vboxClickEffect");
                Platform.runLater(() -> {
                    //                    textAreaSettingsVBox.setVisible(false);
                    //                    textAreaSettingsScrollPane.setVisible(false);
                    textAreaContainerBorderPane.setRight(null);

                });
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

        private void showProgressing(String text, boolean visible, double progressValue) {

            if (visible) {
                textAreaContainerBorderPane.setTop(progressBarHBox);
                //            progressBarHBox.setVisible(visible);
                progressLabel.setText(text);
                progressBar.setProgress(progressValue);

            } else {
                textAreaContainerBorderPane.setTop(null);
            }


        }

        private HBox buildSuggestResultHBox(String resultText, String fileName) {
            HBox hBox = new HBox();
            hBox.setPrefSize(146, 28);
            hBox.setPadding(new Insets(0, 0, 0, 10));
            hBox.setMinHeight(28);
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setStyle("-fx-background-color: #F9F8FD;");
            hBox.getStyleClass().addAll("topRightBottomLeftBorder", "f2f2f9Hover", "opacityHover");

            Label iconLabel = new Label("\ue892");
            iconLabel.setPrefSize(28, 29);
            iconLabel.setAlignment(Pos.CENTER);
            iconLabel.setDisable(false);
//            iconLabel.setStyle("-fx-text-fill:  #A7B0F5;");
            iconLabel.setStyle("-fx-text-fill:  linear-gradient(to right top, #7162fc, #7d5efb, #895afa, #9456f8, #9e51f6, #a44ef5, #a94bf4, #af47f3, #b146f3, #b345f3, #b443f2, #b642f2);");
            iconLabel.getStyleClass().addAll("icon-fill", "font-17");

            Label resultTextLabel = new Label(FontRepair.fixmyanamrfont(resultText.trim()));
            resultTextLabel.setPrefSize(251, 17);
            resultTextLabel.setAlignment(Pos.CENTER_LEFT);
//            resultTextLabel.setStyle("-fx-text-fill:  #A7B0F5;");
            resultTextLabel.setStyle("-fx-text-fill:  linear-gradient(to right top, #7162fc, #7d5efb, #895afa, #9456f8, #9e51f6, #a44ef5, #a94bf4, #af47f3, #b146f3, #b345f3, #b443f2, #b642f2);");
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

            hBox.getChildren().addAll(iconLabel, resultTextLabel, fileNameLabelContainerHBox);

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

//        private VBox buildControlShortcutFilePathHBox(ControlShortcut shortcut, String absolutePath, boolean labelDisable) {
//            String pattern = Pattern.quote(System.getProperty("file.separator"));
//            String[] splittedFileName = absolutePath.split(pattern);
//            HBox topHBox = new HBox();
//            topHBox.setCursor(Cursor.HAND);
//            topHBox.setPrefWidth(305);
//            //                topHBox.setPrefHeight(80);
//            topHBox.setAlignment(Pos.CENTER_LEFT);
//            topHBox.setPadding(new Insets(5, 10, 5, 10));
//            topHBox.setStyle("-fx-background-color:  #F9F8FD;");
//            topHBox.getStyleClass().add("topRightBottomLeftBorder");
//            topHBox.setOnMouseClicked(e -> {
//                if (e.getButton().equals(MouseButton.PRIMARY)) {
//                    chooseNote(absolutePath);
//                }
//            });
//            HBox labelContainerHBox = new HBox();
//            labelContainerHBox.setSpacing(0);
//            labelContainerHBox.setAlignment(Pos.CENTER_LEFT);
//            labelContainerHBox.setPadding(new Insets(5, 5, 5, 10));
//
//            HBox bottomHBox = new HBox();
//            bottomHBox.setPrefWidth(305);
//            //                bottomHBox.setPrefHeight(40);
//            bottomHBox.setAlignment(Pos.CENTER_LEFT);
//            bottomHBox.setPadding(new Insets(5, 10, 5, 5));
//            bottomHBox.setStyle("-fx-background-color:  #F9F8FD;");
//            bottomHBox.getStyleClass().add("topRightBottomLeftBorder");
//
//            HBox bottomIconContainerHBox = new HBox();
//            bottomIconContainerHBox.setSpacing(10);
//            bottomIconContainerHBox.setAlignment(Pos.CENTER_LEFT);
//            bottomIconContainerHBox.setPadding(new Insets(5, 5, 5, 5));
//
//            VBox outermostVBox = new VBox();
//            outermostVBox.setAlignment(Pos.CENTER_LEFT);
//
//
//            for (String splitValue : splittedFileName) {
//
//                Label label = new Label(FontRepair.fixmyanamrfont(splitValue.trim()));
//                label.setCursor(Cursor.HAND);
//                label.setStyle("-fx-text-fill: #9286DF ;");
//
//                label.getStyleClass().addAll("fontPyidaungsu", "opacityHover");
//                label.setOnMouseClicked(e -> {
//
//                    int arrowIndex = labelContainerHBox.getChildren().indexOf(label) + 2;
//                    String[] split = absolutePath.split("\\\\");
//                    ArrayList<String> arrayList = new ArrayList<>();
//                    for (int j = 0; j < arrowIndex / 2; j++) {
//                        //                            System.out.print(split[j]);
//                        String filePath = split[j] + "\\";
//                        arrayList.add(filePath);
//                        //                            System.out.print(filePath);
//                    }
//                    String listString = arrayList.stream().map(Object::toString)
//                            .collect(Collectors.joining(""));
//                    System.out.println(listString);
//                    try {
//                        Desktop.getDesktop().open(new File(listString));
//                    } catch (IOException ioException) {
//                        ioException.printStackTrace();
//                    }
//                });
//                labelContainerHBox.getChildren().add(label);
//                Label arrowIconLabel = new Label("\ue5e1");
//                arrowIconLabel.setCursor(Cursor.HAND);
//                arrowIconLabel.setStyle("-fx-text-fill:  #EA4C89 ;");
//                arrowIconLabel.getStyleClass().addAll("icon-fill", "font12", "opacityHover");
//                arrowIconLabel.setOnMouseClicked(e -> {
//
//                    int arrowIndex = labelContainerHBox.getChildren().indexOf(arrowIconLabel) + 1;
//                    String[] split = absolutePath.split("\\\\");
//
//                    ArrayList<String> arrayList = new ArrayList<>();
//                    for (int j = 0; j < arrowIndex / 2; j++) {
//
//                        String filePath = split[j] + "\\";
//                        arrayList.add(filePath);
//
//                    }
//
//                    String listString = arrayList.stream().map(Object::toString)
//                            .collect(Collectors.joining(""));
//                    System.out.println(listString);
//                    try {
//                        Desktop.getDesktop().open(new File(listString));
//                    } catch (IOException ioException) {
//                        ioException.printStackTrace();
//                    }
//                });
//
//                labelContainerHBox.getChildren().add(arrowIconLabel);
//            }
//
//            Label shortcutIconLabel = new Label("\uEF56");
//            shortcutIconLabel.setStyle("-fx-text-fill: #9286DF;");
//            shortcutIconLabel.getStyleClass().addAll("icon-fill", "font12");
//
//            Label shortcutLabel = new Label(shortcut + "");
//            shortcutLabel.setCursor(Cursor.HAND);
//            shortcutLabel.setTooltip(new Tooltip(shortcut + " " + absolutePath));
//            shortcutLabel.setStyle("-fx-text-fill: #9286DF ;");
//
//            HBox h = new HBox(shortcutIconLabel, shortcutLabel);
//            h.setAlignment(Pos.CENTER_LEFT);
//            h.setSpacing(10);
//
//            topHBox.getChildren().addAll(h, labelContainerHBox);
//
//            Label deleteIconLabel = new Label("\ue92e");
//            deleteIconLabel.setStyle("-fx-text-fill: #EA4C89;");
//            deleteIconLabel.getStyleClass().addAll("icon-fill", "font13");
//            Label deleteLabel = new Label("Delete");
//            deleteLabel.setStyle("-fx-text-fill: #EA4C89;");
//            deleteLabel.getStyleClass().addAll("font12");
//            HBox deleteIconHbox = new HBox(deleteIconLabel, deleteLabel);
//            deleteIconHbox.setSpacing(5);
//            deleteIconHbox.setCursor(Cursor.HAND);
//            deleteIconHbox.setAlignment(Pos.CENTER_LEFT);
//            deleteIconHbox.getStyleClass().add("opacityHover");
//            deleteIconHbox.setOnMouseClicked(e -> {
//                if (e.getButton().equals(MouseButton.PRIMARY)) {
//                    if (deleteLabel.getText().equals("Confirm?")) {
//                        deleteNote(absolutePath);
//                    } else {
//                        deleteLabel.setText("Confirm?");
//                    }
//                }
//            });
//
//
//            Label copyIconLabel = new Label("\ue14d");
//            copyIconLabel.setStyle("-fx-text-fill: #9286DF;");
//            copyIconLabel.getStyleClass().addAll("icon-fill", "font13");
//            Label copyLabel = new Label("Copy");
//            copyLabel.setStyle("-fx-text-fill: #9286DF;");
//            copyLabel.getStyleClass().addAll("font12");
//            HBox copyIconHbox = new HBox(copyIconLabel, copyLabel);
//            copyIconHbox.setSpacing(5);
//            copyIconHbox.setCursor(Cursor.HAND);
//            copyIconHbox.setAlignment(Pos.CENTER_LEFT);
//            copyIconHbox.getStyleClass().add("opacityHover");
//            copyIconHbox.setOnMouseClicked(e -> {
//                if (e.getButton().equals(MouseButton.PRIMARY)) {
//
//                    try {
//                        TextObject to = FileHandlings.buildObject(absolutePath);
//                        layoutPosition.copy(to.getText());
//                    } catch (IOException ioException) {
//                        ioException.printStackTrace();
//                    }
//
//                    copyLabel.setText("Copied");
//                    Platform.runLater(() -> textArea.selectAll());
//                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
//                    pauseTransition.setOnFinished(e1 -> {
//                                copyLabel.setText("Copy");
//                                textArea.deselect();
//                            }
//                    );
//                    pauseTransition.play();
//
//                }
//            });
//
//
//            bottomIconContainerHBox.getChildren().addAll(deleteIconHbox, copyIconHbox);
//            bottomHBox.getChildren().add(bottomIconContainerHBox);
//            outermostVBox.getChildren().add(topHBox);
//
//
////            outermostVBox.getChildren().add(bottomHBox);
//
//
//            return outermostVBox;
//        }

        private HBox buildFilePathHBox(String filePath, boolean labelDisable) {
            String pattern = Pattern.quote(System.getProperty("file.separator"));
            String[] splittedFileName = filePath.split(pattern);
            HBox hBox = new HBox();
            hBox.setCursor(Cursor.HAND);
//            hBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
            hBox.setPrefWidth(305);
            hBox.setMinWidth(305);
            hBox.setMaxHeight(30);

            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(2);
            hBox.setPadding(new Insets(5, 10, 5, 10));
            hBox.setStyle("-fx-background-color:  #F9F8FD;" + "-fx-border-color: #D3D6D8;");
            hBox.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {

                    chooseNote(filePath);

                }
            });


            for (String splitValue : splittedFileName) {

                Label label = new Label(FontRepair.fixmyanamrfont(splitValue.trim()));
                label.setTooltip(new Tooltip(filePath));
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
                    String[] split = absoluteFilePath.split("\\\\");

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

        private void textAreaNewWindow() {
            Stage stage = new Stage();

            WindowTextArea controller = new WindowTextArea(textArea.getText().trim());
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("WindowTextArea", controller);


            Scene scene = new Scene(parent);
            scene.getStylesheets().add("com/notespace/Minimalist/MinimalistPage.css");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit - Note");
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> {            //Save when exit

                controller.save();

                new AllRefreshServicesHandlerBuilder().service_contentService(true).service_suggestService(true).service_textAreaService(true).build();
            });

        }

        private void showFileNameDialog() {

            FileNameDialog controller = new FileNameDialog();
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("FileNameDialog", controller);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Create New Note");
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            controller.fileNameTextField.setOnKeyReleased(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    controller.OKButton.fire();
                }
                if (e.getCode() == KeyCode.ESCAPE) {
                    controller.CancelButton.fire();
                }
            });


            controller.OKButton.setOnAction(e -> {
                String fileName = controller.fileNameTextField.getText().trim();
                if (!fileName.isEmpty()) {

                    String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + fileName + ".txt";
                    boolean exist = FileHandlings.checkFileExist(filepath);
                    if (!exist) {
                        FileHandlings.createNewFile(filepath);

                        minimalistPageContentContainer.contentScrollPane.setVvalue(0);

                        stage.close();
                        absoluteFilePath = filepath;
                        absoluteFilePathPositionIndex = -1;
                        addTempFilePaths(filepath);
                        new AllRefreshServicesHandlerBuilder().service_textObjectList().service_textAreaService().service_contentService().service_logsService().service_suggestService().service_editHistoryService().build();
                    } else {
                        controller.makeDuplicateFileNameWarning();
                    }

                } else {      //fileName empty
                    controller.makeGiveFileNameNoti();
                }

            });
            controller.CancelButton.setOnAction(e -> stage.close());
            stage.setScene(scene);
            stage.show();

        }

        private void showFileNameDialogWithTextArea() {

            FileNameDialogWithText controller = new FileNameDialogWithText();
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("FileNameDialogWithText", controller);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Create New Note");
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            controller.fileNameTextField.setOnKeyReleased(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    controller.OKButton.fire();
                }
                if (e.getCode() == KeyCode.ESCAPE) {
                    controller.CancelButton.fire();
                }
            });

            controller.noteTextArea.setOnKeyReleased(e -> {
                if (e.getCode() == KeyCode.ENTER && e.isControlDown()) {
                    controller.OKButton.fire();
                }
                if (e.getCode() == KeyCode.ESCAPE) {
                    controller.CancelButton.fire();
                }
            });


            controller.OKButton.setOnAction(e -> {
                String fileName = controller.fileNameTextField.getText().trim();
                if (!fileName.isEmpty()) {

                    String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + fileName + ".txt";
                    boolean exist = FileHandlings.checkFileExist(filepath);
                    if (!exist) {
                        FileHandlings.createNewFile(filepath);

                        String noteText = controller.noteTextArea.getText().trim();
                        if (noteText != null) {
                            try {
                                FileHandlings.FileWrite(filepath, noteText);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }

                        minimalistPageContentContainer.contentScrollPane.setVvalue(0);
                        stage.close();
                        absoluteFilePath = filepath;
                        absoluteFilePathPositionIndex = -1;
                        addTempFilePaths(filepath);
                        new AllRefreshServicesHandlerBuilder().service_textObjectList().service_textAreaService().service_contentService().service_logsService().service_suggestService().service_editHistoryService().build();


                    } else {
                        controller.makeDuplicateFileNameWarning();
                    }

                } else {      //fileName empty
                    controller.makeGiveFileNameNoti();
                }

            });
            controller.CancelButton.setOnAction(e -> stage.close());
            stage.setScene(scene);
            stage.show();

        }

        private String showDirectoryChooseDialogAndGetPath() {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            String currentDirPath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
            directoryChooser.setInitialDirectory(new File(currentDirPath));
            File selectedDirectory = directoryChooser.showDialog(new Stage());

            return selectedDirectory.getAbsolutePath();
        }

        private void adjustMediaViewSize() {
            Platform.runLater(() -> {

                imageView.setFitWidth(imageViewAnchorPane.getWidth() + 250);
                imageView.setFitHeight(imageViewAnchorPane.getHeight() + 250);


            });
        }

        private void translateWidth(SplitPane node, double endValue) {
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
            KeyFrame endFrame = new KeyFrame(Duration.seconds(0.3), end, cache, cache2, cache3);

            Timeline timeline = new Timeline(startFrame, endFrame);
            timeline.setAutoReverse(true);
            timeline.setCycleCount(1);
            timeline.play();


        }

        private void makeDraggable(final Node node) {
            final DragContext dragContext = new DragContext();


            node.addEventFilter(
                    MouseEvent.MOUSE_RELEASED,
                    mouseEvent -> {
                        if (dragModeActiveProperty.get()) {

                            // disable mouse events for all children
                            mouseEvent.consume();
                        }
                    });


            node.addEventFilter(
                    MouseEvent.MOUSE_PRESSED,
                    mouseEvent -> {
                        if (dragModeActiveProperty.get()) {

                            // remember initial mouse cursor coordinates
                            // and node position
                            dragContext.mouseAnchorX = mouseEvent.getX();
                            dragContext.mouseAnchorY = mouseEvent.getY();
                            dragContext.initialTranslateX =
                                    node.getTranslateX();
                            dragContext.initialTranslateY =
                                    node.getTranslateY();
                        }
                    });

            node.addEventFilter(
                    MouseEvent.MOUSE_DRAGGED,
                    mouseEvent -> {
                        if (dragModeActiveProperty.get()) {

                            node.setTranslateX(mouseEvent.getX() + node.getTranslateX() - 120);
                            node.setTranslateY(mouseEvent.getY() + node.getTranslateY() - 50);
                        }
                    });


        }

        public char[] getCharacters(String text) {
            char[] words;
            words = text.toCharArray();
            return words;
        }

        public List<String> getWords(String text) {
            System.out.println("change here");
            List<String> wordList = new ArrayList<>();
            if (text != null && text.isEmpty() == false) {
                BreakIterator breakIterator = BreakIterator.getWordInstance();
                breakIterator.setText(text);
                int lastIndex = breakIterator.first();
                while (BreakIterator.DONE != lastIndex) {
                    int firstIndex = lastIndex;
                    lastIndex = breakIterator.next();
                    if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
                        wordList.add(text.substring(firstIndex, lastIndex));
                    }
                }
            }
            return wordList;
        }

        public List<String> getLineObjs(String absolutePath) {
            List<String> lineObjs = new ArrayList<>();
            if (absolutePath == null) return null;
            try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();


                while (line != null) {

                    lineObjs.add(line);
                    sb.append(line);
                    //		    sb.append ( System.lineSeparator ( ) );
                    line = br.readLine();
                    if (sb.toString() != null) {
                        //                    data = sb.toString();
                        File file = new File(absolutePath);
                        Scanner sc = new Scanner(file);
                        // we just need to use \\Z as delimiter
                        sc.useDelimiter("\\Z");

                        sc.close();
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lineObjs;
        }

        public void bindWords() {
            {

                minimalistPageMatchCaseHighlight.matchCaseFlowPane.getChildren().clear();
                if (textArea.getText() != null) {

                    if (matchCaseContainer.mode.equals(MatchCaseMode.WORDS)) {
                        layoutPosition.clearMatchCaseContainerHighlightModeEffect();
                        minimalistPageMatchCaseHighlight.matchCaseWordOptLabel.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());
                        matchCaseArrayList.clear();

                        System.out.println("Text area text " + textArea.getText().trim());
                        getWords(textArea.getText().trim()).forEach(text -> {            //Search Words
//                    FileHandlings.getLineWordMap().entrySet().forEach(e->{
//                    FileHandlings.getLineObjMap().entrySet().forEach(e -> {
                            if (text != null) {
                                Label label = new Label();
                                label.setText(FontRepair.fixmyanamrfont(text));
                                label.setUserData(text);
                                label.setPadding(new Insets(5));
                                label.getStyleClass().addAll("fontPyidaungsu", "font12");
                                minimalistPageMatchCaseHighlight.matchCaseFlowPane.getChildren().add(label);


                                matchCaseArrayList.add(label);
                            }
                        });

                    } else {

                        layoutPosition.clearMatchCaseContainerHighlightModeEffect();
                        minimalistPageMatchCaseHighlight.matchCaseCharOptLabel.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());
                        matchCaseArrayList.clear();
                        for (char c : getCharacters(textArea.getText().trim())) {            //Search Characters
                            Label label = new Label();
                            label.setText(FontRepair.fixmyanamrfont(c + "".trim()));

                            label.setUserData(c + "");
                            label.setPadding(new Insets(5));
                            label.getStyleClass().addAll("fontPyidaungsu", "font12");
                            minimalistPageMatchCaseHighlight.matchCaseFlowPane.getChildren().add(label);

                            matchCaseArrayList.add(label);
                        }
                    }

                }
            }
        }

        public void searchAndHighLight() {
            foundCount = 0;


            matchCaseArrayList.forEach(label -> {
                foundCount = 0;
                label.getStyleClass().removeAll(layoutPosition.highlightYellowClickEffectHex(), layoutPosition.highlightYellowTextEffect(), layoutPosition.highlightYellowBorderEffect());
            });

            matchCaseArrayList.forEach(label -> {
                String value = minimalistPageMatchCaseHighlight.matchCaseTextField.getText().trim();

                if (label.getUserData().toString().toLowerCase().contains(value.toLowerCase())) {
                    foundCount++;
                    label.getStyleClass().addAll(layoutPosition.highlightYellowClickEffectHex(), layoutPosition.highlightYellowTextEffect(), layoutPosition.highlightYellowBorderEffect());
                }
            });
            minimalistPageMatchCaseHighlight.noOfFoundLabel.setText(foundCount + "");

            minimalistPageMatchCaseHighlight.noOfFoundLabel.setText(foundCount + "");
            String noGrammarText = foundCount > 1 ? "places" : "place";
            minimalistPageMatchCaseHighlight.noGrammarLabel.setText(noGrammarText);


        }

        public void bindLineObj() {
            minimalistPageLineCase.lineObjReceiveVBox.getChildren().clear();

            lineCaseArrayList.clear();
//            getWords(textArea.getText().trim()).forEach(text -> {            //Search Words
            //                    FileHandlings.getLineWordMap().entrySet().forEach(e->{
//            FileHandlings.getLineObjMap().entrySet().forEach(e -> {
            lineNumber = 1;
            getLineObjs(absoluteFilePath).forEach(e -> {
                if (e != null) {


                    Label lineNumberLabel = new Label(lineNumber + "   ");
                    lineNumberLabel.setMinWidth(lineNumberLabel.getWidth());
                    lineNumberLabel.getStyleClass().addAll("fontConsolas", "font11");

                    HBox textLabelContainerHbox = new HBox();
                    textLabelContainerHbox.setAlignment(Pos.CENTER_LEFT);
                    textLabelContainerHbox.getChildren().clear();
                    getWords(e).forEach(text -> {

                        Label textLabel = new Label();
                        textLabel.setMinWidth(textLabel.getWidth());
                        textLabel.setText(FontRepair.fixmyanamrfont(text));
                        textLabel.setWrapText(true);
                        textLabel.setPadding(new Insets(5));
                        textLabel.getStyleClass().addAll("fontPyidaungsu", "font12");

                        textLabelContainerHbox.getChildren().add(textLabel);
                    });


                    Label notiIconLabel = new Label();
                    notiIconLabel.setMinWidth(notiIconLabel.getWidth());
                    notiIconLabel.setText("\ue0f0");
                    notiIconLabel.getStyleClass().addAll("icon-fill", "font12");
                    notiIconLabel.setStyle("-fx-text-fill: #F0A732;");


                    HBox hb = new HBox(lineNumberLabel, notiIconLabel, textLabelContainerHbox);
//                    hb.setMinWidth(hb.getWidth());
                    hb.setUserData(e.trim());
                    hb.setAlignment(Pos.CENTER_LEFT);
                    hb.setSpacing(5);
                    hb.setPadding(new Insets(3, 10, 3, 10));

                    minimalistPageLineCase.lineObjReceiveVBox.getChildren().add(hb);
                    lineCaseArrayList.add(hb);

                    lineNumber++;
                }
            });
        }

        public void searchLineObjHighlight() {
            ArrayList<Integer> foundLineNumbers = new ArrayList<>();
            foundCount1 = 0;

            lineCaseArrayList.forEach(hb -> {
                foundCount1 = 0;
                foundLineNumbers.clear();

                hb.getStyleClass().removeAll(layoutPosition.highlightYellowClickEffectHex(), layoutPosition.highlightYellowTextEffect(), layoutPosition.highlightYellowBorderEffect());
//                Label getNotiIconLabel = (Label) hb.getChildren().get(1);
//                Label getTextLabel = (Label) hb.getChildren().get(2);
//                getNotiIconLabel.setVisible(false);
//
//                Arrays.stream(new Label[]{getTextLabel}).forEach(label -> {
//                    label.getStyleClass().removeAll(layoutPosition.highlightYellowClickEffectHex(), layoutPosition.highlightYellowTextEffect());
//
//                });
            });

            lineCaseArrayList.forEach(hb -> {
                String value = minimalistPageLineCase.lineCaseTextField.getText().trim();
                if (hb.getUserData().toString().toLowerCase().contains(value.toLowerCase())) {
                    System.out.println(minimalistPageLineCase.lineObjReceiveVBox.getChildren().indexOf(hb));
//                    System.out.println();
                    foundCount1++;
                    foundLineNumbers.add(minimalistPageLineCase.lineObjReceiveVBox.getChildren().indexOf(hb) + 1); //index change

                    hb.getStyleClass().addAll(layoutPosition.highlightYellowClickEffectHex(), layoutPosition.highlightYellowTextEffect(), layoutPosition.highlightYellowBorderEffect());
//                    Label getNotiIconLabel = (Label) hb.getChildren().get(1);
//                    Label getTextLabel = (Label) hb.getChildren().get(2);
//                    getNotiIconLabel.setVisible(true);
//
//                    Arrays.stream(new Label[]{getTextLabel}).forEach(label -> {
//                        label.getStyleClass().addAll(layoutPosition.highlightYellowClickEffectHex(), layoutPosition.highlightYellowTextEffect());
//
//                    });
                }
            });
            minimalistPageLineCase.noOfFoundLabel.setText(foundCount1 + "");
            String noGrammarText = foundCount1 > 1 ? "places" : "place";
            minimalistPageLineCase.noGrammarLabel.setText(noGrammarText);

            minimalistPageLineCase.lineNumberShowVBox.getChildren().clear();
            if (!minimalistPageLineCase.lineCaseTextField.getText().isEmpty()) {
                foundLineNumbers.forEach(i -> {
                    Label lineLabel = new Label(String.format("line %s", i));
                    lineLabel.getStyleClass().addAll("fontConsolas", "font12", layoutPosition.highlightYellowTextEffect());

                    HBox hb = new HBox(lineLabel);
                    hb.setAlignment(Pos.CENTER_LEFT);
                    hb.setPadding(new Insets(5));
                    hb.setMinHeight(22);

                    minimalistPageLineCase.lineNumberShowVBox.getChildren().add(hb);
                });
            } else {

                minimalistPageLineCase.lineNumberShowVBox.getChildren().clear();
            }
        }


        private void restoreDefaultLayouts() {
            contentContainer.dockContainer();
            searchBar.dockSearchBar();
//            minimalistSettings.dockMinimalistSettings();
        }

        private void DirectoryMissingBlock() {      //conditions whether folder is missing or incorrect struct
            if (NoteSpacePathStorage.CheckNoteSpacePathExists()) {        //folder exists

                if (!BuildNoteSpace.CheckNoteSpaceFormat()) {
                    BuildNoteSpace.BuildSpace(NoteSpacePathStorage.getNoteSpacePathObj().spacePath);
                }
            } else {
                NoteSpacePathStorage.SaveNoteSpacePath(new NoteSpacePath("", "D://NoteSpaceHere//T2"));


//                Alert alert = new Alert(Alert.AlertType.ERROR, "Current NoteSpace missing", ButtonType.OK);
//                alert.showAndWait();
//                alert.setOnCloseRequest(e -> {
//                    NoteSpacePathStorage.SaveNoteSpacePath(new NoteSpacePath("", "D://NoteSpaceHere//T2"));
//                });
                //Go to choose UI
//                directoryChoosePanFlat.dirMissingPanFlat();

            }

        }

        private void openSettings() {
            minimalistSettings.dockMinimalistSettings();
            //        minimalistSettings.dockMinimalisSettingsExistControl();
            layoutPosition.stackLayoutVisiblePriority(settingsAnchorPane);
        }

        private void openDirectoryChoose() {
            directoryChoosePanFlat.dockContainer();
        }

        private void openHome() {
            saveNote();
            layoutPosition.stackLayoutVisiblePriority(homeAnchorPane);
            layoutPosition.stackLayoutVisiblePriority_HomeAnchorPane(homePageAnchorPane);
            absoluteFilePath = null;
            searchResults("");


        }

        private void openLogs() {
//            logsService.restart();
            new AllRefreshServicesHandlerBuilder().service_logsService().build();
            layoutPosition.stackLayoutVisiblePriority(homeAnchorPane);
            layoutPosition.stackLayoutVisiblePriority_HomeAnchorPane(logsAnchorPane);
        }

        private void openBackup() {
//            backupService.restart();
            new AllRefreshServicesHandlerBuilder().service_backupService().build();
            layoutPosition.stackLayoutVisiblePriority(homeAnchorPane);
            layoutPosition.stackLayoutVisiblePriority_HomeAnchorPane(backupAnchorPane);
        }

        private void openEditHistory() {
            layoutPosition.editHistoryBox();
        }

        private void openShortcutsSettingSection() {
            Shortcuts_Section shortcuts_section = new Shortcuts_Section();
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("Shortcuts_Section", shortcuts_section);

            Scene scene = new Scene(parent);

            reopenStageForShortcusSection = new Stage();
            reopenStageForShortcusSection.setScene(scene);
            reopenStageForShortcusSection.setTitle("Shortcuts");
            reopenStageForShortcusSection.show();

        }

        private void openNotePad() {
            showProgressing("opening notepad..", true, -1);
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(e -> {

                try {
                    Desktop.getDesktop().open(new File(absoluteFilePath));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            pauseTransition.play();
            PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(0.3));
            pauseTransition1.setOnFinished(e -> showProgressing("opened..", false, 1));
            pauseTransition1.play();

        }

        private void openRecentlyInnerBorder() {
            Node topComponent = innerBorderPane.getTop();
            if (topComponent == null) {
                innerBorderPane.setTop(recentNoteResultScrollPane1);
            } else {
                innerBorderPane.setTop(null);
            }
        }

        private void openRecently() {
            Node topComponent = textAreaBorderPane.getTop();
            if (topComponent == null) {
                textAreaBorderPane.setTop(recentNoteResultScrollPane);
//                innerBorderPane.setTop(recentNoteResultScrollPane1);

            } else {
                textAreaBorderPane.setTop(null);
//                innerBorderPane.setTop(null);
            }
        }


        private void stackLayoutVisiblePriority(Pane visiblePane) {
            Arrays.stream(new Pane[]{textAreaContainerAnchorPane, settingsAnchorPane, homeAnchorPane, fileNotExistAnchorPane}).forEach(pane -> pane.setVisible(pane.equals(visiblePane)));
        }

        private void stackLayoutVisiblePriority_HomeAnchorPane(Pane visiblePane) {
            Arrays.stream(new Pane[]{homePageAnchorPane, directoryChooseAnchorPane, logsAnchorPane, backupAnchorPane}).forEach(pane -> pane.setVisible(pane.equals(visiblePane)));
        }

        private void translateHeight(ScrollPane node, double endValue) {
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


        }

        private void slowScrollToTop(ScrollPane scrollPane) {
            scrollPane.setCache(true);
            scrollPane.setCacheHint(CacheHint.QUALITY);
            scrollPane.setCacheHint(CacheHint.SPEED);
            scrollPane.setCacheHint(CacheHint.SCALE);

            Animation animation = new Timeline(
                    new KeyFrame(Duration.seconds(0.5),
                            new KeyValue(scrollPane.vvalueProperty(), 0),
                            new KeyValue(scrollPane.cacheHintProperty(), CacheHint.QUALITY)));
            animation.play();


        }

        private void controlShortcutNoValueSpecifyWarning(ControlShortcut shortcut) {
            controlShortcutValueSpecifyLabel.setText(String.format("No value specify for %s", shortcut + ""));
            controlShortcutValueSpecifyAnchorPane.setVisible(true);
            PauseTransition pauseForCloseTransition = new PauseTransition(Duration.seconds(2));
            pauseForCloseTransition.setOnFinished(e -> controlShortcutValueSpecifyAnchorPane.setVisible(false));
            pauseForCloseTransition.play();
        }

        private void openControlShortcutDialog() {
            ControlShortcutDialog controller = new ControlShortcutDialog();
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("ControlShortcutDialog", controller);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Shortcut Dialog");
            stage.setScene(scene);
            stage.show();
        }


    }      //LayoutPosition

    class HomePageControlClass {


        public HomePageControlClass() {

            Platform.runLater(() -> {
                String DirPath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
                directoryNameTextField.setText(DirPath);
            });

            HomePageControlClassActions();
        }

        public void HomePageControlClassActions() {
            //run
            Platform.runLater(() -> {


                shortcutDialogIconLabel.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.openControlShortcutDialog();
                    }
                });


                directoryNameTextField.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        try {
                            Desktop.getDesktop().open(new File(directoryNameTextField.getText().trim()));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });

                backupSearchTextField.textProperty().addListener((ods, oldText, newText) -> {
                    if (newText != null) {
                        backupService._searchBackupResults(newText);
                    }
                });


                closeEditHistoryAnchorPaneLabel.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.editHistoryBoxVisibleProperty(false);
                    }
                });


                fileNameTextField.textProperty().addListener((obs, oldText, newText) -> {
                    String fileName = fileNameTextField.getText().trim();
                    String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + fileName + ".txt";
                    boolean exist = FileHandlings.checkFileExist(filepath);
                    if (exist) {
                        duplicateWarningLabel.setVisible(true);
                        createNewFileLabel.setDisable(true);
                    } else {
                        duplicateWarningLabel.setVisible(false);
                        createNewFileLabel.setDisable(false);

                    }
                });
                fileNameTextField.setOnKeyReleased(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        String fileName = fileNameTextField.getText().trim();
                        if (!fileName.isEmpty()) {
                            String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + fileName + ".txt";
                            boolean exist = FileHandlings.checkFileExist(filepath);
                            if (!exist) {
                                FileHandlings.createNewFile(filepath);
                                fileNameTextField.clear();
                                minimalistPageContentContainer.contentScrollPane.setVvalue(0);
                                absoluteFilePath = filepath;
                                new AllRefreshServicesHandlerBuilder().service_textObjectList().service_textAreaService().service_contentService().service_logsService().service_suggestService().build();
                            }
                        }
                    }
                });

                createNewFileLabel.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        String fileName = fileNameTextField.getText().trim();
                        if (!fileName.isEmpty()) {
                            String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + fileName + ".txt";
                            boolean exist = FileHandlings.checkFileExist(filepath);
                            if (!exist) {
                                FileHandlings.createNewFile(filepath);
                                fileNameTextField.clear();
                                minimalistPageContentContainer.contentScrollPane.setVvalue(0);
                                absoluteFilePath = filepath;
                                new AllRefreshServicesHandlerBuilder().service_textObjectList().service_textAreaService().service_contentService().service_logsService().service_suggestService().build();
                            }
                        }
                    }
                });


                homePageScrollPane.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.translateHeight(homePageScrollPane, homePageAnchorPane.getHeight() - 20);
                    }
                });
                homePageBorderPane.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.slowScrollToTop(homePageScrollPane);
                        layoutPosition.translateHeight(homePageScrollPane, 80);
                    }
                });


                homePageScrollPane.vvalueProperty().addListener((ov, old_val, new_val) -> {
                    //		            fileName.setText(imageNames[(new_val.intValue() - 1)/100]);

                    double d = (double) new_val;
                    System.out.println(d);
                    if (d == 0) {
                        layoutPosition.translateHeight(homePageScrollPane, 80);
                    }
                    if (d > 0) {
                        layoutPosition.translateHeight(homePageScrollPane, homePageAnchorPane.getHeight() - 20);
                    }
                });

                directoryChooseLabel.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        String getDirPath = layoutPosition.showDirectoryChooseDialogAndGetPath();
                        BuildNoteSpace.BuildSpace(getDirPath);
                        NoteSpacePathStorage.SaveNoteSpacePath(new NoteSpacePath("", getDirPath));
                        directoryNameTextField.setText(getDirPath);

                        new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_panFlatRefreshService(true).service_textAreaService(true).build();
                    }
                });

                allUIRefreshLabel.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
//                            pageUiRefresh();

//                        new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_textAreaService(true).service_contentService(true).service_panFlatDetectService(true).service_panFlatRefreshService(true).service_logsService(true).build();
                        textObjectArrayList.clear();
                        new AllRefreshServicesHandlerBuilder().service_textObjectList().service_textAreaService().service_contentService().service_panFlatRefreshService().service_logsService().build();
                    }
                });

                goHomeLabel.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.openHome();
                    }
                });
                redirectHomePageLabel.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.openHome();
                    }
                });

                folderLocationBtn.setOnAction(e -> layoutPosition.openDirectoryChoose());
                logsPaneBtn.setOnAction(e -> layoutPosition.openLogs());
                backupPaneBtn.setOnAction(e -> layoutPosition.openBackup());

                deleteAllLogsLabel.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        LogsObjStorage.getLogsObjList().forEach(logsObj -> LogsObjStorage.delete_logsObj(logsObj.getId()));
                        new AllRefreshServicesHandlerBuilder().service_logsService().build();
                    }
                });

                newNoteButton.setOnAction(e -> layoutPosition.showFileNameDialog());
                openHomeButton.setOnAction(e -> layoutPosition.openHome());
                openContentContainerButton.setOnAction(e -> contentContainer.dockContainerExistControl());
                openSearchBarButton.setOnAction(e -> searchBar.dockSearchBarExistControl());
                openControlShortcutContainerButton.setOnAction(e -> controlShortcutContainer.dockControlShortcutContainer());
                openSettingsButton.setOnAction(e -> layoutPosition.openSettings());
                openNoteFromDirButton.setOnAction(e -> chooseNoteFromDir());

                openDirectoryButton.setOnAction(e -> directoryChoosePanFlat.dockContainer());
                openLogsButton.setOnAction(e -> layoutPosition.openLogs());
                openBackupButton.setOnAction(e -> layoutPosition.openBackup());


                //                openSettingsButton.setOnAction(e->layoutPosition.openSettings());
//                openContentContainerButton.setOnAction(e->contentContainer.dockContainer());


                closeTextAreLabel.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.openHome();
                    }
                });

                textAreaSettingsLabel.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.textAreaSettingsVBox();
//                        if (textAreaContainerBorderPane.getRight() == null) {
//                            textAreaContainerBorderPane.setRight(null);
//                            textAreaContainerBorderPane.setRight(textAreaSettingsScrollPane);
//                        } else {
//                            textAreaContainerBorderPane.setRight(null);
//                        }


                    }
                });


                toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
                    RadioButton rd = (RadioButton) newVal;
                    int col = (int) rd.getUserData();
                    layoutPosition.columnAdjustSize(col);
                    //		System.out.println ( newVal.getClass ( ) );
                });

                layoutPosition.makeDraggable(suggestVBox);
                suggestDragHandlerHBox.setOnMouseClicked(e -> {
                    if (layoutPosition.dragModeActiveProperty.get()) {
                        layoutPosition.dragModeActiveProperty.set(false);
                        suggestVBox.setCursor(Cursor.DEFAULT);
                        suggestDragHandlerHBox.getStyleClass().remove(layoutPosition.suggestVBoxDragClickEffectHex());

                        suggestVBox.setOnMousePressed(event -> suggestVBox.setCursor(Cursor.DEFAULT));

                    } else {
                        layoutPosition.dragModeActiveProperty.set(true);
                        suggestVBox.setCursor(Cursor.OPEN_HAND);
                        suggestDragHandlerHBox.getStyleClass().add(layoutPosition.suggestVBoxDragClickEffectHex());

                        suggestVBox.setOnMousePressed(event -> suggestVBox.setCursor(Cursor.CLOSED_HAND));
                    }
                    suggestVBox.setOnMouseReleased(event -> suggestVBox.setCursor(Cursor.DEFAULT));
                });

                layoutPosition.makeDraggable(editHistoryAnchorPane);
                editHistoryDraghandlerHBox.setOnMouseClicked(e -> {
                    if (layoutPosition.dragModeActiveProperty.get()) {
                        layoutPosition.dragModeActiveProperty.set(false);
                        suggestVBox.setCursor(Cursor.DEFAULT);
                        editHistoryDraghandlerHBox.getStyleClass().remove(layoutPosition.suggestVBoxDragClickEffectHex());

                        editHistoryAnchorPane.setOnMousePressed(event -> editHistoryAnchorPane.setCursor(Cursor.DEFAULT));

                    } else {
                        layoutPosition.dragModeActiveProperty.set(true);
                        editHistoryAnchorPane.setCursor(Cursor.OPEN_HAND);
                        editHistoryDraghandlerHBox.getStyleClass().add(layoutPosition.suggestVBoxDragClickEffectHex());

                        editHistoryAnchorPane.setOnMousePressed(event -> editHistoryAnchorPane.setCursor(Cursor.CLOSED_HAND));
                    }
                    editHistoryAnchorPane.setOnMouseReleased(event -> editHistoryAnchorPane.setCursor(Cursor.DEFAULT));

                });

                textAreaNewWindowHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.textAreaNewWindow();
                    }
                });
                copyClipboardHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        if (!textArea.getText().trim().isEmpty()) {

                            String textForCopy = textArea.getText().trim();
                            layoutPosition.copy(textForCopy);
                            copyClipboardHBoxLabel.setText("Copied to Clipboard");
                            Platform.runLater(() -> textArea.selectAll());
                            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                            pauseTransition.setOnFinished(e1 -> {
                                        copyClipboardHBoxLabel.setText("Copy to Clipboard");
                                        textArea.deselect();
                                    }
                            );
                            pauseTransition.play();
                        } else {
                            copyClipboardHBox.setDisable(true);
                        }

                    }
                });
                suggestHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.suggestVBox();
                    }
                });
                lineCaseHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.lineCaseVBox();
                        layoutPosition.bindLineObj();
                        layoutPosition.searchLineObjHighlight();
                        //bindwords
                        //search and highlight

                    }
                });

                matchCaseHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.matchCaseVBox();
                        layoutPosition.bindWords();
                        layoutPosition.searchAndHighLight();
                    }
                });
                forceToSaveHBox.setOnMouseClicked(event -> {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (absoluteFilePath != null && textArea.getText() != null) {
                            layoutPosition.showProgressing("saving..", true, ProgressIndicator.INDETERMINATE_PROGRESS);
                            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.7));
                            pauseTransition.setOnFinished(e -> {
                                layoutPosition.showProgressing("", false, 0);
                                try {
                                    FileHandlings.FileWrite(absoluteFilePath, textArea.getText());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                saveEditHistory();
                                new AllRefreshServicesHandlerBuilder().service_contentService(true).service_textAreaService(true).service_editHistoryService(true).build();


                            });
                            pauseTransition.play();
                            PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(0.5));
                            pauseTransition1.setOnFinished(e -> layoutPosition.showProgressing("saved", true, 1));
                            pauseTransition1.play();

                        }

                    }
                });
                deleteHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {


                        if (deleteHBoxInsideLabel.getText().equals("Confirm?")) {
                            deleteNote();
                        } else {
                            deleteHBoxInsideLabel.setText("Confirm?");
                        }

                        deleteHBoxInsideLabel.setOnMouseExited(mouseExit -> deleteHBoxInsideLabel.setText("Delete Note"));

                    }
                });
                notePadHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.openNotePad();
                    }
                });
                recentlyOpenedHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.openRecently();
                    }
                });
                editHistoryHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.openEditHistory();
                    }
                });
                nextNoteHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        nextNote();
                    }
                });
                controlShortcutsHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        layoutPosition.openControlShortcutDialog();
                    }
                });


                previousNoteHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        previousNote();
                    }
                });

                undoHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        undo();
                    }
                });

                redoHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        redo();
                    }
                });

                minimalistPageAnchorPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                    Platform.runLater(() -> layoutPosition.adjustMediaViewSize());
                    //								backgroundImage.setFitHeight ( backgroundImage.getFitHeight () +Double.parseDouble ( newVal.toString () ));
                });

//                textArea.setOnMouseExited(e -> {            //textArea events
//                    saveNote();
//                });

                textArea.textProperty().addListener((ods, oldText, newText) -> {
                    if (newText != null) {
//                            saveNote();

                        RuntimeNoteTemp temp = new RuntimeNoteTemp(absoluteFilePath, newText);
                        RuntimeNoteTempStorage.addRuntimeTempObj(temp);


//                        RuntimeNoteTempStorage.getRunTimeTempObj_listByFileName(absoluteFilePath).forEach(runTimeTempObj -> {
//                            if (!runTimeTempObj.getText().trim().equals(newText) && runTimeTempObj.getText() != null) {
//                                RuntimeNoteTempStorage.addRuntimeTempObj(temp);
//                            }
//                        });

                    }
                });

                //lost focus		//textArea events
                textArea.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (newPropertyValue) {

                        textArea.setOnMouseMoved(event -> {
                            cursorX = (int) event.getX();
                            cursorY = (int) event.getY();

                        });
                    } //textarea focus

                });

                textArea.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {

                    if (textArea.getText() != null) {
                        String text = textArea.getText().substring(0, newPosition.intValue());
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
                            if (selectRangeAvailable) {
                                //                    textArea.selectRange(index, index++);            //selectRangeWord
                            } else {

                            }
//                                System.out.println("suffix " + suffix);

                            Scene scene = minimalistPageAnchorPane.getScene();
                            String finalPrefix = prefix;
                            int finalIndex = index;
                            scene.getAccelerators().put(
                                    new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN),     //Ctrl ENTER
                                    new Runnable() {
                                        @FXML
                                        public void run() {
                                            System.out.println(finalIndex);
                                            suggestContainer.loadDictionarySuggestions(finalPrefix);
                                        }
                                    }
                            );
                        }

                    }

                });            //textArea events

                textArea.setOnKeyReleased(e -> {        //textarea shortcuts
                    if (e.getCode() == KeyCode.ESCAPE) {
                        layoutPosition.openHome();
                    }
                    if (e.getCode() == KeyCode.F4 && e.isShiftDown()) {
                        layoutPosition.textAreaNewWindow();
                    }

                    if (e.getCode() == KeyCode.DOWN && e.isShiftDown()) {
                        undo();

                    }

                    if (e.getCode() == KeyCode.UP && e.isShiftDown()) {
                        redo();
                    }

                    if (textAreaCaretPermit) {

                        if (e.getCode() == KeyCode.RIGHT && e.isControlDown()) {
                            textArea.requestFocus();
                            textArea.end();
                        }

                        if (e.getCode() == KeyCode.LEFT && e.isControlDown()) {
                            textArea.requestFocus();
                            textArea.positionCaret(0);
                        }

                    }


//                    EventHandler<KeyEvent> tabListener = evt -> {
//                        if (evt.getCode() == KeyCode.RIGHT && evt.isControlDown()) {
//                            evt.consume();
//                            textArea.requestFocus();
//                            textArea.end();
//                        }
//                    };
//                    minimalistPageAnchorPane.addEventHandler(KeyEvent.ANY, tabListener);
//
//                    EventHandler<KeyEvent> tabListenerReverse = evt -> {
//                        if (evt.getCode() == KeyCode.LEFT && evt.isControlDown()) {
//                            evt.consume();
//                            textArea.requestFocus();
//                            textArea.positionCaret(0);
//                        }
//                    };
//                    minimalistPageAnchorPane.addEventHandler(KeyEvent.ANY, tabListenerReverse);
//
//                    EventHandler<KeyEvent> selectListener = evt -> {
//                        if (evt.getCode() == KeyCode.RIGHT && evt.isShiftDown()) {
//                            evt.consume();
//                            textArea.requestFocus();
//                            textArea.selectAll();
//                        }
//                    };
//                    minimalistPageAnchorPane.addEventHandler(KeyEvent.ANY, selectListener);


                });
            });

        }

    }

    class ContentContainer {
        ContentMode contentMode;
        boolean showFileNamePane = true;
        boolean moreOptionsPane = true;
        Parent parent;
        Stage newWindowStageForContainer;
        int colHBoxUserData = 1;
        int modeHboxUserData = 1;
        int modeIconUserData = 1;


        public ContentContainer() {
            loadContentContainer();


        }

        private void loadContentContainer() {
            minimalistPageContentContainer = new MinimalistPageContentContainer();
            parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageContentContainer", minimalistPageContentContainer);

            contentContainerActions();
        }

        private void contentContainerActions() {


            minimalistPageContentContainer.createNewNoteButton.setOnAction(e -> layoutPosition.showFileNameDialog());
            minimalistPageContentContainer.newNoteButton.setOnAction(e -> layoutPosition.showFileNameDialogWithTextArea());

            minimalistPageContentContainer.quickNoteIconLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    quickNote();
                }
            });

//            Test
            minimalistPageContentContainer.flatContentHBox.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    contentMode = ContentMode.Flat;
                    searchResults("");
                    layoutPosition.contentShowCaseVBoxVisibleProperty(false);
                }
            });
            minimalistPageContentContainer.boxContentHBox.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    contentMode = ContentMode.Box;
                    showFileNamePane = false;
                    searchResults("");
                    layoutPosition.contentShowCaseVBoxVisibleProperty(false);
                }
            });

            minimalistPageContentContainer.boxContentWithFileNameHBox.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    contentMode = ContentMode.Box;
                    showFileNamePane = true;
                    moreOptionsPane = false;

                    searchResults("");
                    layoutPosition.contentShowCaseVBoxVisibleProperty(false);
                }
            });
            minimalistPageContentContainer.boxContentWithFileNameOptionsHBox.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    contentMode = ContentMode.Box;
                    showFileNamePane = true;
                    moreOptionsPane = true;

                    searchResults("");
                    layoutPosition.contentShowCaseVBoxVisibleProperty(false);
                }
            });


//            Test


            minimalistPageContentContainer.closeContentContainerLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    contentContainer.hideContainer();
                }
            });

            minimalistPageContentContainer.contentModeLabel.setOnMouseClicked(e -> {        //ContentContainer
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    layoutPosition.contentShowCaseVBox();
                }
            });

            minimalistPageContentContainer.contentSettingsLabel.setOnMouseClicked(e -> {            //ContentContainer
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    layoutPosition.contentSettingsVBox();
                }
            });
            minimalistPageContentContainer.swipeRightHBox.setOnMouseClicked(e -> {            //ContentContainer
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    innerBorderPane.setLeft(null);
                    innerBorderPane.setRight(contentContainer.parent);
                }
            });
            minimalistPageContentContainer.swipeLeftHBox.setOnMouseClicked(e -> {            //ContentContainer
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    innerBorderPane.setRight(null);
                    innerBorderPane.setLeft(contentContainer.parent);
                }
            });

            minimalistPageContentContainer.scrollbarHBoxCheck.selectedProperty().addListener(            //ContentContainer
                    (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> layoutPosition.scrollBarVisibleProperty(new_val));

            minimalistPageContentContainer.scrollbarHBox.setOnMouseClicked(e -> {            //ContentContainer
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    layoutPosition.scrollBar();
                }
            });


            Arrays.stream(new HBox[]{minimalistPageContentContainer.floatModeHBox, minimalistPageContentContainer.windowModeHBox, minimalistPageContentContainer.dockModeHBox}).forEach(e -> {
                e.setUserData(modeHboxUserData);
                modeHboxUserData++;
            });


            Arrays.stream(new HBox[]{minimalistPageContentContainer.floatModeHBox, minimalistPageContentContainer.windowModeHBox, minimalistPageContentContainer.dockModeHBox}).forEach(e -> e.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    int index = (int) e.getUserData();
                    switch (index) {
                        case 1:
                            contentContainer.floatContainer();
                            break;
                        case 2:
                            contentContainer.windowContainer();
                            break;
                        case 3:
                            contentContainer.dockContainer();
                            break;
                        default:
                    }
                }
            }));

            Arrays.stream(new Label[]{minimalistPageContentContainer.floatModeIconLabel, minimalistPageContentContainer.windowModeIconLabel, minimalistPageContentContainer.dockModeIconLabel}).forEach(e -> {
                e.setUserData(modeIconUserData);
                modeIconUserData++;
            });

            Arrays.stream(new Label[]{minimalistPageContentContainer.floatModeIconLabel, minimalistPageContentContainer.windowModeIconLabel, minimalistPageContentContainer.dockModeIconLabel}).forEach(e -> e.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    int index = (int) e.getUserData();
                    switch (index) {
                        case 1:
                            contentContainer.floatContainer();
                            break;
                        case 2:
                            contentContainer.windowContainer();
                            break;
                        case 3:
                            contentContainer.dockContainer();
                            break;
                        default:
                    }
                }
            }));


            Arrays.stream(new HBox[]{minimalistPageContentContainer.onecolHBox, minimalistPageContentContainer.twocolHBox, minimalistPageContentContainer.threecolHBox}).forEach(e -> {
                e.setUserData(colHBoxUserData);
                colHBoxUserData++;
            });

            Arrays.stream(new HBox[]{minimalistPageContentContainer.onecolHBox, minimalistPageContentContainer.twocolHBox, minimalistPageContentContainer.threecolHBox}).forEach(hbox -> {
                hbox.setOnMouseClicked(event -> {            //ContentContainer
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        int col = (int) hbox.getUserData();
                        layoutPosition.columnAdjustSize(col);
                    }
                });
            });

        }      //contentContainerActions

        private void putLeft() {
//            innerBorderPane.setLeft(null);
//            innerBorderPane.setLeft(parent);

//            innerBorderPane.setLeft(null);
//            dockContainerSplitPane.getItems().add(parent);
//            innerBorderPane.setLeft(dockContainerSplitPane);

            innerBorderPane.setLeft(null);
            dockContainerSplitPane.getItems().clear();

            AnchorPane dockContainerAnchorPane = new AnchorPane();
            dockContainerAnchorPane.getChildren().clear();
            dockContainerAnchorPane.getChildren().add(parent);

            AnchorPane.setTopAnchor(parent, 0.0);
            AnchorPane.setRightAnchor(parent, 0.0);
            AnchorPane.setBottomAnchor(parent, 0.0);
            AnchorPane.setLeftAnchor(parent, 0.0);
            innerBorderPane.setLeft(dockContainerSplitPane);

            dockContainerSplitPane.getItems().add(dockContainerAnchorPane);
//            dockContainerSplitPane.getItems().add(new AnchorPane());


        }

        private void putRight() {
            innerBorderPane.setRight(null);
            innerBorderPane.setRight(parent);
        }

        private void dockContainerExistControl() {          //if left empty => dock
            if (innerBorderPane.getLeft() == null) {
                contentContainer.dockContainer();
            } else {
                innerBorderPane.setLeft(null);
            }
        }

        private void dockContainer() {
            layoutPosition.clearViewModeEffect();
            minimalistPageContentContainer.dockModeHBox.getStyleClass().add(layoutPosition.contentViewModeClickEffectHex());
            minimalistPageContentContainer.dockModeIconLabel.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());

            if (newWindowStageForContainer != null) {
                newWindowStageForContainer.close();
            }
            if (floatContentVBox.getChildren().size() != 0) {
                parent = (Parent) floatContentVBox.getChildren().get(0);
            }

            minimalistPageContentContainer.floatModeHBox.setDisable(false);
            minimalistPageContentContainer.windowModeHBox.setDisable(false);
            minimalistPageContentContainer.floatModeIconLabel.setDisable(false);
            minimalistPageContentContainer.windowModeIconLabel.setDisable(false);

            minimalistPageSettings.contentContainerFloatButton.setDisable(false);
            minimalistPageSettings.contentContainerWindowButton.setDisable(false);


            putLeft();
        }              //dockContainer method


        private void floatContainer() {


            layoutPosition.clearViewModeEffect();
            minimalistPageContentContainer.floatModeHBox.getStyleClass().add(layoutPosition.contentViewModeClickEffectHex());
            minimalistPageContentContainer.floatModeIconLabel.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());
            innerBorderPane.setLeft(null);
            innerBorderPane.setRight(null);


            floatContentVBox.getChildren().clear();
            floatContentVBox.setVisible(true);
            floatContentVBox.getChildren().add(parent);
            minimalistPageContentContainer.floatModeHBox.setDisable(true);
            minimalistPageContentContainer.windowModeHBox.setDisable(true);
            minimalistPageContentContainer.floatModeIconLabel.setDisable(true);
            minimalistPageContentContainer.windowModeIconLabel.setDisable(true);

            minimalistPageSettings.contentContainerFloatButton.setDisable(true);
            minimalistPageSettings.contentContainerWindowButton.setDisable(true);

        }            //floatContainer method

        private void windowContainer() {
            layoutPosition.clearViewModeEffect();
            minimalistPageContentContainer.windowModeHBox.getStyleClass().add(layoutPosition.contentViewModeClickEffectHex());
            minimalistPageContentContainer.windowModeIconLabel.getStyleClass().add(layoutPosition.contentViewModeIconClickEffectHex());
            innerBorderPane.setLeft(null);
            innerBorderPane.setRight(null);


            newWindowStageForContainer = new Stage();

            minimalistPageContentContainer.swipeLeftHBox.setDisable(true);
            minimalistPageContentContainer.swipeRightHBox.setDisable(true);
            minimalistPageContentContainer.floatModeHBox.setDisable(true);
            minimalistPageContentContainer.windowModeHBox.setDisable(true);
            minimalistPageContentContainer.floatModeIconLabel.setDisable(true);
            minimalistPageContentContainer.windowModeIconLabel.setDisable(true);

            minimalistPageSettings.contentContainerFloatButton.setDisable(true);
            minimalistPageSettings.contentContainerWindowButton.setDisable(true);

//            Scene scene = new Scene(parent);
            Scene scene = new Scene(dockContainerSplitPane);
            newWindowStageForContainer.setOnCloseRequest(e -> {
                minimalistPageContentContainer.swipeLeftHBox.setDisable(false);
                minimalistPageContentContainer.swipeRightHBox.setDisable(false);
                dockContainer();
            });
            newWindowStageForContainer.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    textObjectArrayList.clear();            //join condition with service
                    new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_textAreaService(true).service_contentService(true).service_panFlatDetectService(true).build();
                }  //				System.out.println ( "Stage focus lost" );
                //Save Actions

            });

            newWindowStageForContainer.initModality(Modality.WINDOW_MODAL);
            newWindowStageForContainer.setTitle("Note Contents");
            newWindowStageForContainer.initStyle(StageStyle.DECORATED);
            scene.getStylesheets().add("/com/notespace/Minimalist/MinimalistPage.css");
            scene.setFill(Color.TRANSPARENT);
            newWindowStageForContainer.setResizable(true);
            newWindowStageForContainer.setScene(scene);
            newWindowStageForContainer.show();

        }            //WindowContainer method

        private void hideContainer() {           //hideContainer method
            dockContainer();
            innerBorderPane.setLeft(null);
        }


        private void callContent(String absolutePath) {
            if (contentMode.equals(ContentMode.Flat)) {
                callContentFlatBox(absolutePath);
                minimalistPageContentContainer.contentModeLabel.setText("Flat Content");
                layoutPosition.clearContentShowCaseEffect();
                minimalistPageContentContainer.flatContentHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());
                minimalistPageSettings.flatContentHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());
            }
            if (contentMode.equals(ContentMode.Box)) {
                callContentBoxBox(absolutePath);
                minimalistPageContentContainer.contentModeLabel.setText("Box Content");
                layoutPosition.clearContentShowCaseEffect();

                if (contentContainer.showFileNamePane) {
                    minimalistPageContentContainer.boxContentWithFileNameHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());
                    minimalistPageSettings.boxContentWithFileNameHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());

                    if (contentContainer.moreOptionsPane) {
                        minimalistPageContentContainer.boxContentWithFileNameOptionsHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());
                        minimalistPageSettings.boxContentWithFileNameOptionsHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());
                    }

                } else {
                    minimalistPageContentContainer.boxContentHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());
                    minimalistPageSettings.boxContentHBox.getStyleClass().add(layoutPosition.contentColumnModeClickEffectHex());

                }


            }

        }


        private void callContentBoxBox(String absolutePath) {
            MinimalistPageContent controller = new MinimalistPageContent(absolutePath);
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageContent", controller);

            contentMap.put(controller, parent);

            controller.minimalistPageContentAnchorPane.setOnMouseClicked(event -> {      //Perform
                if (event.getButton().equals(MouseButton.PRIMARY)) {

                    removeEffect();
                    //		    controller.minimalistPageContentAnchorPane.getStyleClass ( ).add ( "cardClickEffect" );

                    chooseNote(controller.returnAbsolutePath());

                }
            });
            minimalistPageContentContainer.contentFlowPane.getChildren().add(parent);

            if (contentContainer.showFileNamePane) {
                String pattern = Pattern.quote(System.getProperty("file.separator"));
                String[] splittedFileName = absolutePath.split(pattern);
                HBox topHBox = new HBox();
                topHBox.setCursor(Cursor.HAND);
                topHBox.setPrefWidth(305);
//                topHBox.setPrefHeight(80);
                topHBox.setAlignment(Pos.CENTER_LEFT);
                topHBox.setPadding(new Insets(5, 10, 5, 10));
                topHBox.setStyle("-fx-background-color:  #F9F8FD;");
                topHBox.getStyleClass().add("topRightBottomLeftBorder");
                topHBox.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        chooseNote(absolutePath);
                    }
                });
                HBox labelContainerHBox = new HBox();
                labelContainerHBox.setSpacing(1);
                labelContainerHBox.setAlignment(Pos.CENTER_LEFT);
                labelContainerHBox.setPadding(new Insets(5, 5, 5, 10));


                HBox bottomHBox = new HBox();
                bottomHBox.setPrefWidth(305);
//                bottomHBox.setPrefHeight(40);
                bottomHBox.setAlignment(Pos.CENTER_LEFT);
                bottomHBox.setPadding(new Insets(5, 10, 5, 5));
                bottomHBox.setStyle("-fx-background-color:  #F9F8FD;");
                bottomHBox.getStyleClass().add("topRightBottomLeftBorder");

                HBox bottomIconContainerHBox = new HBox();
                bottomIconContainerHBox.setSpacing(10);
                bottomIconContainerHBox.setAlignment(Pos.CENTER_LEFT);
                bottomIconContainerHBox.setPadding(new Insets(5, 5, 5, 5));

                VBox outermostVBox = new VBox();
                outermostVBox.setAlignment(Pos.CENTER_LEFT);


                for (String splitValue : splittedFileName) {

                    Label label = new Label(FontRepair.fixmyanamrfont(splitValue.trim()));
                    label.setTooltip(new Tooltip(absolutePath));
                    label.setCursor(Cursor.HAND);
                    label.setStyle("-fx-text-fill: #9286DF ;");

                    label.getStyleClass().addAll("fontPyidaungsu", "opacityHover");
                    label.setOnMouseClicked(e -> {

                        int arrowIndex = labelContainerHBox.getChildren().indexOf(label) + 2;
                        String[] split = absolutePath.split("\\\\");
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int j = 0; j < arrowIndex / 2; j++) {
                            //                            System.out.print(split[j]);
                            String filePath = split[j] + "\\";
                            arrayList.add(filePath);
                            //                            System.out.print(filePath);
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
                        try {
                            Desktop.getDesktop().open(new File(listString));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });

                    labelContainerHBox.getChildren().add(arrowIconLabel);
                }

                Label textFileIconLabel = new Label("\ue873");
                textFileIconLabel.setStyle("-fx-text-fill: #9286DF;");
                textFileIconLabel.getStyleClass().addAll("icon-fill", "font12");

                topHBox.getChildren().addAll(textFileIconLabel, labelContainerHBox);

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
                            deleteNote(absolutePath);
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
                            layoutPosition.copy(to.getText());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        copyLabel.setText("Copied");
                        Platform.runLater(() -> textArea.selectAll());
                        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                        pauseTransition.setOnFinished(e1 -> {
                                    copyLabel.setText("Copy");
                                    textArea.deselect();
                                }
                        );
                        pauseTransition.play();

                    }
                });


                bottomIconContainerHBox.getChildren().addAll(deleteIconHbox, copyIconHbox);
                bottomHBox.getChildren().add(bottomIconContainerHBox);
                outermostVBox.getChildren().add(topHBox);

                if (moreOptionsPane) {
                    outermostVBox.getChildren().add(bottomHBox);
                }


                minimalistPageContentContainer.contentFlowPane.getChildren().add(outermostVBox);
            }


        }

        private void callContentFlatBox(String absolutePath) {
            FileNameFlatBox controller = new FileNameFlatBox(absolutePath);
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("FileNameFlatBox", controller);

            contentFileNameFLatMap.put(controller, parent);

            controller.fileNameFlatHBox.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {

                    chooseNote(controller.returnAbsolutePath());


                }
            });
            minimalistPageContentContainer.contentFlowPane.getChildren().add(parent);


        }

    }

    class ControlShortcutContainer {

        Parent parent;
        Stage newWindowStageForControlShortcutContainer;

        public ControlShortcutContainer() {
            loadControlShortcutContainer();
        }

        private void loadControlShortcutContainer() {
            minimalistPageControlShortcutContainer = new MinimalistPageControlShortcutContainer();
            parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageControlShortcutContainer", minimalistPageControlShortcutContainer);


            controlShortcutContainerActions();
        }

        private void controlShortcutContainerActions() {

        }

        private void clickAction(AnchorPane filePathAnchorPane) {
            filePathAnchorPane.setOnMouseClicked(e -> {
                String getFilePath = (String) filePathAnchorPane.getUserData();
                if (getFilePath != null) {
                    filePathAnchorPane.setStyle("-fx-background-color: #f2f2f7;");
                    chooseNote(getFilePath);
                }
            });
        }

        private void dockControlShortcutContainer() {

            if (newWindowStageForControlShortcutContainer != null) {
                newWindowStageForControlShortcutContainer.close();
            }

            if (dockContainerSplitPane.getItems().size() > 1) {
                contentContainer.dockContainer();
            } else {
                contentContainer.dockContainer();

                MinimalistPageControlShortcutContainer controller = new MinimalistPageControlShortcutContainer();
                Parent parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageControlShortcutContainer", controller);

                AnchorPane.setTopAnchor(parent, 0.0);
                AnchorPane.setRightAnchor(parent, 0.0);
                AnchorPane.setBottomAnchor(parent, 0.0);
                AnchorPane.setLeftAnchor(parent, 0.0);
                dockContainerSplitPane.getItems().add(parent);

                clickAction(controller.filePathAnchorPane1);
                clickAction(controller.filePathAnchorPane2);
                clickAction(controller.filePathAnchorPane3);
                clickAction(controller.filePathAnchorPane4);
                clickAction(controller.filePathAnchorPane5);
                clickAction(controller.filePathAnchorPane6);
                clickAction(controller.filePathAnchorPane7);
                clickAction(controller.filePathAnchorPane8);
                clickAction(controller.filePathAnchorPane9);


            }
        }


    }

    class SearchBar {
        Parent parent;
        Stage newWindowStageForSearchbar;

        public SearchBar() {
            loadSearchBar();
        }

        private void loadSearchBar() {
            minimalistPageSearchBar = new MinimalistPageSearchBar();
            parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageSearchBar", minimalistPageSearchBar);

            searchBarActions();

        }

        private void searchBarActions() {
            //SearchBar
            minimalistPageSearchBar.searchTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                if (newPropertyValue) {
                    searchResults(minimalistPageSearchBar.searchTextField.getText().toLowerCase().trim());
                }

            });

            minimalistPageSearchBar.searchTextField.setOnKeyReleased(e -> {            //SearchBar
                if (e.getCode() == KeyCode.ENTER) {
                    TextObject to = searchLists.get(0);
                    chooseNote(to.getAbsolutePath());


//                    pageUiRefresh();
//                    new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_textAreaService(true).build();
                }
            });

            minimalistPageSearchBar.searchTextField.textProperty().addListener((obs, oldText, newText) -> {            //SearchBar
                // do what you need with newText here, e.g.
                if (newText.isEmpty()) {
                    searchResults("");
                } else {
                    searchResults(newText.toLowerCase().trim());
                }
            });


            minimalistPageSearchBar.newNoteButton.setOnAction(e -> layoutPosition.showFileNameDialog());
            minimalistPageSearchBar.nextNoteButton.setOnAction(e -> nextNote());
            minimalistPageSearchBar.previousNoteButton.setOnAction(e -> previousNote());


            minimalistPageSearchBar.windowModeButton.setOnAction(e -> searchBar.windowSearchBar());
            minimalistPageSearchBar.floatModeButton.setOnAction(e -> searchBar.floatSearchBar());
            minimalistPageSearchBar.dockModeButton.setOnAction(e -> searchBar.dockSearchBar());

            minimalistPageSearchBar.closeSearchBoxButton.setOnAction(e -> searchBar.hideSearchBar());

        }

        private void putTop() {
            innerBorderPane.setTop(null);
            innerBorderPane.setTop(parent);
        }

        private void putBottom() {
            innerBorderPane.setBottom(parent);

        }

        private void dockSearchBarExistControl() {           //if left empty => dock
            if (innerBorderPane.getTop() == null) {
                searchBar.dockSearchBar();

            } else {
                innerBorderPane.setTop(null);

            }
        }

        private void dockSearchBar() {

            layoutPosition.clearSearchBarViewModeEffect();
            minimalistPageSearchBar.dockModeButton.getStyleClass().add(layoutPosition.searchBarViewModeClickEffectHex());

            if (newWindowStageForSearchbar != null) {
                newWindowStageForSearchbar.close();
            }
            if (floatSearchBarVBox.getChildren().size() != 0) {
                parent = (Parent) floatSearchBarVBox.getChildren().get(0);
            }

            minimalistPageSearchBar.floatModeButton.setDisable(false);
            minimalistPageSearchBar.windowModeButton.setDisable(false);

            minimalistPageSettings.searchBarFloatButton.setDisable(false);
            minimalistPageSettings.searchBarWindowButton.setDisable(false);


            putTop();
        }       //dockSearchBar method

        private void floatSearchBar() {
            layoutPosition.clearSearchBarViewModeEffect();
            minimalistPageSearchBar.floatModeButton.getStyleClass().add(layoutPosition.searchBarViewModeClickEffectHex());
            innerBorderPane.setTop(null);

            floatSearchBarVBox.getChildren().clear();
            floatSearchBarVBox.setVisible(true);
            floatSearchBarVBox.getChildren().add(parent);
            minimalistPageSearchBar.floatModeButton.setDisable(true);
            minimalistPageSearchBar.windowModeButton.setDisable(true);

            minimalistPageSettings.searchBarFloatButton.setDisable(true);
            minimalistPageSettings.searchBarWindowButton.setDisable(true);

        }       //floatSearchBar method

        private void windowSearchBar() {
            layoutPosition.clearSearchBarViewModeEffect();
            minimalistPageSearchBar.windowModeButton.getStyleClass().add(layoutPosition.searchBarViewModeClickEffectHex());
            innerBorderPane.setTop(null);
            newWindowStageForSearchbar = new Stage();

            Scene scene = new Scene(parent);
            newWindowStageForSearchbar.setOnCloseRequest(e -> dockSearchBar());
            newWindowStageForSearchbar.initModality(Modality.WINDOW_MODAL);
            newWindowStageForSearchbar.setTitle("SearchBar");
            newWindowStageForSearchbar.initStyle(StageStyle.DECORATED);
            scene.setFill(Color.TRANSPARENT);
            newWindowStageForSearchbar.setResizable(true);
            newWindowStageForSearchbar.setScene(scene);
            newWindowStageForSearchbar.show();
            minimalistPageSearchBar.floatModeButton.setDisable(true);
            minimalistPageSearchBar.windowModeButton.setDisable(true);

            minimalistPageSettings.searchBarFloatButton.setDisable(true);
            minimalistPageSettings.searchBarWindowButton.setDisable(true);

        }       //WindowSearchBar method

        private void hideSearchBar() {

            dockSearchBar();
            innerBorderPane.setTop(null);
        }


    }

    class MinimalistSettings {
        Parent parent;
        Stage newWindowStageForMinimalistSettings;

        public MinimalistSettings() {
            loadMinimalistSettings();
        }

        private void loadMinimalistSettings() {
            minimalistPageSettings = new MinimalistPageSettings();
            parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageSettings", minimalistPageSettings);

            settingsActions();

        }

        private void settingsActions() {


            minimalistPageSettings.columnSlider.valueProperty().addListener((ov, old_val, new_val) -> {
                System.out.println(new_val.doubleValue());
                double value = new_val.doubleValue();
                switch ((int) value) {
                    case 1:
                        layoutPosition.columnAdjustSize(1);
                        break;
                    case 2:
                        layoutPosition.columnAdjustSize(2);
                        break;
                    case 3:
                        layoutPosition.columnAdjustSize(3);
                        break;
                    default:
                }

            });
            minimalistPageSettings.flatContentHBox.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    contentContainer.contentMode = ContentMode.Flat;
                    searchResults("");
                }
            });

            minimalistPageSettings.boxContentHBox.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    contentContainer.contentMode = ContentMode.Box;
                    contentContainer.showFileNamePane = false;

                    searchResults("");
                    layoutPosition.contentShowCaseVBoxVisibleProperty(false);

                }

            });

            minimalistPageSettings.boxContentWithFileNameHBox.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    contentContainer.contentMode = ContentMode.Box;
                    contentContainer.showFileNamePane = true;
                    contentContainer.moreOptionsPane = false;

                    searchResults("");
                    layoutPosition.contentShowCaseVBoxVisibleProperty(false);
                }
            });
            minimalistPageSettings.boxContentWithFileNameOptionsHBox.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    contentContainer.contentMode = ContentMode.Box;
                    contentContainer.showFileNamePane = true;
                    contentContainer.moreOptionsPane = true;

                    searchResults("");
                    layoutPosition.contentShowCaseVBoxVisibleProperty(false);
                }
            });


            minimalistPageSettings.windowModeIconLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    if (minimalistSettings.newWindowStageForMinimalistSettings == null) {
                        minimalistSettings.windowMinimalistSettings();
                    }
                }
            });

            minimalistPageSettings.contentContainerDockButton.setOnAction(e -> contentContainer.dockContainer());
            minimalistPageSettings.contentContainerFloatButton.setOnAction(e -> contentContainer.floatContainer());
            minimalistPageSettings.contentContainerWindowButton.setOnAction(e -> contentContainer.windowContainer());
            minimalistPageSettings.contentContainerHideShowButton.setOnAction(e -> contentContainer.dockContainerExistControl());
            minimalistPageSettings.searchBarDockButton.setOnAction(e -> searchBar.dockSearchBar());
            minimalistPageSettings.searchBarFloatButton.setOnAction(e -> searchBar.floatSearchBar());
            minimalistPageSettings.searchBarWindowButton.setOnAction(e -> searchBar.windowSearchBar());
            minimalistPageSettings.searchBarHideShowButton.setOnAction(e -> searchBar.dockSearchBarExistControl());

            minimalistPageSettings.restoreDefaultLayoutButton.setOnAction(e -> layoutPosition.restoreDefaultLayouts());

            minimalistPageSettings.contentContainerDefaultPositionButton.setOnAction(e -> contentContainer.dockContainer());
            minimalistPageSettings.searchBarDefaultPositionButton.setOnAction(e -> searchBar.dockSearchBar());


        }

        private void putAnchor() {
            AnchorPane.setTopAnchor(parent, 0.0);
            AnchorPane.setRightAnchor(parent, 0.0);
            AnchorPane.setBottomAnchor(parent, 0.0);
            AnchorPane.setLeftAnchor(parent, 0.0);
            settingsAnchorPane.getChildren().add(parent);

        }


        private void dockMinimalistSettings() {

            settingsAnchorPane.getChildren().clear();
            if (newWindowStageForMinimalistSettings != null) {
                newWindowStageForMinimalistSettings.close();
            }
            if (settingsAnchorPane.getChildren().size() != 0) {
                parent = (Parent) settingsAnchorPane.getChildren().get(0);
            }


            putAnchor();
        }

        private void windowMinimalistSettings() {
            settingsAnchorPane.getChildren().clear();
            newWindowStageForMinimalistSettings = new Stage();

            Scene scene = new Scene(parent);
            newWindowStageForMinimalistSettings.setOnCloseRequest(e -> {
                newWindowStageForMinimalistSettings = null;
                dockMinimalistSettings();

            });
            newWindowStageForMinimalistSettings.initModality(Modality.WINDOW_MODAL);
            newWindowStageForMinimalistSettings.setTitle("Minimalist Settings");
            newWindowStageForMinimalistSettings.initStyle(StageStyle.DECORATED);
            scene.setFill(Color.TRANSPARENT);
            newWindowStageForMinimalistSettings.setResizable(true);
            newWindowStageForMinimalistSettings.setScene(scene);
            newWindowStageForMinimalistSettings.show();


        }       //WindowSearchBar method


    }

    class MatchCaseContainer {
        MatchCaseMode mode;
        Parent parent;
        Stage newWindowStageForContainer;

        public MatchCaseContainer() {
            loadMatchCaseContainer();
        }

        private void loadMatchCaseContainer() {
            minimalistPageMatchCaseHighlight = new MinimalistPageMatchCaseHighlight();
            parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageMatchCaseHighlight", minimalistPageMatchCaseHighlight);


            matchCaseContainerActions();


            //		windowMatchCaseContainer ( );

        }

        private void matchCaseContainerActions() {

            minimalistPageMatchCaseHighlight.matchCaseWordOptLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    mode = MatchCaseMode.WORDS;
                    layoutPosition.bindWords();
                    layoutPosition.searchAndHighLight();
                }
            });

            minimalistPageMatchCaseHighlight.matchCaseCharOptLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    mode = MatchCaseMode.CHARACTER;
                    layoutPosition.bindWords();
                    layoutPosition.searchAndHighLight();
                }
            });

            minimalistPageMatchCaseHighlight.floatModeIconLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    floatMatchCaseContainer();
                }
            });

            minimalistPageMatchCaseHighlight.windowModeIconLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    windowMatchCaseContainer();
                }
            });

            minimalistPageMatchCaseHighlight.findMatchCaseCloseLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    layoutPosition.matchCaseVBox();
                }
            });

            //lost focus
            minimalistPageMatchCaseHighlight.matchCaseTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                if (newPropertyValue) {
                    layoutPosition.bindWords();
                    layoutPosition.searchAndHighLight();
                } //textarea focus

            });

            minimalistPageMatchCaseHighlight.matchCaseTextField.textProperty().addListener((obs, old, niu) ->
            {      //Textarea typing action
                // TODO here
                layoutPosition.searchAndHighLight();

            });
        }

        private void floatMatchCaseContainer() {
            layoutPosition.matchCaseVBox();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    floatMatchCaseAnchorPane.getChildren().clear();
                    AnchorPane.setTopAnchor(minimalistPageMatchCaseHighlight.highlightAnchorPane, 0.0);
                    AnchorPane.setRightAnchor(minimalistPageMatchCaseHighlight.highlightAnchorPane, 0.0);
                    AnchorPane.setBottomAnchor(minimalistPageMatchCaseHighlight.highlightAnchorPane, 0.0);
                    AnchorPane.setLeftAnchor(minimalistPageMatchCaseHighlight.highlightAnchorPane, 0.0);
                    floatMatchCaseAnchorPane.getChildren().add(parent);
                }
            });

            if (newWindowStageForContainer != null) {
                newWindowStageForContainer.close();
            }

            minimalistPageMatchCaseHighlight.floatModeIconLabel.setDisable(true);
            minimalistPageMatchCaseHighlight.windowModeIconLabel.setDisable(false);

            minimalistPageMatchCaseHighlight.findMatchCaseCloseLabel.setDisable(false);
        }

        private void windowMatchCaseContainer() {
            loadMatchCaseContainer();
            //		floatMatchCaseVBox.setVisible ( false );
            //		layoutPosition.matchCaseVBoxVisibleProperty ( false );
            layoutPosition.matchCaseVBox();
            layoutPosition.clearMatchCaseContainerViewModeEffect();

            minimalistPageMatchCaseHighlight.windowModeIconLabel.getStyleClass().add(layoutPosition.searchBarViewModeClickEffectHex());

            newWindowStageForContainer = new Stage();

            Scene scene = new Scene(parent);
            newWindowStageForContainer.setOnCloseRequest(e -> {
                floatMatchCaseContainer();
            });
            newWindowStageForContainer.initModality(Modality.WINDOW_MODAL);
            newWindowStageForContainer.setTitle("Highlight Word");
            newWindowStageForContainer.initStyle(StageStyle.DECORATED);
            scene.setFill(Color.TRANSPARENT);
            newWindowStageForContainer.setResizable(true);
            newWindowStageForContainer.setScene(scene);
            newWindowStageForContainer.show();
            minimalistPageMatchCaseHighlight.floatModeIconLabel.setDisable(false);
            minimalistPageMatchCaseHighlight.windowModeIconLabel.setDisable(true);
            minimalistPageMatchCaseHighlight.findMatchCaseCloseLabel.setDisable(true);

        }

    }

    class LineCaseContainer {
        Parent parent;
        Stage newWindowStageForLineCaseContainer;

        public LineCaseContainer() {
            loadLineCaseContainer();
        }

        private void loadLineCaseContainer() {
            minimalistPageLineCase = new MinimalistPageLineCase();
            parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageLineCase", minimalistPageLineCase);

            lineCaseContainerActions();
        }

        private void lineCaseContainerActions() {
            minimalistPageLineCase.floatModeIconLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    floatLineCaseContainer();
                }
            });

            minimalistPageLineCase.windowModeIconLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    windowLineCaseContainer();
                }
            });

            minimalistPageLineCase.findMatchCaseCloseLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    layoutPosition.lineCaseVBox();
                }
            });

            //lost focus
            minimalistPageLineCase.lineCaseTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                if (newPropertyValue) {
                    layoutPosition.bindLineObj();
                    layoutPosition.searchLineObjHighlight();
                } //textarea focus
                layoutPosition.bindLineObj();
                layoutPosition.searchLineObjHighlight();

            });


            minimalistPageLineCase.lineCaseTextField.textProperty().addListener((obs, old, niu) ->
            {      //Textarea typing action
                // TODO here
                layoutPosition.searchLineObjHighlight();

            });

        }

        private void floatLineCaseContainer() {
            layoutPosition.lineCaseVBox();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lineMatchCaseAnchorPane.getChildren().clear();
                    AnchorPane.setTopAnchor(minimalistPageLineCase.lineCaseAnchorPane, 0.0);
                    AnchorPane.setRightAnchor(minimalistPageLineCase.lineCaseAnchorPane, 0.0);
                    AnchorPane.setBottomAnchor(minimalistPageLineCase.lineCaseAnchorPane, 0.0);
                    AnchorPane.setLeftAnchor(minimalistPageLineCase.lineCaseAnchorPane, 0.0);
                    lineMatchCaseAnchorPane.getChildren().add(parent);
                }
            });

            if (newWindowStageForLineCaseContainer != null) {
                newWindowStageForLineCaseContainer.close();
            }

            minimalistPageLineCase.floatModeIconLabel.setDisable(true);
            minimalistPageLineCase.windowModeIconLabel.setDisable(false);

            minimalistPageLineCase.findMatchCaseCloseLabel.setDisable(false);
        }

        private void windowLineCaseContainer() {
            loadLineCaseContainer();
            //		floatMatchCaseVBox.setVisible ( false );
            //		layoutPosition.matchCaseVBoxVisibleProperty ( false );
            layoutPosition.lineCaseVBox();
            layoutPosition.clearLineCaseContainerViewModeEffect();

            minimalistPageLineCase.windowModeIconLabel.getStyleClass().add(layoutPosition.searchBarViewModeClickEffectHex());

            newWindowStageForLineCaseContainer = new Stage();

            Scene scene = new Scene(parent);
            newWindowStageForLineCaseContainer.setOnCloseRequest(e -> {
                floatLineCaseContainer();
            });
            newWindowStageForLineCaseContainer.initModality(Modality.WINDOW_MODAL);
            newWindowStageForLineCaseContainer.setTitle("Highlight By Line");
            newWindowStageForLineCaseContainer.initStyle(StageStyle.DECORATED);
            scene.setFill(Color.TRANSPARENT);
            newWindowStageForLineCaseContainer.setResizable(true);
            newWindowStageForLineCaseContainer.setScene(scene);
            newWindowStageForLineCaseContainer.show();

            minimalistPageLineCase.floatModeIconLabel.setDisable(false);
            minimalistPageLineCase.windowModeIconLabel.setDisable(true);
            minimalistPageLineCase.findMatchCaseCloseLabel.setDisable(true);

        }


    }


    class SuggestContainer {
        Map<String, String> filterMap = new HashMap<>();

        public SuggestContainer() {
            loadSuggestions();
        }

        private void loadSuggestions() {

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

                        Platform.runLater(() -> suggestResultVBox.getChildren().add(layoutPosition.buildSuggestResultHBox(m.getKey(), m.getValue())));
                    }
                }


            });

            Platform.runLater(this::suggestContainerActions);

        }

        private void loadDictionarySuggestions(String text) {            //Refresh

            new AllRefreshServicesHandlerBuilder().service_textObjectList(true).build();
            ctrlEnterLabel.setText("Dictionary");

            Service<ArrayList<String>> searchResultDictList = new Service<ArrayList<String>>() {
                @Override
                protected Task<ArrayList<String>> createTask() {
                    return new Task<ArrayList<String>>() {
                        @Override
                        protected ArrayList<String> call() {
                            return dictionaryArrayList;
                        }
                    };
                }
            };

            searchResultDictList.restart();
            searchResultDictList.setOnRunning(e -> {
                suggestResultVBox.getChildren().clear();
                suggestResultVBox.getChildren().add(layoutPosition.buildSuggestResultHBox("indexing...", "Dictionary"));
            });
            searchResultDictList.setOnSucceeded(succeed -> {
                System.out.println("\n");
                suggestResultVBox.getChildren().clear();
                if (text != null && !text.equals("") && text.length() > 1) {

                    searchResultDictList.getValue().forEach(e -> {
                        if (e.toLowerCase().trim().startsWith(text.toLowerCase().trim())) {
                            System.out.println(e);
                            Platform.runLater(() -> suggestResultVBox.getChildren().add(layoutPosition.buildSuggestResultHBox(e, "Dictionary")));
                        }
                    });
                } else {
                    suggestResultVBox.getChildren().add(layoutPosition.buildSuggestResultHBox("No Result", "Dict"));

                }
            });


        }

        private void suggestContainerActions() {

            Scene scene = minimalistPageAnchorPane.getScene();
            scene.getAccelerators().put(                //shortcuts
                    new KeyCodeCombination(KeyCode.SPACE, KeyCombination.SHIFT_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            if (!searchSuggestWordTextField.isFocused()) {  //focus empty
                                layoutPosition.suggestVBoxVisibleProperty(true);
                                searchSuggestWordTextField.requestFocus();
                            } else {
                                textArea.requestFocus();
                            }
                        }
                    }
            );
            scene.getAccelerators().put(                //shortcuts
                    new KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN),
                    new Runnable() {
                        @FXML
                        public void run() {
                            layoutPosition.suggestVBox();
                            //						layoutPosition.suggestVBoxVisibleProperty ( true );

                        }
                    }
            );


            suggestRefreshLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    Platform.runLater(() -> {
//                            suggestRefresh();
                        new AllRefreshServicesHandlerBuilder().service_suggestService(true).build();

                    });
                }
            });

            suggestContainerCloseLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    layoutPosition.suggestVBoxVisibleProperty(false);
                }
            });

            searchSuggestWordTextField.textProperty().addListener((obs, oldText, newText) -> {            //SearchBar
                // do what you need with newText here, e.g.
                if (newText.isEmpty()) {
//                    //			  System.out.println ( "empty" );
                    suggestResultVBox.getChildren().clear();
                    for (Map.Entry<String, String> m : filterMap.entrySet()) {
                        suggestResultVBox.getChildren().add(layoutPosition.buildSuggestResultHBox(m.getKey(), m.getValue()));
                    }
                } else {
                    //			  System.out.println ( "not empty" );
                    suggestResultVBox.getChildren().clear();
                    for (Map.Entry<String, String> m : filterMap.entrySet()) {
                        if (m.getKey().trim().toLowerCase().contains(searchSuggestWordTextField.getText().trim().toLowerCase())) {
                            suggestResultVBox.getChildren().add(layoutPosition.buildSuggestResultHBox(m.getKey(), m.getValue()));
                        }
                    }
                }
            });

            //lost focus		//textArea events
            searchSuggestWordTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                if (newPropertyValue) {
                    suggestResultVBox.getChildren().clear();
                    for (Map.Entry<String, String> m : filterMap.entrySet()) {
                        if (m.getKey().trim().toLowerCase().contains(searchSuggestWordTextField.getText().trim().toLowerCase())) {
                            suggestResultVBox.getChildren().add(layoutPosition.buildSuggestResultHBox(m.getKey(), m.getValue()));
                        }
                    }
                } //textarea focus

            });

            searchSuggestWordTextField.setOnKeyReleased(e -> {            //SearchBar
                if (e.getCode() == KeyCode.ENTER) {
                    if (suggestResultVBox.getChildren().size() != 0) {
                        HBox hb = (HBox) suggestResultVBox.getChildren().get(0);
                        if (hb != null) {
                            Label resultlabel = (Label) hb.getChildren().get(1);
                            layoutPosition.copy(resultlabel.getText().trim());
                            layoutPosition.paste();
                            textArea.requestFocus();
                        }
                    }
                }

                if (e.getCode() == KeyCode.ESCAPE) {
                    Platform.runLater(() -> textArea.requestFocus());
                }

            });


        }

    }

    class DirectoryChoosePanFlat {

        Parent parent;

        public DirectoryChoosePanFlat() {
            loadChoosePanFlat();
        }

        public void loadChoosePanFlat() {
            minimalistPageDirectoryChoosePanFlat = new MinimalistPageDirectoryChoosePanFlat();
            parent = ___Bundle.__ViewLoader._getInstance()._load("MinimalistPageDirectoryChoosePanFlat", minimalistPageDirectoryChoosePanFlat);
        }

        public void dockContainer() {
            layoutPosition.stackLayoutVisiblePriority(homeAnchorPane);
            layoutPosition.stackLayoutVisiblePriority_HomeAnchorPane(directoryChooseAnchorPane);


            directoryChooseAnchorPane.getChildren().clear();
            AnchorPane.setTopAnchor(parent, 0.0);
            AnchorPane.setRightAnchor(parent, 0.0);
            AnchorPane.setBottomAnchor(parent, 0.0);
            AnchorPane.setLeftAnchor(parent, 0.0);
            directoryChooseAnchorPane.getChildren().add(parent);

            minimalistPageDirectoryChoosePanFlat.directoryChooseLabel.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    String getDirPath = layoutPosition.showDirectoryChooseDialogAndGetPath();
                    BuildNoteSpace.BuildSpace(getDirPath);
                    NoteSpacePathStorage.SaveNoteSpacePath(new NoteSpacePath("", getDirPath));
                    directoryNameTextField.setText(getDirPath);

//                        pageUiRefresh();
                    new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_contentService(true).service_panFlatRefreshService(true).build();
                }
            });
        }


    }

    class TextObjectListService extends Service<ArrayList<TextObject>> {

        private TextObjectListService() {

        }

        public void run() {
            restart();
            setOnRunning(e -> {
                minimalistPageContentContainer.progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                minimalistPageContentContainer.progressBar.setVisible(true);
            });
            setOnSucceeded(e -> {
                minimalistPageContentContainer.progressBar.setProgress(0);
                minimalistPageContentContainer.progressBar.setVisible(false);

                if (getValue().size() == 0) {
                    minimalistPageContentContainer.noResultAnchorPane.setVisible(true);
                    minimalistPageContentContainer.contentScrollPane.setVisible(false);

                } else {
                    minimalistPageContentContainer.noResultAnchorPane.setVisible(false);
                    minimalistPageContentContainer.contentScrollPane.setVisible(true);

                    if (textObjectArrayList.size() != getValue().size() || !currentNoteSpace.equals(NoteSpacePathStorage.getNoteSpacePathObj().spacePath)) {
                        currentNoteSpace = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
                        textObjectArrayList = getValue();
                        textObjectArrayList.sort(new Sorting.LatestModifiedDate());


                        searchResults("");

                    }
                }

            });
        }

        @Override
        protected Task<ArrayList<TextObject>> createTask() {
            return new Task<ArrayList<TextObject>>() {
                @Override
                protected ArrayList<TextObject> call() {
                    return FileHandlings.getTextObjectLists();
                }
            };
        }
    }

    class TextAreaService extends Service<TextObject> {
        public TextAreaService() {

        }

        public void run() {
            restart();
            setOnFailed(e -> {

                if (absoluteFilePath != null) {
                    currentFilePathLabel.setText(absoluteFilePath);
                    layoutPosition.stackLayoutVisiblePriority(fileNotExistAnchorPane);
                } else {
                    layoutPosition.stackLayoutVisiblePriority(homeAnchorPane);
                }

            });
            setOnSucceeded(e -> {
                if (bagOfWords(getValue().getFileExtension())) {

                    layoutPosition.stackLayoutVisiblePriority(textAreaContainerAnchorPane);
                    if (getValue().getText() != null) {
                        copyClipboardHBox.setDisable(getValue().getText().trim().isEmpty());
                        textArea.setText(FontRepair.fixmyanamrfont(getValue().getText().trim()));

                        filePathHBoxAnchorPane.getChildren().clear();
                        HBox hBox = layoutPosition.buildFilePathHBox(absoluteFilePath, false);
                        AnchorPane.setTopAnchor(hBox, 0.0);
                        AnchorPane.setRightAnchor(hBox, 0.0);
                        AnchorPane.setBottomAnchor(hBox, 0.0);
                        AnchorPane.setLeftAnchor(hBox, 0.0);
                        filePathHBoxAnchorPane.getChildren().add(hBox);

                        recentNoteResultHBox.getChildren().clear();
                        Collections.reverse(tempFilePaths);
                        Label label = new Label("Recently Opened");
                        label.setStyle("-fx-text-fill: gray;" + "-fx-font-size: 12;");
                        label.setMinWidth(100);
                        recentNoteResultHBox.getChildren().add(label);
                        tempFilePaths.forEach(recentFilePaths -> {
                            recentNoteResultHBox.getChildren().add(layoutPosition.buildFilePathHBox(recentFilePaths, true));
                        });

                        recentNoteResultHBox1.getChildren().clear();
                        Label label2 = new Label("Recently Opened");
                        label2.setStyle("-fx-text-fill: gray;" + "-fx-font-size: 12;");
                        label2.setMinWidth(100);
                        recentNoteResultHBox1.getChildren().add(label2);
                        tempFilePaths.forEach(recentFilePaths -> {
                            recentNoteResultHBox1.getChildren().add(layoutPosition.buildFilePathHBox(recentFilePaths, true));
                        });


                        Collections.reverse(tempFilePaths);

                    }
                    Platform.runLater(() -> {
                        textArea.requestFocus();
                    });
                }
            });
        }

        @Override
        protected Task<TextObject> createTask() {
            return new Task<TextObject>() {
                @Override
                protected TextObject call() throws Exception {
                    return FileHandlings.buildObject(absoluteFilePath);
                }
            };
        }
    }

    class LogsService extends Service<ArrayList<LogsObj>> {
        int computeIndex = 0;

        public LogsService() {
//            restart();
            setOnRunning(e -> {
                logsResultVBox.getChildren().clear();

                Label processingLabel = new Label("Loading");
                processingLabel.setStyle("-fx-text-fill:  #A7B0F5;");
                Service scheduledService = new ScheduledService() {
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
                scheduledService.restart();
//                scheduledService.setPeriod(Duration.seconds(3));
                scheduledService.setOnSucceeded(schedule -> {
//                    processingLabel.setText(computeIndex + "");


                    if (computeIndex == 1) processingLabel.setText("Loading.");
                    if (computeIndex == 2) processingLabel.setText("Loading..");
                    if (computeIndex == 3) processingLabel.setText("Loading...");
                    if (computeIndex == 4) processingLabel.setText("Loading....");

                    if (computeIndex > 4) {
                        computeIndex = 0;
                    }
                    computeIndex++;
                });

                logsResultVBox.getChildren().add(processingLabel);
            });
            setOnSucceeded(e -> {
                ArrayList<LogsObj> logsArrayList = getValue();
                logsResultVBox.getChildren().clear();
                Collections.reverse(logsArrayList);
                logsArrayList.forEach(logsobj -> {
                    LogsContainer controller = new LogsContainer(logsobj);
                    Parent parent = ___Bundle.__ViewLoader._getInstance()._load("LogsContainer", controller);
                    logsResultVBox.getChildren().add(parent);

                    controller.removeLogsLabel.setOnMouseClicked(event -> {
                        if (event.getButton().equals(MouseButton.PRIMARY)) {
                            LogsObjStorage.delete_logsObj(logsobj.getId());
                            new AllRefreshServicesHandlerBuilder().service_logsService(true).build();
                        }
                    });

                    controller.restoreLabel.setOnMouseClicked(event -> {
                        if (event.getButton().equals(MouseButton.PRIMARY)) {
                            Backup backup = BackupStorage.getLogsObjWithFileName(logsobj.getFileName());
                            String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + backup.getBackupFileName();
                            boolean exist = FileHandlings.checkFileExist(filepath);
                            if (!exist) {
                                controller.restoreLabel.setDisable(false);

                                FileHandlings.backupCreateNewFile(filepath);              //create
                                try {
                                    FileHandlings.FileWrite(filepath, backup.getTextData());        //text data
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }

                                new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_contentService(true).service_logsService(true).build();


                            } else {
                                controller.restoreLabel.setDisable(true);
                            }

                        }
                    });

                });

                if (logsArrayList.size() == 0) {
                    Label noResultLabel = new Label("Return No Results");
                    noResultLabel.setOnMouseClicked(clickEvent -> {
                        if (clickEvent.equals(MouseButton.PRIMARY)) {
                            new AllRefreshServicesHandlerBuilder().service_logsService(true).build();
                        }
                    });
                    noResultLabel.setStyle("-fx-text-fill:  #A7B0F5;");
                    logsResultVBox.getChildren().clear();
                    logsResultVBox.getChildren().add(noResultLabel);


                }
            });
        }

        @Override
        protected Task<ArrayList<LogsObj>> createTask() {
            return new Task<ArrayList<LogsObj>>() {
                @Override
                protected ArrayList<LogsObj> call() throws InterruptedException {
//                    Thread.sleep(2000);
                    return LogsObjStorage.getLogsObjList();
                }
            };
        }
    }       //LogsService

    class BackupService extends Service<ArrayList<Backup>> {
        int computeIndex = 0;

        public BackupService() {
//            restart();
            setOnRunning(e -> {
                backupResultVBox.getChildren().clear();

                Label processingLabel = new Label("Loading");
                processingLabel.setStyle("-fx-text-fill:  #A7B0F5;");
                Service scheduledService = new ScheduledService() {
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
                scheduledService.restart();
//                scheduledService.setPeriod(Duration.seconds(3));
                scheduledService.setOnSucceeded(schedule -> {
//                    processingLabel.setText(computeIndex + "");


                    if (computeIndex == 1) processingLabel.setText("Loading.");
                    if (computeIndex == 2) processingLabel.setText("Loading..");
                    if (computeIndex == 3) processingLabel.setText("Loading...");
                    if (computeIndex == 4) processingLabel.setText("Loading....");

                    if (computeIndex > 4) {
                        computeIndex = 0;
                    }
                    computeIndex++;
                });

                backupResultVBox.getChildren().add(processingLabel);
            });
            setOnSucceeded(e -> {
                backupArrayList = getValue();

//                backupResultVBox.getChildren().clear();
                if (backupArrayList.size() == 0) {
                    Label noResultLabel = new Label("Return No Results");
                    noResultLabel.setOnMouseClicked(clickEvent -> {
                        if (clickEvent.equals(MouseButton.PRIMARY)) {
                            new AllRefreshServicesHandlerBuilder().service_backupService(true).build();
                        }
                    });
                    noResultLabel.setStyle("-fx-text-fill:  #A7B0F5;");
                    backupResultVBox.getChildren().clear();
                    backupResultVBox.getChildren().add(noResultLabel);
                } else {
                    Collections.reverse(backupArrayList);
                    _searchBackupResults("");
                }

            });
        }

        private void _constructController(Backup backupObj) {
            BackupContainer controller = new BackupContainer(backupObj);
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("BackupContainer", controller);
            backupResultVBox.getChildren().add(parent);

            controller.restoreLabel.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {

                    String filepath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\" + backupObj.getBackupFileName();
                    boolean exist = FileHandlings.checkFileExist(filepath);
                    if (!exist) {
                        controller.restoreLabel.setDisable(false);

                        FileHandlings.backupCreateNewFile(filepath);              //create
                        try {
                            FileHandlings.FileWrite(filepath, backupObj.getTextData());        //text data
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        new AllRefreshServicesHandlerBuilder().service_textObjectList(true).service_contentService(true).service_logsService(true).build();


                    } else {
                        controller.restoreLabel.setDisable(true);
                    }

                }
            });

            controller.deleteBackupLabel.setOnMouseClicked(event -> {
                BackupStorage.deleteBackup(backupObj.getId());
                new AllRefreshServicesHandlerBuilder().service_backupService(true).build();

            });
        }

        private void _searchBackupResults(String context) {
            Service<ArrayList<Backup>> service = new Service<ArrayList<Backup>>() {
                @Override
                protected Task<ArrayList<Backup>> createTask() {
                    return new Task<ArrayList<Backup>>() {
                        @Override
                        protected ArrayList<Backup> call() throws Exception {
                            return backupArrayList;
                        }
                    };
                }
            };
            service.restart();
            service.setOnSucceeded(backupArraylist -> {
                backupResultVBox.getChildren().clear();
                service.getValue().forEach(backupObj -> {
                    if (backupObj.getFileName().toLowerCase().contains(context.toLowerCase().trim())) {
                        _constructController(backupObj);
                    }
                });
            });
        }

        @Override
        protected Task<ArrayList<Backup>> createTask() {
            return new Task<ArrayList<Backup>>() {
                @Override
                protected ArrayList<Backup> call() throws Exception {
//                    Thread.sleep(2000);
                    return BackupStorage.getBackupList();
                }
            };
        }
    }       //Backup Service

    class EditHistoryService extends Service<ArrayList<EditHistoryObj>> {

        public EditHistoryService() {
//            restart();
            setOnSucceeded(e -> {
                editHistoryContentVBox.getChildren().clear();
                ArrayList<EditHistoryObj> editHistoryObjArrayList = getValue();
                Collections.reverse(editHistoryObjArrayList);

                if (editHistoryObjArrayList.size() != 0) {
                    for (EditHistoryObj eho : editHistoryObjArrayList) {

                        EditHistoryContent controller = new EditHistoryContent(eho);
                        Parent parent = ___Bundle.__ViewLoader._getInstance()._load("EditHistoryContent", controller);

                        editHistoryContentVBox.getChildren().add(parent);

                    }
                }
            });
        }


        @Override
        protected Task<ArrayList<EditHistoryObj>> createTask() {
            return new Task<ArrayList<EditHistoryObj>>() {
                @Override
                protected ArrayList<EditHistoryObj> call() {
                    return EditHistoryObjStorage.getEditHistoryObj_listByFileName(absoluteFilePath);
                }
            };
        }
    }

    class ControlShortcutWatcherService extends ScheduledService<ControlShortcut> {

        public ControlShortcutWatcherService() {
            restart();
            setPeriod(Duration.seconds(1));
            setOnSucceeded(contorlShortcut -> {
                ControlShortcut controlShortcut = getValue();
                if (controlShortcut != null) {
                    shortcutHBox.setVisible(true);
                    shortcutLabel.setText(controlShortcut + "");
                } else {
                    shortcutHBox.setVisible(false);
                }
            });
        }

        @Override
        protected Task<ControlShortcut> createTask() {
            return new Task<ControlShortcut>() {
                @Override
                protected ControlShortcut call() throws Exception {
                    return ControlShortcutStorage.GetControlShortcut(absoluteFilePath);
                }
            };
        }
    }


}