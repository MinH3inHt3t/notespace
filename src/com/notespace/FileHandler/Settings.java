package com.notespace.FileHandler;

import com.notespace.ENUM.DateFormat;
import com.notespace.ENUM.TimeFormat;

import java.io.Serializable;

public class Settings implements Serializable {
    public static String version = "version-info-1.0.1";
    private static Settings instance;

    public String default_BackgroundColor = "#f2f2f7";
    public String default_TextColor = "black";

    public String pinPath = null;

    public String NoteCard_CreateDateFormat = DateFormat.FORMAT1.getFormat();  //format1 dd/MM/yyyy  format2 dd/MMMM/yyyy
    public String NoteCard_ModifiedDateFormat = DateFormat.FORMAT1.getFormat();
    public String NoteCard_ModifiedTimeFormat = TimeFormat.FORMAT1.getFormat();
    public boolean NoteCard_ModifiedTimePrettyFormat = false;   //this means creation date 2 days ago.


    private Settings() {

    }


    public static Settings getInstance() {
        instance = new Settings();
        return instance;
    }

    public static void main(String[] args) {
        Settings settings = SaveSettings.ReadSettings();
        settings.default_BackgroundColor = "#f2f2f9";
        SaveSettings.SaveSettings(settings);
    }

}
