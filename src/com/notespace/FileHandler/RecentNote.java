package com.notespace.FileHandler;

import java.io.Serializable;
import java.util.UUID;

public class RecentNote implements Serializable
{
    public String id;
    public String notePath;
    public long date;



    public RecentNote ( String notePath ,long currentTime )
    {
	  this.id = UUID.randomUUID ( ).toString ( );
	  this.notePath = notePath;
	  this.date = currentTime;

    }

    @Override
    public String toString ( )
    {
	  return "RecentNote{" +
		    "id='" + id + '\'' +
		    ", notePath='" + notePath + '\'' +
		    ", date=" + date +
		    '}';
    }


}
