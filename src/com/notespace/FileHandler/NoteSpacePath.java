package com.notespace.FileHandler;

import java.io.Serializable;

public class NoteSpacePath implements Serializable
{
    public String id;
    public String spacePath;

    public NoteSpacePath(){

    }

    public NoteSpacePath ( String id , String spacePath )
    {
	  this.id = id;
	  this.spacePath = spacePath;
    }


}
