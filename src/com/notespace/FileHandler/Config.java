package com.notespace.FileHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Config
{
    public static String createComDirectory ( )
    {
	  String path = System.getProperty ( "user.home" ) + File.separator + "Documents";
	  path += File.separator + "com.notespace" + File.separator;
	  File file = new File ( path );
	  if ( file.exists ( ) ) {

	  }
	  else {
		file.mkdir ( );
	  }
	  return path;
    }

    public static void checkNoteSpaceCerfiticated ( )
    {      //check the space is wrong or not

    }


    public static void main ( String[] args )
    {
	  File file = new File ( "D:\\Backup\\Back\\SpaceShip" );
	  try {
		Desktop.getDesktop ( ).open ( file );
	  }
	  catch ( IOException e ) {
		e.printStackTrace ( );
	  }
    }
}
