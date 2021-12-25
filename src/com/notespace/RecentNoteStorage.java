package com.notespace;

import com.notespace.FileHandler.Config;
import com.notespace.FileHandler.RecentNote;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class RecentNoteStorage
{
    public static String version = "version-info-1.0.1";
    private static ArrayList < RecentNote > recentNoteArrayList = new ArrayList < RecentNote > ( );
    String filename;


    public RecentNoteStorage ( )
    {

	  //	  read ( );
    }

    public static void main ( String args[] )
    {

    }


    public static void setRecentNote ( String previousPath , String newPath )
    {

	  if ( newPath != null ) {
		if ( previousPath != null ) {
		    RecentNote ns = getRecentNoteObj ( previousPath );
		    if ( ns != null ) {
			  ns.notePath = newPath;
			  ns.date = System.currentTimeMillis ( );
			  updateRecentNote ( ns );
		    }
		    else {
			  if ( checkIfPathExist ( newPath ) ) {
				RecentNote noteCardSetting = new RecentNote ( previousPath , System.currentTimeMillis ( ) );
				noteCardSetting.notePath = newPath;
				addRecentNote ( noteCardSetting );
			  }

		    }

		}
	  }
	  else {
		deleteRecentNote ( previousPath );
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

	  for ( RecentNote ns : getRecentNoteList ( ) ) {
		if ( ns.notePath.equals ( filePath ) ) {
		    return false;
		}
	  }
	  return true;
    }

    public static RecentNote getRecentNoteObj ( String filePath )
    {
	  read ( );
	  RecentNote i = null;
	  for ( RecentNote u : recentNoteArrayList ) {
		if ( filePath.equals ( u.notePath ) ) {
		    i = u;
		}

	  }
	  return i;
    }

    public static ArrayList < RecentNote > getRecentNoteList ( )
    {
	  read ( );
	  return recentNoteArrayList;
    }


    public static void save ( )
    {
	  //        read ();
	  try {
		FileOutputStream fileout = new FileOutputStream ( Config.createComDirectory ( ) + version + "recentNote.temp" );
		ObjectOutputStream out = new ObjectOutputStream ( fileout );
		out.writeObject ( recentNoteArrayList );
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
	  File myfile = new File ( Config.createComDirectory ( ) + version + "recentNote.temp" );
	  if ( myfile.exists ( ) ) {

		try {
		    FileInputStream filein = new FileInputStream ( Config.createComDirectory ( ) + version + "recentNote.temp" );
		    ObjectInputStream in = new ObjectInputStream ( filein );
		    recentNoteArrayList = ( ArrayList < RecentNote > ) in.readObject ( );


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

    private static void addRecentNote ( RecentNote rns )
    {
	  read ( );
	  RecentNoteStorage.recentNoteArrayList.add ( rns );
	  save ( );
    }

    private static void updateRecentNote ( RecentNote rns )
    {
	  read ( );
	  for (
		    int i = 0 ;
		    i < recentNoteArrayList.size ( ) ;
		    i++
	  ) {
		if ( rns.id.equals ( recentNoteArrayList.get ( i ).id ) ) {
		    recentNoteArrayList.set ( i , rns );
		}
	  }
	  save ( );
    }

    public static void deleteRecentNote ( String filePath )
    {
	  read ( );
	  for (
		    Iterator < RecentNote > itrs = recentNoteArrayList.iterator ( ) ;
		    itrs.hasNext ( ) ; ) {
		RecentNote u = itrs.next ( );
		if ( u.notePath.equals ( filePath ) ) {
		    itrs.remove ( );
		}
	  }
	  save ( );
    }
}
