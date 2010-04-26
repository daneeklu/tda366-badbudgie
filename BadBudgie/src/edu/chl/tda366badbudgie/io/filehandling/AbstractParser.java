private package edu.chl.tda366badbudgie.io.filehandling;

import org.w3c.dom.Document;

public abstract class AbstractParser {

	Document xmlData;
	
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
	
}
