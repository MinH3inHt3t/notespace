package com.notespace.ENUM;

public enum DateFormat
{
    FORMAT1 ( "dd/MM/yyyy" ), FORMAT2 ( "dd/MMMM/yyyy" )  ;


    String string;

    DateFormat ( String s )
    {
	  this.string = s;
    }

    public String getFormat(){
        return string;
    }
}
