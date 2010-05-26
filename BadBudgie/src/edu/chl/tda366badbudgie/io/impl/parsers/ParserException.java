package edu.chl.tda366badbudgie.io.impl.parsers;

/**
 * Exception thrown when encountering parsing errors.
 * 
 * @author jesper
 *
 */
public class ParserException extends Exception {

	/**
	 * Serial version UID - generated
	 */
	private static final long serialVersionUID = -4790103983571233971L;

	/**
	 * Creates a new ParserException without a specified message.
	 */
	public ParserException(){
	}
	
	/**
	 * Creats a new ParserException with the supplied error message.
	 * 
	 * @param message error message
	 */
	public ParserException(String message){
		super(message);
	}
	
}

