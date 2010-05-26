package edu.chl.tda366badbudgie.io.impl.parsers;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.chl.tda366badbudgie.util.Animation;
import edu.chl.tda366badbudgie.util.Sprite;

/**
 * Creates sprite instances from elements.
 * @author jesper
 *
 */
public class SpriteParser extends AbstractElementParser<Sprite> {

	public SpriteParser(Element data) {
		super(data);
	}

	@Override
	public Sprite parseData() throws ParserException {
		
		String texID = ElementTools.getString(getData(), "textureid");
		int segX = 0, segY = 0;
		
		if(ElementTools.getBoolean(getData(), "animated")){
			Element segments = (Element)getData().getElementsByTagName("segments").item(0);
			segX = ElementTools.getInteger(segments, "x");
			segY = ElementTools.getInteger(segments, "y");
			
			NodeList anims = getData().getElementsByTagName("animation");
			List<Animation> animList = new LinkedList<Animation>();
			
			for(int i = 0; i < anims.getLength(); i++){
				Element anim = (Element) anims.item(i);
				animList.add((new AnimationParser(anim)).parseData());
			}
			
			return new Sprite(texID, segX, segY, animList);
		} else {
			return new Sprite(texID);
		}
	}

}
