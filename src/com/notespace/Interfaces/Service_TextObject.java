package com.notespace.Interfaces;

import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.TextObject;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Service_TextObject extends Service< TextObject >
{

    TextObject textObject;
    public Service_TextObject(TextObject textObject){
        this.textObject = textObject;

    }

    @Override
    protected Task < TextObject > createTask ( )
    {
        return new Task < TextObject > ( ) {
            @Override
            protected TextObject call ( ) throws Exception
            {
                return FileHandlings.buildObject ( textObject.getAbsolutePath () );
            }
        };
    }
}
