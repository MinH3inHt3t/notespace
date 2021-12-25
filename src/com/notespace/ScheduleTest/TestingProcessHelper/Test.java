package com.notespace.ScheduleTest.TestingProcessHelper;


import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.NoteSpacePath;
import com.notespace.FileHandler.NoteSpacePathStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Test
{
    public static void main ( String[] args ) throws IOException
    {
//	  FileHandlings.getDictionaryList ().stream ().forEach ( result->{
//		System.out.println (result );
//	  } );
        NoteSpacePathStorage.SaveNoteSpacePath(new NoteSpacePath("","D://NoteSpaceHere//Test"));
    }


}
