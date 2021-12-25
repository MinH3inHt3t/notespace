package com.notespace.FileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class CaptureTextStorage
{
    public static String version = "version-info-1.0.1";
    private static ArrayList < CaptureText > captureTextList = new ArrayList < CaptureText>() ;
    String filename;


    public CaptureTextStorage ( )
    {

	  //	  read ( );
    }

    public static void main ( String args[] )
    {



	  addCaptureText ( new CaptureText ( "D:\\MyPhotoshop\\jcpicker.txt","cap 2" ) );



	  getCaptureTextList ( ).stream ( ).forEach ( System.out::println);

    }


    public static ArrayList < CaptureText > getCaptureTextListByFilePath ( String filePath )
    {
	  read ( );
	  ArrayList < CaptureText > arrayList = new ArrayList <> ( );
	  for ( CaptureText eh : captureTextList ) {
		if ( filePath.trim ( ).equals ( eh.getFilePath ().trim ( ) ) ) {
		    arrayList.add ( eh );
		}
	  }

	  return arrayList;
    }

    public static CaptureText getCaptureTextObj ( String filePath )
    {
	  read ( );
	  CaptureText i = null;
	  for ( CaptureText u : captureTextList ) {
		if ( filePath.equals ( u.getFilePath () ) ) {
		    i = u;
		}

	  }
	  return i;
    }

    public static ArrayList < CaptureText > getCaptureTextList ( )
    {
	  read ( );
	  return captureTextList;
    }


    public static void save ( )
    {
	  //        read ();
	  try {
		FileOutputStream fileout = new FileOutputStream ( Config.createComDirectory ( ) + version + "capli.temp" );
		ObjectOutputStream out = new ObjectOutputStream ( fileout );
		out.writeObject ( captureTextList );
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
	  File myfile = new File ( Config.createComDirectory ( ) + version + "capli.temp" );
	  if ( myfile.exists ( ) ) {

		try {
		    FileInputStream filein = new FileInputStream ( Config.createComDirectory ( ) + version + "capli.temp" );
		    ObjectInputStream in = new ObjectInputStream ( filein );
		    captureTextList = ( ArrayList < CaptureText > ) in.readObject ( );

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

    public static void addCaptureText ( CaptureText captureTextObj )
    {
	  read ( );
	  captureTextList.add ( captureTextObj );
	  save ( );
    }

    public static void update_CaptureText ( CaptureText captureTextObj )
    {
	  read ( );
	  for (
		    int i = 0 ;
		    i < captureTextList.size ( ) ;
		    i++
	  ) {
		if ( captureTextObj.getId ().equals ( captureTextList.get ( i ).getId () ) ) {
		    captureTextList.set ( i , captureTextObj );
		}
	  }
	  save ( );
    }

    public static void delete_CaptureText ( String filePath )
    {
	  read ( );
	  for (
		    Iterator < CaptureText > itrs = captureTextList.iterator ( ) ;
		    itrs.hasNext ( ) ; ) {
		CaptureText u = itrs.next ( );
		if ( u.getFilePath ().equals ( filePath ) ) {
		    itrs.remove ( );
		}
	  }
	  save ( );
    }
}
