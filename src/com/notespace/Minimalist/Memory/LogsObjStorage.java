package com.notespace.Minimalist.Memory;

import com.notespace.FileHandler.Config;
import com.notespace.Minimalist.Logs.REASON;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class LogsObjStorage {
    public static String version = "version-info-1.0.1";
    private static ArrayList<LogsObj> logobj_list = new ArrayList<LogsObj>();


    public LogsObjStorage() {

        //	  read ( );
    }

    public static void main(String args[]) {

        addLogsObj(new LogsObj("App2.txt", "D://NoteSpaceHere//T3", REASON.CREATED));

//        delete_logsObj("29d72033-a693-4b0b-8358-ad7ac6ec6e5a");

        getLogsObjList().stream().forEach(e -> {
            System.out.println(e.getFormattedTime());
        });

//        LogsObj logsObj =getLogsObj("73650403-ebb6-48e1-afe5-eb0e0070df9f");
//        System.out.println(logsObj.getReason());
    }

    public static void saveLog() {

    }


    public static LogsObj getLogsObj(String id) {
        read();
        LogsObj i = null;
        for (LogsObj l : logobj_list) {
            if (id.equals(l.getId())) {
                i = l;
            }

        }
        return i;
    }

    public static ArrayList<LogsObj> getLogsObjList() {
        read();
        return logobj_list;
    }

    public static void addLogsObj(LogsObj log) {
        read();
        logobj_list.add(log);
        save();
    }

    private static void save() {
        //        read ();
        try {

            FileOutputStream fileout = new FileOutputStream(Config.createComDirectory() + "logs.tmp");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(logobj_list);
            fileout.close();
            out.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void read() {
        File myfile = new File(Config.createComDirectory() + "logs.tmp");
        if (myfile.exists()) {

            try {
                FileInputStream filein = new FileInputStream(Config.createComDirectory() + "logs.tmp");
                ObjectInputStream in = new ObjectInputStream(filein);
                logobj_list = (ArrayList<LogsObj>) in.readObject();

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

    public static void update_logsObj(LogsObj log) {
        read();
        for (
                int i = 0;
                i < logobj_list.size();
                i++
        ) {
            if (log.getId().equals(logobj_list.get(i).getId())) {
                logobj_list.set(i, log);
            }
        }
        save();
    }

    public static void delete_logsObj(String id) {
        read();
        for (
                Iterator<LogsObj> itrs = logobj_list.iterator();
                itrs.hasNext(); ) {
            LogsObj l = itrs.next();
            if (l.getId().equals(id)) {
                itrs.remove();
            }
        }
        save();
    }
}
