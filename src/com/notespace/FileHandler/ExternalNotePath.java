package com.notespace.FileHandler;

import java.io.Serializable;
import java.util.UUID;

public class ExternalNotePath implements Serializable
{
    String id;
    String filepath;

    public ExternalNotePath ( )
    {
        this.id = UUID.randomUUID ().toString ();
    }

    public ExternalNotePath ( String path )
    {
        this.id = UUID.randomUUID ().toString ();
	  this.filepath = path;
    }

    @Override
    public String toString ( )
    {
	  return "ExternalNotePath{" +
		    "id='" + id + '\'' +
		    ", filepath='" + filepath + '\'' +
		    '}';
    }
}
