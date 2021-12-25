package com.notespace.FileHandler.UISplitMemory;

import com.notespace.FileHandler.NoteSpacePathStorage;

import java.io.*;
import java.util.ArrayList;

public class SplitUIMemoryStorage
{
    public static String version = "version-info-1.0.1";
    private static ArrayList < SplitUIMemory > user_list = new ArrayList < SplitUIMemory > ( );
    String filename;


    public SplitUIMemoryStorage ( )
    {

	  //	  read ( );
    }

    public static void main ( String args[] )
    {

	  //	  SplitUIMemory ns = getSplitMemory ( 200 );


	  //	  updateSplitMemo ( ns );

	  //	  setLabel ( "D:\\NoteSpaceHere\\Page1_Backup.txt","repair" );

	  //	  	  delete_noteCardSetting ( "D:\\NoteSpaceHere\\YOLO.txt" );
	  //	  	  	  setLabel ("D:\\NoteSpaceHere\\YOLO.txt" , "Yolo" );
	  //	  setPassword ( "D:\\NoteSpaceHere\\YOLO.txt" , "yolo" );
	  //	  setLabel ( "D:\\NoteSpaceHere\\UUN.txt" , "password" );
	  //	  setLabel ( "D:\\NoteSpaceHere\\Photoshop Shortcut.txt","Photoshop Usage" );

	  //	  	  System.out.println ( getNoteCardSetting ( "D:\\NoteSpaceHere\\YOsLO.txt" ) );

	  //	  System.out.println ( getSplitMemory ( 1936 ) );
	  //	  setPage1Split1Value ( 1936 , 0.80 );
	  //	  setPage1Split2Value ( 1936 , 0 );
	  getNoteCardSettingList ( ).stream ( ).forEach ( System.out :: println );
    }


    public static void setPage1Split1Value ( double windowWidth , double page1SplitPaneDiviValue )
    {
	  SplitUIMemory sm = getSplitMemory ( windowWidth );
	  if ( sm != null ) {
		sm.page1SplitPane1Value = page1SplitPaneDiviValue;
		updateSplitMemo ( sm );
	  }
	  else {
		SplitUIMemory sum = new SplitUIMemory ( windowWidth );
		sum.page1SplitPane1Value = page1SplitPaneDiviValue;
		addSplitMemo ( sum );

	  }
    }

    public static void setPage1Split2Value ( double windowWidth , double page1SplitPane2DiviValue )
    {
	  SplitUIMemory sm = getSplitMemory ( windowWidth );
	  if ( sm != null ) {
		sm.page1SplitPane2Value = page1SplitPane2DiviValue;
		updateSplitMemo ( sm );
	  }
	  else {
		SplitUIMemory sum = new SplitUIMemory ( windowWidth );
		sum.page1SplitPane1Value = page1SplitPane2DiviValue;
		addSplitMemo ( sum );

	  }
    }

    public static void setMainPageSplit1Value ( double windowWidth , double mainpageSplitPaneDiviValue )
    {
	  SplitUIMemory sm = getSplitMemory ( windowWidth );
	  if ( sm != null ) {
		sm.mainpageSplitPane1Value = mainpageSplitPaneDiviValue;
		updateSplitMemo ( sm );
	  }
	  else {
		SplitUIMemory sum = new SplitUIMemory ( windowWidth );
		sum.mainpageSplitPane1Value = mainpageSplitPaneDiviValue;
		addSplitMemo ( sum );

	  }
    }


    public static SplitUIMemory getSplitMemory ( double windowWidth )
    {
	  read ( );
	  SplitUIMemory i = null;
	  for ( SplitUIMemory u : user_list ) {
		if ( windowWidth == u.windowWidth ) {
		    i = u;
		}

	  }
	  return i;
    }

    public static ArrayList < SplitUIMemory > getNoteCardSettingList ( )
    {
	  read ( );
	  return user_list;
    }

    private static void addSplitMemo ( SplitUIMemory splitMemo )
    {
	  read ( );
	  user_list.add ( splitMemo );
	  save ( );
    }

    public static void save ( )
    {
	  //        read ();
	  try {
		FileOutputStream fileout = new FileOutputStream ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + "splitMemory.ser" );
		ObjectOutputStream out = new ObjectOutputStream ( fileout );
		out.writeObject ( user_list );
		fileout.close ( );
		out.close ( );
		System.out.println ( "file saved" );
	  }
	  catch ( IOException e ) {
		// TODO Auto-generated catch block
		e.printStackTrace ( );
	  }
    }

    public static void read ( )
    {
	  File myfile = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + "splitMemory.ser" );
	  if ( myfile.exists ( ) ) {

		try {
		    FileInputStream filein = new FileInputStream ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + "splitMemory.ser" );
		    ObjectInputStream in = new ObjectInputStream ( filein );
		    user_list = ( ArrayList < SplitUIMemory > ) in.readObject ( );
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

    public static void updateSplitMemo ( SplitUIMemory splitMemo )
    {
	  read ( );
	  for (
		    int i = 0 ;
		    i < user_list.size ( ) ;
		    i++
	  ) {
		if ( splitMemo.windowWidth == user_list.get ( i ).windowWidth ) {
		    user_list.set ( i , splitMemo );
		}
	  }
	  save ( );
    }

    public static void delete_noteCardSetting ( double windowWidth )
    {
	  read ( );
	  user_list.removeIf ( u -> u.windowWidth == windowWidth );
	  save ( );
    }
}
