package com.notespace.ENUM;

public enum TimeFormat
{
    FORMAT1 ( "hh:mm a" ), FORMAT2 ( "hh:mm:ss" );

    String string;

    TimeFormat ( String s )
    {
	  this.string = s;
    }

    public String getFormat ( )
    {
	  return string;
    }
}
