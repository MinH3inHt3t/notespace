package com.notespace;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighLight
{


    public static List<String> getHashHightLight ( String string )
    {
	  String symbol = "#";
	  //	  String str = "Hello I am #Elsa . I am junior #Java n:Developer at @Mandalay(Burma) at time:10pm .";
	  String str = string;
	  Pattern MY_PATTERN = Pattern.compile ( symbol + "(\\S+)" );
	  Matcher mat = MY_PATTERN.matcher ( str );
	  List < String > strs = new ArrayList < String > ( );
	  while ( mat.find ( ) ) {
		//System.out.println(mat.group(1));
		strs.add ( symbol + mat.group ( 1 ) );
	  }
	  return strs;
    }

    public static List<String > getAtHightLight ( String string )
    {
	  String symbol = "@";
	  //	  String str = "Hello I am #Elsa . I am junior #Java n:Developer at @Mandalay(Burma)  @time:10pm .";
	  String str = string;
	  Pattern MY_PATTERN = Pattern.compile ( symbol + "(\\S+)" );
	  Matcher mat = MY_PATTERN.matcher ( str );
	  List < String > strs = new ArrayList < String > ( );
	  while ( mat.find ( ) ) {
		//System.out.println(mat.group(1));
		strs.add ( symbol + mat.group ( 1 ) );
	  }
	  return strs;
    }


    public static List<String > getHyperLinkHightLight ( String string )
        {
    	  String symbol = "http";

    	  //	  String str = "Hello I am #Elsa . I am junior #Java n:Developer at @Mandalay(Burma)  @time:10pm .";
    	  String str = string;
    	  Pattern MY_PATTERN = Pattern.compile ( symbol + "(\\S+)" );
    	  Matcher mat = MY_PATTERN.matcher ( str );
    	  List < String > strs = new ArrayList < String > ( );
    	  while ( mat.find ( ) ) {
    		//System.out.println(mat.group(1));
    		strs.add ( symbol + mat.group ( 1 ) );
    	  }
    	  return strs;
        }

    public static void main ( String[] args )
    {

	  //	  getHashHightLight ( "H" ).stream ( ).forEach ( System.out :: println );
	  getAtHightLight ( "Min @Hein Htet at @Mandalay" ).stream ( ).forEach ( System.out :: println );

    }
}
