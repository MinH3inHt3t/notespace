package com.notespace.FileHandler.UISave;

import java.io.Serializable;

public class MainUISettings implements Serializable
{
    private static MainUISettings instance;

    public boolean mainPageMenuBarVisible = true;
    public boolean mainPageModeMenuVisible = true;

    private MainUISettings ( )
    {

    }

    public static MainUISettings getInstance ( )
    {
	  return new MainUISettings ( );
    }


    @Override
    public String toString ( )
    {
	  return "MainUISettings{" +
		    "mainPageMenuBarVisible=" + mainPageMenuBarVisible +
		    ", mainPageModeMenuVisible=" + mainPageModeMenuVisible +
		    '}';
    }
}
