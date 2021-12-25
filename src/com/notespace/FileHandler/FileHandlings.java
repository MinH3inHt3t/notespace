package com.notespace.FileHandler;

import com.notespace.Minimalist.Logs.REASON;
import com.notespace.Minimalist.Memory.Backup;
import com.notespace.Minimalist.Memory.BackupStorage;
import com.notespace.Minimalist.Memory.LogsObj;
import com.notespace.Minimalist.Memory.LogsObjStorage;
import com.notespace.RecentNoteStorage;
import com.notespace.ScheduleTest.TestingProcessHelper.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.BreakIterator;
import java.util.*;

public class FileHandlings {
    static ArrayList<String> lineObjList = new ArrayList<>();
    static ArrayList<String> lineWordList = new ArrayList<>();
    static Map<String, String> lineWordMap = new HashMap<>();
    static Map<String, String> lineObjMap = new HashMap<>();
    static ArrayList<String> dictionaryList = new ArrayList<>();
    String data = null;

    public static void main(String[] args) {
        try {
            String readText = readTextFromFile("D:\\NoteSpaceHere\\T2\\hamspire.txt");
            System.out.println(readText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getDictionaryList() {
        List<String> dictionaryList = new ArrayList<>();
        InputStream in = Test.class.getResourceAsStream("/com/notespace/Res/dict.txt");
        Scanner scanner = new Scanner(in);

        while (scanner.hasNextLine()) {
            String name = scanner.nextLine().trim().toString();

            dictionaryList.add(name.trim());
        }

        try {
            scanner.close();
            in.close();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
        return (ArrayList<String>) dictionaryList;
    }

    public static ArrayList<String> getLineObjectsLists() {
        List<String> deDupStringList = new ArrayList<>(new HashSet<>(lineObjList));
        return (ArrayList<String>) deDupStringList;
    }

    public static ArrayList<String> getLineWordLists() {
        List<String> deDupStringList = new ArrayList<>(new HashSet<>(lineWordList));
        return (ArrayList<String>) deDupStringList;
    }

    public static Map<String, String> getLineWordMap() {
        return lineWordMap;
    }

    public static Map<String, String> getLineObjMap() {
        return lineObjMap;
    }

    public static ArrayList<TextObject> getTextObjectLists() {

        ArrayList<TextObject> arrayList = new ArrayList<>();
        String currentSpacePath = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
        File f = new File(currentSpacePath);


        File[] files = f.listFiles();
        lineObjList.clear();
        lineWordList.clear();

        lineWordMap.clear();
        lineObjMap.clear();

        for (File file : files) {
            if (file.isFile()) {

                try {
                    arrayList.add(buildObject(file.getAbsolutePath()));
                    //			  RecentNoteSpaceStorage.setRecentNoteSpace ( f.getAbsolutePath ( ) , f.getAbsolutePath ( ) );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }


    public static void setHiddenAttrib(String path) throws IOException {
        Path p = Paths.get(path);

        Files.setAttribute(p, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);      //value true to hide / false to unhide


    }

    public static void setRemoveHiddenAttrib(String path) throws IOException {
        Path p = Paths.get(path);

        Files.setAttribute(p, "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);      //value true to hide / false to unhide

    }

    public static String readTextFromFile(String absolutePath) throws IOException      //read Data from File
    {

        String data = "";


        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();


            while (line != null) {

                lineObjMap.put(line, absolutePath.substring(absolutePath.lastIndexOf("\\") + 1));
                sb.append(line);
                //		    sb.append ( System.lineSeparator ( ) );
                line = br.readLine();
                if (sb.toString() != null) {
                    if (getFileExtenstion(absolutePath).equals(".txt")) {
                        lineObjList.add(sb.toString());

                        getWords(sb.toString()).stream().forEach(string -> {
                            lineWordMap.put(string, absolutePath.substring(absolutePath.lastIndexOf("\\") + 1));
                        });
                    }
//                    data = sb.toString();
                    File file = new File(absolutePath);
                    Scanner sc = new Scanner(file);
                    // we just need to use \\Z as delimiter
                    sc.useDelimiter("\\Z");
                    data = sc.next();
                    sc.close();
                } else {

                    lineObjMap.put(line, absolutePath.substring(absolutePath.lastIndexOf("\\") + 1));
                    sb.append(line);
                    line = br.readLine();
                    if (getFileExtenstion(absolutePath).equals(".txt")) {
                        lineObjList.add(sb.toString());

                        getWords(sb.toString()).stream().forEach(string -> {
                            lineWordMap.put(string, absolutePath.substring(absolutePath.lastIndexOf("\\") + 1));
                        });
                    }
                    data = sb.toString();

                }
            }

        }


//        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
//            StringBuilder sb = new StringBuilder();
//            String line = br.readLine();
//
//            while (line != null) {
//                sb.append(line);
//                //		    sb.append ( System.lineSeparator ( ) );
//                line = br.readLine();
//
//                if (getFileExtenstion(absolutePath).equals(".txt")) {
//                    lineObjList.add(sb.toString());
//                    getWords(sb.toString()).stream().forEach(string -> {
//                        lineWordMap.put(string, absolutePath.substring(absolutePath.lastIndexOf("\\") + 1));
//                    });
//                }
//
//            }
//
//            data = sb.toString();
//
//        }


        return data;
    }

    public static List<String> getWords(String text) {
        List<String> words = new ArrayList<String>();
        if (text != null) {
            BreakIterator breakIterator = BreakIterator.getWordInstance();
            breakIterator.setText(text);
            int lastIndex = breakIterator.first();
            while (BreakIterator.DONE != lastIndex) {
                int firstIndex = lastIndex;
                lastIndex = breakIterator.next();
                if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
                    words.add(text.substring(firstIndex, lastIndex));
                    lineWordList.add(text.substring(firstIndex, lastIndex));
                }
            }

//            String[] splittedArray = text.split("\\s");
//            Arrays.stream(splittedArray).forEach(string -> {
//                words.add(string);
//                lineWordList.add(string);
//            });

        }

        return words;
    }


    public static boolean checkFileExist(String absolutePath) {
        File file = new File(absolutePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static void fileAttributeFix(File file) {
        if (file.isHidden()) {
            try {
                setRemoveHiddenAttrib(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.canRead()) {

        }

    }

    public static void FileWrite(String absolutePath, String text) throws IOException {
        File file = new File(absolutePath);
        if (file.exists()) {
            RecentNoteStorage.setRecentNote(absolutePath, absolutePath);
//            EditHistoryStorage.addEditHistory(new EditHistory(absolutePath, text));


            fileAttributeFix(file);

            FileWriter fw = new FileWriter(file);
            if (text != null) {
                fw.write(text);
                fw.close();


            }
        }
        //	  setHiddenAttrib ( absolutePath );
    }

    public static void createNewFile(String absolutepath) {
        File file = new File(absolutepath);
        String currentNoteSpace = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
        try {
            file.createNewFile();
            String strPath = absolutepath.substring(absolutepath.lastIndexOf("\\") + 1, absolutepath.length());
            LogsObjStorage.addLogsObj(new LogsObj(strPath, currentNoteSpace, REASON.CREATED));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void backupCreateNewFile(String absolutepath) {
        File file = new File(absolutepath);
        String currentNoteSpace = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
        try {
            file.createNewFile();
            String strPath = absolutepath.substring(absolutepath.lastIndexOf("\\") + 1, absolutepath.length());
            LogsObjStorage.addLogsObj(new LogsObj(strPath, currentNoteSpace, REASON.BACKUP));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void deleteFile(String absolutepath) {


        File file = new File(absolutepath);
        String currentNoteSpace = NoteSpacePathStorage.getNoteSpacePathObj().spacePath;
        if (file.exists()) {

            String strPath = absolutepath.substring(absolutepath.lastIndexOf("\\") + 1, absolutepath.length());
            LogsObjStorage.addLogsObj(new LogsObj(strPath, currentNoteSpace, REASON.DELETED));

            try {
                TextObject to = buildObject(absolutepath);
                BackupStorage.addBackup(new Backup(strPath, to.getText()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.delete();
        }
    }

    public static TextObject buildObject(String absolutePath) throws IOException {
        File file = new File(absolutePath);
        String fileName = file.getName();
        String fileAbsolutePath = file.getAbsolutePath();
        String fileText = null;


        fileText = readTextFromFile(fileAbsolutePath);


        boolean canWrite = file.canWrite();
        boolean canRead = file.canRead();
        boolean isHidden = file.isHidden();
        byte length = (byte) file.length();      //file size


        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

        //	  System.out.println("creationTime: " + attr.creationTime());
        //	  System.out.println("lastAccessTime: " + attr.lastAccessTime());
        //	  System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
        long createdDate = attr.creationTime().toMillis();
        long modifiedDate = file.lastModified();

        return new TextObject(fileName, fileAbsolutePath, fileText, canWrite, canRead, isHidden, length, createdDate, modifiedDate);
    }

    public static String getFileExtenstion(String absolutePath) {
        String extension = absolutePath;
        int lastIndexOf = extension.lastIndexOf(".");
        if (lastIndexOf == -1) {
            extension = "";
        }
        extension = extension.substring(lastIndexOf);
        return extension;
    }


}
