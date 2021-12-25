package com.notespace.FileHandler;

import javax.swing.*;

public class Permit
{
    public static void main ( String[] args )
    {
	  permitAccept ( );
    }

    public static void permitAccept ( )
    {
	  if ( NoteSpacePathStorage.CheckNoteSpacePathExists ( ) == false ) {
		JOptionPane.showMessageDialog ( null , "You Accidently Delete or Remove your active NoteSpace \n Please restart the program" , "ERROR" , JOptionPane.ERROR_MESSAGE );
		System.exit ( 1 );
	  }
	  if ( BuildNoteSpace.CheckNoteSpaceFormat ( ) == false ) {
		JOptionPane.showMessageDialog ( null , "You Accidently Edit NoteSpace structure! \n You need to restart the program!" , "ERROR" , JOptionPane.ERROR_MESSAGE );
		System.exit ( 1 );
	  }

    }
}
