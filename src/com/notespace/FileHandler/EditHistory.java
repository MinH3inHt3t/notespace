package com.notespace.FileHandler;

import java.io.Serializable;
import java.util.UUID;

public class EditHistory implements Serializable
{
    public String id;
    public String filePath;
    public String text;
    public long date;

    public EditHistory ( String filePath , String text )
    {
        this.id = UUID.randomUUID ().toString ();
        this.filePath = filePath;
        this.text = text;
        this.date = System.currentTimeMillis ();
    }

    @Override
    public String toString ( )
    {
        return "EditHistory{" +
                "id='" + id + '\'' +
                ", filePath='" + filePath + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
