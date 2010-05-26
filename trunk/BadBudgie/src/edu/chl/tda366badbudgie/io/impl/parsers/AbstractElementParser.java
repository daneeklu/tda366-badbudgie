private package edu.chl.tda366badbudgie.io.impl.parsers;

import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;

/**
 * AbstractParser
 * Defines basic structure of parsers that parses Elements and returns objects.
 * 
 * @author jesper
 *
 * @param <T> return type of parseData method.
 */
public abstract class AbstractElementParser<T> {

	private Element data;
	
	public AbstractElementParser(Element data){
		this.data = data;
	}
	
	/**
	 * Returns the xml-data used by this parser.
	 * @return a Document with xml-data.
	 */
	protected Element getData(){
		return data;
	}
		
	/**
	 * Creates an object from the Element of the parser.
	 * 
	 * @throws ParserException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public abstract T parseData() throws ParserException;
	
}
