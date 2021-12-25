package com.notespace.TreeMode;

import com.ocpsoft.pretty.time.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureModel

{
    private String date;
    private String captureText;


    public CaptureModel ( String captureText , long date )
    {
	  this.captureText = captureText;
	  this.date = convertLongToDate ( date );
    }

    private String convertLongToDate ( long date )
    {
	  SimpleDateFormat dateFormat = new SimpleDateFormat ( "MM/dd/yy HH:mm:ss a" );
	  Date d = new Date ( date );

	  PrettyTime p = new PrettyTime ( );
	  return dateFormat.format ( d ) + " " + p.format ( d );
    }


    public String getDate ( )
    {
	  return date;
    }

    public void setDate ( String date )
    {
	  this.date = date;
    }

    public String getCaptureText ( )
    {
	  return captureText;
    }

    public void setCaptureText ( String captureText )
    {
	  this.captureText = captureText;
    }

    @Override
    public String toString ( )
    {
	  return "CaptureModel{" +
		    "captureText='" + captureText + '\'' +
		    ", date='" + date + '\'' +
		    '}';
    }
}