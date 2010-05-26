package edu.chl.tda366badbudgie.io.impl.parsers;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * XML DOM-element-related tools.
 * 
 * @author jesper
 *
 */
public class ElementTools {

	/**
	 * Quick hand method for extracting an integer
	 * from an attribute in an element
	 * 
	 * @param element the element to look for the attribute in.
	 * @param attributeName the name of the attribute to parse.
	 * @return an integer holding the data of the attribute
	 * @throws ParserException if the attribute is missing or is not a valid integer.
	 */
	public static int getInteger(Element element, String attributeName) throws ParserException{
		try{
			return Integer.parseInt(element.getAttribute(attributeName));
			} catch(NumberFormatException numE){
				throw(new ParserException("Attribute missing or not valid number:"
						+attributeName+" in element:"+ element.getNodeName()));
			}
	}
	
	/**
	 * Quick hand method for extracting a 
	 * double precision number from an attribute in an element
	 * 
	 * @param element the element to look for the attribute in.
	 * @param attributeName the name of the attribute to parse.
	 * @return a double holding the data of the attribute
	 * @throws ParserException if the attribute is missing or is not a valid number.
	 */
	public static double getDouble(Element element, String attributeName) throws ParserException{
		try{
		return Double.parseDouble(element.getAttribute(attributeName));
		} catch(NumberFormatException numE){
			throw(new ParserException("Attribute missing or not valid number:"
					+attributeName+" in element:"+ element.getNodeName()));
		}
	}
	
	/**
	 * Quick hand method for extracting a boolean
	 * from an attribute in an element
	 * 
	 * @param element the element to look for the attribute in.
	 * @param attributeName the name of the attribute to parse.
	 * @return a boolean reflecting the value of the attribute
	 * @throws ParserException if the attribute is missing or is not a valid boolean.
	 */
	public static boolean getBoolean(Element element, String attributeName) throws ParserException{
		return Boolean.parseBoolean(element.getAttribute(attributeName));
	}
	
	/**
	 * Copies all attributes from one element to another.
	 * Conflicting attributes will be overwritten.
	 * 
	 * @param source the element from which the attributes will be copied
	 * @param target the element to which the attribute will be added
	 * @return a copy of target, with the added attributes.
	 */
	public static Element copyAttributes(Element source, Element target){
		NamedNodeMap attrMap = source.getAttributes();
		Element result = (Element)target.cloneNode(true);
		
		for(int i=0; i < attrMap.getLength(); i++){
			Attr att = (Attr)attrMap.item(i);
			result.setAttribute(att.getName(), att.getValue());
		}
		
		return result;
	}
	
	/**
	 * Copies all attributes and children from one element to another.
	 * Conflicting attributes and children may be overwritten if specified.
	 * 
	 * @param source the element from which the attributes will be copied
	 * @param target the element to which the attribute will be added
	 * @return a copy of target, with the added attributes.
	 */
	public static Element copyAll(Element source, Element target, boolean overwrite){
		NamedNodeMap attrMap = source.getAttributes();
		
		Element result = (Element)target.cloneNode(true);
		
		for(int i=0; i < attrMap.getLength(); i++){
			Attr att = (Attr)attrMap.item(i);
			result.setAttribute(att.getName(), att.getValue());
		}
		
		List<Element> sChildren = ElementTools.getImmediateChildren(source);
		List<Element> tChildren = ElementTools.getImmediateChildren(result);
		
		for(Element sChild : sChildren){
			for(Element tChild : tChildren){
				if(tChild.getTagName().equals(sChild.getTagName()) && overwrite){
					result.removeChild(tChild);
				}
			}
			result.appendChild(sChild);
		}
		
		return result;
	}
	
	/**
	 * Retrieves the String value of an attribute. Used to implement 
	 * consistent behaviour and avoid NullPointerException
	 * 
	 * @param source element to extract the attribute from.
	 * @param attribute name of the attribute.
	 * @return the value of the attribute.
	 * @throws ParserException if the attribute is invalid or missing.
	 */
	public static String getString(Element source, String attribute) throws ParserException{
		String att = source.getAttribute(attribute);
		if(att.equals("")){
			throw new ParserException("No attribute: \""+
					attribute+"\" in element: \""+source.getTagName());
		}
		return att;
	}
	
	/**
	 * Retrieves a list of copied Element nodes which are immediate
	 * children of the given source element.
	 * 
	 * @param source
	 * @return
	 */
	public static List<Element> getImmediateChildren(Element source){
		List<Element> list = new LinkedList<Element>();
		
		NodeList nl = source.getElementsByTagName("*");
		if(nl != null){
			if(nl.getLength()>0){
				Node n = nl.item(0);
				while(n.getNextSibling()!= null){
					if(n.getNodeType() == Node.ELEMENT_NODE){
						list.add((Element) n);
					}
					n = n.getNextSibling();
				}
			}
		}
		return list;
	}
	
}
