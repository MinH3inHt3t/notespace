package com.notespace.FileHandler;

import java.io.Serializable;
import java.util.UUID;

public class CaptureText implements Serializable
{
    private String id;
    private String filePath;
    private String captureText;
    private long date;

    public CaptureText ( String filePath , String captureText )
    {
	  this.id = UUID.randomUUID ( ).toString ( );
	  this.filePath = filePath;
	  this.captureText = captureText;
	  this.date = System.currentTimeMillis ( );
    }

    public String getId ( )
    {
	  return id;
    }

    public void setId ( String id )
    {
	  this.id = id;
    }

    public String getFilePath ( )
    {
	  return filePath;
    }

    public void setFilePath ( String filePath )
    {
	  this.filePath = filePath;
    }

    public String getCaptureText ( )
    {
	  return captureText;
    }

    public void setCaptureText ( String captureText )
    {
	  this.captureText = captureText;
    }

    public long getDate ( )
    {
	  return date;
    }

    public void setDate ( long date )
    {
	  this.date = date;
    }

    @Override
    public String toString ( )
    {
	  return "CaptureText{" +
		    "id='" + id + '\'' +
		    ", filePath='" + filePath + '\'' +
		    ", captureText='" + captureText + '\'' +
		    ", date=" + date +
		    '}';
    }
}
