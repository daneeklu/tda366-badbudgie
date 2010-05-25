package edu.chl.tda366badbudgie.io.parsers.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.io.parsers.AbstractElementParser;


public class ParserTools {
	
	//Map holding parser names, keys are element tag names
	private static Map<String, String> parserMap = new HashMap<String, String>();
	
	/**
	 * Attempt to retrieve a parser associated with the provided element.
	 * 
	 * @param target element to be parsed
	 * @return a new AbstractElementParser instance using target as data.
	 * 
	 * @throws ParserException if failing to retrieve the parser.
	 */
	public static AbstractElementParser<?> getElementParser(Element target) throws ParserException{
		
		String parserPath = parserMap.get(target.getTagName());
		if(parserPath==null){
			throw new ParserException("Undefined parser for element "+target.getTagName());
		}
		
		try {
			
			Class<?> retrieve;
			retrieve = Class.forName(parserPath);
			AbstractElementParser<?> parser = (AbstractElementParser<?>) retrieve.getConstructors()[0].newInstance(target);
			return parser;
			
		} catch (ClassNotFoundException e) {
			ParserException p = new ParserException("Couldn't instantiate "+ parserPath+
					". Class could not be found.");
			p.setStackTrace(e.getStackTrace());
			throw p;
		} catch (IllegalArgumentException e) {
			ParserException p = new ParserException("Couldn't instantiate "+ parserPath+
					". Illegal arguments to constructor.");
			p.setStackTrace(e.getStackTrace());
			throw p;
		} catch (SecurityException e) {
			ParserException p = new ParserException("Couldn't instantiate "+ parserPath+
					". Check security settings.");
			p.setStackTrace(e.getStackTrace());
			throw p;
		} catch (InstantiationException e) {
			ParserException p = new ParserException("Couldn't instantiate "+ parserPath);
			p.setStackTrace(e.getStackTrace());
			throw p;
		} catch (IllegalAccessException e) {
			ParserException p = new ParserException("Couldn't instantiate "+ parserPath+
					". Illegal access.");
			p.setStackTrace(e.getStackTrace());
			throw p;
		} catch (InvocationTargetException e) {
			ParserException p = new ParserException("Couldn't instantiate "+ parserPath+
					". Could not invoke constructor.");
			p.setStackTrace(e.getStackTrace());
			throw p;
		}
	}
	
	/**
	 * Binds a parser class name to the associated element.
	 * 
	 * @param elementName tag name of element associated with parser.
	 * @param parserName class name of parser using element tags.
	 * @param parserPath package path to parser class.
	 */
	public static void bindParser(String elementName, String parserName, String parserPath){
		parserMap.put(elementName, parserPath+"."+parserName);
	}
}
