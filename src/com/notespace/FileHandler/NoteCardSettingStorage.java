package com.notespace.FileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class NoteCardSettingStorage {
    public static String version = "version-info-1.0.1";
    private static ArrayList<NoteCardSetting> user_list = new ArrayList<NoteCardSetting>();
    String filename;

    {

    }

    public NoteCardSettingStorage() {

        //	  read ( );
    }

    public static void main(String args[]) {

        NoteCardSetting ns = getNoteCardSetting("D:\\NoteSpaceHere\\5153015b-e3a2-4e30-8b71-9c59d908c630.txt");
        ns.labelfolded = true;
        update_noteCardSetting(ns);

        //	  setLabel ( "D:\\NoteSpaceHere\\Page1_Backup.txt","repair" );

        //	  	  delete_noteCardSetting ( "D:\\NoteSpaceHere\\YOLO.txt" );
        //	  	  	  setLabel ("D:\\NoteSpaceHere\\YOLO.txt" , "Yolo" );
        //	  setPassword ( "D:\\NoteSpaceHere\\YOLO.txt" , "yolo" );
        //	  setLabel ( "D:\\NoteSpaceHere\\UUN.txt" , "password" );
        //	  setLabel ( "D:\\NoteSpaceHere\\Photoshop Shortcut.txt","Photoshop Usage" );

        //	  	  System.out.println ( getNoteCardSetting ( "D:\\NoteSpaceHere\\YOsLO.txt" ) );

        getNoteCardSettingList().stream().forEach(System.out::println);
    }

    public static void setPassword(String filePath, String pass) {
        NoteCardSetting ns = getNoteCardSetting(filePath);
        if (ns != null) {
            ns.password = pass;
            update_noteCardSetting(ns);
        } else {
            NoteCardSetting noteCardSetting = new NoteCardSetting(filePath);
            noteCardSetting.password = pass;
            addNoteCardSetting(noteCardSetting);

        }
    }

    public static void setPasswordAllNotes(String pass) {
        FileHandlings.getTextObjectLists().stream().forEach(e -> {
            setPassword(e.getAbsolutePath(), pass);
        });
    }

    public static void removePasswordAllNotes() {
        FileHandlings.getTextObjectLists().stream().forEach(e -> {
            setPassword(e.getAbsolutePath(), null);
        });
    }

    public static void setLabelText(String filePath, String labeltext) {
        NoteCardSetting ns = getNoteCardSetting(filePath);
        if (ns != null) {
            ns.label = labeltext;
            update_noteCardSetting(ns);
        } else {
            NoteCardSetting noteCardSetting = new NoteCardSetting(filePath);
            noteCardSetting.label = labeltext;
            addNoteCardSetting(noteCardSetting);

        }
    }

    public static void setAllLabelText(String labelText) {
        getNoteCardSettingList().stream().forEach(e -> {
            setLabelText(e.filePath, labelText);
        });
    }

    public static void removeAllLabelText() {
        getNoteCardSettingList().stream().forEach(e -> {
            setLabelText(e.filePath, null);
        });
    }


    public static void setLabelFold(String filePath) {
        NoteCardSetting ns = getNoteCardSetting(filePath);
        ns.labelfolded = true;
        update_noteCardSetting(ns);
    }

    public static void removeLabelFold(String filePath) {
        NoteCardSetting ns = getNoteCardSetting(filePath);
        ns.labelfolded = false;
        update_noteCardSetting(ns);
    }

    public static void setAllLabelFold() {
        getNoteCardSettingList().stream().forEach(e -> {
            if (e.label != null) {
                NoteCardSettingStorage.setLabelFold(e.filePath);
            }
        });
    }

    public static void removeAllLabelFold() {
        getNoteCardSettingList().stream().forEach(e -> {
            if (e.label != null) {
                NoteCardSettingStorage.removeLabelFold(e.filePath);
            }
        });
    }


    public static NoteCardSetting getNoteCardSetting(String filePath) {
        read();
        NoteCardSetting i = null;
        for (NoteCardSetting u : user_list) {
            if (filePath.equals(u.filePath)) {
                i = u;
            }

        }
        return i;
    }

    public static ArrayList<NoteCardSetting> getNoteCardSettingList() {
        read();
        return user_list;
    }

    private static void addNoteCardSetting(NoteCardSetting user) {
        read();
        user_list.add(user);
        save();
    }

    private static void save() {
        //        read ();
        try {
            FileOutputStream fileout = new FileOutputStream(NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\.bin\\" + version + "notecardsettings.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(user_list);
            fileout.close();
            out.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void read() {
        File myfile = new File(NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\.bin\\" + version + "notecardsettings.ser");
        if (myfile.exists()) {

            try {
                FileInputStream filein = new FileInputStream(NoteSpacePathStorage.getNoteSpacePathObj().spacePath + "\\.bin\\" + version + "notecardsettings.ser");
                ObjectInputStream in = new ObjectInputStream(filein);
                user_list = (ArrayList<NoteCardSetting>) in.readObject();

                filein.close();
                in.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {

            System.out.println("file does not exist");
        }
    }

    private static void update_noteCardSetting(NoteCardSetting user) {
        read();
        for (
                int i = 0;
                i < user_list.size();
                i++
        ) {
            if (user.filePath.equals(user_list.get(i).filePath)) {
                user_list.set(i, user);
            }
        }
        save();
    }

    public static void delete_noteCardSetting(String filePath) {
        read();
        for (
                Iterator<NoteCardSetting> itrs = user_list.iterator();
                itrs.hasNext(); ) {
            NoteCardSetting u = itrs.next();
            if (u.filePath.equals(filePath)) {
                itrs.remove();
            }
        }
        save();
    }
}

