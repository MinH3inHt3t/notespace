package com.notespace.FileHandler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class SystemConfigStorage extends Application {

    public static void main(String[] args) {
        //	  Application.launch ( args );

        SystemConfig s = ReadSettings();
        s.isBackgroundImageSet = false;
        s.imagePath1 = "D:\\Wallpapers\\Cyberpunk\\mg1.jpg";

        //	  s.imagePath1 = "D:\\MyPhotoshop\\Ps files\\BackgroundImage.jpg";
        //	  s.imagePath1 = "D:\\MyPhotoshop\\Ps files\\MinGun.jpg";
        //	  s.imagePath2 = "D:\\Wallpapers\\Cyberpunk\\lei-deng-cyberpunk2077.jpg";
        s.imagePath2 = "D:\\Wallpapers\\Cyberpunk\\cyberpunk-2077-scam-featured.jpg";
        s.imagePath1BgColor = "#000000";
        s.imagePath2BgColor = "#362575";

        s.imagePath1effectWidth = 60;
        s.imagePath1effectIteration = 3;

        s.imagePath2effectWidth = 10;
        s.imagePath2effectIteration = 3;

        //	  s.imagePath1 = null;
        //	  s.imagePath2 = null;

        s.glassTheme = "rgba(0,0,0,0.2)";
        //	  s.glassTheme = "rgba(255,255,255,0.5)";
        //	  SaveSystemConfig ( s );
        //	  System.out.println ( s );
        SaveSystemConfig(s);

        //	  System.out.println ( s.pinPath );
        //	  SaveSettings ( s );
        //	  System.out.println ( s.autoScrollToTopWhenChangeOriginalPosition );

        //	  	  s.MainPage_ExpandCreateDateFormat= DateFormat.FORMAT2.getFormat ();
        //	  s.MainPage_ExpandModifiedTimePrettyFormat =true;
        //	  s.MainPage_ExpandModifiedTimeFormat = TimeFormat.FORMAT1.getFormat ( );
        //	  s.type = VIEW.SINGLE;
        //	  s.NoteCard_ModifiedTimePrettyFormat = true;


    }


    public static void SaveSystemConfig(SystemConfig systemconfig) {
        try {

            FileOutputStream fo = new FileOutputStream(Config.createComDirectory() + "sysconfig.temp");
            ObjectOutputStream obo = new ObjectOutputStream(fo);
            obo.writeObject(systemconfig);
            fo.close();
            obo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static SystemConfig ReadSettings() {


        //	  if ( NoteSpacePathStorage.GetNoteSpacePath ( ).spacePath != null ) {
        //		Permit.permitAccept ( );
        File file = new File(Config.createComDirectory() + "sysconfig.temp");

        if (!file.exists()) {
            try {
                file.createNewFile();
                SaveSystemConfig(SystemConfig.getInstance());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            SystemConfig result = (SystemConfig) ois.readObject();
            fis.close();
            ois.close();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        //	  }
        //	  else {
        //		JOptionPane.showMessageDialog ( null , "You Accidently Deleted System files \n Please restart the program!" , "Warning" , JOptionPane.ERROR_MESSAGE );
        //		System.exit ( 1 );
        //	  }


        return null;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button1 = new Button("Btn1");
        Button button2 = new Button("Btn2");

        button1.setOnAction(e -> {
            SystemConfig systemConfig = ReadSettings();
            //		systemConfig.isBackgroundImageSet = true;
            systemConfig.imagePath1 = "D:\\Wallpapers\\Ghost of tsushima\\DJ7CfXqPe3pb5w32ntZ88D.jpg";
            SaveSystemConfig(systemConfig);
        });
        button2.setOnAction(e -> {
            SystemConfig systemConfig = ReadSettings();
            //		systemConfig.isBackgroundImageSet = false;
            systemConfig.imagePath2 = "D:\\Wallpapers\\Ghost of tsushima\\70272E10-871F-43C4-B479-2B0FA85BE31B.jpeg";
            SaveSystemConfig(systemConfig);
        });


        Scene scene = new Scene(new VBox(button1, button2));
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
