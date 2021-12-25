package com.notespace.FileHandler;


import java.util.Comparator;
import java.util.Date;

public class Sorting
{

    public static class FirstCreateDate implements Comparator < TextObject >
    {

	  @Override
	  public int compare ( TextObject o1 , TextObject o2 )
	  {
		return ( int ) ( o1.getCreationDate ( ) - o2.getCreationDate ( ) );
	  }
    }

    public static class LatestCreatedDate implements Comparator < TextObject >
    {

	  @Override
	  public int compare ( TextObject o1 , TextObject o2 )
	  {
		return ( int ) ( o2.getCreationDate ( ) - o1.getCreationDate ( ) );
	  }
    }

    public static class FirstModifiedDate implements Comparator < TextObject >
    {

	  @Override
	  public int compare ( TextObject o1 , TextObject o2 )
	  {
		return ( int ) ( o1.getModifiedDate ( ) - o2.getModifiedDate ( ) );
	  }
    }

    public static class LatestModifiedDate implements Comparator < TextObject >
    {

	  @Override
	  public int compare ( TextObject o1 , TextObject o2 )
	  {
		return ( int ) ( new Date ( o2.getModifiedDate ( ) ).compareTo ( new Date ( o1.getModifiedDate ( ) ) ) );
	  }
    }


    public static class LargeFileSize implements Comparator < TextObject >
    {

	  @Override
	  public int compare ( TextObject o1 , TextObject o2 )
	  {
		return 0;
	  }
    }
}