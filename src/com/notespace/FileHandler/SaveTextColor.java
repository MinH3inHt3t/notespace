package com.notespace.FileHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveTextColor
{

    public static String version = "version-info-1.0.1";

    public SaveTextColor ( )
    {
	  //	  	  saveHashFile ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document.txt","#ffc972" );

    }

    public static void main ( String[] args )
    {
	  //	  System.out.println ( GetCardBackgroundColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document (2).txt" ) );

	  //	  	  SaveCardTextColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\GTA.txt","black");
	  //	  SaveCardTextColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document.txt" , "black" );
	  //	  SaveCardTextColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document (2).txt" , "black" );
	  //	  SaveCardTextColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document (3).txt" , "black" );
	  //	  SaveCardTextColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document (4).txt" , "black" );
	  //	  SaveCardTextColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document (5).txt" , "black" );

//	  SaveCardTextColor ( "D:\\NoteSpaceHere\\New Text Document (4).txt"," linear-gradient(to right top, #a782e0, #ba7fd9, #cb7dd2, #da7bc9, #e67abf, #ea7abb, #ee7ab8, #f27ab4, #f179b7, #f078b9, #ef77bc, #ee76bf)" );
	  SaveCardTextColor ( "D:\\NoteSpaceHere\\Space1\\RachelOo.txt" , "linear-gradient(to top,   derive(#02796b, 50%) , derive(#73e57b, 50%))" );

	  HashMap < String, String > hm = ReturnColorHashMap ( );
	  for ( Map.Entry < String, String > m : hm.entrySet ( ) ) {
		System.out.println ( m.getKey ( ) + " : " + m.getValue ( ) );
	  }


    }


    public static void SaveCardTextColor ( String filePath , String colorHex )
    {

	  HashMap < String, String > hm = new HashMap < String, String > ( );

	  if ( ReturnColorHashMap ( ) != null ) {
		hm = ReturnColorHashMap ( );
	  }
	  hm.put ( filePath , colorHex );
	  Save ( hm );


	  //	  for ( Map.Entry < String, String > m : hm.entrySet ( ) ) {
	  //		System.out.println ( m.getKey ( ) + " : " + m.getValue ( ) );
	  //	  }
    }

    public static String GetCardTextColor ( String filePath )
    {
	  HashMap < String, String > mapInFile = new HashMap < String, String > ( );

	  if ( ReturnColorHashMap ( ) != null ) {
		mapInFile = ReturnColorHashMap ( );
	  }

	  String color = null;
	  color = mapInFile.get ( filePath );
	  return color;
    }

    private static void Save ( HashMap < String, String > map )
    {

	  try {
		File fileOne = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + "txtcolor.data" );
		FileOutputStream fos = new FileOutputStream ( fileOne );
		ObjectOutputStream oos = new ObjectOutputStream ( fos );


		oos.writeObject ( map );
		oos.flush ( );
		oos.close ( );
		fos.close ( );
	  }
	  catch ( Exception e ) { }
    }

    private static HashMap < String, String > ReturnColorHashMap ( )
    {
	  HashMap < String, String > mapInFile = null;
	  if ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath != null ) {

		try {
		    File toRead = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + "txtcolor.data" );
		    FileInputStream fis = new FileInputStream ( toRead );
		    ObjectInputStream ois = new ObjectInputStream ( fis );

		    mapInFile = ( HashMap < String, String > ) ois.readObject ( );

		    ois.close ( );
		    fis.close ( );
		    //print All data in MAP
		    //			         for( Map.Entry<String,String> m :mapInFile.entrySet()){
		    //			             System.out.println(m.getKey()+" : "+m.getValue());
		    //			         }
		}
		catch ( Exception e ) { }
	  }
	  else {

	  }

	  return mapInFile;
    }
}
