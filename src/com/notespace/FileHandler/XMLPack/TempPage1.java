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

public class TempPage1
{

    public static String version = "temp-PG1-info-1.0.1";
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

	  System.out.println ( returnXML ( ) );

	  //	  ReplaceText ( null );
	  //	  System.out.println ( ReadText () );

    }

    public static void ReplaceContentScrollPOS ( double value )
    {
	  double read = ReadContentScrollPOSValue ( );
	  String replaced = returnXML ( ).replaceAll ( "<contentScrollPOS>" + read + "</contentScrollPOS>" , "<contentScrollPOS>" + value + "</contentScrollPOS>" );
	  Save ( replaced );
	  //	  System.out.println ( replaced );
    }

    public static void ReplaceDragScrollPOS ( double value )
    {
	  double read = ReadDragScrollPOSValue ( );
	  String replaced = returnXML ( ).replaceAll ( "<dragScrollPOS>" + read + "</dragScrollPOS>" , "<dragScrollPOS>" + value + "</dragScrollPOS>" );
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

    public static String returnXML ( )
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

    public static double ReadContentScrollPOSValue ( )
    {
	  Create_temp_info_1_0_1 ( );
	  double scrollValue = 0;
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
		NodeList list = doc.getElementsByTagName ( "Module" );

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
			  String result = element.getElementsByTagName ( "contentScrollPOS" ).item ( 0 ).getTextContent ( ).trim ( );
			  scrollValue = Double.parseDouble ( result );


		    }
		}

	  }
	  catch ( ParserConfigurationException | IOException | org.xml.sax.SAXException e ) {
		e.printStackTrace ( );
	  }
	  return scrollValue;
    }

    public static double ReadDragScrollPOSValue ( )
    {
	  Create_temp_info_1_0_1 ( );
	  double scrollValue = 0;
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
		doc.getDocumentElement ( ).normalize ( );


		// get <staff>
		NodeList list = doc.getElementsByTagName ( "Module" );

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

			  String result = element.getElementsByTagName ( "dragScrollPOS" ).item ( 0 ).getTextContent ( ).trim ( );
			  scrollValue = Double.parseDouble ( result );


		    }
		}

	  }
	  catch ( ParserConfigurationException | IOException | org.xml.sax.SAXException e ) {
		e.printStackTrace ( );
	  }
	  return scrollValue;
    }



    private static String format ( )
    {
	 return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Company>\n" +
		   "\n" +
		   "<Module>\n" +
		   "<Page1>\n" +
		   "<contentScrollPOS>"+"0.0"+"</contentScrollPOS>\n" +
		   "<dragScrollPOS>"+"0.0"+"</dragScrollPOS>\n" +
		   "</Page1>\n" +
		   "</Module>\n" +
		   "</Company>\n";

//	  return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Company>\n" +
//		    "\n" +
//		    "<Module>\n" +
//		    "<Page1>\n" +
//		    "<contentScrollPOS>" + "0.0" + "</contentScrollPOS>\n" +
//		    "<dragScrollPOS>" + "0.0" + "</dragScrollPOS>\n" +
//		    "<searchValue>" + "" + "</searchValue> \n" +
//		    "</Page1>\n" +
//		    "</Module>\n" +
//		    "</Company>\n";

    }
}
