package com.notespace.FileHandler;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;

public class XMLReplaceDemo
{
    static String inputFile = NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + "temp-info-1.0.1" + ".xml";
    static String outputFile = NoteSpacePathStorage.getNoteSpacePathObj ( ).spacePath + "\\.bin\\" + "temp-info-1.0.1" + ".xml";

    public static void main ( String[] args ) throws Exception
    {
	  String replace = "'Text'";
	  Document doc = DocumentBuilderFactory.newInstance ( )
		    .newDocumentBuilder ( ).parse ( new InputSource ( inputFile ) );

	  // locate the node(s)
	  XPath xpath = XPathFactory.newInstance ( ).newXPath ( );
	  NodeList nodes = ( NodeList ) xpath.evaluate
		    ( "//AddNewNoteModule/text[text()=" + replace + "]" , doc , XPathConstants.NODESET );

	  // make the change
	  for (
		    int idx = 0 ;
		    idx < nodes.getLength ( ) ;
		    idx++
	  ) {
		nodes.item ( idx ).setTextContent ( "Mammals are the highest group" );
	  }

	  // save the result
	  Transformer xformer = TransformerFactory.newInstance ( ).newTransformer ( );
	  xformer.transform
		    ( new DOMSource ( doc ) , new StreamResult ( new File ( outputFile ) ) );
    }
}