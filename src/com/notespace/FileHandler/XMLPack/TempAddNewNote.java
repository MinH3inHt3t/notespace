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

public class TempAddNewNote
{
    public static String version = "temp-info-1.0.1";
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

	  //	  ReplaceText ( "How are you?" );
//	  ReplaceBackgroundColor ( "Background Color" );
//	  ReplaceTextColor ( "Text Color" );

	  //	  ReplaceVisibility ( false );
	  //
	  System.out.println ( returnXML ( ) );

	  //	  ReplaceText ( null );
	  //	  System.out.println ( ReadText () );

    }

    public static void ReplaceText ( String replaceText )
    {
	  String read = ReadText ( );
	  String replaced = returnXML ( ).replaceAll ( "<text>" + read.trim ( ) + "</text>" , "<text>" + replaceText.trim ( ) + "</text>" );
	  Save ( replaced );
	  //	  System.out.println ( replaced );
    }

    public static void ReplaceBackgroundColor ( String replaceBackgroundColor )
    {
	  String read = ReadBgColor ( );
	  String replaced = returnXML ( ).replaceAll ( "<bgColor>" + read.trim ( ) + "</bgColor>" , "<bgColor>" + replaceBackgroundColor.trim ( ) + "</bgColor>" );
	  Save ( replaced );
	  //	  System.out.println ( replaced );
    }

    public static void ReplaceTextColor ( String replaceTextColor )
    {
	  String read = ReadTextColor ( );
	  String replaced = returnXML ( ).replaceAll ( "<txtColor>" + read + "</txtColor>" , "<txtColor>" + replaceTextColor + "</txtColor>" );
	  Save ( replaced );
	  //	  System.out.println ( replaced );
    }

    public static void ReplaceVisibility ( boolean visibility )
    {
	  String read = String.valueOf ( ReadVisibility ( ) );
	  String concatString = visibility + "";
	  String replaced = returnXML ( ).replaceAll ( "<visibility>" + read + "</visibility>" , "<visibility>" + concatString + "</visibility>" );
	  Save ( replaced );
	  //	  System.out.println ( replaced );
    }

//    public static void ReplaceSearchValue ( String replaceSearchValue )
//    {
//	  String read = ReadSearchValue ( );
//	  String replaced = returnXML ( ).replaceAll ( "<searchValue>" + read + "</searchValue>" , "<searchValue>" + replaceSearchValue + "</searchValue>" );
//	  Save ( replaced );
//	  //	  System.out.println ( replaced );
//    }

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
	  if ( NoteSpacePathStorage.CheckBinFolderExists ( ) ) {
		File file = new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + ".xml" );
		if ( ! file.exists ( ) ) {
		    try {
			  file.createNewFile ( );
			  try {
				FileWriter fileWriter = new FileWriter ( file );
				fileWriter.write ( format ( ) );
				fileWriter.close ( );
			  }
			  catch ( IOException e ) {
				e.printStackTrace ( );
			  }
		    }
		    catch ( IOException e ) {
			  e.printStackTrace ( );
		    }

		}
		else {

		}
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

    public static String ReadText ( )
    {
	  Create_temp_info_1_0_1 ( );
	  String text = null;
	  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ( );

	  try {

		// optional, but recommended
		// process XML securely, avoid attacks like XML External Entities (XXE)
		dbf.setFeature ( XMLConstants.FEATURE_SECURE_PROCESSING , true );

		// parse XML file
		DocumentBuilder db = dbf.newDocumentBuilder ( );
		Document doc = db.parse ( new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + ".xml" ) );
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
			  text = element.getElementsByTagName ( "text" ).item ( 0 ).getTextContent ( ).trim ( );

		    }
		}

	  }
	  catch ( ParserConfigurationException | IOException | org.xml.sax.SAXException e ) {
		e.printStackTrace ( );
	  }
	  return text;
    }

    public static String ReadBgColor ( )
    {
	  Create_temp_info_1_0_1 ( );
	  String bgColor = null;
	  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ( );

	  try {

		// optional, but recommended
		// process XML securely, avoid attacks like XML External Entities (XXE)
		dbf.setFeature ( XMLConstants.FEATURE_SECURE_PROCESSING , true );

		// parse XML file
		DocumentBuilder db = dbf.newDocumentBuilder ( );
		Document doc = db.parse ( new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + ".xml" ) );
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
			  bgColor = element.getElementsByTagName ( "bgColor" ).item ( 0 ).getTextContent ( ).trim ( );

		    }
		}

	  }
	  catch ( ParserConfigurationException | IOException | org.xml.sax.SAXException e ) {
		e.printStackTrace ( );
	  }
	  return bgColor;
    }

    public static String ReadTextColor ( )
    {
	  Create_temp_info_1_0_1 ( );
	  String textColor = null;
	  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ( );

	  try {

		// optional, but recommended
		// process XML securely, avoid attacks like XML External Entities (XXE)
		dbf.setFeature ( XMLConstants.FEATURE_SECURE_PROCESSING , true );

		// parse XML file
		DocumentBuilder db = dbf.newDocumentBuilder ( );
		Document doc = db.parse ( new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + ".xml" ) );
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
			  textColor = element.getElementsByTagName ( "txtColor" ).item ( 0 ).getTextContent ( ).trim ( );

		    }
		}

	  }
	  catch ( ParserConfigurationException | IOException | org.xml.sax.SAXException e ) {
		e.printStackTrace ( );
	  }
	  return textColor;
    }

    public static boolean ReadVisibility ( )
    {
	  Create_temp_info_1_0_1 ( );
	  boolean visibility = false;
	  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ( );

	  try {

		// optional, but recommended
		// process XML securely, avoid attacks like XML External Entities (XXE)
		dbf.setFeature ( XMLConstants.FEATURE_SECURE_PROCESSING , true );

		// parse XML file
		DocumentBuilder db = dbf.newDocumentBuilder ( );

		Document doc = db.parse ( new File ( NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + version + ".xml" ) );


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
			  String result = element.getElementsByTagName ( "visibility" ).item ( 0 ).getTextContent ( ).trim ( );
			  visibility = Boolean.parseBoolean ( result );


		    }
		}

	  }
	  catch ( ParserConfigurationException | IOException | org.xml.sax.SAXException e ) {
		e.printStackTrace ( );
	  }
	  return visibility;
    }

//    public static String ReadSearchValue ( )
//    {
//	  Create_temp_info_1_0_1 ( );
//	  String searchValue = null;
//	  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ( );
//
//	  try {
//
//		// optional, but recommended
//		// process XML securely, avoid attacks like XML External Entities (XXE)
//		dbf.setFeature ( XMLConstants.FEATURE_SECURE_PROCESSING , true );
//
//		// parse XML file
//		DocumentBuilder db = dbf.newDocumentBuilder ( );
//		Document doc = db.parse ( new File ( NoteSpacePathStorage.GetNoteSpacePath ( ).spacePath + "\\.bin\\" + version + ".xml" ) );
//		doc.getDocumentElement ( ).normalize ( );
//		// get <staff>
//		NodeList list = doc.getElementsByTagName ( "Module" );
//
//		for (
//			  int temp = 0 ;
//			  temp < list.getLength ( ) ;
//			  temp++
//		) {
//
//		    Node node = list.item ( temp );
//
//		    if ( node.getNodeType ( ) == Node.ELEMENT_NODE ) {
//			  Element element = ( Element ) node;
//			  searchValue = element.getElementsByTagName ( "searchValue" ).item ( 0 ).getTextContent ( ).trim ( );
//
//		    }
//		}
//
//	  }
//	  catch ( ParserConfigurationException | IOException | org.xml.sax.SAXException e ) {
//		e.printStackTrace ( );
//	  }
//	  return searchValue;
//    }

    private static String format ( )
    {
	  //	  return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Company>\n" +
	  //		    "\n" +
	  //		    "<Module>\n" +
	  //		    "<AddNewNoteModule>\n" +
	  //		    "<text>" + "" + "</text>\n" +
	  //		    "<bgColor>" + "white" + "</bgColor>\n" +
	  //		    "<txtColor>" + "black" + "</txtColor>\n" +
	  //		    "<visibility>" + "false" + "</visibility>\t \n" +
	  //		    "</AddNewNoteModule>\n" +
	  //		    "</Module>\n" +
	  //		    "</Company>\n";

	  return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Company>\n" +
		    "\n" +
		    "<Module>\n" +
		    "<AddNewNoteModule>\n" +
		    "<text>" + "" + "</text>\n" +
		    "<bgColor>" + "white" + "</bgColor>\n" +
		    "<txtColor>" + "black" + "</txtColor>\n" +
		    "<visibility>" + "false" + "</visibility>\n" +
		    "<searchValue>" + "" + "</searchValue>\t \n" +
		    "</AddNewNoteModule>\n" +
		    "</Module>\n" +
		    "</Company>\n";


    }

}
