package com.notespace.FileHandler.UISave;

import com.notespace.FileHandler.Config;

import java.io.*;

public class MainUISettingsStorage
{
    public static String version = "version-info-1.0.1";

    public static void main ( String[] args )
    {
	  MainUISettings mainUISettings = readMainUISettings ( );
	  mainUISettings.mainPageMenuBarVisible = false;
	  mainUISettings.mainPageModeMenuVisible = false;
	  saveMainUISettings ( mainUISettings );
	  System.out.println ( mainUISettings );

    }


    public static void saveMainUISettings ( MainUISettings mainUiSettings )
    {
	  try {

		FileOutputStream fo = new FileOutputStream ( Config.createComDirectory ( ) + version + "mainuiset.temp" );
		ObjectOutputStream obo = new ObjectOutputStream ( fo );
		obo.writeObject ( mainUiSettings );
		fo.close ( );
		obo.close ( );
	  }
	  catch ( IOException e ) {
		e.printStackTrace ( );
	  }

    }

    public static MainUISettings readMainUISettings ( )
    {


	  File file = new File ( Config.createComDirectory ( ) + version + "mainuiset.temp" );

	  if ( ! file.exists ( ) ) {
		try {
		    file.createNewFile ( );
		    saveMainUISettings ( MainUISettings.getInstance ( ) );
		}
		catch ( IOException e ) {
		    e.printStackTrace ( );
		}
	  }
	  try {
		FileInputStream fis = new FileInputStream ( file );
		ObjectInputStream ois = new ObjectInputStream ( fis );
		MainUISettings result = ( MainUISettings ) ois.readObject ( );
		fis.close ( );
		ois.close ( );
		return result;
	  }
	  catch ( IOException | ClassNotFoundException e ) {
		e.printStackTrace ( );

	  }


	  return null;
    }


}
