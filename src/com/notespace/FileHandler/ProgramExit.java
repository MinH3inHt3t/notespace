package com.notespace.FileHandler;

import javax.swing.*;

public class ProgramExit {
    public static boolean ExitWithSavePathCondition() {
        if (NoteSpacePathStorage.getNoteSpacePathObj() == null) {

            JOptionPane.showMessageDialog(null, "You accidently deleted software support file \n" + "Please restart the program!", "ALert", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            return false;
        }
        return true;
    }

    public static boolean ExitWithNoteSpaceDoesNotExist() {
        if (true) {

            JOptionPane.showMessageDialog(null, "You accidently deleted recent NoteSpace", "ALert", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            return false;
        }
        return true;
    }

    public static boolean ExitWithNoteSpaceFormatError() {
        if (true) {

            JOptionPane.showMessageDialog(null, "You accidently edited recent NoteSpace structure", "ALert", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            return false;
        }
        return true;
    }

    public static boolean ExitWithConditions() {
//        ExitWithSavePathCondition ();
//        ExitWithNoteSpaceDoesNotExist ();
//        ExitWithNoteSpaceFormatError ();

        if (ExitWithSavePathCondition() && ExitWithNoteSpaceDoesNotExist() && ExitWithNoteSpaceFormatError()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Program closed cause of some external changes", "ALert", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return false;
    }
}
