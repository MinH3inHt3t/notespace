package com.notespace.FileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EditHistoryStorage
{
    public static String version = "version-info-1.0.1";
    private static ArrayList < EditHistory > editHistoryList = new ArrayList < EditHistory > ( );
    String filename;


    public EditHistoryStorage ( )
    {

	  //	  read ( );
    }

    public static void main ( String args[] )
    {
	  delete_EditHistory ( "D:\\MyPhotoshop\\jcpicker.txt" );

	  getEditHistoryList ( ).stream ( ).forEach ( e -> {
		System.out.println ( e );
	  } );
	  //	    getEditHistoryListByFilePath ( "D:\\MyPhotoshop\\jcpicker.txt" ) .stream ().forEach ( e->{
	  //		  System.out.println (e );
	  //	    } );
	  //	  System.out.println ( );
	  //


    }


    public static ArrayList < EditHistory > getEditHistoryListByFilePath ( String filePath )
    {
	  read ( );
	  ArrayList < EditHistory > arrayList = new ArrayList <> ( );
	  for ( EditHistory eh : editHistoryList ) {
		if ( filePath.trim ( ).equals ( eh.filePath.trim ( ) ) ) {
		    arrayList.add ( eh );
		}
	  }

	  return arrayList;
    }

    public static EditHistory getEditHistoryObj ( String filePath )
    {
	  read ( );
	  EditHistory i = null;
	  for ( EditHistory u : editHistoryList ) {
		if ( filePath.equals ( u.filePath ) ) {
		    i = u;
		}

	  }
	  return i;
    }

    public static ArrayList < EditHistory > getEditHistoryList ( )
    {
	  read ( );
	  return editHistoryList;
    }


    public static void save ( )
    {
	  //        read ();
	  try {
		FileOutputStream fileout = new FileOutputStream ( Config.createComDirectory ( ) + version + "editHistory.temp" );
		ObjectOutputStream out = new ObjectOutputStream ( fileout );
		out.writeObject ( editHistoryList );
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
	  File myfile = new File ( Config.createComDirectory ( ) + version + "editHistory.temp" );
	  if ( myfile.exists ( ) ) {

		try {
		    FileInputStream filein = new FileInputStream ( Config.createComDirectory ( ) + version + "editHistory.temp" );
		    ObjectInputStream in = new ObjectInputStream ( filein );
		    editHistoryList = ( ArrayList < EditHistory > ) in.readObject ( );

		    filein.close ( );
		    in.close ( );

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

    public static void addEditHistory ( EditHistory editHistory )
    {
	  read ( );
	  editHistoryList.add ( editHistory );
	  save ( );
    }

    public static void update_EditHistory ( EditHistory editHistory )
    {
	  read ( );
	  for (
		    int i = 0 ;
		    i < editHistoryList.size ( ) ;
		    i++
	  ) {
		if ( editHistory.id.equals ( editHistoryList.get ( i ).id ) ) {
		    editHistoryList.set ( i , editHistory );
		}
	  }
	  save ( );
    }

    public static void delete_EditHistory ( String filePath )
    {
	  read ( );
	  for (
		    Iterator < EditHistory > itrs = editHistoryList.iterator ( ) ;
		    itrs.hasNext ( ) ; ) {
		EditHistory u = itrs.next ( );
		if ( u.filePath.equals ( filePath ) ) {
		    itrs.remove ( );
		}
	  }
	  save ( );
    }
}
