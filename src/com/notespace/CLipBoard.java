package com.notespace;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CLipBoard
{
    public static void SaveToCLip ( String text )
    {
	  Toolkit toolkit = Toolkit.getDefaultToolkit ( );
	  Clipboard clipboard = toolkit.getSystemClipboard ( );
	  StringSelection strSel = new StringSelection ( text );
	  clipboard.setContents ( strSel , null );
    }
}
