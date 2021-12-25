package com.notespace.FileHandler;

import java.io.File;

public class TextObject
{
    private String name;
    private String absolutePath;
    private String text;
    private boolean canWrite;
    private boolean canRead;
    private boolean isHidden;
    private long length;
    private long creationDate;
    private long modifiedDate;
    private String fileExtension;


    public TextObject ( String name , String absolutePath , String text , boolean canWrite , boolean canRead , boolean isHidden , long length , long creationDate , long modifiedDate )
    {
	  this.name = name;
	  this.absolutePath = absolutePath;
	  this.text = text;
	  this.canWrite = canWrite;
	  this.canRead = canRead;
	  this.isHidden = isHidden;
	  this.length = length;
	  this.creationDate = creationDate;
	  this.modifiedDate = modifiedDate;

	  String extension = absolutePath;
	  int lastIndexOf = name.lastIndexOf ( "." );
	  if ( lastIndexOf == - 1 ) {
		extension = "";
	  }
	  extension = name.substring ( lastIndexOf );
	  this.fileExtension = extension;


    }

    public String getName ( )
    {
	  return name;
    }

    public void setName ( String name )
    {
	  this.name = name;
    }

    public String getAbsolutePath ( )
    {
	  return absolutePath;
    }

    public void setAbsolutePath ( String absolutePath )
    {
	  this.absolutePath = absolutePath;
    }

    public String getText ( )
    {
	  return text;
    }

    public void setText ( String text )
    {
	  this.text = text;
    }

    public boolean isCanWrite ( )
    {
	  return canWrite;
    }

    public void setCanWrite ( boolean canWrite )
    {
	  this.canWrite = canWrite;
    }

    public boolean isCanRead ( )
    {
	  return canRead;
    }

    public void setCanRead ( boolean canRead )
    {
	  this.canRead = canRead;
    }

    public boolean isHidden ( )
    {
	  return isHidden;
    }

    public void setHidden ( boolean hidden )
    {
	  isHidden = hidden;
    }

    public long getLength ( )
    {
	  return length;
    }

    public void setLength ( long length )
    {
	  this.length = length;
    }

    public long getCreationDate ( )
    {
	  return creationDate;
    }

    public void setCreationDate ( long creationDate )
    {
	  this.creationDate = creationDate;
    }

    public long getModifiedDate ( )
    {
	  return modifiedDate;
    }

    public void setModifiedDate ( long modifiedDate )
    {
	  this.modifiedDate = modifiedDate;
    }

    public String getFileExtension ( )
    {
	  return fileExtension;
    }

    public void setFileExtension ( String fileExtension )
    {
	  this.fileExtension = fileExtension;
    }

    @Override
    public String toString ( )
    {
	  return "com.notespace.FileHandler.TextObject{" +
		    "name='" + name + '\'' +
		    ", absolutePath='" + absolutePath + '\'' +
		    ", text='" + text + '\'' +
		    ", canWrite=" + canWrite +
		    ", canRead=" + canRead +
		    ", isHidden=" + isHidden +
		    ", length=" + length +
		    ", creationDate=" + creationDate +
		    ", modifiedDate=" + modifiedDate +
		    '}';
    }

    private String getFileExtension ( File file )
    {
	  String name = file.getName ( );
	  int lastIndexOf = name.lastIndexOf ( "." );
	  if ( lastIndexOf == - 1 ) {
		return ""; // empty extension
	  }
	  return name.substring ( lastIndexOf );
    }


}
