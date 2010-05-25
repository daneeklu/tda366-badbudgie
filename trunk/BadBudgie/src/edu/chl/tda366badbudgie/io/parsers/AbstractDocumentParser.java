package edu.chl.tda366badbudgie.io.parsers;
import org.w3c.dom.Document;

import edu.chl.tda366badbudgie.io.parsers.util.ParserException;

/**
 * Generic, abstract class inherited by classes parsing xml-documents.
 * 
 * @author jesper
 *
 * @param <T> return class of parseData method.
 */
public abstract class AbstractDocumentParser<T> {
	
	private Document data;
	
	/**
	 * Create a new DocumentParser usin the supplied data.
	 * 
	 * @param data Document object containing xml-data.
	 */
	public AbstractDocumentParser(Document data){
		this.data = data;
	}
	
	/**
	 *  Returns the xml-structure used by the parser.
	 * 
	 * @return Document containing the xml-data.
	 */
	protected Document getData(){
		return data;
	}
	
	/**
	 * Parse the supplied data and return a new instance of the
	 * given class T.
	 * 
	 * @return a new instance of T
	 * @throws ParserException if a parser error occurs
	 */
	public abstract T parseData() throws ParserException;
	
}
