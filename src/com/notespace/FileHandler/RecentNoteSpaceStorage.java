package com.notespace.FileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class RecentNoteSpaceStorage
{
    public static String version = "version-info-1.0.1";
    private static ArrayList < RecentNoteSpace > recentNoteSpace = new ArrayList < RecentNoteSpace > ( );
    String filename;


    public RecentNoteSpaceStorage ( )
    {

	  //	  read ( );
    }

    public static void main ( String args[] )
    {

	  //	  setRecentNoteSpace ( "D:\\NoteSpaceHere\\Folder for delete" , "D:\\NoteSpaceHere\\Folder for delete" );
	  //	  setRecentNoteSpace ( "C:\\Users\\Hp\\Downloads\\Video" , "C:\\Users\\Hp\\Downloads\\Video" );

	  //	  deleteRecentNoteSpace ( "D:\\Wallpapers\\Cyberpunk" );
//	  setRecentNoteSpace ( "D:\\NoteSpaceHere" , "D:\\NoteSpaceHere" );
	  getRecentNoteSpaceList ( ).stream ( ).forEach ( System.out :: println );


    }


    //    public static void setRecentNoteSpace ( String previousPath , String newPath )
    //    {
    //	  if ( newPath != null ) {
    //		if ( previousPath != null ) {
    //		    RecentNoteSpace ns = getRecentNoteSpaceObj ( previousPath );
    //		    if ( ns != null ) {
    //			  ns.spaceName = newPath;
    //			  updateRecentNoteSpace ( ns );
    //		    }
    //		    else {
    //			  if ( checkIfPathExist ( newPath ) ) {
    //				RecentNoteSpace noteCardSetting = new RecentNoteSpace ( previousPath );
    //				noteCardSetting.spaceName = newPath;
    //				addRecentNoteSpace ( noteCardSetting );
    //			  }
    //		    }
    //		}
    //	  }
    //	  else {
    //		deleteRecentNoteSpace ( previousPath );
    //	  }
    //    }

    public static void setRecentNoteSpace ( String previousPath , String newPath )
    {

	  if ( newPath != null ) {
		if ( previousPath != null ) {
		    RecentNoteSpace ns = getRecentNoteSpaceObj ( previousPath );
		    if ( ns != null ) {
			  ns.spaceName = newPath;
			  updateRecentNoteSpace ( ns );
		    }
		    else {
			  if ( checkIfPathExist ( newPath ) ) {
				RecentNoteSpace recentNoteSpace = new RecentNoteSpace ( previousPath );
				recentNoteSpace.spaceName = newPath;
				addRecentNoteSpace ( recentNoteSpace );
			  }

		    }

		}
	  }
	  else {
		deleteRecentNoteSpace ( previousPath );
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

	  for ( RecentNoteSpace ns : getRecentNoteSpaceList ( ) ) {
		if ( ns.spaceName.equals ( filePath ) ) {
		    return false;
		}
	  }
	  return true;
    }

    public static RecentNoteSpace getRecentNoteSpaceObj ( String filePath )
    {
	  read ( );
	  RecentNoteSpace i = null;
	  for ( RecentNoteSpace u : recentNoteSpace ) {
		if ( filePath.equals ( u.spaceName ) ) {
		    i = u;
		}

	  }
	  return i;
    }

    public static ArrayList < RecentNoteSpace > getRecentNoteSpaceList ( )
    {
	  read ( );
	  return recentNoteSpace;
    }


    public static void save ( )
    {
	  //        read ();
	  try {
		FileOutputStream fileout = new FileOutputStream ( Config.createComDirectory ( ) + version + "recentNoteSpace.temp" );
		ObjectOutputStream out = new ObjectOutputStream ( fileout );
		out.writeObject ( recentNoteSpace );
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
	  File myfile = new File ( Config.createComDirectory ( ) + version + "recentNoteSpace.temp" );
	  if ( myfile.exists ( ) ) {

		try {
		    FileInputStream filein = new FileInputStream ( Config.createComDirectory ( ) + version + "recentNoteSpace.temp" );
		    ObjectInputStream in = new ObjectInputStream ( filein );
		    recentNoteSpace = ( ArrayList < RecentNoteSpace > ) in.readObject ( );

		    in.close ();
		    filein.close ();
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

    private static void addRecentNoteSpace ( RecentNoteSpace rns )
    {
	  read ( );
	  RecentNoteSpaceStorage.recentNoteSpace.add ( rns );
	  save ( );
    }

    private static void updateRecentNoteSpace ( RecentNoteSpace rns )
    {
	  read ( );
	  for (
		    int i = 0 ;
		    i < recentNoteSpace.size ( ) ;
		    i++
	  ) {
		if ( rns.id.equals ( recentNoteSpace.get ( i ).id ) ) {
		    recentNoteSpace.set ( i , rns );
		}
	  }
	  save ( );
    }

    public static void deleteRecentNoteSpace ( String filePath )
    {
	  read ( );
	  for (
		    Iterator < RecentNoteSpace > itrs = recentNoteSpace.iterator ( ) ;
		    itrs.hasNext ( ) ; ) {
		RecentNoteSpace u = itrs.next ( );
		if ( u.spaceName.equals ( filePath ) ) {
		    itrs.remove ( );
		}
	  }
	  save ( );
    }
}
