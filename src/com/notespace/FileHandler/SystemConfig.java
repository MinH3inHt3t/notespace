package com.notespace.FileHandler;

import java.io.Serializable;

public class SystemConfig implements Serializable
{
    private static SystemConfig instance;

    public boolean isBackgroundImageSet;
    public String imagePath1;
    public String imagePath1BgColor;
    public double imagePath1effectWidth;
    public int imagePath1effectIteration;
    public String imagePath2;
    public String imagePath2BgColor;
    public double imagePath2effectWidth;
    public int imagePath2effectIteration;
    public String glassTheme;
    public String password = "pass";


    private SystemConfig ( )
    {

    }


    public static SystemConfig getInstance ( )
    {
	  instance = new SystemConfig ( );
	  return instance;
    }


    @Override
    public String toString ( )
    {
	  return "SystemConfig{" +
		    "isBackgroundImageSet=" + isBackgroundImageSet +
		    ", imagePath1='" + imagePath1 + '\'' +
		    ", imagePath1BgColor='" + imagePath1BgColor + '\'' +
		    ", imagePath1effectWidth=" + imagePath1effectWidth +
		    ", imagePath1effectIteration=" + imagePath1effectIteration +
		    ", imagePath2='" + imagePath2 + '\'' +
		    ", imagePath2BgColor='" + imagePath2BgColor + '\'' +
		    ", imagePath2effectWidth=" + imagePath2effectWidth +
		    ", imagePath2effectIteration=" + imagePath2effectIteration +
		    ", glassTheme='" + glassTheme + '\'' +
		    '}';
    }
}
