package com.notespace.FileHandler.UISplitMemory;

import java.io.Serializable;

public class SplitUIMemory implements Serializable
{
    public double windowWidth;
    public double page1SplitPane1Value;
    public double page1SplitPane2Value;
    public double mainpageSplitPane1Value;

    public SplitUIMemory ( )
    {

    }

    public SplitUIMemory ( double windowWidth )
    {
	  this.windowWidth = windowWidth;
    }

    @Override
    public String toString ( )
    {
	  return "SplitUIMemory{" +
		    "windowWidth=" + windowWidth +
		    ", page1SplitPane1Value=" + page1SplitPane1Value +
		    ", page1SplitPane2Value=" + page1SplitPane2Value +
		    ", mainpageSplitPane1Value=" + mainpageSplitPane1Value +
		    '}';
    }
}
