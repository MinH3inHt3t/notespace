package com.notespace.FileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ExternalNotePathStorage
{
    public static String version = "version-info-1.0.1";
    private static ArrayList < ExternalNotePath > externalNoteList = new ArrayList < ExternalNotePath > ( );
    String filename;


    public ExternalNotePathStorage ( )
    {

	  //	  read ( );
    }

    public static void main ( String args[] )
    {

//	  setExternalNotePath ( "D://NoteSpaceHere","newSpace" );
//	  setExternalNotePath ( "","ExternalTxt" );
//	  	  addExternalNote ( new ExternalNotePath ( "D://HelloWorld" ) );


	  //	  	  addExternalNote ( new ExternalNotePath ( "D://Javafx" ) );

	  //	  delete_ExternalNote ( "D://HelloWorld" );

	  //	  ExternalNotePath en = getExternalNote ( "D://HelloWorld" );	//UPDATE PROCESS
	  //	  en.filepath = "filepath";
	  //	  update_ExternalNote ( en );
	  //	  System.out.println ( en.id );

//	  setExternalNotePath ( "D://HW" , null );
	  //	  delete_ExternalNote ( "D://helloworld" );

	  //	  delete_ExternalNote ( "D://PPT" );
	  //	  setExternalNotePath ( "D://Hello World" , null );


	  getExternalNoteList ( ).stream ( ).forEach ( System.out :: println );



    }


    public static void setExternalNotePath ( String previousPath , String newPath )
    {

	  if ( newPath != null ) {
		if ( previousPath != null ) {
		    ExternalNotePath ns = getExternalNote ( previousPath );
		    if ( ns != null ) {
			  ns.filepath = newPath;
			  update_ExternalNote ( ns );
		    }
		    else {
			  if ( checkIfPathExist ( newPath ) ) {
				ExternalNotePath noteCardSetting = new ExternalNotePath ( previousPath );
				noteCardSetting.filepath = newPath;
				addExternalNote ( noteCardSetting );
			  }

		    }

		}
	  }
	  else {
		delete_ExternalNote ( previousPath );
	  }


    }
    //
    //    public static void setLabel ( String filePath , String labeltext )
    //    {
    //	  NoteCardSetting ns = getExternalNote ( filePath );
    //	  if ( ns != null ) {
    //		ns.label = labeltext;
    //		update_ExternalNote ( ns );
    //	  }
    //	  else {
    //		NoteCardSetting noteCardSetting = new NoteCardSetting ( filePath );
    //		noteCardSetting.label = labeltext;
    //		addExternalNote ( noteCardSetting );
    //
    //	  }
    //
    //    }

    public static boolean checkIfPathExist ( String filePath )
    {

	  for ( ExternalNotePath ns : getExternalNoteList ( ) ) {
		if ( ns.filepath.equals ( filePath ) ) {
		    return false;
		}
	  }
	  return true;
    }

    public static ExternalNotePath getExternalNote ( String filePath )
    {
	  read ( );
	  ExternalNotePath i = null;
	  for ( ExternalNotePath u : externalNoteList ) {
		if ( filePath.equals ( u.filepath ) ) {
		    i = u;
		}

	  }
	  return i;
    }

    public static ArrayList < ExternalNotePath > getExternalNoteList ( )
    {
	  read ( );
	  return externalNoteList;
    }


    public static void save ( )
    {
	  //        read ();
	  try {
		FileOutputStream fileout = new FileOutputStream ( Config.createComDirectory ( ) + version + "extntlist.temp" );
		ObjectOutputStream out = new ObjectOutputStream ( fileout );
		out.writeObject ( externalNoteList );
		fileout.close ( );
		out.close ( );
//		System.out.println ( "file saved" );
	  }

	  catch ( IOException e ) {
		// TODO Auto-generated catch block
		e.printStackTrace ( );
	  }
    }

    public static void read ( )
    {
	  File myfile = new File ( Config.createComDirectory ( ) + version + "extntlist.temp" );
	  if ( myfile.exists ( ) ) {

		try {
		    FileInputStream filein = new FileInputStream ( Config.createComDirectory ( ) + version + "extntlist.temp" );
		    ObjectInputStream in = new ObjectInputStream ( filein );
		    externalNoteList = ( ArrayList < ExternalNotePath > ) in.readObject ( );

		    filein.close ();
		    in.close ();
		}
		catch ( Exception e ) {
		    // TODO Auto-generated catch block
		    e.printStackTrace ( );
		}

	  }
	  else {

		System.out.println ( "file does not exist" );
	  }
    }

    private static void addExternalNote ( ExternalNotePath extPath )
    {
	  read ( );
	  externalNoteList.add ( extPath );
	  save ( );
    }

    private static void update_ExternalNote ( ExternalNotePath extPath )
    {
	  read ( );
	  for (
		    int i = 0 ;
		    i < externalNoteList.size ( ) ;
		    i++
	  ) {
		if ( extPath.id.equals ( externalNoteList.get ( i ).id ) ) {
		    externalNoteList.set ( i , extPath );
		}
	  }
	  save ( );
    }

    public static void delete_ExternalNote ( String filePath )
    {
	  read ( );
	  for (
		    Iterator < ExternalNotePath > itrs = externalNoteList.iterator ( ) ;
		    itrs.hasNext ( ) ; ) {
		ExternalNotePath u = itrs.next ( );
		if ( u.filepath.equals ( filePath ) ) {
		    itrs.remove ( );
		}
	  }
	  save ( );
    }
}
