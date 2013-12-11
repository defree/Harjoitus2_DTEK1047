package mapviewer;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.*;

import org.xml.sax.SAXException;

/**
 * Lukee XML-tiedoston osoitteesta syötetystä osoitteesta, eli tässä harjoituksessa: 
 * http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities
 * 
 * Parsii XML-tiedostoston hakien siitä layer-tagin sisältä name-tagin ja title-tagin sisällöt.
 * Parsimen asetukset, määritykset jne {@link MapXMLHandler} luokassa.
 */
public class MapXML {
	
	public MapXMLHandler handler;
	
	public List<MapLayers> getMapLayers(String capabilitiesURL) throws Exception {
		try {  
			URL url = new URL(capabilitiesURL);
			URLConnection con = url.openConnection();
			con.setReadTimeout( 1000 );
			InputStream iS = con.getInputStream();
			
			SAXParserFactory parserFactor = SAXParserFactory.newInstance();
			SAXParser parser = parserFactor.newSAXParser();
			handler = new MapXMLHandler();
			parser.parse(iS, handler);
			//new File("/Users/Defu/git/Harjoitus2/Harjoitus2_DTEK1047/mapviewer/wms.xml")

			
		} catch (NullPointerException e) {
			e.printStackTrace();
		
		} catch (IOException p) {
		    throw new SAXException("I/O error", p);
		}
		
		return handler.layerList;
	}
}
