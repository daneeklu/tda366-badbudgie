private package edu.chl.tda366badbudgie.io.impl;

import org.w3c.dom.Document;

/**
 * AbstractParser
 * Defines basic structure of xml-parsers.
 * @author jesper
 *
 */
public abstract class AbstractParser {

	private Document xmlData;
	
	public AbstractParser(Document xmlDocument){
		xmlData = xmlDocument;
	}
	
	/**
	 * Returns the xml-data used by this parser.
	 * @return a Document with xml-data.
	 */
	public Document getData(){
		return xmlData;
	}
	
	/**
	 * Parses the xml-data of the document.
	 */
	public abstract void parseData();
	
	/**
	 * Tests whether the xml structure is valid.
	 * @return true if the xml structure passes the test.
	 */
	public abstract boolean validate();
}
