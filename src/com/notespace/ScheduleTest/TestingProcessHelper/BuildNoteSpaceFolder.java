package com.notespace.ScheduleTest.TestingProcessHelper;

import com.notespace.FileHandler.BuildNoteSpace;
import com.notespace.FileHandler.NoteSpacePath;
import com.notespace.FileHandler.NoteSpacePathStorage;
import com.notespace.TabMode.Tabs.TabsUI_Test;
import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class BuildNoteSpaceFolder extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //	  SystemConfig config = SystemConfigStorage.ReadSettings ();
        //	  config.imagePath1 = getFilePath ();
        //	  config.imagePath1BgColor = "#D8D3CF";
        //	  SystemConfigStorage.SaveSystemConfig ( config );


        getDirectoryPath();


    }

    public String getFilePath() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"),
                new FileChooser.ExtensionFilter("ZIP", "*.zip"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("TEXT", "*.txt"),
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
        );

        File file = fileChooser.showOpenDialog(new Stage());

        if (file.getAbsolutePath() != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public void getTheUserFilePath() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"),
                new FileChooser.ExtensionFilter("ZIP", "*.zip"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("TEXT", "*.txt"),
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
        );


        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            // pickUpPathField it's your TextField fx:id
            System.out.println(file.getAbsolutePath());
            new TabsUI_Test(file.getAbsolutePath().trim()).startUINewWindow(new Stage());
        } else {
            System.out.println("error"); // or something else
        }

    }

    public void getDirectoryPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory == null) {
            //No Directory selected
        } else {
            //		System.out.println ( selectedDirectory.getAbsolutePath ( ) );
            BuildNoteSpace.BuildSpace(selectedDirectory.getAbsolutePath());
            NoteSpacePathStorage.SaveNoteSpacePath(new NoteSpacePath("", selectedDirectory.getAbsolutePath()));

        }

    }

}
