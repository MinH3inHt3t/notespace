package com.notespace.Minimalist.Memory;

import com.kitfox.svg.A;
import com.notespace.FileHandler.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EditHistoryObjStorage {
    public static String version = "version-info-1.0.1";
    private static ArrayList<EditHistoryObj> editHistoryObj_list = new ArrayList<EditHistoryObj>();


    public EditHistoryObjStorage() {

        //	  read ( );
    }

    public static void main(String args[]) {

        EditHistoryObj eo = new EditHistoryObj("D://NoteSpaceHere//Test//Goose.txt", "text2.txt");
        addEditHistoryObj(eo);

//        deleteEditHistoryObj("ba92cd9f-517d-417d-8103-de906a10e315");

        getEditHistoryObj_list().stream().forEach(obj->{
            System.out.println(obj);
        });

    }

    public static void saveLog() {

    }


    public static EditHistoryObj getEditHistoryObj(String id) {
        read();
        EditHistoryObj i = null;
        for (EditHistoryObj l : editHistoryObj_list) {
            if (id.equals(l.getId())) {
                i = l;
            }

        }
        return i;
    }

    public static ArrayList<EditHistoryObj> getEditHistoryObj_list() {
        read();
        return editHistoryObj_list;
    }

    public static ArrayList<EditHistoryObj> getEditHistoryObj_listByFileName(String fileName){
        read();
        ArrayList<EditHistoryObj> arrayList = new ArrayList<>();
        for(EditHistoryObj editHistoryObj:editHistoryObj_list){
            if(editHistoryObj.getFileName().equals(fileName)){
                arrayList.add(editHistoryObj);
            }
        }
        return arrayList;
    }

    public static void addEditHistoryObj(EditHistoryObj editHisObj) {
        read();
        editHistoryObj_list.add(editHisObj);
        save();
    }

    private static void save() {
        //        read ();
        try {

            FileOutputStream fileout = new FileOutputStream(Config.createComDirectory() + "editHistory.tmp");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(editHistoryObj_list);
            fileout.close();
            out.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void read() {
        File myfile = new File(Config.createComDirectory() + "editHistory.tmp");
        if (myfile.exists()) {

            try {
                FileInputStream filein = new FileInputStream(Config.createComDirectory() + "editHistory.tmp");
                ObjectInputStream in = new ObjectInputStream(filein);
                editHistoryObj_list = (ArrayList<EditHistoryObj>) in.readObject();

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

    public static void updateEditHistoryObj(EditHistoryObj editHisObj) {
        read();
        for (
                int i = 0;
                i < editHistoryObj_list.size();
                i++
        ) {
            if (editHisObj.getId().equals(editHistoryObj_list.get(i).getId())) {
                editHistoryObj_list.set(i, editHisObj);
            }
        }
        save();
    }

    public static void deleteEditHistoryObj(String id) {
        read();
        for (
                Iterator<EditHistoryObj> itrs = editHistoryObj_list.iterator();
                itrs.hasNext(); ) {
            EditHistoryObj eho = itrs.next();
            if (eho.getId().equals(id)) {
                itrs.remove();
            }
        }
        save();
    }
}
