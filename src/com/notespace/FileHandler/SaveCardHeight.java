package com.notespace.FileHandler;

import java.io.*;
import java.util.HashMap;

public class SaveCardHeight
{

    public static String version = "version-info-1.0.1";
    public String absolutePath;
    public String colorHex;

    public SaveCardHeight ( )
    {
	  //	  	  saveHashFile ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document.txt","#ffc972" );

    }

    public static void main ( String[] args )
    {
	  //	  System.out.println ( GetCardBackgroundColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document (2).txt" ) );

	  //	  com.notespace.FileHandler.SaveCardBackgroundColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\GTA.txt","#f2f2f7" );
	  //	  	  SaveCardBackgroundColor ( "D:\\NoteSpaceHere\\New Text Document.txt" , "#f2f2f7" );
	  //	  SaveCardBackgroundColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document (2).txt" , "#ff9b73" );
	  //	  SaveCardBackgroundColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document (3).txt" , "#b592ff" );
	  //	  SaveCardBackgroundColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document (4).txt" , "#e4ee90" );
	  //	  SaveCardBackgroundColor ( "C:\\Users\\Hp\\Documents\\Your Custom Folder\\New Text Document (5).txt" , "#1E67D8" );

	  SaveCardHeight ( "D:\\NoteSpaceHere\\New Text Document.txt" , "600" );
	  System.out.println ( GetCardHeight ( "D:\\NoteSpaceHere\\New Text Document.txt" ) );

	  //	  HashMap < String, String > hm = ReturnColorHashMap ( );
	  //	  for ( Map.Entry < String, String > m : hm.entrySet ( ) ) {
	  //		System.out.println ( m.getKey ( ) + " : " + m.getValue ( ) );
	  //	  }


    }


    public static void SaveCardHeight ( String filePath , String widthValue )
    {

	  HashMap < String, String > hm = new HashMap < String, String > ( );

	  if ( ReturnHeightHashMap ( ) != null ) {
		hm = ReturnHeightHashMap ( );
	  }
	  hm.put ( filePath , widthValue );
	  Save ( hm );


	  //	  for ( Map.Entry < String, String > m : hm.entrySet ( ) ) {
	  //		System.out.println ( m.getKey ( ) + " : " + m.getValue ( ) );
	  //	  }
    }

    public static String GetCardHeight ( String filePath )
    {
	  HashMap < String, String > mapInFile = new HashMap < String, String > ( );

	  if ( ReturnHeightHashMap ( ) != null ) {
		mapInFile = ReturnHeightHashMap ( );
	  }

	  String color = null;
	  color = mapInFile.get ( filePath );
	  return color;
    }

    private static void Save ( HashMap < String, String > map )
    {

	  try {

		File fileOne = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + "cdheight.data" );
		FileOutputStream fos = new FileOutputStream ( fileOne );
		ObjectOutputStream oos = new ObjectOutputStream ( fos );


		oos.writeObject ( map );
		oos.flush ( );
		oos.close ( );
		fos.close ( );
	  }
	  catch ( Exception e ) { }
    }

    private static HashMap < String, String > ReturnHeightHashMap ( )
    {
	  HashMap < String, String > mapInFile = null;
	  if ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath != null ) {

		try {
		    //		    System.out.println ( NoteSpacePathStorage.GetNoteSpacePath ( ).spacePath + "\\.bin\\BackgroundColor.save" );
		    File toRead = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + "cdheight.data" );
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
