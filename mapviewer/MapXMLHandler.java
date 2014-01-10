package mapviewer;


import java.util.ArrayList;
import java.util.List;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class MapXMLHandler extends DefaultHandler {

	boolean rightLayer = false;
	boolean rightSRS = false;
	
	List<MapLayers> layerList = new ArrayList<>();
	MapLayers layer = null;
	

	
    boolean bName = false;
    boolean bTitle = false; 
	
    @Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		//System.out.println(localName+" "+qName);
		if (qName.equalsIgnoreCase("layer")) {
			rightLayer = true;			
		}		
		else if (qName.equalsIgnoreCase("srs")) {//Odotetaan että XML-documentissa esiintyy SRS				
			rightSRS = true;
			layer = new MapLayers();
		}
		else if (qName.equalsIgnoreCase("name") && rightSRS && rightLayer) {		
			bName = true;
		} 
		else if (qName.equalsIgnoreCase("title") && rightSRS && rightLayer) {
			bTitle = true;
		}
				
	}
	
	@Override  
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (bName) {
			layer.setName(new String(ch, start, length).trim());
			bName = false;
		}
		else if (bTitle) {
			layer.setTitle(new String(ch, start, length).trim());
			bTitle = false;
			rightLayer = false;
			layerList.add(layer);
		}
	}
    
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
			
	}
}
		

