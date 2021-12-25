package com.notespace.FileHandler;


import javax.swing.*;
import java.io.*;

public class NoteSpacePathStorage
{

    public static NoteSpacePath cache_data;


    //	public Cache_Storage(String fileString) {
    //		// TODO Auto-generated constructor stub
    //		this.filename = fileString;
    //		read();
    //	}

    public static void main ( String[] args )
    {
	  //	  	  SaveNoteSpacePath ( new NoteSpacePath ( "UUID1" , "D:\\NoteSpaceHere\\New folder" ) );
	  //	  	  	  SaveNoteSpacePath ( new NoteSpacePath ( "UUID1" , "D:\\Wallpapers\\Cyberpunk" ) );
	  //	  	  	  SaveNoteSpacePath ( new NoteSpacePath ("UUID2","D:\\NoteSpaceHere\\Space1") );
	  	  	  SaveNoteSpacePath ( new NoteSpacePath ( "UUID2" , "D:\\NoteSpaceHere\\Test" ) );


	  //	  	  SaveNoteSpacePath ( new NoteSpacePath ("1","C:\\Users\\Hp\\Downloads\\Video") );
	  //		    SaveNoteSpacePath ( new NoteSpacePath ( "","D:\\NoteSpaceHere\\Folder for delete" ) );

	  //	  SaveNoteSpacePath ( new NoteSpacePath ("1","D:\\Wallpapers\\Red dead 2") );


	  //	  NoteSpacePath noteSpacePath = getNoteSpacePathObj ( );
	  //	  System.out.println ( noteSpacePath.id + noteSpacePath.spacePath );

	  System.out.println ( getNoteSpacePathObj ( ).spacePath );
	  //	  CheckNoteSpacePathExists ( );
	  //	  System.out.println ( BuildNoteSpace.CheckNoteSpaceFormat () );

    }

    public static NoteSpacePath getNoteSpacePathObj ( )
    {

	  read ( );
	  return cache_data;

    }

    public static boolean CheckBinFolderExists ( )
    {
	  File file = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" );
	  if ( file.exists ( ) ) {
		return true;
	  }
	  else {
		JOptionPane.showMessageDialog ( null , "Don't 'DELETE' or 'REMOVE' software essential files while running \n SO,Please restart the prgoram." , "ERROR" , JOptionPane.ERROR_MESSAGE );
		System.exit ( 1 );
	  }
	  return false;
    }


    public static boolean CheckNoteSpacePathExists ( )
    {
	  if ( getNoteSpacePathObj ( ).spacePath != null ) {
		File file = new File ( getNoteSpacePathObj ( ).spacePath );
		if ( file.exists ( ) ) {
		    return true;
		}
		else {
		    //		    JOptionPane.showMessageDialog ( null , "Notespace path does not exist" );
		}
	  }

	  return false;
    }


    public static void read ( )
    {


	  File myFile = new File ( Config.createComDirectory ( ) + "temp.temp" );
	  if ( myFile.exists ( ) ) {
		try {
		    FileInputStream filein = new FileInputStream ( Config.createComDirectory ( ) + "temp.temp" );
		    ObjectInputStream in = new ObjectInputStream ( filein );
		    cache_data = ( NoteSpacePath ) in.readObject ( );
		    filein.close ( );
		    in.close ( );
		}
		catch ( Exception e ) {
		    // TODO Auto-generated catch block
		    //				e.printStackTrace();

		}
	  }
	  else {
		try {
		    myFile.createNewFile ( );
		    //				Cache_Storage cache_Storage = new Cache_Storage(config_path);
		    cache_data = new NoteSpacePath ( );
		    //				cache_Storage.save(cache_data);
		    SaveNoteSpacePath ( cache_data );

		}
		catch ( IOException e1 ) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace ( );
		}
	  }
    }

    public static void SaveNoteSpacePath ( NoteSpacePath cache_data )
    {

	  try {
		FileOutputStream fileout = new FileOutputStream ( Config.createComDirectory ( ) + "temp.temp" );
		ObjectOutputStream out = new ObjectOutputStream ( fileout );

		out.writeObject ( cache_data );
		fileout.close ( );
		out.close ( );
		System.out.println ( "saved" );
	  }
	  catch ( IOException e ) {
		// TODO Auto-generated catch block
		e.printStackTrace ( );
	  }
    }


}

