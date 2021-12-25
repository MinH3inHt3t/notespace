package com.notespace.FileHandler.XMLPack;

import com.notespace.FileHandler.NoteSpacePathStorage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class TempMainPage
{

    private static String version = "temp-MainPage-info-1.0.1";

    private static String Xmlmoduleformat = "Module";
    private static String Xmlmainpageformat = "MainPage";
    private static String Xmlserchvalueformat = "SearchValue";
    //    public static String PATH = NoteSpacePathStorage.GetNoteSpacePath ( ).spacePath + "\\.bin\\" + version + ".xml";

    public static void main ( String[] args )
    {
	  //	  Create_temp_info_1_0_1 ( );
	  //	  System.out.println ( ReadText ( ) );
	  //	  	  System.out.println ( ReadBgColor ( ) );
	  //	  	  System.out.println ( ReadVisibility () );
	  //	  	ReplaceVisibility ( true );

	  //	  	  System.out.println ( ReadTextColor ( ) );
	  //	  		ReadText ();
	  //	  ReplaceContentScrollPOS ( 1.5 );
	  //	  ReplaceDragScrollPOS ( 6.5 );
	  //	  ReplaceSearchValue ( "Aero" );

	  //	  System.out.println ( ReadContentScrollPOSValue () );
	  //	  System.err.println ( ReadDragScrollPOSValue () );

	  ReplaceSearchValue ( "မင်းဟိန်းထက်" );
	  System.out.println ( returnXML ( ) );

	  //	  ReplaceText ( null );
	  //	  System.out.println ( ReadText () );

    }

    private static String format ( )
    {
	  return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Company>\n" +
		    "\n" +
		    "<" + Xmlmoduleformat + ">\n" +
		    "<" + Xmlmainpageformat + ">\n" +
		    "<" + Xmlserchvalueformat + ">" + "" + "</" + Xmlserchvalueformat + ">\n" +
		    "</" + Xmlmainpageformat + ">\n" +
		    "</" + Xmlmoduleformat + ">\n" +
		    "</Company>\n";

    }

    public static void ReplaceSearchValue ( String value )
    {
	  String read = ReadSearchValue ( );
	  String replaced = returnXML ( ).replaceAll ( "<" + Xmlserchvalueformat + ">" + read + "</" + Xmlserchvalueformat + ">" , "<" + Xmlserchvalueformat + ">" + value + "</" + Xmlserchvalueformat + ">" );
	  Save ( replaced );
	  //	  System.out.println ( replaced );
    }


    public static void DeleteTempFile ( )
    {
	  File file = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + ".xml" );
	  file.delete ( );
    }

    private static void Save ( String text )
    {
	  try {
		File file = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + ".xml" );
		FileWriter fileWriter = new FileWriter ( file );
		fileWriter.write ( text );
		fileWriter.close ( );
	  }
	  catch ( IOException e ) {
		e.printStackTrace ( );
	  }
    }

    private static void Create_temp_info_1_0_1 ( )
    {
	  File file = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + ".xml" );
	  if ( ! file.exists ( ) ) {
		try {
		    file.createNewFile ( );
		}
		catch ( IOException e ) {
		    e.printStackTrace ( );
		}
		try {
		    FileWriter fileWriter = new FileWriter ( file );
		    fileWriter.write ( format ( ) );
		    fileWriter.close ( );
		}
		catch ( IOException e ) {
		    e.printStackTrace ( );
		}
	  }
	  else {

	  }
    }

    private static String returnXML ( )
    {
	  Create_temp_info_1_0_1 ( );
	  String data = null;


	  try ( BufferedReader br = new BufferedReader ( new FileReader ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + ".xml" ) ) ) {
		StringBuilder sb = new StringBuilder ( );
		String line = br.readLine ( );

		while ( line != null ) {
		    sb.append ( line );
		    sb.append ( System.lineSeparator ( ) );
		    line = br.readLine ( );
		}
		data = sb.toString ( );
	  }
	  catch ( IOException e ) {
		e.printStackTrace ( );
	  }


	  return data;
    }

    public static String ReadSearchValue ( )
    {
	  Create_temp_info_1_0_1 ( );
	  String returnValue = null;
	  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ( );

	  try {

		// optional, but recommended
		// process XML securely, avoid attacks like XML External Entities (XXE)
		dbf.setFeature ( XMLConstants.FEATURE_SECURE_PROCESSING , true );

		// parse XML file
		DocumentBuilder db = dbf.newDocumentBuilder ( );
		Document doc = null;
		File file = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + ".xml" );
		if ( file.exists ( ) ) {
		    doc = db.parse ( file );
		}
		// optional, but recommended
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement ( ).normalize ( );

		//		System.out.println ( "Root Element :" + doc.getDocumentElement ( ).getNodeName ( ) );
		//		System.out.println ( "------" );

		// get <staff>
		NodeList list = doc.getElementsByTagName ( Xmlmoduleformat );

		for (
			  int temp = 0 ;
			  temp < list.getLength ( ) ;
			  temp++
		) {

		    Node node = list.item ( temp );

		    if ( node.getNodeType ( ) == Node.ELEMENT_NODE ) {

			  Element element = ( Element ) node;

			  // get staff's attribute
			  //			  String id = element.getAttribute ( "id" );

			  // get text
			  String result = element.getElementsByTagName ( Xmlserchvalueformat ).item ( 0 ).getTextContent ( ).trim ( );
			  returnValue = result;


		    }
		}

	  }
	  catch ( ParserConfigurationException | IOException | org.xml.sax.SAXException e ) {
		e.printStackTrace ( );
	  }
	  return returnValue;
    }


}
