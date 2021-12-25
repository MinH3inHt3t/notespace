import com.notespace.FileHandler.BuildNoteSpace;
import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.NoteSpacePathStorage;
import com.notespace.FileHandler.TextObject;
import com.notespace.FileHandler.UISplitMemory.SplitUIMemoryStorage;
import com.notespace.HomePage.HomePage;
import com.notespace.MainPage.Main_Page;
import com.notespace.NoteCard.NoteCardUI_Test;
import com.notespace.Page1.Page1;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import product_out.PURPOSE;
import product_out.___Bundle;

import java.util.ArrayList;

@___Bundle.__DBConfig(_DBName = "taskiv", _Host = "localhost", _UserName = "root", _Password = "mysql123", _Port = "3306")
public class GUI_Start extends Application {
    public GUI_Start() {
        //		  ___Bundle._getInstance ( PURPOSE.EXPORT );
    }

    public static void main(String[] args) {
        //        System.out.println ( ___Bundle.__Helper_Database._getInstance ()._GetConnection ( GUI_Start.class ) );
        //	  new com.notespace.FileHandler.SaveCardBackgroundColor ( );
        ___Bundle._getInstance(PURPOSE.TESTER);
        Application.launch(args);


        //	  System.out.println ( com.notespace.Settings.NoteCard_CreateDateFormat);
    }

    static void startGame(Stage primaryStage) {
        // initialisation from start method goes here

        if (NoteSpacePathStorage.getNoteSpacePathObj().spacePath != null && NoteSpacePathStorage.CheckNoteSpacePathExists() && BuildNoteSpace.CheckNoteSpaceFormat()) { //if notespace storage null and
            Scene scene = new Scene(___Bundle.__ViewLoader._getInstance()._load("Main_Page", new Main_Page()));
            scene.getStylesheets().add("com/notespace/CSS/extra.css");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.setWidth(1290);
            primaryStage.setHeight(750);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Indie+Flower");
                    scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Material+Icons");

                }
            });
            primaryStage.show();


        } else {
            //		JOptionPane.showMessageDialog ( null , "Recent NoteSpace is missing" );
            //Go to choice directory UI

            Scene scene = new Scene(___Bundle.__ViewLoader._getInstance()._load("HomePage", new HomePage()));
            primaryStage.setScene(scene);
            primaryStage.setWidth(1290);
            primaryStage.setHeight(750);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Indie+Flower");
                    scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Material+Icons");

                }
            });
            primaryStage.show();


        }


        primaryStage.setOnCloseRequest(e -> {

            //		JOptionPane.showMessageDialog ( null , "Message" , "" , JOptionPane.ERROR_MESSAGE );
            if (NoteSpacePathStorage.CheckNoteSpacePathExists() == true) {
                double stageWidth = primaryStage.getWidth();
                SplitUIMemoryStorage.setPage1Split1Value(stageWidth, Page1.splitPane1Value);
                SplitUIMemoryStorage.setPage1Split2Value(stageWidth, Page1.splitPane2Value);
                SplitUIMemoryStorage.setMainPageSplit1Value(stageWidth, Main_Page.splitPane1Value);
                Platform.exit();
            } else {
//                JOptionPane.showMessageDialog(null, "You Accidently Delete or Remove your active NoteSpace \n Please restart the program", "ERROR", JOptionPane.ERROR_MESSAGE);
//                               System.exit(1);
            }

            //		System.exit ( 1 );
        });
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    public static void restart(Stage stage) {
        //	  cleanup ( );
        startGame(stage);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {


        //	  String[] arr = { "D:\\NoteSpaceHere\\Space2\\final.txt" , "D:\\NoteSpaceHere\\Space2\\Student.java" };
        //	  int i = 0;
        //	  while ( i < arr.length ) {
        //		new NoteCardUI_Test ( arr[ i ] ).startUI ( new Stage ( ) );
        //		i++;
        //	  }

        Service<ArrayList<TextObject>> service = new Service<ArrayList<TextObject>>() {
            @Override
            protected Task<ArrayList<TextObject>> createTask() {
                return new Task<ArrayList<TextObject>>() {
                    @Override
                    protected ArrayList<TextObject> call() throws Exception {
                        return FileHandlings.getTextObjectLists();
                    }
                };
            }
        };
        //	  service.restart ( );
        service.setOnSucceeded(e -> {
            double posX = 20;
            double posY = 100;
            for (TextObject to : service.getValue()) {
                Stage stage = new Stage();
                stage.setX(posX);
                stage.setY(posY);
                new NoteCardUI_Test(to.getAbsolutePath()).startUINewWindow(stage);
                //		    posX += 400;
                //		    posY += 0;


            }
        });
        startGame(primaryStage);

        Stage stage = new Stage();
        stage.centerOnScreen();
//	  new NoteCardUI_Test ( "D:\\NoteSpaceHere\\Space2\\New Text Document.txt" ).startUINewWindow ( stage );

        Stage stage1 = new Stage();
        stage1.setX(50);
        stage1.setY(100);
        //	  	  new NoteCardUI_Test ( "D:\\NoteSpaceHere\\Space2\\Student.java" ).startUINewWindow ( stage1 );

    }
}
