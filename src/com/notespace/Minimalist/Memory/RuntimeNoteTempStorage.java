package com.notespace.Minimalist.Memory;

import java.util.ArrayList;
import java.util.Iterator;

public class RuntimeNoteTempStorage {

    private static ArrayList<RuntimeNoteTemp> runTimeTempObj_list = new ArrayList<>();


    public RuntimeNoteTempStorage() {

        //	  read ( );
    }

    public static void main(String args[]) {

//        RuntimeNoteTemp temp = new RuntimeNoteTemp("Doc1.txt", "Hello");
//        addRuntimeTempObj(temp);
//        RuntimeNoteTemp temp1 = new RuntimeNoteTemp("Doc2.txt", "Javafx programming");
//        addRuntimeTempObj(temp1);
//        RuntimeNoteTemp temp3 = new RuntimeNoteTemp("Doc2.txt", "Javafx programming 2");
//        addRuntimeTempObj(temp3);


        runTimeTempObj_list.forEach(runTimeNoteTemp -> {
            System.out.println(runTimeNoteTemp);
        });

//        getRunTimeTempObj_listByFileName("Doc2.txt").forEach(tempObj -> {
//            System.out.println(tempObj);
//        });

    }


    public static RuntimeNoteTemp getRuntimeNoteTemp(String id) {

        RuntimeNoteTemp i = null;
        for (RuntimeNoteTemp l : runTimeTempObj_list) {
            if (id.equals(l.getId())) {
                i = l;
            }

        }
        return i;
    }

    public static ArrayList<RuntimeNoteTemp> getRunTimeTempObj_list() {

        return runTimeTempObj_list;
    }

    public static ArrayList<RuntimeNoteTemp> getRunTimeTempObj_listByFileName(String fileName) {

        ArrayList<RuntimeNoteTemp> arrayList = new ArrayList<>();
        for (RuntimeNoteTemp editHistoryObj : runTimeTempObj_list) {
            if (editHistoryObj.getFileName().equals(fileName)) {
                arrayList.add(editHistoryObj);
            }
        }
        return arrayList;
    }

    public static void addRuntimeTempObj(RuntimeNoteTemp runTimeTempObj) {

        runTimeTempObj_list.add(runTimeTempObj);

    }


    public static void updateRunTimeTempObj(RuntimeNoteTemp runTimeTempObj) {

        for (
                int i = 0;
                i < runTimeTempObj_list.size();
                i++
        ) {
            if (runTimeTempObj.getId().equals(runTimeTempObj_list.get(i).getId())) {
                runTimeTempObj_list.set(i, runTimeTempObj);
            }
        }

    }

    public static void deleteEditHistoryObj(String id) {

        for (
                Iterator<RuntimeNoteTemp> itrs = runTimeTempObj_list.iterator();
                itrs.hasNext(); ) {
            RuntimeNoteTemp eho = itrs.next();
            if (eho.getId().equals(id)) {
                itrs.remove();
            }
        }
    }
}
