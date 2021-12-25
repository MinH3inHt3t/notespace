package com.notespace.ScheduleTest.TestingProcessHelper;

import com.notespace.FileHandler.BuildNoteSpace;

public class DetectAllNoteSpaceFolder
{
    public static void main ( String[] args )
    {
	  //	  String[] strings = { ".png" , ".jpg" , ".jpeg" };
	  //	  String matchString = ".png";
	  //	  boolean contains = Arrays.stream ( strings ).anyMatch ( ".jpegs" :: equals );
	  //        System.out.println (contains );

	  BuildNoteSpace.getDetectedNoteSpacePath ("D:").stream ().forEach (System.out::println);

    }
}
