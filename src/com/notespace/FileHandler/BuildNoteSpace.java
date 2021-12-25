package com.notespace.FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BuildNoteSpace
{
    public static void main ( String[] args ) throws IOException
    {
	  //	  	  	  read_FXML_FromProject1 ( );
	  //	  dd ( ).stream ( ).forEach ( System.out :: println );


	  getDetectedNoteSpacePath ( "D:" ).stream ( ).forEach ( System.out :: println );
	  getPartitionsList ( ).stream ( ).forEach ( System.out :: println );

	  //	  File file = new File ( "E:\\ChooseDirect\\.idel" );
	  //	  System.err.println ( file.isDirectory () );

	  //	BuildSpace ( "D:\\NoteSpace" );


    }

    public static boolean CheckNoteSpaceFormat ( )
    {

	  File file = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin" );
	  if ( file.exists ( ) ) {
		return true;
	  }
	  else {
		//		JOptionPane.showMessageDialog ( null,"NoteSpace format wrong " );
	  }
	  return false;
    }


    public static ArrayList < String > getDetectedNoteSpacePath ( String partition )
    {
	  ArrayList < String > arrayList = new ArrayList <> ( );

	  int[] count = { 0 };
	  try {
		Files.walkFileTree (
			  Paths.get ( partition ) ,
			  new HashSet < FileVisitOption > ( Arrays.asList ( FileVisitOption.FOLLOW_LINKS ) ) ,
			  Integer.MAX_VALUE , new SimpleFileVisitor < Path > ( )
			  {
				@Override
				public FileVisitResult visitFile ( Path file , BasicFileAttributes attrs )
					  throws IOException
				{
				    //				    	                      System.out.printf("Visiting file %s\n", file);
				    String path = file.toFile ( ).toString ( );
				    String splits[] = path.trim ( ).split ( "\\\\" );
				    String string = splits[ splits.length - 1 ];      //Main.java
				    String[] temp = string.toString ( ).split ( "\\." );
				    String matchString = temp[ 0 ] + "." + temp[ temp.length - 1 ];
				    if ( matchString.equals ( "notespace.certificate" ) ) {
					  //					  System.err.println ( file.toFile ( ).getAbsolutePath ( ) );
					  arrayList.add ( file.getParent ( ).getParent ( ).toString ( ) );
				    }


				    ++ count[ 0 ];
				    return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed ( Path file , IOException e )
					  throws IOException
				{
				    //	                      System.err.printf("Visiting failed for %s\n", file);
				    String match = file.toFile ( ).toString ( );

				    //				    System.err.println ( match );
				    return FileVisitResult.SKIP_SUBTREE;
				}

				@Override
				public FileVisitResult preVisitDirectory ( Path dir ,
											 BasicFileAttributes attrs )
					  throws IOException
				{
				    //	                      System.out.printf("About to visit directory %s\n", dir);
				    return FileVisitResult.CONTINUE;
				}
			  } );
	  }
	  catch ( IOException e ) {
		// handle exception
	  }
	  return arrayList;
    }

    //    public static void BuildSpace ( String path )
    //    {
    //	  File file = new File ( path );
    //	  if ( ! file.exists ( ) ) {
    //		file.mkdir ( );
    //		File file1 = new File ( path + "\\" + ".bin" );      // D:\.idel
    //		file1.mkdir ( );
    ////		try {
    ////		    setHiddenAttrib ( file1.toString ( ) );
    ////		}
    ////		catch ( IOException e ) {
    ////		    e.printStackTrace ( );
    ////		}
    ////		File file2 = new File ( path + "\\" + ".bin" + "\\" + "notespace.certificate" );
    ////		try {
    ////		    file2.createNewFile ( );
    ////		}
    ////		catch ( IOException e ) {
    ////		    e.printStackTrace ( );
    ////		}
    //	  }
    //	  else {
    //		Alert alert = new Alert ( Alert.AlertType.ERROR );
    //		alert.setTitle ( "Error Dialog" );
    //		alert.setHeaderText ( "Cannot Create Directory" );
    //		alert.setContentText ( "Duplicate Directory Name" );
    //
    //		alert.showAndWait ( );
    //	  }
    //    }

    public static ArrayList < String > getPartitionsList ( )
    {
	  ArrayList < String > arrayList = new ArrayList <> ( );

	  File[] f = File.listRoots ( );
	  for (
		    int i = 0 ;
		    i < f.length ;
		    i++
	  ) {
		//		System.out.println ( "Drive: " + f[ i ] );
		String st = f[ i ].toString ( ); //C:\
		if ( ! st.equals ( "C:\\" ) ) {

		    arrayList.add ( f[ i ].toString ( ) );
		}


	  }
	  return arrayList;
    }

    public static void setHiddenAttrib ( String path ) throws IOException
    {
	  Path p = Paths.get ( path );

	  Files.setAttribute ( p , "dos:hidden" , true , LinkOption.NOFOLLOW_LINKS );      //value true to hide / false to unhide


    }

    public static void BuildSpace ( String folder )
    {
	  if ( folder != null ) {
		File file = new File ( folder );
		if ( file.exists ( ) ) {
		    File buildBinFolder = new File ( folder + "\\" + ".bin" );
		    buildBinFolder.mkdir ( );
		    File noteSpaceCertificateFile = new File ( folder + "\\" + ".bin" + "\\" + "notespace.certificate" );
		    try {
			  noteSpaceCertificateFile.createNewFile ( );
		    }
		    catch ( IOException e ) {
			  e.printStackTrace ( );
		    }
		}
		//		else {
		//		    Alert alert = new Alert ( Alert.AlertType.ERROR );
		//		    alert.setTitle ( "Error Dialog" );
		//		    alert.setHeaderText ( "Cannot Create Directory" );
		//		    alert.setContentText ( "Duplicate Directory Name" );
		//
		//		    alert.showAndWait ( );
		//		}
	  }
    }


}