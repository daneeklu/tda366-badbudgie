package edu.chl.tda366badbudgie.io.impl.parsers;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.util.Animation;

/**
 * Parser used to create Animations from elements.
 * @author jesper
 *
 */
public class AnimationParser extends AbstractElementParser<Animation> {

	public AnimationParser(Element data) {
		super(data);
	}

	@Override
	public Animation parseData() throws ParserException {
		
		int duration = ElementTools.getInteger(getData(), "duration");
		String animId = ElementTools.getString(getData(), "id");

		String indicesStrings[] = ElementTools.getString(getData(), "indices").split(",");
		int indices[] = new int[indicesStrings.length];
		
		for(int i=0; i < indicesStrings.length; i++){
			indices[i] = Integer.parseInt(indicesStrings[i]);
		}
		return new Animation(animId, indices, duration);
	}

}
