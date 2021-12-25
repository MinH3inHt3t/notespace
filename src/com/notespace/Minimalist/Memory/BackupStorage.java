package com.notespace.Minimalist.Memory;

import com.notespace.FileHandler.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BackupStorage {
    public static String version = "version-info-1.0.1";
    private static ArrayList<Backup> backup_list = new ArrayList<Backup>();


    public BackupStorage() {

        //	  read ( );
    }

    public static void main(String args[]) {

//        addBackup(new Backup("Hello.txt", "Hello  this is text data."));
//        addBackup(new Backup("hello.txt", "hello world"));
        getBackupList().stream().forEach(b -> {
            System.out.println(b);
        });

//        System.out.println();
//        System.out.println();

//        getBackupListByFileName("Hello.txt").stream().forEach(b -> {
//            System.out.println(b);
//        });

//        System.out.println("\n");
//        System.out.println(getLogsObj("0da00085-55bd-42c4-9d39-b52facc511f0"));

    }

    public static void saveLog() {

    }


    public static Backup getLogsObj(String id) {
        read();
        Backup i = null;
        for (Backup b : backup_list) {
            if (id.equals(b.getId())) {
                i = b;
            }

        }
        return i;
    }

    public static Backup getLogsObjWithFileName(String fileName) {
        read();
        Backup i = null;
        for (Backup b : backup_list) {
            if (fileName.equals(b.getFileName())) {
                i = b;
            }

        }
        return i;
    }

    public static ArrayList<Backup> getBackupListByFileName(String fileName) {
        read();
        ArrayList<Backup> backupArrayList = new ArrayList<>();
        for (Backup backup : backup_list) {
            if (backup.getFileName().equals(fileName)) {
                backupArrayList.add(backup);
            }
        }
        return backupArrayList;
    }

    public static ArrayList<Backup> getBackupList() {
        read();
        return backup_list;
    }

    public static void addBackup(Backup backup) {
        read();
        backup_list.add(backup);
        save();
    }

    private static void save() {
        //        read ();
        try {

            FileOutputStream fileout = new FileOutputStream(Config.createComDirectory() + "backup.tmp");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(backup_list);
            fileout.close();
            out.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void read() {
        File myfile = new File(Config.createComDirectory() + "backup.tmp");
        if (myfile.exists()) {

            try {
                FileInputStream filein = new FileInputStream(Config.createComDirectory() + "backup.tmp");
                ObjectInputStream in = new ObjectInputStream(filein);
                backup_list = (ArrayList<Backup>) in.readObject();

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

    public static void updateBackup(Backup backup) {
        read();
        for (
                int i = 0;
                i < backup_list.size();
                i++
        ) {
            if (backup.getId().equals(backup_list.get(i).getId())) {
                backup_list.set(i, backup);
            }
        }
        save();
    }

    public static void deleteBackup(String id) {
        read();
        for (
                Iterator<Backup> itrs = backup_list.iterator();
                itrs.hasNext(); ) {
            Backup b = itrs.next();
            if (b.getId().equals(id)) {
                itrs.remove();
            }
        }
        save();
    }
}
