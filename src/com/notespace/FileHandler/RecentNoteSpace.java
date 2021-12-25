package com.notespace.FileHandler;

import java.io.Serializable;
import java.util.UUID;

public class RecentNoteSpace implements Serializable
{
    public String  id;
    public String spaceName;

    public RecentNoteSpace ( String spaceName )
    {
	  this.id = UUID.randomUUID ( ).toString ( );
	  this.spaceName = spaceName;
    }

    @Override
    public String toString ( )
    {
	  return "RecentNoteSpace{" +
		    "id='" + id + '\'' +
		    ", spaceName='" + spaceName + '\'' +
		    '}';
    }
}
