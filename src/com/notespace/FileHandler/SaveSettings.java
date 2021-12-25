package com.notespace.FileHandler;

import javax.swing.*;
import java.io.*;

public class SaveSettings
{

    public static void main ( String[] args )
    {

	  Settings s = ReadSettings ( );
	  //	  	          s.NoteCard_CreateDateFormat = DateFormat.FORMAT1.getFormat ();
	  //	  s.type = VIEW.SINGLE;
	  //	  s.SYNC_DELAY_PERMIT = false;
	  //	  	  	  s.pinPath ="D:\\NoteSpaceHere\\New Text Document.txt";

	  //	  System.out.println ( s.pinPath );
	  //	  s.NoteCard_ModifiedTimePrettyFormat = true;
	  //	  s.pinPath = null;
	  //	  s.pinPath= "D:\\NoteSpaceHere\\Space1\\Adjustment Layer.txt";
	  s.default_BackgroundColor = "black";
	  s.default_TextColor = "linear-gradient(to top,   derive(#3980F4, 50%) , derive(#BB8EF5, 50%))";
	  SaveSettings ( s );

	  //	  System.out.println ( s.pinPath );
	  //	  SaveSettings ( s );
	  //	  System.out.println ( s.autoScrollToTopWhenChangeOriginalPosition );

	  //	  	  s.MainPage_ExpandCreateDateFormat= DateFormat.FORMAT2.getFormat ();
	  //	  s.MainPage_ExpandModifiedTimePrettyFormat =true;
	  //	  s.MainPage_ExpandModifiedTimeFormat = TimeFormat.FORMAT1.getFormat ( );
	  //	  s.type = VIEW.SINGLE;
	  //	  s.NoteCard_ModifiedTimePrettyFormat = true;


    }


    public static void SaveSettings ( Settings settings )
    {
	  try {

		FileOutputStream fo = new FileOutputStream ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + Settings.version + ".set" );
		ObjectOutputStream obo = new ObjectOutputStream ( fo );
		obo.writeObject ( settings );
		fo.close ( );
		obo.close ( );
	  }
	  catch ( IOException e ) {
		e.printStackTrace ( );
	  }

    }

    public static Settings ReadSettings ( )
    {


	  if ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath != null ) {
		Permit.permitAccept ( );
		File file = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + Settings.version + ".set" );

		if ( ! file.exists ( ) ) {
		    try {
			  file.createNewFile ( );
			  SaveSettings ( Settings.getInstance ( ) );
		    }
		    catch ( IOException e ) {
			  e.printStackTrace ( );
		    }
		}
		try {
		    FileInputStream fis = new FileInputStream ( file );
		    ObjectInputStream ois = new ObjectInputStream ( fis );
		    Settings result = ( Settings ) ois.readObject ( );
		    fis.close ( );
		    ois.close ( );
		    return result;
		}
		catch ( IOException | ClassNotFoundException e ) {
		    e.printStackTrace ( );

		}
	  }
	  else {
		JOptionPane.showMessageDialog ( null , "You Accidently Deleted System files \n Please restart the program!" , "Warning" , JOptionPane.ERROR_MESSAGE );
		System.exit ( 1 );
	  }


	  return null;
    }


}
