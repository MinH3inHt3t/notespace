package com.notespace.Minimalist.Memory;

import com.notespace.FileHandler.Config;
import com.notespace.Minimalist.ENUM.ControlShortcut;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ControlShortcutStorage {

    public static String version = "version-info-1.0.1";
    public String absolutePath;
    public String colorHex;

    public ControlShortcutStorage() {
        //	  	  saveHashFile ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document.txt","#ffc972" );

    }

    public static void main(String[] args) {


//            SaveControlShortcut(ControlShortcut.CTRL1,"D:\\NoteSpaceHere\\Test\\Temp3.txt");


//        SaveControlShortcut(ControlShortcut.Ctrl4,null);

//        HashMap<ControlShortcut, String> hm = ReturnControlShortcutHashMap();
//        hm.forEach((key, value) -> System.out.println(key + " : " + value));

        System.out.println(GetControlShortcut("D:\\NoteSpaceHere\\Test\\သူခိုး.txt"));


//        System.out.println(GetFilePath(ControlShortcut.CTRL3));

    }


    public static void SaveControlShortcut(ControlShortcut shortcut, String filePath) {

        HashMap<ControlShortcut, String> hm = new HashMap<>();

        if (ReturnControlShortcutHashMap() != null) {
            hm = ReturnControlShortcutHashMap();
        }
        hm.put(shortcut, filePath);
        Save(hm);




//        for (Map.Entry<ControlShortcut, String> m : hm.entrySet()) {
//            System.out.println(m.getKey() + " : " + m.getValue());
//        }
    }

    public static String GetFilePath(ControlShortcut shortCut) {
        HashMap<ControlShortcut, String> mapInFile = new HashMap<>();

        if (ReturnControlShortcutHashMap() != null) {
            mapInFile = ReturnControlShortcutHashMap();
        }

        String filePath = null;
        filePath = mapInFile.get(shortCut);
        return filePath;
    }

    public static ControlShortcut GetControlShortcut(String filePath) {
        HashMap<ControlShortcut, String> mapInFile = new HashMap<>();

        if (ReturnControlShortcutHashMap() != null) {
            mapInFile = ReturnControlShortcutHashMap();
        }

        ControlShortcut shortcut = null;
        for (Map.Entry<ControlShortcut, String> entry : mapInFile.entrySet()) {

            // if give value is equal to value from entry
            // print the corresponding key
            if (entry.getValue() != null) {

                if (entry.getValue().equals(filePath)) {
//               System.out.println("The key for value " + value + " is " + entry.getKey());
                    shortcut = entry.getKey();
                    break;
                }
            }
        }

        return shortcut;

    }


    private static void Save(HashMap<ControlShortcut, String> map) {

        try {

            File fileOne = new File(Config.createComDirectory() + "Controlshortcut.tmp");
            FileOutputStream fos = new FileOutputStream(fileOne);
            ObjectOutputStream oos = new ObjectOutputStream(fos);


            oos.writeObject(map);
            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception e) {
        }
    }

    public static HashMap<ControlShortcut, String> ReturnControlShortcutHashMap() {
        HashMap<ControlShortcut, String> mapInFile = null;
        File myfile = new File(Config.createComDirectory() + "Controlshortcut.tmp");
        if (myfile.exists()) {

            try {
                //		    System.out.println ( NoteSpacePathStorage.GetNoteSpacePath ( ).spacePath + "\\.bin\\BackgroundColor.save" );
                File toRead = new File(Config.createComDirectory() + "Controlshortcut.tmp");
                FileInputStream fis = new FileInputStream(toRead);
                ObjectInputStream ois = new ObjectInputStream(fis);

                mapInFile = (HashMap<ControlShortcut, String>) ois.readObject();

                ois.close();
                fis.close();
                //print All data in MAP
                //			         for( Map.Entry<String,String> m :mapInFile.entrySet()){
                //			             System.out.println(m.getKey()+" : "+m.getValue());
                //			         }
            } catch (Exception e) {
            }
        } else {

        }
        return mapInFile;
    }
}
