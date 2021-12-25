package com.notespace.FileHandler;

import java.io.Serializable;

public class NoteCardSetting implements Serializable
{
	public String filePath;
	public boolean fadeAnimation=true;
	public String password=null;
	public String label=null;
	public boolean labelfolded = false;

	public NoteCardSetting(){

	}

    public NoteCardSetting ( String filePath  )
    {
	  this.filePath = filePath;

    }

    @Override
    public String toString ( )
    {
	  return "NoteCardSetting{" +
		    "filePath='" + filePath + '\'' +
		    ", fadeAnimation=" + fadeAnimation +
		    ", password='" + password + '\'' +
		    ", label='" + label + '\'' +
		    ", labelfolded=" + labelfolded +
		    '}';
    }
}
